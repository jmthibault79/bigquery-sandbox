package org.broadinstitute.thibault;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.*;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: thibault
 * Date: 8/7/13
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Insert {
    private Bigquery bigquery;

    public Insert(Credential credential) {
        bigquery = new Bigquery(Constants.TRANSPORT, Constants.JSON_FACTORY, credential);
    }

    private void createDatasetIfNecessary(String datasetName) throws IOException {
        DatasetList datasetList = bigquery.datasets().list(Constants.PROJECT_ID).execute();
        if (datasetList.getDatasets() != null) {
            List<DatasetList.Datasets> datasets = datasetList.getDatasets();
            for (DatasetList.Datasets existingDataset : datasets) {
                if (datasetName.equals(existingDataset.getDatasetReference().getDatasetId()))
                    return;
            }
        }

        DatasetReference datasetReference = new DatasetReference().setDatasetId(datasetName);
        Dataset newDataset = new Dataset().setDatasetReference(datasetReference);
        bigquery.datasets().insert(Constants.PROJECT_ID, newDataset).execute();
    }

    private void createTableIfNecessary(String datasetName, String tableName, TableSchema schema) throws IOException {
        List<TableList.Tables> tableList = bigquery.tables().list(Constants.PROJECT_ID, datasetName).execute().getTables();
        if (tableList != null) {
            for (TableList.Tables existingTable : tableList) {
                if (tableName.equals(existingTable.getTableReference().getTableId()))
                    return;
            }
        }

        TableReference tableReference = new TableReference().setProjectId(Constants.PROJECT_ID).setDatasetId(datasetName).setTableId(tableName);
        Table table = new Table().setTableReference(tableReference).setSchema(schema);
        bigquery.tables().insert(Constants.PROJECT_ID, datasetName, table).execute();
    }

    private JobReference insert(String datasetName, String tableName, TableSchema schema, AbstractInputStreamContent data) throws IOException {
        createDatasetIfNecessary(datasetName);
        createTableIfNecessary(datasetName,  tableName, schema);

        TableReference tableReference = new TableReference().setProjectId(Constants.PROJECT_ID).setDatasetId(datasetName).setTableId(tableName);

        JobConfigurationLoad loadConfig = new JobConfigurationLoad();
        loadConfig.setDestinationTable(tableReference);
        loadConfig.setSchema(schema);
        loadConfig.setEncoding("UTF-8");
        loadConfig.setSourceFormat("NEWLINE_DELIMITED_JSON");
        loadConfig.setWriteDisposition("WRITE_APPEND");

        JobConfiguration config = new JobConfiguration().setLoad(loadConfig);
        Job job = new Job().setConfiguration(config);

        return bigquery.jobs().insert(Constants.PROJECT_ID, job, data).execute().getJobReference();
    }

    void doInsert(String datasetName, String tableName, TableSchema schema, AbstractInputStreamContent data) throws IOException {
        JobReference job = insert(datasetName, tableName, schema, data);
        try{
            if (HelloWorld.waitForJobCompletion(job, bigquery)) {
                System.out.println("Insert done");
            }
            else {
                System.out.format("Insert did not complete after %d ms\n", Constants.WAIT_TIME);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

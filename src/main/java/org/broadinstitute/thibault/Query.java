package org.broadinstitute.thibault;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.*;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: thibault
 * Date: 7/30/13
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Query {
    private Bigquery bigquery;

    public Query(Credential credential) {
        bigquery = new Bigquery(Constants.TRANSPORT, Constants.JSON_FACTORY, credential);
    }

    private String displayQueryResult(Iterable<TableRow> responseRows) throws IOException {
        StringBuilder s = new StringBuilder();
        s.append("Query Results:\n----------------\n");
        for (TableRow row : responseRows) {
            for (TableCell field : row.getF()) {
                s.append(String.format("%-50s", field.getV()));
            }
            s.append("\n");
        }

        return s.toString();
    }

    private QueryResponse synchronousQuery(String query) throws IOException {
        QueryRequest request = new QueryRequest().setQuery(query);

        return bigquery.jobs().query(Constants.PROJECT_ID, request).execute();
    }

    void displaySynchronousQueryResult(String query) throws IOException {
        QueryResponse response = synchronousQuery(query);
        System.out.println(displayQueryResult(response.getRows()));
    }

    private JobReference asynchronousQuery(String query) throws IOException {
        JobConfigurationQuery queryConfig = new JobConfigurationQuery().setQuery(query);
        JobConfiguration config = new JobConfiguration().setQuery(queryConfig);
        Job job = new Job().setConfiguration(config);

        return bigquery.jobs().insert(Constants.PROJECT_ID, job).execute().getJobReference();
    }

    private boolean waitForAsynchronousQueryCompletion(JobReference job, long msToWait) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;

        while (msToWait > elapsedTime) {
            Job pollJob = bigquery.jobs().get(Constants.PROJECT_ID, job.getJobId()).execute();
            elapsedTime = System.currentTimeMillis() - startTime;
            System.out.format("Job status (%dms) %s: %s\n", elapsedTime,
                    job.getJobId(), pollJob.getStatus().getState());

            if (pollJob.getStatus().getState().equals("DONE")) {
                return true;
            }

            Thread.sleep(1000);
        }

        return false;
    }

    void displayAsynchronousQueryResult(String query) throws IOException {
        JobReference job = asynchronousQuery(query);
        try{
            if (waitForAsynchronousQueryCompletion(job, Constants.ASYNCHRONOUS_WAIT_TIME)) {
                GetQueryResultsResponse response = bigquery.jobs().getQueryResults(Constants.PROJECT_ID, job.getJobId()).execute();
                System.out.println(displayQueryResult(response.getRows()));
            }
            else {
                System.out.format("Query did not complete after %d ms\n", Constants.ASYNCHRONOUS_WAIT_TIME);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void listDatasets(String projectId) throws IOException {
        DatasetList datasetList = bigquery.datasets().list(projectId).execute();
        if (datasetList.getDatasets() == null) {
            System.out.println("no datasets available");
        }
        else {
            List<DatasetList.Datasets> datasets = datasetList.getDatasets();
            System.out.println("Available datasets:\n");
            for (DatasetList.Datasets dataset : datasets) {
                System.out.format("%s\n", dataset.getDatasetReference().getDatasetId());
            }
        }
    }


}

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
    static QueryResponse synchronousQuery(String query, Credential credential) throws IOException {
        Bigquery bigquery = new Bigquery(Constants.TRANSPORT, Constants.JSON_FACTORY, credential);
        QueryRequest request = new QueryRequest().setQuery(query);

        return bigquery.jobs().query(Constants.PROJECT_ID, request).execute();
    }

    static String displaySynchronousQueryResult(String query, Credential credential) throws IOException {
        StringBuilder s = new StringBuilder();
        s.append("Query Results:\n----------------\n");

        QueryResponse response = synchronousQuery(query, credential);
        for (TableRow row : response.getRows()) {
            for (TableCell field : row.getF()) {
                s.append(String.format("%-50s", field.getV()));
            }
            s.append("\n");
        }

        return s.toString();
    }

    public static void listDatasets(Credential credential) throws IOException {
        Bigquery bigquery = new Bigquery(Constants.TRANSPORT, Constants.JSON_FACTORY, credential);

        DatasetList datasetList = bigquery.datasets().list(Constants.PROJECT_ID).execute();
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

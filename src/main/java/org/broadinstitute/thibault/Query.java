package org.broadinstitute.thibault;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
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
    private static final NetHttpTransport transport = new NetHttpTransport();
    private static final JsonFactory jsonFactory = new JacksonFactory();

    private static final String PROJECT_ID = "1008486208127";

    static QueryResponse synchronousQuery(String query, Credential credential) throws IOException {
        Bigquery bigquery = new Bigquery(transport, jsonFactory, credential);
        QueryRequest request = new QueryRequest().setQuery(query);

        return bigquery.jobs().query(PROJECT_ID, request).execute();
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
        Bigquery bigquery = new Bigquery(transport, jsonFactory, credential);

        DatasetList datasetList = bigquery.datasets().list(PROJECT_ID).execute();
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

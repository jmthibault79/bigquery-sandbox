package org.broadinstitute.thibault;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.Job;
import com.google.api.services.bigquery.model.JobReference;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: thibault
 * Date: 7/29/13
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorld {
    public static boolean waitForJobCompletion(JobReference job, Bigquery bigquery) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;

        while (Constants.WAIT_TIME > elapsedTime) {
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

    public static void main(String args[]) {
        String wikiQuery = "SELECT TOP(title, 10) as title, COUNT(*) as revision_count FROM [publicdata:samples.wikipedia] WHERE wp_namespace = 0;";
        try {
            Insert insert = new Insert(ClientAuth.getCredential());
            AbstractInputStreamContent data = new FileContent("application/octet-stream", new File("simple_test.json"));
            insert.doInsert("thibault_test", "simple", Schema.getSimpleSchema(), data);

            Query query = new Query(ClientAuth.getCredential());
            query.displaySynchronousQueryResult(wikiQuery);
            query.displayAsynchronousQueryResult(wikiQuery);
            query.listDatasets(Constants.PROJECT_ID);
            query.listDatasets("publicdata");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

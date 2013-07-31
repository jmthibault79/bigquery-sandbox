package org.broadinstitute.thibault;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: thibault
 * Date: 7/29/13
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorld {
    public static void main(String args[]) {
        String wikiQuery = "SELECT TOP(title, 10) as title, COUNT(*) as revision_count FROM [publicdata:samples.wikipedia] WHERE wp_namespace = 0;";
        try {
            Query query = new Query(ServiceAuth.getCredential());
            query.displaySynchronousQueryResult(wikiQuery);
            query.displayAsynchronousQueryResult(wikiQuery);
            query.listDatasets(Constants.PROJECT_ID);
            query.listDatasets("publicdata");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

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
        System.out.println("Client Secrets loaded: " + ClientAuth.clientSecrets);
        System.out.println("Client Credential built: " + ClientAuth.credential);

        System.out.println("Service Credential built: " + ServiceAuth.credential);

        String wikiQuery = "SELECT TOP(title, 10) as title, COUNT(*) as revision_count FROM [publicdata:samples.wikipedia] WHERE wp_namespace = 0;";
        try {
            Query query = new Query(ServiceAuth.credential);
            query.displaySynchronousQueryResult(wikiQuery);
            query.displayAsynchronousQueryResult(wikiQuery);
            query.listDatasets(Constants.PROJECT_ID);
            query.listDatasets("publicdata");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

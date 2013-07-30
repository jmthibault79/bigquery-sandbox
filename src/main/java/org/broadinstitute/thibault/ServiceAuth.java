package org.broadinstitute.thibault;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.bigquery.BigqueryScopes;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: thibault
 * Date: 7/30/13
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceAuth {
    private static final String SERVICE_ACCOUNT_ID = "1008486208127-2o9v0e7b6ge70lmmm3ur4l5f1l4op1lg@developer.gserviceaccount.com";
    private static final String SERVICE_PRIVATE_KEY_LOCATION = "6dc91078d724087ecead3522126559dfc965c656-privatekey.p12";

    private static final JsonFactory jsonFactory = new JacksonFactory();
    private static final NetHttpTransport transport = new NetHttpTransport();
    private static final List SCOPES = Arrays.asList(BigqueryScopes.BIGQUERY);

    static final Credential credential = buildCredential();

    static Credential buildCredential() {
        try {
            GoogleCredential.Builder credentialBuilder = new GoogleCredential.Builder();
            credentialBuilder.setJsonFactory(jsonFactory);
            credentialBuilder.setTransport(transport);
            credentialBuilder.setServiceAccountScopes(SCOPES);
            credentialBuilder.setServiceAccountId(SERVICE_ACCOUNT_ID);
            credentialBuilder.setServiceAccountPrivateKeyFromP12File(new File(SERVICE_PRIVATE_KEY_LOCATION));

            return credentialBuilder.build();
        } catch (Exception e) {
            System.out.println("Could not build the credential");
            e.printStackTrace();

            return null;
        }
    }
}

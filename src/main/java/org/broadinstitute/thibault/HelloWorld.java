package org.broadinstitute.thibault;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.bigquery.BigqueryScopes;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: thibault
 * Date: 7/29/13
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorld {
    private static final String PROJECT_ID = "1008486208127";
    private static final String CLIENTSECRETS_LOCATION = "client_secrets.json";
    private static final List SCOPES = Arrays.asList(BigqueryScopes.BIGQUERY);
    private static final JsonFactory jsonFactory = new JacksonFactory();
    private static final NetHttpTransport transport = new NetHttpTransport();

    private static final GoogleClientSecrets clientSecrets = loadClientSecrets();
    private static final Credential credential = buildCredential();

    static GoogleClientSecrets loadClientSecrets() {
        try {
            InputStream jsonStream = new FileInputStream(CLIENTSECRETS_LOCATION);
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, jsonStream);

            return clientSecrets;
        } catch (Exception e) {
            System.out.println("Could not load client_secrets.json");
            e.printStackTrace();

            return null;
        }
    }

    static Credential buildCredential() {
        try {
            GoogleCredential.Builder credentialBuilder = new GoogleCredential.Builder();
            credentialBuilder.setJsonFactory(jsonFactory);
            credentialBuilder.setClientSecrets(clientSecrets);
            credentialBuilder.setTransport(transport);

            return credentialBuilder.build();
        } catch (Exception e) {
            System.out.println("Could not build the credential");
            e.printStackTrace();

            return null;
        }
    }

    public static void main(String args[]) {
        System.out.println("Client Secrets loaded: " + clientSecrets);
        System.out.println("Credential built: " + credential);
    }
}

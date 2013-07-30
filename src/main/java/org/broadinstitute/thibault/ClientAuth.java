package org.broadinstitute.thibault;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: thibault
 * Date: 7/30/13
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClientAuth {

    private static final String CLIENTSECRETS_LOCATION = "client_secrets.json";
    private static final JsonFactory jsonFactory = new JacksonFactory();
    private static final NetHttpTransport transport = new NetHttpTransport();

    static final GoogleClientSecrets clientSecrets = loadClientSecrets();
    static final Credential credential = buildCredential();

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

}

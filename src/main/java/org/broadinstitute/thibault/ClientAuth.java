package org.broadinstitute.thibault;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

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
    static final GoogleClientSecrets clientSecrets = loadClientSecrets();
    static final Credential credential = buildCredential();

    static GoogleClientSecrets loadClientSecrets() {
        try {
            InputStream jsonStream = new FileInputStream(Constants.CLIENTSECRETS_LOCATION);
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(Constants.JSON_FACTORY, jsonStream);

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
            credentialBuilder.setJsonFactory(Constants.JSON_FACTORY);
            credentialBuilder.setClientSecrets(clientSecrets);
            credentialBuilder.setTransport(Constants.TRANSPORT);

            return credentialBuilder.build();
        } catch (Exception e) {
            System.out.println("Could not build the credential");
            e.printStackTrace();
            return null;
        }
    }

}

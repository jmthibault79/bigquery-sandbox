package org.broadinstitute;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

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

    static GoogleClientSecrets clientSecrets = loadClientSecrets();

    static GoogleClientSecrets loadClientSecrets() {
        try {
            InputStream jsonStream = new FileInputStream(CLIENTSECRETS_LOCATION);
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(new JacksonFactory(), jsonStream);

            return clientSecrets;
        } catch (Exception e) {
            System.out.println("Could not load client_secrets.json");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String args[]) {
        System.out.println("Client Secrets loaded " + clientSecrets);
    }
}

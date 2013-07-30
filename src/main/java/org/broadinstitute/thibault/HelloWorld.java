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
    private static final List SCOPES = Arrays.asList(BigqueryScopes.BIGQUERY);

    public static void main(String args[]) {
        System.out.println("Client Secrets loaded: " + ClientAuth.clientSecrets);
        System.out.println("Client Credential built: " + ClientAuth.credential);
    }
}

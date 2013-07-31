package org.broadinstitute.thibault;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: thibault
 * Date: 7/30/13
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClientAuth {
    private static GoogleClientSecrets clientSecrets = null;
    private static Credential credential = null;
    private static GoogleAuthorizationCodeFlow flow = null;

    private static GoogleClientSecrets getClientSecrets() {
        if (clientSecrets == null)
            clientSecrets = loadClientSecrets();

        return clientSecrets;
    }

    public static Credential getCredential() {
        if (credential == null)
            credential = buildCredential();

        return credential;
    }

    private static GoogleClientSecrets loadClientSecrets() {
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

    private static GoogleAuthorizationCodeFlow getFlow() {
        if (flow == null) {
            flow = new GoogleAuthorizationCodeFlow.Builder(Constants.TRANSPORT, Constants.JSON_FACTORY, getClientSecrets(), Constants.SCOPES)
                    .setAccessType("offline").setApprovalPrompt("force").build();
        }
        return flow;
    }

    private static Credential buildCredential() {
        try {
            String authorizeUrl = new GoogleAuthorizationCodeRequestUrl(
                    getClientSecrets(),
                    Constants.REDIRECT_URI,
                    Constants.SCOPES).setState("").build();

            System.out.println("Paste this URL into a web browser to authorize BigQuery Access:\n" + authorizeUrl);

            System.out.println("... and type the code you received here: ");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String authorizationCode = in.readLine();

            GoogleAuthorizationCodeFlow flow = getFlow();
            GoogleTokenResponse response = flow.newTokenRequest(authorizationCode).setRedirectUri(Constants.REDIRECT_URI).execute();

            Credential c = flow.createAndStoreCredential(response, null);
            System.out.println("Client Credential built.");
            return c;
        } catch (Exception e) {
            System.out.println("Could not build the credential");
            e.printStackTrace();
            return null;
        }
    }

}

package org.broadinstitute.thibault;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: thibault
 * Date: 7/30/13
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceAuth {
    static final Credential credential = buildCredential();

    static Credential buildCredential() {
        try {
            GoogleCredential.Builder credentialBuilder = new GoogleCredential.Builder();
            credentialBuilder.setJsonFactory(Constants.JSON_FACTORY);
            credentialBuilder.setTransport(Constants.TRANSPORT);
            credentialBuilder.setServiceAccountScopes(Constants.SCOPES);
            credentialBuilder.setServiceAccountId(Constants.SERVICE_ACCOUNT_ID);
            credentialBuilder.setServiceAccountPrivateKeyFromP12File(new File(Constants.SERVICE_PRIVATE_KEY_LOCATION));

            return credentialBuilder.build();
        } catch (Exception e) {
            System.out.println("Could not build the credential");
            e.printStackTrace();

            return null;
        }
    }
}

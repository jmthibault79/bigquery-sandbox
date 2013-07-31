package org.broadinstitute.thibault;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.bigquery.BigqueryScopes;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: thibault
 * Date: 7/30/13
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class Constants {

    static final JsonFactory JSON_FACTORY = new JacksonFactory();
    static final NetHttpTransport TRANSPORT = new NetHttpTransport();
    static final List SCOPES = Arrays.asList(BigqueryScopes.BIGQUERY);

    static final String CLIENTSECRETS_LOCATION = "client_secrets.json";
    static final String SERVICE_ACCOUNT_ID = "1008486208127-2o9v0e7b6ge70lmmm3ur4l5f1l4op1lg@developer.gserviceaccount.com";
    static final String SERVICE_PRIVATE_KEY_LOCATION = "6dc91078d724087ecead3522126559dfc965c656-privatekey.p12";
    static final String PROJECT_ID = "1008486208127";
    static final long ASYNCHRONOUS_WAIT_TIME = 5000;
    static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
}

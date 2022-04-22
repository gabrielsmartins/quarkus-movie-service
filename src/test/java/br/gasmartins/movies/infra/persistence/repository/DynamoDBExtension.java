package br.gasmartins.movies.infra.persistence.repository;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DynamoDBExtension implements AfterEachCallback, BeforeEachCallback {

    private DynamoDBProxyServer server;

    public DynamoDBExtension(){
        // This one should be copied during test-compile time. If project's basedir does not contains a folder
        // named 'native-libs' please try '$ mvn clean install' from command line first
        System.setProperty("sqlite4java.library.path", "native-libs");

    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        server.stop();
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        String port = "8000";
        server = ServerRunner.createServerFromCommandLineArgs(new String[]{"-inMemory", "-port", port});
        server.start();
    }
}

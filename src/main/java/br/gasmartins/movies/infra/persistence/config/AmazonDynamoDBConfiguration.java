package br.gasmartins.movies.infra.persistence.config;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;


@ApplicationScoped
public class AmazonDynamoDBConfiguration {

    private final AwsDynamoDBConfigurationProperties properties;

    @Inject
    public AmazonDynamoDBConfiguration(AwsDynamoDBConfigurationProperties properties) {
        this.properties = properties;
    }

    @Produces
    public AmazonDynamoDB dynamoDbClient() {
        var dynamoDbCredentialsProperties = properties.credentials();
        var credentials = new BasicAWSCredentials(dynamoDbCredentialsProperties.accessKey(),dynamoDbCredentialsProperties.secretKey());
        var awsCredentialsProvider = new AWSStaticCredentialsProvider(credentials);
        return  AmazonDynamoDBClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(properties.endpoint(), properties.region()))
                .build();
    }

    @Produces
    public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB){
        return new DynamoDBMapper(amazonDynamoDB);
    }


}

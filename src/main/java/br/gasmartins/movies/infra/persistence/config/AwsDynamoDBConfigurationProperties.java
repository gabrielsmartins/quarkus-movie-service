package br.gasmartins.movies.infra.persistence.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "aws.dynamodb")
public interface AwsDynamoDBConfigurationProperties {

    @WithName("endpoint")
    String endpoint();

    @WithName("region")
    String region();

    @WithName("credentials")
    DynamoDbCredentialsProperties credentials();


    interface DynamoDbCredentialsProperties {
        @WithName("access-key")
        String accessKey();

        @WithName("secret-key")
        String secretKey();
    }


}

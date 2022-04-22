# Quarkus Movie Service

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

1.[Setup Environment](#setup_environment)

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-movies-service-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- AWS CLI ([guide](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-version.html)): Install AWS CLI
- AWS Localstack ([guide](https://github.com/localstack/localstack)): Run AWS Localstack
- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A JAX-RS implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

### <a name="setup_environment">Setup Environment</a>

1. Download [AWS Cli](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-version.html)
2. Configure AWS Cli:
```shell script
$ aws configure
```
- AWS Access Key ID: `movie-service`
- AWS Secret Access Key: `movie-service`
- Default region name: `sa-east-1`
- Default output format: `json`
3. Clone [localstack](https://github.com/localstack/localstack):
```shell script
$ git clone https://github.com/localstack/localstack.git
```
4. Run [localstack](https://github.com/localstack/localstack):
```shell script
docker-compose up -d
```
5. Create tables:
```shell script
$ aws --endpoint http://localhost:4566 dynamodb \
 create-table --table-name Movies \
 --attribute-definition AttributeName=Id,AttributeType=S \
 --key-schema AttributeName=Id,KeyType=HASH \
 --billing-mode PAY_PER_REQUEST
```
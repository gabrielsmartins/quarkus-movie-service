# Quarkus Movie Service

[![Quarkus Movies Service CI](https://github.com/gabrielsmartins/quarkus-movie-service/actions/workflows/quarkus-movies-service-actions.yml/badge.svg?branch=master)](https://github.com/gabrielsmartins/quarkus-movie-service/actions/workflows/quarkus-movies-service-actions.yml)

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

See my article on [Medium](https://gasmartins.medium.com/quarkus-application-using-aws-dynamodb-and-localstack-4aa578528400)

- 1.[Setup Environment](#setup_environment)
  - 1.1.[Configure AWS CLI](#configure_aws_cli)
  - 1.2.[Configure AWS Localstack](#configure_aws_localstack)
  - 1.3.[Create Movie Table](#create_movie_table)
- 2.[Running Application](#running_application)
  - 2.1.[Running the application in dev mode](#run_dev_mode) 
  - 2.2.[Packaging and running the application](#packing_and_running)
  - 2.3.[Creating a native executable](#native_executable)
- 3.[API Reference](#api_reference)
  - 3.1.[Create New Movie](#create_new_movie)
  - 3.2.[Update Movie](#update_movie)
  - 3.3.[Search Movie By ID](#search_movie_by_id)
  - 3.3.[Search Movies By Name](#search_movies_by_name)
  - 3.4.[Search All Movies](#search_all_movies)
  - 3.2.[Delete Movie](#delete_movie)
- 4.[Related Guides](#related_guides)
  - 4.1.[RESTEasy Reactive]("#rest_easy_reactive")

## <a name="setup_environment">Setup Environment</a>

Follow the instructions to setup the environment

### <a name="configure_aws_cli">Configure AWS CLI</a>

1. Download [AWS Cli](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-version.html)
2. Configure AWS Cli:
```shell script
$ aws configure
```
- AWS Access Key ID: `movie-service`
- AWS Secret Access Key: `movie-service`
- Default region name: `sa-east-1`
- Default output format: `json`

### <a name="configure_aws_localstack">Configure AWS Localstack</a>

1. Clone [localstack](https://github.com/localstack/localstack):
```shell script
$ git clone https://github.com/localstack/localstack.git
```
2. Run [localstack](https://github.com/localstack/localstack):
```shell script
docker-compose up -d
```

### <a name="create_movie_table">Create Movie Table</a>

1. Create tables:
```shell script
$ aws --endpoint http://localhost:4566 dynamodb \
 create-table --table-name Movies \
 --attribute-definition AttributeName=Id,AttributeType=S \
 --key-schema AttributeName=Id,KeyType=HASH \
 --billing-mode PAY_PER_REQUEST
```
## <a name="running_application">Running Application</a>

Follow the instructions to run the application

### <a name="run_dev_mode">Running the application in dev mode</a>

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

### <a name="packing_and_running">Packaging and running the application</a>

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

### <a name="native_executable">Creating a native executable</a>

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

## <a name="api_reference">API Reference</a>

This application has the following features:

### <a name="create_new_movie">Create New Movie</a>

Use the following example to create a new movie:

#### *Request*
```shell script
curl --location --request POST 'http://localhost:8080/movies/v1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Rocky Balboa",
    "genre": "ACTION",
    "year": 1976,
    "duration": "01:59",
    "directed_by": "John G. Avildsen",
    "actors": [
        {
            "name": "Sylvester Stallone",
            "role": "Rocky Balboa",
            "dob": "1946-07-06",
            "gender": "M"
        }
    ]
}'
```

#### *Response*
```json
{
  "id": "c63fa101-b2a9-446b-99d7-6911ffa82ee7",
  "name": "Rocky Balboa",
  "genre": "ACTION",
  "year": 1976,
  "directed_by": "John G. Avildsen",
  "duration": "01:59",
  "actors": [
    {
      "name": "Sylvester Stallone",
      "role": "Rocky Balboa",
      "dob": "1946-07-06",
      "gender": "MALE"
    }
  ]
}
```

### <a name="update_movie">Update Movie</a>

Use the following example to update a movie:

#### *Request*
```shell script
curl --location --request PUT 'http://localhost:8080/movies/v1/c63fa101-b2a9-446b-99d7-6911ffa82ee7' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "The Godfather",
    "genre": "DRAMA",
    "year": 1972,
    "duration": "02:55",
    "directed_by": "Francis Ford Coppola",
    "actors": [
        {
            "name": "Marlon Brando",
            "role": "Vito Corleone",
            "dob": "1924-04-03",
            "gender": "M"
        }
    ]
}'
```

#### *Response*
```json
{
  "id": "c63fa101-b2a9-446b-99d7-6911ffa82ee7",
  "name": "The Godfather",
  "genre": "DRAMA",
  "year": 1972,
  "directed_by": "Francis Ford Coppola",
  "duration": "02:55",
  "actors": [
    {
      "name": "Marlon Brando",
      "role": "Vito Corleone",
      "dob": "1924-04-03",
      "gender": "MALE"
    }
  ]
}
```

### <a name="search_movie_by_id">Search Movie By ID</a>

Use the following example to search movie by ID:

#### *Request*
```shell script
curl --location --request GET 'http://localhost:8080/movies/v1/c63fa101-b2a9-446b-99d7-6911ffa82ee7'
```

#### *Response*
```json
{
  "id": "c63fa101-b2a9-446b-99d7-6911ffa82ee7",
  "name": "Rocky Balboa",
  "genre": "ACTION",
  "year": 1976,
  "directed_by": "John G. Avildsen",
  "duration": "01:59",
  "actors": [
    {
      "name": "Sylvester Stallone",
      "role": "Rocky Balboa",
      "dob": "1946-07-06",
      "gender": "MALE"
    }
  ]
}
```
### <a name="search_movies_by_name">Search Movies By Name</a>

Use the following example to search movies by name:

#### *Request*
```shell script
curl --location --request GET 'http://localhost:8080/movies/v1?name=Rocky' 
```

#### *Response*
```json
{
  "data": [
    {
      "id": "622a86dc-3f22-4be3-87d8-619e9cdaa88a",
      "name": "Rocky Balboa",
      "genre": "ACTION",
      "year": 1976,
      "directed_by": "John G. Avildsen",
      "duration": "01:59",
      "actors": [
        {
          "name": "Sylvester Stallone",
          "role": "Rocky Balboa",
          "dob": "1946-07-06",
          "gender": "MALE"
        }
      ]
    }
  ],
  "page_size": 30,
  "page_number": 0,
  "total_pages": 1,
  "total_elements": 1
}
```

### <a name="search_all_movies">Search All Movies</a>

Use the following example to search all movies:

#### *Request*
```shell script
curl --location --request GET 'http://localhost:8080/movies/v1/' 
```

#### *Response*
```json
{
  "data": [
    {
      "id": "622a86dc-3f22-4be3-87d8-619e9cdaa88a",
      "name": "Rocky Balboa",
      "genre": "ACTION",
      "year": 1976,
      "directed_by": "John G. Avildsen",
      "duration": "01:59",
      "actors": [
        {
          "name": "Sylvester Stallone",
          "role": "Rocky Balboa",
          "dob": "1946-07-06",
          "gender": "MALE"
        }
      ]
    },
    {
      "id": "c63fa101-b2a9-446b-99d7-6911ffa82ee7",
      "name": "The Godfather",
      "genre": "DRAMA",
      "year": 1972,
      "directed_by": "Francis Ford Coppola",
      "duration": "02:55",
      "actors": [
        {
          "name": "Marlon Brando",
          "role": "Vito Corleone",
          "dob": "1924-04-03",
          "gender": "MALE"
        }
      ]
    }
  ],
  "page_size": 30,
  "page_number": 0,
  "total_pages": 1,
  "total_elements": 2
}
```

### <a name="delete_movie">Delete Movie</a>

Use the following example to delete a movie:

#### *Request*
```shell script
curl --location --request DELETE 'http://localhost:8080/movies/v1/c63fa101-b2a9-446b-99d7-6911ffa82ee7'
```

## <a name="related_guides">Related Guides</a>

- AWS CLI ([guide](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-version.html)): Install AWS CLI
- AWS Localstack ([guide](https://github.com/localstack/localstack)): Run AWS Localstack
- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A JAX-RS implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.

### <a name="rest_easy_reactive">RESTEasy Reactive</a>

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
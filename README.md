# Statement Processor API

API for validation customer statements

## Getting Started

These instructions will get you the project up and running on your
local machine

### Prerequisites

For building and running the application you need:

- [JDK 12](https://jdk.java.net/12/)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine.
One way is to execute the `main` method in the `com.golinko.statement.processor.Application` class
from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Running the tests

To run the tests you can also run JUnit `com.golinko.statement.processor.ApplicationTests` class from your IDE or just

```shell
mvn clean verify
```

## API Specification

After the application up and running you can visit [Swagger UI](http://localhost:8081/statement-processor/swagger-ui.html)
to get API endpoints specifications.
There you can also try them out by clicking on the `Try it out` button

## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/golinko/statement-processor/blob/master/LICENSE) file.

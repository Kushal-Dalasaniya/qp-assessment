# qp-assessment

# Grocery Store Application

This project is a Grocery Store application built using Spring Boot. It utilizes Swagger for API documentation and auto-generates model classes and API interfaces, following best practices for Spring Boot development.

## Getting Started

To get all API details, refer to the Swagger documentation file:

- **Swagger File:** [grocery-api.yaml](src/main/resources/swagger/grocery-api.yaml)

## Prerequisites

Before running the application, ensure you have the following prerequisites installed:

- **Docker:** Install Docker to containerize and deploy the application.

## Build and Run

1. Run Maven install to generate the JAR file with the latest commands:

    ```bash
    mvn install
    ```

2. Build the Docker image:

    ```bash
    docker build -t grocery-store-img:0.0.2 .
    ```

3. Start the Spring Boot application using Docker Compose:

    ```bash
    docker-compose up --build
    ```

## Spring Boot Best Practices

- The application is developed using Spring Boot, following industry best practices.

- Auto-generated model classes and API interfaces are based on Swagger documentation in the `src/main/resources/swagger/` folder.

- Make sure to run `mvn install` before any Docker commands to ensure the JAR file is up-to-date.

## Author

Kushal Dalasaniya
# Jobify API

`Jobify API` is a `REST API` developed with `Java`, `Spring Boot` and `Maven`, serving as
the backend for the web application `Jobify`, a job management platform with a frontend built in `Vue.js`
and the `Quasar Framework`. This API provides full CRUD operations for managing job listings, including creating,
retrieving, updating and deleting jobs, with structured JSON responses and proper error handling.

## Installation Guide

1. Clone the project: `git clone https://github.com/ionut1993255/jobify-backend-spring-boot.git`

2. Navigate into the project directory: `cd jobify-backend-spring-boot`

3. Run the application: `./mvnw spring-boot:run`

> **Notes:**
> - The command `./mvnw` is recommended for Linux/macOS, but can also work on Windows.
> - Make sure you have configured the database in `application.properties` or use the test profile with `H2`.
> - Also install the detached frontend by cloning the following repository:  
    [https://github.com/ionut1993255/jobify-frontend](https://github.com/ionut1993255/jobify-frontend)

## Key Features

- Creating, reading, updating and deleting jobs
- Structured JSON responses (via `ApiResponse<T>`)
- REST exception handling with `@RestControllerAdvice`

## Important Endpoints

- `POST /jobs` - create new job
- `GET /jobs` - list all jobs
- `GET /jobs/{id}` - get job by ID
- `PUT /jobs/{id}` - update job
- `DELETE /jobs/{id}` - delete job

## Technologies Used

- `Java 24` - main programming language of the application
- `Spring Boot 3.5.0` - framework for developing REST applications
- `Maven` - build system and dependency management
- `Spring Data JPA` - for ORM operations and database interactions
- `ModelMapper` - for automatic mapping between entities and DTOs
- `H2 & MySQL` - databases (in-memory for tests and MySQL for runtime)
- `JUnit 5` - main framework for writing tests (unit and integration)
- `Mockito` - used for unit tests by mocking dependencies
- `Lombok` - to reduce boilerplate code (getters, setters, constructors etc.)

## Project Structure

### `src/main/java/dev/ionut/jobify/`

- `config/` - global configurations like CORS (`CorsConfig`) and `ModelMapper` (`MapperConfig`)
- `controller/` - exposes REST endpoints:
    - `JobController` - manage jobs
- `domain/`
    - `dto/` - Data Transfer Objects (`JobDto`)
        - `response/` - generic API response wrapper (`ApiResponse<T>`)
    - `entity/` - JPA entities for database persistence (`JobEntity`)
- `exception/` - global error handling (`GlobalExceptionHandler`, `ResourceNotFoundException`)
- `mapper/` - conversion between entities and DTOs
    - `Mapper<A, B>` - generic interface
    - `impl/` - job-specific mapper (`JobMapper`)
- `repository/` - contains `JobRepository`, which extends `CrudRepository` for managing `JobEntity` persistence
- `service/`
    - `JobService` - defines the job service layer interface
    - `impl/` - contains the implementation class `JobServiceImpl`

### `src/main/resources/`

- `application.properties` - main config
- `application-dev.properties` - development config

### `src/test/java/dev/ionut/jobify/`

- `controller/` - integration tests for `JobController`
- `repository/` - integration tests for `JobRepository`
- `service/`
    - unit tests with `Mockito` for `JobServiceImpl`
    - integration tests for `JobService`
- `util/` - `TestDataUtil` utility for generating test entities and DTOs

### `src/test/resources/`

- `application-test.properties` - configurations for running tests with `H2`

## Testing

Tests cover:

- `Repository` – integration with the database
- `Service` – unit and integration tests
- `Controller` – integration tests with `MockMvc`

Run tests with: `./mvnw test`

## CORS Configured

CORS is configured globally in `CorsConfig.java` to allow access from the frontend (regardless of origin).

## Conclusion

This REST API represents a solid and scalable backend for the `Jobify` application, built following
best `Spring Boot` practices: clear layered separation, elegant error handling, comprehensive testing and consistent
documentation.
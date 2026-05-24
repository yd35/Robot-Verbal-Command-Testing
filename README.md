# Robot Verbal Command Testing

This repository contains an academic project focused on testing and quality assurance for a robot verbal-command service. The project models service/controller-style logic for interpreting spoken commands, handling language recognition and translation behaviour, and enforcing timeout rules while a robot is listening.

The main portfolio focus is the testing strategy: unit and integration tests exercise command interpretation, language behaviour, error handling, and edge cases. The project uses JUnit 5, Mockito, JaCoCo coverage reporting, PIT mutation testing, Maven, and GitHub Actions CI.

## Features

- Interpret robot movement commands such as forwards, backwards, left, and right.
- Clarify uncertain verbal commands before execution.
- Support language recognition and translation behaviour.
- Handle invalid input and listening timeout scenarios.
- Demonstrate test-driven quality checks with coverage and mutation testing.

## Tech Stack

- Java
- Maven / Maven Wrapper
- JUnit 5
- Mockito
- JaCoCo
- PIT mutation testing
- GitHub Actions

## Commands

Run unit tests:

```bash
./mvnw clean test
```

Generate a JaCoCo coverage report:

```bash
./mvnw clean test jacoco:report
```

Run PIT mutation testing:

```bash
./mvnw clean test pitest:mutationCoverage@run-pitest
```

Run the course code-style checker, if you have local credentials configured:

```bash
./mvnw clean compile exec:java@style
```

On Windows, use `mvnw.cmd` instead of `./mvnw`.

## Local Configuration

`codestyle.config` is a local-only course tooling configuration file. It is ignored by Git and should never be committed or shared publicly.

## License

No license is currently provided because this is coursework based on starter/template material.

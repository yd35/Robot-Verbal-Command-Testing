# Robot Verbal Command Testing

Robot Verbal Command Testing is an academic Java project focused on testing and quality assurance for a robot command service. The project models how a robot listens for spoken instructions, interprets movement commands, handles language-related behaviour, and responds to invalid input or timeout scenarios.

The main focus of this repository is the testing approach. It uses unit testing, mocking, coverage reporting, mutation testing, and CI to check that the command-handling logic behaves reliably across normal and edge-case scenarios.

## Features

- Interprets robot movement commands such as forwards, backwards, left, and right
- Handles unclear or invalid verbal commands
- Supports language recognition and translation-related behaviour
- Enforces timeout rules while the robot is listening
- Tests command logic using unit, integration, coverage, and mutation testing

## Tech Stack

- Java
- Maven / Maven Wrapper
- JUnit 5
- Mockito
- JaCoCo
- PIT mutation testing
- GitHub Actions

## Commands

Run the test suite:

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

On Windows, use `mvnw.cmd` instead of `./mvnw`.

## Local Configuration

`codestyle.config` is a local-only configuration file used for course tooling. It is ignored by Git and should not be committed or shared publicly.

## License

No open-source license is currently provided for this repository.

This project is shared publicly for portfolio and educational viewing purposes only. It includes academic project structure and starter/template material, so please do not copy, redistribute, or reuse the code without permission.

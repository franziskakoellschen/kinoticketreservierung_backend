# Readme

[![Java CI](https://github.com/franziskakoellschen/kinoticketreservierung_backend/actions/workflows/java_ci.yml/badge.svg)](https://github.com/franziskakoellschen/kinoticketreservierung_backend/actions/workflows/java_ci.yml)

## Setup

### Required System Environment Variables

`DATABASE_URL` with value like `postgres://test:password@localhost:5432/test`.
`KINOTICKET_EMAIL` containing a valid email address.
`KINOTICKET_EMAIL_PW` containing the associated password for this email address.

### Install dependencies

`mvn clean install`

## Run unit tests

`mvn verify`

## Run the application

`docker-compose up` \
`mvn spring-boot:run`

## Run the application with dev config

`mvn spring-boot:run -D"spring-boot.run.profiles"=local` \
This allows all tables to be regenerated a application start.

## Useful Plugins

- [Lombok Annotations Support for VS Code](https://marketplace.visualstudio.com/items?itemName=GabrielBB.vscode-lombok)
- [JPA Buddy for IntelliJ](https://plugins.jetbrains.com/plugin/15075-jpa-buddy)

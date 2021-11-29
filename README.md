# Readme
[![Java CI](https://github.com/franziskakoellschen/kinoticketreservierung_backend/actions/workflows/java_ci.yml/badge.svg)](https://github.com/franziskakoellschen/kinoticketreservierung_backend/actions/workflows/java_ci.yml)

### Install dependencies
`mvn clean install`

### Run unit tests
`mvn verify`

### Run the application
`mvn spring-boot:run`

### Run the application with dev config
`mvn spring-boot:run -D"spring-boot.run.profiles"=local`
This allows all tables to be regenerated a application start.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Pipeline Status](https://gitlab.com/stasys/accounts-transfers/badges/refactor/pipeline.svg)](https://gitlab.com/stasys/accounts-transfers/pipelines)

# Accounts and Transfers
## About
This is code review and refactoring effort of [vzurauskas/accounts-transfers](https://github.com/vzurauskas/accounts-transfers) solution.

Codebase before refactoring -> [113d9b4781f6eec0a0e7d0eb5072ee4fbd03e280](https://github.com/stasys-skliutas/accounts-transfers/tree/113d9b4781f6eec0a0e7d0eb5072ee4fbd03e280)

Codebase after refactoring -> [refactor branch](https://github.com/stasys-skliutas/accounts-transfers/tree/master)
### Review
TODO 
## Original app description
Demo object oriented web application: create accounts and transfer money between them via a RESTful API.
## Build and run
### Prerequisites
+ Make sure docker is installed and running
+ Java 8 JDK installed
### Build
To build service executable issue
```shell script 
# In *nix like environments
./mvnw clean package
# In windows
mvnw.cmd clean package
```
This will result in executable named `accountstransfers-1.0-SNAPSHOT-jar-with-dependencies.jar` built in project's `/target` directory.
## Run
To start service, issue the following command
```shell script
docker-compose up
```
The database and app's service will be started and ready to use. Wait for the service to start.

Note: Because currently healthchecks are not used to monitor service's status while starting, the easy route was taken to 
restart service until database is up. Wait until errors are gone and service is running.
## Run acceptance tests 
Acceptance tests reside as separate project inside app's root. After app's service is started, issue the following commands from the project's root
```shell script
cd acceptance
# In *nix like environments
./gradlew clean test
# In windows
gradlew.bat clean test
```

## API definition
1. Open https://editor.swagger.io/
2. Click File -> Import URL
3. Paste https://raw.githubusercontent.com/vzurauskas/accounts-transfers/master/swagger.yaml

### POST /transfers idempotency
N.B. POST /transfers operation is idempotent. An idempotency key (header "x-idempotency-key") is used to guard against the execution of duplicate transfers. The request is treated as idempotent if a request from the same client (header "x-client-id") with the same idempotency key has already been executed.
  
This will start the server on port 8080 and the root URI will be http://localhost:8080

## Technology stack
Takes framework - https://github.com/yegor256/takes  
JOOQ - https://www.jooq.org/  
H2 database (in memory) - http://www.h2database.com/html/main.html  

# MONEY TRANSFER API
Simple implementation of RESTful API for money transfers between accounts.

## Task
Design and implement a RESTful API (including data model and the backing implementation) for money transfers between accounts.

### Explicit requirements:
1. You can use Java, Scala or Kotlin.
1. Keep it simple and to the point (e.g. no need to implement any authentication).
1. Assume the API is invoked by multiple systems and services on behalf of end users.
1. You can use frameworks/libraries if you like (except Spring), but don't forget about requirement #2 – keep it simple and avoid heavy frameworks.
1. The datastore should run in-memory for the sake of this test.
1. The final result should be executable as a standalone program (should not require a pre-installed container/server).
1. Demonstrate with tests that the API works as expected.

## Technology stack
- Java 8
- [Maven](https://maven.apache.org/)
- [Jersey](https://eclipse-ee4j.github.io/jersey/)
- [Jetty](https://www.eclipse.org/jetty/)
- [JUnit 4](https://junit.org/junit5/)

## How to run
- mvn package
- java -jar ".\target\moneytransfer.jar"

## Available Resources
- GET [http://localhost:8080/account](http://localhost:8080/account)
Gets all accounts
- GET [http://localhost:8080/account/1](http://localhost:8080/account/1)
Gets an account info with given id
- POST [http://localhost:8080/account/](http://localhost:8080/account/)
Creates an account with given name and 0 balance
- PUT [http://localhost:8080/account/1](http://localhost:8080/account//1)
Updates the account's name
- DELETE [http://localhost:8080/account/1](http://localhost:8080/account/1)
Deletes the account

- GET [http://localhost:8080/transaction](http://localhost:8080/transaction)
Gets all transactions
- GET [http://localhost:8080/transaction/1](http://localhost:8080/transaction/1)
Gets a transaction with the given id
- GET [http://localhost:8080/transaction/account/1](http://localhost:8080/transaction/account/1)
Gets an account's transaction history ordered by create date descending
- POST [http://localhost:8080/transaction/transfer](http://localhost:8080/transaction/transfer)
Transfers money between two accounts
- POST [http://localhost:8080/transaction/deposit](http://localhost:8080/transaction/deposit)
Deposit to money account
- POST [http://localhost:8080/transaction/withdraw](http://localhost:8080/transaction/withdraw)
Withdraws money from an accounts

## Http status
- 200 OK
- 400 Bad request
- 404 Not found

## Notes
- Didn't have time for proper JUnit tests, but tested using Postman
- The postman test info is included [here](https://www.getpostman.com/collections/ea29655d9b58f9814233)
# Shopping Cart Localization with MariaDB

This is a JavaFX Shopping Cart application that uses a MariaDB database to store localization strings (English, Finnish, Swedish, Japanese, Arabic) and saves shopping cart session data (records and individual items).

## Prerequisites
* Java 21
* Maven
* MariaDB (Running locally on port 3306)

## Database Setup
1. Log into MariaDB: `mariadb -u root -p`
2. Run the SQL script to create the schema (included in the assignment folder or `db_schema.sql`).
3. Update the `DatabaseConnection.java` file with your local MariaDB password.

## How to Run
```bash
mvn clean compile javafx:run

# Bank Application

This bank application has been developed to simulate a real bank. It utilizes the following technologies:

- Java 17 as the main programming language.
- Maven as the dependency management and build tool.
- PostgreSQL as the database for storing customer information, accounts, transactions, etc.
- Thymeleaf as the frontend framework for creating the user interface.

## Running the Application

To run the application, follow these steps:

1. Make sure you have Java 17 and Maven installed on your computer.
2. Clone this repository to your local machine.
3. Navigate to the root directory of the project.
4. Configure the PostgreSQL database connection in the `application.properties` file. You can modify the parameter values such as `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` according to your local setup.
5. Run the command `mvn spring-boot:run` in the root directory of the project to start the application.

After the application is successfully launched, it will be available at `http://localhost:8080` in your web browser.

## Application Features

The bank application enables the following operations:

1. User Registration - New users can register in the system by providing the necessary information.
2. Login - Registered users can log in to their accounts using their valid user ID and password.
3. Money Transfer - Users can transfer funds from one bank account to another by providing the recipient's account number and the transfer amount.
4. Loan Application - Users can apply for a loan by providing required information such as the loan amount and repayment period.
5. Viewing Transaction History - Users can view their transaction history, including details of past transfers, allowing them to track their financial activities and monitor their transaction records.

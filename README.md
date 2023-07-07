Order Management System - Test-Driven Development (TDD) Project
This is a simple Order Management System implemented using Spring Boot and following the principles of Test-Driven Development (TDD). The system allows users to perform basic operations related to order handling, such as creating new orders, updating existing orders, fetching order details, and deleting orders. It serves as an example of a real-world system that businesses can use to manage their order processes.

Key Functionalities
Create a new order: Users can create a new order by providing customer name, order date, shipping address, and total order amount. The order is then saved in the database.
Update an existing order: Users can update the details of an existing order, including customer name, shipping address, and total amount. The updated order is saved in the database.
Fetch order details: Users can retrieve the details of a specific order by providing the order ID. The application will display the complete details of the order.
Delete an order: Users can delete an existing order by providing the order ID. The order is removed from the database.
By implementing these features, the Order Management System provides a basic platform for managing and maintaining business orders. The project showcases the process of developing a Spring Boot application using Test-Driven Development (TDD), where tests are written first and then the application code is implemented to pass those tests. This approach ensures that every line of code serves a purpose and functions as intended.

Instructions
Part 1: Setting Up Your Development Environment
Create a new Spring Boot application with the necessary dependencies, including Web, JPA, and H2.
Configure the application.properties file to enable the H2 console and set up the data source URL.
Part 2: Designing the Order Entity
Create an Order entity class with the following fields:
id (Long, Auto Increment)
customerName (String)
orderDate (LocalDate)
shippingAddress (String)
total (Double)
Part 3: Test-Driven Development
Write failing tests for the OrderRepository to save an Order object.
Implement the code necessary to make the tests pass.
Repeat this process for all CRUD operations (create, read, update, and delete).
Part 4: Building the REST APIs
Write failing tests for a REST controller that will handle HTTP requests for each CRUD operation.
Implement the code necessary to make the tests pass.
Part 5: Implementing Validations
Add validation constraints to the Order entity (e.g., customerName and shippingAddress should not be empty, total should be positive, etc.).
Write failing tests for these validations in the OrderController.
Implement the code necessary to make the tests pass.
Part 6: Error Handling
Write failing tests for scenarios where the application should throw exceptions (e.g., trying to update or delete an order that doesn't exist).
Implement the code necessary to make the tests pass.

Order Management System - Testing Guide
This guide provides instructions on how to test the Order Management System application. Testing is an essential part of the software development process to ensure the correctness, reliability, and robustness of the application. The testing approach for the Order Management System involves unit tests and integration tests.

Unit Tests
Unit tests focus on testing individual components or functions in isolation. They help validate the behavior of individual units of code and ensure their correctness.

Running Unit Tests
To run the unit tests in the Order Management System application, follow these steps:

Open the project in IntelliJ IDEA.
Navigate to the test directory .
Right-click on the package, class, or method containing the unit tests.
Choose "Run" or "Debug" to execute the tests.
Observe the test results in the test runner window.
Ensure that all unit tests pass successfully.
Investigate any failures or errors and make necessary adjustments to the code or tests.

The tests in the Order Management System are designed to test the CRUD (Create, Read, Update, Delete) components of the application. These tests ensure that the application can perform all the necessary operations related to order management correctly.
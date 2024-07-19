# Retail Discounts

# Overview
This module provides functionality to calculate discounts and determine the net payable amount in a retail store application. It includes two main components:

1.DiscountCalculatorServiceImpl: A Spring service that uses DiscountUtils to compute the final amount payable for a given bill.
2.DiscountUtils: A utility class that encapsulates the logic for calculating various discounts based on user type and product categories.

# Features
Calculates discounts based on user type and bill amount.
Handles different user types: Employee, Affiliate, and regular Customer.
Checks loyalty status of customers based on registration date for additional discounts.
Placeholder method for future implementation: grocery check logic.

# Components
1. DiscountCalculatorServiceImpl:
   DiscountCalculatorServiceImpl is a Spring-managed service that interacts with DiscountUtils to compute the net payable amount for a bill.
2. DiscountUtils:
   DiscountUtils contains the core logic for calculating percentage-based and flat discounts. It provides methods to determine discounts based on user type and the total amount of the products.
3. DiscountController:
   DiscountController is a REST controller that provides an API endpoint to calculate the net payable amount for a bill.
4. Models (Bill, User, UserType,Product,ProductCategory)
   Bill: A class representing a bill containing user information and a list of products.
   User: A class representing a user with an ID, name, user type, and the date they became a customer.
   Product: A class representing a product with an ID, name, category, and price. 
   ProductCategory: An enumeration representing product categories.
   UserType: An enumeration representing user types.
5. ExceptionHandling:
   RetailStoreException is a custom exception used to handle application-specific errors.
   RetailStoreExceptionHandler is a global exception handler that manages different types of exceptions and provides appropriate HTTP responses.
6. Exception Handlers
   Handles generic exceptions (Exception) and runtime exceptions (RuntimeException).
   Logs the errors and returns a 500 Internal Server Error status with an appropriate message.

# Setup and Usage
# Prerequisites
Java 8 or higher
Maven
IDE (e.g., IntelliJ IDEA, Eclipse)

# Running the Application

1)Clone the repository:
git clone <repository_url>
cd retail-discount-service

2)Build the project using Maven:

```bash
mvn clean install
```

3)Run the application:

```bash
mvn spring-boot:run
```
4)The application will start at http://localhost:8080. You can access the discount calculation API endpoints from there.

# API Endpoints
POST /api/bill/calculateNetPayableAmount
Calculates net payable amount for a given bill.
Request Body: JSON representing Bill object with total amount and User details.
Response: Net payable amount after applying discounts.


#Example Request
````
POST /api/bill/calculateNetPayableAmount
Content-Type: application/json

{
  "id": 1,
  "user": {
    "id": 1,
    "name": "John Doe",
    "userType": "CUSTOMER",
    "customerSince": "2019-01-01"
  },
  "products": [
    {
      "id": 1,
      "name": "Laptop",
      "category": "OTHER",
      "price": 10000.0
    },
    {
      "id": 2,
      "name": "Phone",
      "category": "OTHER",
      "price": 800.0
    }
  ]
}


````

# Example Response
````
HTTP/1.1 200 OK
Content-Type: application/json
9720.0
````
# Future Improvements
Implement grocery check logic to differentiate groceries from other items.
Enhance security and validation for input data.
Extend functionality with more discount types or promotions.


## Authors
Harshitha
 

# Student-Application

### Overview

- Solution to "College Library Project" : Problem Statement 1
  (Student Details App)
- Submission by team Dockerators (Kalyan Ram, Vidhish T, Vikas K)
- This project behaves as a microservice whose APIs are directly queried by our submission to Problem Statement 3 (Student Lending App)

### Functionality

- The API endpoints allows users to add, update and delete students.
- Each student has the following attributes:
    - Student RollNo (unique for each student)
    - Student Name
    - Student Email
    - Student Phone

### Endpoints

- The following are the API endpoints in our application:
  | Method | Endpoint | Description |
  |--------|----------|--------------|
  |GET |/api/students | Returns all students that have been added to the database so far. |
  |GET| /api/students/rollNo/{rollNo} | Returns the student that corresponds the rollNo in the path variable. Returns a StudentNotFoundException if there is no student that corresponds to the given rollNo. |
  |POST |/api/students | Takes a student object in the body of the request and adds it to the database. Returns a StudentAlreadyExistsException if there is already a student in the DB with the same rollNo (as student rollNo is unique). Returns a NullFieldsException if there is any attribute of the student that is null in the request (all student fields are mandatory). |
  |PUT| /api/students | Takes a student object in the body of the request and updates the student in the DB that corresponds to the rollNo of the student in request body to the student in the request body. Returns a StudentNotFoundException if there is no student in the DB with corresponding student rollNo. Returns a NullFieldsException if there is any attribute of the student that is null in the request (all student fields are mandatory). |
  |DELETE |/api/students/rollNo/{rollNo} | Deletes the student that corresponds the rollNo in the path variable from the DB. Returns a StudentNotFoundException if there is no student that corresponds to the given rollNo. |

### Project Structure

- Follows the Controller-Service-Repository pattern.
    - The controller layer is responsible for management of the REST interface to the business logic. It defines the REST API endpoints and calls the respective service layer functions based on which endpoint is invoked. It uses the @RestController annotation to make the application RESTful.
    - The service layer handles the business logic implementation. There is an interface for the student service and is then extended with a Student Service Implementation. This is in accordance to the SOLID Principles. Logs are made here in the different service functions using the slf4j logger class to denote different successes and failures.
    - The repository layer handles all interaction with the database. Our application uses the JPA library to make database operations easier to code.

### Testing

- The Testing is performed in the following way (using JUnit to automate the tests):
    - Create unit test cases for each method in the repo layer. Verify that the data access operations (CRUD) are performed correctly. These tests use the in memory H2 Database to avoid affecting the actual production database.
    - Create unit test cases for each method in the service layer. Mock the repo layer (using Mockito) to isolate the business logic.
      Test different scenarios and edge cases to validate the business rules.
    - Create integration tests to test the API endpoints provided by the controller (using mockMVC). Verify that the endpoints handle requests and responses correctly. Test different HTTP methods (GET, POST, PUT, DELETE) and verify the corresponding actions.

# Employee Management System - Backend

The Employee Management System (EMS) backend is a RESTful API developed using Spring Boot. It provides CRUD operations for managing employee data, including features to create, read, update, and delete employee records. The API is designed to be easily integrated with a frontend and is CORS-enabled to allow requests from specified domains.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Setup Instructions](#setup-instructions)
- [API Endpoints](#api-endpoints)
- [Environment Variables](#environment-variables)

## Features

- **Create** an employee
- **Retrieve** a list of all employees
- **Retrieve** a single employee by ID
- **Update** an employee's information
- **Delete** an employee

## Tech Stack

- **Java** - Programming language
- **Spring Boot** - Application framework
- **Spring Data JPA** - For ORM and database interactions
- **PostgreSQL** - Database
- **Docker** - Containerization

## Setup Instructions

### Prerequisites

- **Java 17** or later
- **Maven** (to build the project)
- **PostgreSQL** (or a compatible database)
- **Docker** (optional, for containerization)

### Running Locally

1. **Clone the repository**

   ```bash
   git clone https://github.com/yourusername/employee-management-system-backend.git
   cd employee-management-system-backend
   ```

2. **Set up PostgreSQL Database**

   Ensure PostgreSQL is installed and running. Create a new database for the project:

   ```sql
   CREATE DATABASE employee_db;
   ```

3. **Configure Application Properties**

   Update `src/main/resources/application.yml` with your database credentials:

   ```yaml
    url: jdbc:postgresql://${POSTGRES_HOST:localhost}:${POSTGRES_PORT:5432}/${POSTGRES_DB:employee_db}
    username: ${POSTGRES_USER:root}
    password: ${POSTGRES_PASSWORD:password}
   ```

4. **Build the Application**

   ```bash
   mvn clean install
   ```

5. **Run the Application**

   ```bash
   java -jar target/employee-system-backend-0.0.1-SNAPSHOT.jar
   ```

   The server will start on `http://localhost:8080`.

### Docker Setup

1. **Build the Docker Image**

   ```bash
   docker build -t employee-system-backend .
   ```

2. **Run the Docker Container**

   ```bash
   docker run -p 8080:8080 employee-system-backend
   ```

   The application will be available at `http://localhost:8080`.

## API Endpoints

The API follows RESTful conventions with the following endpoints:

| Method | Endpoint                 | Description                        |
|--------|---------------------------|------------------------------------|
| POST   | `/api/v1/employees`       | Create a new employee              |
| GET    | `/api/v1/employees`       | Retrieve all employees             |
| GET    | `/api/v1/employees/{id}`  | Retrieve an employee by ID         |
| PUT    | `/api/v1/employees/{id}`  | Update an employee's details       |
| DELETE | `/api/v1/employees/{id}`  | Delete an employee by ID           |

### Example Request and Response

#### Create an Employee

- **Request**

  ```http
  POST /api/v1/employees
  Content-Type: application/json

  {
    "firstName": "John",
    "lastName": "Doe",
    "emailId": "johndoe@example.com"
  }
  ```

- **Response**

  ```json
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "emailId": "johndoe@example.com"
  }
  ```

## Environment Variables

You can define the following environment variables in an `.env` file for Docker:

```dotenv
      POSTGRES_HOST: 'localhost'
      POSTGRES_PORT: 5432
      POSTGRES_DB: employee_db
      POSTGRES_USER: 'root'
      POSTGRES_PASSWORD: 'password'
```

## CORS Configuration

CORS is enabled for the frontend URLs specified in the `EmployeeController`:

```java
@CrossOrigin(origins = {"http://localhost:5173", "https://employee-management-system-frontend-hazel.vercel.app/"})
```

To add more origins, modify the `origins` attribute in `@CrossOrigin`.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
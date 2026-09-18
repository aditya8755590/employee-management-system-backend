# Understanding This Project

A plain-English, file-by-file walkthrough of the Employee Management System backend.
Read this first if you just cloned the repo and want to know how it works.
(For interview prep, see `docs/INTERVIEW_PREP.md`.)

---

## 1. What It Is

A REST API that lets you Create, Read, Update, and Delete (CRUD) *employee* records.
It exposes JSON endpoints under `/api/v1`, stores employees in PostgreSQL, and is
built with Spring Boot 3.2.5 (Java 17), Spring Web, Spring Data JPA, Lombok, and PostgreSQL.

## 2. Tech Stack

| Concern | Technology |
|---------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.2.5 |
| Web layer | Spring MVC (`spring-boot-starter-web`) |
| Persistence | Spring Data JPA / Hibernate (`spring-boot-starter-data-jpa`) |
| Database | PostgreSQL (`postgresql`, runtime scope) |
| Boilerplate | Lombok (`@Data`, `@RequiredArgsConstructor`) |
| Build tool | Maven (with wrapper `./mvnw`) |
| Container | Docker (`Dockerfile`, `.dockerignore`) |

## 3. How to Run It

### Local
```bash
./mvnw clean package                       # build the jar
java -jar target/employee-system-backend-0.0.1-SNAPSHOT.jar
# needs PostgreSQL on localhost:5432, db `employee_db`, user `root`, password `password`
```

### Docker
```bash
./mvnw package                             # build jar first (image is runtime-only)
docker build -t employee-system-backend .
docker run -p 8080:8080 employee-system-backend
```

All database settings come from env vars with sensible defaults, so nothing hardcodes a connection.

## 4. Project Structure

```
├── pom.xml                          # Maven config: deps, Java 17, Spring Boot parent
├── Dockerfile                       # runtime-only image (copies pre-built jar)
├── .dockerignore                    # keeps target/, .git/, IDE files out of the image context
├── .gitignore                       # target/, IDE, and local env files
├── README.md                        # setup + API docs
├── docs/INTERVIEW_PREP.md           # interview Q&A + architecture notes
└── src/
    ├── main/
    │   ├── java/com/harsh/employee/
    │   │   ├── EmployeeSystemBackendApplication.java  # entry point (@SpringBootApplication)
    │   │   ├── api/ApiPaths.java                       # URL path constants (/api/v1, /employees, ...)
    │   │   ├── controller/EmployeeController.java      # REST endpoints
    │   │   ├── entity/EmployeeEntity.java              # JPA @Entity mapped to the `employee` table
    │   │   ├── mapper/EmployeeMapper.java              # DTO <-> Entity converters
    │   │   ├── model/EmployeeDto.java                  # JSON request/response object
    │   │   ├── repository/EmployeeRepository.java      # Spring Data JpaRepository
    │   │   └── service/
    │   │       ├── EmployeeService.java                # interface (the "contract")
    │   │       └── impl/EmployeeServiceImpl.java       # the actual CRUD logic
    │   └── resources/application.yml                   # DB/env config, JPA settings, port
    └── test/java/com/harsh/employee/
        ├── EmployeeSystemBackendApplicationTests.java  # full-context smoke test (needs DB)
        ├── .../mapper/EmployeeMapperTest.java          # unit tests for the mapper
        ├── .../service/impl/EmployeeServiceImplTest.java  # unit tests w/ mocked repository
        └── .../controller/EmployeeControllerTest.java  # @WebMvcTest slice tests (no DB)
```

## 5. The Flow — How a Request Travels Through the Code

Take `GET /api/v1/employees/1`:

1. **Controller** — `EmployeeController.getEmployee()` (controller/EmployeeController.java:51)
   receives the request, reads `{id}` via `@PathVariable`, and calls the service.
2. **Service** — `EmployeeServiceImpl.getEmployeeById(7L)` (service/impl/EmployeeServiceImpl.java:34)
   calls `employeeRepository.findById(id)` and maps the result.
3. **Repository** — `EmployeeRepository` (repository/EmployeeRepository.java) is a Spring Data
   interface; `findById` is derived from `JpaRepository` — no SQL written by hand.
4. **Database** — Hibernate translates the method name into a SQL `SELECT` against PostgreSQL.
5. **Back up the stack** — the entity comes back, the **Mapper** (`EmployeeMapper.fromEntity`)
   converts it to an `EmployeeDto`, and the controller wraps it in `ResponseEntity`:
   `200 OK` with the JSON body, or `204 No Content` if nothing matched.

### Each CRUD verb, mapped to real code

| Verb | Controller method | Service impl method | DB call |
|------|-------------------|---------------------|---------|
| Create | `createEmployee` → 201 | `createEmployee` | `save(toEntity(dto))` |
| Read all | `getAllEmployees` → 200 / 204 | `getAllEmployees` | `findAll()` |
| Read one | `getEmployee` → 200 / 204 | `getEmployeeById` | `findById(id)` |
| Update | `updateEmployee` → 200 | `updateEmployee` | `findById(id)` then `save(copyInto(entity, dto))` |
| Delete | `deleteEmployee` → 200 / 204 | `deleteEmployeeById` | `findById(id)` then `deleteById(id)` |

## 6. The Key Classes, Explained

### `EmployeeSystemBackendApplication`
The entry point. `@SpringBootApplication` = `@Configuration` + `@EnableAutoConfiguration` +
`@ComponentScan` — it's what turns the app on and wires everything up.

### `EmployeeController`
The HTTP face. Annotated with `@RestController`, `@RequestMapping("/api/v1")` (via `ApiPaths`),
constructor-injected with `EmployeeService` (via Lombok `@RequiredArgsConstructor`), and
`@CrossOrigin`-enabled for the React frontend origins. All responses are `ResponseEntity`,
so status codes are explicit (200 / 201 / 204).

### `EmployeeService` + `EmployeeServiceImpl`
The business layer. The interface is the contract; the impl does the work. Note these
conventions:
- returns `null` when a record doesn't exist (the controller translates that to `204`);
- **create** maps DTO → entity with `toEntity`, saves it, returns the input DTO;
- **update** loads the existing entity and copies onto it with `copyInto`, so the persisted
  record *keeps its id* (reusing `toEntity` would wipe the id).

### `EmployeeRepository`
`interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>` — Spring Data
generates the implementation at runtime. `Entity` type first, id type second.

### `EmployeeMapper`
Static conversion helpers, so mapping exists in exactly one place:
- `fromEntity(entity)` → `EmployeeDto` (built via constructor);
- `toEntity(dto)` → fresh `EmployeeEntity` (via `BeanUtils.copyProperties`);
- `copyInto(entity, dto)` → copies DTO fields onto an existing entity (used by update).

### `EmployeeEntity` vs `EmployeeDto`
- **Entity** = the database row. JPA annotations (`@Entity`, `@Table(name = "employee")`,
  `@Id`, `@GeneratedValue(IDENTITY)`), Lombok `@Data`.
- **Dto** = what the API sends/receives. No JPA annotations.
The split keeps the API contract independent from the persistence model.

### `ApiPaths`
One place for all URL constants — controller annotations and tests both reference them, so a
path change happens in a single line.

## 7. Configuration — `application.yml`

```yaml
spring:
  datasource:
    url:      jdbc:postgresql://${POSTGRES_HOST:localhost}:${POSTGRES_PORT:5432}/${POSTGRES_DB:employee_db}
    username: ${POSTGRES_USER:root}
    password: ${POSTGRES_PASSWORD:password}
  jpa:
    show-sql: true
    hibernate.ddl-auto: update     # Hibernate creates/updates tables from entities
server.port: 8080
```

- `POSTGRES_*` env vars **with defaults** = zero-config on a dev machine, overridable in Docker.
- `ddl-auto: update` = handy for prototyping, risky for production (no migration control).

## 8. Data Model

| Column | Java type | Notes |
|--------|-----------|-------|
| `id` | `Long` | primary key, DB-generated (IDENTITY → BIGSERIAL) |
| `first_name` | `String` | `firstName` |
| `last_name` | `String` | `lastName` |
| `email_id` | `String` | `emailId` |

Spring Boot's naming strategy maps camelCase fields to snake_case columns automatically.

## 9. Testing

- **`EmployeeMapperTest`** — DTO↔Entity conversion, copy-on-update reuses the target instance,
  null and boundary values. Pure JUnit, no Spring.
- **`EmployeeServiceImplTest`** — CRUD behavior with `@Mock EmployeeRepository` (Mockito):
  which repository methods get called, id preservation on update, null when missing.
- **`EmployeeControllerTest`** — `@WebMvcTest` slice + `@MockBean` service: HTTP status codes
  (200/201/204) and JSON bodies, no database needed.
- **`EmployeeSystemBackendApplicationTests`** — `@SpringBootTest` loading the full context;
  needs a running PostgreSQL (fails otherwise — it's the only DB-dependent test).

Run the fast ones: `./mvnw test -Dtest='EmployeeMapperTest,EmployeeServiceImplTest,EmployeeControllerTest'`

## 10. API Quick Reference

| Method | Path | Success | Empty / Missing |
|--------|------|---------|-----------------|
| POST | `/api/v1/employees` | 201 + employee | — |
| GET | `/api/v1/employees` | 200 + array | 204 |
| GET | `/api/v1/employees/{id}` | 200 + employee | 204 |
| PUT | `/api/v1/employees/{id}` | 200 + body | — |
| DELETE | `/api/v1/employees/{id}` | 200 + deleted employee | 204 |

```bash
curl -X POST localhost:8080/api/v1/employees -H 'Content-Type: application/json' \
  -d '{"firstName":"John","lastName":"Doe","emailId":"john@example.com"}'
curl localhost:8080/api/v1/employees
```
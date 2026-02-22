# Java Full Stack Development Interview Guide - Spring & Spring Boot

## Table of Contents
1. [Spring Core Concepts](#spring-core)
2. [REST API](#rest-api)
3. [HTTP Methods](#http-methods)
4. [Dependency Injection](#dependency-injection)
5. [Spring Boot Basics](#spring-boot-basics)
6. [Annotations](#annotations)
7. [Spring Boot Application Flow](#spring-boot-flow)
8. [Code Examples](#code-examples)

---

## <a name="spring-core"></a>1. Spring Core Concepts

### Q: What is Spring?

**Answer:**
Spring is a lightweight, open-source framework for building enterprise Java applications. It provides comprehensive infrastructure support for developing Java applications with features like Dependency Injection, Aspect-Oriented Programming, and transaction management.

**Key Benefits:**
- Reduces boilerplate code
- Promotes loose coupling through DI
- Easy to test
- Modular architecture

**Cross Question:** What are the core modules of Spring?
**Answer:** Spring Core (DI/IoC), Spring AOP, Spring Data Access (JDBC, ORM), Spring Web (MVC, REST), Spring Security, Spring Boot.

---

### Q: What is IoC (Inversion of Control)?

**Answer:**
IoC is a design principle where the control of object creation and management is transferred from the application code to the Spring container. Instead of creating objects using `new`, Spring container creates and manages them.

**Without IoC:**
```java
public class UserService {
    private UserRepository userRepository = new UserRepository(); // tight coupling
}
```

**With IoC:**
```java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository; // Spring manages this
}
```

**Real-world Analogy:** Instead of cooking your own food (creating objects), you order from a restaurant (Spring container) that prepares and serves it.

---

## <a name="dependency-injection"></a>2. Dependency Injection (DI)

### Q: What is Dependency Injection?

**Answer:**
Dependency Injection is a design pattern where dependencies (objects) are injected into a class rather than the class creating them itself. Spring implements IoC through DI.

**Benefits:**
- Loose coupling
- Easier testing (can inject mock objects)
- Better code organization
- Flexibility to change implementations

---

### Q: Types of Dependency Injection

**Answer:**

**1. Constructor Injection (Recommended)**
```java
@Service
public class UserService {
    private final UserRepository userRepository;
    
    @Autowired // Optional in Spring 4.3+
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

**2. Setter Injection**
```java
@Service
public class UserService {
    private UserRepository userRepository;
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

**3. Field Injection**
```java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
}
```

**Cross Question:** Which DI type is best and why?
**Answer:** Constructor Injection is best because:
- Ensures dependencies are not null (required at object creation)
- Supports immutability (final fields)
- Makes testing easier
- Clear visibility of dependencies

---

### Q: If using @Autowired, why use it? What are its disadvantages?

**Answer:**

**Why use @Autowired:**
- Automatic dependency injection
- Reduces boilerplate code
- Spring manages object lifecycle
- Easy to switch implementations

**Disadvantages:**
1. **Tight coupling to Spring framework** - Code becomes Spring-dependent
2. **Hidden dependencies** - With field injection, dependencies aren't visible in constructors
3. **Harder to test** - Field injection requires reflection for testing
4. **Circular dependency risk** - Can create circular dependencies unintentionally
5. **Null pointer exceptions** - If bean not found and required=false

**Best Practice:** Use constructor injection without @Autowired (Spring 4.3+):
```java
@Service
public class UserService {
    private final UserRepository repository;
    
    // No @Autowired needed for single constructor
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

---

### Q: DI Annotations

**Answer:**

**@Component** - Generic stereotype for any Spring-managed component
**@Service** - Business logic layer
**@Repository** - Data access layer (adds exception translation)
**@Controller** - Web controller (MVC)
**@RestController** - REST API controller (@Controller + @ResponseBody)
**@Autowired** - Injects dependencies
**@Qualifier** - Specifies which bean to inject when multiple candidates exist
**@Primary** - Marks a bean as primary choice when multiple candidates exist

**Example with @Qualifier:**
```java
@Service
public class PaymentService {
    @Autowired
    @Qualifier("creditCardPayment")
    private PaymentProcessor processor;
}
```

---

## <a name="rest-api"></a>3. REST API

### Q: Explain REST API - how to use it, execute it, the whole flow structure

**Answer:**

**REST (Representational State Transfer)** is an architectural style for designing networked applications using HTTP protocol.

**Key Principles:**
1. **Stateless** - Each request contains all information needed
2. **Client-Server** - Separation of concerns
3. **Uniform Interface** - Standardized communication
4. **Resource-based** - Everything is a resource (User, Product, etc.)

**Complete Flow Structure:**

```
Client Request → DispatcherServlet → HandlerMapping → Controller 
→ Service Layer → Repository Layer → Database
→ Response back through layers → Client
```

**Detailed Flow:**

1. **Client sends HTTP request** (e.g., GET /api/users/1)
2. **DispatcherServlet** receives the request (Front Controller)
3. **HandlerMapping** finds appropriate controller method
4. **Controller** handles request, calls service layer
5. **Service Layer** contains business logic
6. **Repository Layer** interacts with database
7. **Data flows back** through layers
8. **Controller returns ResponseEntity** with data
9. **Jackson converts** Java object to JSON
10. **HTTP Response** sent to client

**Example REST Controller:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
}
```

**Cross Question:** What is the role of DispatcherServlet?
**Answer:** DispatcherServlet is the front controller in Spring MVC that receives all HTTP requests and delegates them to appropriate handlers (controllers). It's the entry point for all requests.

---

## <a name="http-methods"></a>4. HTTP Methods

### Q: Explain HTTP Methods

**Answer:**

| Method | Purpose | Idempotent | Safe | Request Body |
|--------|---------|------------|------|--------------|
| **GET** | Retrieve resource | Yes | Yes | No |
| **POST** | Create resource | No | No | Yes |
| **PUT** | Update/Replace entire resource | Yes | No | Yes |
| **PATCH** | Partial update | No | No | Yes |
| **DELETE** | Delete resource | Yes | No | Optional |
| **HEAD** | Same as GET but no body | Yes | Yes | No |
| **OPTIONS** | Describe communication options | Yes | Yes | No |

**Detailed Explanations:**

**GET** - Read operation
```java
@GetMapping("/users/{id}")
public ResponseEntity<User> getUser(@PathVariable Long id) {
    return ResponseEntity.ok(userService.findById(id));
}
```

**POST** - Create new resource
```java
@PostMapping("/users")
public ResponseEntity<User> createUser(@RequestBody User user) {
    User created = userService.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
}
```

**PUT** - Update entire resource
```java
@PutMapping("/users/{id}")
public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
    User updated = userService.update(id, user);
    return ResponseEntity.ok(updated);
}
```

**DELETE** - Remove resource
```java
@DeleteMapping("/users/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
}
```

**Cross Question:** What is idempotent?
**Answer:** An operation is idempotent if calling it multiple times produces the same result as calling it once. GET, PUT, DELETE are idempotent. POST is not (creates multiple resources).

**Cross Question:** Difference between PUT and PATCH?
**Answer:** 
- PUT replaces the entire resource (send all fields)
- PATCH updates only specific fields (send only changed fields)

---

## <a name="spring-boot-basics"></a>5. Spring Boot Basics

### Q: What is Spring Boot?

**Answer:**
Spring Boot is an opinionated framework built on top of Spring that simplifies the development of production-ready applications with minimal configuration.

**Key Features:**
- Auto-configuration
- Embedded servers (Tomcat, Jetty)
- Starter dependencies
- Production-ready features (metrics, health checks)
- No XML configuration needed

---

### Q: Why Spring Boot? / Advantages over Spring Framework

**Answer:**

**Spring Framework Challenges:**
- Lots of XML configuration
- Dependency management is complex
- Manual server setup required
- Time-consuming setup

**Spring Boot Solutions:**

| Aspect | Spring | Spring Boot |
|--------|--------|-------------|
| Configuration | XML/Java Config | Auto-configuration |
| Dependencies | Manual management | Starter POMs |
| Server | External (Tomcat/Jetty) | Embedded server |
| Setup Time | Hours/Days | Minutes |
| Boilerplate | More | Minimal |

**Example:**

**Spring Framework** - Configure DataSource:
```xml
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
    <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://localhost:3306/mydb"/>
    <property name="username" value="root"/>
    <property name="password" value="password"/>
</bean>
```

**Spring Boot** - Just add properties:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=password
```

---

### Q: What is Auto-configuration? / What does Auto-configuration do?

**Answer:**
Auto-configuration automatically configures Spring application based on dependencies present in classpath. It follows "convention over configuration" principle.

**How it works:**
1. Spring Boot scans classpath for dependencies
2. Based on dependencies, it auto-configures beans
3. Uses `@Conditional` annotations to apply configurations
4. Can be overridden with custom configuration

**Example:**
```java
// When spring-boot-starter-data-jpa is in classpath:
// - Auto-configures DataSource
// - Auto-configures EntityManagerFactory
// - Auto-configures TransactionManager
// - Auto-configures JPA repositories
```

**Behind the scenes:**
```java
@Configuration
@ConditionalOnClass(DataSource.class)
public class DataSourceAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        // creates datasource
    }
}
```

**Cross Question:** How to disable specific auto-configuration?
**Answer:**
```java
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

---

### Q: How can you read DB.properties as well as application.properties?

**Answer:**

**Method 1: Using @PropertySource**
```java
@Configuration
@PropertySource("classpath:db.properties")
@PropertySource("classpath:application.properties")
public class AppConfig {
    
    @Value("${db.url}")
    private String dbUrl;
    
    @Value("${app.name}")
    private String appName;
}
```

**Method 2: Using @ConfigurationProperties**

**db.properties:**
```properties
db.url=jdbc:mysql://localhost:3306/mydb
db.username=root
db.password=secret
```

**Config class:**
```java
@Configuration
@ConfigurationProperties(prefix = "db")
@PropertySource("classpath:db.properties")
public class DatabaseConfig {
    private String url;
    private String username;
    private String password;
    
    // getters and setters
}
```

**Method 3: Specify in application.properties**
```properties
# application.properties
spring.config.import=classpath:db.properties
```

**Cross Question:** What's the order of property precedence?
**Answer:** (Highest to Lowest)
1. Command line arguments
2. SPRING_APPLICATION_JSON properties
3. @PropertySource annotations
4. application.properties (in classpath)
5. application.yml
6. Default properties

---

### Q: Explain Bean Lifecycle

**Answer:**

**Bean Lifecycle Phases:**

```
1. Bean Instantiation (Constructor called)
   ↓
2. Populate Properties (Dependency Injection)
   ↓
3. setBeanName() - if implements BeanNameAware
   ↓
4. setBeanFactory() - if implements BeanFactoryAware
   ↓
5. setApplicationContext() - if implements ApplicationContextAware
   ↓
6. @PostConstruct or init-method
   ↓
7. Bean Ready for Use
   ↓
8. @PreDestroy or destroy-method
   ↓
9. Bean Destroyed
```

**Example:**
```java
@Component
public class UserService {
    
    @Autowired
    private UserRepository repository;
    
    public UserService() {
        System.out.println("1. Constructor called");
    }
    
    @PostConstruct
    public void init() {
        System.out.println("2. @PostConstruct - Initialization");
        // Initialize resources, load data, etc.
    }
    
    @PreDestroy
    public void cleanup() {
        System.out.println("3. @PreDestroy - Cleanup");
        // Close connections, release resources
    }
}
```

**Alternative using interfaces:**
```java
@Component
public class UserService implements InitializingBean, DisposableBean {
    
    @Override
    public void afterPropertiesSet() throws Exception {
        // Called after properties are set
        System.out.println("Bean initialized");
    }
    
    @Override
    public void destroy() throws Exception {
        // Called before bean destruction
        System.out.println("Bean destroyed");
    }
}
```

---

### Q: Spring Boot Configuration Files

**Answer:**

**1. application.properties**
```properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.jpa.hibernate.ddl-auto=update
logging.level.root=INFO
```

**2. application.yml** (Hierarchical, more readable)
```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: root
    password: secret
  jpa:
    hibernate:
      ddl-auto: update

logging:
  level:
    root: INFO
```

**3. Profile-specific configurations**
- application-dev.properties
- application-prod.properties
- application-test.properties

**Activate profile:**
```properties
spring.profiles.active=dev
```

**4. Bootstrap.properties** (loaded before application.properties)
- Used for Spring Cloud Config

**Cross Question:** Which has higher priority - properties or yml?
**Answer:** application.properties has higher priority than application.yml when both are present.

---

### Q: What are the layers in Spring Boot? Explain all layers

**Answer:**

**4-Tier Architecture:**

```
Presentation Layer (Controller)
        ↓
Business Logic Layer (Service)
        ↓
Persistence Layer (Repository)
        ↓
Database Layer
```

**1. Controller Layer (Presentation)**
- Handles HTTP requests
- Returns HTTP responses
- Input validation
- Maps requests to service methods

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
}
```

**2. Service Layer (Business Logic)**
- Contains business logic
- Transaction management
- Coordinates multiple repositories
- Transforms data between layers

```java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }
}
```

**3. Repository Layer (Data Access)**
- Database operations (CRUD)
- Query execution
- Data mapping

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
}
```

**4. Model/Entity Layer**
- Represents database tables
- Domain objects

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    // getters, setters
}
```

**Additional Layers:**

**5. DTO Layer** (Data Transfer Objects)
```java
public class UserDTO {
    private String name;
    private String email;
    // No sensitive data like password
}
```

**6. Exception Handling Layer**
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
```

**Cross Question:** Why separate layers?
**Answer:**
- **Separation of Concerns** - Each layer has single responsibility
- **Maintainability** - Changes in one layer don't affect others
- **Testability** - Easy to unit test each layer
- **Reusability** - Service layer can be used by multiple controllers
- **Scalability** - Can scale layers independently

---

### Q: Explain the flow of a Spring Boot application

**Answer:**

**Complete Request-Response Flow:**

```
1. Client sends HTTP Request
   ↓
2. Embedded Tomcat Server receives request
   ↓
3. DispatcherServlet (Front Controller) intercepts
   ↓
4. HandlerMapping finds appropriate Controller
   ↓
5. Filters/Interceptors process (Security, Logging, etc.)
   ↓
6. Controller method executes
   ↓
7. @Valid validates request body (if present)
   ↓
8. Controller calls Service Layer
   ↓
9. Service Layer executes Business Logic
   ↓
10. Service calls Repository Layer
   ↓
11. Repository executes Database Query
   ↓
12. Data returns through layers
   ↓
13. Controller returns ResponseEntity
   ↓
14. HttpMessageConverter converts Object to JSON
   ↓
15. DispatcherServlet sends HTTP Response
   ↓
16. Client receives Response
```

**Application Startup Flow:**

```
1. main() method executes
   ↓
2. SpringApplication.run() is called
   ↓
3. Creates ApplicationContext
   ↓
4. Scans for @Component, @Service, @Repository, etc.
   ↓
5. Auto-configuration classes execute
   ↓
6. Beans are created and initialized
   ↓
7. Embedded server (Tomcat) starts
   ↓
8. Application is ready to handle requests
```

**Cross Question:** What happens at application startup?
**Answer:**
```java
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
        // 1. Creates Spring Application Context
        // 2. Scans @ComponentScan packages
        // 3. Executes Auto-configuration
        // 4. Initializes beans
        // 5. Starts embedded server
    }
}
```

---

### Q: What is GlobalExceptionHandling?

**Answer:**
Global Exception Handling provides centralized exception handling across the entire application instead of handling exceptions in each controller.

**Implementation using @RestControllerAdvice:**

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // Handle specific exception
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    // Handle validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "An error occurred: " + ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

**ErrorResponse class:**
```java
public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    
    // constructors, getters, setters
}
```

**Custom Exception:**
```java
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}
```

**Benefits:**
- Centralized exception handling
- Consistent error responses
- Cleaner controller code
- Easy to maintain

---

## <a name="annotations"></a>6. Common Annotations and Their Usage

### Q: Common Spring Boot Annotations

**Answer:**

**Core Annotations:**

**@SpringBootApplication** - Combines three annotations:
```java
@SpringBootApplication
// = @Configuration + @EnableAutoConfiguration + @ComponentScan
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

**Stereotype Annotations:**
- **@Component** - Generic Spring bean
- **@Service** - Business logic layer
- **@Repository** - Data access layer, adds exception translation
- **@Controller** - MVC controller
- **@RestController** - REST API controller (@Controller + @ResponseBody)

**Request Mapping:**
- **@RequestMapping** - Map HTTP requests to methods
- **@GetMapping** - GET requests
- **@PostMapping** - POST requests
- **@PutMapping** - PUT requests
- **@DeleteMapping** - DELETE requests
- **@PatchMapping** - PATCH requests

**Parameter Annotations:**
- **@RequestBody** - Binds HTTP request body to object
- **@PathVariable** - Extracts value from URI path
- **@RequestParam** - Extracts query parameters
- **@RequestHeader** - Extracts HTTP headers

**Example:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    // GET /api/users/123
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }
    
    // GET /api/users?email=test@example.com
    @GetMapping
    public User getUserByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }
    
    // POST /api/users
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }
}
```

**Validation Annotations:**
- **@Valid** - Triggers validation
- **@NotNull** - Field cannot be null
- **@NotEmpty** - Collection/String cannot be empty
- **@NotBlank** - String cannot be blank
- **@Size** - String/Collection size constraints
- **@Email** - Valid email format
- **@Min/@Max** - Numeric constraints

**JPA Annotations:**
- **@Entity** - Marks class as JPA entity
- **@Table** - Specifies table name
- **@Id** - Primary key
- **@GeneratedValue** - Auto-generate ID
- **@Column** - Maps to column
- **@OneToMany, @ManyToOne, @OneToOne, @ManyToMany** - Relationships

**Configuration:**
- **@Configuration** - Configuration class
- **@Bean** - Defines a bean
- **@Value** - Injects property values
- **@ConfigurationProperties** - Binds properties to object

---

### Q: What happens if @Service is used instead of @Repository?

**Answer:**

**Functionally:** The application will work fine. Both create Spring beans.

**Key Differences:**

**@Repository advantages:**
1. **Exception Translation** - Converts database exceptions to Spring's DataAccessException
2. **Semantic clarity** - Clearly indicates data access layer
3. **Better tooling support** - IDEs can provide better code assistance

**Example:**

```java
// Using @Repository (Recommended)
@Repository
public class UserRepositoryImpl implements UserRepository {
    
    @PersistenceContext
    private EntityManager em;
    
    public User findById(Long id) {
        // If SQLException occurs, Spring translates it to DataAccessException
        return em.find(User.class, id);
    }
}
```

```java
// Using @Service (Works but not semantic)
@Service  // This works but loses semantic meaning
public class UserRepositoryImpl implements UserRepository {
    // Same code, but no exception translation
}
```

**Exception Translation Example:**

```java
// With @Repository
try {
    userRepository.save(user);
} catch (DataAccessException e) {
    // Spring's unified exception - easy to handle
}

// With @Service - might get various database-specific exceptions
try {
    userRepository.save(user);
} catch (SQLException | HibernateException | ... e) {
    // Multiple exception types to handle
}
```

**Best Practice:** Use appropriate annotations:
- @Repository for data access
- @Service for business logic
- @Controller/@RestController for web layer
- @Component for generic beans

---

### Q: How to check request body is not empty? / @Valid and why we need it

**Answer:**

**Using @Valid annotation:**

**Step 1: Add dependency**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

**Step 2: Create DTO with validation constraints**
```java
public class UserDTO {
    
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotNull(message = "Age cannot be null")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be less than 100")
    private Integer age;
    
    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
             message = "Password must contain uppercase, lowercase, number and special character")
    private String password;
    
    // getters and setters
}
```

**Step 3: Use @Valid in Controller**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
        // If validation fails, MethodArgumentNotValidException is thrown
        User user = userService.create(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
```

**Step 4: Handle validation errors globally**
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
```

**Why we need @Valid:**
1. **Automatic validation** - No manual null/empty checks
2. **Centralized validation logic** - Rules in one place
3. **Consistent error responses** - Standard format
4. **Cleaner code** - Less boilerplate
5. **Reusable** - Same DTO can be validated in multiple controllers

**Common Validation Annotations:**

| Annotation | Purpose | Example |
|------------|---------|---------|
| @NotNull | Cannot be null | @NotNull Integer age |
| @NotEmpty | Collection/String not empty | @NotEmpty List<String> tags |
| @NotBlank | String not blank (not null, not empty, not whitespace) | @NotBlank String name |
| @Size | Size constraints | @Size(min=2, max=30) String name |
| @Min/@Max | Numeric min/max | @Min(18) Integer age |
| @Email | Valid email | @Email String email |
| @Pattern | Regex pattern | @Pattern(regexp="...") |
| @Past/@Future | Date in past/future | @Past LocalDate birthDate |

**Cross Question:** Difference between @NotNull, @NotEmpty, @NotBlank?

**Answer:**
```java
@NotNull     // "" (empty) ✓    " " (blank) ✓    null ✗
@NotEmpty    // "" (empty) ✗    " " (blank) ✓    null ✗
@NotBlank    // "" (empty) ✗    " " (blank) ✗    null ✗
```

**Custom Validation:**
```java
// Create custom annotation
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface ValidPhoneNumber {
    String message() default "Invalid phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

// Validator implementation
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        return phoneNumber != null && phoneNumber.matches("^\\d{10}$");
    }
}

// Usage
public class UserDTO {
    @ValidPhoneNumber
    private String phoneNumber;
}
```


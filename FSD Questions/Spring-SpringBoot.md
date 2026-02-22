# Java Full Stack Interview Guide
## Spring Framework & Spring Boot - Easy to Understand Solutions

---

## 6. Spring Framework – Core Concepts

### 1. What is dependency injection? How do we achieve it in Spring Boot?

**Simple Explanation:**
Dependency Injection (DI) is like ordering food at a restaurant. You don't go to the kitchen and cook it yourself - the waiter brings it to you. Similarly, you don't create objects yourself; Spring creates and gives them to you.

**How it works:**
- Instead of writing `UserService service = new UserService()`, Spring automatically provides the object
- Your class just declares what it needs, and Spring injects (provides) it

**Ways to achieve DI in Spring Boot:**

```java
// 1. Constructor Injection (RECOMMENDED)
@Service
public class OrderService {
    private final UserService userService;
    
    public OrderService(UserService userService) {
        this.userService = userService;
    }
}

// 2. Field Injection (Not recommended but commonly used)
@Service
public class OrderService {
    @Autowired
    private UserService userService;
}

// 3. Setter Injection
@Service
public class OrderService {
    private UserService userService;
    
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
```

---

### 2. Why use dependency injection instead of creating objects with `new`?

**Real-world analogy:** Think of it like using a charging cable. With DI, you can easily swap between different chargers (implementations) without changing your phone (class).

**Benefits:**

1. **Loose Coupling:** Your code doesn't depend on specific implementations
2. **Easy Testing:** You can easily replace real services with mock/test versions
3. **Flexibility:** Change implementation without modifying code
4. **Spring manages lifecycle:** No need to worry about creating/destroying objects

**Example:**

```java
// BAD - Tight coupling
public class OrderService {
    private EmailService emailService = new EmailService(); // Hard to test or change
}

// GOOD - Loose coupling with DI
@Service
public class OrderService {
    private final EmailService emailService;
    
    public OrderService(EmailService emailService) {
        this.emailService = emailService; // Can inject EmailService or SmsService
    }
}
```

---

### 3. Difference between @Component, @Service, and @Repository

**Simple Answer:** They're all the same technically, but have different meanings for humans reading the code.

| Annotation | Purpose | Layer | Example |
|------------|---------|-------|---------|
| **@Component** | Generic Spring bean | Any | Utility classes, helpers |
| **@Service** | Business logic | Service Layer | OrderService, PaymentService |
| **@Repository** | Database operations | Data Access Layer | UserRepository, OrderRepository |

**Code Example:**

```java
// Generic component
@Component
public class EmailValidator {
    public boolean isValid(String email) {
        return email.contains("@");
    }
}

// Business logic
@Service
public class OrderService {
    public void processOrder(Order order) {
        // Business logic here
    }
}

// Database access
@Repository
public class UserRepository {
    public User findById(Long id) {
        // Database query here
    }
}
```

**Key Difference:** `@Repository` has special exception translation - it converts database errors into Spring's DataAccessException.

---

### 4. What is @Qualifier annotation in Spring Boot?

**Simple Explanation:** When you have multiple children with the same name in a family, you need to specify which one you're calling. `@Qualifier` does that for Spring beans.

**When to use:** When you have multiple implementations of the same interface.

**Example:**

```java
// Two payment services
@Service
@Qualifier("credit")
public class CreditCardPayment implements PaymentService {
    public void pay(double amount) {
        System.out.println("Paying with Credit Card: " + amount);
    }
}

@Service
@Qualifier("debit")
public class DebitCardPayment implements PaymentService {
    public void pay(double amount) {
        System.out.println("Paying with Debit Card: " + amount);
    }
}

// Using @Qualifier to specify which one
@RestController
public class PaymentController {
    
    @Autowired
    @Qualifier("credit")  // Specifies CreditCardPayment
    private PaymentService paymentService;
    
    // OR in constructor
    public PaymentController(@Qualifier("debit") PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

---

### 5. What is @ExceptionHandler and @ControllerAdvice

**Simple Explanation:** 
- `@ExceptionHandler`: Like a safety net for one trapeze artist (one controller)
- `@ControllerAdvice`: Like a safety net for the entire circus (all controllers)

**@ExceptionHandler** - Handles exceptions in a single controller:

```java
@RestController
public class UserController {
    
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id); // May throw exception
    }
    
    // Handles exception only in this controller
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
```

**@ControllerAdvice** - Handles exceptions globally for all controllers:

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    // Handles UserNotFoundException everywhere
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(404, ex.getMessage());
        return ResponseEntity.status(404).body(error);
    }
    
    // Handles all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(500, "Internal Server Error");
        return ResponseEntity.status(500).body(error);
    }
}

class ErrorResponse {
    private int status;
    private String message;
    
    // Constructor, getters, setters
}
```

---

## 7. Spring Boot – Internals & Configuration

### 1. Why use Spring Boot? How is it different from Spring?

**Simple Analogy:** 
- **Spring Framework:** Like buying ingredients and cooking from scratch
- **Spring Boot:** Like getting a meal kit with everything pre-measured and ready

**Spring Boot Benefits:**

1. **Auto-configuration:** Automatically configures your application
2. **Embedded servers:** No need to deploy WAR files
3. **Starter dependencies:** Pre-packaged dependencies
4. **Production-ready:** Built-in monitoring, health checks
5. **Less XML:** Minimal configuration needed

**Comparison:**

```java
// Traditional Spring - Lots of configuration
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
    <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://localhost:3306/mydb"/>
    <property name="username" value="root"/>
    <property name="password" value="password"/>
</bean>

// Spring Boot - Just add properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=password
```

---

### 2. What does @SpringBootApplication do internally?

**Simple Explanation:** It's like a combo meal at a restaurant - you get 3 things in one.

**@SpringBootApplication = 3 annotations combined:**

```java
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}

// Is equivalent to:

@SpringBootConfiguration  // = @Configuration (marks as config class)
@EnableAutoConfiguration  // Enables auto-configuration
@ComponentScan           // Scans for components in this package and sub-packages
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

**What each does:**
1. **@SpringBootConfiguration:** Marks this class as a configuration source
2. **@EnableAutoConfiguration:** Automatically configures beans based on classpath
3. **@ComponentScan:** Finds and registers @Component, @Service, @Repository classes

---

### 3. What is auto-configuration?

**Simple Explanation:** Spring Boot is like a smart assistant that looks at what you have and sets things up automatically.

**How it works:**
- Checks what libraries are in your project (classpath)
- Automatically configures beans based on what it finds
- You can override if needed

**Example:**
```java
// If you have this dependency
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

// Spring Boot automatically configures:
// - DataSource
// - EntityManagerFactory
// - TransactionManager
// You don't need to configure these manually!
```

---

### 4. What benefits does auto-configuration give?

**Benefits:**

1. **Saves time:** No need to write boilerplate configuration
2. **Less errors:** Pre-tested configurations
3. **Convention over configuration:** Smart defaults
4. **Easy to override:** Can customize when needed
5. **Quick startup:** Get running fast

**Example:**
```java
// Without auto-configuration (Traditional Spring)
@Bean
public DataSource dataSource() {
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName("com.mysql.jdbc.Driver");
    ds.setUrl("jdbc:mysql://localhost:3306/mydb");
    ds.setUsername("root");
    ds.setPassword("password");
    return ds;
}

// With auto-configuration (Spring Boot)
// Just add this to application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=password
// DataSource bean is created automatically!
```

---

### 5. Purpose of application.properties or application.yml

**Simple Explanation:** Like a settings file for your application - all configurations in one place.

**Common uses:**
- Database connection details
- Server port
- Logging levels
- Custom application properties

**Examples:**

```properties
# application.properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=secret
logging.level.root=INFO
app.name=MyApplication
```

```yaml
# application.yml (cleaner format)
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: root
    password: secret

logging:
  level:
    root: INFO

app:
  name: MyApplication
```

---

### 6. How do you read values from application.properties?

**Three ways to read properties:**

```java
// Method 1: Using @Value
@Service
public class EmailService {
    @Value("${app.email.from}")
    private String fromEmail;
    
    @Value("${app.email.timeout:5000}")  // Default value: 5000
    private int timeout;
}

// Method 2: Using @ConfigurationProperties (RECOMMENDED for multiple properties)
@Configuration
@ConfigurationProperties(prefix = "app.email")
public class EmailProperties {
    private String from;
    private String host;
    private int port;
    
    // Getters and setters
}

// Method 3: Using Environment
@Service
public class MyService {
    @Autowired
    private Environment env;
    
    public void someMethod() {
        String fromEmail = env.getProperty("app.email.from");
    }
}
```

**application.properties:**
```properties
app.email.from=noreply@example.com
app.email.host=smtp.gmail.com
app.email.port=587
```

---

### 7. What is build.gradle used for?

**Simple Explanation:** build.gradle is like a shopping list and recipe combined - it tells Gradle what to download and how to build your project.

**Purpose:**
- Manage dependencies (libraries)
- Define build process
- Configure plugins
- Set project properties

**Example build.gradle:**

```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.2.0'
    id 'io.spring.dependency-management' version '1.1.0'
}

group = 'com.example'
version = '1.0.0'
sourceCompatibility = '17'

repositories {
    mavenCentral()  // Where to download dependencies from
}

dependencies {
    // Spring Boot starters
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    
    // Database
    runtimeOnly 'com.mysql:mysql-connector-j'
    
    // Testing
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
    useJUnitPlatform()
}
```

---

### 8. What are Spring Boot starters?

**Simple Explanation:** Starters are like combo meals - instead of ordering individual items, you get everything you need in one package.

**Common Starters:**

| Starter | What it includes | Use case |
|---------|------------------|----------|
| spring-boot-starter-web | Spring MVC, Tomcat, Jackson | REST APIs, Web apps |
| spring-boot-starter-data-jpa | JPA, Hibernate | Database access |
| spring-boot-starter-security | Spring Security | Authentication/Authorization |
| spring-boot-starter-test | JUnit, Mockito, AssertJ | Testing |
| spring-boot-starter-validation | Hibernate Validator | Input validation |

**Example:**

```gradle
// Instead of adding all these separately:
// - spring-web
// - spring-webmvc
// - tomcat-embed-core
// - jackson-databind
// - etc. (20+ dependencies)

// Just add one starter:
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
}
```

---

### 9. What is Spring Boot Actuator?

**Simple Explanation:** Actuator is like the dashboard in your car - it shows you what's happening inside your application.

**Main Features:**

1. **Health checks:** Is the app running?
2. **Metrics:** Memory usage, request counts
3. **Application info:** Version, build details
4. **Environment:** Configuration properties
5. **Logs:** Access and modify log levels

**Setup:**

```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
}
```

```properties
# application.properties
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=always
```

**Common Endpoints:**

- `http://localhost:8080/actuator/health` - Application health
- `http://localhost:8080/actuator/info` - Application info
- `http://localhost:8080/actuator/metrics` - Metrics
- `http://localhost:8080/actuator/env` - Environment properties

---

### 10. What are Spring Boot profiles?

**Simple Explanation:** Profiles are like having different outfits for different occasions - casual for home, formal for office.

**Use cases:**
- Different database for dev, test, production
- Different configurations per environment
- Enable/disable features per environment

**Setup:**

```properties
# application.properties (default/common)
app.name=MyApp

# application-dev.properties (development)
spring.datasource.url=jdbc:mysql://localhost:3306/dev_db
logging.level.root=DEBUG

# application-prod.properties (production)
spring.datasource.url=jdbc:mysql://prod-server:3306/prod_db
logging.level.root=WARN
```

**Activate profile:**

```bash
# Method 1: Command line
java -jar myapp.jar --spring.profiles.active=prod

# Method 2: application.properties
spring.profiles.active=dev

# Method 3: Environment variable
export SPRING_PROFILES_ACTIVE=prod
```

**Use in code:**

```java
@Configuration
@Profile("dev")
public class DevConfiguration {
    @Bean
    public DataSource dataSource() {
        // Dev database configuration
    }
}

@Configuration
@Profile("prod")
public class ProdConfiguration {
    @Bean
    public DataSource dataSource() {
        // Production database configuration
    }
}
```

---

### 11. Scheduled task that runs every 10 seconds

**Complete Code:**

```java
// Step 1: Enable scheduling in main application
@SpringBootApplication
@EnableScheduling  // Important: Enable scheduling
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}

// Step 2: Create scheduled task
@Component
public class ScheduledTasks {
    
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private int counter = 0;
    
    // Runs every 10 seconds
    @Scheduled(fixedRate = 10000)  // 10000 milliseconds = 10 seconds
    public void printMessage() {
        counter++;
        logger.info("Scheduled task executed {} times - Current time: {}", 
                    counter, LocalDateTime.now());
        System.out.println("Hello! Task running every 10 seconds - Count: " + counter);
    }
    
    // Alternative: Fixed delay (waits 10 seconds AFTER previous execution completes)
    @Scheduled(fixedDelay = 10000)
    public void printMessageWithDelay() {
        System.out.println("Task with fixed delay - Time: " + LocalDateTime.now());
    }
    
    // Alternative: Cron expression (every 10 seconds)
    @Scheduled(cron = "*/10 * * * * *")
    public void printMessageWithCron() {
        System.out.println("Task with cron expression - Time: " + LocalDateTime.now());
    }
}
```

**Different @Scheduled options:**

```java
// Fixed Rate - starts every 10 seconds (regardless of previous execution)
@Scheduled(fixedRate = 10000)

// Fixed Delay - waits 10 seconds AFTER previous execution finishes
@Scheduled(fixedDelay = 10000)

// Initial Delay - waits 5 seconds before first execution, then every 10 seconds
@Scheduled(initialDelay = 5000, fixedRate = 10000)

// Cron expression - more flexible timing
@Scheduled(cron = "*/10 * * * * *")  // Every 10 seconds
@Scheduled(cron = "0 0 9 * * MON-FRI")  // 9 AM on weekdays
```

**Cron Expression Format:**
```
 ┌───────────── second (0-59)
 │ ┌───────────── minute (0-59)
 │ │ ┌───────────── hour (0-23)
 │ │ │ ┌───────────── day of month (1-31)
 │ │ │ │ ┌───────────── month (1-12 or JAN-DEC)
 │ │ │ │ │ ┌───────────── day of week (0-7 or MON-SUN)
 │ │ │ │ │ │
 * * * * * *
```

---

## Quick Tips for Interview:

1. **Always explain with simple analogies** - interviewers appreciate clarity
2. **Give real-world examples** - shows practical understanding
3. **Mention best practices** - like using constructor injection
4. **Be honest** - if you don't know something, say "I haven't used it much, but I understand it does X"
5. **Ask clarifying questions** - shows you think before answering

**Good luck with your interview! 🚀**
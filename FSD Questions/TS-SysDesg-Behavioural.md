# Interview Preparation Guide
## TypeScript, System Design & Behavioral Questions

---

## Section 1: TypeScript Questions

### Question 1: Explain generics in TypeScript and how they help us

**Simple Explanation:**

Generics are like placeholders that let you write reusable code that works with different data types. Think of them as "type variables" - you define the logic once, and it adapts to whatever type you pass in.

**Real-World Analogy:**

Imagine a box that can hold anything - toys, books, or clothes. You don't need different boxes for each item type; you just need one flexible box design. Generics work the same way!

**Code Example:**

```typescript
// Without Generics - repetitive code
function getNumberArray(item: number): number[] {
  return [item];
}

function getStringArray(item: string): string[] {
  return [item];
}

// With Generics - reusable code
function getArray<T>(item: T): T[] {
  return [item];
}

// Usage
const numbers = getArray<number>(5);        // number[]
const strings = getArray<string>("hello");  // string[]
const booleans = getArray<boolean>(true);   // boolean[]
```

**Benefits:**

1. **Type Safety** - Catches errors at compile time
2. **Code Reusability** - Write once, use with multiple types
3. **Better IntelliSense** - IDE provides better autocomplete
4. **Flexibility** - Works with any type while maintaining type safety

**Cross Questions:**

- **Q: What's the difference between generics and `any` type?**
  - A: `any` loses all type checking. Generics maintain type safety while being flexible. With `any`, you can accidentally mix types and get runtime errors.

- **Q: Can you use multiple generic types?**
  - A: Yes! `function pair<T, U>(first: T, second: U): [T, U] { return [first, second]; }`

- **Q: What are generic constraints?**
  - A: They limit what types can be used. Example: `<T extends string | number>` means T can only be string or number.

---

### Question 2: Create a TypeScript interface for SportsHub and log its details

**Solution:**

```typescript
// Define the interface
interface SportsHub {
  id: number;
  name: string;
  location: string;
  category: string;
}

// Function to log SportsHub details
function logSportsHubDetails(hub: SportsHub): void {
  console.log("=== Sports Hub Details ===");
  console.log(`ID: ${hub.id}`);
  console.log(`Name: ${hub.name}`);
  console.log(`Location: ${hub.location}`);
  console.log(`Category: ${hub.category}`);
  console.log("========================");
}

// Usage Example
const myHub: SportsHub = {
  id: 1,
  name: "Elite Sports Arena",
  location: "Mumbai, Maharashtra",
  category: "Multi-Sport Complex"
};

logSportsHubDetails(myHub);

// Output:
// === Sports Hub Details ===
// ID: 1
// Name: Elite Sports Arena
// Location: Mumbai, Maharashtra
// Category: Multi-Sport Complex
// ========================
```

**Enhanced Version with Validation:**

```typescript
interface SportsHub {
  id: number;
  name: string;
  location: string;
  category: string;
}

function logSportsHubDetails(hub: SportsHub): void {
  // Validation
  if (!hub.id || !hub.name || !hub.location || !hub.category) {
    console.error("Invalid SportsHub: Missing required fields");
    return;
  }

  console.log(`
    ╔════════════════════════════════════╗
    ║      Sports Hub Details            ║
    ╠════════════════════════════════════╣
    ║ ID:       ${hub.id.toString().padEnd(24)} ║
    ║ Name:     ${hub.name.padEnd(24)} ║
    ║ Location: ${hub.location.padEnd(24)} ║
    ║ Category: ${hub.category.padEnd(24)} ║
    ╚════════════════════════════════════╝
  `);
}
```

**Cross Questions:**

- **Q: What's the difference between `interface` and `type` in TypeScript?**
  - A: Both are similar, but interfaces can be extended and merged. Types are better for unions and intersections.

- **Q: How would you make some fields optional?**
  - A: Use the `?` operator: `location?: string;`

- **Q: How to enforce that certain fields cannot be changed?**
  - A: Use `readonly`: `readonly id: number;`

---

## Section 2: System Design & Scenario-Based Questions

### Question 1: Design a Microservice from Scratch with Spring Boot

**Simple Explanation:**

A microservice is a small, independent service that does one thing well. Like having separate shops for vegetables, fruits, and dairy instead of one giant supermarket.

**Step-by-Step Design:**

**1. Define the Service Purpose**
Let's create a "Product Service" for managing sports equipment.

**2. Spring Boot API Structure:**

```java
// 1. Application Entry Point
@SpringBootApplication
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}

// 2. Entity (Database Model)
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String category;
    private Double price;
    private Integer stock;
    
    // Getters, Setters, Constructors
}

// 3. Repository Layer (Database Access)
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByPriceLessThan(Double price);
}

// 4. Service Layer (Business Logic)
@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }
    
    public Product createProduct(Product product) {
        // Business validation
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        return productRepository.save(product);
    }
    
    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());
        return productRepository.save(product);
    }
    
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}

// 5. Controller Layer (API Endpoints)
@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(productService.createProduct(product));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id, 
            @Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

// 6. Exception Handling
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
```

**3. Application Properties (application.yml):**

```yaml
spring:
  application:
    name: product-service
  datasource:
    url: jdbc:postgresql://localhost:5432/productdb
    username: admin
    password: admin123
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8081

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
```

**4. Key Microservice Principles Applied:**

1. **Single Responsibility** - Service only manages products
2. **Independence** - Can be deployed separately
3. **API First** - Clear REST API design
4. **Stateless** - Each request is independent
5. **Observability** - Health endpoints enabled

**Cross Questions:**

- **Q: How do you secure these endpoints?**
  - A: Use Spring Security with JWT tokens, OAuth2, or API keys. Add `@PreAuthorize` annotations.

- **Q: How to handle database migrations?**
  - A: Use Flyway or Liquibase for version-controlled database changes.

- **Q: How do microservices communicate?**
  - A: Synchronous (REST, gRPC) or Asynchronous (Message Queues like Kafka, RabbitMQ).

---

### Question 2: Monitor and Debug a Slow Microservice in Production

**Interview Answer Structure:**

**Step 1: Identify the Problem**
"First, I would use monitoring tools to identify where the bottleneck is."

**Tools & Approach:**

```
1. APM Tools (Application Performance Monitoring)
   - New Relic
   - Datadog
   - Dynatrace
   - AppDynamics

2. Metrics to Check:
   ✓ Response Time (P50, P95, P99 latencies)
   ✓ Error Rate
   ✓ Throughput (requests/second)
   ✓ CPU & Memory Usage
   ✓ Database Query Times

3. Logging & Tracing
   - ELK Stack (Elasticsearch, Logstash, Kibana)
   - Distributed Tracing (Jaeger, Zipkin)
   - Splunk
```

**Step 2: Debug Process**

```java
// Add detailed logging
@Service
public class BookingService {
    
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);
    
    public Booking createBooking(BookingRequest request) {
        long startTime = System.currentTimeMillis();
        
        logger.info("Creating booking for user: {}", request.getUserId());
        
        try {
            // Step 1: Validate request
            long validationStart = System.currentTimeMillis();
            validateRequest(request);
            logger.info("Validation took: {}ms", 
                System.currentTimeMillis() - validationStart);
            
            // Step 2: Check availability
            long availabilityStart = System.currentTimeMillis();
            boolean available = checkAvailability(request);
            logger.info("Availability check took: {}ms", 
                System.currentTimeMillis() - availabilityStart);
            
            // Step 3: Save booking
            long saveStart = System.currentTimeMillis();
            Booking booking = bookingRepository.save(request);
            logger.info("Save operation took: {}ms", 
                System.currentTimeMillis() - saveStart);
            
            logger.info("Total booking creation took: {}ms", 
                System.currentTimeMillis() - startTime);
            
            return booking;
        } catch (Exception e) {
            logger.error("Error creating booking", e);
            throw e;
        }
    }
}
```

**Step 3: Common Issues & Solutions**

| Problem | Solution |
|---------|----------|
| **Slow Database Queries** | Add indexes, optimize queries, use connection pooling |
| **N+1 Query Problem** | Use `@EntityGraph` or JOIN FETCH in JPA |
| **Memory Leaks** | Use profilers (JProfiler, VisualVM), check for unclosed resources |
| **Slow External APIs** | Add timeouts, circuit breakers (Resilience4j), caching |
| **Thread Starvation** | Increase thread pool size, use async processing |

**Step 4: Quick Fixes**

```java
// 1. Add Caching
@Service
public class UserService {
    
    @Cacheable(value = "users", key = "#userId")
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }
}

// 2. Add Circuit Breaker
@Service
public class PaymentService {
    
    @CircuitBreaker(name = "paymentService", fallbackMethod = "fallbackPayment")
    public PaymentResponse processPayment(PaymentRequest request) {
        return externalPaymentAPI.process(request);
    }
    
    private PaymentResponse fallbackPayment(PaymentRequest request, Exception e) {
        // Return cached response or queue for later
        return new PaymentResponse("PENDING");
    }
}

// 3. Async Processing
@Service
public class NotificationService {
    
    @Async
    public void sendNotification(String userId, String message) {
        // This runs in a separate thread
        emailService.send(userId, message);
    }
}
```

**Cross Questions:**

- **Q: How do you prevent cascading failures?**
  - A: Use circuit breakers, bulkheads, rate limiting, and timeouts.

- **Q: What's the difference between monitoring and observability?**
  - A: Monitoring tells you what's wrong. Observability helps you understand why it's wrong.

---

### Question 3: Handle High Memory Usage Causing Service Restarts

**Interview Answer:**

"When a service is consuming excessive memory and restarting, I follow this systematic approach:"

**Immediate Actions (Stop the Bleeding):**

```
1. Scale horizontally - Add more instances
2. Increase memory limits temporarily (if justified)
3. Enable health checks to prevent traffic to unhealthy instances
4. Check recent deployments - rollback if needed
```

**Root Cause Analysis:**

**Step 1: Collect Data**

```bash
# Check memory usage
kubectl top pods

# Get heap dump (for Java)
jmap -dump:live,format=b,file=heap-dump.hprof <PID>

# Check logs for OutOfMemoryError
kubectl logs <pod-name> | grep -i "OutOfMemory"

# Monitor GC activity
jstat -gc <PID> 1000
```

**Step 2: Analyze Common Causes**

```java
// Problem 1: Memory Leaks - Unclosed Resources
// BAD
public class BadService {
    public List<User> getAllUsers() {
        Connection conn = dataSource.getConnection();
        // Connection never closed - MEMORY LEAK!
        return users;
    }
}

// GOOD
public class GoodService {
    public List<User> getAllUsers() {
        try (Connection conn = dataSource.getConnection()) {
            // Auto-closed
            return users;
        }
    }
}

// Problem 2: Large Collections in Memory
// BAD
public class BadReportService {
    public Report generateReport() {
        // Loading 1 million records into memory!
        List<Transaction> allTransactions = transactionRepo.findAll();
        return processReport(allTransactions);
    }
}

// GOOD
public class GoodReportService {
    public Report generateReport() {
        // Process in batches using pagination
        int pageSize = 1000;
        int page = 0;
        
        while (true) {
            Page<Transaction> transactions = 
                transactionRepo.findAll(PageRequest.of(page, pageSize));
            
            processBatch(transactions.getContent());
            
            if (!transactions.hasNext()) break;
            page++;
        }
        return report;
    }
}

// Problem 3: Caching Without Limits
// BAD
@Service
public class BadCacheService {
    private Map<String, Object> cache = new HashMap<>();
    
    public Object get(String key) {
        if (!cache.containsKey(key)) {
            cache.put(key, fetchFromDB(key));
            // Cache grows indefinitely!
        }
        return cache.get(key);
    }
}

// GOOD
@Service
public class GoodCacheService {
    
    @Cacheable(value = "myCache", key = "#key")
    public Object get(String key) {
        return fetchFromDB(key);
    }
}

// application.yml
spring:
  cache:
    caffeine:
      spec: maximumSize=1000,expireAfterWrite=10m
```

**Step 3: Monitoring & Prevention**

```java
// Add Memory Monitoring
@Component
public class MemoryMonitor {
    
    private static final Logger logger = LoggerFactory.getLogger(MemoryMonitor.class);
    
    @Scheduled(fixedRate = 60000) // Every minute
    public void logMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        long maxMemory = runtime.maxMemory();
        
        double usagePercent = (usedMemory * 100.0) / maxMemory;
        
        logger.info("Memory Usage: {}MB / {}MB ({}%)",
            usedMemory / 1024 / 1024,
            maxMemory / 1024 / 1024,
            String.format("%.2f", usagePercent));
        
        if (usagePercent > 80) {
            logger.warn("HIGH MEMORY USAGE DETECTED: {}%", usagePercent);
            // Trigger alert
        }
    }
}
```

**Step 4: Configuration Optimization**

```yaml
# JVM Options
JAVA_OPTS:
  - "-Xms512m"           # Initial heap size
  - "-Xmx2048m"          # Maximum heap size
  - "-XX:+HeapDumpOnOutOfMemoryError"
  - "-XX:HeapDumpPath=/dumps"
  - "-XX:+UseG1GC"       # Use G1 Garbage Collector
  - "-XX:MaxGCPauseMillis=200"

# Kubernetes Resource Limits
resources:
  requests:
    memory: "1Gi"
    cpu: "500m"
  limits:
    memory: "2Gi"
    cpu: "1000m"
```

**Cross Questions:**

- **Q: What's the difference between memory leak and memory pressure?**
  - A: Memory leak is memory that's allocated but never released. Memory pressure is when you need more memory than available, but there's no leak.

- **Q: How do you analyze a heap dump?**
  - A: Use tools like Eclipse MAT (Memory Analyzer Tool) or VisualVM to find memory-consuming objects and references.

---

### Question 4: Design a Sports Booking Platform

**System Design Overview:**

```
┌─────────────────────────────────────────────┐
│           API Gateway (Port 8080)           │
└─────────┬───────────────────────┬───────────┘
          │                       │
    ┌─────▼──────┐         ┌──────▼──────┐
    │   User     │         │   Booking   │
    │  Service   │◄────────┤   Service   │
    │ (Port 8081)│         │ (Port 8082) │
    └─────┬──────┘         └──────┬──────┘
          │                       │
    ┌─────▼──────┐         ┌──────▼──────┐
    │   User DB  │         │ Booking DB  │
    └────────────┘         └─────────────┘
```

**API Design:**

**1. Booking Service API Endpoints:**

```java
// Booking Entity
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userId;
    private Long sportsHubId;
    private String sportType;
    private LocalDateTime bookingDate;
    private String timeSlot;
    private BookingStatus status;
    private Double amount;
    private LocalDateTime createdAt;
}

// Controller
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;
    
    // 1. Create a new booking
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest request) {
        /*
         * Request Body:
         * {
         *   "userId": 123,
         *   "sportsHubId": 456,
         *   "sportType": "Badminton",
         *   "bookingDate": "2025-01-15",
         *   "timeSlot": "10:00-11:00"
         * }
         */
        BookingResponse booking = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }
    
    // 2. Get all bookings for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getUserBookings(
            @PathVariable Long userId,
            @RequestParam(required = false) BookingStatus status) {
        /*
         * GET /api/bookings/user/123
         * GET /api/bookings/user/123?status=CONFIRMED
         */
        List<BookingResponse> bookings = 
            bookingService.getBookingsByUserId(userId, status);
        return ResponseEntity.ok(bookings);
    }
    
    // 3. Get booking by ID
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBooking(
            @PathVariable Long bookingId) {
        BookingResponse booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }
    
    // 4. Cancel a booking
    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Long bookingId) {
        BookingResponse booking = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(booking);
    }
    
    // 5. Get available slots
    @GetMapping("/availability")
    public ResponseEntity<List<TimeSlot>> getAvailableSlots(
            @RequestParam Long sportsHubId,
            @RequestParam String date,
            @RequestParam String sportType) {
        /*
         * GET /api/bookings/availability?
         *     sportsHubId=456&date=2025-01-15&sportType=Badminton
         */
        List<TimeSlot> slots = 
            bookingService.getAvailableSlots(sportsHubId, date, sportType);
        return ResponseEntity.ok(slots);
    }
}

// Request/Response DTOs
public class BookingRequest {
    @NotNull
    private Long userId;
    
    @NotNull
    private Long sportsHubId;
    
    @NotBlank
    private String sportType;
    
    @NotNull
    @Future
    private LocalDateTime bookingDate;
    
    @NotBlank
    private String timeSlot;
    
    // Getters, Setters
}

public class BookingResponse {
    private Long bookingId;
    private UserDTO user;          // User details from User Service
    private SportsHubDTO sportsHub; // Sports hub details
    private String sportType;
    private LocalDateTime bookingDate;
    private String timeSlot;
    private BookingStatus status;
    private Double amount;
    private LocalDateTime createdAt;
    
    // Getters, Setters
}
```

**2. Service Communication (Fetching User Details):**

**Option A: Synchronous REST Call (Using RestTemplate/WebClient)**

```java
@Service
public class BookingService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${user.service.url}")
    private String userServiceUrl;
    
    public BookingResponse createBooking(BookingRequest request) {
        // 1. Create booking
        Booking booking = new Booking();
        booking.setUserId(request.getUserId());
        booking.setSportsHubId(request.getSportsHubId());
        booking.setBookingDate(request.getBookingDate());
        booking.setTimeSlot(request.getTimeSlot());
        booking.setStatus(BookingStatus.CONFIRMED);
        
        booking = bookingRepository.save(booking);
        
        // 2. Fetch user details from User Service
        String userUrl = userServiceUrl + "/api/users/" + request.getUserId();
        UserDTO user = restTemplate.getForObject(userUrl, UserDTO.class);
        
        // 3. Build response with user details
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getId());
        response.setUser(user);
        response.setBookingDate(booking.getBookingDate());
        response.setTimeSlot(booking.getTimeSlot());
        response.setStatus(booking.getStatus());
        
        return response;
    }
    
    public List<BookingResponse> getBookingsByUserId(Long userId, BookingStatus status) {
        // 1. Fetch bookings
        List<Booking> bookings;
        if (status != null) {
            bookings = bookingRepository.findByUserIdAndStatus(userId, status);
        } else {
            bookings = bookingRepository.findByUserId(userId);
        }
        
        // 2. Fetch user details once
        String userUrl = userServiceUrl + "/api/users/" + userId;
        UserDTO user = restTemplate.getForObject(userUrl, UserDTO.class);
        
        // 3. Map to response
        return bookings.stream()
            .map(booking -> mapToResponse(booking, user))
            .collect(Collectors.toList());
    }
}
```

**Option B: Using Feign Client (Cleaner Approach)**

```java
// 1. Define Feign Client Interface
@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserServiceClient {
    
    @GetMapping("/api/users/{userId}")
    UserDTO getUserById(@PathVariable Long userId);
    
    @GetMapping("/api/users/batch")
    List<UserDTO> getUsersByIds(@RequestParam List<Long> userIds);
}

// 2. Use in Service
@Service
public class BookingService {
    
    @Autowired
    private UserServiceClient userServiceClient;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    public BookingResponse createBooking(BookingRequest request) {
        // Create booking
        Booking booking = bookingRepository.save(mapToEntity(request));
        
        // Fetch user details using Feign
        UserDTO user = userServiceClient.getUserById(request.getUserId());
        
        // Build response
        return mapToResponse(booking, user);
    }
}

// 3. Configuration
@Configuration
public class FeignConfig {
    
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
    
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // Add auth headers, correlation IDs, etc.
            template.header("X-Request-ID", UUID.randomUUID().toString());
        };
    }
}
```

**Option C: Event-Driven (Asynchronous)**

```java
// When user is created/updated, publish event
@Service
public class UserService {
    
    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;
    
    public User createUser(UserRequest request) {
        User user = userRepository.save(new User(request));
        
        // Publish event
        UserEvent event = new UserEvent(user.getId(), user.getName(), user.getEmail());
        kafkaTemplate.send("user-events", event);
        
        return user;
    }
}

// Booking Service listens and caches user data
@Service
public class UserCacheService {
    
    @Autowired
    private CacheManager cacheManager;
    
    @KafkaListener(topics = "user-events")
    public void handleUserEvent(UserEvent event) {
        // Cache user details locally
        Cache cache = cacheManager.getCache("users");
        cache.put(event.getUserId(), event);
    }
    
    public UserDTO getUserById(Long userId) {
        Cache cache = cacheManager.getCache("users");
        UserDTO user = cache.get(userId, UserDTO.class);
        
        if (user == null) {
            // Fallback to sync call if not in cache
            user = userServiceClient.getUserById(userId);
            cache.put(userId, user);
        }
        
        return user;
    }
}
```

**3. Complete API Documentation:**

```
Booking Service API Endpoints
==============================

1. Create Booking
   POST /api/bookings
   Body: { userId, sportsHubId, sportType, bookingDate, timeSlot }
   Response: BookingResponse with user details
   Status: 201 Created

2. Get User's Bookings
   GET /api/bookings/user/{userId}
   Query Params: status (optional)
   Response: List<BookingResponse>
   Status: 200 OK

3. Get Booking Details
   GET /api/bookings/{bookingId}
   Response: BookingResponse
   Status: 200 OK

4. Cancel Booking
   PUT /api/bookings/{bookingId}/cancel
   Response: BookingResponse
   Status: 200 OK

5. Check Availability
   GET /api/bookings/availability
   Query Params: sportsHubId, date, sportType
   Response: List<TimeSlot>
   Status: 200 OK

User Service API Endpoints
===========================

1. Get User by ID
   GET /api/users/{userId}
   Response: UserDTO
   Status: 200 OK

2. Get Multiple Users
   GET /api/users/batch?ids=1,2,3
   Response: List<UserDTO>
   Status: 200 OK
```

**Cross Questions:**

- **Q: How do you handle failures when User Service is down?**
  - A: Use circuit breakers (Resilience4j), cache user data locally, implement fallback responses, retry logic with exponential backoff.

- **Q: How do you prevent double booking?**
  - A: Use database locks (`SELECT FOR UPDATE`), implement optimistic locking with version numbers, or use Redis distributed locks.

- **Q: How would you scale this for thousands of concurrent users?**
  - A: Horizontal scaling, load balancers, caching (Redis), database read replicas, async processing for non-critical operations.

---

## Section 3: Behavioral Questions

### Question 1: How do you gather business requirements?

**Interview Answer:**

"I follow a structured approach to ensure I understand requirements completely:"

**Step 1: Initial Discovery**
- Schedule meeting with stakeholders (Product Manager, Business Analyst)
- Prepare questions beforehand based on the feature brief
- Understand the "Why" - What problem are we solving?
- Identify the target users

**Step 2: Detailed Requirements Gathering**

```
Questions I Ask:

1. Business Context
   - What's the business goal?
   - What's the expected impact (revenue, user engagement, etc.)?
   - What's the priority of this feature?
   - Are there any compliance/regulatory requirements?

2. User Stories
   - Who are the end users?
   - What are their pain points?
   - How will they use this feature?
   - What devices/platforms will they use?

3. Functional Requirements
   - What should the feature do?
   - What are the input/output specifications?
   - What validations are needed?
   - What are the edge cases?

4. Non-Functional Requirements
   - Expected load (users, requests per second)?
   - Performance requirements (response time)?
   - Security requirements?
   - Availability requirements (99.9% uptime)?

5. Integration Points
   - Which systems does this integrate with?
   - What APIs do we need to call?
   - What data do we need to share?

6. Success Criteria
   - How do we measure success?
   - What metrics should we track?
   - What are the acceptance criteria?
```

**Step 3: Documentation**

```markdown
## Requirement Document Template

### 1. Overview
- Feature Name: Sports Booking System
- Business Objective: Enable users to book sports facilities online
- Priority: High
- Target Launch: Q1 2025

### 2. User Stories
As a sports enthusiast
I want to book a badminton court online
So that I can secure my preferred time slot without visiting the venue

### 3. Functional Requirements
- User can search available sports facilities
- User can view available time slots
- User can book a time slot
- User receives confirmation email
- User can cancel booking up to 2 hours before

### 4. Acceptance Criteria
✓ Booking confirmation within 2 seconds
✓ Email sent within 1 minute of booking
✓ Payment integrated
✓ Mobile responsive design

### 5. Technical Specifications
- REST API endpoints
- Database schema
- Integration requirements
```

**Step 4: Validation & Refinement**
- Share document with stakeholders for review
- Conduct walkthrough sessions
- Clarify ambiguities
- Get sign-off before development

**Step 5: Continuous Communication**
- Daily standups for updates
- Demo progress regularly
- Gather feedback early and often
- Adjust based on feedback

**Real Example:**

"In my last project, we were building a booking system. Initially, the requirement was 'users should be able to book slots.' Through my questions, I discovered:
- Peak booking times were 6-8 PM
- System needed to handle 500 concurrent users
- Integration with payment gateway was critical
- Cancellation policy needed to prevent last-minute cancellations

This helped us design the right solution from the start."

---

### Question 2: Tell about SDLC

**Simple Explanation:**

SDLC (Software Development Life Cycle) is the process we follow to build software from start to finish. Think of it like building a house - you don't just start constructing; you plan, design, build, test, and then move in.

**SDLC Phases:**

```
1. Planning & Requirement Analysis
   ↓
2. Design
   ↓
3. Implementation (Development)
   ↓
4. Testing
   ↓
5. Deployment
   ↓
6. Maintenance
```

**Detailed Breakdown:**

**Phase 1: Planning & Requirement Analysis**
```
Activities:
- Identify project goals
- Gather requirements from stakeholders
- Feasibility study (technical, financial, operational)
- Define scope
- Create project timeline

Deliverables:
- Requirement Document (BRD/FRD)
- Project Plan
- Resource Allocation

Example:
"We want to build a sports booking platform. We analyze:
- Do we have the team?
- What's the budget?
- Can we complete in 3 months?
- What technology stack to use?"
```

**Phase 2: Design**
```
Activities:
- System Architecture Design
- Database Design
- API Design
- UI/UX Design
- Security Design

Deliverables:
- High-Level Design (HLD)
- Low-Level Design (LLD)
- Database Schema
- API Specifications
- UI Mockups

Example:
HLD: "We'll use microservices architecture with 3 services"
LLD: "User Service will have UserController, UserService, UserRepository"
```

**Phase 3: Implementation (Development)**
```
Activities:
- Write code according to design
- Follow coding standards
- Use version control (Git)
- Code reviews
- Unit testing

Tools:
- IDE (IntelliJ, VS Code)
- Git/GitHub
- Build tools (Maven, Gradle)

Example:
"Sprint 1: Implement User Registration and Login
Sprint 2: Implement Booking Creation
Sprint 3: Implement Payment Integration"
```

**Phase 4: Testing**
```
Testing Types:

1. Unit Testing - Test individual functions
   @Test
   public void testCreateBooking() {
       Booking booking = bookingService.create(request);
       assertNotNull(booking.getId());
   }

2. Integration Testing - Test service interactions
   - Test API endpoints
   - Test database operations

3. System Testing - Test complete system
   - End-to-end workflows
   - Performance testing
   - Security testing

4. UAT (User Acceptance Testing)
   - Stakeholders test
   - Real users test

Tools:
- JUnit, Mockito (Unit tests)
- Postman, RestAssured (API tests)
- Selenium (UI tests)
- JMeter (Performance tests)
```

**Phase 5: Deployment**
```
Activities:
- Deploy to staging environment
- Final testing in staging
- Deploy to production
- Monitor deployment

Deployment Strategies:
1. Blue-Green Deployment
2. Canary Deployment
3. Rolling Deployment

Example:
"We deployed to staging on Friday, ran smoke tests over the weekend,
and deployed to production on Monday morning during low traffic hours."

Tools:
- Jenkins, GitLab CI/CD
- Docker, Kubernetes
- AWS, Azure, GCP
```

**Phase 6: Maintenance**
```
Activities:
- Bug fixes
- Performance optimization
- Security patches
- Feature enhancements
- User support

Types:
- Corrective: Fix bugs
- Adaptive: Update for new OS/browser versions
- Perfective: Improve performance
- Preventive: Refactor code, update dependencies

Example:
"After launch, we monitor logs daily, fix critical bugs within 24 hours,
and release minor updates bi-weekly."
```

**SDLC Models:**

**1. Waterfall Model**
```
Requirements → Design → Development → Testing → Deployment
(Sequential, no going back)

Pros: Simple, well-documented
Cons: Inflexible, late testing
```

**2. Agile Model** (Most Common)
```
Sprint Planning → Development → Testing → Review → Retrospective
(Iterative, 2-week sprints)

Pros: Flexible, continuous feedback
Cons: Requires active stakeholder involvement
```

**3. DevOps**
```
Continuous Integration → Continuous Testing → Continuous Deployment
(Automated, fast releases)

Pros: Fast delivery, high quality
Cons: Requires automation infrastructure
```

**My Experience:**

"In my current role, we follow Agile methodology:
- 2-week sprints
- Daily standups (15 min)
- Sprint planning on Monday
- Demo on Thursday
- Retrospective on Friday

We use Jira for task tracking, Git for version control,
Jenkins for CI/CD, and deploy to AWS using Docker containers."

---

### Question 3: Product Team Asks for Quick Feature Delivery

**Interview Answer:**

"When asked to deliver a feature quickly, I balance speed with quality using this approach:"

**Step 1: Understand the Urgency**
```
Questions to Ask:
1. What's driving the urgency?
   - Customer commitment?
   - Market opportunity?
   - Competitive pressure?

2. What's the minimum viable feature?
   - What's absolutely necessary?
   - What can be deferred?

3. What's the impact of delay?
   - Revenue loss?
   - Customer churn?
   - Competitive disadvantage?
```

**Step 2: Assess Feasibility**
```
Quick Analysis:
✓ Current sprint commitments
✓ Team capacity
✓ Technical complexity
✓ Dependencies on other teams
✓ Testing requirements

Example:
"The feature needs 40 hours. We have 20 hours available this sprint.
We need 2 days for testing. Earliest delivery: Next week."
```

**Step 3: Propose Options**

**Option A: MVP Approach (Recommended)**
```
"Instead of building the complete feature, let's deliver an MVP:

Full Feature (2 weeks):
- Advanced search filters
- Multiple payment methods
- Email + SMS notifications
- Admin dashboard
- Analytics

MVP (1 week):
- Basic search
- One payment method
- Email notifications only
- Manual admin actions
- Basic logging

We deliver MVP now, iterate based on feedback."
```

**Option B: Resource Reallocation**
```
"We can deliver faster if we:
1. Pause lower-priority features
2. Bring in an additional developer
3. Reduce scope of current feature
4. Defer some testing to post-release (with monitoring)

Trade-offs:
✓ Faster delivery
✗ Other features delayed
✗ Higher technical debt
✗ More bugs in production"
```

**Option C: Overtime/Extended Hours**
```
"We can work extra hours short-term:
- Team works 2-3 hours extra for 1 week
- Weekend work if absolutely critical

Considerations:
✗ Team burnout
✗ Not sustainable
✗ Quality may suffer
✓ Shows commitment
✓ Meets urgent deadline"
```

**Step 4: Set Expectations**
```
Clear Communication:

"I can deliver this feature by next Friday if we:
1. Reduce scope to MVP (list specific features)
2. Skip advanced error handling (add monitoring instead)
3. Manual testing only (no automated tests initially)
4. Deploy to 10% users first (canary deployment)

Risks:
- May have some bugs (we'll fix quickly)
- Not all features included
- Need 2 weeks for full feature

I recommend MVP approach because it gives you 80% value
in 20% time, and we can iterate based on real user feedback."
```

**Step 5: Execution Strategy**
```
Fast-Track Process:

Day 1-2: Core Development
- Focus on happy path
- Skip edge cases initially
- Use existing libraries
- Minimize new dependencies

Day 3-4: Integration & Testing
- Integration with existing systems
- Basic testing
- Fix critical bugs only

Day 5: Deployment
- Deploy to staging
- Quick smoke tests
- Deploy to 10% production
- Monitor closely

Post-Launch:
- Fix bugs as they come
- Add missing features
- Write automated tests
- Refactor if needed
```

**Real Example:**

"In my previous role, product needed a discount feature urgently for a marketing campaign in 1 week. Instead of building a complex system, I:

1. Created a simple percentage discount API
2. Added it to checkout flow only
3. Manual admin entry (no UI)
4. Basic validation only
5. Extensive monitoring

Delivered in 5 days. After campaign, spent 2 weeks adding:
- Admin UI
- Multiple discount types
- Advanced validation
- Automated tests

The urgent need was met, and we improved it incrementally."

**Key Principles:**
- Always be honest about timeline
- Propose options, let stakeholders decide
- Document trade-offs clearly
- Never compromise on critical security/data integrity
- Plan for technical debt repayment

---

### Question 4: Learn a New Topic in Two Days for Interview

**Interview Answer:**

"When I need to learn something quickly, I use the 'Focused Learning Sprint' approach:"

**Day 1: Foundation Building (8 hours)**

**Hour 1-2: High-Level Understanding**
```
Activities:
1. Read official documentation introduction
2. Watch one comprehensive YouTube tutorial (45-60 min)
3. Read 2-3 blog posts on "What is [Topic]"
4. Understand: What problem does it solve?

Example (Learning Kafka):
- Watch: "Apache Kafka in 1 hour" tutorial
- Read: Kafka official docs - Introduction
- Understand: Kafka solves distributed messaging
```

**Hour 3-4: Core Concepts**
```
Create a mind map:
- Main components
- Key terminology
- How it works (architecture)
- Common use cases

Example (Kafka Mind Map):
Kafka
├── Producer (sends messages)
├── Consumer (reads messages)
├── Topic (message category)
├── Partition (parallel processing)
├── Broker (Kafka server)
└── Use Cases (event streaming, logging)
```

**Hour 5-6: Hands-On Practice**
```
Build Something Small:
1. Follow "Getting Started" tutorial
2. Create hello-world project
3. Modify it slightly
4. Break it intentionally to understand errors

Example (Kafka):
1. Set up Kafka locally
2. Create a topic
3. Write a producer (send messages)
4. Write a consumer (receive messages)
5. Send 10 messages, verify consumption
```

**Hour 7-8: Deep Dive on Key Areas**
```
Focus on:
- Most commonly asked interview questions
- Best practices
- Common pitfalls
- Real-world scenarios

Resources:
- InterviewBit
- GeeksforGeeks
- LeetCode discussions
- Reddit threads
```

**Day 2: Practice & Mastery (8 hours)**

**Hour 1-3: Interview Questions Practice**
```
Find Top 50 Interview Questions:

1. Theoretical Questions
   Q: What is Kafka?
   Q: Explain Producer-Consumer model
   Q: What are partitions?

2. Practical Questions
   Q: How to handle message ordering?
   Q: How to ensure exactly-once delivery?
   Q: How to handle consumer lag?

Practice answering OUT LOUD:
- Explain as if teaching someone
- Use whiteboard/paper
- Record yourself (check clarity)
```

**Hour 4-5: Build a Real Project**
```
Build something interview-worthy:

Example (Kafka Project):
"Real-time Order Processing System"

Components:
1. Order Service (Producer)
   - Sends order events to Kafka

2. Inventory Service (Consumer)
   - Reads orders, updates inventory

3. Notification Service (Consumer)
   - Sends confirmation emails

This shows: End-to-end understanding
```

**Hour 6-7: System Design Scenarios**
```
Practice explaining how you'd use it:

Scenario 1: "Design a notification system"
Answer: "I'd use Kafka because...
- High throughput
- Decouples services
- Guarantees delivery
- Scales horizontally"

Scenario 2: "Design a logging system"
Scenario 3: "Design an event tracking system"
```

**Hour 8: Mock Interview**
```
Test yourself:
1. Explain topic to a friend (5 min)
2. Draw architecture on whiteboard
3. Answer random questions from list
4. Identify weak areas, review them
```

**My Actual Learning Strategy:**

```
Pomodoro Technique (25 min focus + 5 min break):
- Maximizes retention
- Prevents burnout
- Maintains energy

Note-Taking:
- Use notion/OneNote
- Create cheat sheets
- Highlight key points
- Draw diagrams

Active Learning:
- Don't just read/watch
- Code along
- Explain concepts out loud
- Teach someone else (best retention)
```

**Resources I Use:**

```
Documentation:
✓ Official docs (primary source)

Video Tutorials:
✓ YouTube (Traversy Media, freeCodeCamp)
✓ Udemy (specific courses)

Practice:
✓ GitHub (sample projects)
✓ Medium (case studies)
✓ Stack Overflow (common issues)

Interview Prep:
✓ InterviewBit
✓ GeeksforGeeks
✓ LeetCode
✓ Glassdoor (company-specific)
```

**Day-by-Day Plan (Real Example: Learning Redis)**

**Day 1:**
```
Morning (4 hours):
☐ Watch: Redis Crash Course (1 hour)
☐ Read: Redis official docs - Introduction
☐ Understand: In-memory data store, caching
☐ Learn: Data types (String, List, Set, Hash, Sorted Set)

Afternoon (4 hours):
☐ Install Redis locally
☐ Practice commands in redis-cli
☐ Build: Simple cache implementation in Spring Boot
☐ Test: Add caching to REST API
```

**Day 2:**
```
Morning (4 hours):
☐ Top 30 Redis interview questions
☐ Practice answering without notes
☐ Learn: Persistence, Replication, Clustering

Afternoon (4 hours):
☐ Build: Session management with Redis
☐ Implement: Rate limiting with Redis
☐ Practice: Explain to imaginary interviewer
☐ Create: Cheat sheet with key points
```

**Interview Day Preparation:**
```
1 Hour Before:
✓ Review cheat sheet
✓ Revise architecture diagram
✓ Practice 5 most common questions
✓ Deep breath, confidence!
```

**Tips for Learning Fast:**

```
DO:
✓ Focus on fundamentals first
✓ Get hands dirty with code
✓ Understand "why" not just "how"
✓ Practice explaining concepts
✓ Build something real

DON'T:
✗ Try to learn everything
✗ Just watch videos passively
✗ Skip hands-on practice
✗ Memorize without understanding
✗ Panic about what you don't know
```

**Confidence Builder:**

"Remember: You don't need to know everything. Interviewers want to see:
- Learning ability
- Problem-solving approach
- Honest about what you don't know
- Enthusiasm to learn

It's okay to say: 'I learned this recently, so my understanding is that...' followed by what you know."

---

### Question 5: Questions to Ask Before Writing Code

**Interview Answer:**

"Before I write any code, I ask these questions to ensure I'm building the right thing, the right way:"

**Category 1: Understanding the 'Why'**

```
1. What problem are we solving?
   - What's broken/missing currently?
   - What pain point does this address?

2. Who are the users?
   - End customers? Internal teams? Admins?
   - What's their technical proficiency?

3. What's the business value?
   - How does this impact revenue/growth?
   - What metrics will improve?
   - What's the expected ROI?

4. What happens if we don't build this?
   - Is this urgent or nice-to-have?
   - What's the opportunity cost?

Example:
Instead of just "Build a booking feature," I ask:
"Why do users need to book online? Are offline bookings causing issues?
What percentage of bookings will move online? What's the current manual process?"
```

**Category 2: Functional Requirements**

```
5. What exactly should this feature do?
   - Core functionality
   - User flows/journeys
   - Input and output specifications

6. What are the acceptance criteria?
   - How do we know it's done?
   - What constitutes success?
   - What should be testable?

7. What are the edge cases?
   - What can go wrong?
   - Invalid inputs?
   - Boundary conditions?

8. What are the business rules?
   - Validations
   - Constraints
   - Approval workflows

Example:
For booking feature:
- Can users book multiple slots?
- Can they book for others?
- What's the cancellation policy?
- Is payment immediate or later?
- What if the slot is full?
```

**Category 3: Non-Functional Requirements**

```
9. What's the expected scale?
   - How many users?
   - Requests per second?
   - Data volume?
   - Geographic distribution?

10. What are performance requirements?
    - Response time expectations?
    - Page load time?
    - API latency?

11. What are security requirements?
    - Authentication needed?
    - Authorization levels?
    - Sensitive data handling?
    - Compliance requirements (GDPR, PCI-DSS)?

12. What's the availability requirement?
    - 24/7 uptime?
    - Acceptable downtime?
    - Disaster recovery plan?

Example:
"For the sports booking system:
- Peak: 1000 concurrent users
- Response time: < 2 seconds
- Payment data: PCI-DSS compliant
- Uptime: 99.9% (8 hours downtime/year acceptable)"
```

**Category 4: Technical Constraints**

```
13. What's the existing tech stack?
    - Languages/frameworks used?
    - Database (SQL/NoSQL)?
    - Infrastructure (Cloud/On-premise)?

14. What are the integration points?
    - External APIs to call?
    - Internal services to connect?
    - Third-party systems?

15. What are the technical limitations?
    - Legacy system constraints?
    - Database limitations?
    - Network restrictions?

16. What's the deployment environment?
    - Development/Staging/Production?
    - CI/CD pipeline exists?
    - Rollback strategy?

Example:
"Our stack is Spring Boot with PostgreSQL on AWS. We need to integrate
with existing User Service and Payment Gateway. We deploy using Jenkins
to Kubernetes clusters."
```

**Category 5: Data Questions**

```
17. What data do we need?
    - What data to collect?
    - Data sources?
    - Data format?

18. What data already exists?
    - Can we reuse existing data?
    - Where is it stored?
    - How to access it?

19. What are data retention policies?
    - How long to keep data?
    - Archival strategy?
    - Deletion policies (GDPR)?

20. How should data be structured?
    - Database schema?
    - Relationships?
    - Indexing requirements?

Example:
For booking:
- User data: From User Service
- Sports Hub data: New table needed
- Booking history: Retain 7 years (tax compliance)
- Payment info: Encrypted, PCI-compliant storage
```

**Category 6: User Experience**

```
21. What's the user interface?
    - Web/Mobile/Both?
    - Responsive design needed?
    - Accessibility requirements?

22. What's the user flow?
    - Step-by-step journey?
    - Where do users come from?
    - Where do they go next?

23. What feedback do users need?
    - Success messages?
    - Error handling?
    - Loading states?

Example:
"User journey:
1. Search sports hub
2. Select date & time
3. Review booking details
4. Make payment
5. Receive confirmation (email + SMS)

Each step needs clear progress indication and error messages."
```

**Category 7: Testing & Quality**

```
24. How will this be tested?
    - Unit tests coverage expected?
    - Integration tests needed?
    - Performance tests required?

25. What's the testing environment?
    - Test data available?
    - Mock services needed?
    - Testing tools/frameworks?

26. What's the rollout strategy?
    - Feature flag?
    - Gradual rollout?
    - A/B testing needed?

Example:
"For booking feature:
- 80% unit test coverage
- Integration tests for payment flow
- Load test with 5000 concurrent users
- Deploy to 10% users first (canary)"
```

**Category 8: Maintenance & Support**

```
27. What monitoring is needed?
    - What metrics to track?
    - What alerts to set up?
    - Logging requirements?

28. What's the support plan?
    - On-call rotation?
    - SLA commitments?
    - Escalation process?

29. How will we measure success?
    - KPIs to track?
    - Business metrics?
    - Technical metrics?

Example:
Monitoring:
- Track: Booking success rate, response time, error rate
- Alert: If success rate < 95% or response time > 3 sec
- Log: All payment transactions with correlation IDs
```

**Category 9: Timeline & Resources**

```
30. What's the timeline?
    - Hard deadline or flexible?
    - Why this timeline?
    - Any dependencies?

31. What resources are available?
    - Team size?
    - Skillsets?
    - External dependencies?

32. What's the priority?
    - Must-have vs nice-to-have?
    - Trade-offs acceptable?
    - MVP approach possible?

Example:
"Timeline: 4 weeks (marketing campaign dependent)
Team: 2 backend, 1 frontend, 1 QA
Must-have: Basic booking + payment
Nice-to-have: Advanced filters, analytics
MVP possible: Yes, defer analytics to v2"
```

**Category 10: Future Considerations**

```
33. What's the long-term vision?
    - Future enhancements planned?
    - Scalability needs?
    - Extensibility requirements?

34. What can be deferred?
    - What's not needed immediately?
    - What can be added later?
    - Technical debt acceptable?

35. What documentation is needed?
    - API documentation?
    - User guides?
    - Technical documentation?

Example:
"Version 1: Single sports hub booking
Version 2 (3 months): Multi-hub search
Version 3 (6 months): Membership subscriptions
Design APIs to support future enhancements"
```

**My Question Framework Template:**

```
┌─────────────────────────────────────────────┐
│        BEFORE CODING CHECKLIST              │
├─────────────────────────────────────────────┤
│ ☐ Understand the WHY                        │
│ ☐ Define clear acceptance criteria          │
│ ☐ Know the scale & performance needs        │
│ ☐ Identify all integrations                 │
│ ☐ Clarify data requirements                 │
│ ☐ Understand user flow                      │
│ ☐ Define testing strategy                   │
│ ☐ Plan monitoring & alerts                  │
│ ☐ Confirm timeline & resources              │
│ ☐ Consider future extensibility             │
└─────────────────────────────────────────────┘
```

**Real-World Example:**

"When asked to build a booking feature, here's my actual conversation:

**Me:** 'What problem are we solving?'
**PM:** 'Users call to book, it's manual and error-prone.'

**Me:** 'What's the expected volume?'
**PM:** 'Currently 500 calls/day, expect 1000 online bookings/day.'

**Me:** 'What payment methods?'
**PM:** 'Credit card and wallet.'

**Me:** 'What if payment fails?'
**PM:** 'Hmm, good question. Let's hold the slot for 10 minutes.'

**Me:** 'Do we need admin approval for bookings?'
**PM:** 'No, but we need an admin panel to manage slots.'

**Me:** 'Timeline?'
**PM:** '4 weeks for launch.'

**Me:** 'Can we do MVP in 2 weeks without admin panel, add it later?'
**PM:** 'Yes! That works better.'

By asking these questions, we:
- Reduced scope (MVP first)
- Clarified requirements (payment failure handling)
- Identified missing requirement (admin panel)
- Set realistic timeline
- Avoided building wrong thing"

**Key Principles:**

```
✓ Ask "why" before "how"
✓ Challenge assumptions
✓ Think about edge cases
✓ Consider non-functional requirements
✓ Plan for future
✓ Be curious, not critical
✓ Document answers
✓ Confirm understanding
```

**What NOT to Do:**

```
✗ Start coding immediately
✗ Assume you understand everything
✗ Skip non-functional requirements
✗ Ignore edge cases
✗ Forget about monitoring
✗ Not document requirements
✗ Be afraid to ask "stupid" questions
```

**Final Tip:**

"The best developers spend more time understanding the problem than writing code. It's better to ask 100 questions upfront than to rebuild the feature 3 times because requirements were unclear.

Remember: **Code is cheap. Time is expensive. Misunderstanding is most expensive.**"

---

## Interview Tips Summary

### For TypeScript Questions:
- Start with simple explanation
- Give practical code examples
- Explain real-world benefits
- Be ready for follow-up questions on edge cases

### For System Design Questions:
- Think out loud
- Start with high-level design
- Drill down into details
- Consider scalability from the start
- Discuss trade-offs openly
- Use diagrams when possible

### For Behavioral Questions:
- Use STAR method (Situation, Task, Action, Result)
- Give specific examples from experience
- Be honest about challenges
- Show learning from mistakes
- Demonstrate problem-solving approach
- Focus on outcomes and impact

### General Interview Tips:
1. **Clarify before answering** - Ask questions if anything is unclear
2. **Think aloud** - Let interviewer follow your thought process
3. **Start simple, then expand** - Basic solution first, then optimize
4. **Admit what you don't know** - Better than bluffing
5. **Show enthusiasm** - Passion for technology matters
6. **Ask questions** - Show genuine interest in role/company
7. **Be concise** - Respect interviewer's time
8. **Use examples** - Real experiences resonate better

### Common Mistakes to Avoid:
❌ Jumping to code without understanding requirements
❌ Over-engineering simple problems
❌ Not considering edge cases
❌ Ignoring non-functional requirements
❌ Not asking clarifying questions
❌ Speaking in very technical jargon without explanation
❌ Not admitting when you don't know something
❌ Negative talk about previous employers

### Questions to Ask Interviewer:
1. What does success look like in this role?
2. What are the biggest challenges the team is facing?
3. What's the tech stack and why was it chosen?
4. How does the team handle production incidents?
5. What's the code review process?
6. What's the deployment frequency?
7. How is work-life balance in the team?
8. What are growth opportunities?

---

## Additional Resources for Preparation

### Online Platforms:
- **LeetCode** - Coding practice
- **System Design Primer** (GitHub) - System design concepts
- **GeeksforGeeks** - Interview questions
- **InterviewBit** - Comprehensive prep
- **Pramp** - Mock interviews

### Books:
- "Designing Data-Intensive Applications" by Martin Kleppmann
- "System Design Interview" by Alex Xu
- "Cracking the Coding Interview" by Gayle Laakmann McDowell

### Practice:
- Build side projects
- Contribute to open source
- Write technical blogs
- Participate in coding challenges
- Mock interviews with peers

---

## Good Luck with Your Interview! 🚀

Remember: **Preparation** + **Practice** + **Confidence** = Success!

You've got this! 💪
# Spring Boot REST API Interview Guide

## 1. What is @RestController?

### Simple Explanation
`@RestController` is a special annotation in Spring Boot that combines two things:
- `@Controller` - marks the class as a controller
- `@ResponseBody` - automatically converts returned objects to JSON/XML

**In simple terms**: It's a shortcut annotation that tells Spring "this class handles web requests and returns data directly (not HTML pages)."

### Example
```java
@RestController
@RequestMapping("/api")
public class ProductController {
    
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World"; // Returns plain text
    }
    
    @GetMapping("/product")
    public Product getProduct() {
        return new Product(1, "Laptop", 50000);
        // Automatically converted to JSON
    }
}
```

### Cross Questions You Might Face
- **Q: What does @ResponseBody do?**
  - A: It converts the return value to JSON/XML format automatically and writes it to the HTTP response body.

- **Q: Can we use @RestController for returning HTML pages?**
  - A: Not ideal. Use @Controller for HTML pages. @RestController is for REST APIs that return data.

---

## 2. Difference between @RestController and @Controller

### Simple Explanation

| Feature | @Controller | @RestController |
|---------|------------|-----------------|
| **Purpose** | Returns HTML views (web pages) | Returns data (JSON/XML) |
| **@ResponseBody** | Need to add manually on methods | Already included |
| **Use Case** | Traditional web applications | REST APIs |
| **Return Type** | View name (String) | Object/Data |

### Code Comparison

**@Controller Example:**
```java
@Controller
public class WebController {
    
    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("message", "Welcome");
        return "home"; // Returns home.html view
    }
    
    @GetMapping("/data")
    @ResponseBody // Need to add this!
    public Product getData() {
        return new Product(1, "Phone", 20000);
    }
}
```

**@RestController Example:**
```java
@RestController
public class ApiController {
    
    @GetMapping("/api/data")
    public Product getData() {
        return new Product(1, "Phone", 20000);
        // Automatically returns JSON, no @ResponseBody needed
    }
}
```

### Cross Questions
- **Q: Can we mix @Controller and @RestController in the same application?**
  - A: Yes! Use @Controller for web pages and @RestController for APIs.

- **Q: What happens if we use @Controller without @ResponseBody for API?**
  - A: Spring will look for a view (HTML file) with that name and throw an error if not found.

---

## 3. Build REST APIs to Add and Get a Product

### Complete Implementation

#### Step 1: Product Model
```java
public class Product {
    private Long id;
    private String name;
    private Double price;
    private String category;
    
    // Constructors
    public Product() {}
    
    public Product(Long id, String name, Double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
```

#### Step 2: Product Controller
```java
@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    // In-memory storage (for demo)
    private List<Product> products = new ArrayList<>();
    private Long idCounter = 1L;
    
    // 1. Add a new Product (POST)
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        product.setId(idCounter++);
        products.add(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
    
    // 2. Get Product by ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = products.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElse(null);
        
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Bonus: Get all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(products);
    }
}
```

### How to Test

**Add a Product:**
```bash
POST http://localhost:8080/api/products
Content-Type: application/json

{
    "name": "Laptop",
    "price": 50000.00,
    "category": "Electronics"
}
```

**Get Product by ID:**
```bash
GET http://localhost:8080/api/products/1
```

### Cross Questions

- **Q: What is @RequestBody?**
  - A: It binds the HTTP request body (JSON) to a Java object automatically.

- **Q: What is @PathVariable?**
  - A: It extracts values from the URL path. Example: `/products/{id}` - id is a path variable.

- **Q: What is ResponseEntity?**
  - A: A wrapper that gives you control over HTTP response (status code, headers, body).

- **Q: Difference between @PostMapping and @GetMapping?**
  - A: @PostMapping is for creating data (HTTP POST), @GetMapping is for retrieving data (HTTP GET).

- **Q: What if the product ID doesn't exist?**
  - A: Return 404 Not Found status using `ResponseEntity.notFound().build()`

---

## 4. Fetch User by ID using RestTemplate

### Simple Explanation
**RestTemplate** is a Spring class used to make HTTP calls to other REST APIs (as a client).

### Complete Implementation

#### Step 1: Configuration
```java
@Configuration
public class AppConfig {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

#### Step 2: User Model
```java
public class User {
    private Long id;
    private String name;
    private String email;
    private String phone;
    
    // Constructors, Getters, Setters
    public User() {}
    
    public User(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
```

#### Step 3: Service Layer
```java
@Service
public class UserService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    private static final String USER_API_URL = "https://jsonplaceholder.typicode.com/users";
    
    public User getUserById(Long id) {
        String url = USER_API_URL + "/" + id;
        
        try {
            User user = restTemplate.getForObject(url, User.class);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
}
```

#### Step 4: Controller
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
```

### How to Test
```bash
GET http://localhost:8080/api/users/1
```

### Cross Questions

- **Q: What is RestTemplate used for?**
  - A: To call external REST APIs from your Spring Boot application (acts as HTTP client).

- **Q: What are the common RestTemplate methods?**
  - A: 
    - `getForObject()` - GET request, returns object
    - `getForEntity()` - GET request, returns ResponseEntity
    - `postForObject()` - POST request
    - `exchange()` - Generic method for any HTTP method

- **Q: What's the difference between getForObject and getForEntity?**
  - A: 
    - `getForObject()` returns only the body
    - `getForEntity()` returns ResponseEntity (body + status + headers)

- **Q: What if the external API is down?**
  - A: RestTemplate will throw an exception. Use try-catch and implement error handling.

- **Q: Is RestTemplate still recommended?**
  - A: RestTemplate is in maintenance mode. Spring recommends WebClient for new projects, but RestTemplate is still widely used.

---

## 5. Design REST API for Order Service

### System Design

#### Requirements Analysis
- Backend service (microservices architecture)
- CRUD operations for orders
- Search and filter capabilities
- Status management
- Error handling

### Complete Implementation

#### Step 1: Order Model
```java
@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    
    private Long customerId;
    private String customerName;
    
    @ElementCollection
    private List<OrderItem> items;
    
    private Double totalAmount;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    
    private LocalDateTime orderDate;
    private String deliveryAddress;
    
    // Constructors, Getters, Setters
}

public class OrderItem {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
    
    // Constructors, Getters, Setters
}

public enum OrderStatus {
    PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
}
```

#### Step 2: Repository
```java
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByStatus(OrderStatus status);
}
```

#### Step 3: Service Layer
```java
@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    // Create Order
    public Order createOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }
    
    // Get Order by ID
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }
    
    // Get all orders by customer
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
    
    // Update Order Status
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }
    
    // Cancel Order
    public Order cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new InvalidOperationException("Cannot cancel delivered order");
        }
        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
    
    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
```

#### Step 4: REST Controller
```java
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    // 1. Create Order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }
    
    // 2. Get Order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }
    
    // 3. Get Orders by Customer ID
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }
    
    // 4. Get All Orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    
    // 5. Update Order Status
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }
    
    // 6. Cancel Order
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId) {
        Order cancelledOrder = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(cancelledOrder);
    }
}
```

#### Step 5: Exception Handling
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOperation(InvalidOperationException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    
    // Constructor, Getters, Setters
}
```

### API Endpoints Summary

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/orders` | Create new order |
| GET | `/api/orders/{orderId}` | Get order by ID |
| GET | `/api/orders` | Get all orders |
| GET | `/api/orders/customer/{customerId}` | Get orders by customer |
| PATCH | `/api/orders/{orderId}/status` | Update order status |
| PATCH | `/api/orders/{orderId}/cancel` | Cancel order |

### Cross Questions

- **Q: Why use PATCH instead of PUT for status update?**
  - A: PATCH is for partial updates (only status), PUT is for complete replacement of the resource.

- **Q: What is @RestControllerAdvice?**
  - A: Global exception handler for all controllers. Handles exceptions centrally.

- **Q: How would you handle authentication for these APIs?**
  - A: Use Spring Security with JWT tokens. Add @PreAuthorize or security filters.

- **Q: How to handle concurrent order updates?**
  - A: Use optimistic locking with @Version annotation or database transactions.

- **Q: What if Order Service needs to call Product Service?**
  - A: Use RestTemplate, WebClient, or Feign Client for inter-service communication.

- **Q: How would you implement pagination?**
  - A: Use Pageable parameter:
  ```java
  @GetMapping
  public Page<Order> getAllOrders(Pageable pageable) {
      return orderRepository.findAll(pageable);
  }
  ```

- **Q: What about validation?**
  - A: Use @Valid and validation annotations:
  ```java
  @PostMapping
  public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
      // ...
  }
  ```

---

## Key Interview Tips

### 1. Always Explain in Layers
- Start with simple explanation
- Give real-world example
- Show code if asked
- Explain trade-offs

### 2. Common HTTP Status Codes to Know
- **200 OK** - Success
- **201 Created** - Resource created
- **400 Bad Request** - Invalid input
- **404 Not Found** - Resource not found
- **500 Internal Server Error** - Server error

### 3. REST API Best Practices
- Use proper HTTP methods (GET, POST, PUT, PATCH, DELETE)
- Use meaningful URL paths
- Return appropriate status codes
- Handle exceptions properly
- Use DTOs to separate internal models from API
- Implement validation
- Add pagination for large datasets
- Version your APIs (/api/v1/orders)

### 4. Be Ready to Discuss
- Microservices communication
- API Security (JWT, OAuth2)
- Database transactions
- Caching strategies
- API documentation (Swagger)
- Testing (Unit tests, Integration tests)

---

## Quick Revision Points

**@RestController:**
- Combination of @Controller + @ResponseBody
- Returns data (JSON/XML), not views
- Used for REST APIs

**RestTemplate:**
- HTTP client to call external APIs
- Common methods: getForObject, postForObject, exchange
- Being replaced by WebClient

**REST API Design:**
- Follow RESTful principles
- Proper HTTP methods and status codes
- Exception handling with @RestControllerAdvice
- Service layer for business logic
- Repository for data access

**Key Annotations:**
- @RestController, @RequestMapping
- @GetMapping, @PostMapping, @PutMapping, @PatchMapping, @DeleteMapping
- @PathVariable, @RequestParam, @RequestBody
- @Service, @Repository, @Autowired

Good luck with your interview! 🚀
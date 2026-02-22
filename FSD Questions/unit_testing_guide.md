# Unit Testing Guide - Java & Spring Boot
## Complete Interview Preparation Document

---

## 1. What is Unit Testing?

**Simple Answer:**
Unit testing is testing the smallest piece of code (like a single method or function) in isolation to make sure it works correctly.

**Interview Answer:**
"Unit testing is a software testing technique where individual units or components of code are tested independently to verify that each unit functions correctly. A unit is the smallest testable part of an application, typically a method or function. The goal is to catch bugs early in the development cycle."

**Cross Questions You Might Face:**
- Q: Why is unit testing important?
- A: It helps catch bugs early, makes code more maintainable, serves as documentation, and enables safe refactoring.

- Q: What makes a good unit test?
- A: Fast, Independent, Repeatable, Self-validating, and Timely (FIRST principles)

---

## 2. Difference Between Unit Tests and Integration Tests

**Simple Explanation:**
- **Unit Test**: Tests one small piece alone (like testing a calculator's add method)
- **Integration Test**: Tests how multiple pieces work together (like testing if calculator works with database)

**Detailed Comparison:**

| Aspect | Unit Test | Integration Test |
|--------|-----------|------------------|
| Scope | Single method/class | Multiple components together |
| Dependencies | Mocked/Stubbed | Real dependencies |
| Speed | Very fast (milliseconds) | Slower (seconds) |
| Isolation | Complete isolation | Tests interactions |
| Example | Testing a sum method | Testing REST API with database |

**Interview Answer:**
"Unit tests focus on testing individual components in isolation with mocked dependencies, executing quickly. Integration tests verify that multiple components work together correctly with real dependencies, taking more time to execute."

**Cross Questions:**
- Q: When would you choose integration tests over unit tests?
- A: When testing critical workflows, database interactions, or third-party API integrations where component interaction is crucial.

---

## 3. What Tools Have You Used for Unit Testing?

**Answer Template:**
"In my projects, I've primarily used:
- **JUnit 5** (Jupiter) - Main testing framework
- **Mockito** - For mocking dependencies
- **AssertJ** - For fluent assertions
- **Spring Boot Test** - For Spring-specific testing
- **MockMvc** - For testing REST controllers
- **H2 Database** - For testing with in-memory database"

**Cross Questions:**
- Q: Why JUnit 5 over JUnit 4?
- A: Better lambda support, new annotations like @ParameterizedTest, improved extension model, and better exception testing.

---

## 4. Which Framework is Commonly Used for Unit Testing in Java?

**Simple Answer:**
JUnit is the most popular framework for unit testing in Java.

**Interview Answer:**
"JUnit is the de facto standard for unit testing in Java. Currently, JUnit 5 (also known as JUnit Jupiter) is widely adopted. It comes built-in with Spring Boot and provides annotations like @Test, @BeforeEach, @AfterEach, and assertions for validating test outcomes."

**Example:**
```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @Test
    void testAddition() {
        Calculator calc = new Calculator();
        assertEquals(5, calc.add(2, 3));
    }
}
```

---

## 5. How Does Mockito Help?

**Simple Answer:**
Mockito creates fake objects (mocks) so you can test your code without needing real dependencies like databases or external services.

**Interview Answer:**
"Mockito is a mocking framework that helps create mock objects for dependencies, allowing us to test classes in isolation. It enables us to:
- Create mock objects of dependencies
- Define behavior of mocked methods using when-then syntax
- Verify if methods were called with specific arguments
- Test without real database connections or external APIs"

**Real-World Example:**
"Suppose I have a UserService that depends on UserRepository. Instead of connecting to a real database during testing, I use Mockito to create a fake UserRepository that returns predefined data."

**Cross Questions:**
- Q: What's the difference between @Mock and @InjectMocks?
- A: @Mock creates a mock object, while @InjectMocks creates an instance and injects mocked dependencies into it.

---

## 6. Common Mockito Methods with Examples

### a) **@Mock** - Create mock object
```java
@Mock
private UserRepository userRepository;
```

### b) **@InjectMocks** - Inject mocks into this object
```java
@InjectMocks
private UserService userService;
```

### c) **when().thenReturn()** - Define behavior
```java
when(userRepository.findById(1L))
    .thenReturn(Optional.of(new User("John")));
```

### d) **verify()** - Check if method was called
```java
verify(userRepository).save(any(User.class));
verify(userRepository, times(1)).findById(1L);
```

### e) **doThrow()** - Throw exception for void methods
```java
doThrow(new RuntimeException("Error"))
    .when(userRepository).delete(any(User.class));
```

### f) **any(), anyString(), anyInt()** - Argument matchers
```java
when(userRepository.findByEmail(anyString()))
    .thenReturn(Optional.of(user));
```

### g) **doNothing()** - For void methods
```java
doNothing().when(emailService).sendEmail(anyString());
```

**Cross Questions:**
- Q: What's the difference between when().thenReturn() and doReturn().when()?
- A: They're mostly the same, but doReturn().when() doesn't call the real method, useful for spies or when method has side effects.

---

## 7. How to Write Unit Tests in Spring Boot Using JUnit and Mockito?

**Step-by-Step Approach:**

### Complete Example:

```java
// Service Class
@Service
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}

// Test Class
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    void testGetUserById_Success() {
        // Arrange
        User expectedUser = new User(1L, "John Doe");
        when(userRepository.findById(1L))
            .thenReturn(Optional.of(expectedUser));
        
        // Act
        User actualUser = userService.getUserById(1L);
        
        // Assert
        assertNotNull(actualUser);
        assertEquals("John Doe", actualUser.getName());
        verify(userRepository, times(1)).findById(1L);
    }
    
    @Test
    void testGetUserById_NotFound() {
        // Arrange
        when(userRepository.findById(1L))
            .thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(1L);
        });
    }
}
```

**Key Points to Mention:**
1. Use @ExtendWith(MockitoExtension.class) for JUnit 5
2. Follow AAA pattern: Arrange, Act, Assert
3. Mock dependencies with @Mock
4. Inject mocks with @InjectMocks
5. Verify interactions when necessary

---

## 8. How to Mock Dependencies? Can Static Methods Be Mocked?

### Mocking Dependencies:

**Answer:**
"We mock dependencies using @Mock annotation or Mockito.mock() method. The mocked object is then injected into the class under test using @InjectMocks."

```java
@Mock
private EmailService emailService;

@InjectMocks
private NotificationService notificationService;
```

### Can Static Methods Be Mocked?

**Answer:**
"Regular Mockito cannot mock static methods directly. However, from Mockito 3.4.0 onwards, we can use mockStatic() to mock static methods."

**Example:**
```java
@Test
void testStaticMethod() {
    try (MockedStatic<UtilityClass> mockedStatic = 
         Mockito.mockStatic(UtilityClass.class)) {
        
        mockedStatic.when(() -> UtilityClass.staticMethod())
                    .thenReturn("Mocked Result");
        
        String result = UtilityClass.staticMethod();
        assertEquals("Mocked Result", result);
    }
}
```

**Best Practice Note:**
"However, heavy use of static methods is generally discouraged as it makes testing harder. Dependency injection is preferred."

**Cross Questions:**
- Q: Can we mock final classes or methods?
- A: Yes, with Mockito 2+ using mockito-inline dependency.

---

## 9. How to Mock External Services?

**Interview Answer:**
"To mock external services, I create a mock of the service interface/class and define its behavior using Mockito's when-then syntax. This allows testing without making actual HTTP calls or connecting to external APIs."

**Example:**

```java
// External Service Interface
public interface PaymentGatewayService {
    PaymentResponse processPayment(PaymentRequest request);
}

// Service using external service
@Service
public class OrderService {
    private final PaymentGatewayService paymentGateway;
    
    public OrderService(PaymentGatewayService paymentGateway) {
        this.paymentGateway = paymentGateway;
    }
    
    public Order placeOrder(Order order) {
        PaymentResponse response = paymentGateway
            .processPayment(new PaymentRequest(order.getAmount()));
        
        if (response.isSuccess()) {
            order.setStatus("CONFIRMED");
        } else {
            order.setStatus("FAILED");
        }
        return order;
    }
}

// Test Class
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    
    @Mock
    private PaymentGatewayService paymentGateway;
    
    @InjectMocks
    private OrderService orderService;
    
    @Test
    void testPlaceOrder_PaymentSuccess() {
        // Arrange
        Order order = new Order(100.0);
        PaymentResponse successResponse = new PaymentResponse(true);
        
        when(paymentGateway.processPayment(any(PaymentRequest.class)))
            .thenReturn(successResponse);
        
        // Act
        Order result = orderService.placeOrder(order);
        
        // Assert
        assertEquals("CONFIRMED", result.getStatus());
        verify(paymentGateway).processPayment(any(PaymentRequest.class));
    }
    
    @Test
    void testPlaceOrder_PaymentFailure() {
        // Arrange
        Order order = new Order(100.0);
        PaymentResponse failureResponse = new PaymentResponse(false);
        
        when(paymentGateway.processPayment(any(PaymentRequest.class)))
            .thenReturn(failureResponse);
        
        // Act
        Order result = orderService.placeOrder(order);
        
        // Assert
        assertEquals("FAILED", result.getStatus());
    }
}
```

---

## 10. Unit Test for Sum Method

**Question:** Write a unit test case for a sum method that takes two numbers as arguments and returns their sum.

**Solution:**

```java
// Class to be tested
public class Calculator {
    public int sum(int a, int b) {
        return a + b;
    }
}

// Test Class
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    
    private Calculator calculator;
    
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }
    
    @Test
    void testSum_PositiveNumbers() {
        // Arrange
        int a = 5;
        int b = 3;
        
        // Act
        int result = calculator.sum(a, b);
        
        // Assert
        assertEquals(8, result, "5 + 3 should equal 8");
    }
    
    @Test
    void testSum_NegativeNumbers() {
        int result = calculator.sum(-5, -3);
        assertEquals(-8, result);
    }
    
    @Test
    void testSum_MixedNumbers() {
        int result = calculator.sum(10, -5);
        assertEquals(5, result);
    }
    
    @Test
    void testSum_WithZero() {
        int result = calculator.sum(5, 0);
        assertEquals(5, result);
    }
}
```

**Interview Tips:**
- Test multiple scenarios (positive, negative, zero, edge cases)
- Use descriptive test method names
- Follow AAA pattern

---

## 11. Unit Test for UserService with Mockito (Note: Question mentions "kmock" but likely means Mockito)

**Question:** Write a unit test case using Mockito for a UserService class with a getUserById method.

**Solution:**

```java
// Entity
public class User {
    private Long id;
    private String name;
    private String email;
    
    // Constructors, getters, setters
}

// Repository Interface
public interface UserRepository extends JpaRepository<User, Long> {
}

// Service Class
@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}

// Test Class
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    void testGetUserById_UserExists() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setName("John Doe");
        mockUser.setEmail("john@example.com");
        
        when(userRepository.findById(userId))
            .thenReturn(Optional.of(mockUser));
        
        // Act
        User result = userService.getUserById(userId);
        
        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        
        // Verify interaction
        verify(userRepository, times(1)).findById(userId);
    }
    
    @Test
    void testGetUserById_UserNotFound() {
        // Arrange
        Long userId = 999L;
        when(userRepository.findById(userId))
            .thenReturn(Optional.empty());
        
        // Act & Assert
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> userService.getUserById(userId)
        );
        
        assertTrue(exception.getMessage().contains("User not found"));
        verify(userRepository, times(1)).findById(userId);
    }
    
    @Test
    void testGetUserById_NullId() {
        // This tests what happens when null is passed
        assertThrows(Exception.class, () -> {
            userService.getUserById(null);
        });
    }
}
```

---

## 12. Unit Test ProductService Without Hitting Database

**Question:** You have a ProductService that calls ProductRepository already in your project. How would you unit test the service without hitting the database?

**Answer:**
"I would mock the ProductRepository using Mockito so that the test doesn't connect to the actual database. The mocked repository returns predefined data, allowing us to test the service logic in isolation."

**Complete Example:**

```java
// Entity
@Entity
public class Product {
    @Id
    private Long id;
    private String name;
    private Double price;
    private Integer stock;
    
    // Constructors, getters, setters
}

// Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceGreaterThan(Double price);
}

// Service
@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
    
    public Product saveProduct(Product product) {
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        return productRepository.save(product);
    }
    
    public List<Product> getExpensiveProducts(Double minPrice) {
        return productRepository.findByPriceGreaterThan(minPrice);
    }
    
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Cannot delete non-existent product");
        }
        productRepository.deleteById(id);
    }
}

// Test Class
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    
    @Mock
    private ProductRepository productRepository;
    
    @InjectMocks
    private ProductService productService;
    
    @Test
    void testGetProductById_Success() {
        // Arrange
        Product mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setName("Laptop");
        mockProduct.setPrice(1200.0);
        
        when(productRepository.findById(1L))
            .thenReturn(Optional.of(mockProduct));
        
        // Act
        Product result = productService.getProductById(1L);
        
        // Assert
        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals(1200.0, result.getPrice());
        verify(productRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(productRepository);
    }
    
    @Test
    void testGetProductById_NotFound() {
        // Arrange
        when(productRepository.findById(999L))
            .thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(999L);
        });
        
        verify(productRepository).findById(999L);
    }
    
    @Test
    void testSaveProduct_Success() {
        // Arrange
        Product inputProduct = new Product();
        inputProduct.setName("Mouse");
        inputProduct.setPrice(25.0);
        
        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Mouse");
        savedProduct.setPrice(25.0);
        
        when(productRepository.save(any(Product.class)))
            .thenReturn(savedProduct);
        
        // Act
        Product result = productService.saveProduct(inputProduct);
        
        // Assert
        assertNotNull(result.getId());
        assertEquals("Mouse", result.getName());
        verify(productRepository).save(inputProduct);
    }
    
    @Test
    void testSaveProduct_NegativePrice() {
        // Arrange
        Product invalidProduct = new Product();
        invalidProduct.setName("Invalid");
        invalidProduct.setPrice(-10.0);
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            productService.saveProduct(invalidProduct);
        });
        
        verify(productRepository, never()).save(any(Product.class));
    }
    
    @Test
    void testGetExpensiveProducts() {
        // Arrange
        List<Product> expensiveProducts = Arrays.asList(
            new Product(1L, "MacBook", 2000.0, 10),
            new Product(2L, "iPhone", 1000.0, 20)
        );
        
        when(productRepository.findByPriceGreaterThan(500.0))
            .thenReturn(expensiveProducts);
        
        // Act
        List<Product> result = productService.getExpensiveProducts(500.0);
        
        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(p -> p.getPrice() > 500.0));
        verify(productRepository).findByPriceGreaterThan(500.0);
    }
    
    @Test
    void testDeleteProduct_Success() {
        // Arrange
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);
        doNothing().when(productRepository).deleteById(productId);
        
        // Act
        productService.deleteProduct(productId);
        
        // Assert
        verify(productRepository).existsById(productId);
        verify(productRepository).deleteById(productId);
    }
    
    @Test
    void testDeleteProduct_NotFound() {
        // Arrange
        Long productId = 999L;
        when(productRepository.existsById(productId)).thenReturn(false);
        
        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productService.deleteProduct(productId);
        });
        
        verify(productRepository).existsById(productId);
        verify(productRepository, never()).deleteById(any());
    }
}
```

**Key Interview Points:**
1. "I use @Mock to create a fake ProductRepository"
2. "I use when-thenReturn to define what the mock should return"
3. "This way, no database connection is made during testing"
4. "I can test various scenarios like success, failure, and edge cases"
5. "I verify that repository methods are called correctly"

---

## 13. Testing Exception Scenarios - PaymentService

**Question:** How would you test this scenario?

```java
class PaymentService {
    void process() throws IOException {
        throw new IOException("Gateway down");
    }
}
```

**Solution:**

```java
// The class to be tested
class PaymentService {
    void process() throws IOException {
        throw new IOException("Gateway down");
    }
}

// Test Class - Multiple Approaches

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {
    
    // Approach 1: Using assertThrows (Recommended)
    @Test
    void testProcess_ThrowsIOException_Approach1() {
        // Arrange
        PaymentService paymentService = new PaymentService();
        
        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> {
            paymentService.process();
        });
        
        assertEquals("Gateway down", exception.getMessage());
    }
    
    // Approach 2: Using assertThrows with message verification
    @Test
    void testProcess_ThrowsIOException_Approach2() {
        PaymentService paymentService = new PaymentService();
        
        IOException exception = assertThrows(
            IOException.class,
            () -> paymentService.process(),
            "Expected process() to throw IOException"
        );
        
        assertTrue(exception.getMessage().contains("Gateway down"));
    }
    
    // Approach 3: Using try-catch (Old style, not recommended)
    @Test
    void testProcess_ThrowsIOException_Approach3() {
        PaymentService paymentService = new PaymentService();
        
        try {
            paymentService.process();
            fail("Expected IOException to be thrown");
        } catch (IOException e) {
            assertEquals("Gateway down", e.getMessage());
        }
    }
}
```

**More Realistic Scenario with Mockito:**

```java
// Enhanced PaymentService that uses a gateway
@Service
class PaymentService {
    
    private final PaymentGateway paymentGateway;
    
    public PaymentService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }
    
    public void process(Payment payment) throws IOException {
        paymentGateway.sendPayment(payment);
    }
}

interface PaymentGateway {
    void sendPayment(Payment payment) throws IOException;
}

class Payment {
    private String id;
    private Double amount;
    // Constructor, getters, setters
}

// Comprehensive Test Class
@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    
    @Mock
    private PaymentGateway paymentGateway;
    
    @InjectMocks
    private PaymentService paymentService;
    
    @Test
    void testProcess_ThrowsIOException_WhenGatewayIsDown() throws IOException {
        // Arrange
        Payment payment = new Payment("PAY123", 100.0);
        
        doThrow(new IOException("Gateway down"))
            .when(paymentGateway)
            .sendPayment(any(Payment.class));
        
        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> {
            paymentService.process(payment);
        });
        
        assertEquals("Gateway down", exception.getMessage());
        verify(paymentGateway, times(1)).sendPayment(payment);
    }
    
    @Test
    void testProcess_Success() throws IOException {
        // Arrange
        Payment payment = new Payment("PAY123", 100.0);
        doNothing().when(paymentGateway).sendPayment(any(Payment.class));
        
        // Act
        paymentService.process(payment);
        
        // Assert
        verify(paymentGateway).sendPayment(payment);
    }
    
    @Test
    void testProcess_ThrowsIOException_WithSpecificMessage() throws IOException {
        // Arrange
        Payment payment = new Payment("PAY123", 100.0);
        
        doThrow(new IOException("Network timeout"))
            .when(paymentGateway)
            .sendPayment(payment);
        
        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> {
            paymentService.process(payment);
        });
        
        assertTrue(exception.getMessage().contains("timeout"));
    }
}
```

**Interview Explanation:**
"To test methods that throw exceptions, I use JUnit's `assertThrows()` method. It verifies that the expected exception is thrown and allows me to check the exception message. When testing with Mockito, I use `doThrow()` to make the mocked dependency throw an exception, then verify that the exception propagates correctly through my service."

---

## Additional Important Topics for Interview

### Test Coverage
**Q: What is test coverage and what's a good percentage?**
**A:** "Test coverage measures the percentage of code executed during tests. While 80%+ is often recommended, quality matters more than quantity. Critical business logic should have near 100% coverage, while simple getters/setters may not need explicit tests."

### Test-Driven Development (TDD)
**Q: What is TDD?**
**A:** "TDD is a development approach where you write tests before writing code. The cycle is: Red (write failing test) → Green (write minimum code to pass) → Refactor (improve code)."

### @BeforeEach vs @BeforeAll
```java
@BeforeEach  // Runs before each test method
void setUp() {
    calculator = new Calculator();
}

@BeforeAll  // Runs once before all tests
static void init() {
    // Heavy initialization
}
```

### Parameterized Tests
```java
@ParameterizedTest
@CsvSource({
    "1, 2, 3",
    "5, 5, 10",
    "10, -5, 5"
})
void testSum(int a, int b, int expected) {
    assertEquals(expected, calculator.sum(a, b));
}
```

---

## Quick Reference: Common Annotations

| Annotation | Purpose |
|------------|---------|
| @Test | Marks a method as test |
| @Mock | Creates mock object |
| @InjectMocks | Injects mocks into this object |
| @BeforeEach | Runs before each test |
| @AfterEach | Runs after each test |
| @BeforeAll | Runs once before all tests |
| @AfterAll | Runs once after all tests |
| @ExtendWith | Specifies extension (MockitoExtension) |
| @DisplayName | Custom test name |
| @Disabled | Skip this test |

---

## Best Practices Summary

1. ✅ **Write independent tests** - Tests shouldn't depend on each other
2. ✅ **Use descriptive names** - `testGetUserById_WhenUserExists_ReturnsUser()`
3. ✅ **Follow AAA pattern** - Arrange, Act, Assert
4. ✅ **Test one thing per test** - Each test should verify one behavior
5. ✅ **Mock external dependencies** - Don't hit databases or APIs
6. ✅ **Verify interactions** - Use `verify()` to check method calls
7. ✅ **Test edge cases** - null, empty, negative values
8. ✅ **Keep tests fast** - Unit tests should run in milliseconds
9. ✅ **Don't test framework code** - Focus on your business logic
10. ✅ **Clean up resources** - Use @AfterEach when needed

---

## Common Interview Questions Recap

1. **"How do you ensure your tests are reliable?"**
   - Use isolated tests, mock dependencies, avoid random data, clean state

2. **"What's the difference between Spy and Mock?"**
   - Mock creates fake object, Spy wraps real object (partial mocking)

3. **"How do you test private methods?"**
   - Generally don't test directly; test through public methods. If needed, use reflection (not recommended)

4. **"What's code coverage and is 100% necessary?"**
   - Percentage of code executed by tests. 100% isn't always necessary; focus on critical paths

5. **"How do you test REST controllers?"**
   - Use MockMvc or @WebMvcTest annotation

---

## Final Tips for Interview Success

1. **Show your thought process** - Explain why you're doing something
2. **Start with simple test** - Then add edge cases
3. **Mention trade-offs** - "I'd use integration test here because..."
4. **Be honest** - If you don't know, say so and explain how you'd learn
5. **Practice coding** - Be ready to write tests on whiteboard/laptop

---

**Good Luck with Your Interview! 🚀**
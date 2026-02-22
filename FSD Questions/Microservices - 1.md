# Microservices Architecture - Complete Interview Guide

## 1. What is Microservices Architecture?

### Simple Explanation
Microservices is like building a city with many small, specialized shops instead of one giant supermarket.

**Imagine:**
- **Monolithic** = One huge supermarket selling everything (groceries, clothes, electronics)
- **Microservices** = Separate specialized stores (grocery store, clothing store, electronics store)

Each store (service) is:
- Independent
- Focused on one thing
- Can be changed without affecting others
- Can use different technologies

### Technical Definition
Microservices architecture is a design approach where:
- Application is divided into **small, independent services**
- Each service runs in its **own process**
- Services communicate through **APIs** (REST, messaging)
- Each service can be **deployed independently**
- Each service has its **own database**

### Example
```
E-commerce Application:
├── User Service (handles login, profiles)
├── Product Service (manages product catalog)
├── Order Service (processes orders)
├── Payment Service (handles payments)
├── Notification Service (sends emails/SMS)
└── Inventory Service (tracks stock)
```

### Cross Questions

**Q: What is a service in microservices?**
- A: A service is a small, independent application that does ONE specific business function. Example: User authentication service only handles login/signup.

**Q: How small should a microservice be?**
- A: Follow the "Single Responsibility Principle" - one service should do one thing well. If your team can rewrite it in 2 weeks, it's a good size.

---

## 2. Microservices vs Monolithic Architecture

### Visual Comparison

**MONOLITHIC ARCHITECTURE:**
```
┌─────────────────────────────────────┐
│     Single Application              │
│  ┌──────────────────────────────┐  │
│  │   User Management            │  │
│  │   Product Catalog            │  │
│  │   Order Processing           │  │
│  │   Payment Processing         │  │
│  │   Notification System        │  │
│  └──────────────────────────────┘  │
│                                     │
│      Single Database                │
└─────────────────────────────────────┘
       One Deployment
```

**MICROSERVICES ARCHITECTURE:**
```
┌─────────┐  ┌─────────┐  ┌─────────┐
│ User    │  │Product  │  │ Order   │
│ Service │  │Service  │  │ Service │
│  + DB   │  │  + DB   │  │  + DB   │
└─────────┘  └─────────┘  └─────────┘
     ↕            ↕            ↕
    API       Gateway      (Communication)
     ↕            ↕            ↕
┌─────────┐  ┌─────────┐  ┌─────────┐
│Payment  │  │Notif.   │  │Inventory│
│Service  │  │Service  │  │Service  │
│  + DB   │  │  + DB   │  │  + DB   │
└─────────┘  └─────────┘  └─────────┘
```

### Detailed Comparison Table

| Aspect | Monolithic | Microservices |
|--------|-----------|---------------|
| **Structure** | Single codebase | Multiple independent services |
| **Database** | One shared database | Each service has its own DB |
| **Deployment** | Deploy entire app | Deploy services independently |
| **Technology** | Same tech stack | Different tech per service |
| **Scaling** | Scale entire application | Scale individual services |
| **Development** | Single team works together | Multiple teams work independently |
| **Testing** | Test entire application | Test each service separately |
| **Failure Impact** | Entire app goes down | Only one service fails |
| **Complexity** | Simple to start | Complex infrastructure |

### Real-World Example

**Monolithic Example: Early Amazon (2000s)**
```java
@SpringBootApplication
public class AmazonMonolith {
    // All in one application
    UserController userController;
    ProductController productController;
    OrderController orderController;
    PaymentController paymentController;
    // ... everything in one app
}
```

**Microservices Example: Modern Netflix**
```
Netflix has 700+ microservices:
- Recommendation Service
- Video Streaming Service
- User Profile Service
- Billing Service
- Content Management Service
... each runs independently
```

### Cross Questions

**Q: Why did companies move from monolithic to microservices?**
- A: As applications grew bigger, monolithic apps became:
  - Hard to maintain (millions of lines of code)
  - Slow to deploy (one bug stops entire release)
  - Hard to scale (can't scale just one feature)
  - Risky (one crash brings down everything)

**Q: Can you convert monolithic to microservices?**
- A: Yes, through "strangler pattern" - gradually extract features into services while keeping monolith running.

---

## 3. Advantages of Microservices over Monolithic

### Simple Explanation
Think of it like this: Would you rather have one person doing everything (cooking, cleaning, driving) or specialized people for each task?

### Key Advantages

#### 1. Independent Deployment
```
Monolithic: Fix a bug in payment → Deploy entire app → Risk everything
Microservices: Fix payment service → Deploy only payment service → No risk to others
```

#### 2. Technology Freedom
```java
// User Service - Java Spring Boot
@RestController
public class UserService {
    // Java code
}

// Recommendation Service - Python
from flask import Flask
app = Flask(__name__)
# Python code

// Notification Service - Node.js
const express = require('express');
// JavaScript code
```

#### 3. Better Scaling
```
Black Friday Sale:
Monolithic: Must scale entire application (expensive)
Microservices: Scale only Product + Order + Payment services (cost-effective)
```

#### 4. Fault Isolation
```
If Payment service crashes:
Monolithic: Entire website down ❌
Microservices: Users can browse, add to cart, everything except payment ✅
```

#### 5. Team Independence
```
Monolithic:
- 50 developers working on same codebase
- Merge conflicts, dependencies, waiting

Microservices:
- 5 teams of 10 developers
- Each team owns a service
- No conflicts, faster development
```

#### 6. Easier Maintenance
```
Monolithic: Change one line → Test entire app (takes days)
Microservices: Change one service → Test only that service (takes hours)
```

### Complete Advantages List

1. **Independent Deployment** - Deploy services separately
2. **Technology Diversity** - Use best tool for each job
3. **Scalability** - Scale specific services based on demand
4. **Fault Isolation** - One failure doesn't crash everything
5. **Team Autonomy** - Teams work independently
6. **Faster Development** - Parallel development
7. **Easy Maintenance** - Smaller codebases
8. **Better Resource Utilization** - Optimize each service differently
9. **Continuous Delivery** - Deploy multiple times per day
10. **Business Agility** - Quick feature additions

### Cross Questions

**Q: Are microservices always better than monolithic?**
- A: No! Microservices add complexity. For small applications or startups, monolithic is often better.

**Q: What does "fault isolation" mean?**
- A: When one service fails, others keep working. Like if kitchen has fire, dining room stays open.

**Q: Give real example of independent scaling?**
- A: Zomato/Swiggy during lunch time (12-2pm):
  - Restaurant Service gets 10x traffic → Scale it up
  - Payment Service is normal → Keep as is
  - Save money by not scaling everything

---

## 4. When to Prefer Monolithic Architecture?

### Simple Answer
Use monolithic when you're building something **small, simple, or just starting out**.

### Scenarios for Monolithic

#### 1. Small Applications
```
Example: Blog website, Portfolio site, Small business website
Reason: Not enough complexity to justify microservices overhead
```

#### 2. Startups & MVPs
```
Example: New startup testing product-market fit
Reason: 
- Need to move fast
- Requirements change frequently
- Small team (2-5 developers)
- Limited budget
```

#### 3. Simple Business Logic
```
Example: CRUD application with basic operations
Reason: No need for complex distributed system
```

#### 4. Limited Team Size
```
Team Size: 1-5 developers
Reason: Microservices need DevOps expertise, monitoring, and multiple teams
```

#### 5. Tight Deadlines
```
Scenario: Launch product in 3 months
Reason: Monolithic is faster to build initially
```

#### 6. Budget Constraints
```
Monolithic Cost: 1 server + 1 database = $100/month
Microservices Cost: 10 services + 10 DBs + orchestration + monitoring = $1000/month
```

### When Monolithic Makes Sense - Decision Matrix

| Factor | Monolithic | Microservices |
|--------|-----------|---------------|
| **Team Size** | < 10 developers | 10+ developers |
| **Application Size** | < 100,000 lines | 100,000+ lines |
| **Expected Traffic** | < 10,000 users | 100,000+ users |
| **Budget** | Limited | Well-funded |
| **Time to Market** | 1-3 months | 6+ months |
| **Complexity** | Low-Medium | High |
| **Deployment Frequency** | Weekly/Monthly | Multiple times/day |

### Real Examples of Successful Monoliths

**1. Shopify (Started Monolithic)**
```
- Powers 1.7 million businesses
- Initially built as monolith (Ruby on Rails)
- Still largely monolithic with some microservices
- Reason: Simpler to maintain, team knows it well
```

**2. Basecamp**
```
- Multi-million dollar company
- Deliberately stays monolithic
- Reason: Small team, simple to deploy, easy to understand
```

### Cross Questions

**Q: Can a successful company stay monolithic?**
- A: Yes! Basecamp, Shopify, many others. It depends on your needs, not industry trends.

**Q: What if monolithic app becomes too big?**
- A: Then migrate to microservices gradually (strangler pattern). Start when pain points appear.

**Q: How do you know when to switch from monolithic to microservices?**
- A: When you experience:
  - Deployment takes too long
  - Can't scale specific features
  - Too many merge conflicts
  - Different teams stepping on each other
  - One bug brings down entire app

**Q: Is it wrong to start with monolithic?**
- A: Not at all! Many experts recommend "Start with monolithic, move to microservices when needed."

---

## 5. Problems of Microservices Architecture

### Simple Explanation
Microservices solve many problems but create new ones. It's like having many small shops instead of one store - more flexibility but harder to coordinate!

### Major Problems

#### 1. **Increased Complexity**

**Problem:**
```
Monolithic: 1 application to manage
Microservices: 50+ services to manage
- Where is the bug?
- Which service is slow?
- How do services connect?
```

**Real Example:**
```
Amazon shopping cart not working:
- Is User Service down?
- Is Product Service slow?
- Is Cart Service failing?
- Is Database connection issue?
- Is network problem?
→ Need to check 5+ services instead of 1!
```

#### 2. **Data Management**

**Problem:**
```java
// Monolithic - Easy
User user = userRepo.findById(1);
Order order = user.getOrders(); // Simple join

// Microservices - Complex
User user = userService.getUserById(1);  // Call to User Service
Order order = orderService.getOrderByUserId(1); // Call to Order Service
// Now combine data from 2 different services!
```

**Issues:**
- No database joins across services
- Data consistency problems
- Duplicate data across services

#### 3. **Network Latency**

**Problem:**
```
Monolithic: In-memory function call (0.001ms)
Microservices: Network HTTP call (50-100ms)

One request might call 10 services = 500ms delay!
```

**Real Impact:**
```
Shopping checkout process:
User Service → Product Service → Inventory Service → 
Cart Service → Payment Service → Order Service → Notification Service

If each takes 100ms = 700ms total response time!
```

#### 4. **Distributed Transactions**

**Problem:**
```java
// Monolithic - Easy transaction
@Transactional
public void placeOrder(Order order) {
    orderRepo.save(order);              // Step 1
    paymentRepo.processPayment(payment); // Step 2
    inventoryRepo.reduceStock(items);    // Step 3
    // If any fails, all rollback automatically
}

// Microservices - Very Complex!
public void placeOrder(Order order) {
    orderService.createOrder(order);        // Different DB
    paymentService.processPayment(payment); // Different DB
    inventoryService.reduceStock(items);    // Different DB
    
    // What if payment succeeds but inventory fails?
    // Need SAGA pattern or event sourcing!
}
```

#### 5. **Testing Complexity**

**Problem:**
```
Monolithic: Run one test suite, test everything

Microservices:
- Unit tests for each service
- Integration tests for service pairs
- End-to-end tests for entire flow
- Contract tests between services
- Performance tests
Need 5x more testing effort!
```

#### 6. **Deployment Complexity**

**Problem:**
```
Monolithic: 
1. Build app
2. Deploy to server
Done!

Microservices:
1. Build 20 services
2. Deploy each service
3. Configure load balancers
4. Set up service discovery
5. Configure API gateway
6. Monitor all services
7. Set up logging aggregation
8. Handle service dependencies
Need DevOps team!
```

#### 7. **Debugging Challenges**

**Problem:**
```
Error: "Payment failed"

Monolithic: 
- Check one log file
- Find error
- Fix it

Microservices:
- Check User Service logs
- Check Cart Service logs  
- Check Payment Service logs
- Check API Gateway logs
- Trace request across services
- Need distributed tracing tool (Zipkin, Jaeger)
```

#### 8. **Operational Overhead**

**Costs:**
```
Monolithic:
- 1 server
- 1 database
- 1 monitoring tool
Total: $200/month

Microservices:
- 20 servers
- 20 databases
- Service mesh (Istio)
- API Gateway
- Monitoring (Prometheus, Grafana)
- Log aggregation (ELK stack)
- Tracing (Jaeger)
- Container orchestration (Kubernetes)
Total: $2000+/month
```

#### 9. **Versioning Hell**

**Problem:**
```
User Service v1.0 calls Order Service v2.0
Order Service v2.0 changes API format
User Service breaks!

Need to maintain:
- Backward compatibility
- API versioning
- Multiple versions running simultaneously
```

#### 10. **Team Coordination**

**Problem:**
```
Monolithic: 
- One team meeting
- Everyone knows everything

Microservices:
- Multiple team meetings
- Need documentation for every service
- Cross-team dependencies
- Who owns which service?
```

### Complete Problems List

1. **Complexity** - More moving parts
2. **Data Management** - No joins, eventual consistency
3. **Network Latency** - Multiple network calls
4. **Distributed Transactions** - Hard to maintain consistency
5. **Testing** - More complex testing scenarios
6. **Deployment** - Complex CI/CD pipelines
7. **Debugging** - Hard to trace issues
8. **Operational Cost** - More infrastructure needed
9. **Versioning** - API version management
10. **Team Coordination** - Need more communication
11. **Security** - More attack surfaces
12. **Monitoring** - Need distributed monitoring
13. **Service Discovery** - Services need to find each other
14. **Cascading Failures** - One failure can trigger others

### Cross Questions

**Q: If microservices have so many problems, why use them?**
- A: For large, complex applications, benefits outweigh problems. For small apps, stick to monolithic.

**Q: How to handle data consistency across services?**
- A: Use patterns like:
  - Saga pattern (choreography or orchestration)
  - Event sourcing
  - CQRS (Command Query Responsibility Segregation)
  - Eventual consistency

**Q: What is the biggest challenge in microservices?**
- A: Distributed data management and maintaining data consistency across services.

**Q: How do big companies handle these problems?**
- A: They invest heavily in:
  - DevOps teams
  - Monitoring tools
  - Service mesh (Istio, Linkerd)
  - API gateways
  - Strong engineering practices

---

## 6. Microservices Design Patterns

### Simple Explanation
Design patterns are proven solutions to common problems. Like recipes for building microservices correctly.

### Essential Patterns

#### 1. **API Gateway Pattern**

**Problem:** Clients need to call multiple services

**Solution:** Single entry point for all requests

```
┌─────────┐
│ Mobile  │
│  App    │
└────┬────┘
     │
     ↓
┌─────────────┐
│ API Gateway │  ← Single Entry Point
└─────┬───────┘
      │
   ┌──┴──┬──────┬───────┐
   ↓     ↓      ↓       ↓
┌────┐ ┌────┐ ┌────┐ ┌────┐
│User│ │Prod│ │Cart│ │Pay │
└────┘ └────┘ └────┘ └────┘
```

**Code Example:**
```java
@RestController
@RequestMapping("/api")
public class APIGateway {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @GetMapping("/user-dashboard/{userId}")
    public DashboardResponse getUserDashboard(@PathVariable Long userId) {
        // Single API call from client, but gateway calls multiple services
        
        User user = restTemplate.getForObject(
            "http://user-service/users/" + userId, User.class);
        
        List<Order> orders = restTemplate.getForObject(
            "http://order-service/orders/user/" + userId, List.class);
        
        Cart cart = restTemplate.getForObject(
            "http://cart-service/cart/" + userId, Cart.class);
        
        return new DashboardResponse(user, orders, cart);
    }
}
```

**Benefits:**
- Client makes one call instead of many
- Centralized authentication
- Rate limiting
- Request routing
- Response aggregation

#### 2. **Service Discovery Pattern**

**Problem:** Services don't know where other services are located

**Solution:** Central registry where services register themselves

```
┌──────────────────┐
│Service Discovery │
│  (Eureka/Consul) │
└────────┬─────────┘
         │
    ┌────┴─────┬────────┐
    ↓          ↓        ↓
┌─────────┐ ┌──────┐ ┌──────┐
│User Svc │ │Order │ │Payment│
│Register │ │Svc   │ │Service│
└─────────┘ └──────┘ └───────┘
```

**Code Example:**
```java
// User Service - Registers itself
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}

// Order Service - Discovers User Service
@Service
public class OrderService {
    
    @Autowired
    private DiscoveryClient discoveryClient;
    
    public User getUserInfo(Long userId) {
        // Get User Service location from Service Registry
        List<ServiceInstance> instances = 
            discoveryClient.getInstances("USER-SERVICE");
        
        String url = instances.get(0).getUri() + "/users/" + userId;
        return restTemplate.getForObject(url, User.class);
    }
}
```

#### 3. **Database per Service Pattern**

**Problem:** Shared database creates tight coupling

**Solution:** Each service has its own database

```
┌─────────────┐       ┌─────────────┐       ┌─────────────┐
│User Service │       │Order Service│       │Pay Service  │
└──────┬──────┘       └──────┬──────┘       └──────┬──────┘
       │                     │                      │
       ↓                     ↓                      ↓
┌─────────────┐       ┌─────────────┐       ┌─────────────┐
│  Users DB   │       │  Orders DB  │       │ Payments DB │
└─────────────┘       └─────────────┘       └─────────────┘
```

#### 4. **Saga Pattern**

**Problem:** Distributed transactions across services

**Solution:** Break transaction into smaller steps with compensating actions

**Example: Order Placement**
```java
// Choreography-based Saga
@Service
public class OrderSagaService {
    
    @Autowired
    private EventPublisher eventPublisher;
    
    public void placeOrder(Order order) {
        // Step 1: Create Order
        orderRepository.save(order);
        eventPublisher.publish(new OrderCreatedEvent(order));
    }
    
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        // Step 2: Process Payment
        paymentService.processPayment(event.getOrder());
        eventPublisher.publish(new PaymentProcessedEvent(event.getOrder()));
    }
    
    @EventListener
    public void handlePaymentProcessed(PaymentProcessedEvent event) {
        // Step 3: Update Inventory
        inventoryService.reduceStock(event.getOrder());
        eventPublisher.publish(new InventoryUpdatedEvent(event.getOrder()));
    }
    
    // Compensating Transaction if payment fails
    @EventListener
    public void handlePaymentFailed(PaymentFailedEvent event) {
        orderRepository.cancelOrder(event.getOrderId());
        eventPublisher.publish(new OrderCancelledEvent(event.getOrderId()));
    }
}
```

#### 5. **CQRS Pattern**

**Problem:** Read and write operations have different requirements

**Solution:** Separate read and write operations

```
Write Side:                  Read Side:
┌──────────┐                ┌──────────┐
│Command   │    Events      │  Query   │
│Service   │──────────────→ │ Service  │
│(Write DB)│                │(Read DB) │
└──────────┘                └──────────┘
                            Optimized for reads
```

#### 6. **Event Sourcing Pattern**

**Problem:** Hard to track state changes

**Solution:** Store events instead of current state

```java
// Instead of storing current state
Order order = {id: 1, status: "DELIVERED", total: 100}

// Store all events
Events:
1. OrderCreated(id=1, items=[...], total=100)
2. PaymentReceived(orderId=1, amount=100)
3. OrderShipped(orderId=1, trackingId="XYZ")
4. OrderDelivered(orderId=1, deliveredAt="2024-01-15")

// Current state = replay all events
```

#### 7. **Circuit Breaker Pattern** (Detailed in next section)

#### 8. **Bulkhead Pattern**

**Problem:** One service failure affects all operations

**Solution:** Isolate resources

```java
@Service
public class OrderService {
    
    // Separate thread pool for each external service
    @HystrixCommand(
        threadPoolKey = "paymentServicePool",
        threadPoolProperties = {
            @HystrixProperty(name="coreSize", value="10")
        }
    )
    public Payment processPayment() {
        return paymentClient.pay();
    }
}
```

### Pattern Summary Table

| Pattern | Problem Solved | Use When |
|---------|---------------|----------|
| API Gateway | Too many client calls | Always in microservices |
| Service Discovery | Hard-coded service URLs | Dynamic service locations |
| Database per Service | Tight coupling | Data independence needed |
| Saga | Distributed transactions | Need consistency across services |
| CQRS | Complex queries | Read/write patterns differ |
| Event Sourcing | Track all changes | Audit trail needed |
| Circuit Breaker | Cascading failures | External dependencies |
| Bulkhead | Resource exhaustion | Isolate failures |

### Cross Questions

**Q: Which pattern is most important?**
- A: API Gateway and Service Discovery - they're fundamental to microservices.

**Q: Do we need to implement all patterns?**
- A: No! Implement based on your needs. Start simple, add patterns as problems arise.

**Q: What's the difference between Saga and CQRS?**
- A: 
  - Saga: Manages distributed transactions (writes across services)
  - CQRS: Separates read and write operations (optimization)

---

## 7. Circuit Breaker Pattern (Detailed)

### Simple Explanation
Circuit breaker is like an electrical circuit breaker in your house. When there's a problem, it "trips" to prevent damage.

**Real-life analogy:**
```
Normal: Electricity flows → Appliances work
Problem: Short circuit detected → Breaker trips → Prevent fire
After fixing: Reset breaker → Electricity flows again
```

### The Problem

```java
// Order Service calls Payment Service
@Service
public class OrderService {
    
    public void placeOrder(Order order) {
        // What if Payment Service is down or slow?
        Payment payment = paymentService.processPayment(order);
        
        // Problems:
        // 1. Request hangs for 30 seconds (timeout)
        // 2. 1000 users placing orders = 1000 hanging requests
        // 3. Order Service runs out of threads
        // 4. Order Service crashes
        // 5. Entire system down! (Cascading failure)
    }
}
```

### Circuit Breaker Solution

#### Three States

```
1. CLOSED (Normal)
   └─→ Requests flow through normally
   └─→ If failures exceed threshold → OPEN

2. OPEN (Failure detected)
   └─→ Requests fail immediately
   └─→ Don't even call failing service
   └─→ After timeout period → HALF_OPEN

3. HALF_OPEN (Testing)
   └─→ Allow few test requests
   └─→ If successful → CLOSED
   └─→ If failed → OPEN again
```

**Visual Flow:**
```
     Success Rate > 50%
  ┌──────────────────────┐
  │                      │
  ↓                      │
CLOSED ──Failed%>50%──→ OPEN
  ↑                      │
  │                      │ After 60s
  │                      ↓
  └─── Success ───── HALF_OPEN
```

### Implementation with Netflix Hystrix

```java
// Add dependency
// <dependency>
//     <groupId>org.springframework.cloud</groupId>
//     <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
// </dependency>

@SpringBootApplication
@EnableCircuitBreaker
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}

@Service
public class OrderService {
    
    @Autowired
    private PaymentServiceClient paymentClient;
    
    // Circuit Breaker Configuration
    @HystrixCommand(
        fallbackMethod = "paymentFallback",
        commandProperties = {
            // Open circuit if 50% of requests fail
            @HystrixProperty(
                name = "circuitBreaker.errorThresholdPercentage", 
                value = "50"
            ),
            // Minimum requests before opening circuit
            @HystrixProperty(
                name = "circuitBreaker.requestVolumeThreshold", 
                value = "10"
            ),
            // Time circuit stays open before testing (ms)
            @HystrixProperty(
                name = "circuitBreaker.sleepWindowInMilliseconds", 
                value = "60000"
            ),
            // Request timeout (ms)
            @HystrixProperty(
                name = "execution.isolation.thread.timeoutInMilliseconds", 
                value = "3000"
            )
        }
    )
    public Payment processPayment(Order order) {
        // Call Payment Service
        return paymentClient.pay(order);
    }
    
    // Fallback method - called when circuit is OPEN
    public Payment paymentFallback(Order order, Throwable t) {
        // Graceful degradation
        logger.error("Payment service unavailable: " + t.getMessage());
        
        // Return default response
        return Payment.builder()
            .status("PENDING")
            .message("Payment will be processed later")
            .build();
    }
}
```

### Implementation with Resilience4j (Modern Approach)

```java
// Add dependency
// <dependency>
//     <groupId>io.github.resilience4j</groupId>
//     <artifactId>resilience4j-spring-boot2</artifactId>
// </dependency>

// application.yml configuration
/*
resilience4j:
  circuitbreaker:
    instances:
      paymentService:
        registerHealthIndicator: true
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        waitDurationInOpenState: 60s
        failureRateThreshold: 50
        slowCallRateThreshold: 50
        slowCallDurationThreshold: 2s
*/

@Service
public class OrderService {
    
    @Autowired
    private PaymentServiceClient paymentClient;
    
    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    @Retry(name = "paymentService")
    @TimeLimiter(name = "paymentService")
    public Payment processPayment(Order order) {
        return paymentClient.pay(order);
    }
    
    public Payment paymentFallback(Order order, Exception e) {
        return Payment.builder()
            .status("PENDING")
            .message("Payment service temporarily unavailable")
            .build();
    }
}
```

### Real-World Example: Netflix

```
Netflix User watching video:
1. Video Service calls Recommendation Service
2. Recommendation Service is slow/down
3. Circuit Breaker opens
4. Video Service shows: "Continue watching previous shows"
5. User can still watch videos!
6. Circuit Breaker tests Recommendation Service every 60s
7. When recovered, shows personalized recommendations again
```

### Benefits

1. **Prevents Cascading Failures**
   - One service down doesn't crash others

2. **Fast Failure**
   - Fail immediately instead of hanging

3. **Automatic Recovery**
   - Tests service automatically

4. **Resource Protection**
   - Prevents thread exhaustion

5. **Graceful Degradation**
   - Provide fallback responses

### Monitoring Circuit Breaker

```java
// Hystrix Dashboard
@SpringBootApplication
@EnableHystrixDashboard
public class MonitoringApplication {
    // Access at: http://localhost:8080/hystrix
}

// Metrics exposed
{
  "circuitBreaker": "paymentService",
  "status": "OPEN",
  "failureRate": "75%",
  "lastFailureTime": "2024-01-15T10:30:00",
  "nextRetryTime": "2024-01-15T10:31:00"
}
```

### Cross Questions

**Q: What happens when circuit is OPEN?**
- A: Requests fail immediately with fallback response. Service is not called at all.

**Q: How does circuit breaker know when to OPEN?**
- A: Based on configuration:
  - Failure rate threshold (e.g., 50% failures)
  - Minimum number of calls (e.g., 10 calls)
  - Time window (e.g., last 60 seconds)

**Q: Difference between Hystrix and Resilience4j?**
- A: 
  - Hystrix: Netflix library (now in maintenance mode)
  - Resilience4j: Modern alternative, actively maintained, lighter weight

**Q: What if we don't want fallback?**
- A: Circuit breaker can just fail fast without fallback. But fallback provides better user experience.

**Q: Can circuit breaker handle timeouts?**
- A: Yes! If a request takes too long, circuit breaker times it out and counts it as failure.

---

## 8. How to Handle Failures in Microservices

### Simple Explanation
In microservices, services depend on each other. If one fails, we need strategies to prevent complete system failure.

### Failure Handling Strategies

#### 1. **Timeouts**

**Problem:** Service hangs indefinitely

**Solution:** Set maximum wait time

```java
@Configuration
public class RestTemplateConfig {
    
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = 
            new SimpleClientHttpRequestFactory();
        
        factory.setConnectTimeout(3000); // 3 seconds to connect
        factory.setReadTimeout(5000);    // 5 seconds to read response
        
        return new RestTemplate(factory);
    }
}

@Service
public class OrderService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public Product getProduct(Long id) {
        try {
            // If Product Service doesn't respond in 5s, timeout!
            return restTemplate.getForObject(
                "http://product-service/products/" + id, 
                Product.class
            );
        } catch (ResourceAccessException e) {
            // Handle timeout
            logger.error("Product service timeout: " + e.getMessage());
            return getProductFromCache(id); // Fallback
        }
    }
}
```

#### 2. **Retry Mechanism**

**Problem:** Transient failures (network blip)

**Solution:** Retry failed requests

```java
// Using Spring Retry
@EnableRetry
@SpringBootApplication
public class Application {
    // ...
}

@Service
public class OrderService {
    
    @Retryable(
        value = {ServiceUnavailableException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000) // Wait 2s between retries
    )
    public Payment processPayment(Order order) {
        return paymentClient.pay(order);
    }
    
    @Recover
    public Payment recoverPayment(ServiceUnavailableException e, Order order) {
        // After 3 failed attempts
        logger.error("Payment failed after 3 retries");
        return Payment.failed("Service unavailable");
    }
}
```

**Retry with Exponential Backoff:**
```java
@Retryable(
    maxAttempts = 5,
    backoff = @Backoff(
        delay = 1000,      // Start with 1s
        multiplier = 2,    // Double each time
        maxDelay = 10000   // Max 10s
    )
)
// Retry delays: 1s, 2s, 4s, 8s, 10s
```

#### 3. **Circuit Breaker** (Already discussed)

```java
@CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
public Payment processPayment(Order order) {
    return paymentClient.pay(order);
}
```

#### 4. **Fallback Responses**

**Provide degraded functionality instead of error**

```java
@Service
public class RecommendationService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private CacheManager cacheManager;
    
    public List<Product> getRecommendations(Long userId) {
        try {
            // Try to get personalized recommendations
            return restTemplate.getForObject(
                "http://ml-service/recommendations/" + userId,
                List.class
            );
        } catch (Exception e) {
            // Fallback 1: Get from cache
            List<Product> cached = getFromCache(userId);
            if (cached != null) return cached;
            
            // Fallback 2: Return popular products
            return getPopularProducts();
        }
    }
    
    private List<Product> getPopularProducts() {
        // Return bestsellers - always works!
        return productService.getBestsellers();
    }
}
```

#### 5. **Bulkhead Pattern**

**Isolate resources to prevent cascading failures**

```java
@Configuration
public class ThreadPoolConfig {
    
    @Bean(name = "paymentThreadPool")
    public Executor paymentThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("payment-");
        executor.initialize();
        return executor;
    }
    
    @Bean(name = "notificationThreadPool")
    public Executor notificationThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("notification-");
        executor.initialize();
        return executor;
    }
}

@Service
public class OrderService {
    
    @Async("paymentThreadPool")
    public CompletableFuture<Payment> processPayment(Order order) {
        // Uses separate thread pool
        // If payment service is slow, only affects payment threads
        return CompletableFuture.completedFuture(paymentClient.pay(order));
    }
    
    @Async("notificationThreadPool")
    public CompletableFuture<Void> sendNotification(Order order) {
        // Uses different thread pool
        // Notification issues don't affect payments
        notificationClient.send(order);
        return CompletableFuture.completedFuture(null);
    }
}
```

#### 6. **Health Checks**

**Monitor service health**

```java
@RestController
public class HealthController {
    
    @Autowired
    private DatabaseConnection dbConnection;
    
    @Autowired
    private PaymentServiceClient paymentClient;
    
    @GetMapping("/health")
    public ResponseEntity<HealthStatus> health() {
        boolean dbHealthy = dbConnection.isConnected();
        boolean paymentHealthy = paymentClient.isAvailable();
        
        if (dbHealthy && paymentHealthy) {
            return ResponseEntity.ok(new HealthStatus("UP"));
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new HealthStatus("DOWN"));
        }
    }
}

// Spring Boot Actuator - Built-in health checks
@SpringBootApplication
public class Application {
    // Access at: /actuator/health
}
```

#### 7. **Load Balancing**

**Distribute load across multiple instances**

```java
@Configuration
public class LoadBalancerConfig {
    
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

@Service
public class OrderService {
    
    @Autowired
    private RestTemplate restTemplate; // Load-balanced
    
    public Payment processPayment(Order order) {
        // Automatically distributes to available payment service instances
        // payment-service-1, payment-service-2, payment-service-3
        return restTemplate.postForObject(
            "http://payment-service/pay",
            order,
            Payment.class
        );
    }
}
```

#### 8. **Dead Letter Queue (DLQ)**

**Handle failed message processing**

```java
@Configuration
public class RabbitMQConfig {
    
    @Bean
    public Queue orderQueue() {
        Map<String, Object> args = new HashMap<>();
        // If message fails 3 times, send to DLQ
        args.put("x-dead-letter-exchange", "dlx");
        args.put("x-dead-letter-routing-key", "failed.orders");
        
        return new Queue("orders", true, false, false, args);
    }
    
    @Bean
    public Queue deadLetterQueue() {
        return new Queue("failed.orders", true);
    }
}

@Component
public class OrderMessageHandler {
    
    @RabbitListener(queues = "orders")
    public void handleOrder(Order order) {
        try {
            processOrder(order);
        } catch (Exception e) {
            // After retries, message goes to DLQ
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
    
    // Separate handler for failed messages
    @RabbitListener(queues = "failed.orders")
    public void handleFailedOrder(Order order) {
        // Manual investigation or alternative processing
        logger.error("Order failed permanently: " + order.getId());
        notifyAdmins(order);
    }
}
```

#### 9. **Monitoring and Alerting**

```java
// Prometheus metrics
@Component
public class MetricsCollector {
    
    private final Counter failedPayments = Counter.builder("failed_payments")
        .description("Number of failed payment attempts")
        .register(Metrics.globalRegistry);
    
    private final Timer paymentDuration = Timer.builder("payment_duration")
        .description("Payment processing time")
        .register(Metrics.globalRegistry);
    
    public void recordFailedPayment() {
        failedPayments.increment();
        // Alert if > 100 failures in 5 minutes
    }
}
```

### Complete Failure Handling Strategy

```java
@Service
public class RobustOrderService {
    
    // 1. Timeout
    @Autowired
    private RestTemplate restTemplate; // Configured with timeouts
    
    // 2. Retry
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000))
    // 3. Circuit Breaker
    @CircuitBreaker(name = "payment", fallbackMethod = "paymentFallback")
    public Order placeOrder(OrderRequest request) {
        
        // 4. Bulkhead - separate thread pool
        CompletableFuture<Payment> paymentFuture = 
            processPaymentAsync(request);
        
        try {
            Payment payment = paymentFuture.get(5, TimeUnit.SECONDS);
            return createOrder(request, payment);
            
        } catch (TimeoutException e) {
            // 5. Fallback
            return createPendingOrder(request);
        }
    }
    
    // Fallback method
    public Order paymentFallback(OrderRequest request, Exception e) {
        logger.error("Payment failed: " + e.getMessage());
        
        // 6. Graceful degradation
        return Order.builder()
            .status("PAYMENT_PENDING")
            .message("Your order is placed. Payment will be processed shortly.")
            .build();
    }
    
    @Async("paymentThreadPool")
    public CompletableFuture<Payment> processPaymentAsync(OrderRequest request) {
        return CompletableFuture.completedFuture(
            paymentService.pay(request)
        );
    }
}
```

### Cross Questions

**Q: What's the difference between retry and circuit breaker?**
- A:
  - **Retry**: Try again immediately (good for transient failures)
  - **Circuit Breaker**: Stop trying for a period (good for sustained failures)

**Q: How many times should we retry?**
- A: Typically 2-3 times with exponential backoff. Too many retries can overload failing service.

**Q: What if all fallbacks fail?**
- A: Return user-friendly error message and log for investigation. Never expose technical details to users.

**Q: Should every service call have circuit breaker?**
- A: Not necessarily. Use for:
  - External service calls
  - Critical dependencies
  - Services with history of failures

---

## 9. What Happens When a Service Goes Down?

### Scenario 1: No Failure Handling ❌

```
Payment Service crashes at 2 PM

Without protection:
2:00 PM - Payment Service down
2:01 PM - 1000 users trying to pay
         - Each request hangs for 30s
2:02 PM - Order Service runs out of threads
         - Order Service crashes
2:03 PM - Cart Service can't reach Order Service
         - Cart Service crashes
2:05 PM - Entire system down! (Cascading failure)

Impact: Complete outage, revenue loss, angry customers
```

### Scenario 2: With Proper Failure Handling ✅

```
Payment Service crashes at 2 PM

With protection:
2:00 PM - Payment Service down
2:00:05 - Circuit breaker detects failures
2:00:06 - Circuit opens, fallback activated
2:01 PM - Users get message: "Payment pending, order confirmed"
         - Orders saved with status: PAYMENT_PENDING
         - Users can continue browsing
2:10 PM - Payment Service restored
2:10:30 - Circuit closes, normal operation resumed
2:15 PM - Pending payments processed

Impact: Minimal - users slightly inconvenienced but system working
```

### What Happens in Detail

#### 1. **Service Discovery Updates**

```
┌─────────────────┐
│Service Discovery│
│    (Eureka)     │
└────────┬────────┘
         │
    ┌────┴─────┬──────┬────────┐
    ↓          ↓      ↓        ↓
 User Svc   Order  Payment  Product
           Service   Svc      Service
                      ❌ (DOWN)

What happens:
1. Payment Service stops sending heartbeat
2. After 30s, Eureka marks it as DOWN
3. Eureka stops routing requests to it
4. Other services notified: Payment Service unavailable
```

#### 2. **Load Balancer Removes Instance**

```
Before:
API Gateway → Load Balancer → [Payment-1, Payment-2, Payment-3]

After Payment-2 crashes:
API Gateway → Load Balancer → [Payment-1, ❌, Payment-3]
                                     ↓
              Routes only to: [Payment-1, Payment-3]
```

#### 3. **Circuit Breaker Activates**

```java
Time 0s:  Payment Service working normally
          Circuit: CLOSED

Time 30s: Payment Service crashes
          Requests start failing

Time 60s: 10 consecutive failures detected
          Circuit: OPEN
          Fallback activated

Time 90s: Users get fallback response immediately
          No more timeouts!

Time 5m:  Circuit: HALF_OPEN
          Test request sent

Time 5m:  Payment Service back up
          Test succeeds
          Circuit: CLOSED
          Normal operation
```

#### 4. **Dependent Services React**

```java
@Service
public class OrderService {
    
    @CircuitBreaker(name = "payment", fallbackMethod = "paymentFallback")
    public Order placeOrder(OrderRequest request) {
        // Try to process payment
        Payment payment = paymentService.pay(request);
        return createOrder(request, payment, "CONFIRMED");
    }
    
    // Fallback when Payment Service is down
    public Order paymentFallback(OrderRequest request, Exception e) {
        logger.warn("Payment service down, creating pending order");
        
        // Save order with pending status
        Order order = createOrder(request, null, "PAYMENT_PENDING");
        
        // Queue for later processing
        messageQueue.send(new PaymentRetryMessage(order.getId()));
        
        // User gets confirmation
        return order;
    }
}

@Component
public class PaymentRetryWorker {
    
    @Scheduled(fixedRate = 60000) // Every minute
    public void retryPendingPayments() {
        List<Order> pending = orderRepo.findByStatus("PAYMENT_PENDING");
        
        for (Order order : pending) {
            try {
                if (isPaymentServiceUp()) {
                    Payment payment = paymentService.pay(order);
                    order.setStatus("CONFIRMED");
                    orderRepo.save(order);
                }
            } catch (Exception e) {
                logger.warn("Payment still unavailable: " + e.getMessage());
            }
        }
    }
}
```

### Real-World Example: Amazon Prime Day

```
Scenario: Inventory Service crashes during peak traffic

What Amazon does:
1. Circuit breaker opens immediately
2. Users can still:
   - Browse products
   - Add to cart
   - Place orders
3. Inventory checks skipped temporarily
4. Orders marked as "Processing"
5. When Inventory Service recovers:
   - Verify stock availability
   - Cancel orders if out of stock
   - Notify users

Result: System stays up, revenue continues
```

### Impact Levels

#### Level 1: Non-Critical Service Down
```
Example: Recommendation Service crashes

Impact:
- Users see generic recommendations
- Core functionality works
- No revenue impact
- Fix during business hours
```

#### Level 2: Important Service Down
```
Example: Search Service crashes

Impact:
- Users can't search
- Can browse categories
- Can access wishlist/cart
- Moderate revenue impact
- Fix immediately
```

#### Level 3: Critical Service Down
```
Example: Payment Service crashes

Impact:
- Can't process payments
- Orders saved as pending
- Major revenue impact
- All hands on deck!
```

#### Level 4: Database/Core Service Down
```
Example: Order Database crashes

Impact:
- Can't save new orders
- Existing orders still visible
- Use fallback database
- Critical incident
```

### Prevention Strategies

#### 1. **Multiple Instances**
```
Don't run 1 instance of critical services
Run 3-5 instances in different availability zones

Payment-Service-1 (Zone A) ✅
Payment-Service-2 (Zone B) ✅
Payment-Service-3 (Zone C) ❌ (One crashes, two still working)
```

#### 2. **Health Monitoring**
```java
@Component
public class ServiceMonitor {
    
    @Scheduled(fixedRate = 10000) // Every 10 seconds
    public void checkServiceHealth() {
        boolean paymentHealthy = checkPayment();
        boolean orderHealthy = checkOrder();
        boolean inventoryHealthy = checkInventory();
        
        if (!paymentHealthy) {
            alertOps("Payment Service Down!");
            activateBackup();
        }
    }
}
```

#### 3. **Database Replication**
```
Primary DB (Write) ───→ Replica 1 (Read)
                   ───→ Replica 2 (Read)
                   ───→ Replica 3 (Read)

If Primary fails → Promote Replica to Primary
```

#### 4. **Graceful Degradation**
```
Full Feature Set (All services up):
- Personalized recommendations
- Real-time inventory
- Instant payment processing
- Detailed analytics

Degraded Mode (Some services down):
- Generic recommendations
- Estimated inventory
- Pending payment processing
- Basic tracking

Minimum Viable (Critical services only):
- Basic browsing
- Cart functionality
- Order placement (pending)
- No analytics
```

### Cross Questions

**Q: How quickly should we detect a service is down?**
- A: Within 10-30 seconds using health checks and heartbeats.

**Q: Should we automatically restart crashed services?**
- A: Yes, use container orchestration (Kubernetes) for auto-restart and self-healing.

**Q: What if database goes down?**
- A: 
  - Use database replication (promote replica to primary)
  - Cache frequently accessed data
  - Queue write operations
  - Implement eventual consistency

**Q: How to prioritize which services to fix first?**
- A: Based on impact:
  1. Critical path services (payment, order)
  2. High traffic services
  3. User-facing services
  4. Backend/analytics services

---

## 10. E-Commerce System: Practical Microservices Design

### Business Requirements

```
Fashion E-commerce Platform:
✓ User login & profile management
✓ Product catalog (clothing, accessories)
✓ Wishlist management
✓ Shopping cart
✓ Checkout & payment
✓ Order tracking
✓ Notifications (email/SMS)
```

### Step 1: Identify Microservices

#### Service Breakdown

```
1. User Service
   - User registration/login
   - Profile management
   - Address management
   - Authentication/Authorization

2. Product Service
   - Product catalog
   - Product search
   - Product details
   - Categories & filters

3. Wishlist Service
   - Add/remove wishlist items
   - View wishlist
   - Share wishlist

4. Cart Service
   - Add/remove cart items
   - Update quantities
   - Calculate totals
   - Apply coupons

5. Order Service
   - Place orders
   - Order history
   - Order status tracking
   - Order management

6. Payment Service
   - Process payments
   - Payment gateway integration
   - Refunds
   - Payment history

7. Inventory Service
   - Stock management
   - Real-time availability
   - Warehouse management

8. Notification Service
   - Email notifications
   - SMS notifications
   - Push notifications

9. Review Service
   - Product reviews
   - Ratings
   - Comments
```

### Step 2: System Architecture

```
                    ┌─────────────────┐
                    │  API Gateway    │
                    │  (Port: 8080)   │
                    └────────┬────────┘
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
        ↓                    ↓                    ↓
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│ User Service │    │Product Service│    │Cart Service  │
│  (Port 8081) │    │  (Port 8082)  │    │ (Port 8083)  │
│   + User DB  │    │  + Product DB │    │  + Cart DB   │
└──────────────┘    └──────────────┘    └──────────────┘
        │                    │                    │
        └────────────────────┼────────────────────┘
                             │
        ┌────────────────────┼────────────────────┐
        ↓                    ↓                    ↓
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│Order Service │    │Payment Service│   │Wishlist Svc  │
│  (Port 8084) │    │  (Port 8085)  │    │ (Port 8086)  │
│  + Order DB  │    │  + Payment DB │    │ + Wishlist DB│
└──────────────┘    └──────────────┘    └──────────────┘
        │                    │
        └────────────────────┤
                             ↓
                    ┌──────────────┐
                    │ Notification │
                    │   Service    │
                    │  (Port 8087) │
                    └──────────────┘
```

### Step 3: Database Design

#### User Service Database
```sql
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE addresses (
    address_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    address_type VARCHAR(20), -- SHIPPING, BILLING
    street VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    zip_code VARCHAR(20),
    country VARCHAR(100),
    is_default BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```

#### Product Service Database
```sql
CREATE TABLE products (
    product_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(100),
    brand VARCHAR(100),
    price DECIMAL(10, 2),
    discount_percentage DECIMAL(5, 2),
    image_url VARCHAR(500),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE product_variants (
    variant_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT,
    size VARCHAR(10),
    color VARCHAR(50),
    sku VARCHAR(100) UNIQUE,
    stock_quantity INT,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
```

### Step 4: Complete Implementation

#### 1. User Service

**User Model:**
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    private String firstName;
    private String lastName;
    private String phone;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    // Getters, Setters, Constructors
}

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Enumerated(EnumType.STRING)
    private AddressType addressType; // SHIPPING, BILLING
    
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private Boolean isDefault = false;
    
    // Getters, Setters
}
```

**User Controller:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    // 1. User Registration
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegistrationRequest request) {
        User user = userService.registerUser(request);
        String token = jwtTokenProvider.generateToken(user.getEmail());
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new UserResponse(user, token));
    }
    
    // 2. User Login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = userService.authenticate(request.getEmail(), request.getPassword());
        String token = jwtTokenProvider.generateToken(user.getEmail());
        
        return ResponseEntity.ok(new LoginResponse(user, token));
    }
    
    // 3. Get User Profile
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
    
    // 4. Update User Profile
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateRequest request) {
        User updatedUser = userService.updateUser(userId, request);
        return ResponseEntity.ok(updatedUser);
    }
    
    // 5. Add Address
    @PostMapping("/{userId}/addresses")
    public ResponseEntity<Address> addAddress(
            @PathVariable Long userId,
            @Valid @RequestBody AddressRequest request) {
        Address address = userService.addAddress(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }
    
    // 6. Get User Addresses
    @GetMapping("/{userId}/addresses")
    public ResponseEntity<List<Address>> getAddresses(@PathVariable Long userId) {
        List<Address> addresses = userService.getUserAddresses(userId);
        return ResponseEntity.ok(addresses);
    }
}
```

**User Service:**
```java
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User registerUser(UserRegistrationRequest request) {
        // Check if email exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }
        
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        
        return userRepository.save(user);
    }
    
    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        
        return user;
    }
    
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    
    public Address addAddress(Long userId, AddressRequest request) {
        User user = getUserById(userId);
        
        Address address = new Address();
        address.setUser(user);
        address.setAddressType(request.getAddressType());
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setZipCode(request.getZipCode());
        address.setCountry(request.getCountry());
        address.setIsDefault(request.getIsDefault());
        
        return addressRepository.save(address);
    }
}
```

#### 2. Product Service

**Product Controller:**
```java
@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    // 1. Get all products with pagination
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String category) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products;
        
        if (category != null) {
            products = productService.getProductsByCategory(category, pageable);
        } else {
            products = productService.getAllProducts(pageable);
        }
        
        return ResponseEntity.ok(products);
    }
    
    // 2. Get product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> getProductById(@PathVariable Long productId) {
        ProductDetailResponse product = productService.getProductWithDetails(productId);
        return ResponseEntity.ok(product);
    }
    
    // 3. Search products
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String query) {
        List<Product> products = productService.searchProducts(query);
        return ResponseEntity.ok(products);
    }
    
    // 4. Get products by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable String category) {
        List<Product> products = productService.getByCategory(category);
        return ResponseEntity.ok(products);
    }
    
    // 5. Get product variants
    @GetMapping("/{productId}/variants")
    public ResponseEntity<List<ProductVariant>> getProductVariants(@PathVariable Long productId) {
        List<ProductVariant> variants = productService.getVariants(productId);
        return ResponseEntity.ok(variants);
    }
}
```

#### 3. Cart Service

**Cart Model:**
```java
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    
    @Column(nullable = false)
    private Long userId;
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();
    
    private Double totalAmount = 0.0;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // Methods to calculate totals
    public void calculateTotal() {
        this.totalAmount = items.stream()
            .mapToDouble(item -> item.getPrice() * item.getQuantity())
            .sum();
    }
}

@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;
    
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    
    private Long productId;
    private Long variantId;
    private String productName;
    private String size;
    private String color;
    private Double price;
    private Integer quantity;
    private String imageUrl;
    
    // Getters, Setters
}
```

**Cart Controller:**
```java
@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    // 1. Get user cart
    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResponse> getUserCart(@PathVariable Long userId) {
        Cart cart = cartService.getUserCart(userId);
        return ResponseEntity.ok(new CartResponse(cart));
    }
    
    // 2. Add item to cart
    @PostMapping("/user/{userId}/items")
    public ResponseEntity<Cart> addToCart(
            @PathVariable Long userId,
            @Valid @RequestBody AddToCartRequest request) {
        Cart cart = cartService.addItem(userId, request);
        return ResponseEntity.ok(cart);
    }
    
    // 3. Update cart item quantity
    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<Cart> updateQuantity(
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity) {
        Cart cart = cartService.updateItemQuantity(cartItemId, quantity);
        return ResponseEntity.ok(cart);
    }
    
    // 4. Remove item from cart
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long cartItemId) {
        cartService.removeItem(cartItemId);
        return ResponseEntity.noContent().build();
    }
    
    // 5. Clear cart
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
    
    // 6. Apply coupon
    @PostMapping("/user/{userId}/coupon")
    public ResponseEntity<Cart> applyCoupon(
            @PathVariable Long userId,
            @RequestParam String couponCode) {
        Cart cart = cartService.applyCoupon(userId, couponCode);
        return ResponseEntity.ok(cart);
    }
}
```

**Cart Service:**
```java
@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private RestTemplate restTemplate; // For calling Product Service
    
    public Cart getUserCart(Long userId) {
        return cartRepository.findByUserId(userId)
            .orElseGet(() -> createNewCart(userId));
    }
    
    public Cart addItem(Long userId, AddToCartRequest request) {
        Cart cart = getUserCart(userId);
        
        // Call Product Service to get product details
        ProductResponse product = restTemplate.getForObject(
            "http://product-service/api/products/" + request.getProductId(),
            ProductResponse.class
        );
        
        // Check if item already exists
        Optional<CartItem> existing = cart.getItems().stream()
            .filter(item -> item.getProductId().equals(request.getProductId()) &&
                           item.getVariantId().equals(request.getVariantId()))
            .findFirst();
        
        if (existing.isPresent()) {
            // Update quantity
            existing.get().setQuantity(
                existing.get().getQuantity() + request.getQuantity()
            );
        } else {
            // Add new item
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProductId(request.getProductId());
            newItem.setVariantId(request.getVariantId());
            newItem.setProductName(product.getName());
            newItem.setPrice(product.getPrice());
            newItem.setQuantity(request.getQuantity());
            newItem.setSize(request.getSize());
            newItem.setColor(request.getColor());
            newItem.setImageUrl(product.getImageUrl());
            
            cart.getItems().add(newItem);
        }
        
        cart.calculateTotal();
        return cartRepository.save(cart);
    }
    
    public Cart updateItemQuantity(Long cartItemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));
        
        if (quantity <= 0) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }
        
        Cart cart = item.getCart();
        cart.calculateTotal();
        return cartRepository.save(cart);
    }
    
    public void removeItem(Long cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));
        
        Cart cart = item.getCart();
        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        
        cart.calculateTotal();
        cartRepository.save(cart);
    }
}
```

#### 4. Wishlist Service

**Wishlist Controller:**
```java
@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    
    @Autowired
    private WishlistService wishlistService;
    
    // 1. Get user wishlist
    @GetMapping("/user/{userId}")
    public ResponseEntity<WishlistResponse> getUserWishlist(@PathVariable Long userId) {
        Wishlist wishlist = wishlistService.getUserWishlist(userId);
        return ResponseEntity.ok(new WishlistResponse(wishlist));
    }
    
    // 2. Add item to wishlist
    @PostMapping("/user/{userId}/items")
    public ResponseEntity<Wishlist> addToWishlist(
            @PathVariable Long userId,
            @RequestParam Long productId) {
        Wishlist wishlist = wishlistService.addItem(userId, productId);
        return ResponseEntity.ok(wishlist);
    }
    
    // 3. Remove item from wishlist
    @DeleteMapping("/user/{userId}/items/{productId}")
    public ResponseEntity<Void> removeFromWishlist(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        wishlistService.removeItem(userId, productId);
        return ResponseEntity.noContent().build();
    }
    
    // 4. Check if product is in wishlist
    @GetMapping("/user/{userId}/items/{productId}/exists")
    public ResponseEntity<Boolean> isInWishlist(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        boolean exists = wishlistService.isProductInWishlist(userId, productId);
        return ResponseEntity.ok(exists);
    }
}
```

#### 5. Order Service (Most Complex)

**Order Model:**
```java
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    
    private String orderNumber; // Unique order identifier
    private Long userId;
    private Long addressId;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
    
    private Double subtotal;
    private Double tax;
    private Double shippingCharges;
    private Double discount;
    private Double totalAmount;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    private String paymentMethod;
    private String paymentId;
    
    @CreationTimestamp
    private LocalDateTime orderDate;
    
    private LocalDateTime deliveryDate;
    
    // Getters, Setters
}

public enum OrderStatus {
    PENDING,
    PAYMENT_PENDING,
    CONFIRMED,
    PROCESSING,
    SHIPPED,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED,
    RETURNED
}
```

**Order Controller:**
```java
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    // 1. Place order (checkout)
    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest request) {
        Order order = orderService.placeOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new OrderResponse(order));
    }
    
    // 2. Get order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> getOrder(@PathVariable Long orderId) {
        OrderDetailResponse order = orderService.getOrderWithDetails(orderId);
        return ResponseEntity.ok(order);
    }
    
    // 3. Get user orders
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId) {
        List<Order> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }
    
    // 4. Update order status
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {
        Order order = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(order);
    }
    
    // 5. Cancel order
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId) {
        Order order = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(order);
    }
    
    // 6. Track order
    @GetMapping("/{orderId}/track")
    public ResponseEntity<OrderTrackingResponse> trackOrder(@PathVariable Long orderId) {
        OrderTrackingResponse tracking = orderService.getOrderTracking(orderId);
        return ResponseEntity.ok(tracking);
    }
}
```

**Order Service with Microservice Communication:**
```java
@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;
    
    @Transactional
    @CircuitBreaker(name = "orderService", fallbackMethod = "placeOrderFallback")
    public Order placeOrder(OrderRequest request) {
        // 1. Validate user (call User Service)
        User user = restTemplate.getForObject(
            "http://user-service/api/users/" + request.getUserId(),
            User.class
        );
        
        // 2. Get cart items (call Cart Service)
        Cart cart = restTemplate.getForObject(
            "http://cart-service/api/cart/user/" + request.getUserId(),
            Cart.class
        );
        
        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException("Cannot place order with empty cart");
        }
        
        // 3. Check inventory (call Inventory Service)
        InventoryCheckRequest inventoryRequest = new InventoryCheckRequest(cart.getItems());
        Boolean stockAvailable = restTemplate.postForObject(
            "http://inventory-service/api/inventory/check",
            inventoryRequest,
            Boolean.class
        );
        
        if (!stockAvailable) {
            throw new InsufficientStockException("Some items are out of stock");
        }
        
        // 4. Create order
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setUserId(request.getUserId());
        order.setAddressId(request.getAddressId());
        order.setSubtotal(cart.getTotalAmount());
        order.setTax(calculateTax(cart.getTotalAmount()));
        order.setShippingCharges(calculateShipping());
        order.setTotalAmount(order.getSubtotal() + order.getTax() + order.getShippingCharges());
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentMethod(request.getPaymentMethod());
        
        // Convert cart items to order items
        List<OrderItem> orderItems = cart.getItems().stream()
            .map(cartItem -> convertToOrderItem(cartItem, order))
            .collect(Collectors.toList());
        order.setItems(orderItems);
        
        Order savedOrder = orderRepository.save(order);
        
        // 5. Process payment (call Payment Service)
        PaymentRequest paymentRequest = new PaymentRequest(
            savedOrder.getOrderId(),
            savedOrder.getTotalAmount(),
            request.getPaymentMethod(),
            request.getPaymentDetails()
        );
        
        PaymentResponse payment = restTemplate.postForObject(
            "http://payment-service/api/payments/process",
            paymentRequest,
            PaymentResponse.class
        );
        
        if (payment.getStatus().equals("SUCCESS")) {
            savedOrder.setStatus(OrderStatus.CONFIRMED);
            savedOrder.setPaymentId(payment.getPaymentId());
            
            // 6. Update inventory (reduce stock)
            restTemplate.postForObject(
                "http://inventory-service/api/inventory/reduce",
                inventoryRequest,
                Void.class
            );
            
            // 7. Clear cart
            restTemplate.delete("http://cart-service/api/cart/user/" + request.getUserId());
            
            // 8. Send notification (publish event to Kafka)
            kafkaTemplate.send("order-events", 
                new OrderEvent(savedOrder.getOrderId(), "ORDER_PLACED", user.getEmail())
            );
            
        } else {
            savedOrder.setStatus(OrderStatus.PAYMENT_PENDING);
        }
        
        return orderRepository.save(savedOrder);
    }
    
    // Fallback method when services are down
    public Order placeOrderFallback(OrderRequest request, Exception e) {
        logger.error("Failed to place order: " + e.getMessage());
        
        // Create pending order
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setUserId(request.getUserId());
        order.setStatus(OrderStatus.PAYMENT_PENDING);
        order.setTotalAmount(0.0);
        
        Order savedOrder = orderRepository.save(order);
        
        // Queue for later processing
        kafkaTemplate.send("order-retry-queue", 
            new OrderRetryEvent(savedOrder.getOrderId(), request)
        );
        
        return savedOrder;
    }
    
    private String generateOrderNumber() {
        return "ORD" + System.currentTimeMillis();
    }
}
```

### Step 5: Service Communication

#### Method 1: Synchronous (REST API)

```java
@Configuration
public class RestTemplateConfig {
    
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = 
            new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(5000);
        
        return new RestTemplate(factory);
    }
}

// Usage in Order Service
@Service
public class OrderService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public Order placeOrder(OrderRequest request) {
        // Call User Service
        User user = restTemplate.getForObject(
            "http://user-service/api/users/" + request.getUserId(),
            User.class
        );
        
        // Call Product Service
        Product product = restTemplate.getForObject(
            "http://product-service/api/products/" + request.getProductId(),
            Product.class
        );
        
        // Process order
        return createOrder(user, product);
    }
}
```

#### Method 2: Asynchronous (Message Queue - Kafka)

```java
// Order Service - Producer
@Service
public class OrderService {
    
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;
    
    public Order placeOrder(OrderRequest request) {
        Order order = createOrder(request);
        
        // Publish event to Kafka
        OrderEvent event = new OrderEvent(
            order.getOrderId(),
            order.getUserId(),
            order.getTotalAmount(),
            "ORDER_PLACED"
        );
        
        kafkaTemplate.send("order-events", event);
        
        return order;
    }
}

// Notification Service - Consumer
@Service
public class NotificationConsumer {
    
    @Autowired
    private EmailService emailService;
    
    @KafkaListener(topics = "order-events", groupId = "notification-group")
    public void handleOrderEvent(OrderEvent event) {
        if (event.getEventType().equals("ORDER_PLACED")) {
            // Send confirmation email
            emailService.sendOrderConfirmation(
                event.getUserId(),
                event.getOrderId()
            );
        }
    }
}

// Inventory Service - Consumer
@Service
public class InventoryConsumer {
    
    @Autowired
    private InventoryService inventoryService;
    
    @KafkaListener(topics = "order-events", groupId = "inventory-group")
    public void handleOrderEvent(OrderEvent event) {
        if (event.getEventType().equals("ORDER_PLACED")) {
            // Reduce stock
            inventoryService.reduceStock(event.getOrderId());
        }
    }
}
```

#### Method 3: API Gateway Pattern

```java
@RestController
@RequestMapping("/api/gateway")
public class APIGatewayController {
    
    @Autowired
    private RestTemplate restTemplate;
    
    // Single endpoint for complete checkout flow
    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponse> checkout(@RequestBody CheckoutRequest request) {
        
        // 1. Get user details
        User user = restTemplate.getForObject(
            "http://user-service/api/users/" + request.getUserId(),
            User.class
        );
        
        // 2. Get cart
        Cart cart = restTemplate.getForObject(
            "http://cart-service/api/cart/user/" + request.getUserId(),
            Cart.class
        );
        
        // 3. Create order
        OrderRequest orderRequest = new OrderRequest(request.getUserId(), cart, request.getAddressId());
        Order order = restTemplate.postForObject(
            "http://order-service/api/orders",
            orderRequest,
            Order.class
        );
        
        // 4. Process payment
        PaymentRequest paymentRequest = new PaymentRequest(order.getOrderId(), order.getTotalAmount());
        Payment payment = restTemplate.postForObject(
            "http://payment-service/api/payments/process",
            paymentRequest,
            Payment.class
        );
        
        // 5. Return combined response
        return ResponseEntity.ok(new CheckoutResponse(user, order, payment));
    }
}
```

### Communication Flow Diagram

```
Checkout Flow:
1. User clicks "Place Order"
2. API Gateway receives request
3. Gateway → User Service (get user details)
4. Gateway → Cart Service (get cart items)
5. Gateway → Product Service (validate products)
6. Gateway → Inventory Service (check stock)
7. Gateway → Order Service (create order)
8. Order Service → Payment Service (process payment)
9. Order Service → Kafka (publish ORDER_PLACED event)
10. Notification Service ← Kafka (send email)
11. Inventory Service ← Kafka (reduce stock)
12. Gateway returns response to user
```

### Step 6: Configuration Files

**application.properties for Order Service:**
```properties
# Server Configuration
server.port=8084
spring.application.name=order-service

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/order_db
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update

# Eureka Client (Service Discovery)
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

# Resilience4j Circuit Breaker
resilience4j.circuitbreaker.instances.orderService.slidingWindowSize=10
resilience4j.circuitbreaker.instances.orderService.failureRateThreshold=50
resilience4j.circuitbreaker.instances.orderService.waitDurationInOpenState=60s

# Kafka Configuration
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer

# RestTemplate Timeout
rest.template.connect-timeout=3000
rest.template.read-timeout=5000
```

### Cross Questions

**Q: Why did you separate Wishlist and Cart?**
- A: Different business logic:
  - Cart is temporary (session-based)
  - Wishlist is permanent (saved items)
  - Cart has quantity, Wishlist doesn't
  - Different scalability needs

**Q: How do you handle when Product Service is down during checkout?**
- A:
  1. Cache product data in Order Service
  2. Use circuit breaker with fallback
  3. Store pending orders, process when service is up
  4. Show user: "Order placed, will be confirmed shortly"

**Q: What if payment succeeds but inventory update fails?**
- A: Use Saga pattern:
  - Compensating transaction: Refund payment
  - Or use eventual consistency: Retry inventory update
  - Queue failed operations for manual review

**Q: How to maintain data consistency across services?**
- A:
  - Use distributed transactions (Saga pattern)
  - Event-driven architecture
  - Idempotent operations
  - Compensating transactions

**Q: How many databases do you need?**
- A: One per service (8 databases):
  - User DB, Product DB, Cart DB, Wishlist DB
  - Order DB, Payment DB, Inventory DB, Notification DB

**Q: What if User Service needs Order data?**
- A:
  - User Service calls Order Service API
  - Or maintain denormalized data
  - Or use event sourcing (User Service listens to order events)

**Q: How do you test this system?**
- A:
  1. Unit tests for each service
  2. Integration tests for service pairs
  3. Contract tests for APIs
  4. End-to-end tests for complete flows
  5. Use test containers for dependencies

---

## Interview Tips & Best Practices

### When Explaining Microservices

**1. Start Simple:**
```
"Microservices is like having specialized restaurants instead of one supermarket.
Each restaurant focuses on one cuisine and does it well."
```

**2. Use Real Examples:**
```
"Netflix has 700+ microservices. Each handles one thing:
- Video streaming
- Recommendations
- User profiles
- Billing
etc."
```

**3. Acknowledge Trade-offs:**
```
"Microservices solve scaling and deployment issues but add complexity.
For small apps, monolithic is simpler."
```

### Common Interview Questions

**Q: When would you NOT use microservices?**
- Small applications
- Startups/MVPs
- Limited team size
- Tight deadlines
- Budget constraints

**Q: How do you handle distributed transactions?**
- Saga pattern
- Event sourcing
- CQRS
- Eventual consistency
- Compensating transactions

**Q: How do you monitor microservices?**
- Centralized logging (ELK stack)
- Distributed tracing (Jaeger, Zipkin)
- Metrics (Prometheus, Grafana)
- Health checks
- APM tools

**Q: How do you secure microservices?**
- API Gateway authentication
- JWT tokens
- OAuth 2.0
- Service-to-service authentication
- API rate limiting

### Key Takeaways

1. **Microservices are not always the answer** - Use when benefits outweigh complexity
2. **Start with business domains** - Each service should represent a business capability
3. **Design for failure** - Use circuit breakers, timeouts, retries
4. **Invest in infrastructure** - Monitoring, logging, tracing are essential
5. **Database per service** - Each service owns its data
6. **Async communication** - Use message queues for non-critical flows
7. **API Gateway** - Single entry point for clients
8. **Service Discovery** - Dynamic service location
9. **Gradual migration** - Don't rewrite everything at once
10. **Team autonomy** - Each team owns a service

---

## Quick Revision Checklist

**Core Concepts:**
- ✓ Microservices definition
- ✓ Monolithic vs Microservices
- ✓ Advantages and disadvantages
- ✓ When to use which architecture

**Design Patterns:**
- ✓ API Gateway
- ✓ Service Discovery
- ✓ Circuit Breaker
- ✓ Saga
- ✓ CQRS
- ✓ Database per Service

**Failure Handling:**
- ✓ Timeouts
- ✓ Retries
- ✓ Circuit breakers
- ✓ Fallbacks
- ✓ Bulkhead
- ✓ Health checks

**Communication:**
- ✓ Synchronous (REST)
- ✓ Asynchronous (Messaging)
- ✓ Event-driven
- ✓ API Gateway

**E-commerce Design:**
- ✓ Service identification
- ✓ Database design
- ✓ API endpoints
- ✓ Service communication
- ✓ Failure scenarios

Good luck with your interview! 🚀
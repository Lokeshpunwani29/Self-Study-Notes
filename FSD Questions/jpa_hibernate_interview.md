# Spring Data JPA & Hibernate - Interview Guide

## 1. What is JPA and what are the annotations available? Give me annotations names.

**Simple Explanation:**
JPA is like a translator between your Java objects and database tables. Instead of writing SQL queries manually, you just use Java objects, and JPA handles the database operations for you.

**Interview Answer:**
JPA (Java Persistence API) is a specification for managing relational data in Java applications. It provides a standard way to map Java objects to database tables and perform CRUD operations without writing SQL.

**Common JPA Annotations:**

**Entity-Level Annotations:**
- `@Entity` - Marks a class as a database entity
- `@Table` - Specifies the table name
- `@Id` - Marks the primary key field
- `@GeneratedValue` - Defines primary key generation strategy
- `@Column` - Maps a field to a database column
- `@Transient` - Excludes a field from database mapping

**Relationship Annotations:**
- `@OneToOne` - One-to-one relationship
- `@OneToMany` - One-to-many relationship
- `@ManyToOne` - Many-to-one relationship
- `@ManyToMany` - Many-to-many relationship
- `@JoinColumn` - Specifies the foreign key column
- `@JoinTable` - Specifies the join table for many-to-many

**Other Important Annotations:**
- `@Temporal` - Maps date/time fields
- `@Enumerated` - Maps enum fields
- `@Lob` - Maps large objects (BLOB/CLOB)
- `@Embedded` and `@Embeddable` - For embedded objects
- `@Version` - For optimistic locking
- `@CreatedDate` and `@LastModifiedDate` - Audit fields

**Possible Cross Questions:**
- *What's the difference between @Id and @GeneratedValue?*
  - @Id marks a field as primary key, @GeneratedValue defines how the ID should be generated (AUTO, IDENTITY, SEQUENCE, TABLE)
- *When would you use @Transient?*
  - For fields that should not be persisted to the database, like calculated fields or temporary variables

---

## 2. What is JPA? What is ORM used in it?

**Simple Explanation:**
Imagine you're working with both Java objects and database tables. ORM is like a smart assistant that automatically converts your Java objects into database rows and vice versa, so you don't have to write conversion code manually.

**Interview Answer:**
**JPA (Java Persistence API)** is a Java specification that defines how to manage relational data in Java applications using Object-Relational Mapping.

**ORM (Object-Relational Mapping)** is a programming technique that creates a "virtual object database" that can be used from within your programming language. It maps:
- Java Classes → Database Tables
- Class Fields → Table Columns
- Objects → Table Rows
- Relationships → Foreign Keys

**Benefits of ORM:**
- Reduces boilerplate code
- Database independence (switch databases without changing code)
- Automatic SQL generation
- Built-in caching mechanisms
- Handles complex relationships automatically

**Popular ORM implementations of JPA:**
- Hibernate (most popular)
- EclipseLink
- OpenJPA

**Example:**
```java
// Instead of writing SQL:
// SELECT * FROM users WHERE id = ?

// You write:
User user = userRepository.findById(1);
```

**Possible Cross Questions:**
- *What problems does ORM solve?*
  - Eliminates repetitive JDBC code, handles object-relational impedance mismatch, provides database independence, and offers better maintainability
- *What is impedance mismatch?*
  - The difference between object-oriented concepts (inheritance, polymorphism) and relational database concepts (tables, foreign keys)

---

## 3. Difference between JPA and JDBC

**Simple Explanation:**
JDBC is like driving a manual car where you control everything (gear shifts, clutch, etc.). JPA is like driving an automatic car where many things are handled for you automatically.

**Interview Answer:**

| Aspect | JDBC | JPA |
|--------|------|-----|
| **Type** | Low-level API | High-level specification |
| **Code Amount** | More boilerplate code | Less code, more annotations |
| **SQL** | Write SQL manually | Auto-generates SQL |
| **Object Mapping** | Manual mapping required | Automatic ORM mapping |
| **Database Independence** | Database-specific SQL | Database-independent |
| **Caching** | No built-in caching | First & second level caching |
| **Relationships** | Manual handling | Automatic relationship handling |
| **Performance** | Fast but requires more code | Optimized with proper configuration |
| **Learning Curve** | Easier to start | Steeper initially |

**JDBC Example:**
```java
Connection conn = DriverManager.getConnection(url, user, password);
PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
stmt.setInt(1, userId);
ResultSet rs = stmt.executeQuery();
while(rs.next()) {
    User user = new User();
    user.setId(rs.getInt("id"));
    user.setName(rs.getString("name"));
    // ... manual mapping
}
```

**JPA Example:**
```java
User user = userRepository.findById(userId).orElse(null);
```

**When to use what:**
- Use JDBC for simple applications, complex custom queries, or when you need maximum control
- Use JPA for complex applications with many entities and relationships

**Possible Cross Questions:**
- *Can you use both JDBC and JPA together?*
  - Yes, you can use JdbcTemplate for complex queries while using JPA for regular CRUD operations
- *Is JPA slower than JDBC?*
  - Not necessarily. JPA has caching mechanisms and can be optimized. The performance difference is negligible in most applications

---

## 4. Differences between Hibernate and JPA?

**Simple Explanation:**
JPA is like a recipe or specification that says "this is how you should bake a cake." Hibernate is an actual cake-baking method that follows that recipe. JPA defines the rules, Hibernate implements them.

**Interview Answer:**

| Aspect | JPA | Hibernate |
|--------|-----|-----------|
| **Nature** | Specification/Interface | Implementation/Framework |
| **Defined By** | Oracle (part of Jakarta EE) | Hibernate team (Red Hat) |
| **Annotations** | javax.persistence.* | org.hibernate.* |
| **Features** | Standard features only | Additional features beyond JPA |
| **Portability** | Can switch implementations | Vendor-specific features lock you in |
| **Example** | @Entity, @Table | @Formula, @Where |

**Key Points:**
- JPA is a standard, Hibernate is one of its implementations
- Hibernate existed before JPA and influenced its design
- Using JPA annotations makes your code portable across different ORM providers
- Hibernate provides additional features beyond JPA specification

**Code Example:**
```java
// Pure JPA (portable)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

// Hibernate-specific (not portable)
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User {
    // Hibernate's @Formula annotation
    @org.hibernate.annotations.Formula("CONCAT(first_name, ' ', last_name)")
    private String fullName;
}
```

**Possible Cross Questions:**
- *Can you use JPA without Hibernate?*
  - Yes, you can use other implementations like EclipseLink or OpenJPA
- *Should you use Hibernate-specific features?*
  - Only if you're certain you won't switch ORM providers. For maximum portability, stick to JPA standard annotations
- *What are some Hibernate-specific features?*
  - @Formula, @Where, @Filter, @DynamicInsert, @DynamicUpdate, custom user types

---

## 5. What is an Entity and how do you define it?

**Simple Explanation:**
An Entity is like a blueprint for a database table written in Java. Each Entity class represents a table, and each object of that class represents a row in that table.

**Interview Answer:**
An Entity is a lightweight Java class that represents a table in a relational database. Each instance of an entity represents a row in the table.

**How to Define an Entity:**

```java
@Entity
@Table(name = "employees")
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal salary;
    
    @Temporal(TemporalType.DATE)
    private Date joiningDate;
    
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;
    
    @Transient
    private int age; // Not stored in database
    
    // Constructors, Getters, and Setters
    public Employee() {}
    
    public Employee(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    // Getters and setters...
}
```

**Entity Requirements:**
1. Must be annotated with `@Entity`
2. Must have a no-argument constructor (can be protected or public)
3. Must have a primary key marked with `@Id`
4. Class should not be final
5. Persistent fields should not be final

**Generation Strategies:**
- `GenerationType.AUTO` - JPA chooses the strategy
- `GenerationType.IDENTITY` - Database auto-increment
- `GenerationType.SEQUENCE` - Database sequence
- `GenerationType.TABLE` - Separate table for ID generation

**Possible Cross Questions:**
- *Why must an entity have a no-argument constructor?*
  - JPA providers use reflection to instantiate entities when retrieving data from the database
- *What's the difference between @Table and @Entity?*
  - @Entity marks a class as an entity, @Table specifies the table name (optional, defaults to class name)
- *Can you have an entity without @Table annotation?*
  - Yes, the table name will default to the entity class name

---

## 6. What is a Repository in Spring Data JPA?

**Simple Explanation:**
A Repository is like a librarian for your database. Instead of you searching through all the books (data), you just tell the librarian what you want, and they fetch it for you. You don't need to know where or how it's stored.

**Interview Answer:**
A Repository in Spring Data JPA is an interface that provides data access methods for entities. It eliminates boilerplate code by providing pre-defined methods and allowing custom query methods through method naming conventions.

**Benefits:**
- No need to write implementation classes
- Provides common CRUD operations out of the box
- Custom queries through method names
- Reduces boilerplate code by 90%
- Type-safe query methods

**Example:**
```java
// Just declare an interface, Spring provides the implementation
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Pre-defined methods (no need to implement):
    // save(), findAll(), findById(), deleteById(), etc.
    
    // Custom methods - Spring generates queries from method names
    User findByEmail(String email);
    
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
    
    List<User> findByAgeGreaterThan(int age);
    
    List<User> findByLastNameOrderByFirstNameAsc(String lastName);
    
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findUserByEmail(String email);
    
    @Query(value = "SELECT * FROM users WHERE status = ?1", nativeQuery = true)
    List<User> findByStatusNative(String status);
    
    @Modifying
    @Query("UPDATE User u SET u.status = ?2 WHERE u.id = ?1")
    int updateUserStatus(Long id, String status);
}
```

**Method Naming Conventions:**
- `findBy`, `readBy`, `getBy` - Select queries
- `countBy` - Count queries
- `deleteBy`, `removeBy` - Delete queries
- `And`, `Or` - Logical operators
- `GreaterThan`, `LessThan`, `Between` - Comparison
- `Like`, `NotLike`, `StartingWith`, `EndingWith` - Pattern matching
- `OrderBy` - Sorting
- `Top`, `First` - Limiting results

**Possible Cross Questions:**
- *Do you need to write implementation for Repository interfaces?*
  - No, Spring Data JPA creates proxy implementations at runtime
- *What if the method name becomes too long?*
  - Use @Query annotation to write custom JPQL or native SQL queries
- *Can you have custom logic in Repository?*
  - Yes, create a custom repository interface and implementation class

---

## 7. Difference between Entity and Repository

**Simple Explanation:**
Entity is like a form that defines what information you want to store (name, age, address). Repository is like the filing cabinet that helps you store, retrieve, and manage those forms.

**Interview Answer:**

| Aspect | Entity | Repository |
|--------|--------|------------|
| **Purpose** | Represents data structure | Provides data access operations |
| **Type** | Java class with fields | Interface |
| **Role** | Data model | Data access layer |
| **Maps To** | Database table | Collection of entities |
| **Contains** | Data fields & getters/setters | Query methods |
| **Annotation** | @Entity | @Repository (optional) |
| **Example** | User, Product, Order | UserRepository, ProductRepository |

**Analogy:**
Think of a library:
- **Entity** = Book (has properties like title, author, ISBN)
- **Repository** = Librarian (helps you find books, add new books, remove books)

**Code Example:**
```java
// Entity - What data looks like
@Entity
public class Product {
    @Id
    private Long id;
    private String name;
    private Double price;
    // Getters and setters
}

// Repository - How to access that data
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceGreaterThan(Double price);
    Product findByName(String name);
}

// Usage in Service
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
```

**Key Relationship:**
- One Entity can have one Repository
- Repository operates on Entity objects
- Entity is passive (just data), Repository is active (performs operations)

**Possible Cross Questions:**
- *Can you have an Entity without a Repository?*
  - Yes, but you won't have convenient data access methods. You'd need to use EntityManager directly
- *Can one Repository handle multiple Entities?*
  - Not recommended. Follow the pattern of one Repository per Entity for clarity

---

## 8. Write a JPA Repository class for Spring Boot

**Simple Explanation:**
Creating a repository is like creating a menu at a restaurant. Spring Boot gives you a standard menu (basic CRUD operations), and you can add special dishes (custom queries) to it.

**Interview Answer:**

**Complete Example with Entity and Repository:**

```java
// 1. Entity Class
@Entity
@Table(name = "customers")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(length = 15)
    private String phone;
    
    private String city;
    
    private Boolean active = true;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    // Constructors
    public Customer() {}
    
    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    // ... other getters and setters
}

// 2. Repository Interface
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    // Query methods using method naming convention
    Customer findByEmail(String email);
    
    List<Customer> findByCity(String city);
    
    List<Customer> findByActiveTrue();
    
    List<Customer> findByNameContainingIgnoreCase(String name);
    
    List<Customer> findTop5ByOrderByCreatedAtDesc();
    
    Long countByCity(String city);
    
    boolean existsByEmail(String email);
    
    // Custom JPQL queries
    @Query("SELECT c FROM Customer c WHERE c.active = true AND c.city = :city")
    List<Customer> findActiveCustomersByCity(@Param("city") String city);
    
    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Customer> searchByKeyword(@Param("keyword") String keyword);
    
    // Native SQL query
    @Query(value = "SELECT * FROM customers WHERE created_at > :date", nativeQuery = true)
    List<Customer> findCustomersCreatedAfter(@Param("date") LocalDateTime date);
    
    // Update query
    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.active = :status WHERE c.id = :id")
    int updateCustomerStatus(@Param("id") Long id, @Param("status") Boolean status);
    
    // Delete query
    @Modifying
    @Transactional
    @Query("DELETE FROM Customer c WHERE c.active = false AND c.createdAt < :date")
    int deleteInactiveCustomersOlderThan(@Param("date") LocalDateTime date);
    
    // Projection - return only specific fields
    @Query("SELECT new com.example.dto.CustomerDTO(c.id, c.name, c.email) FROM Customer c WHERE c.city = :city")
    List<CustomerDTO> findCustomerDTOsByCity(@Param("city") String city);
    
    // Pagination and Sorting
    Page<Customer> findByCity(String city, Pageable pageable);
    
    List<Customer> findByActive(Boolean active, Sort sort);
}

// 3. Service Class (Usage)
@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    // Create
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    
    // Read
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
    
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
    
    // Update
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = getCustomerById(id);
        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhone(customerDetails.getPhone());
        return customerRepository.save(customer);
    }
    
    // Delete
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
    
    // Custom methods
    public List<Customer> getActiveCustomers() {
        return customerRepository.findByActiveTrue();
    }
    
    public Page<Customer> getCustomersByCity(String city, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return customerRepository.findByCity(city, pageable);
    }
}

// 4. Controller (Optional - REST API)
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id, 
            @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
```

**application.properties:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

**Possible Cross Questions:**
- *What does @Modifying do?*
  - Indicates that a query method modifies data (UPDATE/DELETE). Must be used with @Transactional
- *What's the difference between save() and saveAndFlush()?*
  - save() persists to persistence context, saveAndFlush() immediately flushes to database
- *How do you handle exceptions when entity is not found?*
  - Use Optional: `findById(id).orElseThrow(() -> new EntityNotFoundException())`

---

## 9. What are the different types of mappings in JPA?

**Simple Explanation:**
Mappings are like describing family relationships. Just as you can say "one parent has many children" or "one person has one passport," JPA mappings describe how database tables relate to each other.

**Interview Answer:**

JPA provides four types of relationship mappings:

### **1. @OneToOne**
One entity is associated with exactly one instance of another entity.

**Example: User ↔ UserProfile**
```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private UserProfile profile;
}

@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bio;
    private String website;
    
    @OneToOne(mappedBy = "profile")
    private User user;
}
```

### **2. @OneToMany & @ManyToOne**
One entity is associated with multiple instances of another entity.

**Example: Department ↔ Employees**
```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();
}

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
```

### **3. @ManyToMany**
Multiple instances of one entity are associated with multiple instances of another entity.

**Example: Students ↔ Courses**
```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();
}

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();
}
```

### **4. @Embedded & @Embeddable**
Not a relationship, but embedding one object into another (same table).

**Example: Address embedded in Employee**
```java
@Embeddable
public class Address {
    private String street;
    private String city;
    private String zipCode;
    // Getters and setters
}

@Entity
public class Employee {
    @Id
    private Long id;
    private String name;
    
    @Embedded
    private Address address;
}
// Creates one table 'employee' with columns: id, name, street, city, zipCode
```

**Comparison Table:**

| Mapping Type | Relationship | Example |
|-------------|--------------|---------|
| @OneToOne | 1:1 | User ↔ Passport |
| @ManyToOne | N:1 | Employee → Department |
| @OneToMany | 1:N | Department → Employees |
| @ManyToMany | N:M | Student ↔ Courses |

**Important Attributes:**
- `cascade` - Propagate operations (PERSIST, MERGE, REMOVE, REFRESH, DETACH, ALL)
- `fetch` - LAZY or EAGER loading
- `mappedBy` - Indicates the inverse side of the relationship
- `orphanRemoval` - Delete child when removed from parent

**Possible Cross Questions:**
- *What is mappedBy and why is it used?*
  - mappedBy indicates the inverse/non-owning side of a bidirectional relationship. It prevents duplicate foreign key columns
- *What's the difference between @JoinColumn and @JoinTable?*
  - @JoinColumn is used for OneToOne/ManyToOne (foreign key in entity table). @JoinTable creates a separate join table for ManyToMany
- *What is cascade and when to use CascadeType.ALL?*
  - Cascade propagates operations from parent to child. Use ALL carefully as it includes REMOVE, which might delete related entities unexpectedly

---

## 10. Explain relationships in JPA. Implement Many-to-One relationship

**Simple Explanation:**
A Many-to-One relationship is like many employees working in one department. Many employees (many) belong to one specific department (one).

**Interview Answer:**

A Many-to-One relationship exists when multiple instances of one entity are associated with a single instance of another entity.

**Real-world examples:**
- Many Orders → One Customer
- Many Employees → One Department
- Many Books → One Author
- Many Comments → One Post

**Complete Implementation:**

```java
// Parent Entity (One side)
@Entity
@Table(name = "departments")
public class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    private String location;
    
    // Bidirectional relationship (optional)
    // mappedBy refers to the field name in Employee class
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();
    
    // Helper methods for bidirectional relationship
    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }
    
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setDepartment(null);
    }
    
    // Constructors
    public Department() {}
    
    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }
}

// Child Entity (Many side)
@Entity
@Table(name = "employees")
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true)
    private String email;
    
    private Double salary;
    
    // Many employees can belong to one department
    // This is the owning side of the relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    
    // Constructors
    public Employee() {}
    
    public Employee(String name, String email, Double salary) {
        this.name = name;
        this.email = email;
        this.salary = salary;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
    
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
}

// Repositories
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByName(String name);
}

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment(Department department);
    List<Employee> findByDepartmentId(Long departmentId);
    List<Employee> findByDepartmentName(String departmentName);
}

// Service Layer
@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Transactional
    public Employee createEmployee(Employee employee, Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        
        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }
    
    public List<Employee> getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }
}

// Usage Example
@RestController
@RequestMapping("/api")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @PostMapping("/departments/{deptId}/employees")
    public ResponseEntity<Employee> addEmployee(
            @PathVariable Long deptId,
            @RequestBody Employee employee) {
        Employee saved = employeeService.createEmployee(employee, deptId);
        return ResponseEntity.ok(saved);
    }
}
```

**Database Schema Generated:**
```sql
-- departments table
CREATE TABLE departments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    location VARCHAR(255)
);

-- employees table
CREATE TABLE employees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    salary DOUBLE,
    department_id BIGINT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);
```

**Key Points:**
- **Owning side:** Employee (has @JoinColumn)
- **Inverse side:** Department (has mappedBy)
- **Foreign key:** department_id in employees table
- **Fetch type:** Use LAZY to avoid loading department every time
- **Cascade:** Use carefully - CascadeType.ALL on Department will delete employees when department is deleted

**Possible Cross Questions:**
- *What is the owning side of the relationship?*
  - The side with @JoinColumn annotation (Employee in this case). It holds the foreign key
- *Why use LAZY fetch?*
  - To avoid loading unnecessary data. Department will only be loaded when accessed
- *What happens if you delete a Department?*
  - Depends on cascade settings. With CascadeType.ALL, employees are deleted. Without it, you'll get a constraint violation
- *Can you have unidirectional Many-to-One?*
  - Yes, just don't add @OneToMany on the Department side

---

## 11. Suppose: One Order can have multiple Products. How would you model this using JPA annotations?

**Simple Explanation:**
Think of an Amazon order. One order can contain multiple products (laptop, mouse, keyboard), and the same product can appear in multiple orders. This is a Many-to-Many relationship.

**Interview Answer:**

This is a classic Many-to-Many relationship with additional information (like quantity, price) stored in the join table. The best way to model this is using a separate OrderItem entity.

**Solution 1: Using a Join Entity (Recommended)**

```java
// Order Entity
@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "order_number", unique = true)
    private String orderNumber;
    
    private LocalDateTime orderDate;
    
    private String customerName;
    
    private Double totalAmount;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    // One order has many order items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    
    // Helper methods
    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }
    
    public void removeOrderItem(OrderItem item) {
        orderItems.remove(item);
        item.setOrder(null);
    }
    
    public void calculateTotalAmount() {
        this.totalAmount = orderItems.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
    }
    
    // Constructors, Getters, Setters
    public Order() {
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }
    
    public Order(String orderNumber, String customerName) {
        this();
        this.orderNumber = orderNumber;
        this.customerName = customerName;
    }
    
    // Getters and Setters...
}

// Product Entity
@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @Column(nullable = false)
    private Double price;
    
    private Integer stockQuantity;
    
    private String category;
    
    // One product can be in many order items
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems = new ArrayList<>();
    
    // Constructors, Getters, Setters
    public Product() {}
    
    public Product(String name, Double price, Integer stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    
    // Getters and Setters...
}

// OrderItem Entity (Join Table with extra attributes)
@Entity
@Table(name = "order_items")
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Many order items belong to one order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    
    // Many order items can refer to one product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false)
    private Double priceAtPurchase; // Price when ordered (may differ from current product price)
    
    private Double discount = 0.0;
    
    // Calculated field
    @Transient
    private Double subtotal;
    
    public Double getSubtotal() {
        return (priceAtPurchase * quantity) - discount;
    }
    
    // Constructors
    public OrderItem() {}
    
    public OrderItem(Order order, Product product, Integer quantity, Double priceAtPurchase) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }
    
    // Getters and Setters...
}

// Enum
public enum OrderStatus {
    PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
}

// Repositories
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByOrderNumber(String orderNumber);
    List<Order> findByCustomerName(String customerName);
    List<Order> findByStatus(OrderStatus status);
    
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems WHERE o.id = :orderId")
    Optional<Order> findByIdWithItems(@Param("orderId") Long orderId);
}

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    
    @Query("SELECT p FROM Product p WHERE p.stockQuantity > 0")
    List<Product> findAvailableProducts();
}

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
    List<OrderItem> findByProduct(Product product);
    
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId")
    List<OrderItem> findByOrderId(@Param("orderId") Long orderId);
}

// Service Layer
@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Transactional
    public Order createOrder(String orderNumber, String customerName, 
                            List<OrderItemDTO> items) {
        Order order = new Order(orderNumber, customerName);
        
        for (OrderItemDTO itemDTO : items) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            
            // Check stock
            if (product.getStockQuantity() < itemDTO.getQuantity()) {
                throw new RuntimeException("Insufficient stock for: " + product.getName());
            }
            
            OrderItem orderItem = new OrderItem(
                    order, 
                    product, 
                    itemDTO.getQuantity(), 
                    product.getPrice()
            );
            
            order.addOrderItem(orderItem);
            
            // Update stock
            product.setStockQuantity(product.getStockQuantity() - itemDTO.getQuantity());
            productRepository.save(product);
        }
        
        order.calculateTotalAmount();
        return orderRepository.save(order);
    }
    
    public Order getOrderWithItems(Long orderId) {
        return orderRepository.findByIdWithItems(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
    
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = getOrderWithItems(orderId);
        
        // Restore stock
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productRepository.save(product);
        }
        
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}

// DTO for creating orders
public class OrderItemDTO {
    private Long productId;
    private Integer quantity;
    
    // Constructors, Getters, Setters
}

// Controller
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(
                request.getOrderNumber(),
                request.getCustomerName(),
                request.getItems()
        );
        return ResponseEntity.ok(order);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderWithItems(id));
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }
}

// Request DTO
public class CreateOrderRequest {
    private String orderNumber;
    private String customerName;
    private List<OrderItemDTO> items;
    
    // Getters and Setters
}
```

**Database Schema:**
```sql
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_number VARCHAR(255) UNIQUE,
    order_date DATETIME,
    customer_name VARCHAR(255),
    total_amount DOUBLE,
    status VARCHAR(50)
);

CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DOUBLE NOT NULL,
    stock_quantity INT,
    category VARCHAR(100)
);

CREATE TABLE order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT,
    product_id BIGINT,
    quantity INT NOT NULL,
    price_at_purchase DOUBLE NOT NULL,
    discount DOUBLE DEFAULT 0,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

**Solution 2: Simple Many-to-Many (Not Recommended for this use case)**

```java
@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToMany
    @JoinTable(
        name = "order_products",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();
}

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToMany(mappedBy = "products")
    private Set<Order> orders = new HashSet<>();
}
```

**Why Solution 1 is better:**
- Can store quantity, price at purchase time, discount
- Better performance with explicit queries
- More flexibility for business logic
- Easier to track individual order items

**Possible Cross Questions:**
- *Why not use pure ManyToMany?*
  - Because we need additional attributes (quantity, price, discount) which can't be stored in a simple join table
- *Why store priceAtPurchase instead of just referencing product.price?*
  - Product prices change over time. We need to preserve the price at the time of purchase for historical accuracy
- *How do you handle product deletion?*
  - Don't delete products that have been ordered. Instead, mark them as inactive or discontinued
- *What about performance when loading orders with many products?*
  - Use JOIN FETCH in queries to avoid N+1 problem, or use DTO projections to load only required data

---

## 12. What is Lazy Loading and Eager Loading?

**Simple Explanation:**
Imagine a book with many chapters. Eager loading is like reading the entire book at once. Lazy loading is like reading only the chapter you need right now, and reading other chapters only when you actually need them.

**Interview Answer:**

Lazy and Eager loading are fetch strategies that determine when related entities are loaded from the database.

### **Eager Loading (@FetchType.EAGER)**
- Related entities are loaded immediately with the parent entity
- Uses JOIN query by default
- Data is available even outside transaction

### **Lazy Loading (@FetchType.LAZY)**
- Related entities are loaded only when accessed
- Uses proxy objects
- Requires active transaction when accessing lazy relationships

**Code Example:**

```java
@Entity
public class Department {
    @Id
    private Long id;
    private String name;
    
    // EAGER - Employees loaded immediately with Department
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    private List<Employee> employees;
}

@Entity
public class Department {
    @Id
    private Long id;
    private String name;
    
    // LAZY - Employees loaded only when accessed
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Employee> employees;
}

// Usage
@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository repository;
    
    // Eager loading
    public void eagerExample() {
        Department dept = repository.findById(1L).get();
        // SQL: SELECT * FROM department d 
        //      LEFT JOIN employee e ON d.id = e.department_id
        // Employees already loaded
        System.out.println(dept.getEmployees().size()); // Works
    }
    
    // Lazy loading
    @Transactional // Required!
    public void lazyExample() {
        Department dept = repository.findById(1L).get();
        // SQL: SELECT * FROM department WHERE id = 1
        // Employees NOT loaded yet
        
        System.out.println(dept.getEmployees().size());
        // Now SQL: SELECT * FROM employee WHERE department_id = 1
        // Employees loaded when accessed
    }
    
    // LazyInitializationException
    public void lazyError() {
        Department dept = repository.findById(1L).get();
        // Transaction closed here
        
        // ERROR: LazyInitializationException
        System.out.println(dept.getEmployees().size());
    }
}
```

**Default Fetch Types:**
- `@OneToOne`: EAGER
- `@ManyToOne`: EAGER
- `@OneToMany`: LAZY
- `@ManyToMany`: LAZY

**Comparison:**

| Aspect | Eager Loading | Lazy Loading |
|--------|--------------|--------------|
| **When loaded** | Immediately | When accessed |
| **Query** | Single JOIN query | Multiple queries |
| **Memory** | More memory usage | Less memory usage |
| **Performance** | Slower if data not needed | Faster initial load |
| **LazyInitializationException** | Never | Possible |
| **Use Case** | Small related data always needed | Large data sometimes needed |

**Solving N+1 Problem with Lazy Loading:**

```java
// Bad: N+1 queries
public List<Department> getAllDepartments() {
    return departmentRepository.findAll(); // 1 query
    // When accessing employees for each department:
    // N additional queries (one per department)
}

// Good: Use JOIN FETCH
@Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees")
List<Department> findAllWithEmployees(); // Single query

// Or use EntityGraph
@EntityGraph(attributePaths = {"employees"})
List<Department> findAll();
```

**Possible Cross Questions:**
- *What is LazyInitializationException?*
  - Occurs when trying to access lazy-loaded data outside a transaction. Solution: Use @Transactional or eager loading
- *Which is better - Lazy or Eager?*
  - Lazy is generally better for performance. Use eager only for small, always-needed relationships
- *How to fix N+1 problem?*
  - Use JOIN FETCH, @EntityGraph, or batch fetching (@BatchSize)
- *Can you change fetch type at runtime?*
  - Yes, using @EntityGraph or explicit JOIN FETCH in queries

---

## 13. What is JPQL vs Native Query in Spring Data JPA?

**Simple Explanation:**
JPQL is like speaking to the database in Java language - you use class names and field names. Native Query is like speaking the database's native language (SQL) - you use table names and column names.

**Interview Answer:**

### **JPQL (Java Persistence Query Language)**
- Object-oriented query language
- Works with entities and their properties
- Database-independent
- Type-safe with entity names

### **Native SQL Query**
- Standard SQL queries
- Works with database tables and columns
- Database-specific
- More control over query optimization

**Comparison:**

| Aspect | JPQL | Native SQL |
|--------|------|------------|
| **Syntax** | Entity-based | Table-based |
| **Portability** | Database-independent | Database-specific |
| **Performance** | Good for simple queries | Better for complex queries |
| **Learning Curve** | Requires JPA knowledge | Standard SQL knowledge |
| **Use Case** | Most business logic | Complex joins, DB-specific features |

**Examples:**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // ===== JPQL Examples =====
    
    // Basic JPQL
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmailJPQL(@Param("email") String email);
    
    // JPQL with multiple conditions
    @Query("SELECT u FROM User u WHERE u.age > :minAge AND u.city = :city")
    List<User> findByAgeAndCityJPQL(@Param("minAge") int minAge, @Param("city") String city);
    
    // JPQL with JOIN
    @Query("SELECT u FROM User u JOIN u.orders o WHERE o.status = :status")
    List<User> findUsersWithOrderStatusJPQL(@Param("status") String status);
    
    // JPQL with aggregate functions
    @Query("SELECT COUNT(u) FROM User u WHERE u.active = true")
    Long countActiveUsersJPQL();
    
    // JPQL with ORDER BY
    @Query("SELECT u FROM User u WHERE u.age > :age ORDER BY u.name ASC")
    List<User> findByAgeOrderedJPQL(@Param("age") int age);
    
    // JPQL UPDATE query
    @Modifying
    @Query("UPDATE User u SET u.active = :status WHERE u.id = :id")
    int updateUserStatusJPQL(@Param("id") Long id, @Param("status") boolean status);
    
    // JPQL DELETE query
    @Modifying
    @Query("DELETE FROM User u WHERE u.active = false AND u.createdDate < :date")
    int deleteInactiveUsersJPQL(@Param("date") LocalDateTime date);
    
    // JPQL with DTO projection
    @Query("SELECT new com.example.dto.UserDTO(u.id, u.name, u.email) FROM User u WHERE u.city = :city")
    List<UserDTO> findUserDTOsByCityJPQL(@Param("city") String city);
    
    // ===== Native SQL Examples =====
    
    // Basic Native Query
    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    User findByEmailNative(@Param("email") String email);
    
    // Native query with multiple conditions
    @Query(value = "SELECT * FROM users WHERE age > :minAge AND city = :city", 
           nativeQuery = true)
    List<User> findByAgeAndCityNative(@Param("minAge") int minAge, @Param("city") String city);
    
    // Native query with complex JOIN
    @Query(value = """
            SELECT u.* FROM users u
            INNER JOIN orders o ON u.id = o.user_id
            INNER JOIN order_items oi ON o.id = oi.order_id
            WHERE oi.product_id = :productId
            GROUP BY u.id
            HAVING COUNT(o.id) > :minOrders
            """, nativeQuery = true)
    List<User> findUsersWhoOrderedProductNative(
            @Param("productId") Long productId,
            @Param("minOrders") int minOrders);
    
    // Native query with database-specific functions
    @Query(value = "SELECT * FROM users WHERE DATEDIFF(CURDATE(), created_date) < 30", 
           nativeQuery = true)
    List<User> findRecentUsersNative();
    
    // Native UPDATE query
    @Modifying
    @Query(value = "UPDATE users SET active = :status WHERE id = :id", 
           nativeQuery = true)
    int updateUserStatusNative(@Param("id") Long id, @Param("status") boolean status);
    
    // Native query with pagination
    @Query(value = "SELECT * FROM users WHERE city = :city ORDER BY name LIMIT :limit OFFSET :offset", 
           nativeQuery = true)
    List<User> findByCityWithPaginationNative(
            @Param("city") String city,
            @Param("limit") int limit,
            @Param("offset") int offset);
    
    // Native query for complex aggregation
    @Query(value = """
            SELECT 
                u.city,
                COUNT(*) as user_count,
                AVG(u.age) as avg_age,
                MAX(u.age) as max_age
            FROM users u
            WHERE u.active = true
            GROUP BY u.city
            HAVING COUNT(*) > :minCount
            ORDER BY user_count DESC
            """, nativeQuery = true)
    List<Object[]> getCityStatisticsNative(@Param("minCount") int minCount);
}

// DTO for JPQL projection
package com.example.dto;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    
    public UserDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    // Getters and setters
}

// Service example showing both
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // Using JPQL
    public List<User> searchUsersJPQL(String city) {
        return userRepository.findByAgeAndCityJPQL(18, city);
    }
    
    // Using Native SQL
    public List<User> searchUsersNative(String city) {
        return userRepository.findByAgeAndCityNative(18, city);
    }
}
```

**When to use JPQL:**
- Standard CRUD operations
- Simple to medium complexity queries
- Need database independence
- Working primarily with entity relationships
- Want type-safety

**When to use Native SQL:**
- Complex queries with multiple joins
- Database-specific features (window functions, stored procedures)
- Performance optimization with database hints
- Working with database views
- Complex aggregations and reporting

**Performance Tips:**

```java
// Avoid: Fetching entire entity when only few fields needed
@Query("SELECT u FROM User u")
List<User> findAll(); // Loads all columns

// Better: Use projection
@Query("SELECT new UserDTO(u.id, u.name) FROM User u")
List<UserDTO> findAllProjection(); // Loads only needed columns

// Avoid: N+1 problem
@Query("SELECT u FROM User u")
List<User> findAll(); // Then accessing u.getOrders() for each

// Better: Use JOIN FETCH
@Query("SELECT u FROM User u LEFT JOIN FETCH u.orders")
List<User> findAllWithOrders(); // Single query
```

**Possible Cross Questions:**
- *Can you use JPQL and Native queries together?*
  - Yes, you can have both in the same repository for different methods
- *Which is faster - JPQL or Native SQL?*
  - Native SQL can be faster for complex queries, but JPQL is sufficient for most cases
- *How do you return custom objects from Native queries?*
  - Use @SqlResultSetMapping or return List<Object[]> and map manually
- *Can JPQL handle database-specific functions?*
  - No, for database-specific functions you must use Native queries

---

## 14. What's the difference between CrudRepository, JpaRepository, and PagingAndSortingRepository?

**Simple Explanation:**
Think of them as three levels of a toolbox. CrudRepository is a basic toolbox with essential tools. PagingAndSortingRepository adds sorting and organizing tools. JpaRepository is a complete professional toolbox with all tools plus some extras.

**Interview Answer:**

These are three interfaces in Spring Data JPA hierarchy, each adding more functionality:

**Hierarchy:**
```
Repository (marker interface)
    ↓
CrudRepository
    ↓
PagingAndSortingRepository
    ↓
JpaRepository
```

### **1. CrudRepository**
Basic CRUD operations

**Methods:**
```java
public interface CrudRepository<T, ID> {
    <S extends T> S save(S entity);
    <S extends T> Iterable<S> saveAll(Iterable<S> entities);
    Optional<T> findById(ID id);
    boolean existsById(ID id);
    Iterable<T> findAll();
    Iterable<T> findAllById(Iterable<ID> ids);
    long count();
    void deleteById(ID id);
    void delete(T entity);
    void deleteAll(Iterable<? extends T> entities);
    void deleteAll();
}
```

### **2. PagingAndSortingRepository**
Extends CrudRepository + adds pagination and sorting

**Additional Methods:**
```java
public interface PagingAndSortingRepository<T, ID> extends CrudRepository<T, ID> {
    Iterable<T> findAll(Sort sort);
    Page<T> findAll(Pageable pageable);
}
```

### **3. JpaRepository**
Extends PagingAndSortingRepository + adds JPA-specific operations

**Additional Methods:**
```java
public interface JpaRepository<T, ID> extends PagingAndSortingRepository<T, ID> {
    List<T> findAll();
    List<T> findAll(Sort sort);
    List<T> findAllById(Iterable<ID> ids);
    <S extends T> List<S> saveAll(Iterable<S> entities);
    void flush();
    <S extends T> S saveAndFlush(S entity);
    void deleteInBatch(Iterable<T> entities);
    void deleteAllInBatch();
    T getOne(ID id); // Returns reference, doesn't hit DB
    T getReferenceById(ID id);
}
```

**Comparison Table:**

| Feature | CrudRepository | PagingAndSortingRepository | JpaRepository |
|---------|---------------|---------------------------|--------------|
| **CRUD Operations** | ✅ Yes | ✅ Yes | ✅ Yes |
| **Pagination** | ❌ No | ✅ Yes | ✅ Yes |
| **Sorting** | ❌ No | ✅ Yes | ✅ Yes |
| **Batch Operations** | ❌ No | ❌ No | ✅ Yes |
| **Flush** | ❌ No | ❌ No | ✅ Yes |
| **Return Type** | Iterable | Iterable/Page | List |
| **JPA Specific** | ❌ No | ❌ No | ✅ Yes |

**Complete Examples:**

```java
// === CrudRepository ===
@Repository
public interface ProductCrudRepository extends CrudRepository<Product, Long> {
    // Only basic CRUD methods available
}

@Service
public class ProductService1 {
    @Autowired
    private ProductCrudRepository repository;
    
    public void crudOperations() {
        // Save
        Product product = new Product("Laptop", 999.99);
        repository.save(product);
        
        // Find
        Optional<Product> found = repository.findById(1L);
        
        // Find all (returns Iterable, not List)
        Iterable<Product> all = repository.findAll();
        
        // Count
        long count = repository.count();
        
        // Delete
        repository.deleteById(1L);
        
        // NO pagination or sorting available!
    }
}

// === PagingAndSortingRepository ===
@Repository
public interface ProductPagingRepository 
        extends PagingAndSortingRepository<Product, Long> {
    // CRUD + Pagination + Sorting
}

@Service
public class ProductService2 {
    @Autowired
    private ProductPagingRepository repository;
    
    public void pagingAndSorting() {
        // Sorting
        Sort sort = Sort.by("price").descending().and(Sort.by("name"));
        Iterable<Product> sorted = repository.findAll(sort);
        
        // Pagination
        Pageable pageable = PageRequest.of(0, 10); // Page 0, Size 10
        Page<Product> page = repository.findAll(pageable);
        
        System.out.println("Total elements: " + page.getTotalElements());
        System.out.println("Total pages: " + page.getTotalPages());
        System.out.println("Current page: " + page.getNumber());
        System.out.println("Has next: " + page.hasNext());
        
        // Pagination with sorting
        Pageable pageableWithSort = PageRequest.of(
                0, 10, Sort.by("price").ascending()
        );
        Page<Product> sortedPage = repository.findAll(pageableWithSort);
    }
}

// === JpaRepository (Most commonly used) ===
@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {
    // All CRUD + Pagination + Sorting + JPA-specific methods
    
    // Custom query methods
    List<Product> findByCategory(String category);
    List<Product> findByPriceBetween(Double min, Double max);
}

@Service
public class ProductService3 {
    @Autowired
    private ProductJpaRepository repository;
    
    public void jpaOperations() {
        // Returns List instead of Iterable
        List<Product> all = repository.findAll();
        
        // Batch save
        List<Product> products = Arrays.asList(
                new Product("Laptop", 999.99),
                new Product("Mouse", 29.99),
                new Product("Keyboard", 79.99)
        );
        repository.saveAll(products);
        
        // Flush - immediately synchronizes with database
        Product product = new Product("Monitor", 299.99);
        repository.saveAndFlush(product); // Saves and flushes immediately
        
        // Batch delete (more efficient than individual deletes)
        List<Product> toDelete = repository.findByCategory("Obsolete");
        repository.deleteAllInBatch(toDelete); // Single DELETE query
        
        // Get reference (doesn't hit database, returns proxy)
        Product reference = repository.getReferenceById(1L);
        
        // Pagination
        Page<Product> page = repository.findAll(PageRequest.of(0, 10));
        
        // Sorting
        List<Product> sorted = repository.findAll(Sort.by("price").descending());
    }
}
```

**Practical Usage Recommendations:**

```java
// Use CrudRepository when:
// - Simple CRUD operations
// - No need for pagination/sorting
// - Working with small datasets
@Repository
public interface SimpleEntityRepository extends CrudRepository<SimpleEntity, Long> {
}

// Use PagingAndSortingRepository when:
// - Need pagination and sorting
// - Don't need JPA-specific features
@Repository
public interface ListingRepository extends PagingAndSortingRepository<Listing, Long> {
}

// Use JpaRepository when: (MOST COMMON)
// - Need all features
// - Production applications
// - Working with large datasets
// - Need batch operations
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Recommended for most use cases
}
```

**Pagination Example in Detail:**

```java
@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductJpaRepository productRepository;
    
    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC") 
                ? Sort.Direction.ASC 
                : Sort.Direction.DESC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        
        Page<Product> productsPage = productRepository.findAll(pageable);
        
        return ResponseEntity.ok(productsPage);
    }
    
    // Response will include:
    // {
    //   "content": [...],
    //   "pageable": {...},
    //   "totalPages": 10,
    //   "totalElements": 100,
    //   "last": false,
    //   "number": 0,
    //   "size": 10,
    //   "sort": {...},
    //   "first": true,
    //   "numberOfElements": 10
    // }
}
```

**Possible Cross Questions:**
- *Which repository should I use in production?*
  - JpaRepository in most cases, as it provides all features and better return types (List instead of Iterable)
- *What's the difference between deleteAll() and deleteAllInBatch()?*
  - deleteAll() fetches entities first then deletes individually. deleteAllInBatch() executes a single DELETE query (more efficient)
- *What is the difference between getOne() and findById()?*
  - getOne() returns a lazy proxy without hitting the DB (deprecated, use getReferenceById). findById() immediately fetches from DB
- *Can I extend multiple repository interfaces?*
  - No need to. Just extend JpaRepository as it already extends the others

---

## 15. What is a Transaction, and what transaction scopes are available?

**Simple Explanation:**
A transaction is like a package deal - either everything in the package happens successfully, or nothing happens at all. Imagine transferring money: deducting from one account and adding to another must both succeed or both fail. You can't have money disappear!

**Interview Answer:**

A transaction is a unit of work that must be executed completely or not at all, following ACID properties (Atomicity, Consistency, Isolation, Durability).

### **ACID Properties:**

**Atomicity:** All or nothing - either all operations succeed or all fail
**Consistency:** Database remains in a valid state before and after transaction
**Isolation:** Concurrent transactions don't interfere with each other
**Durability:** Once committed, changes are permanent even if system crashes

### **Transaction Management in Spring:**

**1. Declarative Transaction Management**
Using annotations (recommended)

```java
@Service
public class BankService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    // Method-level transaction
    @Transactional
    public void transferMoney(Long fromId, Long toId, Double amount) {
        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new RuntimeException("Target account not found"));
        
        if (from.getBalance() < amount) {
            throw new InsufficientFundsException("Not enough balance");
        }
        
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        
        accountRepository.save(from);
        accountRepository.save(to);
        
        // If any exception occurs, both operations are rolled back
    }
}
```

**2. Programmatic Transaction Management**
Using TransactionTemplate (more control)

```java
@Service
public class OrderService {
    
    @Autowired
    private TransactionTemplate transactionTemplate;
    
    @Autowired
    private OrderRepository orderRepository;
    
    public Order createOrder(Order order) {
        return transactionTemplate.execute(status -> {
            try {
                // Your transactional code
                Order saved = orderRepository.save(order);
                // More operations...
                return saved;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
        });
    }
}
```

### **Transaction Propagation Types (Scopes):**

```java
@Service
public class TransactionPropagationExamples {
    
    // 1. REQUIRED (Default)
    // Uses existing transaction or creates new one
    @Transactional(propagation = Propagation.REQUIRED)
    public void required() {
        // If caller has transaction, join it
        // Otherwise, create new transaction
    }
    
    // 2. REQUIRES_NEW
    // Always creates new transaction, suspends existing one
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiresNew() {
        // Creates new transaction
        // Suspends caller's transaction if exists
        // Commits/rollbacks independently
    }
    
    // 3. MANDATORY
    // Must be called within existing transaction
    @Transactional(propagation = Propagation.MANDATORY)
    public void mandatory() {
        // Throws exception if no transaction exists
    }
    
    // 4. SUPPORTS
    // Executes within transaction if exists, otherwise non-transactionally
    @Transactional(propagation = Propagation.SUPPORTS)
    public void supports() {
        // Joins transaction if exists
        // Otherwise runs without transaction
    }
    
    // 5. NOT_SUPPORTED
    // Executes non-transactionally, suspends existing transaction
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void notSupported() {
        // Suspends current transaction
        // Executes without transaction
    }
    
    // 6. NEVER
    // Must be called without transaction
    @Transactional(propagation = Propagation.NEVER)
    public void never() {
        // Throws exception if transaction exists
    }
    
    // 7. NESTED
    // Executes within nested transaction
    @Transactional(propagation = Propagation.NESTED)
    public void nested() {
        // Creates nested transaction (savepoint)
        // Can rollback to savepoint without affecting outer transaction
    }
}
```

**Propagation Comparison:**

| Propagation | Existing Transaction | No Transaction | Use Case |
|-------------|---------------------|----------------|----------|
| REQUIRED | Join | Create new | Most common, default |
| REQUIRES_NEW | Suspend & create new | Create new | Independent operations |
| MANDATORY | Join | Exception | Enforce transactional context |
| SUPPORTS | Join | Run without | Read operations |
| NOT_SUPPORTED | Suspend | Run without | Non-transactional operations |
| NEVER | Exception | Run without | Ensure no transaction |
| NESTED | Nested | Create new | Partial rollback scenarios |

### **Transaction Isolation Levels:**

```java
@Service
public class TransactionIsolationExamples {
    
    // Default isolation (database default)
    @Transactional(isolation = Isolation.DEFAULT)
    public void defaultIsolation() {}
    
    // Read Uncommitted - lowest isolation, allows dirty reads
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void readUncommitted() {
        // Can read uncommitted changes from other transactions
        // Dirty reads possible
    }
    
    // Read Committed - prevents dirty reads
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void readCommitted() {
        // Only reads committed data
        // Non-repeatable reads possible
    }
    
    // Repeatable Read - prevents dirty and non-repeatable reads
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void repeatableRead() {
        // Ensures same data if read multiple times
        // Phantom reads possible
    }
    
    // Serializable - highest isolation
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void serializable() {
        // Complete isolation
        // Transactions execute as if serial
        // Slowest performance
    }
}
```

### **Additional Transaction Attributes:**

```java
@Service
public class CompleteTransactionExample {
    
    @Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.READ_COMMITTED,
        timeout = 30, // seconds
        readOnly = false,
        rollbackFor = {Exception.class},
        noRollbackFor = {IllegalArgumentException.class}
    )
    public void comprehensiveTransaction() {
        // Transaction configuration:
        // - Joins existing or creates new
        // - READ_COMMITTED isolation
        // - 30 second timeout
        // - Read-write transaction
        // - Rollback on any Exception
        // - No rollback for IllegalArgumentException
    }
    
    // Read-only transaction (optimization)
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        // Optimized for read operations
        // Hibernate won't check for dirty objects
        // Better performance
        return userRepository.findAll();
    }
}
```

### **Real-World Example:**

```java
@Service
public class OrderProcessingService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private InventoryService inventoryService;
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private NotificationService notificationService;
    
    @Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.READ_COMMITTED,
        rollbackFor = Exception.class,
        timeout = 60
    )
    public Order processOrder(OrderRequest request) throws OrderProcessingException {
        try {
            // 1. Create order (transactional)
            Order order = new Order();
            order.setCustomerId(request.getCustomerId());
            order.setStatus(OrderStatus.PENDING);
            order = orderRepository.save(order);
            
            // 2. Reserve inventory (transactional)
            inventoryService.reserveItems(request.getItems());
            
            // 3. Process payment (REQUIRES_NEW - independent transaction)
            Payment payment = paymentService.processPayment(
                    request.getPaymentDetails(), 
                    order.getTotalAmount()
            );
            
            // 4. Update order status
            order.setStatus(OrderStatus.CONFIRMED);
            order.setPaymentId(payment.getId());
            order = orderRepository.save(order);
            
            // 5. Send notification (NOT_SUPPORTED - non-transactional)
            notificationService.sendOrderConfirmation(order);
            
            return order;
            
        } catch (InsufficientInventoryException e) {
            // Rollback entire transaction
            throw new OrderProcessingException("Items out of stock", e);
        } catch (PaymentFailedException e) {
            // Rollback entire transaction
            throw new OrderProcessingException("Payment failed", e);
        }
    }
}

@Service
public class PaymentService {
    
    // Independent transaction - commits even if order processing fails
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Payment processPayment(PaymentDetails details, Double amount) {
        // Process payment with payment gateway
        // This transaction is independent
        // Will commit regardless of outer transaction
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setStatus("COMPLETED");
        return paymentRepository.save(payment);
    }
}

@Service
public class NotificationService {
    
    // Non-transactional - runs outside transaction
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void sendOrderConfirmation(Order order) {
        // Send email notification
        // Runs without transaction
        // Failure here won't rollback order
        emailService.send(order.getCustomerEmail(), "Order Confirmed");
    }
}
```

### **Common Transaction Pitfalls:**

```java
@Service
public class TransactionPitfalls {
    
    @Autowired
    private UserRepository userRepository;
    
    // WRONG: Self-invocation doesn't work
    public void wrongUsage() {
        this.updateUser(); // @Transactional won't work!
    }
    
    @Transactional
    public void updateUser() {
        // Transaction won't be applied when called from wrongUsage()
    }
    
    // CORRECT: Inject self or use separate service
    @Autowired
    private TransactionPitfalls self; // or use @Lazy
    
    public void correctUsage() {
        self.updateUser(); // Now @Transactional works!
    }
    
    // WRONG: Catching and not rethrowing
    @Transactional
    public void wrongExceptionHandling() {
        try {
            // Some operation that might fail
            userRepository.save(new User());
        } catch (Exception e) {
            // Transaction won't rollback!
            e.printStackTrace();
        }
    }
    
    // CORRECT: Rethrow or use rollbackFor
    @Transactional
    public void correctExceptionHandling() throws Exception {
        try {
            userRepository.save(new User());
        } catch (Exception e) {
            // Rethrow to trigger rollback
            throw e;
        }
    }
}
```

**Possible Cross Questions:**
- *What happens if a method with @Transactional calls another @Transactional method?*
  - Depends on propagation. With REQUIRED (default), they share the same transaction. With REQUIRES_NEW, a new transaction is created
- *Why doesn't @Transactional work when calling method from same class?*
  - Spring uses proxies for @Transactional. Self-invocation bypasses the proxy. Use separate service or inject self with @Lazy
- *When should you use REQUIRES_NEW?*
  - For independent operations that should commit regardless of outer transaction (logging, auditing, payment processing)
- *What's the difference between readOnly=true and without it?*
  - readOnly=true optimizes the transaction for read operations, Hibernate doesn't check for dirty objects, and some databases optimize read-only transactions
- *How to handle partial rollback?*
  - Use NESTED propagation or create savepoints programmatically

---

## Quick Reference Table

| Concept | Key Points | Common Annotations |
|---------|------------|-------------------|
| **JPA** | Specification for ORM | @Entity, @Table, @Id |
| **Entity** | Java class mapped to table | @Entity, @Column, @GeneratedValue |
| **Repository** | Data access interface | @Repository, extends JpaRepository |
| **Relationships** | @OneToOne, @OneToMany, @ManyToOne, @ManyToMany | @JoinColumn, @JoinTable |
| **Fetching** | LAZY vs EAGER | FetchType.LAZY, FetchType.EAGER |
| **Queries** | JPQL vs Native SQL | @Query, @Modifying |
| **Repository Types** | CrudRepository < PagingAndSortingRepository < JpaRepository | extends JpaRepository |
| **Transactions** | ACID properties, Propagation | @Transactional, Propagation types |

---

## Pro Tips for Interview:

1. **Always explain with examples** - Don't just define, show code
2. **Know the differences** - Be clear about comparisons (JPQL vs SQL, Lazy vs Eager)
3. **Understand relationships** - Be able to draw ER diagrams
4. **Explain trade-offs** - Every solution has pros/cons
5. **Transaction management** - Very important, know propagation levels
6. **N+1 problem** - Understand and know solutions (JOIN FETCH, @EntityGraph)
7. **Real-world scenarios** - Connect concepts to practical applications
8. **Best practices** - Use JpaRepository, LAZY loading, proper transactions

Good luck with your interview! 🚀
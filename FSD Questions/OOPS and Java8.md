# Java Full Stack Interview Guide
## Object-Oriented Programming & Java 8 Features - Easy to Understand

---

## Object-Oriented Programming Concepts

### 1. Explain OOP concepts in Java with real-life examples

**The 4 Pillars of OOP:**

---

#### **1. ENCAPSULATION** 
**Simple Explanation:** Like a medicine capsule that hides the bitter medicine inside. You don't need to know what's inside, just swallow it.

**Real-life Example:** ATM Machine
- You can withdraw money (public method)
- You can't directly access the bank's vault (private data)
- You interact through a controlled interface

**Code Example:**

```java
public class BankAccount {
    // Private data - hidden from outside
    private String accountNumber;
    private double balance;
    private String pin;
    
    // Public methods - controlled access
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        }
    }
    
    public boolean withdraw(double amount, String enteredPin) {
        // Validation before allowing access
        if (enteredPin.equals(pin) && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
    
    // Getter - controlled read access
    public double getBalance(String enteredPin) {
        if (enteredPin.equals(pin)) {
            return balance;
        }
        return 0.0;
    }
    
    // No direct access to balance from outside
}

// Usage
BankAccount account = new BankAccount();
account.deposit(1000);
// account.balance = 5000; // ERROR! Can't access directly
account.withdraw(500, "1234"); // Must use method
```

**Benefits:**
- Data protection
- Control over how data is accessed/modified
- Easy to maintain and change internal implementation

---

#### **2. INHERITANCE**
**Simple Explanation:** Like children inheriting traits from parents. You get your parent's features but can add your own.

**Real-life Example:** Vehicle Hierarchy
- All vehicles have wheels and can move
- Car is a Vehicle with 4 wheels
- Bike is a Vehicle with 2 wheels

**Code Example:**

```java
// Parent class - Common features
public class Vehicle {
    protected String brand;
    protected int wheels;
    
    public void start() {
        System.out.println("Vehicle started");
    }
    
    public void stop() {
        System.out.println("Vehicle stopped");
    }
}

// Child class - Inherits + adds specific features
public class Car extends Vehicle {
    private int doors;
    private boolean hasAC;
    
    public Car(String brand, int doors) {
        this.brand = brand;  // Inherited from Vehicle
        this.wheels = 4;     // Inherited from Vehicle
        this.doors = doors;
        this.hasAC = true;
    }
    
    // Override parent method
    @Override
    public void start() {
        System.out.println("Car engine started with key");
    }
    
    // New method specific to Car
    public void turnOnAC() {
        System.out.println("AC turned on");
    }
}

public class Bike extends Vehicle {
    private boolean hasCarrier;
    
    public Bike(String brand) {
        this.brand = brand;
        this.wheels = 2;
    }
    
    @Override
    public void start() {
        System.out.println("Bike started with kick");
    }
}

// Usage
Car car = new Car("Honda", 4);
car.start();      // Car's implementation
car.stop();       // Inherited from Vehicle
car.turnOnAC();   // Car's own method

Bike bike = new Bike("Yamaha");
bike.start();     // Bike's implementation
```

**Benefits:**
- Code reusability
- Hierarchical organization
- Easy to extend functionality

---

#### **3. POLYMORPHISM**
**Simple Explanation:** "Poly" = many, "morph" = forms. One thing behaving in different ways. Like a person being a father at home, employee at office, customer at shop.

**Real-life Example:** Payment System
- Same "pay" action, different implementations

**Code Example:**

```java
// Interface/Parent class
interface Payment {
    void pay(double amount);
}

// Different implementations
class CreditCardPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Credit Card");
        System.out.println("Processing through Visa/Mastercard network...");
    }
}

class PayPalPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using PayPal");
        System.out.println("Redirecting to PayPal login...");
    }
}

class CashPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " in Cash");
        System.out.println("Please collect your receipt...");
    }
}

// Usage - Same interface, different behavior
public class PaymentProcessor {
    public void processPayment(Payment payment, double amount) {
        // Don't need to know which type of payment
        payment.pay(amount);  // Polymorphic behavior
    }
    
    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor();
        
        // Same method, different implementations
        processor.processPayment(new CreditCardPayment(), 100);
        processor.processPayment(new PayPalPayment(), 200);
        processor.processPayment(new CashPayment(), 50);
    }
}
```

**Real Output:**
```
Paid 100.0 using Credit Card
Processing through Visa/Mastercard network...
Paid 200.0 using PayPal
Redirecting to PayPal login...
Paid 50.0 in Cash
Please collect your receipt...
```

**Benefits:**
- Flexibility in code
- Easy to add new implementations
- Cleaner, more maintainable code

---

#### **4. ABSTRACTION**
**Simple Explanation:** Like driving a car - you know how to use steering, brake, accelerator. You don't need to know how engine works internally.

**Real-life Example:** TV Remote
- You press power button (what to do)
- You don't know the circuit inside (how it's done)

**Code Example:**

```java
// Abstract class - Hides implementation details
abstract class DatabaseConnection {
    // Abstract method - subclass must implement
    abstract void connect();
    abstract void executeQuery(String query);
    abstract void disconnect();
    
    // Concrete method - common for all
    public void logActivity(String message) {
        System.out.println("LOG: " + message);
    }
}

// MySQL implementation
class MySQLConnection extends DatabaseConnection {
    @Override
    void connect() {
        System.out.println("Connecting to MySQL database...");
        logActivity("MySQL connection established");
    }
    
    @Override
    void executeQuery(String query) {
        System.out.println("Executing MySQL query: " + query);
    }
    
    @Override
    void disconnect() {
        System.out.println("MySQL connection closed");
    }
}

// MongoDB implementation
class MongoDBConnection extends DatabaseConnection {
    @Override
    void connect() {
        System.out.println("Connecting to MongoDB...");
        logActivity("MongoDB connection established");
    }
    
    @Override
    void executeQuery(String query) {
        System.out.println("Executing MongoDB query: " + query);
    }
    
    @Override
    void disconnect() {
        System.out.println("MongoDB connection closed");
    }
}

// Usage - User doesn't need to know internal details
public class Application {
    public static void main(String[] args) {
        DatabaseConnection db = new MySQLConnection();
        db.connect();                    // How it connects? Don't care!
        db.executeQuery("SELECT * FROM users");
        db.disconnect();
    }
}
```

**Benefits:**
- Reduces complexity
- Hides implementation details
- Provides clear interface

---

## 2. Difference between Abstract Classes and Interfaces

**Simple Analogy:**
- **Abstract Class:** Like a semi-finished house - some rooms complete, some need work
- **Interface:** Like a blueprint - just the plan, no construction done

**Detailed Comparison:**

| Feature | Abstract Class | Interface |
|---------|---------------|-----------|
| **Keyword** | `abstract class` | `interface` |
| **Methods** | Can have both abstract & concrete | All methods abstract (before Java 8) |
| **Variables** | Can have any type of variables | Only public static final (constants) |
| **Inheritance** | Single inheritance (`extends`) | Multiple inheritance (`implements`) |
| **Constructor** | Can have constructors | Cannot have constructors |
| **Access Modifiers** | Can use any | Methods are public by default |
| **When to use** | When classes share common code | When defining a contract/capability |

**Code Example:**

```java
// ABSTRACT CLASS EXAMPLE
abstract class Animal {
    protected String name;
    protected int age;
    
    // Constructor
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Concrete method - shared by all animals
    public void sleep() {
        System.out.println(name + " is sleeping");
    }
    
    // Abstract method - each animal implements differently
    abstract void makeSound();
    abstract void move();
}

class Dog extends Animal {
    public Dog(String name, int age) {
        super(name, age);
    }
    
    @Override
    void makeSound() {
        System.out.println(name + " says: Woof!");
    }
    
    @Override
    void move() {
        System.out.println(name + " runs on 4 legs");
    }
}

// INTERFACE EXAMPLE
interface Flyable {
    // Constants
    int MAX_ALTITUDE = 10000;
    
    // Abstract methods
    void fly();
    void land();
}

interface Swimmable {
    void swim();
}

// A class can implement multiple interfaces
class Duck extends Animal implements Flyable, Swimmable {
    public Duck(String name, int age) {
        super(name, age);
    }
    
    @Override
    void makeSound() {
        System.out.println(name + " says: Quack!");
    }
    
    @Override
    void move() {
        System.out.println(name + " waddles");
    }
    
    @Override
    public void fly() {
        System.out.println(name + " is flying");
    }
    
    @Override
    public void land() {
        System.out.println(name + " has landed");
    }
    
    @Override
    public void swim() {
        System.out.println(name + " is swimming");
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog("Buddy", 3);
        dog.makeSound();  // Woof!
        dog.sleep();      // From abstract class
        
        Duck duck = new Duck("Donald", 2);
        duck.makeSound(); // Quack!
        duck.fly();       // Can fly
        duck.swim();      // Can swim
        duck.sleep();     // Inherited from Animal
    }
}
```

**When to use what:**

```java
// Use ABSTRACT CLASS when:
// - You have common code to share
// - You need constructors
// - You want to provide some default implementation

abstract class Employee {
    protected String name;
    protected double baseSalary;
    
    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }
    
    // Common implementation
    public void clockIn() {
        System.out.println(name + " clocked in");
    }
    
    // Let subclass decide
    abstract double calculateSalary();
}

// Use INTERFACE when:
// - You want to define a contract/capability
// - You need multiple inheritance
// - You want unrelated classes to share behavior

interface Printable {
    void print();
}

class Invoice implements Printable {
    public void print() {
        System.out.println("Printing invoice...");
    }
}

class Report implements Printable {
    public void print() {
        System.out.println("Printing report...");
    }
}
```

---

## 3. Explain Singleton Design Pattern

**Simple Explanation:** Like a country having only ONE president at a time. No matter how many times you try to elect, the same person remains president.

**Real-life Examples:**
- Database connection pool
- Logger
- Configuration manager
- Print spooler

**Why Singleton?**
- Ensure only one instance exists
- Global access point
- Resource sharing
- Controlled access

**Implementation Methods:**

#### **Method 1: Eager Initialization (Simple)**

```java
public class DatabaseConnection {
    // Created at class loading time
    private static final DatabaseConnection instance = new DatabaseConnection();
    
    // Private constructor - prevents external instantiation
    private DatabaseConnection() {
        System.out.println("Database connection created");
    }
    
    // Public method to get instance
    public static DatabaseConnection getInstance() {
        return instance;
    }
    
    public void connect() {
        System.out.println("Connected to database");
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        
        System.out.println(db1 == db2);  // true - same instance!
        
        // DatabaseConnection db3 = new DatabaseConnection(); // ERROR!
    }
}
```

#### **Method 2: Lazy Initialization (Thread-Safe)**

```java
public class Logger {
    private static Logger instance;
    
    private Logger() {
        System.out.println("Logger initialized");
    }
    
    // Thread-safe using synchronized
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    
    public void log(String message) {
        System.out.println("LOG: " + message);
    }
}
```

#### **Method 3: Double-Checked Locking (Best Performance)**

```java
public class ConfigurationManager {
    private static volatile ConfigurationManager instance;
    
    private String appName;
    private String version;
    
    private ConfigurationManager() {
        // Load configuration
        this.appName = "MyApp";
        this.version = "1.0";
    }
    
    public static ConfigurationManager getInstance() {
        if (instance == null) {  // First check (no locking)
            synchronized (ConfigurationManager.class) {
                if (instance == null) {  // Second check (with locking)
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }
    
    public String getAppName() {
        return appName;
    }
    
    public String getVersion() {
        return version;
    }
}
```

#### **Method 4: Bill Pugh Singleton (Recommended)**

```java
public class PrinterSpooler {
    
    private PrinterSpooler() {
        System.out.println("Printer Spooler initialized");
    }
    
    // Inner static helper class
    private static class SingletonHelper {
        private static final PrinterSpooler INSTANCE = new PrinterSpooler();
    }
    
    public static PrinterSpooler getInstance() {
        return SingletonHelper.INSTANCE;
    }
    
    public void print(String document) {
        System.out.println("Printing: " + document);
    }
}
```

#### **Method 5: Enum Singleton (Simplest & Thread-Safe)**

```java
public enum DatabaseManager {
    INSTANCE;  // Single instance
    
    private String connectionString;
    
    // Constructor
    DatabaseManager() {
        connectionString = "jdbc:mysql://localhost:3306/mydb";
    }
    
    public void connect() {
        System.out.println("Connected to: " + connectionString);
    }
    
    public void executeQuery(String query) {
        System.out.println("Executing: " + query);
    }
}

// Usage
DatabaseManager.INSTANCE.connect();
DatabaseManager.INSTANCE.executeQuery("SELECT * FROM users");
```

**Real-World Example:**

```java
public class ApplicationConfig {
    private static volatile ApplicationConfig instance;
    private Properties properties;
    
    private ApplicationConfig() {
        properties = new Properties();
        loadConfiguration();
    }
    
    public static ApplicationConfig getInstance() {
        if (instance == null) {
            synchronized (ApplicationConfig.class) {
                if (instance == null) {
                    instance = new ApplicationConfig();
                }
            }
        }
        return instance;
    }
    
    private void loadConfiguration() {
        properties.setProperty("db.url", "jdbc:mysql://localhost:3306/mydb");
        properties.setProperty("db.username", "root");
        properties.setProperty("app.name", "MyApplication");
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}

// Usage across application
public class UserService {
    public void connectToDatabase() {
        ApplicationConfig config = ApplicationConfig.getInstance();
        String dbUrl = config.getProperty("db.url");
        System.out.println("Connecting to: " + dbUrl);
    }
}

public class OrderService {
    public void connectToDatabase() {
        ApplicationConfig config = ApplicationConfig.getInstance();
        String dbUrl = config.getProperty("db.url");
        System.out.println("Connecting to: " + dbUrl);
        // Same instance as UserService!
    }
}
```

**Summary:**

| Method | Pros | Cons | When to Use |
|--------|------|------|-------------|
| Eager | Simple, thread-safe | Created even if not used | Small objects |
| Lazy | Created only when needed | Not thread-safe by default | Single-threaded |
| Synchronized | Thread-safe | Performance overhead | Multi-threaded |
| Double-Check | Better performance | Complex | High-performance needs |
| Bill Pugh | Thread-safe, lazy | Slightly complex | Recommended approach |
| Enum | Simplest, serialization-safe | Less flexible | Best for simple cases |

---

## 4. Compile Time Polymorphism vs Runtime Polymorphism

**Simple Analogy:**
- **Compile Time:** Like planning a party - you decide the menu before guests arrive
- **Runtime:** Like ordering at a restaurant - you decide what to eat when you see the menu

---

### **COMPILE TIME POLYMORPHISM (Method Overloading)**

**Also called:** Static Polymorphism, Early Binding

**Simple Explanation:** Same method name, different parameters. Decision made at compile time based on method signature.

**Real-life Example:** Calculator with different add methods

```java
public class Calculator {
    
    // Add two integers
    public int add(int a, int b) {
        System.out.println("Adding two integers");
        return a + b;
    }
    
    // Add three integers - different number of parameters
    public int add(int a, int b, int c) {
        System.out.println("Adding three integers");
        return a + b + c;
    }
    
    // Add two doubles - different parameter types
    public double add(double a, double b) {
        System.out.println("Adding two doubles");
        return a + b;
    }
    
    // Add string numbers - different parameter types
    public String add(String a, String b) {
        System.out.println("Concatenating strings");
        return a + b;
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        
        System.out.println(calc.add(5, 10));           // Calls int version
        System.out.println(calc.add(5, 10, 15));       // Calls 3-param version
        System.out.println(calc.add(5.5, 10.5));       // Calls double version
        System.out.println(calc.add("Hello", "World")); // Calls String version
        
        // Compiler decides which method to call based on arguments
    }
}
```

**Another Example: Constructor Overloading**

```java
public class Employee {
    private String name;
    private int age;
    private double salary;
    
    // Constructor 1 - Name only
    public Employee(String name) {
        this.name = name;
        this.age = 18;  // default
        this.salary = 30000;  // default
    }
    
    // Constructor 2 - Name and age
    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
        this.salary = 30000;  // default
    }
    
    // Constructor 3 - All parameters
    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
}

// Usage
Employee emp1 = new Employee("John");
Employee emp2 = new Employee("Jane", 25);
Employee emp3 = new Employee("Bob", 30, 50000);
// Compiler picks the right constructor at compile time
```

---

### **RUNTIME POLYMORPHISM (Method Overriding)**

**Also called:** Dynamic Polymorphism, Late Binding

**Simple Explanation:** Parent class reference, child class object. Decision made at runtime based on actual object type.

**Real-life Example:** Payment processing with different payment methods

```java
// Parent class
class Payment {
    public void processPayment(double amount) {
        System.out.println("Processing payment of: " + amount);
    }
}

// Child classes override parent method
class CreditCard extends Payment {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment: " + amount);
        System.out.println("Charging to card ending in 1234");
    }
}

class PayPal extends Payment {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment: " + amount);
        System.out.println("Redirecting to PayPal...");
    }
}

class BankTransfer extends Payment {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing bank transfer: " + amount);
        System.out.println("Transfer will take 2-3 business days");
    }
}

// Usage - Runtime polymorphism
public class PaymentProcessor {
    public static void main(String[] args) {
        // Parent reference, child object
        Payment payment;
        
        // Decision made at RUNTIME based on actual object
        payment = new CreditCard();
        payment.processPayment(100);  // Calls CreditCard version
        
        payment = new PayPal();
        payment.processPayment(200);  // Calls PayPal version
        
        payment = new BankTransfer();
        payment.processPayment(300);  // Calls BankTransfer version
        
        // Same reference variable, different behavior at runtime!
    }
}
```

**Real-World Example: Notification System**

```java
abstract class Notification {
    protected String message;
    
    public Notification(String message) {
        this.message = message;
    }
    
    // Method to be overridden
    public abstract void send();
    
    // Common method
    public void log() {
        System.out.println("Notification logged: " + message);
    }
}

class EmailNotification extends Notification {
    private String email;
    
    public EmailNotification(String message, String email) {
        super(message);
        this.email = email;
    }
    
    @Override
    public void send() {
        System.out.println("Sending EMAIL to " + email);
        System.out.println("Message: " + message);
    }
}

class SMSNotification extends Notification {
    private String phoneNumber;
    
    public SMSNotification(String message, String phoneNumber) {
        super(message);
        this.phoneNumber = phoneNumber;
    }
    
    @Override
    public void send() {
        System.out.println("Sending SMS to " + phoneNumber);
        System.out.println("Message: " + message);
    }
}

class PushNotification extends Notification {
    private String deviceId;
    
    public PushNotification(String message, String deviceId) {
        super(message);
        this.deviceId = deviceId;
    }
    
    @Override
    public void send() {
        System.out.println("Sending PUSH notification to device: " + deviceId);
        System.out.println("Message: " + message);
    }
}

// Usage
public class NotificationService {
    public void sendNotification(Notification notification) {
        notification.log();    // Common method
        notification.send();   // Runtime polymorphism!
    }
    
    public static void main(String[] args) {
        NotificationService service = new NotificationService();
        
        // All using parent reference
        Notification notif1 = new EmailNotification("Hello", "user@example.com");
        Notification notif2 = new SMSNotification("Alert!", "+1234567890");
        Notification notif3 = new PushNotification("Update", "device123");
        
        // Method behavior decided at runtime
        service.sendNotification(notif1);
        service.sendNotification(notif2);
        service.sendNotification(notif3);
    }
}
```

---

### **Key Differences:**

| Aspect | Compile Time | Runtime |
|--------|--------------|---------|
| **Also called** | Method Overloading | Method Overriding |
| **Binding** | Early Binding | Late Binding |
| **When decided** | At compile time | At runtime |
| **Based on** | Method signature | Object type |
| **Performance** | Faster | Slightly slower |
| **Inheritance** | Not required | Required |
| **Method signature** | Must be different | Must be same |
| **Example** | `add(int, int)` vs `add(double, double)` | Parent method overridden in child |

**Side-by-Side Comparison:**

```java
public class PolymorphismDemo {
    
    // COMPILE TIME POLYMORPHISM
    public void print(String message) {
        System.out.println("String: " + message);
    }
    
    public void print(int number) {
        System.out.println("Integer: " + number);
    }
    
    public void print(String msg, int num) {
        System.out.println(msg + " " + num);
    }
}

// RUNTIME POLYMORPHISM
class Animal {
    public void makeSound() {
        System.out.println("Some sound");
    }
}

class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }
}

class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Meow!");
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        // COMPILE TIME
        PolymorphismDemo demo = new PolymorphismDemo();
        demo.print("Hello");      // Compiler picks String version
        demo.print(42);           // Compiler picks int version
        demo.print("Age:", 25);   // Compiler picks 2-param version
        
        // RUNTIME
        Animal animal;
        animal = new Dog();
        animal.makeSound();  // Decided at runtime: Woof!
        
        animal = new Cat();
        animal.makeSound();  // Decided at runtime: Meow!
    }
}
```

---

## Java 8 Features

### 1. What are some Java 8 features you have used?

**Major Java 8 Features:**

1. **Lambda Expressions** ✨
2. **Stream API** 🌊
3. **Optional** 📦
4. **Functional Interfaces** 🔧
5. **Method References** 👉
6. **Default Methods in Interfaces** 🎯
7. **Date and Time API** 📅
8. **forEach() method** 🔄

---

#### **1. Lambda Expressions**

**Simple Explanation:** Lambda is a shorthand way to write anonymous functions. Like texting "lol" instead of "laugh out loud".

**Before Java 8 (Verbose):**

```java
// Old way - Anonymous inner class
List<String> names = Arrays.asList("John", "Jane", "Bob");

Collections.sort(names, new Comparator<String>() {
    @Override
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }
});
```

**After Java 8 (Concise):**

```java
// Lambda way - Much shorter!
List<String> names = Arrays.asList("John", "Jane", "Bob");
Collections.sort(names, (s1, s2) -> s1.compareTo(s2));

// Even shorter with method reference
Collections.sort(names, String::compareTo);
```

**Lambda Syntax:**

```java
// Basic syntax: (parameters) -> expression
(int a, int b) -> a + b

// Single parameter - parentheses optional
x -> x * x

// Multiple statements - use braces
(x, y) -> {
    int sum = x + y;
    return sum;
}

// No parameters
() -> System.out.println("Hello")
```

**Real Examples:**

```java
// 1. Runnable thread
// Old way
new Thread(new Runnable() {
    public void run() {
        System.out.println("Running");
    }
}).start();

// Lambda way
new Thread(() -> System.out.println("Running")).start();

// 2. Button click handler (like in JavaFX)
button.setOnAction(event -> System.out.println("Button clicked!"));

// 3. List iteration
List<String> fruits = Arrays.asList("Apple", "Banana", "Cherry");
fruits.forEach(fruit -> System.out.println(fruit));

// 4. Custom sorting
List<Employee> employees = getEmployees();
employees.sort((e1, e2) -> e1.getSalary() - e2.getSalary());

// 5. Filtering with lambda
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
numbers.stream()
       .filter(n -> n % 2 == 0)  // Lambda to filter even numbers
       .forEach(System.out::println);
```

---

#### **2. Functional Interfaces**

**Simple Explanation:** Interface with exactly ONE abstract method. Perfect for lambda expressions.

**Common Built-in Functional Interfaces:**

```java
// 1. Predicate<T> - Takes input, returns boolean
Predicate<Integer> isEven = num -> num % 2 == 0;
System.out.println(isEven.test(4));  // true

// 2. Function<T, R> - Takes input T, returns R
Function<String, Integer> stringLength = str -> str.length();
System.out.println(stringLength.apply("Hello"));  // 5

// 3. Consumer<T> - Takes input, returns nothing
Consumer<String> printer = msg -> System.out.println(msg);
printer.accept("Hello World");

// 4. Supplier<T> - Takes nothing, returns T
Supplier<Double> randomSupplier = () -> Math.random();
System.out.println(randomSupplier.get());

// 5. BiFunction<T, U, R> - Takes two inputs, returns R
BiFunction<Integer, Integer, Integer> adder = (a, b) -> a + b;
System.out.println(adder.apply(5, 3));  // 8
```

**Custom Functional Interface:**

```java
@FunctionalInterface  // Optional but recommended
interface Calculator {
    int calculate(int a, int b);  // Single abstract method
    
    // Can have default and static methods
    default void log() {
        System.out.println("Calculation done");
    }
}

// Usage
Calculator add = (a, b) -> a + b;
Calculator multiply = (a, b) -> a * b;

System.out.println(add.calculate(5, 3));       // 8
System.out.println(multiply.calculate(5, 3));  // 15
```

---

#### **3. Method References**

**Simple Explanation:** Shorthand for lambda expressions. Like using "@" instead of "at" symbol.

**Types of Method References:**

```java
// 1. Static method reference
// Lambda: (args) -> ClassName.staticMethod(args)
// Method Reference: ClassName::staticMethod

List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
numbers.forEach(n -> System.out.println(n));  // Lambda
numbers.forEach(System.out::println);          // Method reference

// 2. Instance method reference
// Lambda: (arg) -> obj.instanceMethod(arg)
// Method Reference: obj::instanceMethod

String str = "Hello";
Supplier<Integer> lengthSupplier = () -> str.length();  // Lambda
Supplier<Integer> lengthRef = str::length;              // Method reference

// 3. Instance method of arbitrary object
// Lambda: (obj, args) -> obj.instanceMethod(args)
// Method Reference: ClassName::instanceMethod

List<String> names = Arrays.asList("John", "Jane", "Bob");
names.sort((s1, s2) -> s1.compareToIgnoreCase(s2));  // Lambda
names.sort(String::compareToIgnoreCase);              // Method reference

// 4. Constructor reference
// Lambda: (args) -> new ClassName(args)
// Method Reference: ClassName::new

Supplier<List<String>> listSupplier = () -> new ArrayList<>();  // Lambda
Supplier<List<String>> listRef = ArrayList::new;                // Method reference
```

---

#### **4. Default Methods in Interfaces**

**Simple Explanation:** Interfaces can now have method implementations. Like adding new features to old phones without breaking them.

```java
interface Vehicle {
    void start();  // Abstract method
    
    // Default method - has implementation
    default void honk() {
        System.out.println("Beep beep!");
    }
    
    // Static method
    static void service() {
        System.out.println("Vehicle serviced");
    }
}

class Car implements Vehicle {
    @Override
    public void start() {
        System.out.println("Car started");
    }
    
    // Can use default honk() or override it
    @Override
    public void honk() {
        System.out.println("Car horn: HONK!");
    }
}

// Usage
Car car = new Car();
car.start();   // Car started
car.honk();    // Car horn: HONK!
Vehicle.service();  // Static method call
```

---

#### **5. forEach() Method**

**Simple Explanation:** Easier way to loop through collections.

```java
// Old way
List<String> names = Arrays.asList("John", "Jane", "Bob");
for (String name : names) {
    System.out.println(name);
}

// Java 8 way
names.forEach(name -> System.out.println(name));

// Even shorter with method reference
names.forEach(System.out::println);

// With Map
Map<String, Integer> ages = new HashMap<>();
ages.put("John", 25);
ages.put("Jane", 30);

ages.forEach((name, age) -> {
    System.out.println(name + " is " + age + " years old");
});
```

---

#### **6. New Date and Time API**

**Simple Explanation:** Better, easier-to-use date handling than old Date and Calendar classes.

```java
// LocalDate - Date without time
LocalDate today = LocalDate.now();
LocalDate birthday = LocalDate.of(1990, Month.JANUARY, 15);
System.out.println("Today: " + today);

// LocalTime - Time without date
LocalTime now = LocalTime.now();
LocalTime meetingTime = LocalTime.of(14, 30);  // 2:30 PM
System.out.println("Current time: " + now);

// LocalDateTime - Both date and time
LocalDateTime dateTime = LocalDateTime.now();
System.out.println("Date and Time: " + dateTime);

// Date calculations
LocalDate nextWeek = today.plusWeeks(1);
LocalDate lastMonth = today.minusMonths(1);

// Period - difference between dates
Period period = Period.between(birthday, today);
System.out.println("Age: " + period.getYears() + " years");
```

---

### 2. Explain Stream API and its advantages

**Simple Explanation:** Stream is like a conveyor belt in a factory - data flows through, gets processed, and comes out transformed.

**What is Stream API?**
- Process collections in a functional style
- Chain operations together
- Lazy evaluation - processes only when needed
- Can be parallel for better performance

**Stream Operations:**

#### **Creating Streams:**

```java
// 1. From Collection
List<String> names = Arrays.asList("John", "Jane", "Bob");
Stream<String> stream1 = names.stream();

// 2. From Array
String[] arr = {"A", "B", "C"};
Stream<String> stream2 = Arrays.stream(arr);

// 3. From values
Stream<Integer> stream3 = Stream.of(1, 2, 3, 4, 5);

// 4. Infinite streams
Stream<Integer> infiniteStream = Stream.iterate(0, n -> n + 2);  // 0, 2, 4, 6...
Stream<Double> randomStream = Stream.generate(Math::random);
```

#### **Intermediate Operations (Returns Stream):**

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// 1. filter() - Select elements
numbers.stream()
       .filter(n -> n % 2 == 0)  // Only even numbers
       .forEach(System.out::println);  // 2, 4, 6, 8, 10

// 2. map() - Transform elements
List<String> names = Arrays.asList("john", "jane", "bob");
names.stream()
     .map(String::toUpperCase)  // Convert to uppercase
     .forEach(System.out::println);  // JOHN, JANE, BOB

// 3. sorted() - Sort elements
numbers.stream()
       .sorted()
       .forEach(System.out::println);

// 4. distinct() - Remove duplicates
List<Integer> nums = Arrays.asList(1, 2, 2, 3, 3, 3, 4);
nums.stream()
    .distinct()
    .forEach(System.out::println);  // 1, 2, 3, 4

// 5. limit() - Limit number of elements
numbers.stream()
       .limit(5)
       .forEach(System.out::println);  // First 5 elements

// 6. skip() - Skip elements
numbers.stream()
       .skip(5)
       .forEach(System.out::println);  // Skip first 5
```

#### **Terminal Operations (Produces Result):**

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// 1. forEach() - Iterate
numbers.stream().forEach(System.out::println);

// 2. collect() - Convert to collection
List<Integer> evenNumbers = numbers.stream()
                                   .filter(n -> n % 2 == 0)
                                   .collect(Collectors.toList());

// 3. count() - Count elements
long count = numbers.stream()
                    .filter(n -> n > 5)
                    .count();
System.out.println("Count: " + count);  // 5

// 4. reduce() - Combine elements
int sum = numbers.stream()
                 .reduce(0, (a, b) -> a + b);
System.out.println("Sum: " + sum);  // 55

// 5. anyMatch(), allMatch(), noneMatch()
boolean hasEven = numbers.stream().anyMatch(n -> n % 2 == 0);  // true
boolean allPositive = numbers.stream().allMatch(n -> n > 0);    // true
boolean noneNegative = numbers.stream().noneMatch(n -> n < 0);  // true

// 6. findFirst(), findAny()
Optional<Integer> first = numbers.stream().findFirst();
System.out.println("First: " + first.get());  // 1

// 7. min(), max()
Optional<Integer> min = numbers.stream().min(Integer::compareTo);
Optional<Integer> max = numbers.stream().max(Integer::compareTo);
```

#### **Real-World Examples:**

```java
// Example 1: Employee filtering and processing
class Employee {
    String name;
    int age;
    double salary;
    String department;
    
    // Constructor, getters
}

List<Employee> employees = Arrays.asList(
    new Employee("John", 25, 50000, "IT"),
    new Employee("Jane", 30, 60000, "HR"),
    new Employee("Bob", 35, 70000, "IT"),
    new Employee("Alice", 28, 55000, "Finance")
);

// Find IT employees with salary > 50000
List<String> itEmployees = employees.stream()
    .filter(e -> e.getDepartment().equals("IT"))
    .filter(e -> e.getSalary() > 50000)
    .map(Employee::getName)
    .collect(Collectors.toList());

// Average salary by department
Map<String, Double> avgSalaryByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.averagingDouble(Employee::getSalary)
    ));

// Oldest employee
Optional<Employee> oldest = employees.stream()
    .max(Comparator.comparing(Employee::getAge));

// Total salary expense
double totalSalary = employees.stream()
    .mapToDouble(Employee::getSalary)
    .sum();
```

```java
// Example 2: Order processing
class Order {
    String id;
    double amount;
    String status;
    
    // Constructor, getters
}

List<Order> orders = getOrders();

// Get total of completed orders over $100
double total = orders.stream()
    .filter(o -> o.getStatus().equals("COMPLETED"))
    .filter(o -> o.getAmount() > 100)
    .mapToDouble(Order::getAmount)
    .sum();

// Group orders by status
Map<String, List<Order>> ordersByStatus = orders.stream()
    .collect(Collectors.groupingBy(Order::getStatus));

// Top 5 highest value orders
List<Order> topOrders = orders.stream()
    .sorted(Comparator.comparing(Order::getAmount).reversed())
    .limit(5)
    .collect(Collectors.toList());
```

```java
// Example 3: String processing
List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "elderberry");

// Count words starting with specific letter
long countA = words.stream()
    .filter(w -> w.startsWith("a"))
    .count();

// Get words longer than 5 characters, uppercase, sorted
List<String> result = words.stream()
    .filter(w -> w.length() > 5)
    .map(String::toUpperCase)
    .sorted()
    .collect(Collectors.toList());

// Concatenate all words
String concatenated = words.stream()
    .collect(Collectors.joining(", "));
```

---

### **Advantages of Stream API:**

1. **Functional Programming Style**
```java
// Declarative - WHAT to do
List<Integer> evens = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());

// vs Imperative - HOW to do
List<Integer> evens = new ArrayList<>();
for (int n : numbers) {
    if (n % 2 == 0) {
        evens.add(n);
    }
}
```

2. **Lazy Evaluation**
```java
// Operations not executed until terminal operation
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

Stream<Integer> stream = numbers.stream()
    .filter(n -> {
        System.out.println("Filtering: " + n);
        return n > 2;
    })
    .map(n -> {
        System.out.println("Mapping: " + n);
        return n * 2;
    });

// Nothing printed yet! Operations are lazy

stream.findFirst();  // NOW it executes - only until first match found
```

3. **Parallel Processing**
```java
// Sequential
long count = numbers.stream()
    .filter(n -> n % 2 == 0)
    .count();

// Parallel - uses multiple cores
long count = numbers.parallelStream()
    .filter(n -> n % 2 == 0)
    .count();
```

4. **Chaining Operations**
```java
// Clean, readable pipeline
List<String> result = employees.stream()
    .filter(e -> e.getAge() > 25)
    .filter(e -> e.getDepartment().equals("IT"))
    .sorted(Comparator.comparing(Employee::getSalary).reversed())
    .map(Employee::getName)
    .limit(5)
    .collect(Collectors.toList());
```

5. **Reduced Boilerplate Code**
```java
// Old way - lots of code
List<String> names = new ArrayList<>();
for (Employee emp : employees) {
    if (emp.getDepartment().equals("IT")) {
        names.add(emp.getName());
    }
}

// Stream way - one line
List<String> names = employees.stream()
    .filter(e -> e.getDepartment().equals("IT"))
    .map(Employee::getName)
    .collect(Collectors.toList());
```

---

### 3. What is Optional and why is it used?

**Simple Explanation:** Optional is like a box that might or might not contain something. Instead of getting null (and NullPointerException), you get a box you can safely check.

**Problem Without Optional:**

```java
// Traditional approach - prone to NullPointerException
public String getUserEmail(String userId) {
    User user = findUserById(userId);  // Might return null
    
    if (user != null) {
        Address address = user.getAddress();  // Might be null
        if (address != null) {
            return address.getEmail();  // Might be null
        }
    }
    return "default@email.com";
}

// Lots of null checks! What if we forget one? 💥 NullPointerException
```

**Solution With Optional:**

```java
public Optional<String> getUserEmail(String userId) {
    return findUserById(userId)
        .map(User::getAddress)
        .map(Address::getEmail);
}

// Cleaner, safer, no null checks needed!
```

---

#### **Creating Optional:**

```java
// 1. Empty Optional
Optional<String> empty = Optional.empty();

// 2. Optional with value (throws exception if null)
Optional<String> name = Optional.of("John");

// 3. Optional that might be null (recommended)
String str = null;
Optional<String> optional = Optional.ofNullable(str);  // Safe!
```

---

#### **Checking and Retrieving Values:**

```java
Optional<String> optional = Optional.of("Hello");

// 1. isPresent() - check if value exists
if (optional.isPresent()) {
    System.out.println(optional.get());
}

// 2. isEmpty() - Java 11+
if (optional.isEmpty()) {
    System.out.println("No value");
}

// 3. ifPresent() - execute action if present
optional.ifPresent(value -> System.out.println(value));
optional.ifPresent(System.out::println);  // Method reference

// 4. ifPresentOrElse() - Java 9+
optional.ifPresentOrElse(
    value -> System.out.println("Found: " + value),
    () -> System.out.println("Not found")
);
```

---

#### **Getting Values Safely:**

```java
Optional<String> optional = Optional.ofNullable(null);

// 1. get() - DON'T USE! Throws exception if empty
// String value = optional.get();  // NoSuchElementException!

// 2. orElse() - return default value
String value = optional.orElse("Default");
System.out.println(value);  // "Default"

// 3. orElseGet() - return value from supplier (lazy)
String value = optional.orElseGet(() -> "Computed Default");

// 4. orElseThrow() - throw custom exception
String value = optional.orElseThrow(() -> 
    new IllegalArgumentException("Value not found"));
```

---

#### **Transforming Values:**

```java
Optional<String> name = Optional.of("john");

// 1. map() - transform value if present
Optional<String> upperName = name.map(String::toUpperCase);
System.out.println(upperName.get());  // "JOHN"

// 2. flatMap() - when transformation returns Optional
Optional<String> email = name.flatMap(this::findEmailByName);

// 3. filter() - keep value if matches condition
Optional<String> filtered = name.filter(n -> n.length() > 3);
```

---

#### **Real-World Examples:**

```java
// Example 1: User repository
public class UserRepository {
    
    public Optional<User> findById(String id) {
        User user = database.get(id);  // might be null
        return Optional.ofNullable(user);
    }
    
    public Optional<User> findByEmail(String email) {
        return users.stream()
            .filter(u -> u.getEmail().equals(email))
            .findFirst();  // Returns Optional
    }
}

// Usage
UserRepository repo = new UserRepository();

// Old way - null checks
User user = repo.findById("123");
if (user != null) {
    System.out.println(user.getName());
}

// Optional way - cleaner
repo.findById("123")
    .ifPresent(user -> System.out.println(user.getName()));

// With default
String userName = repo.findById("123")
    .map(User::getName)
    .orElse("Guest");
```

```java
// Example 2: Configuration service
public class ConfigService {
    private Map<String, String> config = new HashMap<>();
    
    public Optional<String> getConfig(String key) {
        return Optional.ofNullable(config.get(key));
    }
    
    public int getIntConfig(String key, int defaultValue) {
        return getConfig(key)
            .map(Integer::parseInt)
            .orElse(defaultValue);
    }
}

// Usage
ConfigService service = new ConfigService();

// Get with default
String dbUrl = service.getConfig("db.url")
    .orElse("jdbc:mysql://localhost:3306/default");

int timeout = service.getIntConfig("timeout", 5000);
```

```java
// Example 3: Order processing
public class OrderService {
    
    public Optional<Order> findOrder(String orderId) {
        return Optional.ofNullable(database.findOrder(orderId));
    }
    
    public String getOrderStatus(String orderId) {
        return findOrder(orderId)
            .map(Order::getStatus)
            .orElse("ORDER_NOT_FOUND");
    }
    
    public double calculateDiscount(String orderId) {
        return findOrder(orderId)
            .filter(order -> order.getAmount() > 1000)
            .map(order -> order.getAmount() * 0.1)
            .orElse(0.0);
    }
    
    public void processOrder(String orderId) {
        findOrder(orderId)
            .filter(order -> order.getStatus().equals("PENDING"))
            .ifPresentOrElse(
                this::processPayment,
                () -> System.out.println("Order not found or not pending")
            );
    }
}
```

```java
// Example 4: Chaining Optionals
public class UserService {
    
    public Optional<String> getUserCity(String userId) {
        return findUserById(userId)
            .flatMap(User::getAddress)       // User might not have address
            .flatMap(Address::getCity)       // Address might not have city
            .map(City::getName);             // City has name
    }
    
    // Without Optional - nested null checks
    public String getUserCityOldWay(String userId) {
        User user = findUserById(userId);
        if (user != null) {
            Address address = user.getAddress();
            if (address != null) {
                City city = address.getCity();
                if (city != null) {
                    return city.getName();
                }
            }
        }
        return null;
    }
}
```

---

#### **Why Use Optional?**

**Benefits:**

1. **Avoid NullPointerException**
```java
// Dangerous
String email = user.getEmail().toLowerCase();  // NPE if email is null

// Safe
Optional<String> email = Optional.ofNullable(user.getEmail());
String lowercase = email.map(String::toLowerCase).orElse("no-email");
```

2. **Makes API Clearer**
```java
// Unclear - does this return null?
public User findUser(String id);

// Clear - this might not find a user
public Optional<User> findUser(String id);
```

3. **Forces Proper Null Handling**
```java
// Caller must handle absence
Optional<User> userOpt = repository.findById("123");
User user = userOpt.orElse(defaultUser);  // Must decide what to do
```

4. **Functional Programming Style**
```java
// Chaining operations
repository.findById(userId)
    .map(User::getEmail)
    .filter(email -> email.endsWith("@company.com"))
    .map(String::toUpperCase)
    .ifPresent(this::sendEmail);
```

---

#### **Best Practices:**

```java
// ✅ DO: Use Optional as return type
public Optional<User> findUser(String id) {
    return Optional.ofNullable(database.get(id));
}

// ❌ DON'T: Use Optional as parameter
// public void updateUser(Optional<User> user)  // Bad!
public void updateUser(User user)  // Good!

// ✅ DO: Use orElse/orElseGet
String name = optional.orElse("Default");

// ❌ DON'T: Use get() without checking
// String name = optional.get();  // Can throw exception!

// ✅ DO: Use Optional with streams
List<String> emails = users.stream()
    .map(User::getEmail)
    .filter(Optional::isPresent)
    .map(Optional::get)
    .collect(Collectors.toList());

// ✅ DO: Use ofNullable for potentially null values
Optional<String> opt = Optional.ofNullable(mightBeNull);

// ❌ DON'T: Use of() for nullable values
// Optional<String> opt = Optional.of(mightBeNull);  // NPE if null!
```

---

## Quick Interview Tips:

1. **For OOP:** Always give real-world examples - makes concepts stick
2. **For Polymorphism:** Remember "compile time = overloading, runtime = overriding"
3. **For Singleton:** Mention thread-safety concerns
4. **For Lambda:** Show you know the concise syntax
5. **For Stream:** Emphasize lazy evaluation and parallel processing
6. **For Optional:** Focus on null-safety benefits

**Remember:** Understanding > Memorization. Interviewers appreciate when you can explain WHY, not just WHAT.

Good luck! 🚀
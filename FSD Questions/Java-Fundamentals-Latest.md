# Java Full Stack Interview Guide - Core Java & OOPs

## 1. String Immutability & String Pool

### Is String immutable? Why?

**Answer:** Yes, String is immutable in Java. Once a String object is created, its value cannot be changed.

**Benefits of Immutability:**
1. **Security** - Strings are used in network connections, database URLs, usernames/passwords. If strings were mutable, these could be changed leading to security issues.
2. **Thread Safety** - Since strings cannot be modified, they can be safely shared between multiple threads without synchronization.
3. **Caching** - Hashcode of string is cached and doesn't need recalculation, making it faster for use in HashMap/HashSet.
4. **String Pool Optimization** - Enables string interning for memory efficiency.

### String Constant Pool (String Pool)

The String Pool is a special memory region in Java Heap where String literals are stored. When you create a string literal, JVM checks if it already exists in the pool. If yes, it returns the reference to that string; if no, it creates a new string in the pool.

```java
String s1 = "Hello"; // Stored in String Pool
String s2 = "Hello"; // Points to same object in pool
String s3 = new String("Hello"); // Created in heap memory

System.out.println(s1 == s2); // true (same reference)
System.out.println(s1 == s3); // false (different references)
System.out.println(s1.equals(s3)); // true (same content)
```

### Why StringBuffer and StringBuilder?

Since String is immutable, every modification creates a new object, which is inefficient when you need to perform many modifications.

**StringBuffer:**
- Mutable (can be modified)
- Thread-safe (synchronized methods)
- Slower due to synchronization overhead
- Use in multi-threaded environment

**StringBuilder:**
- Mutable (can be modified)
- NOT thread-safe (no synchronization)
- Faster than StringBuffer
- Use in single-threaded environment

```java
// Performance comparison
String str = "Hello";
str = str + " World"; // Creates 2 objects

StringBuilder sb = new StringBuilder("Hello");
sb.append(" World"); // Modifies same object, more efficient
```

**Cross Question:** What happens when you do `String s = new String("Hello")`?
**Answer:** It creates two objects - one in the string pool (literal "Hello") and one in heap memory (using new keyword). The variable `s` references the heap object.

---

## 2. OOPs Concepts - The 4 Pillars

### The Four Pillars of OOPs:

#### 1. **Encapsulation**
Wrapping data (variables) and code (methods) together as a single unit. Data hiding is achieved by making variables private and providing public getter/setter methods.

```java
public class Employee {
    private String name; // Data hidden
    private double salary;
    
    // Public methods to access/modify private data
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public void setSalary(double salary) {
        if(salary > 0) { // Validation
            this.salary = salary;
        }
    }
}
```

**Benefits:** Data security, flexibility, reusability, easy maintenance.

#### 2. **Inheritance**
A mechanism where a new class (child/subclass) acquires properties and behaviors of an existing class (parent/superclass).

**Types of Inheritance in Java:**
- Single Inheritance
- Multilevel Inheritance
- Hierarchical Inheritance
- (Multiple and Hybrid NOT supported directly - use interfaces)

```java
// Single Inheritance
class Animal {
    void eat() {
        System.out.println("Animal is eating");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Dog is barking");
    }
}

// Multilevel Inheritance
class Puppy extends Dog {
    void weep() {
        System.out.println("Puppy is weeping");
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        Puppy p = new Puppy();
        p.eat();  // From Animal
        p.bark(); // From Dog
        p.weep(); // From Puppy
    }
}
```

#### 3. **Polymorphism**
The ability of an object to take many forms. Same method name behaving differently in different contexts.

**Types:**
- **Compile-time Polymorphism (Static)** - Method Overloading
- **Runtime Polymorphism (Dynamic)** - Method Overriding

**Method Overloading (Compile-time):**
```java
class Calculator {
    // Same method name, different parameters
    int add(int a, int b) {
        return a + b;
    }
    
    int add(int a, int b, int c) {
        return a + b + c;
    }
    
    double add(double a, double b) {
        return a + b;
    }
}
```

**Method Overriding (Runtime):**
```java
class Animal {
    void sound() {
        System.out.println("Animal makes sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}

class Cat extends Animal {
    @Override
    void sound() {
        System.out.println("Cat meows");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal a; // Parent reference
        
        a = new Dog();
        a.sound(); // Dog barks (decided at runtime)
        
        a = new Cat();
        a.sound(); // Cat meows (decided at runtime)
    }
}
```

**Cross Question:** Difference between Overloading and Overriding?
**Answer:**

| Aspect | Method Overloading | Method Overriding |
|--------|-------------------|-------------------|
| When | Compile-time | Runtime |
| Where | Same class | Different classes (inheritance) |
| Parameters | Must be different | Must be same |
| Return type | Can be different | Must be same (covariant allowed) |
| Access modifier | Can be different | Cannot be more restrictive |
| Binding | Static binding | Dynamic binding |

#### 4. **Abstraction**
Hiding implementation details and showing only essential features. Achieved using abstract classes and interfaces.

```java
// Abstract Class
abstract class Vehicle {
    abstract void start(); // Abstract method (no body)
    
    void stop() { // Concrete method
        System.out.println("Vehicle stopped");
    }
}

class Car extends Vehicle {
    @Override
    void start() {
        System.out.println("Car starts with key");
    }
}

// Interface
interface Drawable {
    void draw(); // Abstract by default
}

class Circle implements Drawable {
    @Override
    public void draw() {
        System.out.println("Drawing Circle");
    }
}
```

---

## 3. Classes and Objects

**Class:** Blueprint or template that defines properties and behaviors.
**Object:** Instance of a class; a real-world entity.

```java
// Class definition
class Student {
    // Properties (instance variables)
    String name;
    int rollNo;
    double marks;
    
    // Method (behavior)
    void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Roll No: " + rollNo);
        System.out.println("Marks: " + marks);
    }
    
    void calculateGrade() {
        if(marks >= 90) {
            System.out.println("Grade: A");
        } else if(marks >= 75) {
            System.out.println("Grade: B");
        } else {
            System.out.println("Grade: C");
        }
    }
}

// Creating and using objects
public class Main {
    public static void main(String[] args) {
        // Object creation
        Student s1 = new Student();
        s1.name = "Rahul";
        s1.rollNo = 101;
        s1.marks = 85.5;
        
        Student s2 = new Student();
        s2.name = "Priya";
        s2.rollNo = 102;
        s2.marks = 92.0;
        
        // Using objects
        s1.displayInfo();
        s1.calculateGrade();
        
        s2.displayInfo();
        s2.calculateGrade();
    }
}
```

---

## 4. Interface vs Abstract Class

| Aspect | Interface | Abstract Class |
|--------|-----------|----------------|
| Multiple Inheritance | Supported (can implement multiple) | Not supported |
| Methods | All methods abstract (Java 7), can have default/static (Java 8+) | Can have both abstract and concrete methods |
| Variables | Only public static final (constants) | Can have any type of variables |
| Constructor | Cannot have constructor | Can have constructor |
| Access Modifiers | Methods are public by default | Can use any access modifier |
| When to use | For complete abstraction, multiple inheritance | For partial abstraction, code reusability |

```java
// Interface Example
interface Printable {
    void print(); // public abstract by default
    
    default void scan() { // Default method (Java 8+)
        System.out.println("Scanning document");
    }
}

interface Showable {
    void show();
}

// A class can implement multiple interfaces
class Document implements Printable, Showable {
    @Override
    public void print() {
        System.out.println("Printing document");
    }
    
    @Override
    public void show() {
        System.out.println("Showing document");
    }
}

// Abstract Class Example
abstract class Shape {
    String color; // Instance variable
    
    // Constructor
    Shape(String color) {
        this.color = color;
    }
    
    // Abstract method
    abstract double calculateArea();
    
    // Concrete method
    void displayColor() {
        System.out.println("Color: " + color);
    }
}

class Circle extends Shape {
    double radius;
    
    Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }
    
    @Override
    double calculateArea() {
        return Math.PI * radius * radius;
    }
}
```

**Cross Question:** Can an interface extend another interface?
**Answer:** Yes, an interface can extend one or more interfaces using the `extends` keyword.

```java
interface A {
    void methodA();
}

interface B extends A {
    void methodB();
}

// Class implementing B must implement both methodA and methodB
class C implements B {
    public void methodA() { }
    public void methodB() { }
}
```

---

## 5. Diamond Problem

The diamond problem occurs in multiple inheritance when a class inherits from two classes that have a common parent, creating ambiguity about which parent's method to use.

**Problem Illustration:**
```
      A
     / \
    B   C
     \ /
      D
```

If B and C both override a method from A, which version should D inherit?

**Java's Solution:**
- Java doesn't support multiple inheritance with classes (to avoid this problem)
- Java allows multiple inheritance with interfaces

**With Interfaces (Java 8+):**
```java
interface A {
    default void display() {
        System.out.println("Interface A");
    }
}

interface B extends A {
    default void display() {
        System.out.println("Interface B");
    }
}

interface C extends A {
    default void display() {
        System.out.println("Interface C");
    }
}

// This will cause compilation error unless we override
class D implements B, C {
    @Override
    public void display() {
        // Must explicitly choose or provide new implementation
        B.super.display(); // Calling specific interface method
        // Or provide custom implementation
        System.out.println("Class D");
    }
}
```

**Cross Question:** How does Java resolve the diamond problem with interfaces?
**Answer:** Java requires the implementing class to explicitly override the conflicting method and provide its own implementation or explicitly call the desired interface's default method using `InterfaceName.super.methodName()`.

---

## 6. Concurrency vs Parallelism

**Concurrency:**
- Multiple tasks making progress in overlapping time periods
- Tasks can be executed on a single core through context switching
- About dealing with multiple things at once
- Example: Single chef handling multiple orders by switching between them

**Parallelism:**
- Multiple tasks executing simultaneously at the exact same time
- Requires multiple cores/processors
- About doing multiple things at once
- Example: Multiple chefs each handling one order simultaneously

```java
// Concurrency Example
class Task implements Runnable {
    String name;
    
    Task(String name) {
        this.name = name;
    }
    
    public void run() {
        for(int i = 1; i <= 5; i++) {
            System.out.println(name + " - " + i);
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ConcurrencyExample {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Task("Task1"));
        Thread t2 = new Thread(new Task("Task2"));
        
        t1.start(); // Both tasks will run concurrently
        t2.start(); // (interleaved on single/multiple cores)
    }
}
```

| Aspect | Concurrency | Parallelism |
|--------|-------------|-------------|
| Definition | Multiple tasks in progress | Multiple tasks executing simultaneously |
| Resources | Can work on single core | Requires multiple cores |
| Focus | Task structure and coordination | Performance and speed |
| Example | Multitasking on single CPU | Multi-core processing |

---

## 7. Encapsulation Scenario

**Question:** Is this valid?
```java
private final String name = "raj";
private final String emailId;
```

**Answer:** 
- First line is **VALID** - `final` variable initialized at declaration
- Second line is **INVALID** - `final` variable declared but not initialized

**Explanation:**
- `final` variables must be initialized either at declaration, in instance initializer block, or in constructor
- Once initialized, final variables cannot be changed

**Correct Implementation:**
```java
public class User {
    private final String name = "raj"; // Valid - initialized at declaration
    private final String emailId;      // Declared here
    
    // Must initialize in constructor
    public User(String email) {
        this.emailId = email; // Valid - initialized in constructor
    }
    
    // Alternative: Instance initializer block
    {
        // emailId = "default@example.com"; 
    }
}
```

**Cross Question:** Can we have a final variable without initialization?
**Answer:** No, final variables must be initialized before the constructor completes. For instance variables, initialization can happen at declaration, instance initializer block, or constructor. For local variables, they must be initialized before use.

---

## 8. Stream API - Sum of Even and Odd Numbers

```java
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Method 1: Separate calculations
        int sumEven = numbers.stream()
                            .filter(n -> n % 2 == 0)
                            .mapToInt(Integer::intValue)
                            .sum();
        
        int sumOdd = numbers.stream()
                           .filter(n -> n % 2 != 0)
                           .mapToInt(Integer::intValue)
                           .sum();
        
        System.out.println("Sum of Even: " + sumEven);
        System.out.println("Sum of Odd: " + sumOdd);
        
        // Method 2: Using partitioningBy (single pass)
        Map<Boolean, Integer> sums = numbers.stream()
            .collect(Collectors.partitioningBy(
                n -> n % 2 == 0,
                Collectors.summingInt(Integer::intValue)
            ));
        
        System.out.println("Sum of Even: " + sums.get(true));
        System.out.println("Sum of Odd: " + sums.get(false));
        
        // Method 3: Using reduce
        int evenSum = numbers.stream()
                            .filter(n -> n % 2 == 0)
                            .reduce(0, Integer::sum);
        
        int oddSum = numbers.stream()
                           .filter(n -> n % 2 != 0)
                           .reduce(0, Integer::sum);
        
        System.out.println("Even Sum: " + evenSum);
        System.out.println("Odd Sum: " + oddSum);
    }
}
```

**Output:**
```
Sum of Even: 30
Sum of Odd: 25
```

---

## 9. Collection vs Collections

| Collection | Collections |
|------------|-------------|
| **Interface** | **Utility Class** |
| Root interface of Collection Framework | Helper class with static methods |
| Defines basic operations (add, remove, etc.) | Provides utility methods (sort, reverse, etc.) |
| Cannot be instantiated | Cannot be instantiated (all methods static) |
| Example: List, Set, Queue | Example: Collections.sort(), Collections.reverse() |

```java
import java.util.*;

public class Example {
    public static void main(String[] args) {
        // Collection - Interface
        Collection<String> collection = new ArrayList<>();
        collection.add("Java");
        collection.add("Python");
        
        // Collections - Utility class
        List<Integer> list = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 9));
        Collections.sort(list);              // Sorting
        Collections.reverse(list);           // Reversing
        int max = Collections.max(list);     // Finding max
        
        System.out.println("Sorted & Reversed: " + list);
        System.out.println("Max: " + max);
    }
}
```

---

## 10. Comparator vs Comparable

| Comparable | Comparator |
|------------|------------|
| java.lang package | java.util package |
| compareTo(Object) method | compare(Object, Object) method |
| Natural ordering (single sorting sequence) | Custom ordering (multiple sorting sequences) |
| Modifies the class whose objects are sorted | Separate class for comparison logic |
| Used with Collections.sort(List) | Used with Collections.sort(List, Comparator) |

**Comparable Example:**
```java
import java.util.*;

class Student implements Comparable<Student> {
    String name;
    int marks;
    
    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }
    
    // Natural ordering by marks
    @Override
    public int compareTo(Student other) {
        return this.marks - other.marks; // Ascending order
    }
    
    @Override
    public String toString() {
        return name + ": " + marks;
    }
}

public class ComparableExample {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Raj", 85));
        students.add(new Student("Priya", 92));
        students.add(new Student("Amit", 78));
        
        Collections.sort(students); // Uses compareTo
        System.out.println(students);
    }
}
```

**Comparator Example:**
```java
import java.util.*;

class Student {
    String name;
    int marks;
    
    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }
    
    @Override
    public String toString() {
        return name + ": " + marks;
    }
}

// Comparator for sorting by name
class NameComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.name.compareTo(s2.name);
    }
}

// Comparator for sorting by marks (descending)
class MarksComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s2.marks - s1.marks; // Descending
    }
}

public class ComparatorExample {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Raj", 85));
        students.add(new Student("Priya", 92));
        students.add(new Student("Amit", 78));
        
        // Sort by name
        Collections.sort(students, new NameComparator());
        System.out.println("By Name: " + students);
        
        // Sort by marks (descending)
        Collections.sort(students, new MarksComparator());
        System.out.println("By Marks: " + students);
        
        // Using Lambda (Java 8+)
        Collections.sort(students, (s1, s2) -> s1.name.compareTo(s2.name));
    }
}
```

---

## 11. Array vs ArrayList

| Array | ArrayList |
|-------|-----------|
| Fixed size | Dynamic size (resizable) |
| Can store primitives and objects | Can only store objects (uses wrapper classes for primitives) |
| Length using .length property | Size using .size() method |
| Better performance | Slightly slower due to dynamic resizing |
| No built-in methods | Many utility methods (add, remove, contains, etc.) |
| Syntax: int[] arr = new int[5] | Syntax: ArrayList<Integer> list = new ArrayList<>() |

```java
import java.util.ArrayList;

public class ArrayVsArrayList {
    public static void main(String[] args) {
        // Array
        int[] arr = new int[5];
        arr[0] = 10;
        arr[1] = 20;
        System.out.println("Array length: " + arr.length);
        // arr[5] = 30; // ArrayIndexOutOfBoundsException
        
        // ArrayList
        ArrayList<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30); // Automatically resizes
        list.add(40);
        System.out.println("ArrayList size: " + list.size());
        
        list.remove(0); // Remove by index
        list.contains(20); // Check if exists
        
        System.out.println("ArrayList: " + list);
    }
}
```

---

## 12. ArrayList vs LinkedList

| ArrayList | LinkedList |
|-----------|------------|
| Uses dynamic array internally | Uses doubly linked list internally |
| Fast random access (O(1)) | Slow random access (O(n)) |
| Slow insertion/deletion in middle (O(n)) | Fast insertion/deletion (O(1)) |
| Better for retrieval operations | Better for manipulation operations |
| Less memory (only stores data) | More memory (stores data + two pointers) |
| Implements List interface | Implements List and Deque interfaces |

```java
import java.util.*;

public class ArrayListVsLinkedList {
    public static void main(String[] args) {
        // ArrayList - Better for access
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("A");
        arrayList.add("B");
        arrayList.add("C");
        String element = arrayList.get(1); // Fast O(1)
        System.out.println("ArrayList get(1): " + element);
        
        // LinkedList - Better for insertion/deletion
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("A");
        linkedList.add("B");
        linkedList.add("C");
        linkedList.addFirst("Start"); // Fast O(1)
        linkedList.addLast("End");    // Fast O(1)
        linkedList.remove(2);          // Faster than ArrayList
        
        System.out.println("LinkedList: " + linkedList);
        
        // LinkedList as Queue
        linkedList.offer("X");
        String first = linkedList.poll(); // Remove first
        System.out.println("Dequeue: " + first);
    }
}
```

**When to use:**
- **ArrayList:** When you need fast access and iteration, rare insertions/deletions
- **LinkedList:** When you need frequent insertions/deletions, implementing Queue/Deque

---

## 13. Static Variables and Types of Variables

### Types of Variables in Java:

1. **Local Variables** - Declared inside methods, constructors, or blocks
2. **Instance Variables** - Non-static variables declared in a class
3. **Static Variables** - Class-level variables shared by all instances

```java
public class VariableTypes {
    // Static variable (Class variable)
    static int staticCounter = 0;
    
    // Instance variable
    int instanceCounter = 0;
    String name;
    
    void method() {
        // Local variable
        int localVar = 10;
        System.out.println("Local: " + localVar);
    }
    
    void incrementCounters() {
        staticCounter++;   // Shared across all objects
        instanceCounter++; // Separate for each object
    }
    
    public static void main(String[] args) {
        VariableTypes obj1 = new VariableTypes();
        VariableTypes obj2 = new VariableTypes();
        
        obj1.incrementCounters();
        obj1.incrementCounters();
        
        obj2.incrementCounters();
        
        System.out.println("Static Counter: " + staticCounter);        // 3
        System.out.println("obj1 Instance: " + obj1.instanceCounter);  // 2
        System.out.println("obj2 Instance: " + obj2.instanceCounter);  // 1
    }
}
```

### Static Variable Characteristics:
- Shared among all instances of the class
- Memory allocated once when class is loaded
- Can be accessed using class name
- Initialized only once at class loading
- Belongs to class, not objects

**Cross Question:** Can we access static variables from instance methods and vice versa?
**Answer:** 
- Instance methods can access both static and instance variables
- Static methods can only access static variables directly (need object reference for instance variables)

```java
class Example {
    static int staticVar = 10;
    int instanceVar = 20;
    
    // Instance method - can access both
    void instanceMethod() {
        System.out.println(staticVar);   // OK
        System.out.println(instanceVar); // OK
    }
    
    // Static method - can only access static
    static void staticMethod() {
        System.out.println(staticVar);   // OK
        // System.out.println(instanceVar); // ERROR
        
        Example obj = new Example();
        System.out.println(obj.instanceVar); // OK with object reference
    }
}
```

---

## 14. final Keyword

The `final` keyword is used to create constants, prevent inheritance, and prevent method overriding.

### final with Variables:
```java
class FinalVariable {
    // Must be initialized
    final int CONSTANT = 100;
    final int value;
    
    // Can initialize in constructor
    FinalVariable(int value) {
        this.value = value;
    }
    
    void method() {
        final int localConst = 50;
        // localConst = 60; // ERROR - cannot modify
        
        // this.CONSTANT = 200; // ERROR - cannot modify
    }
}
```

### final with Methods:
```java
class Parent {
    // Cannot be overridden
    final void display() {
        System.out.println("Final method");
    }
}

class Child extends Parent {
    // ERROR - Cannot override final method
    // void display() { }
}
```

### final with Classes:
```java
// Cannot be inherited
final class FinalClass {
    void method() {
        System.out.println("Final class method");
    }
}

// ERROR - Cannot extend final class
// class SubClass extends FinalClass { }
```

**Examples of final classes in Java:** String, Integer, Double, System

---

## 15. static Keyword

### Static Variable:
```java
class Counter {
    static int count = 0; // Shared by all objects
    
    Counter() {
        count++;
    }
    
    static void displayCount() {
        System.out.println("Count: " + count);
    }
}
```

### Static Method:
```java
class MathUtils {
    // Can be called without creating object
    static int add(int a, int b) {
        return a + b;
    }
    
    static int multiply(int a, int b) {
        return a * b;
    }
}

// Usage
int result = MathUtils.add(5, 3); // No object needed
```

### Static Block:
```java
class Database {
    static String url;
    static String username;
    
    // Executed once when class is loaded
    static {
        System.out.println("Static block executed");
        url = "jdbc:mysql://localhost:3306/mydb";
        username = "admin";
    }
    
    static {
        // Can have multiple static blocks
        System.out.println("Second static block");
    }
}
```

**Execution Order:**
1. Static blocks (when class is loaded)
2. Instance blocks (when object is created)
3. Constructor (when object is created)

```java
class ExecutionOrder {
    static {
        System.out.println("1. Static block");
    }
    
    {
        System.out.println("2. Instance block");
    }
    
    ExecutionOrder() {
        System.out.println("3. Constructor");
    }
    
    public static void main(String[] args) {
        new ExecutionOrder();
        new ExecutionOrder();
    }
}

// Output:
// 1. Static block
// 2. Instance block
// 3. Constructor
// 2. Instance block
// 3. Constructor
```

---

## 16. Constructors - Default and Parameterized

### Default Constructor:
```java
class Student {
    String name;
    int rollNo;
    
    // Default constructor (provided by Java if not defined)
    Student() {
        System.out.println("Default constructor called");
        name = "Unknown";
        rollNo = 0;
    }
    
    void display() {
        System.out.println("Name: " + name + ", Roll: " + rollNo);
    }
}
```

### Parameterized Constructor:
```java
class Student {
    String name;
    int rollNo;
    
    // Parameterized constructor
    Student(String name, int rollNo) {
        this.name = name;
        this.rollNo = rollNo;
    }
    
    // Constructor overloading
    Student(String name) {
        this.name = name;
        this.rollNo = 0;
    }
    
    void display() {
        System.out.println("Name: " + name + ", Roll: " + rollNo);
    }
}
```

### Constructor Chaining:
```java
class Employee {
    String name;
    int id;
    double salary;
    
    // Constructor 1
    Employee() {
        this("Unknown", 0, 0.0); // Calls Constructor 3
    }
    
    // Constructor 2
    Employee(String name, int id) {
        this(name, id, 0.0); // Calls Constructor 3
    }
    
    // Constructor 3
    Employee(String name, int id, double salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }
    
    void display() {
        System.out
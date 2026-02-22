# Complete Java Interview Guide

## 1. What is OOPs (Object-Oriented Programming)?

OOPs is a programming paradigm based on the concept of "objects" which contain data (attributes/fields) and code (methods). It focuses on organizing software design around data (objects) rather than functions and logic.

**Real-world analogy:** Think of a Car. It has properties (color, model, speed) and behaviors (start, stop, accelerate).

```java
class Car {
    // Data (attributes)
    String color;
    String model;
    
    // Behavior (methods)
    void start() {
        System.out.println("Car started");
    }
}
```

## 2. OOPs Features

### A. Encapsulation
Wrapping data (variables) and code (methods) together as a single unit and restricting access using access modifiers.

```java
class BankAccount {
    private double balance;  // Hidden from outside
    
    public void deposit(double amount) {
        if(amount > 0) {
            balance += amount;
        }
    }
    
    public double getBalance() {
        return balance;
    }
}
```

### B. Inheritance
Mechanism where one class acquires properties and methods of another class.

```java
class Animal {
    void eat() {
        System.out.println("Eating...");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Barking...");
    }
}

// Dog inherits eat() from Animal
Dog d = new Dog();
d.eat();   // Inherited method
d.bark();  // Own method
```

### C. Polymorphism
Ability to take many forms. One action behaving differently in different situations.

```java
class Shape {
    void draw() {
        System.out.println("Drawing shape");
    }
}

class Circle extends Shape {
    void draw() {  // Overriding
        System.out.println("Drawing circle");
    }
}

Shape s = new Circle();
s.draw();  // Output: Drawing circle (Runtime polymorphism)
```

### D. Abstraction
Hiding implementation details and showing only functionality.

```java
abstract class Vehicle {
    abstract void start();  // No implementation
}

class Bike extends Vehicle {
    void start() {
        System.out.println("Bike starts with kick");
    }
}
```

## 3. Java Features

1. **Simple:** Easy to learn, removes complexity of C++ (pointers, operator overloading)
2. **Object-Oriented:** Everything is an object
3. **Platform Independent:** Write Once, Run Anywhere (WORA)
4. **Secure:** No explicit pointers, bytecode verification, security manager
5. **Robust:** Strong memory management, exception handling, garbage collection
6. **Architecture Neutral:** Bytecode can run on any platform
7. **Portable:** Bytecode is portable across platforms
8. **Multithreaded:** Supports concurrent execution
9. **Dynamic:** Runtime binding, can load classes at runtime
10. **High Performance:** JIT (Just-In-Time) compiler improves performance

## 4. Java Architecture

```
Source Code (.java)
        ↓
Java Compiler (javac)
        ↓
Bytecode (.class)
        ↓
JVM (Java Virtual Machine)
    ├── ClassLoader
    ├── Bytecode Verifier
    ├── Execution Engine
    │   ├── Interpreter
    │   └── JIT Compiler
    └── Runtime Data Areas
        ├── Method Area
        ├── Heap
        ├── Stack
        ├── PC Registers
        └── Native Method Stack
```

**Key Components:**
- **JDK (Java Development Kit):** Development tools + JRE
- **JRE (Java Runtime Environment):** Libraries + JVM
- **JVM (Java Virtual Machine):** Executes bytecode

## 5. Compiling and Execution Procedure

### Compilation Phase:
```
Step 1: Write HelloWorld.java
Step 2: javac HelloWorld.java
Step 3: Generates HelloWorld.class (bytecode)
```

### Execution Phase:
```
Step 1: java HelloWorld
Step 2: ClassLoader loads the class
Step 3: Bytecode Verifier checks security
Step 4: Execution Engine executes
        ├── Interpreter (line by line)
        └── JIT Compiler (hot code → native code)
```

**Example:**
```java
// HelloWorld.java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

// Compile: javac HelloWorld.java
// Run: java HelloWorld
```

## 6. Stack and Heap

### Stack Memory
- Stores **local variables** and **method calls**
- LIFO (Last In First Out)
- Thread-specific (each thread has its own stack)
- Automatically managed
- Faster access
- Limited size (StackOverflowError)

### Heap Memory
- Stores **objects** and **instance variables**
- Shared among all threads
- Managed by Garbage Collector
- Slower access
- Larger size (OutOfMemoryError)

```java
public class MemoryDemo {
    public static void main(String[] args) {
        int x = 10;              // Stack: primitive variable
        String str = "Hello";    // Stack: reference variable
                                  // Heap: String object "Hello"
        
        Person p = new Person(); // Stack: reference 'p'
                                  // Heap: Person object
    }
}

class Person {
    String name;  // Heap: instance variable
}
```

**Memory Layout:**
```
Stack:                    Heap:
+----------------+       +------------------+
| x = 10         |       | String "Hello"   |
| str → [ref]    |------>+------------------+
| p → [ref]      |------>| Person object    |
+----------------+       |   name = null    |
                         +------------------+
```

## 7. Arrays Concept

Arrays are containers that hold fixed number of values of a single type.

```java
// Declaration and Initialization
int[] arr1 = new int[5];              // Array of size 5
int[] arr2 = {1, 2, 3, 4, 5};        // Direct initialization
String[] names = {"John", "Alice"};   // String array

// Accessing elements
System.out.println(arr2[0]);  // Output: 1

// 2D Array
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6}
};
System.out.println(matrix[1][2]);  // Output: 6

// Array properties
System.out.println(arr2.length);  // Output: 5

// Iterating
for(int i = 0; i < arr2.length; i++) {
    System.out.println(arr2[i]);
}

// Enhanced for loop
for(int num : arr2) {
    System.out.println(num);
}
```

**Important Points:**
- Arrays are objects in Java (stored in heap)
- Array index starts from 0
- ArrayIndexOutOfBoundsException for invalid index
- Arrays.sort(), Arrays.toString() utility methods

## 8. Boxing and Unboxing

### Autoboxing
Converting primitive type to wrapper class object automatically.

### Unboxing
Converting wrapper class object to primitive type automatically.

```java
// Autoboxing - Primitive to Object
int num = 10;
Integer obj = num;  // Autoboxing (int → Integer)

// Unboxing - Object to Primitive
Integer obj2 = 20;
int num2 = obj2;    // Unboxing (Integer → int)

// In Collections (requires objects, not primitives)
List<Integer> list = new ArrayList<>();
list.add(5);        // Autoboxing happens
int value = list.get(0);  // Unboxing happens

// Wrapper Classes
Integer intObj = 100;       // int wrapper
Double doubleObj = 10.5;    // double wrapper
Character charObj = 'A';    // char wrapper
Boolean boolObj = true;     // boolean wrapper
```

**Why Wrapper Classes?**
- Collections can only store objects
- Provide utility methods (Integer.parseInt())
- Nullable values (Integer can be null, int cannot)

## 9. Method Overloading

Same method name with different parameters (number, type, or order) in the same class. **Compile-time polymorphism.**

```java
class Calculator {
    // Different number of parameters
    int add(int a, int b) {
        return a + b;
    }
    
    int add(int a, int b, int c) {
        return a + b + c;
    }
    
    // Different type of parameters
    double add(double a, double b) {
        return a + b;
    }
    
    // Different order of parameters
    String add(String a, int b) {
        return a + b;
    }
    
    String add(int a, String b) {
        return a + b;
    }
}

// Usage
Calculator calc = new Calculator();
calc.add(5, 10);           // Calls first method
calc.add(5, 10, 15);       // Calls second method
calc.add(5.5, 10.5);       // Calls third method
calc.add("Value: ", 10);   // Calls fourth method
```

**Rules:**
- Must have different parameter lists
- Return type alone is NOT enough
- Access modifier can be different
- Can throw different exceptions

## 10. Object Class Methods

Every class in Java inherits from Object class. Key methods:

```java
class Employee {
    int id;
    String name;
    
    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // 1. toString() - String representation of object
    @Override
    public String toString() {
        return "Employee[id=" + id + ", name=" + name + "]";
    }
    
    // 2. equals() - Compare objects
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Employee emp = (Employee) obj;
        return id == emp.id;
    }
    
    // 3. hashCode() - Hash value for object
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
    
    // 4. clone() - Create copy of object
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    // 5. getClass() - Returns runtime class
    public void showClass() {
        System.out.println(getClass().getName());
    }
    
    // 6. finalize() - Called by GC before destroying
    @Override
    protected void finalize() throws Throwable {
        System.out.println("Object is garbage collected");
    }
}

// Usage
Employee e1 = new Employee(1, "John");
Employee e2 = new Employee(1, "John");

System.out.println(e1.toString());        // Employee[id=1, name=John]
System.out.println(e1.equals(e2));        // true
System.out.println(e1.hashCode());        // Hash value
System.out.println(e1.getClass());        // class Employee
```

**Other methods:**
- **wait()**, **notify()**, **notifyAll()** - For thread synchronization

## 11. Constructor

Special method used to initialize objects. Same name as class, no return type.

```java
class Student {
    int rollNo;
    String name;
    
    // 1. Default Constructor (provided by Java if not defined)
    Student() {
        System.out.println("Default constructor");
    }
    
    // 2. Parameterized Constructor
    Student(int rollNo, String name) {
        this.rollNo = rollNo;
        this.name = name;
    }
    
    // 3. Copy Constructor
    Student(Student s) {
        this.rollNo = s.rollNo;
        this.name = s.name;
    }
    
    // 4. Constructor Overloading
    Student(int rollNo) {
        this(rollNo, "Unknown");  // Calls parameterized constructor
    }
}

// Usage
Student s1 = new Student();              // Default
Student s2 = new Student(101, "Alice");  // Parameterized
Student s3 = new Student(s2);            // Copy
Student s4 = new Student(102);           // Overloaded
```

**Constructor Chaining:**
```java
class Parent {
    Parent() {
        System.out.println("Parent constructor");
    }
}

class Child extends Parent {
    Child() {
        super();  // Calls parent constructor (implicit if not written)
        System.out.println("Child constructor");
    }
}
```

**Key Points:**
- Called automatically when object is created
- Can't be static, final, abstract
- Can be private (Singleton pattern)

## 12. Static in Detail

**Static** means class-level (shared among all instances).

### Static Variable
```java
class Counter {
    static int count = 0;  // Class variable (shared)
    int id;                 // Instance variable (unique)
    
    Counter() {
        count++;           // Incremented for each object
        id = count;
    }
}

Counter c1 = new Counter();  // count = 1, c1.id = 1
Counter c2 = new Counter();  // count = 2, c2.id = 2
System.out.println(Counter.count);  // 2 (accessed via class name)
```

### Static Method
```java
class MathUtil {
    // Can be called without creating object
    static int add(int a, int b) {
        return a + b;
    }
    
    static void display() {
        // Can access only static members
        System.out.println("Static method");
        // Cannot use 'this' keyword
    }
}

// Usage
int result = MathUtil.add(5, 10);  // No object needed
```

### Static Block
```java
class Database {
    static String url;
    
    // Executed when class is loaded (before main method)
    static {
        System.out.println("Static block executed");
        url = "jdbc:mysql://localhost:3306/mydb";
    }
    
    public static void main(String[] args) {
        System.out.println("Main method");
    }
}

// Output:
// Static block executed
// Main method
```

### Static Class (Nested)
```java
class Outer {
    static int x = 10;
    
    // Static nested class
    static class Inner {
        void display() {
            System.out.println("x = " + x);  // Can access static members
        }
    }
}

// Usage
Outer.Inner obj = new Outer.Inner();  // No Outer object needed
obj.display();
```

**Important Points:**
- Static members loaded during class loading
- Static methods can't access instance variables
- Main method is static (entry point, no object needed)
- Static variables: one copy per class
- Instance variables: one copy per object

## 13. Inheritance and Polymorphism

### Inheritance
Acquiring properties and behaviors from parent class.

```java
// Single Inheritance
class Animal {
    void eat() {
        System.out.println("Eating...");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Barking...");
    }
}

// Multilevel Inheritance
class Puppy extends Dog {
    void weep() {
        System.out.println("Weeping...");
    }
}

// Hierarchical Inheritance
class Cat extends Animal {
    void meow() {
        System.out.println("Meowing...");
    }
}
```

**Types:**
- Single: A → B
- Multilevel: A → B → C
- Hierarchical: A → B, A → C
- Multiple: NOT supported (use interfaces)
- Hybrid: NOT supported directly

### Polymorphism

**A. Compile-time (Static) - Method Overloading**
```java
class Demo {
    void show(int a) { }
    void show(String a) { }
}
```

**B. Runtime (Dynamic) - Method Overriding**
```java
class Parent {
    void display() {
        System.out.println("Parent display");
    }
}

class Child extends Parent {
    @Override
    void display() {
        System.out.println("Child display");
    }
}

// Runtime polymorphism
Parent obj = new Child();  // Upcasting
obj.display();  // Output: Child display (decided at runtime)
```

**Method Overriding Rules:**
- Same method signature in parent and child
- Access modifier: same or more accessible
- Return type: same or covariant (subtype)
- Cannot override: static, final, private methods
- Use @Override annotation

```java
class Bank {
    int getRateOfInterest() {
        return 0;
    }
}

class SBI extends Bank {
    int getRateOfInterest() {
        return 7;
    }
}

class ICICI extends Bank {
    int getRateOfInterest() {
        return 8;
    }
}

// Usage
Bank b1 = new SBI();
Bank b2 = new ICICI();
System.out.println(b1.getRateOfInterest());  // 7
System.out.println(b2.getRateOfInterest());  // 8
```

## 14. Abstract Class in Detail

Class declared with **abstract** keyword. Cannot be instantiated. Can have abstract and concrete methods.

```java
abstract class Shape {
    String color;
    
    // Abstract method (no body)
    abstract double calculateArea();
    
    // Concrete method (has body)
    void setColor(String color) {
        this.color = color;
    }
    
    // Constructor allowed
    Shape() {
        System.out.println("Shape constructor");
    }
}

class Circle extends Shape {
    double radius;
    
    Circle(double radius) {
        this.radius = radius;
    }
    
    // Must implement abstract method
    @Override
    double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    double length, width;
    
    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    
    @Override
    double calculateArea() {
        return length * width;
    }
}

// Usage
Shape s1 = new Circle(5);
Shape s2 = new Rectangle(4, 6);
System.out.println(s1.calculateArea());  // 78.54
System.out.println(s2.calculateArea());  // 24.0
```

**Key Features:**
- 0-100% abstraction (can have both abstract and concrete methods)
- Can have constructors
- Can have instance variables
- Can have static methods
- Cannot be instantiated
- Can have final methods
- Can extend only one class
- Use when: classes share common behavior with some variations

**Abstract vs Concrete:**
```java
abstract class Vehicle {
    abstract void start();           // Abstract - no implementation
    
    void stop() {                     // Concrete - has implementation
        System.out.println("Vehicle stopped");
    }
}
```

## 15. Interface Methods

Interface is a blueprint of a class. Provides full abstraction.

```java
interface Animal {
    // 1. Abstract methods (public abstract by default)
    void eat();
    void sleep();
    
    // 2. Default method (Java 8+)
    default void breathe() {
        System.out.println("Breathing...");
    }
    
    // 3. Static method (Java 8+)
    static void info() {
        System.out.println("Animals are living beings");
    }
    
    // 4. Private method (Java 9+)
    private void helper() {
        System.out.println("Helper method");
    }
    
    // Variables (public static final by default)
    int LEGS = 4;
}

class Dog implements Animal {
    @Override
    public void eat() {
        System.out.println("Dog eats");
    }
    
    @Override
    public void sleep() {
        System.out.println("Dog sleeps");
    }
    
    // Can override default method
    @Override
    public void breathe() {
        System.out.println("Dog breathes");
    }
}

// Usage
Animal dog = new Dog();
dog.eat();           // Dog eats
dog.sleep();         // Dog sleeps
dog.breathe();       // Dog breathes (overridden)
Animal.info();       // Static method call via interface
```

### Interface Types:

**A. Marker Interface** (Empty interface)
```java
interface Serializable {
    // No methods
}
```

**B. Functional Interface** (Single abstract method - Java 8)
```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
    
    // Can have default and static methods
    default void display() {
        System.out.println("Calculator");
    }
}

// Lambda expression
Calculator add = (a, b) -> a + b;
System.out.println(add.calculate(5, 3));  // 8
```

**C. Multiple Inheritance**
```java
interface Printable {
    void print();
}

interface Showable {
    void show();
}

class Document implements Printable, Showable {
    public void print() {
        System.out.println("Printing...");
    }
    
    public void show() {
        System.out.println("Showing...");
    }
}
```

### Interface Features Summary:

| Feature | Details |
|---------|---------|
| Abstract Methods | public abstract by default |
| Default Methods | Can have implementation (Java 8+) |
| Static Methods | Called via interface name (Java 8+) |
| Private Methods | Helper methods (Java 9+) |
| Variables | public static final by default |
| Multiple Inheritance | A class can implement multiple interfaces |

**Abstract Class vs Interface:**

| Abstract Class | Interface |
|----------------|-----------|
| Can have constructors | No constructors |
| Can have instance variables | Only constants (static final) |
| 0-100% abstraction | 100% abstraction (before Java 8) |
| extends one class | implements multiple interfaces |
| Can have any access modifiers | Methods are public by default |

---

# Additional Important Interview Questions

## 16. Exception Handling

```java
try {
    int result = 10 / 0;  // ArithmeticException
} catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero");
} finally {
    System.out.println("Always executed");
}

// Custom exception
class InvalidAgeException extends Exception {
    InvalidAgeException(String message) {
        super(message);
    }
}

void validate(int age) throws InvalidAgeException {
    if(age < 18) {
        throw new InvalidAgeException("Age must be 18+");
    }
}
```

## 17. Collections Framework

```java
// List - Ordered, allows duplicates
List<String> list = new ArrayList<>();
list.add("Java");
list.add("Python");

// Set - Unordered, no duplicates
Set<Integer> set = new HashSet<>();
set.add(10);
set.add(20);

// Map - Key-value pairs
Map<String, Integer> map = new HashMap<>();
map.put("Alice", 25);
map.put("Bob", 30);
```

## 18. String vs StringBuilder vs StringBuffer

```java
String s1 = "Hello";          // Immutable
s1 = s1 + " World";           // Creates new object

StringBuilder sb = new StringBuilder("Hello");
sb.append(" World");          // Mutable, not thread-safe, faster

StringBuffer sbf = new StringBuffer("Hello");
sbf.append(" World");         // Mutable, thread-safe, slower
```

## 19. Final Keyword

```java
final int MAX = 100;           // Constant variable
final void display() { }       // Cannot be overridden
final class Utility { }        // Cannot be inherited

class Parent {
    final void show() {
        System.out.println("Final method");
    }
}

class Child extends Parent {
    // Cannot override final method
    // void show() { }  // Compilation error
}
```

## 20. this vs super

```java
class Parent {
    int x = 10;
    
    void display() {
        System.out.println("Parent");
    }
}

class Child extends Parent {
    int x = 20;
    
    void display() {
        System.out.println(this.x);      // 20 (current class)
        System.out.println(super.x);     // 10 (parent class)
        super.display();                 // Parent method
    }
}
```

## 21. Multithreading

```java
// Method 1: Extend Thread
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread running");
    }
}
MyThread t = new MyThread();
t.start();

// Method 2: Implement Runnable
class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Runnable running");
    }
}
Thread t2 = new Thread(new MyRunnable());
t2.start();

// Lambda (Java 8+)
Thread t3 = new Thread(() -> {
    System.out.println("Lambda thread");
});
t3.start();
```

## 22. Generics

```java
// Generic class
class Box<T> {
    private T value;
    
    void set(T value) {
        this.value = value;
    }
    
    T get() {
        return value;
    }
}

Box<Integer> intBox = new Box<>();
intBox.set(10);
int value = intBox.get();

// Generic method
<T> void printArray(T[] array) {
    for(T element : array) {
        System.out.println(element);
    }
}
```

## 23. Access Modifiers

| Modifier | Class | Package | Subclass | World |
|----------|-------|---------|----------|-------|
| private | ✓ | ✗ | ✗ | ✗ |
| default | ✓ | ✓ | ✗ | ✗ |
| protected | ✓ | ✓ | ✓ | ✗ |
| public | ✓ | ✓ | ✓ | ✓ |

## 24. equals() vs ==

```java
String s1 = new String("Hello");
String s2 = new String("Hello");
String s3 = "Hello";
String s4 = "Hello";

System.out.println(s1 == s2);       // false (different objects)
System.out.println(s1.equals(s2));  // true (same content)
System.out.println(s3 == s4);       // true (string pool)
```

## 25. Serialization

```java
import java.io.*;

class Student implements Serializable {
    int id;
    String name;
    transient String password;  // Won't be serialized
}

// Serialize
ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.ser"));
out.writeObject(student);

// Deserialize
ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.ser"));
Student s = (Student) in.readObject();
```

---

## Quick Revision Checklist

✓ OOPs concepts and features  
✓ Java features and architecture  
✓ Memory management (Stack & Heap)  
✓ Arrays and Collections  
✓ Boxing/Unboxing  
✓ Method Overloading vs Overriding  
✓ Object class methods  
✓ Constructors and constructor chaining  
✓ Static keyword (variable, method, block, class)  
✓ Inheritance types and polymorphism  
✓ Abstract classes vs Interfaces  
✓ Interface methods (default, static, private)  
✓ Exception handling  
✓ String handling  
✓ Final keyword  
✓ this vs super  
✓ Multithreading basics  
✓ Generics  
✓ Access modifiers  
✓ Serialization  

**Pro Tips for Interview:**
- Always provide real-world examples
- Draw diagrams for architecture questions
- Write clean, compilable code
- Explain time/space complexity where relevant
- Discuss trade-offs (e.g., ArrayList vs LinkedList)
- Be ready to explain "Why?" not just "What?"

Good luck with your interview! 🚀
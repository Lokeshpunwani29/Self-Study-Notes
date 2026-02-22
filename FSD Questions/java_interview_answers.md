# Java Interview Questions - Easy to Understand Answers

## Question 1: Explain Static Keyword in Java

**Simple Explanation:**
`static` means "belongs to the class itself, not to individual objects." It's shared by all instances.

**Think of it like this:** 
- Non-static = Each person has their own phone (unique to each object)
- Static = Everyone shares the same office printer (common to all)

### Static Variable (Class Variable)

```java
class Student {
    String name;              // Instance variable (unique per student)
    static String school = "ABC School";  // Static variable (shared by all)
    static int studentCount = 0;         // Shared counter
    
    Student(String name) {
        this.name = name;
        studentCount++;  // Increments for every student
    }
    
    void display() {
        System.out.println(name + " studies at " + school);
    }
}

// Usage
Student s1 = new Student("Alice");
Student s2 = new Student("Bob");
Student s3 = new Student("Charlie");

System.out.println(Student.studentCount);  // 3 (shared by all)
System.out.println(Student.school);         // ABC School
```

**Memory:**
```
Instance Variables (Heap):
s1 → name: "Alice"
s2 → name: "Bob"
s3 → name: "Charlie"

Static Variables (Method Area - shared):
school: "ABC School"
studentCount: 3
```

### Static Method

```java
class Calculator {
    // Static method - can be called without creating object
    static int add(int a, int b) {
        return a + b;
    }
    
    // Instance method - needs object
    int multiply(int a, int b) {
        return a * b;
    }
}

// Usage
int sum = Calculator.add(5, 3);  // No object needed! ✓

// For instance method, object is required
Calculator calc = new Calculator();
int product = calc.multiply(5, 3);
```

**Rules for Static Methods:**
- ✅ Can access static variables
- ✅ Can call other static methods
- ❌ Cannot access instance variables
- ❌ Cannot use `this` keyword

```java
class Example {
    int instanceVar = 10;        // Instance variable
    static int staticVar = 20;   // Static variable
    
    static void staticMethod() {
        System.out.println(staticVar);      // ✓ OK
        // System.out.println(instanceVar); // ✗ ERROR - cannot access
        // System.out.println(this.staticVar); // ✗ ERROR - cannot use 'this'
    }
    
    void instanceMethod() {
        System.out.println(instanceVar);  // ✓ OK
        System.out.println(staticVar);    // ✓ OK - can access static
    }
}
```

### Static Block

Executes when class is loaded (before main method, before any object creation).

```java
class Database {
    static String connectionUrl;
    
    // Static block - runs once when class is loaded
    static {
        System.out.println("Loading database configuration...");
        connectionUrl = "jdbc:mysql://localhost:3306/mydb";
        System.out.println("Database ready!");
    }
    
    public static void main(String[] args) {
        System.out.println("Main method");
        System.out.println(connectionUrl);
    }
}

// Output:
// Loading database configuration...
// Database ready!
// Main method
// jdbc:mysql://localhost:3306/mydb
```

### Static Nested Class

```java
class Outer {
    static int x = 10;
    int y = 20;
    
    // Static nested class
    static class Inner {
        void display() {
            System.out.println("x = " + x);  // Can access static members
            // System.out.println("y = " + y); // ERROR - cannot access instance
        }
    }
}

// Usage - No outer object needed
Outer.Inner inner = new Outer.Inner();
inner.display();
```

### Real-World Example: Singleton Pattern

```java
class Database {
    // Single static instance
    private static Database instance = null;
    
    // Private constructor - prevents direct instantiation
    private Database() {
        System.out.println("Database connection created");
    }
    
    // Static method to get instance
    public static Database getInstance() {
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }
}

// Usage
Database db1 = Database.getInstance();  // Creates connection
Database db2 = Database.getInstance();  // Returns same instance
System.out.println(db1 == db2);  // true - same object
```

---

## Question 2: Difference between List, Set, and Map

**Simple Analogy:**
- **List** = Shopping list (ordered, can have duplicates, access by position)
- **Set** = Unique badge collection (no duplicates, no order)
- **Map** = Phone book (key-value pairs, name → phone number)

### List - Ordered Collection with Duplicates

```java
List<String> shoppingList = new ArrayList<>();
shoppingList.add("Milk");      // Index 0
shoppingList.add("Bread");     // Index 1
shoppingList.add("Milk");      // Index 2 - Duplicate allowed!
shoppingList.add("Eggs");      // Index 3

System.out.println(shoppingList);  // [Milk, Bread, Milk, Eggs]
System.out.println(shoppingList.get(0));  // Access by index: Milk
```

**Key Features:**
- ✅ Maintains insertion order
- ✅ Allows duplicates
- ✅ Access by index (position)
- 📦 Implementations: ArrayList, LinkedList, Vector

### Set - Unique Collection (No Duplicates)

```java
Set<String> uniqueNames = new HashSet<>();
uniqueNames.add("Alice");
uniqueNames.add("Bob");
uniqueNames.add("Alice");  // Duplicate - will be ignored
uniqueNames.add("Charlie");

System.out.println(uniqueNames);  // [Alice, Bob, Charlie] - no duplicate
System.out.println(uniqueNames.size());  // 3, not 4
```

**Key Features:**
- ✅ No duplicates (automatically removed)
- ❌ No guaranteed order (HashSet)
- ❌ No index access
- 📦 Implementations: HashSet, LinkedHashSet, TreeSet

### Map - Key-Value Pairs

```java
Map<String, String> phoneBook = new HashMap<>();
phoneBook.put("Alice", "123-456-7890");
phoneBook.put("Bob", "987-654-3210");
phoneBook.put("Charlie", "555-123-4567");
phoneBook.put("Alice", "111-222-3333");  // Updates Alice's number

System.out.println(phoneBook.get("Bob"));  // 987-654-3210
System.out.println(phoneBook.containsKey("Alice"));  // true
```

**Key Features:**
- ✅ Stores key-value pairs
- ✅ Keys must be unique (values can duplicate)
- ✅ Fast lookup by key
- 📦 Implementations: HashMap, LinkedHashMap, TreeMap, Hashtable

### Comparison Table

| Feature | List | Set | Map |
|---------|------|-----|-----|
| Duplicates | ✅ Allowed | ❌ Not allowed | Keys: No, Values: Yes |
| Order | ✅ Maintains | Depends on implementation | Depends on implementation |
| Null values | ✅ Multiple nulls | One null (HashSet) | One null key, multiple null values |
| Access | By index | No index | By key |
| Example | [1, 2, 2, 3] | [1, 2, 3] | {a→1, b→2, c→3} |

### Visual Example

```java
// LIST - Like a numbered line
List: [Apple, Banana, Apple, Cherry]
       ↓      ↓       ↓      ↓
     Index:  0      1       2      3

// SET - Like a bag of unique items
Set: {Apple, Banana, Cherry}  // No duplicates, no index

// MAP - Like a dictionary
Map: {
    "Apple"  → "$2",
    "Banana" → "$1",
    "Cherry" → "$3"
}
```

### Practical Examples

```java
// List - Order matters, duplicates needed
List<String> tasks = new ArrayList<>();
tasks.add("Wake up");
tasks.add("Breakfast");
tasks.add("Work");
tasks.add("Breakfast");  // Can eat breakfast twice!

// Set - Unique items only
Set<String> uniqueEmails = new HashSet<>();
uniqueEmails.add("user@email.com");
uniqueEmails.add("user@email.com");  // Ignored
uniqueEmails.add("admin@email.com");

// Map - Looking up by key
Map<String, Integer> studentGrades = new HashMap<>();
studentGrades.put("Alice", 95);
studentGrades.put("Bob", 87);
studentGrades.put("Charlie", 92);
System.out.println("Alice's grade: " + studentGrades.get("Alice"));
```

---

## Question 3: Difference between JDK, JRE, and JVM?

**Simple Analogy:**
- **JDK** = Complete Kitchen (has everything - stove, utensils, recipes)
- **JRE** = Kitchen for eating (has stove and utensils, but no recipes to create new dishes)
- **JVM** = The Stove (actually cooks/runs the food/code)

### Visual Hierarchy

```
┌─────────────────────────────────────────┐
│             JDK                          │
│  (Java Development Kit)                  │
│  ┌────────────────────────────────────┐ │
│  │         JRE                         │ │
│  │  (Java Runtime Environment)         │ │
│  │  ┌──────────────────────────────┐  │ │
│  │  │       JVM                     │  │ │
│  │  │  (Java Virtual Machine)       │  │ │
│  │  │   - Executes bytecode         │  │ │
│  │  └──────────────────────────────┘  │ │
│  │  + Libraries (java.lang, java.io)  │ │
│  └────────────────────────────────────┘ │
│  + Development Tools                     │
│    (javac, javadoc, debugger)            │
└─────────────────────────────────────────┘
```

### JVM (Java Virtual Machine)

**What it does:** Executes Java bytecode (runs your program)

```
HelloWorld.class (bytecode)
         ↓
    ┌─────────┐
    │   JVM   │  ← Executes the code
    └─────────┘
         ↓
    Output: Hello World
```

**Components:**
- **Class Loader**: Loads .class files
- **Bytecode Verifier**: Checks code is safe
- **Execution Engine**: Actually runs the code
  - Interpreter: Reads bytecode line by line
  - JIT Compiler: Converts hot code to native machine code

**Example:**
```java
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

// When you run: java Hello
// JVM loads, verifies, and executes the bytecode
```

**Key Point:** JVM is platform-specific (Windows JVM ≠ Linux JVM), but bytecode is same!

### JRE (Java Runtime Environment)

**What it does:** Provides environment to run Java applications

**Contains:**
- JVM (to execute)
- Libraries (java.lang, java.util, etc.)
- Other support files

**Use case:** You only want to RUN Java programs, not develop them.

```
JRE = JVM + Libraries

Example: If you download Minecraft (Java game)
         You need JRE to run it
         You don't need JDK (unless you're modding)
```

### JDK (Java Development Kit)

**What it does:** Provides tools to DEVELOP Java applications

**Contains:**
- JRE (to run)
- Development tools:
  - `javac` (compiler)
  - `javadoc` (documentation)
  - `jar` (archiver)
  - Debugger
  - Other utilities

**Use case:** You want to WRITE and DEVELOP Java programs.

```
JDK = JRE + Development Tools

Example: You're a Java developer
         You need JDK to write, compile, and run code
```

### Practical Example

```bash
# Step 1: Write code (need a text editor)
# HelloWorld.java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

# Step 2: Compile (need JDK's javac)
javac HelloWorld.java
# Creates: HelloWorld.class (bytecode)

# Step 3: Run (need JRE's java command)
java HelloWorld
# JVM executes the bytecode
# Output: Hello World
```

### Comparison Table

| Feature | JVM | JRE | JDK |
|---------|-----|-----|-----|
| Purpose | Execute bytecode | Run Java apps | Develop Java apps |
| Contains | Execution engine | JVM + Libraries | JRE + Dev tools |
| Who needs it | Everyone | End users | Developers |
| Example tools | - | java command | javac, javadoc, jar |
| Size | Smallest | Medium | Largest |

### Real-World Analogy Extended

```
Scenario: Making Coffee

JVM = Coffee Machine
     - Actually makes the coffee

JRE = Coffee Machine + Coffee Beans + Water
     - Everything to make/drink coffee

JDK = Coffee Machine + Coffee Beans + Water + Recipe Book + Roasting Equipment
     - Everything to make coffee AND create new coffee recipes
```

---

## Question 4: How does HashMap work internally?

**Simple Explanation:**
HashMap is like a smart filing cabinet where items are stored in buckets (drawers) based on their hash code. It provides O(1) average lookup time!

### Step-by-Step Working

#### Step 1: Hash Code Calculation

```java
Map<String, Integer> map = new HashMap<>();
map.put("John", 25);

// Internal process:
String key = "John";
int hashCode = key.hashCode();  // e.g., 2314539
System.out.println("HashCode of 'John': " + hashCode);
```

#### Step 2: Bucket Location (Index Calculation)

```java
// HashMap has array of buckets (default size = 16)
int index = hashCode & (capacity - 1);  // Bitwise AND
// Example: 2314539 & 15 = 11
// So "John" goes to bucket[11]

/*
Buckets Array (initially):
[0] → null
[1] → null
...
[11] → null  ← "John" will go here
...
[15] → null
*/
```

#### Step 3: Storing Entry (Node)

```java
class Node {
    int hash;          // Stored hash
    String key;        // Key ("John")
    Integer value;     // Value (25)
    Node next;         // Next node (for collision)
    
    Node(int hash, String key, Integer value, Node next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }
}

// Storage structure:
// bucket[11] → Node(hash, "John", 25, null)
```

### Hash Collision Handling

**Collision:** When two keys have same bucket index

```java
Map<String, Integer> map = new HashMap<>();
map.put("FB", 100);  // hashCode = 2236, index = 4
map.put("Ea", 200);  // hashCode = 2236, index = 4  ← Collision!

/*
Before Java 8 (LinkedList):
bucket[4] → Node("FB", 100) → Node("Ea", 200) → null

After Java 8 (Tree when size > 8):
If bucket has > 8 entries, LinkedList converts to Red-Black Tree
bucket[4] → TreeNode (for better performance)
*/
```

### Complete Example with Visualization

```java
public class HashMapDemo {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        
        // Adding entries
        map.put("Alice", 25);   // hashCode → bucket[5]
        map.put("Bob", 30);     // hashCode → bucket[12]
        map.put("Charlie", 35); // hashCode → bucket[5]  ← Collision!
        
        /*
        Internal Structure:
        
        Buckets:
        [0]  → null
        [1]  → null
        ...
        [5]  → Node("Alice", 25) → Node("Charlie", 35) → null
        ...
        [12] → Node("Bob", 30) → null
        ...
        [15] → null
        */
        
        // Retrieving value
        Integer age = map.get("Charlie");
        
        /*
        Get Process:
        1. Calculate hash of "Charlie"
        2. Find bucket index = 5
        3. Go to bucket[5]
        4. Traverse linked list:
           - Check "Alice" - equals() returns false
           - Check "Charlie" - equals() returns true ✓
        5. Return value: 35
        */
    }
}
```

### Load Factor and Rehashing

```java
// Default capacity = 16
// Default load factor = 0.75
// Threshold = capacity × load factor = 16 × 0.75 = 12

Map<String, Integer> map = new HashMap<>();

// Add 12 entries... (75% full)
for(int i = 0; i < 12; i++) {
    map.put("Key" + i, i);
}

// Add 13th entry → Triggers rehashing!
map.put("Key12", 12);

/*
Rehashing Process:
1. Create new array with double capacity (32)
2. Recalculate index for all entries
3. Move all entries to new positions
4. Old array is garbage collected

Why? To maintain O(1) performance and avoid long chains
*/
```

### Java 8 Improvement: Treeification

```java
/*
When bucket has many entries (> 8), linked list converts to tree:

Before (LinkedList - O(n)):
bucket[5] → n1 → n2 → n3 → n4 → n5 → n6 → n7 → n8 → n9 → null

After (Red-Black Tree - O(log n)):
bucket[5] → TreeNode (root)
             /         \
        TreeNode    TreeNode
        /     \      /     \
      ...    ...   ...    ...
*/
```

### Why equals() and hashCode() Matter

```java
class Person {
    String name;
    int age;
    
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Must override both!
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return age == person.age && name.equals(person.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age);  // Consistent with equals
    }
}

// Usage
Map<Person, String> map = new HashMap<>();
Person p1 = new Person("Alice", 25);
map.put(p1, "Engineer");

Person p2 = new Person("Alice", 25);  // Same data
System.out.println(map.get(p2));  // "Engineer" - works because of equals/hashCode
```

### Time Complexity

| Operation | Average | Worst Case (no tree) | Worst Case (with tree) |
|-----------|---------|---------------------|----------------------|
| get() | O(1) | O(n) | O(log n) |
| put() | O(1) | O(n) | O(log n) |
| remove() | O(1) | O(n) | O(log n) |

---

## Question 5 & 6: Explain equals() and hashCode()

### equals() Method

**Purpose:** Checks if two objects are logically equal (same content)

**Default behavior (from Object class):**
```java
public boolean equals(Object obj) {
    return (this == obj);  // Checks reference equality
}
```

**Custom implementation:**
```java
class Student {
    int id;
    String name;
    
    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    @Override
    public boolean equals(Object obj) {
        // 1. Check if same reference
        if(this == obj) return true;
        
        // 2. Check if null or different class
        if(obj == null || getClass() != obj.getClass()) return false;
        
        // 3. Cast and compare fields
        Student student = (Student) obj;
        return id == student.id && name.equals(student.name);
    }
}

// Usage
Student s1 = new Student(1, "Alice");
Student s2 = new Student(1, "Alice");
Student s3 = s1;

System.out.println(s1 == s2);        // false (different objects)
System.out.println(s1.equals(s2));   // true (same content)
System.out.println(s1 == s3);        // true (same reference)
```

### hashCode() Method

**Purpose:** Returns integer hash code for object (used in hash-based collections)

**Default behavior:**
```java
// Returns memory address converted to integer
public int hashCode() {
    return // memory address based value
}
```

**Custom implementation:**
```java
class Student {
    int id;
    String name;
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name);  // Consistent with equals
        
        // Or manually:
        // int result = 17;
        // result = 31 * result + id;
        // result = 31 * result + (name != null ? name.hashCode() : 0);
        // return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return id == student.id && Objects.equals(name, student.name);
    }
}
```

### Why Both Are Important

**The Contract (Golden Rule):**
```
If a.equals(b) returns true
Then a.hashCode() MUST equal b.hashCode()

But:
If a.hashCode() == b.hashCode()
Then a.equals(b) may or may not be true (collision allowed)
```

### Problem Without Proper Override

```java
class Employee {
    int id;
    String name;
    
    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // Only equals() overridden, hashCode() NOT overridden!
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Employee) {
            Employee e = (Employee) obj;
            return this.id == e.id;
        }
        return false;
    }
    
    // hashCode() not overridden - uses default (memory address)
}

// Problem in HashMap/HashSet:
Map<Employee, String> map = new HashMap<>();
Employee e1 = new Employee(1, "Alice");
map.put(e1, "Engineer");

Employee e2 = new Employee(1, "Alice");  // Equal content
System.out.println(e1.equals(e2));  // true
System.out.println(map.get(e2));    // null ❌ - BUG!

// Why null?
// e1.hashCode() = 12345 (memory address)
// e2.hashCode() = 67890 (different memory address)
// HashMap looks in different bucket!
```

### Correct Implementation

```java
class Employee {
    int id;
    String name;
    
    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return id == employee.id && Objects.equals(name, employee.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name);  // Consistent!
    }
}

// Now it works:
Map<Employee, String> map = new HashMap<>();
Employee e1 = new Employee(1, "Alice");
map.put(e1, "Engineer");

Employee e2 = new Employee(1, "Alice");
System.out.println(map.get(e2));  // "Engineer" ✓ Works!
```

### Visual Explanation

```
HashMap Storage with equals() and hashCode():

Employee e1 = new Employee(1, "Alice");  // hashCode = 100
Employee e2 = new Employee(1, "Alice");  // hashCode = 100 (same!)

Step 1: put(e1, "Engineer")
HashMap calculates: hash(e1) → 100 → bucket[5]
Stores: bucket[5] → (e1, "Engineer")

Step 2: get(e2)
HashMap calculates: hash(e2) → 100 → bucket[5]
Goes to bucket[5]
Finds entry, calls e2.equals(e1) → true ✓
Returns: "Engineer"
```

### Key Rules

1. **Override both or neither**
   ```java
   // Bad: Only equals()
   @Override equals() ✓
   hashCode() ✗
   
   // Good: Both
   @Override equals() ✓
   @Override hashCode() ✓
   ```

2. **Consistency**
   ```java
   // If two objects are equal, hash codes must be equal
   if(obj1.equals(obj2)) {
       assert obj1.hashCode() == obj2.hashCode();  // Must be true
   }
   ```

3. **Immutability in hash collections**
   ```java
   // Don't change object after adding to HashMap/HashSet
   Employee e = new Employee(1, "Alice");
   Set<Employee> set = new HashSet<>();
   set.add(e);
   
   e.name = "Bob";  // ❌ Bad! Changes hashCode, can't find it now
   set.contains(e);  // false - can't find it anymore!
   ```

---

## Question 7: Count Frequency of Each Character

```java
public class CharacterFrequency {
    
    // Method 1: Using HashMap
    public static void countCharactersHashMap(String str) {
        // Remove spaces and convert to lowercase
        str = str.replaceAll("\\s", "").toLowerCase();
        
        Map<Character, Integer> frequencyMap = new HashMap<>();
        
        // Count each character
        for(char ch : str.toCharArray()) {
            frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
        }
        
        // Print results
        System.out.println("Character frequencies:");
        for(Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
    
    // Method 2: Using Array (for ASCII characters only)
    public static void countCharactersArray(String str) {
        str = str.replaceAll("\\s", "").toLowerCase();
        
        int[] frequency = new int[256];  // ASCII characters
        
        // Count each character
        for(char ch : str.toCharArray()) {
            frequency[ch]++;
        }
        
        // Print non-zero frequencies
        System.out.println("Character frequencies:");
        for(int i = 0; i < 256; i++) {
            if(frequency[i] > 0) {
                System.out.println((char)i + ": " + frequency[i]);
            }
        }
    }
    
    // Method 3: Using Java 8 Streams
    public static void countCharactersStream(String str) {
        str = str.replaceAll("\\s", "").toLowerCase();
        
        Map<Character, Long> frequencyMap = str.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        
        frequencyMap.forEach((key, value) -> 
            System.out.println(key + ": " + value));
    }
    
    public static void main(String[] args) {
        String input = "Hello World";
        
        System.out.println("Input: " + input);
        System.out.println("\n--- Method 1: HashMap ---");
        countCharactersHashMap(input);
        
        System.out.println("\n--- Method 2: Array ---");
        countCharactersArray(input);
        
        System.out.println("\n--- Method 3: Streams ---");
        countCharactersStream(input);
    }
}

/*
Output:
Input: Hello World

--- Method 1: HashMap ---
Character frequencies:
d: 1
e: 1
h: 1
l: 3
o: 2
r: 1
w: 1

--- Method 2: Array ---
Character frequencies:
d: 1
e: 1
h: 1
l: 3
o: 2
r: 1
w: 1

--- Method 3: Streams ---
d: 1
e: 1
h: 1
l: 3
o: 2
r: 1
w: 1
*/
```

---

## Question 8: Find Missing Number in Array

```java
public class FindMissingNumber {
    
    // Method 1: Using Sum Formula
    // Array contains 1 to n, one number is missing
    public static int findMissingSum(int[] arr, int n) {
        // Sum of first n natural numbers: n * (n + 1) / 2
        int expectedSum = n * (n + 1) / 2;
        
        // Calculate actual sum
        int actualSum = 0;
        for(int num : arr) {
            actualSum += num;
        }
        
        // Missing number = expected - actual
        return expectedSum - actualSum;
    }
    
    // Method 2: Using XOR (works for 1 to n)
    public static int findMissingXOR(int[] arr, int n) {
        int xor1 = 0;  // XOR of all numbers from 1 to n
        int xor2 = 0;  // XOR of all array elements
        
        // XOR all numbers from 1 to n
        for(int i = 1; i <= n; i++) {
            xor1 ^= i;
        }
        
        // XOR all array elements
        for(int num : arr) {
            xor2 ^= num;
        }
        
        // XOR of both gives missing number
        return xor1 ^ xor2;
    }
    
    // Method 3: Using HashSet (works for any range)
    public static int findMissingHashSet(int[] arr) {
        Set<Integer> set = new HashSet<>();
        
        // Add all elements to set
        for(int num : arr) {
            set.add(num);
        }
        
        // Find the first missing number
        int min = Arrays.stream(arr).min().getAsInt();
        int max = Arrays.stream(arr).max().getAsInt();
        
        for(int i = min; i <= max; i++) {
            if(!set.contains(i)) {
                return i;
            }
        }
        
        return -1;  // No missing number
    }
    
    // Method 4: Using Sorting
    public static int findMissingSorting(int[] arr) {
        Arrays.sort(arr);
        
        // Check if first element is not 1
        if(arr[0] != 1) {
            return 1;
        }
        
        // Check consecutive elements
        for(int i = 0; i < arr.length - 1; i++) {
            if(arr[i + 1] - arr[i] > 1) {
                return arr[i] + 1;
            }
        }
        
        // Missing number is next after last element
        return arr[arr.length - 1] + 1;
    }
    
    public static void main(String[] args) {
        // Test Case 1: Array from 1 to n with one missing
        int[] arr1 = {1, 2, 4, 5, 6};  // Missing: 3
        int n1 = 6;
        System.out.println("Array: " + Arrays.toString(arr1));
        System.out.println("Missing (Sum): " + findMissingSum(arr1, n1));
        System.out.println("Missing (XOR): " + findMissingXOR(arr1, n1));
        
        // Test Case 2: Random order
        int[] arr2 = {1, 3, 4, 5, 6, 7, 8};  // Missing: 2
        System.out.println("\nArray: " + Arrays.toString(arr2));
        System.out.println("Missing (HashSet): " + findMissingHashSet(arr2));
        System.out.println("Missing (Sorting): " + findMissingSorting(arr2));
        
        // Test Case 3: Larger array
        int[] arr3 = {1, 2, 3, 4, 5, 7, 8, 9, 10};  // Missing: 6
        System.out.println("\nArray: " + Arrays.toString(arr3));
        System.out.println("Missing: " + findMissingSum(arr3, 10));
    }
}

/*
Output:
Array: [1, 2, 4, 5, 6]
Missing (Sum): 3
Missing (XOR): 3

Array: [1, 3, 4, 5, 6, 7, 8]
Missing (HashSet): 2
Missing (Sorting): 2

Array: [1, 2, 3, 4, 5, 7, 8, 9, 10]
Missing: 6
*/
```

**Time & Space Complexity:**

| Method | Time | Space |
|--------|------|-------|
| Sum Formula | O(n) | O(1) |
| XOR | O(n) | O(1) |
| HashSet | O(n) | O(n) |
| Sorting | O(n log n) | O(1) |

---

## Question 9: Find Maximum Salary from Employee List

```java
import java.util.*;
import java.util.stream.*;

class Employee {
    private int id;
    private String name;
    private double salary;
    private String department;
    
    public Employee(int id, String name, double salary, String department) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDepartment() {
        return department;
    }
    
    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', salary=" + salary + 
               ", department='" + department + "'}";
    }
}

public class MaxSalaryFinder {
    
    // Method 1: Using Simple Loop
    public static Employee findMaxSalaryLoop(List<Employee> employees) {
        if(employees == null || employees.isEmpty()) {
            return null;
        }
        
        Employee maxSalaryEmployee = employees.get(0);
        
        for(Employee emp : employees) {
            if(emp.getSalary() > maxSalaryEmployee.getSalary()) {
                maxSalaryEmployee = emp;
            }
        }
        
        return maxSalaryEmployee;
    }
    
    // Method 2: Using Collections.max()
    public static Employee findMaxSalaryCollections(List<Employee> employees) {
        if(employees == null || employees.isEmpty()) {
            return null;
        }
        
        return Collections.max(employees, Comparator.comparing(Employee::getSalary));
    }
    
    // Method 3: Using Java 8 Stream
    public static Employee findMaxSalaryStream(List<Employee> employees) {
        if(employees == null || employees.isEmpty()) {
            return null;
        }
        
        return employees.stream()
            .max(Comparator.comparing(Employee::getSalary))
            .orElse(null);
    }
    
    // Method 4: Get just the maximum salary value
    public static double getMaxSalary(List<Employee> employees) {
        return employees.stream()
            .mapToDouble(Employee::getSalary)
            .max()
            .orElse(0.0);
    }
    
    // Method 5: Find top N employees by salary
    public static List<Employee> findTopNBySalary(List<Employee> employees, int n) {
        return employees.stream()
            .sorted(Comparator.comparing(Employee::getSalary).reversed())
            .limit(n)
            .collect(Collectors.toList());
    }
    
    // Method 6: Find maximum salary by department
    public static Map<String, Employee> findMaxSalaryByDepartment(List<Employee> employees) {
        return employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.collectingAndThen(
                    Collectors.maxBy(Comparator.comparing(Employee::getSalary)),
                    Optional::get
                )
            ));
    }
    
    public static void main(String[] args) {
        // Create employee list
        List<Employee> employees = Arrays.asList(
            new Employee(1, "Alice", 80000, "IT"),
            new Employee(2, "Bob", 60000, "HR"),
            new Employee(3, "Charlie", 95000, "IT"),
            new Employee(4, "David", 75000, "Finance"),
            new Employee(5, "Eve", 65000, "HR"),
            new Employee(6, "Frank", 90000, "IT"),
            new Employee(7, "Grace", 70000, "Finance")
        );
        
        System.out.println("All Employees:");
        employees.forEach(System.out::println);
        
        // Method 1: Simple Loop
        System.out.println("\n--- Max Salary (Loop) ---");
        Employee max1 = findMaxSalaryLoop(employees);
        System.out.println(max1);
        
        // Method 2: Collections.max()
        System.out.println("\n--- Max Salary (Collections) ---");
        Employee max2 = findMaxSalaryCollections(employees);
        System.out.println(max2);
        
        // Method 3: Stream
        System.out.println("\n--- Max Salary (Stream) ---");
        Employee max3 = findMaxSalaryStream(employees);
        System.out.println(max3);
        
        // Method 4: Just salary value
        System.out.println("\n--- Maximum Salary Value ---");
        double maxSalary = getMaxSalary(employees);
        System.out.println("Max Salary: $" + maxSalary);
        
        // Method 5: Top 3 by salary
        System.out.println("\n--- Top 3 Employees by Salary ---");
        List<Employee> top3 = findTopNBySalary(employees, 3);
        top3.forEach(System.out::println);
        
        // Method 6: Max salary by department
        System.out.println("\n--- Max Salary by Department ---");
        Map<String, Employee> maxByDept = findMaxSalaryByDepartment(employees);
        maxByDept.forEach((dept, emp) -> 
            System.out.println(dept + ": " + emp.getName() + " ($" + emp.getSalary() + ")"));
    }
}

/*
Output:
All Employees:
Employee{id=1, name='Alice', salary=80000.0, department='IT'}
Employee{id=2, name='Bob', salary=60000.0, department='HR'}
Employee{id=3, name='Charlie', salary=95000.0, department='IT'}
Employee{id=4, name='David', salary=75000.0, department='Finance'}
Employee{id=5, name='Eve', salary=65000.0, department='HR'}
Employee{id=6, name='Frank', salary=90000.0, department='IT'}
Employee{id=7, name='Grace', salary=70000.0, department='Finance'}

--- Max Salary (Loop) ---
Employee{id=3, name='Charlie', salary=95000.0, department='IT'}

--- Max Salary (Collections) ---
Employee{id=3, name='Charlie', salary=95000.0, department='IT'}

--- Max Salary (Stream) ---
Employee{id=3, name='Charlie', salary=95000.0, department='IT'}

--- Maximum Salary Value ---
Max Salary: $95000.0

--- Top 3 Employees by Salary ---
Employee{id=3, name='Charlie', salary=95000.0, department='IT'}
Employee{id=6, name='Frank', salary=90000.0, department='IT'}
Employee{id=1, name='Alice', salary=80000.0, department='IT'}

--- Max Salary by Department ---
Finance: David ($75000.0)
IT: Charlie ($95000.0)
HR: Eve ($65000.0)
*/
```

---

## Question 10: Sum of Page Counts with Null/Empty Handling

```java
import java.util.*;

public class PageCountCalculator {
    
    // Method 1: Using Simple Loop with Null Check
    public static int getTotalPages(List<Integer> pageCountList) {
        // Handle null or empty list
        if(pageCountList == null || pageCountList.isEmpty()) {
            System.out.println("List is null or empty");
            return 0;
        }
        
        int totalPages = 0;
        
        for(Integer pages : pageCountList) {
            // Handle null elements
            if(pages != null) {
                totalPages += pages;
            }
        }
        
        return totalPages;
    }
    
    // Method 2: Using Java 8 Stream
    public static int getTotalPagesStream(List<Integer> pageCountList) {
        if(pageCountList == null || pageCountList.isEmpty()) {
            return 0;
        }
        
        return pageCountList.stream()
            .filter(Objects::nonNull)  // Remove null elements
            .mapToInt(Integer::intValue)
            .sum();
    }
    
    // Method 3: Using Optional for Better Handling
    public static Optional<Integer> getTotalPagesOptional(List<Integer> pageCountList) {
        if(pageCountList == null || pageCountList.isEmpty()) {
            return Optional.empty();
        }
        
        int total = pageCountList.stream()
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .sum();
        
        return Optional.of(total);
    }
    
    // Method 4: With Statistics (Min, Max, Average, etc.)
    public static void getPageStatistics(List<Integer> pageCountList) {
        if(pageCountList == null || pageCountList.isEmpty()) {
            System.out.println("No data available");
            return;
        }
        
        IntSummaryStatistics stats = pageCountList.stream()
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .summaryStatistics();
        
        System.out.println("Total Pages: " + stats.getSum());
        System.out.println("Average Pages: " + stats.getAverage());
        System.out.println("Min Pages: " + stats.getMin());
        System.out.println("Max Pages: " + stats.getMax());
        System.out.println("Number of Chapters: " + stats.getCount());
    }
    
    // Method 5: Enhanced with Exception Handling
    public static int getTotalPagesWithException(List<Integer> pageCountList) 
            throws IllegalArgumentException {
        
        if(pageCountList == null) {
            throw new IllegalArgumentException("Page count list cannot be null");
        }
        
        if(pageCountList.isEmpty()) {
            throw new IllegalArgumentException("Page count list cannot be empty");
        }
        
        return pageCountList.stream()
            .filter(pages -> {
                if(pages == null) {
                    System.out.println("Warning: Skipping null page count");
                    return false;
                }
                if(pages < 0) {
                    System.out.println("Warning: Skipping negative page count: " + pages);
                    return false;
                }
                return true;
            })
            .mapToInt(Integer::intValue)
            .sum();
    }
    
    public static void main(String[] args) {
        // Test Case 1: Normal list
        System.out.println("=== Test Case 1: Normal List ===");
        List<Integer> chapters1 = Arrays.asList(50, 75, 100, 25, 80);
        System.out.println("Chapters: " + chapters1);
        System.out.println("Total Pages (Loop): " + getTotalPages(chapters1));
        System.out.println("Total Pages (Stream): " + getTotalPagesStream(chapters1));
        
        // Test Case 2: Null list
        System.out.println("\n=== Test Case 2: Null List ===");
        List<Integer> chapters2 = null;
        System.out.println("Chapters: " + chapters2);
        System.out.println("Total Pages: " + getTotalPages(chapters2));
        
        // Test Case 3: Empty list
        System.out.println("\n=== Test Case 3: Empty List ===");
        List<Integer> chapters3 = new ArrayList<>();
        System.out.println("Chapters: " + chapters3);
        System.out.println("Total Pages: " + getTotalPages(chapters3));
        
        // Test Case 4: List with null elements
        System.out.println("\n=== Test Case 4: List with Null Elements ===");
        List<Integer> chapters4 = Arrays.asList(50, null, 75, null, 100);
        System.out.println("Chapters: " + chapters4);
        System.out.println("Total Pages: " + getTotalPages(chapters4));
        
        // Test Case 5: Using Optional
        System.out.println("\n=== Test Case 5: Using Optional ===");
        Optional<Integer> total = getTotalPagesOptional(chapters1);
        System.out.println("Total: " + total.orElse(0));
        
        // Test Case 6: Statistics
        System.out.println("\n=== Test Case 6: Statistics ===");
        getPageStatistics(chapters1);
        
        // Test Case 7: With exception handling
        System.out.println("\n=== Test Case 7: Exception Handling ===");
        List<Integer> chapters7 = Arrays.asList(50, -10, 75, null, 100);
        try {
            System.out.println("Total Pages: " + getTotalPagesWithException(chapters7));
        } catch(IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

/*
Output:
=== Test Case 1: Normal List ===
Chapters: [50, 75, 100, 25, 80]
Total Pages (Loop): 330
Total Pages (Stream): 330

=== Test Case 2: Null List ===
Chapters: null
List is null or empty
Total Pages: 0

=== Test Case 3: Empty List ===
Chapters: []
List is null or empty
Total Pages: 0

=== Test Case 4: List with Null Elements ===
Chapters: [50, null, 75, null, 100]
Total Pages: 225

=== Test Case 5: Using Optional ===
Total: 330

=== Test Case 6: Statistics ===
Total Pages: 330
Average Pages: 66.0
Min Pages: 25
Max Pages: 100
Number of Chapters: 5

=== Test Case 7: Exception Handling ===
Warning: Skipping negative page count: -10
Warning: Skipping null page count
Total Pages: 225
*/
```

---

## 🎯 Summary & Key Takeaways

### Quick Reference

| Topic | Key Point |
|-------|-----------|
| **Static** | Belongs to class, shared by all instances |
| **List vs Set vs Map** | List=ordered+duplicates, Set=unique, Map=key-value |
| **JDK vs JRE vs JVM** | JDK=develop, JRE=run, JVM=execute |
| **HashMap** | Array+LinkedList/Tree, O(1) average |
| **equals/hashCode** | Must override both for hash collections |
| **Null Handling** | Always check for null, use Optional |

### Best Practices

✅ **Static**: Use for utility methods, constants, shared resources  
✅ **Collections**: Choose right type based on use case  
✅ **HashMap**: Always override equals() and hashCode() for custom keys  
✅ **Null Safety**: Check null before operations, use Optional  
✅ **Streams**: Use for readability, handle null with filters  

Good luck with your interview! 🚀
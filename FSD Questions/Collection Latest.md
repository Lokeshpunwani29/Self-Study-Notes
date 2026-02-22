# Java Collections Framework - Complete Interview Guide

## 1. What are Collections?

**Answer:** Collections in Java are objects that group multiple elements into a single unit. The Java Collections Framework provides a unified architecture for storing and manipulating groups of objects.

**Key Benefits:**
1. **Reduces programming effort** - Ready-to-use data structures and algorithms
2. **Increases performance** - High-performance implementations of useful data structures
3. **Provides interoperability** - Collections can be passed and manipulated independently of implementation details
4. **Reduces effort to learn APIs** - Consistent and unified API
5. **Reduces design and implementation effort** - Don't need to produce ad-hoc collection implementations

### Collection Framework Hierarchy:

```
Collection (Interface)
├── List (Interface) - Ordered collection, allows duplicates
│   ├── ArrayList (Class) - Resizable array
│   ├── LinkedList (Class) - Doubly-linked list
│   └── Vector (Class) - Synchronized ArrayList
│
├── Set (Interface) - No duplicates allowed
│   ├── HashSet (Class) - Uses HashMap internally
│   ├── LinkedHashSet (Class) - Maintains insertion order
│   └── TreeSet (Class) - Sorted set (Red-Black tree)
│
└── Queue (Interface) - FIFO operations
    ├── PriorityQueue (Class) - Priority-based ordering
    └── Deque (Interface) - Double-ended queue
        └── ArrayDeque (Class) - Resizable array implementation

Map (Interface) - Key-Value pairs (not part of Collection)
├── HashMap (Class) - Hash table based
├── LinkedHashMap (Class) - Maintains insertion order
├── TreeMap (Class) - Sorted map (Red-Black tree)
└── Hashtable (Class) - Synchronized HashMap (legacy)
```

**Basic Example:**
```java
import java.util.*;

public class CollectionBasics {
    public static void main(String[] args) {
        // List - ordered, allows duplicates
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Python");
        list.add("Java"); // duplicate allowed
        
        // Set - no duplicates
        Set<String> set = new HashSet<>();
        set.add("Java");
        set.add("Python");
        set.add("Java"); // ignored - no duplicates
        
        // Map - key-value pairs
        Map<String, Integer> map = new HashMap<>();
        map.put("Java", 1);
        map.put("Python", 2);
        
        // Queue - FIFO
        Queue<String> queue = new LinkedList<>();
        queue.offer("First");
        queue.offer("Second");
        
        System.out.println("List: " + list);     // [Java, Python, Java]
        System.out.println("Set: " + set);       // [Java, Python]
        System.out.println("Map: " + map);       // {Java=1, Python=2}
        System.out.println("Queue: " + queue);   // [First, Second]
    }
}
```

---

## 2. Collections Used in Projects

**Interview Answer with Real-World Examples:**

### E-Commerce Application Example:

```java
public class ECommerceApplication {
    
    // ArrayList - Product catalog (frequent reads, indexed access)
    private List<Product> productCatalog = new ArrayList<>();
    
    // HashMap - Quick user lookup by userId (O(1) access)
    private Map<String, User> userCache = new HashMap<>();
    
    // HashSet - Unique product IDs, prevent duplicates
    private Set<String> uniqueProductIds = new HashSet<>();
    
    // LinkedHashMap - Shopping cart (maintains insertion order)
    private Map<String, CartItem> shoppingCart = new LinkedHashMap<>();
    
    // TreeMap - Price-based product sorting (sorted by key)
    private TreeMap<Double, List<Product>> productsByPrice = new TreeMap<>();
    
    // PriorityQueue - Order processing queue (priority-based)
    private Queue<Order> orderQueue = new PriorityQueue<>();
    
    // LinkedList - Recently viewed products (frequent add/remove from ends)
    private Deque<Product> recentlyViewed = new LinkedList<>();
    
    // ConcurrentHashMap - Thread-safe session management
    private Map<String, Session> activeSessions = new ConcurrentHashMap<>();
}
```

### Banking Application Example:

```java
public class BankingSystem {
    
    // HashMap - Account lookup by account number
    private Map<String, Account> accounts = new HashMap<>();
    
    // TreeMap - Transaction history sorted by timestamp
    private TreeMap<Long, Transaction> transactionHistory = new TreeMap<>();
    
    // ArrayList - List of customers for iteration
    private List<Customer> customers = new ArrayList<>();
    
    // PriorityQueue - Loan applications (priority processing)
    private PriorityQueue<LoanApplication> loanQueue = new PriorityQueue<>();
    
    // HashSet - Unique account numbers
    private Set<String> accountNumbers = new HashSet<>();
}
```

**Interview Response Template:**

"In my project, I used several collections based on specific requirements:

1. **ArrayList** - For storing product catalogs and user lists where we needed fast random access and frequent iterations. For example, displaying paginated product lists.

2. **HashMap** - Extensively used for caching user sessions and configuration data because it provides O(1) lookup time. For instance, retrieving user details by user ID.

3. **HashSet** - To maintain unique elements like email addresses during user registration to prevent duplicates.

4. **LinkedHashMap** - Implemented LRU (Least Recently Used) cache for frequently accessed data, maintaining insertion order was crucial.

5. **TreeMap** - Used in reporting features where we needed sorted data, like financial reports sorted by date.

6. **PriorityQueue** - For task scheduling where high-priority tasks needed to be processed first."

---

## 3. Array vs ArrayList

| Feature | Array | ArrayList |
|---------|-------|-----------|
| **Size** | Fixed size | Dynamic (auto-resizing) |
| **Type** | Can store primitives & objects | Only stores objects (uses autoboxing for primitives) |
| **Performance** | Faster | Slightly slower due to overhead |
| **Type Safety** | Limited (can use generics with objects) | Type-safe with generics |
| **Syntax** | `int[] arr = new int[5]` | `ArrayList<Integer> list = new ArrayList<>()` |
| **Length/Size** | `arr.length` (property) | `list.size()` (method) |
| **Multidimensional** | Supported natively | Need ArrayList of ArrayList |
| **Utility Methods** | None | add(), remove(), contains(), etc. |
| **Memory** | Fixed allocation | Dynamic allocation (may waste some space) |

```java
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayVsArrayList {
    public static void main(String[] args) {
        
        // ===== ARRAY =====
        int[] array = new int[3];
        array[0] = 10;
        array[1] = 20;
        array[2] = 30;
        // array[3] = 40; // ArrayIndexOutOfBoundsException
        
        System.out.println("Array length: " + array.length);
        System.out.println("Array: " + Arrays.toString(array));
        
        // Array of primitives - memory efficient
        int[] primitiveArray = {1, 2, 3, 4, 5};
        
        // ===== ARRAYLIST =====
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(10);
        arrayList.add(20);
        arrayList.add(30);
        arrayList.add(40); // Auto-resizes, no exception
        
        System.out.println("ArrayList size: " + arrayList.size());
        System.out.println("ArrayList: " + arrayList);
        
        // Rich API
        arrayList.remove(0);           // Remove by index
        arrayList.contains(20);        // Check existence
        arrayList.indexOf(30);         // Find index
        
        // Cannot use primitives directly (uses autoboxing)
        // ArrayList<int> list = new ArrayList<>(); // ERROR
        ArrayList<Integer> list = new ArrayList<>(); // Correct
        
        // ===== When to use what =====
        
        // Use Array when:
        // 1. Size is known and fixed
        // 2. Need to store primitives (memory efficient)
        // 3. Performance critical (no overhead)
        int[] scores = new int[100]; // Fixed 100 students
        
        // Use ArrayList when:
        // 1. Size is unknown or changes
        // 2. Need utility methods
        // 3. Type safety with generics
        ArrayList<String> names = new ArrayList<>(); // Dynamic user list
    }
}
```

**Cross Question:** Why can't ArrayList store primitives directly?

**Answer:** ArrayList is a generic class that works with objects. Primitives (int, char, boolean, etc.) are not objects. However, Java provides wrapper classes (Integer, Character, Boolean) and autoboxing/unboxing to automatically convert between primitives and their wrapper objects.

```java
ArrayList<Integer> list = new ArrayList<>();
list.add(5); // Autoboxing: int -> Integer
int value = list.get(0); // Unboxing: Integer -> int
```

---

## 4. ArrayList vs LinkedList - In-Depth Comparison

### ArrayList vs Doubly LinkedList

| Aspect | ArrayList | LinkedList (Doubly) |
|--------|-----------|---------------------|
| **Internal Structure** | Dynamic Array | Doubly Linked Nodes |
| **Memory Layout** | Contiguous | Scattered |
| **Node Structure** | Just data | data + prev + next pointers |
| **Random Access** | O(1) via index | O(n) - needs traversal |
| **Search** | O(n) linear search | O(n) linear search |
| **Insertion at End** | O(1) amortized | O(1) |
| **Insertion at Beginning** | O(n) - shift all elements | O(1) |
| **Insertion at Middle** | O(n) - shift elements | O(n) to find + O(1) to insert |
| **Deletion from End** | O(1) | O(1) |
| **Deletion from Beginning** | O(n) - shift all elements | O(1) |
| **Memory Overhead** | Minimal (unused capacity) | High (2 pointers per element) |
| **Cache Performance** | Excellent (locality) | Poor (scattered) |
| **Implements** | List | List, Deque, Queue |

### Visual Representation:

**ArrayList Internal Structure:**
```
[10][20][30][40][null][null] <- contiguous memory
 0   1   2   3   4     5
```

**LinkedList Internal Structure:**
```
[prev|10|next] <-> [prev|20|next] <-> [prev|30|next]
     ↑                                        ↓
    head                                    tail
```

### Performance Comparison Code:

```java
import java.util.*;

public class ArrayListVsLinkedListPerformance {
    public static void main(String[] args) {
        int size = 100000;
        
        ArrayList<Integer> arrayList = new ArrayList<>();
        LinkedList<Integer> linkedList = new LinkedList<>();
        
        // ===== INSERTION AT END =====
        long start = System.currentTimeMillis();
        for(int i = 0; i < size; i++) {
            arrayList.add(i);
        }
        long end = System.currentTimeMillis();
        System.out.println("ArrayList add at end: " + (end - start) + "ms");
        
        start = System.currentTimeMillis();
        for(int i = 0; i < size; i++) {
            linkedList.add(i);
        }
        end = System.currentTimeMillis();
        System.out.println("LinkedList add at end: " + (end - start) + "ms");
        
        // ===== RANDOM ACCESS =====
        start = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++) {
            arrayList.get(size / 2);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList random access: " + (end - start) + "ms");
        
        start = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++) {
            linkedList.get(size / 2);
        }
        end = System.currentTimeMillis();
        System.out.println("LinkedList random access: " + (end - start) + "ms");
        
        // ===== INSERTION AT BEGINNING =====
        start = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++) {
            arrayList.add(0, i); // Costly - shifts all elements
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList add at beginning: " + (end - start) + "ms");
        
        start = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++) {
            linkedList.addFirst(i); // Fast - just pointer manipulation
        }
        end = System.currentTimeMillis();
        System.out.println("LinkedList add at beginning: " + (end - start) + "ms");
    }
}
```

### When to Use ArrayList:

1. **Frequent random access by index** - `list.get(index)`
2. **Primarily read operations** - Iterating, searching
3. **Memory is a concern** - Less overhead
4. **Better cache locality needed** - Sequential access
5. **Not many insertions/deletions in middle**

**Example Use Cases:**
- Displaying paginated data
- Storing configuration values
- Read-heavy operations like displaying product catalogs

### When to Use LinkedList:

1. **Frequent insertions/deletions at beginning or middle**
2. **Implementing Queue/Deque operations**
3. **No random access needed**
4. **Memory is not a constraint**

**Example Use Cases:**
- Implementing Queue (FIFO)
- Browser history (back/forward)
- Music playlist (add/remove songs)
- Undo functionality

```java
// LinkedList as Queue
LinkedList<String> queue = new LinkedList<>();
queue.offer("First");      // Add to end
queue.offer("Second");
String item = queue.poll(); // Remove from front

// LinkedList as Deque
queue.addFirst("Front");
queue.addLast("Back");
queue.removeFirst();
queue.removeLast();
```

**Cross Question:** If LinkedList has O(n) for insertion in the middle (due to traversal), why use it over ArrayList?

**Answer:** While finding the position is O(n) for both, the actual insertion/deletion in LinkedList is O(1) once the position is found (just pointer manipulation), whereas ArrayList needs O(n) for shifting elements. Also, LinkedList excels at insertions/deletions at the beginning (O(1) vs O(n) for ArrayList).

---

## 5. Searching in ArrayList with Thousand Queries

**Question:** How to efficiently search in an ArrayList when you have thousands of queries?

### Problem:
Linear search in ArrayList is O(n). For 1000 queries on 10,000 elements = 10 million operations!

### Solutions:

#### Solution 1: Sort and Use Binary Search
```java
import java.util.*;

public class EfficientSearch {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        
        // Add 10000 elements
        for(int i = 0; i < 10000; i++) {
            list.add(new Random().nextInt(50000));
        }
        
        // IMPORTANT: Sort the list first
        Collections.sort(list);
        
        // Binary Search - O(log n)
        int index = Collections.binarySearch(list, 25000);
        if(index >= 0) {
            System.out.println("Found at index: " + index);
        } else {
            System.out.println("Not found");
        }
        
        // For 1000 queries: O(1000 * log n) vs O(1000 * n)
        for(int i = 0; i < 1000; i++) {
            int target = new Random().nextInt(50000);
            Collections.binarySearch(list, target);
        }
    }
}
```

**Time Complexity:**
- Single search: O(log n)
- 1000 searches: O(1000 * log n) ≈ 13,000 operations for 10,000 elements

#### Solution 2: Convert to HashMap (Best for Frequent Lookups)
```java
import java.util.*;

public class HashMapSearch {
    public static void main(String[] args) {
        // Original ArrayList
        List<Student> students = new ArrayList<>();
        students.add(new Student(101, "Raj", 85));
        students.add(new Student(102, "Priya", 92));
        students.add(new Student(103, "Amit", 78));
        // ... 10000 students
        
        // Convert to HashMap for O(1) lookup
        Map<Integer, Student> studentMap = new HashMap<>();
        for(Student s : students) {
            studentMap.put(s.getId(), s);
        }
        
        // Now search is O(1)
        Student found = studentMap.get(102); // Instant lookup
        
        // For 1000 queries: O(1000 * 1) = 1000 operations only!
        for(int i = 0; i < 1000; i++) {
            Student s = studentMap.get(i + 100);
        }
    }
}

class Student {
    int id;
    String name;
    int marks;
    
    Student(int id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }
    
    int getId() { return id; }
}
```

**Time Complexity:**
- Conversion: O(n) one-time cost
- Single search: O(1)
- 1000 searches: O(1000)

#### Solution 3: Use HashSet for Existence Checks
```java
import java.util.*;

public class HashSetSearch {
    public static void main(String[] args) {
        List<String> emails = new ArrayList<>();
        // ... thousands of emails
        
        // Convert to HashSet for O(1) contains check
        Set<String> emailSet = new HashSet<>(emails);
        
        // Fast lookup
        boolean exists = emailSet.contains("user@example.com"); // O(1)
    }
}
```

#### Solution 4: Index-Based Optimization (Custom Solution)
```java
import java.util.*;

public class IndexedSearch {
    private List<Product> products = new ArrayList<>();
    private Map<String, List<Product>> categoryIndex = new HashMap<>();
    private Map<String, Product> idIndex = new HashMap<>();
    
    public void addProduct(Product p) {
        products.add(p);
        
        // Build indexes
        idIndex.put(p.getId(), p);
        
        categoryIndex
            .computeIfAbsent(p.getCategory(), k -> new ArrayList<>())
            .add(p);
    }
    
    // O(1) search by ID
    public Product findById(String id) {
        return idIndex.get(id);
    }
    
    // O(1) to get category list
    public List<Product> findByCategory(String category) {
        return categoryIndex.getOrDefault(category, new ArrayList<>());
    }
}

class Product {
    private String id;
    private String category;
    private String name;
    
    Product(String id, String category, String name) {
        this.id = id;
        this.category = category;
        this.name = name;
    }
    
    String getId() { return id; }
    String getCategory() { return category; }
}
```

### Comparison of Search Methods:

| Method | Single Search | 1000 Searches | Space | Best For |
|--------|---------------|---------------|-------|----------|
| Linear Search | O(n) | O(1000n) | O(1) | Small lists, unsorted |
| Binary Search (sorted) | O(log n) | O(1000 log n) | O(1) | Sorted data |
| HashMap Conversion | O(1) | O(1000) | O(n) | Frequent lookups |
| HashSet (exists check) | O(1) | O(1000) | O(n) | Existence checks only |

**Interview Answer:**

"For thousands of queries on an ArrayList, I would:
1. **If data is static/rarely changes**: Convert to HashMap for O(1) lookups
2. **If data is sorted**: Use binary search for O(log n) per query
3. **If only checking existence**: Convert to HashSet
4. **If data changes frequently**: Build secondary indexes for common search criteria"

---

## 6. HashMap Internal Working

### HashMap Structure:

HashMap internally uses an array of buckets (also called bins), where each bucket can hold a linked list or tree (Java 8+) of entries.

**Key Components:**
1. **Hash Function** - Converts key to array index
2. **Buckets** - Array of linked lists/trees
3. **Entry** - Key-value pair object
4. **Load Factor** - Threshold for resizing (default 0.75)
5. **Capacity** - Size of the bucket array (default 16)

### Visual Representation:

```
HashMap Internal Structure:

Bucket Array (default size 16):
Index  Bucket
[0]  -> null
[1]  -> null
[2]  -> Entry(key2, value2) -> Entry(key18, value18) -> null
[3]  -> null
[4]  -> Entry(key4, value4) -> null
[5]  -> null
...
[15] -> Entry(key15, value15) -> null
```

### How HashMap Works - Step by Step:

```java
HashMap<String, Integer> map = new HashMap<>();
map.put("John", 25);
```

**Step 1: Calculate HashCode**
```java
String key = "John";
int hashCode = key.hashCode(); // Let's say: 2301506
```

**Step 2: Calculate Index (Compression)**
```java
int index = hashCode & (capacity - 1); // Using bitwise AND
// If capacity = 16, then: index = hashCode & 15
// This gives index between 0-15
```

**Step 3: Handle Collision**
```java
// If bucket[index] is empty:
//    - Create new Entry(key, value)
//    - Store at bucket[index]

// If bucket[index] has existing entry:
//    - Check if keys are equal using equals()
//    - If equal: Replace value
//    - If not equal: Add to linked list (collision)
```

### Complete Internal Working Code:

```java
import java.util.*;

public class HashMapInternals {
    
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        
        // PUT Operation
        map.put("John", 25);   // 1. hashCode() -> 2. calculate index -> 3. store
        map.put("Alice", 30);
        map.put("Bob", 35);
        
        // GET Operation
        Integer age = map.get("John");
        // 1. Calculate hashCode of "John"
        // 2. Find bucket index
        // 3. Traverse linked list/tree
        // 4. Compare keys using equals()
        // 5. Return value
        
        System.out.println("Age of John: " + age);
        
        // Demonstrating collision
        demonstrateCollision();
    }
    
    static void demonstrateCollision() {
        HashMap<Employee, String> map = new HashMap<>();
        
        Employee e1 = new Employee(1, "John");
        Employee e2 = new Employee(2, "Alice");
        
        map.put(e1, "Developer");
        map.put(e2, "Manager");
        
        // If e1 and e2 have same hashCode but different equals,
        // they will be in same bucket but different entries
    }
}

class Employee {
    int id;
    String name;
    
    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // CRITICAL: Override both hashCode and equals
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Employee emp = (Employee) obj;
        return id == emp.id && Objects.equals(name, emp.name);
    }
}
```

### hashCode() vs equals()

| Method | Purpose | When Called | Contract |
|--------|---------|-------------|----------|
| **hashCode()** | Generate hash value for object | Put/Get in HashMap | Equal objects must have equal hashCodes |
| **equals()** | Check if two objects are equal | After finding bucket, to compare keys | If equals() returns true, hashCode() must be same |

### The Contract Between hashCode() and equals():

**Rule 1:** If two objects are equal according to `equals()`, they MUST have the same `hashCode()`.

**Rule 2:** If two objects have the same `hashCode()`, they MAY OR MAY NOT be equal (collision is allowed).

**Rule 3:** If you override `equals()`, you MUST override `hashCode()` and vice versa.

```java
class Student {
    int id;
    String name;
    
    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // WRONG - Breaking the contract
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Student s = (Student) obj;
        return id == s.id;
    }
    // Missing hashCode() override - VIOLATION!
    
    // CORRECT - Following the contract
    @Override
    public int hashCode() {
        return Objects.hash(id); // Use same fields as equals()
    }
}
```

### What Happens if You Break the Contract?

```java
public class BrokenContract {
    public static void main(String[] args) {
        HashMap<Student, String> map = new HashMap<>();
        
        Student s1 = new Student(1, "John");
        Student s2 = new Student(1, "John");
        
        map.put(s1, "Developer");
        
        // If hashCode() not overridden properly:
        String value = map.get(s2); // Returns NULL (wrong!)
        // Because s1 and s2 have different hashCodes (default Object.hashCode())
        // Even though equals() says they are equal
        
        System.out.println("Value: " + value); // null (unexpected!)
    }
}

class Student {
    int id;
    String name;
    
    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Student s = (Student) obj;
        return id == s.id && Objects.equals(name, s.name);
    }
    
    // MUST override this too!
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
```

### Collision Handling in HashMap:

**Before Java 8:**
- Used Linked List in each bucket
- Worst case: O(n) for get/put when many collisions

**Java 8 onwards:**
- Uses Linked List initially
- When bucket size exceeds 8 (TREEIFY_THRESHOLD), converts to Balanced Tree (Red-Black Tree)
- Worst case improves to O(log n)

```java
// Collision scenario
HashMap<Integer, String> map = new HashMap<>();

// Assuming these keys hash to same bucket
map.put(key1, "value1"); // First entry
map.put(key2, "value2"); // Collision - added to linked list
map.put(key3, "value3"); // Collision - added to linked list
// ... 8 more collisions
// 9th collision - converts to TreeNode structure
```

**Cross Question:** Why does HashMap use 0.75 as default load factor?

**Answer:** Load factor of 0.75 provides a good trade-off between time and space complexity:
- **Higher load factor (>0.75)**: Less memory usage but more collisions (slower)
- **Lower load factor (<0.75)**: Fewer collisions but more memory waste and frequent resizing
- **0.75**: Optimal balance - reduces collision probability while maintaining reasonable memory usage

---

## 7. Filter Objects from ArrayList - Code Implementation

### Requirement: Filter students with marks > 80

```java
import java.util.*;
import java.util.stream.Collectors;

class Student {
    int id;
    String name;
    int marks;
    
    Student(int id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }
    
    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', marks=" + marks + "}";
    }
    
    public int getMarks() { return marks; }
    public String getName() { return name; }
}

public class FilterArrayList {
    public static void main(String[] args) {
        // Sample data
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Raj", 85));
        students.add(new Student(2, "Priya", 92));
        students.add(new Student(3, "Amit", 78));
        students.add(new Student(4, "Sneha", 88));
        students.add(new Student(5, "Vikram", 72));
        
        System.out.println("Original List: " + students);
        
        // ===== METHOD 1: Using Java 8 Streams (Most Efficient) =====
        List<Student> highScorers = students.stream()
            .filter(s -> s.getMarks() > 80)
            .collect(Collectors.toList());
        
        System.out.println("\nMethod 1 - Stream Filter:");
        System.out.println(highScorers);
        
        // ===== METHOD 2: Traditional For Loop =====
        List<Student> filtered = new ArrayList<>();
        for(Student s : students) {
            if(s.getMarks() > 80) {
                filtered.add(s);
            }
        }
        
        System.out.println("\nMethod 2 - Traditional Loop:");
        System.out.println(filtered);
        
        // ===== METHOD 3: Using Iterator (Safe Removal) =====
        List<Student> copyList = new ArrayList<>(students);
        Iterator<Student> iterator = copyList.iterator();
        while(iterator.hasNext()) {
            Student s = iterator.next();
            if(s.getMarks() <= 80) {
                iterator.remove(); // Safe removal during iteration
            }
        }
        
        System.out.println("\nMethod 3 - Iterator:");
        System.out.println(copyList);
        
        // ===== METHOD 4: removeIf (Java 8+) =====
        List<Student> removeIfList = new ArrayList<>(students);
        removeIfList.removeIf(s -> s.getMarks() <= 80);
        
        System.out.println("\nMethod 4 - removeIf:");
        System.out.println(removeIfList);
    }
}
```

### Multiple Filter Criteria:

```java
public class MultipleFilters {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Raj", 85));
        students.add(new Student(2, "Priya", 92));
        students.add(new Student(3, "Amit", 78));
        students.add(new Student(4, "Sneha", 88));
        students.add(new Student(5, "Vikram", 95));
        
        // Multiple filters: marks > 80 AND name starts with 'R' or 'P'
        List<Student> result = students.stream()
            .filter(s -> s.getMarks() > 80)
            .filter(s -> s.getName().startsWith("R") || s.getName().startsWith("P"))
            .collect(Collectors.toList());
        
        System.out.println("Multiple Filters Result: " + result);
        
        // Filter by matching an object's properties
        Student searchCriteria = new Student(0, "Priya", 92);
        List<Student> matchingStudents = students.stream()
            .filter(s -> s.getName().equals(searchCriteria.getName()) 
                      && s.getMarks() == searchCriteria.getMarks())
            .collect(Collectors.toList());
        
        System.out.println("Matching Students: " + matchingStudents);
    }
}
```

### Advanced Filtering - Custom Predicate:

```java
import java.util.function.Predicate;

public class CustomPredicateFilter {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Raj", 85));
        students.add(new Student(2, "Priya", 92));
        students.add(new Student(3, "Amit", 78));
        
        // Define custom predicate
        Predicate<Student> highScorer = s -> s.getMarks() > 80;
        Predicate<Student> nameStartsWithR = s -> s.getName().startsWith("R");
        
        // Combine predicates
        List<Student> result = students.stream()
            .filter(highScorer.and(nameStartsWithR))
            .collect(Collectors.toList());
        
        System.out.println("Custom Predicate Result: " + result);
        
        // Reusable filter method
        List<Student> topStudents = filterStudents(students, s -> s.getMarks() >= 90);
        System.out.println("Top Students: " + topStudents);
    }
    
    // Reusable generic filter method
    public static List<Student> filterStudents(List<Student> list, Predicate<Student> condition) {
        return list.stream()
            .filter(condition)
            .collect(Collectors.toList());
    }
}
```

### Filtering with Complex Objects:

```java
class Employee {
    String name;
    String department;
    double salary;
    
    Employee(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }
    
    @Override
    public String toString() {
        return name + " (" + department + "): $" + salary;
    }
}

public class ComplexFiltering {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John", "IT", 75000));
        employees.add(new Employee("Alice", "HR", 65000));
        employees.add(new Employee("Bob", "IT", 85000));
        employees.add(new Employee("Carol", "Finance", 70000));
        
        // Filter IT employees with salary > 70000
        List<Employee> itHighEarners = employees.stream()
            .filter(e -> e.department.equals("IT"))
            .filter(e -> e.salary > 70000)
            .collect(Collectors.toList());
        
        System.out.println("IT High Earners: " + itHighEarners);
        
        // Group by department and filter
        Map<String, List<Employee>> byDept = employees.stream()
            .collect(Collectors.groupingBy(e -> e.department));
        
        System.out.println("\nGrouped by Department: " + byDept);
    }
}
```

---

## 8. How Does ArrayList Work Internally?

### Internal Structure:

```java
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
    
    // Default initial capacity
    private static final int DEFAULT_CAPACITY = 10;
    
    // The array buffer into which elements are stored
    transient Object[] elementData;
    
    // The size of the ArrayList (number of elements)
    private int size;
    
    // Constructor
    public ArrayList() {
        this.elementData = new Object[DEFAULT_CAPACITY];
    }
}
```

### Key Internal Operations:

#### 1. Adding Elements:

```java
public boolean add(E e) {
    // Ensure capacity
    ensureCapacityInternal(size + 1);
    
    // Add element at end
    elementData[size++] = e;
    return true;
}

private void ensureCapacityInternal(int minCapacity) {
    if (minCapacity > elementData.length) {
        grow(minCapacity); // Need to resize
    }
}
```

#### 2. Getting Elements:

```java
public E get(int index) {
    // Range check
    if (index >= size || index < 0)
        throw new IndexOutOfBoundsException();
    
    // Direct array access - O(1)
    return (E) elementData[index];
}
```

#### 3. Removing Elements:

```java
public E remove(int index) {
    rangeCheck(index);
    
    E oldValue = (E) elementData[index];
    
    int numMoved = size - index - 1;
    if (numMoved > 0) {
        // Shift elements left
        System.arraycopy(elementData, index+1, elementData, index, numMoved);
    }
    
    elementData[--size] = null; // Clear to let GC work
    
    return oldValue;
}
```

### Visualization of Internal Working:

```java
public class ArrayListInternalDemo {
    public static void main(String[] args) {
        // Step-by-step visualization
        
        // Initial state: capacity = 10, size = 0
        // [null, null, null, null, null, null, null, null, null, null]
        List<String> list = new ArrayList<>();
        
        // After add("A"): size = 1
        // ["A", null, null, null, null, null, null, null, null, null]
        list.add("A");
        
        // After add("B"): size = 2
        // ["A", "B", null, null, null, null, null, null, null, null]
        list.add("B");
        
        // After add("C"): size = 3
        // ["A", "B", "C", null, null, null, null, null, null, null]
        list.add("C");
        
        // get(1) - Direct access to index 1
        String value = list.get(1); // Returns "B" in O(1) time
        
        // remove(1) - Remove "B"
        // ["A", "C", null, null, null, null, null, null, null, null]
        // "C" shifted left, size = 2
        list.remove(1);
        
        System.out.println("After operations: " + list);
    }
}
```

---

## 9. Dynamic Resizing in ArrayList

### How Resizing Works:

When the internal array is full and you add a new element, ArrayList:
1. Creates a new array with **1.5x the current capacity**
2. Copies all elements from old array to new array
3. References the new array
4. Old array is garbage collected

### Resize Formula (Java 8+):

```java
private void grow(int minCapacity) {
    int oldCapacity = elementData.length;
    
    // New capacity = oldCapacity + (oldCapacity / 2)
    // This is approximately 1.5x growth
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    
    if (newCapacity < minCapacity)
        newCapacity = minCapacity;
    
    // Create new array and copy elements
    elementData = Arrays.copyOf(elementData, newCapacity);
}
```

### Resize Demonstration:

```java
import java.util.*;

public class ArrayListResizing {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        
        System.out.println("=== Demonstrating ArrayList Resizing ===\n");
        
        // Initial capacity: 10
        System.out.println("Initial capacity: 10 (default)");
        
        // Add 11th element - triggers resize
        for(int i = 1; i <= 15; i++) {
            list.add(i);
            if(i == 10) {
                System.out.println("Added 10 elements - array full");
            }
            if(i == 11) {
                System.out.println("Added 11th element - RESIZE triggered!");
                System.out.println("New capacity: 10 + (10/2) = 15");
            }
            if(i == 15) {
                System.out.println("Array full again at 15 elements");
            }
        }
        
        // Add 16th element - triggers another resize
        list.add(16);
        System.out.println("Added 16th element - RESIZE triggered!");
        System.out.println("New capacity: 15 + (15/2) = 22");
        
        // Capacity growth pattern
        System.out.println("\n=== Capacity Growth Pattern ===");
        System.out.println("Initial: 10");
        System.out.println("1st resize: 15 (10 + 5)");
        System.out.println("2nd resize: 22 (15 + 7)");
        System.out.println("3rd resize: 33 (22 + 11)");
        System.out.println("4th resize: 49 (33 + 16)");
    }
}
```

### Performance Impact of Resizing:

```java
public class ResizePerformance {
    public static void main(String[] args) {
        // Without initial capacity (multiple resizes)
        long start = System.nanoTime();
        ArrayList<Integer> list1 = new ArrayList<>();
        for(int i = 0; i < 100000; i++) {
            list1.add(i); // Multiple resize operations
        }
        long end = System.nanoTime();
        System.out.println("Without initial capacity: " + (end - start) / 1000000 + "ms");
        
        // With initial capacity (no resize)
        start = System.nanoTime();
        ArrayList<Integer> list2 = new ArrayList<>(100000);
        for(int i = 0; i < 100000; i++) {
            list2.add(i); // No resize needed
        }
        end = System.nanoTime();
        System.out.println("With initial capacity: " + (end - start) / 1000000 + "ms");
    }
}
```

### Best Practices:

```java
// If you know approximate size, specify initial capacity
ArrayList<String> list = new ArrayList<>(1000);

// Avoid frequent resizing
ArrayList<Integer> numbers = new ArrayList<>(100); // Better than default 10

// Trim to size after building list
ArrayList<String> data = new ArrayList<>(10000);
// ... add elements
data.trimToSize(); // Reduces capacity to current size
```

**Cross Question:** What is the time complexity of adding elements to ArrayList?

**Answer:**
- **Amortized O(1)** - Average case when array has space
- **O(n)** - Worst case when resizing is needed (copy all elements)
- Over many operations, the cost averages out to O(1) per insertion

---

## 10. Storing Custom Objects in ArrayList

### Basic Example:

```java
import java.util.*;

class Student {
    private int id;
    private String name;
    private double marks;
    
    // Constructor
    public Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }
    
    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getMarks() { return marks; }
    
    // Setters
    public void setMarks(double marks) { this.marks = marks; }
    
    // toString for easy printing
    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', marks=" + marks + "}";
    }
    
    // equals and hashCode (important for searching, removing)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

public class CustomObjectArrayList {
    public static void main(String[] args) {
        // Create ArrayList of custom objects
        ArrayList<Student> students = new ArrayList<>();
        
        // Add students
        students.add(new Student(1, "Raj", 85.5));
        students.add(new Student(2, "Priya", 92.0));
        students.add(new Student(3, "Amit", 78.5));
        students.add(new Student(4, "Sneha", 88.0));
        
        // Display all students
        System.out.println("All Students:");
        for(Student s : students) {
            System.out.println(s);
        }
        
        // Access specific student
        Student first = students.get(0);
        System.out.println("\nFirst Student: " + first);
        
        // Search for student
        Student searchStudent = new Student(2, "Priya", 92.0);
        boolean exists = students.contains(searchStudent);
        System.out.println("\nStudent with id=2 exists: " + exists);
        
        // Remove student
        students.remove(searchStudent);
        System.out.println("\nAfter removing student id=2:");
        System.out.println(students);
        
        // Update student marks
        students.get(0).setMarks(90.0);
        System.out.println("\nAfter updating first student's marks:");
        System.out.println(students.get(0));
    }
}
```

### Sorting Custom Objects:

```java
import java.util.*;

public class SortingCustomObjects {
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student(3, "Amit", 78.5));
        students.add(new Student(1, "Raj", 85.5));
        students.add(new Student(2, "Priya", 92.0));
        
        System.out.println("Original List:");
        students.forEach(System.out::println);
        
        // Sort by marks (using Comparator)
        students.sort((s1, s2) -> Double.compare(s2.getMarks(), s1.getMarks()));
        System.out.println("\nSorted by Marks (Descending):");
        students.forEach(System.out::println);
        
        // Sort by name
        students.sort(Comparator.comparing(Student::getName));
        System.out.println("\nSorted by Name:");
        students.forEach(System.out::println);
        
        // Sort by multiple fields
        students.sort(Comparator.comparing(Student::getMarks)
                               .thenComparing(Student::getName));
        System.out.println("\nSorted by Marks then Name:");
        students.forEach(System.out::println);
    }
}
```

### Complex Custom Object Operations:

```java
class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;
    
    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }
    
    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    
    @Override
    public String toString() {
        return String.format("Employee{id=%d, name='%s', dept='%s', salary=%.2f}", 
                           id, name, department, salary);
    }
}

public class ComplexOperations {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "John", "IT", 75000));
        employees.add(new Employee(2, "Alice", "HR", 65000));
        employees.add(new Employee(3, "Bob", "IT", 85000));
        employees.add(new Employee(4, "Carol", "Finance", 70000));
        employees.add(new Employee(5, "David", "IT", 80000));
        
        // Filter IT department employees
        List<Employee> itEmployees = employees.stream()
            .filter(e -> e.getDepartment().equals("IT"))
            .collect(Collectors.toList());
        
        System.out.println("IT Employees:");
        itEmployees.forEach(System.out::println);
        
        // Calculate average salary by department
        Map<String, Double> avgSalaryByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.averagingDouble(Employee::getSalary)
            ));
        
        System.out.println("\nAverage Salary by Department:");
        avgSalaryByDept.forEach((dept, avg) -> 
            System.out.printf("%s: $%.2f\n", dept, avg));
        
        // Find highest paid employee
        Optional<Employee> highestPaid = employees.stream()
            .max(Comparator.comparing(Employee::getSalary));
        
        highestPaid.ifPresent(e -> 
            System.out.println("\nHighest Paid: " + e));
        
        // Get list of names only
        List<String> names = employees.stream()
            .map(Employee::getName)
            .collect(Collectors.toList());
        
        System.out.println("\nEmployee Names: " + names);
    }
}
```

---

## 11. Relationship Between equals() and hashCode()

### The Contract:

**Three Golden Rules:**

1. **If two objects are equal according to equals(), they MUST have the same hashCode()**
   ```java
   if (obj1.equals(obj2)) {
       // Then this MUST be true:
       obj1.hashCode() == obj2.hashCode()
   }
   ```

2. **If two objects have the same hashCode(), they MAY or MAY NOT be equal**
   ```java
   if (obj1.hashCode() == obj2.hashCode()) {
       // obj1.equals(obj2) can be true or false
       // This is called a "collision" when false
   }
   ```

3. **If you override equals(), you MUST override hashCode() and vice versa**

### Why This Contract Exists:

HashMap, HashSet, and other hash-based collections rely on this contract to function correctly.

```java
class Person {
    String name;
    int age;
    
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // WRONG - Only overriding equals()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name);
    }
    // Missing hashCode() - BROKEN CONTRACT!
}

public class BrokenContractDemo {
    public static void main(String[] args) {
        Person p1 = new Person("John", 25);
        Person p2 = new Person("John", 25);
        
        System.out.println("p1.equals(p2): " + p1.equals(p2)); // true
        System.out.println("p1.hashCode(): " + p1.hashCode()); // e.g., 123456
        System.out.println("p2.hashCode(): " + p2.hashCode()); // e.g., 789012
        
        // Problem in HashMap
        HashMap<Person, String> map = new HashMap<>();
        map.put(p1, "Developer");
        
        // This returns NULL because p2 has different hashCode!
        String value = map.get(p2);
        System.out.println("Value for p2: " + value); // null (WRONG!)
    }
}
```

### Correct Implementation:

```java
class Person {
    String name;
    int age;
    
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // CORRECT - Both overridden using same fields
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age); // Use same fields as equals()
    }
}

public class CorrectContractDemo {
    public static void main(String[] args) {
        Person p1 = new Person("John", 25);
        Person p2 = new Person("John", 25);
        
        System.out.println("p1.equals(p2): " + p1.equals(p2)); // true
        System.out.println("p1.hashCode(): " + p1.hashCode()); // same
        System.out.println("p2.hashCode(): " + p2.hashCode()); // same
        
        // Works correctly in HashMap
        HashMap<Person, String> map = new HashMap<>();
        map.put(p1, "Developer");
        
        String value = map.get(p2);
        System.out.println("Value for p2: " + value); // Developer (CORRECT!)
    }
}
```

### What Happens in HashMap:

```java
// When you do map.put(key, value):
// 1. Calculate hashCode of key
int hash = key.hashCode();

// 2. Find bucket index
int index = hash & (capacity - 1);

// 3. Check if bucket has existing entries
if (bucket[index] != null) {
    // 4. Traverse entries in bucket
    for (Entry e : bucket[index]) {
        // 5. Compare using equals()
        if (e.key.equals(key)) {
            // Replace value
            e.value = value;
            return;
        }
    }
}

// When you do map.get(key):
// 1. Calculate hashCode
int hash = key.hashCode();

// 2. Find bucket
int index = hash & (capacity - 1);

// 3. Traverse bucket and compare with equals()
for (Entry e : bucket[index]) {
    if (e.key.equals(key)) {
        return e.value;
    }
}
return null;
```

### Common Mistakes:

```java
// MISTAKE 1: Using different fields
class Student {
    int id;
    String name;
    int marks;
    
    @Override
    public boolean equals(Object o) {
        Student s = (Student) o;
        return id == s.id; // Only using id
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, marks); // Using all fields - WRONG!
    }
}

// MISTAKE 2: Mutable fields in hashCode
class Employee {
    int id;
    String name; // mutable
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name); // If name changes, hashCode changes!
    }
}

// Better approach - use only immutable fields
@Override
public int hashCode() {
    return Objects.hash(id); // Only immutable field
}
```

### Best Practices:

```java
class BestPracticeExample {
    private final int id; // immutable
    private String name;  // mutable
    private int age;      // mutable
    
    // Use only immutable fields for hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    // Can use all fields for equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BestPracticeExample that = (BestPracticeExample) o;
        return id == that.id && 
               age == that.age && 
               Objects.equals(name, that.name);
    }
}
```

**Cross Question:** What happens if hashCode() always returns the same value?

**Answer:** It's technically valid (doesn't break the contract) but extremely inefficient. All objects will hash to the same bucket, turning HashMap into a linked list with O(n) performance instead of O(1). It defeats the purpose of hashing.

```java
// Legal but terrible performance
@Override
public int hashCode() {
    return 1; // All objects go to same bucket!
}
```

---

## 12. Choosing the Right Collection - Scenario-Based

### Decision Tree:

```
Need to store elements?
│
├── Need key-value pairs?
│   ├── YES → Use Map
│   │   ├── Need sorting by key? → TreeMap
│   │   ├── Need insertion order? → LinkedHashMap
│   │   └── Just fast access? → HashMap
│   │
│   └── NO → Continue
│
├── Need unique elements only?
│   ├── YES → Use Set
│   │   ├── Need sorting? → TreeSet
│   │   ├── Need insertion order? → LinkedHashSet
│   │   └── Just uniqueness? → HashSet
│   │
│   └── NO → Use List/Queue
│
└── Need ordered collection with duplicates?
    ├── Random access needed? → ArrayList
    ├── Frequent insert/delete at ends? → LinkedList
    ├── FIFO processing? → Queue (LinkedList/PriorityQueue)
    └── LIFO processing? → Stack (or Deque)
```

### Scenario-Based Examples:

#### Scenario 1: User Registration System
**Requirement:** Store user emails, ensure no duplicates

```java
// Solution: HashSet
Set<String> registeredEmails = new HashSet<>();

public boolean registerUser(String email) {
    if (registeredEmails.contains(email)) {
        return false; // Already registered
    }
    registeredEmails.add(email);
    return true;
}
```

**Why HashSet?**
- Need uniqueness: ✓
- Fast lookup O(1): ✓
- Don't need ordering: ✓

#### Scenario 2: Shopping Cart
**Requirement:** Store products with quantities, maintain insertion order

```java
// Solution: LinkedHashMap
Map<Product, Integer> cart = new LinkedHashMap<>();

public void addToCart(Product product, int quantity) {
    cart.put(product, cart.getOrDefault(product, 0) + quantity);
}

public void displayCart() {
    // Displays in insertion order
    cart.forEach((product, qty) -> 
        System.out.println(product.getName() + ": " + qty));
}
```

**Why LinkedHashMap?**
- Need key-value (product-quantity): ✓
- Need insertion order: ✓
- Fast access: ✓

#### Scenario 3: Task Queue
**Requirement:** Process tasks in priority order

```java
// Solution: PriorityQueue
class Task implements Comparable<Task> {
    String name;
    int priority;
    
    public int compareTo(Task other) {
        return Integer.compare(other.priority, this.priority); // Higher first
    }
}

PriorityQueue<Task> taskQueue = new PriorityQueue<>();

public void addTask(Task task) {
    taskQueue.offer(task);
}

public Task getNextTask() {
    return taskQueue.poll(); // Returns highest priority
}
```

**Why PriorityQueue?**
- Need priority-based processing: ✓
- FIFO with ordering: ✓

#### Scenario 4: Leaderboard/Ranking
**Requirement:** Store player scores, always sorted

```java
// Solution: TreeMap
TreeMap<Integer, String> leaderboard = new TreeMap<>(Collections.reverseOrder());

public void updateScore(String player, int score) {
    leaderboard.put(score, player);
}

public void displayTop10() {
    leaderboard.entrySet().stream()
        .limit(10)
        .forEach(entry -> 
            System.out.println(entry.getValue() + ": " + entry.getKey()));
}
```

**Why TreeMap?**
- Need sorting by score: ✓
- Key-value storage: ✓
- Automatic sorting: ✓

#### Scenario 5: Product Catalog
**Requirement:** Store thousands of products, frequent searches by ID

```java
// Solution: HashMap
Map<String, Product> productCatalog = new HashMap<>();

public Product findProduct(String productId) {
    return productCatalog.get(productId); // O(1) lookup
}

public void addProduct(Product product) {
    productCatalog.put(product.getId(), product);
}
```

**Why HashMap?**
- Need fast lookup by key: ✓
- Large dataset: ✓
- No ordering needed: ✓

#### Scenario 6: Browser History
**Requirement:** Back/Forward navigation

```java
// Solution: LinkedList (as Deque)
Deque<String> history = new LinkedList<>();
Deque<String> forward = new LinkedList<>();

public void visit(String url) {
    history.push(url);
    forward.clear();
}

public String back() {
    if (history.size() <= 1) return null;
    String current = history.pop();
    forward.push(current);
    return history.peek();
}

public String forward() {
    if (forward.isEmpty()) return null;
    String url = forward.pop();
    history.push(url);
    return url;
}
```

**Why LinkedList (Deque)?**
- Need stack operations (push/pop): ✓
- Efficient add/remove at both ends: ✓

---

## 13. Performance-Based Collection Selection

### Time Complexity Comparison:

| Operation | ArrayList | LinkedList | HashMap | HashSet | TreeMap | TreeSet |
|-----------|-----------|------------|---------|---------|---------|---------|
| **Add** | O(1)* | O(1) | O(1) | O(1) | O(log n) | O(log n) |
| **Get/Access** | O(1) | O(n) | O(1) | N/A | O(log n) | N/A |
| **Remove** | O(n) | O(1)** | O(1) | O(1) | O(log n) | O(log n) |
| **Contains** | O(n) | O(n) | O(1) | O(1) | O(log n) | O(log n) |
| **Insert at Beginning** | O(n) | O(1) | N/A | N/A | N/A | N/A |
| **Iteration** | O(n) | O(n) | O(n) | O(n) | O(n) | O(n) |

*Amortized (may need resizing)  
**If node reference is known

### Space Complexity:

| Collection | Space Complexity | Notes |
|------------|------------------|-------|
| **ArrayList** | O(n) | Plus unused capacity |
| **LinkedList** | O(n) | Plus 2 pointers per node |
| **HashMap** | O(n) | Plus bucket array overhead |
| **HashSet** | O(n) | Uses HashMap internally |
| **TreeMap** | O(n) | Plus tree node overhead |
| **TreeSet** | O(n) | Uses TreeMap internally |

### Performance Testing Code:

```java
import java.util.*;

public class PerformanceComparison {
    public static void main(String[] args) {
        int size = 100000;
        
        testAddPerformance(size);
        testSearchPerformance(size);
        testRemovePerformance(size);
    }
    
    static void testAddPerformance(int size) {
        System.out.println("=== ADD PERFORMANCE ===");
        
        // ArrayList
        long start = System.currentTimeMillis();
        List<Integer> arrayList = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            arrayList.add(i);
        }
        long end = System.currentTimeMillis();
        System.out.println("ArrayList add: " + (end - start) + "ms");
        
        // LinkedList
        start = System.currentTimeMillis();
        List<Integer> linkedList = new LinkedList<>();
        for(int i = 0; i < size; i++) {
            linkedList.add(i);
        }
        end = System.currentTimeMillis();
        System.out.println("LinkedList add: " + (end - start) + "ms");
        
        // HashMap
        start = System.currentTimeMillis();
        Map<Integer, Integer> hashMap = new HashMap<>();
        for(int i = 0; i < size; i++) {
            hashMap.put(i, i);
        }
        end = System.currentTimeMillis();
        System.out.println("HashMap put: " + (end - start) + "ms");
        
        // TreeMap
        start = System.currentTimeMillis();
        Map<Integer, Integer> treeMap = new TreeMap<>();
        for(int i = 0; i < size; i++) {
            treeMap.put(i, i);
        }
        end = System.currentTimeMillis();
        System.out.println("TreeMap put: " + (end - start) + "ms");
    }
    
    static void testSearchPerformance(int size) {
        System.out.println("\n=== SEARCH PERFORMANCE ===");
        
        // Prepare collections
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        Set<Integer> hashSet = new HashSet<>();
        Set<Integer> treeSet = new TreeSet<>();
        
        for(int i = 0; i < size; i++) {
            arrayList.add(i);
            linkedList.add(i);
            hashSet.add(i);
            treeSet.add(i);
        }
        
        int searches = 1000;
        Random random = new Random();
        
        // ArrayList search
        long start = System.currentTimeMillis();
        for(int i = 0; i < searches; i++) {
            arrayList.contains(random.nextInt(size));
        }
        long end = System.currentTimeMillis();
        System.out.println("ArrayList contains: " + (end - start) + "ms");
        
        // LinkedList search
        start = System.currentTimeMillis();
        for(int i = 0; i < searches; i++) {
            linkedList.contains(random.nextInt(size));
        }
        end = System.currentTimeMillis();
        System.out.println("LinkedList contains: " + (end - start) + "ms");
        
        // HashSet search
        start = System.currentTimeMillis();
        for(int i = 0; i < searches; i++) {
            hashSet.contains(random.nextInt(size));
        }
        end = System.currentTimeMillis();
        System.out.println("HashSet contains: " + (end - start) + "ms");
        
        // TreeSet search
        start = System.currentTimeMillis();
        for(int i = 0; i < searches; i++) {
            treeSet.contains(random.nextInt(size));
        }
        end = System.currentTimeMillis();
        System.out.println("TreeSet contains: " + (end - start) + "ms");
    }
    
    static void testRemovePerformance(int size) {
        System.out.println("\n=== REMOVE PERFORMANCE ===");
        
        // ArrayList remove from beginning
        List<Integer> arrayList = new ArrayList<>();
        for(int i = 0; i < 10000; i++) arrayList.add(i);
        
        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++) {
            arrayList.remove(0); // Remove from beginning
        }
        long end = System.currentTimeMillis();
        System.out.println("ArrayList remove from beginning: " + (end - start) + "ms");
        
        // LinkedList remove from beginning
        List<Integer> linkedList = new LinkedList<>();
        for(int i = 0; i < 10000; i++) linkedList.add(i);
        
        start = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++) {
            linkedList.remove(0); // Remove from beginning
        }
        end = System.currentTimeMillis();
        System.out.println("LinkedList remove from beginning: " + (end - start) + "ms");
    }
}
```

### Real-World Performance Scenarios:

#### Scenario 1: High-Frequency Reads, Rare Writes
**Use Case:** Product catalog, configuration data

```java
// Best Choice: ArrayList + HashMap for indexed access
List<Product> products = new ArrayList<>(10000);
Map<String, Product> productIndex = new HashMap<>();

// One-time load
products.addAll(loadProducts());
products.forEach(p -> productIndex.put(p.getId(), p));

// Fast reads
Product p = productIndex.get("PROD123"); // O(1)
Product p2 = products.get(50); // O(1)
```

**Why?**
- ArrayList: Fast iteration for displaying all products
- HashMap: O(1) lookup by product ID
- Trade-off: More memory for better performance

#### Scenario 2: Frequent Insertions/Deletions
**Use Case:** Task queue, messaging system

```java
// Best Choice: LinkedList or PriorityQueue
Deque<Message> messageQueue = new LinkedList<>();

// Fast operations
messageQueue.addFirst(urgentMessage); // O(1)
messageQueue.addLast(normalMessage);  // O(1)
Message next = messageQueue.pollFirst(); // O(1)
```

**Why?**
- LinkedList: O(1) for add/remove at both ends
- No shifting of elements

#### Scenario 3: Need Sorting + Fast Lookup
**Use Case:** Leaderboard, ranking system

```java
// Best Choice: TreeMap
TreeMap<Integer, Player> leaderboard = new TreeMap<>(Collections.reverseOrder());

// Auto-sorted by score
leaderboard.put(player.getScore(), player); // O(log n)

// Get top 10
List<Player> top10 = leaderboard.values().stream()
    .limit(10)
    .collect(Collectors.toList());
```

**Why?**
- Automatic sorting: Always maintains order
- O(log n) operations: Acceptable for ranking
- No manual sorting needed

#### Scenario 4: Unique Elements + Fast Lookup
**Use Case:** Email validation, duplicate prevention

```java
// Best Choice: HashSet
Set<String> uniqueEmails = new HashSet<>();

// Fast operations
boolean added = uniqueEmails.add(email); // O(1)
boolean exists = uniqueEmails.contains(email); // O(1)
```

**Why?**
- O(1) for add/contains
- Automatic duplicate prevention

#### Scenario 5: Large Dataset with Range Queries
**Use Case:** Date-based queries, numerical ranges

```java
// Best Choice: TreeMap
TreeMap<LocalDate, List<Order>> ordersByDate = new TreeMap<>();

// Range queries
Map<LocalDate, List<Order>> lastWeek = ordersByDate.subMap(
    startDate, true,
    endDate, true
);

// Get all orders after certain date
Map<LocalDate, List<Order>> recentOrders = ordersByDate.tailMap(
    LocalDate.now().minusDays(7)
);
```

**Why?**
- Sorted structure: Enables range queries
- subMap/headMap/tailMap: O(log n + k) where k is result size

### Memory vs Performance Trade-offs:

```java
public class MemoryVsPerformance {
    
    // Scenario: 1 million user lookups per second
    
    // Option 1: Memory-efficient (ArrayList)
    // Memory: ~4MB, Lookup: O(n) = SLOW
    List<User> users = new ArrayList<>();
    
    // Option 2: Performance-optimized (HashMap)
    // Memory: ~40MB, Lookup: O(1) = FAST
    Map<String, User> userMap = new HashMap<>();
    
    // Option 3: Balanced (ArrayList + HashMap index)
    // Memory: ~44MB, Lookup: O(1), Iteration: Fast
    List<User> userList = new ArrayList<>();
    Map<String, User> userIndex = new HashMap<>();
    
    // Decision: For 1M lookups/sec, use Option 2 or 3
    // Memory cost is worth the performance gain
}
```

### Collection Selection Cheatsheet:

| Requirement | Best Choice | Why |
|-------------|-------------|-----|
| Fast random access | ArrayList | O(1) index-based access |
| Fast insert/delete at ends | LinkedList/ArrayDeque | O(1) at both ends |
| Unique elements | HashSet | O(1) add/contains |
| Sorted unique elements | TreeSet | O(log n), auto-sorted |
| Key-value fast lookup | HashMap | O(1) get/put |
| Sorted key-value | TreeMap | O(log n), auto-sorted |
| Maintain insertion order | LinkedHashMap/LinkedHashSet | Order + fast access |
| Priority-based processing | PriorityQueue | O(log n), automatic ordering |
| FIFO queue | ArrayDeque/LinkedList | O(1) operations |
| Thread-safe | ConcurrentHashMap | Lock-free reads |

---

## 14. Advanced Collection Concepts

### ConcurrentModificationException

**Problem:** Modifying collection while iterating

```java
public class ConcurrentModificationExample {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        
        // WRONG - Throws ConcurrentModificationException
        try {
            for(Integer num : list) {
                if(num == 3) {
                    list.remove(num); // Modifying while iterating
                }
            }
        } catch(ConcurrentModificationException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        // CORRECT Solution 1: Use Iterator
        list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()) {
            Integer num = iterator.next();
            if(num == 3) {
                iterator.remove(); // Safe removal
            }
        }
        System.out.println("After iterator removal: " + list);
        
        // CORRECT Solution 2: Use removeIf (Java 8+)
        list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        list.removeIf(num -> num == 3);
        System.out.println("After removeIf: " + list);
        
        // CORRECT Solution 3: Use Stream
        list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        list = list.stream()
            .filter(num -> num != 3)
            .collect(Collectors.toList());
        System.out.println("After stream filter: " + list);
    }
}
```

### Fail-Fast vs Fail-Safe Iterators

```java
import java.util.*;
import java.util.concurrent.*;

public class FailFastVsFailSafe {
    public static void main(String[] args) {
        
        // Fail-Fast (ArrayList, HashMap, HashSet)
        // Throws ConcurrentModificationException if modified during iteration
        List<String> failFast = new ArrayList<>(Arrays.asList("A", "B", "C"));
        
        try {
            for(String s : failFast) {
                failFast.add("D"); // Exception!
            }
        } catch(ConcurrentModificationException e) {
            System.out.println("Fail-Fast: " + e.getClass().getSimpleName());
        }
        
        // Fail-Safe (CopyOnWriteArrayList, ConcurrentHashMap)
        // Works on copy, no exception
        List<String> failSafe = new CopyOnWriteArrayList<>(Arrays.asList("A", "B", "C"));
        
        for(String s : failSafe) {
            failSafe.add("D"); // No exception!
        }
        System.out.println("Fail-Safe result: " + failSafe);
    }
}
```

### Collections Utility Methods

```java
import java.util.*;

public class CollectionsUtility {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 9, 3));
        
        // Sorting
        Collections.sort(list);
        System.out.println("Sorted: " + list);
        
        // Reverse
        Collections.reverse(list);
        System.out.println("Reversed: " + list);
        
        // Shuffle
        Collections.shuffle(list);
        System.out.println("Shuffled: " + list);
        
        // Binary Search (requires sorted list)
        Collections.sort(list);
        int index = Collections.binarySearch(list, 5);
        System.out.println("Index of 5: " + index);
        
        // Min and Max
        int min = Collections.min(list);
        int max = Collections.max(list);
        System.out.println("Min: " + min + ", Max: " + max);
        
        // Frequency
        list.add(5);
        list.add(5);
        int freq = Collections.frequency(list, 5);
        System.out.println("Frequency of 5: " + freq);
        
        // Fill
        Collections.fill(list, 0);
        System.out.println("After fill: " + list);
        
        // Unmodifiable collection
        List<String> original = new ArrayList<>(Arrays.asList("A", "B", "C"));
        List<String> unmodifiable = Collections.unmodifiableList(original);
        
        try {
            unmodifiable.add("D"); // UnsupportedOperationException
        } catch(UnsupportedOperationException e) {
            System.out.println("Cannot modify unmodifiable list");
        }
        
        // Synchronized collection (thread-safe)
        List<String> syncList = Collections.synchronizedList(new ArrayList<>());
        
        // Singleton collections
        Set<String> singleton = Collections.singleton("Only");
        System.out.println("Singleton: " + singleton);
    }
}
```

### WeakHashMap - Garbage Collection Friendly

```java
import java.util.*;

public class WeakHashMapExample {
    public static void main(String[] args) throws InterruptedException {
        
        // Regular HashMap - prevents garbage collection
        Map<String, String> hashMap = new HashMap<>();
        String key1 = new String("Key1");
        hashMap.put(key1, "Value1");
        key1 = null; // Remove reference
        System.gc(); // Request GC
        Thread.sleep(1000);
        System.out.println("HashMap size after GC: " + hashMap.size()); // Still 1
        
        // WeakHashMap - allows garbage collection
        Map<String, String> weakMap = new WeakHashMap<>();
        String key2 = new String("Key2");
        weakMap.put(key2, "Value2");
        System.out.println("WeakHashMap size before GC: " + weakMap.size()); // 1
        
        key2 = null; // Remove reference
        System.gc(); // Request GC
        Thread.sleep(1000);
        System.out.println("WeakHashMap size after GC: " + weakMap.size()); // 0 (entry removed!)
    }
}
```

### EnumSet and EnumMap - Specialized Collections

```java
import java.util.*;

enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

public class EnumCollections {
    public static void main(String[] args) {
        
        // EnumSet - Very efficient for enum types
        EnumSet<Day> weekend = EnumSet.of(Day.SATURDAY, Day.SUNDAY);
        EnumSet<Day> weekdays = EnumSet.range(Day.MONDAY, Day.FRIDAY);
        EnumSet<Day> allDays = EnumSet.allOf(Day.class);
        
        System.out.println("Weekend: " + weekend);
        System.out.println("Weekdays: " + weekdays);
        
        // EnumMap - Faster than HashMap for enum keys
        EnumMap<Day, String> schedule = new EnumMap<>(Day.class);
        schedule.put(Day.MONDAY, "Meeting");
        schedule.put(Day.FRIDAY, "Review");
        
        System.out.println("Schedule: " + schedule);
    }
}
```

---

## 15. Interview Tips and Common Pitfalls

### Common Interview Questions:

**Q1: Why is String immutable and how does it help in HashMap?**

**Answer:** String immutability ensures that once a String is created, its hashCode() remains constant. In HashMap, if keys were mutable and their hashCode changed after insertion, the entry would be lost (bucket index would change). String immutability guarantees stable hashCodes, making Strings ideal as HashMap keys.

**Q2: What happens if you add duplicate keys to HashMap?**

**Answer:**
```java
HashMap<String, Integer> map = new HashMap<>();
map.put("A", 1);
map.put("A", 2); // Replaces value

System.out.println(map.get("A")); // 2 (latest value)
```
The value is replaced. HashMap doesn't store duplicate keys; the latest put() overwrites the previous value.

**Q3: Difference between HashMap and Hashtable?**

| HashMap | Hashtable |
|---------|-----------|
| Not synchronized (not thread-safe) | Synchronized (thread-safe) |
| Allows one null key, multiple null values | No null keys or values |
| Faster | Slower (due to synchronization) |
| Iterator is fail-fast | Enumerator is fail-safe |
| Introduced in Java 1.2 | Legacy class (Java 1.0) |

**Recommendation:** Use ConcurrentHashMap instead of Hashtable for thread-safety with better performance.

**Q4: How does ArrayList grow internally?**

**Answer:** When ArrayList reaches capacity, it creates a new array with 1.5x the current size (newCapacity = oldCapacity + oldCapacity/2), copies all elements to the new array, and replaces the reference. This is why add() has amortized O(1) time complexity.

**Q5: Can you store different types in a collection?**

```java
// Without generics - YES (not recommended)
List list = new ArrayList();
list.add(1);        // Integer
list.add("String"); // String
list.add(3.14);     // Double

// With generics - NO (compile error)
List<Integer> typedList = new ArrayList<>();
typedList.add(1);
// typedList.add("String"); // Compile error!
```

### Common Pitfalls:

**Pitfall 1: Forgetting to override hashCode() with equals()**
```java
// WRONG
class Person {
    String name;
    
    @Override
    public boolean equals(Object o) { ... }
    // Missing hashCode() - HashMap won't work correctly!
}
```

**Pitfall 2: Using mutable objects as HashMap keys**
```java
class MutableKey {
    int value;
    
    @Override
    public int hashCode() {
        return value; // Changes when value changes!
    }
}

MutableKey key = new MutableKey();
key.value = 1;
map.put(key, "Data");

key.value = 2; // hashCode changes!
map.get(key); // Returns null (wrong bucket!)
```

**Pitfall 3: Modifying collection during iteration**
```java
// WRONG
for(String s : list) {
    list.remove(s); // ConcurrentModificationException!
}

// CORRECT
list.removeIf(s -> condition);
```

**Pitfall 4: Assuming ArrayList is thread-safe**
```java
// WRONG - Race conditions in multi-threaded environment
List<String> list = new ArrayList<>();

// CORRECT
List<String> list = Collections.synchronizedList(new ArrayList<>());
// Or use CopyOnWriteArrayList
```

### Quick Reference Table:

| Need | Use | Don't Use |
|------|-----|-----------|
| Fast random access | ArrayList | LinkedList |
| Frequent insert/delete at ends | LinkedList | ArrayList |
| Unique elements | HashSet | ArrayList with contains() |
| Sorted collection | TreeSet/TreeMap | ArrayList + sort |
| Thread-safe list | CopyOnWriteArrayList | ArrayList |
| Thread-safe map | ConcurrentHashMap | HashMap |
| Cache with auto-eviction | LinkedHashMap (override removeEldestEntry) | HashMap |

---

## Summary

**Key Takeaways:**

1. **ArrayList** - Fast access, slow insertion/deletion in middle
2. **LinkedList** - Fast insertion/deletion, slow random access
3. **HashMap** - O(1) operations, requires proper equals()/hashCode()
4. **HashSet** - Unique elements with O(1) operations
5. **TreeMap/TreeSet** - Sorted collections with O(log n) operations
6. **Always override both equals() and hashCode() together**
7. **Choose collection based on operations frequency, not just functionality**
8. **Use appropriate initial capacity to avoid resizing overhead**

This comprehensive guide covers all aspects of Java Collections Framework needed for interviews!
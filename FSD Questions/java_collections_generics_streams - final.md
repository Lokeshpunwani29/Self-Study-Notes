# Java Collection Framework, Generics, Streams & Lambda - Interview Q&A

## 📚 SECTION 1: COLLECTION FRAMEWORK

### Q1. What is the Java Collection Framework? Explain its hierarchy.

**Answer:**
The Java Collection Framework is a unified architecture for representing and manipulating collections of objects. It provides interfaces, implementations, and algorithms.

**Hierarchy:**
```
Collection (Interface)
├── List (Interface)
│   ├── ArrayList
│   ├── LinkedList
│   ├── Vector
│   └── Stack
├── Set (Interface)
│   ├── HashSet
│   ├── LinkedHashSet
│   └── SortedSet (Interface)
│       └── TreeSet
└── Queue (Interface)
    ├── PriorityQueue
    ├── LinkedList
    └── Deque (Interface)
        ├── ArrayDeque
        └── LinkedList

Map (Interface) - Separate hierarchy
├── HashMap
├── LinkedHashMap
├── Hashtable
├── SortedMap (Interface)
│   └── TreeMap
└── ConcurrentHashMap
```

---

### Q2. What's the difference between Collection and Collections?

**Answer:**

| Collection | Collections |
|------------|-------------|
| Interface (root of collection hierarchy) | Utility class |
| Defines common methods for collections | Provides static utility methods |
| `Collection<E>` | `Collections.sort()`, `Collections.reverse()` |

**Example:**
```java
// Collection - Interface
Collection<String> list = new ArrayList<>();

// Collections - Utility class
Collections.sort(list);
Collections.reverse(list);
Collections.shuffle(list);
```

---

### Q3. Explain ArrayList vs LinkedList with use cases.

**Answer:**

| Feature | ArrayList | LinkedList |
|---------|-----------|------------|
| Internal Structure | Dynamic array | Doubly linked list |
| Access Time | O(1) - Fast random access | O(n) - Slow random access |
| Insertion/Deletion | O(n) - Slow (shifting required) | O(1) - Fast (just link change) |
| Memory | Less memory overhead | More (stores previous/next pointers) |
| Best for | Random access, read operations | Frequent add/remove operations |

**Examples:**
```java
// ArrayList - Best for random access
List<String> arrayList = new ArrayList<>();
arrayList.add("Java");
arrayList.add("Python");
arrayList.add("C++");
System.out.println(arrayList.get(1));  // Fast O(1)

// LinkedList - Best for frequent modifications
List<String> linkedList = new LinkedList<>();
linkedList.add("First");
linkedList.add(0, "New First");  // Fast insertion at beginning
linkedList.remove(0);             // Fast deletion

// LinkedList as Queue
Queue<String> queue = new LinkedList<>();
queue.offer("Task1");
queue.offer("Task2");
queue.poll();  // Remove first element
```

**Use Cases:**
- **ArrayList**: Reading data frequently, random access needed
- **LinkedList**: Queue/Stack implementation, frequent insertions at middle

---

### Q4. What is the difference between HashSet, LinkedHashSet, and TreeSet?

**Answer:**

```java
// HashSet - No order, fastest
Set<String> hashSet = new HashSet<>();
hashSet.add("Banana");
hashSet.add("Apple");
hashSet.add("Cherry");
System.out.println(hashSet);  // [Banana, Cherry, Apple] - Random order

// LinkedHashSet - Insertion order maintained
Set<String> linkedHashSet = new LinkedHashSet<>();
linkedHashSet.add("Banana");
linkedHashSet.add("Apple");
linkedHashSet.add("Cherry");
System.out.println(linkedHashSet);  // [Banana, Apple, Cherry] - Insertion order

// TreeSet - Sorted order (natural ordering)
Set<String> treeSet = new TreeSet<>();
treeSet.add("Banana");
treeSet.add("Apple");
treeSet.add("Cherry");
System.out.println(treeSet);  // [Apple, Banana, Cherry] - Sorted
```

**Comparison:**

| Feature | HashSet | LinkedHashSet | TreeSet |
|---------|---------|---------------|---------|
| Order | No order | Insertion order | Sorted (natural/comparator) |
| Performance | O(1) | O(1) | O(log n) |
| Null elements | Allows one null | Allows one null | Not allowed (NPE) |
| Internal | HashMap | LinkedHashMap | Red-Black tree |
| Use case | Fast operations | Order matters | Sorted data needed |

---

### Q5. Explain HashMap vs Hashtable vs ConcurrentHashMap vs TreeMap.

**Answer:**

```java
// HashMap - Not thread-safe, allows null
Map<String, Integer> hashMap = new HashMap<>();
hashMap.put(null, 100);  // Allowed
hashMap.put("Alice", 25);
// Not synchronized - Fast but not thread-safe

// Hashtable - Thread-safe (synchronized), no null
Map<String, Integer> hashtable = new Hashtable<>();
// hashtable.put(null, 100);  // NullPointerException
hashtable.put("Bob", 30);
// Synchronized - Slow, legacy class

// ConcurrentHashMap - Thread-safe, better performance
Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
// concurrentMap.put(null, 100);  // NullPointerException
concurrentMap.put("Charlie", 35);
// Segment locking - Better performance than Hashtable

// TreeMap - Sorted by keys
Map<String, Integer> treeMap = new TreeMap<>();
treeMap.put("Zebra", 1);
treeMap.put("Apple", 2);
treeMap.put("Mango", 3);
System.out.println(treeMap);  // {Apple=2, Mango=3, Zebra=1}
```

**Comparison Table:**

| Feature | HashMap | Hashtable | ConcurrentHashMap | TreeMap |
|---------|---------|-----------|-------------------|---------|
| Thread-safe | ❌ | ✅ | ✅ | ❌ |
| Null key | ✅ (one) | ❌ | ❌ | ❌ |
| Null value | ✅ | ❌ | ❌ | ✅ |
| Performance | Fast | Slow | Better than Hashtable | O(log n) |
| Order | No order | No order | No order | Sorted |
| Synchronization | No | Full lock | Segment lock | No |

---

### Q6. How does HashMap work internally? Explain hash collision.

**Answer:**

**Internal Structure:**
```
HashMap uses Array + LinkedList/Tree structure

[0] → null
[1] → Node(key1, value1) → Node(key2, value2)
[2] → null
[3] → Node(key3, value3)
...
[15] → null

Each Node contains: hash, key, value, next
```

**Working Process:**
```java
Map<String, Integer> map = new HashMap<>();
map.put("John", 25);

// Step 1: Calculate hashCode of key
int hash = "John".hashCode();  // e.g., 2314539

// Step 2: Calculate index (bucket location)
int index = hash & (capacity - 1);  // e.g., index = 3

// Step 3: Store at that index
// If collision occurs, add to LinkedList/Tree
```

**Hash Collision Handling:**

```java
// Example: Two keys map to same index
Map<String, Integer> map = new HashMap<>();
map.put("FB", 1);   // hashCode: 2236
map.put("Ea", 2);   // hashCode: 2236 (same!)

// Both stored in same bucket as LinkedList
// Bucket[index]: FB → Ea

// Retrieval uses equals() to find exact key
map.get("Ea");  // Iterates through bucket, uses equals()
```

**Java 8 Improvement:**
- If bucket has >8 entries, LinkedList converts to **Red-Black Tree**
- Improves worst-case from O(n) to O(log n)

**Key Points:**
1. Default capacity: 16
2. Load factor: 0.75 (resizes at 75% full)
3. equals() and hashCode() must be overridden correctly

```java
class Student {
    int id;
    String name;
    
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Student s = (Student) o;
        return id == s.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
```

---

### Q7. What is the difference between fail-fast and fail-safe iterators?

**Answer:**

**Fail-Fast Iterator:**
- Throws `ConcurrentModificationException` if collection is modified during iteration
- Works on original collection
- Examples: ArrayList, HashMap, HashSet

```java
List<String> list = new ArrayList<>();
list.add("A");
list.add("B");
list.add("C");

Iterator<String> iterator = list.iterator();
while(iterator.hasNext()) {
    String value = iterator.next();
    list.add("D");  // ConcurrentModificationException!
}

// Correct way - use iterator's remove method
Iterator<String> iterator2 = list.iterator();
while(iterator2.hasNext()) {
    String value = iterator2.next();
    if(value.equals("B")) {
        iterator2.remove();  // Safe removal
    }
}
```

**Fail-Safe Iterator:**
- Does NOT throw exception
- Works on clone/snapshot of collection
- Examples: CopyOnWriteArrayList, ConcurrentHashMap

```java
List<String> list = new CopyOnWriteArrayList<>();
list.add("A");
list.add("B");
list.add("C");

for(String value : list) {
    list.add("D");  // No exception - works on copy
}
System.out.println(list);  // [A, B, C, D, D, D]
```

**Comparison:**

| Fail-Fast | Fail-Safe |
|-----------|-----------|
| Throws exception | No exception |
| Fast | Slower |
| Memory efficient | More memory (creates copy) |
| ArrayList, HashMap | CopyOnWriteArrayList, ConcurrentHashMap |

---

### Q8. Explain Comparable vs Comparator with examples.

**Answer:**

**Comparable** - Natural ordering (single sorting sequence)
```java
class Student implements Comparable<Student> {
    int id;
    String name;
    
    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // Compare by id (natural ordering)
    @Override
    public int compareTo(Student other) {
        return this.id - other.id;
    }
    
    @Override
    public String toString() {
        return "[" + id + "," + name + "]";
    }
}

List<Student> students = new ArrayList<>();
students.add(new Student(3, "Alice"));
students.add(new Student(1, "Bob"));
students.add(new Student(2, "Charlie"));

Collections.sort(students);  // Uses compareTo()
System.out.println(students);  // [[1,Bob], [2,Charlie], [3,Alice]]
```

**Comparator** - Custom ordering (multiple sorting sequences)
```java
class Student {
    int id;
    String name;
    int age;
    
    Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    
    @Override
    public String toString() {
        return "[" + id + "," + name + "," + age + "]";
    }
}

List<Student> students = new ArrayList<>();
students.add(new Student(3, "Alice", 22));
students.add(new Student(1, "Bob", 20));
students.add(new Student(2, "Charlie", 21));

// Sort by name
Comparator<Student> nameComparator = new Comparator<Student>() {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.name.compareTo(s2.name);
    }
};
Collections.sort(students, nameComparator);
System.out.println(students);  // [[3,Alice,22], [1,Bob,20], [2,Charlie,21]]

// Sort by age (Java 8 Lambda)
students.sort((s1, s2) -> s1.age - s2.age);
System.out.println(students);  // [[1,Bob,20], [2,Charlie,21], [3,Alice,22]]

// Sort by multiple fields
students.sort(Comparator.comparing(s -> s.name)
                        .thenComparing(s -> s.age));
```

**Comparison:**

| Comparable | Comparator |
|------------|------------|
| Single sorting sequence | Multiple sorting sequences |
| Modifies original class | Separate class |
| `compareTo(Object)` | `compare(Object, Object)` |
| `java.lang` package | `java.util` package |
| Use for natural ordering | Use for custom ordering |

---

### Q9. What are Queue and Deque? Give examples.

**Answer:**

**Queue** - FIFO (First In First Out)
```java
// PriorityQueue - Natural ordering
Queue<Integer> queue = new PriorityQueue<>();
queue.offer(30);
queue.offer(10);
queue.offer(20);
System.out.println(queue.poll());  // 10 (smallest first)
System.out.println(queue.poll());  // 20
System.out.println(queue.poll());  // 30

// LinkedList as Queue - FIFO
Queue<String> taskQueue = new LinkedList<>();
taskQueue.offer("Task1");
taskQueue.offer("Task2");
taskQueue.offer("Task3");
System.out.println(taskQueue.peek());  // Task1 (don't remove)
System.out.println(taskQueue.poll());  // Task1 (remove)
System.out.println(taskQueue.poll());  // Task2
```

**Deque** - Double Ended Queue (both ends)
```java
Deque<String> deque = new ArrayDeque<>();

// Add at both ends
deque.addFirst("First");
deque.addLast("Last");
deque.offerFirst("New First");
deque.offerLast("New Last");

// Remove from both ends
System.out.println(deque.pollFirst());  // New First
System.out.println(deque.pollLast());   // New Last

// Use as Stack (LIFO)
Deque<Integer> stack = new ArrayDeque<>();
stack.push(1);
stack.push(2);
stack.push(3);
System.out.println(stack.pop());  // 3 (last in, first out)
System.out.println(stack.pop());  // 2
```

**Queue Methods:**
- `offer()` / `add()` - Add element
- `poll()` / `remove()` - Remove and return
- `peek()` / `element()` - View without removing

---

### Q10. Explain the purpose of Iterator and ListIterator.

**Answer:**

**Iterator** - Forward direction only
```java
List<String> list = Arrays.asList("A", "B", "C", "D");

Iterator<String> iterator = list.iterator();
while(iterator.hasNext()) {
    String value = iterator.next();
    System.out.println(value);
    
    if(value.equals("B")) {
        iterator.remove();  // Safe removal
    }
}
```

**ListIterator** - Bidirectional traversal + more operations
```java
List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));

ListIterator<String> listIterator = list.listIterator();

// Forward traversal
while(listIterator.hasNext()) {
    int index = listIterator.nextIndex();
    String value = listIterator.next();
    System.out.println(index + ": " + value);
    
    // Modify during iteration
    if(value.equals("B")) {
        listIterator.set("B_Modified");  // Replace
    }
    
    if(value.equals("C")) {
        listIterator.add("C+");  // Add after current
    }
}

// Backward traversal
while(listIterator.hasPrevious()) {
    String value = listIterator.previous();
    System.out.println(value);
}

System.out.println(list);  // [A, B_Modified, C, C+, D]
```

**Comparison:**

| Iterator | ListIterator |
|----------|--------------|
| Forward only | Bidirectional |
| All collections | Only List |
| Only remove() | remove(), set(), add() |
| hasNext(), next() | hasPrevious(), previous(), nextIndex(), previousIndex() |

---

## 🎯 SECTION 2: GENERICS

### Q11. What are Generics? Why are they important?

**Answer:**

Generics enable types (classes and interfaces) to be parameters when defining classes, interfaces, and methods. They provide compile-time type safety.

**Without Generics (Before Java 5):**
```java
List list = new ArrayList();
list.add("String");
list.add(10);  // No error!
list.add(new Date());  // No error!

String s = (String) list.get(0);  // Manual casting
String s2 = (String) list.get(1);  // Runtime ClassCastException!
```

**With Generics:**
```java
List<String> list = new ArrayList<>();
list.add("String");
// list.add(10);  // Compile-time error!

String s = list.get(0);  // No casting needed
```

**Benefits:**
1. **Type Safety** - Compile-time checking
2. **No Casting** - Automatic type casting
3. **Code Reusability** - One class, multiple types

---

### Q12. Explain Generic Class, Generic Method, and Generic Interface.

**Answer:**

**Generic Class:**
```java
class Box<T> {
    private T value;
    
    public void set(T value) {
        this.value = value;
    }
    
    public T get() {
        return value;
    }
}

// Usage
Box<Integer> intBox = new Box<>();
intBox.set(10);
int value = intBox.get();

Box<String> strBox = new Box<>();
strBox.set("Hello");
String str = strBox.get();
```

**Multiple Type Parameters:**
```java
class Pair<K, V> {
    private K key;
    private V value;
    
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    public K getKey() { return key; }
    public V getValue() { return value; }
}

// Usage
Pair<String, Integer> pair = new Pair<>("Age", 25);
System.out.println(pair.getKey() + ": " + pair.getValue());
```

**Generic Method:**
```java
class Util {
    // Generic method
    public static <T> void printArray(T[] array) {
        for(T element : array) {
            System.out.println(element);
        }
    }
    
    // Generic method with return type
    public static <T> T getFirst(List<T> list) {
        return list.isEmpty() ? null : list.get(0);
    }
}

// Usage
Integer[] intArray = {1, 2, 3};
String[] strArray = {"A", "B", "C"};

Util.printArray(intArray);  // Type inferred automatically
Util.printArray(strArray);

List<String> names = Arrays.asList("John", "Alice");
String first = Util.getFirst(names);
```

**Generic Interface:**
```java
interface Comparable<T> {
    int compareTo(T other);
}

class Student implements Comparable<Student> {
    int id;
    String name;
    
    @Override
    public int compareTo(Student other) {
        return this.id - other.id;
    }
}
```

---

### Q13. What are Bounded Type Parameters? Explain upper and lower bounds.

**Answer:**

**Upper Bounded (<? extends T>) - Read Only**
```java
// Accepts T or any subclass of T
class Util {
    // Only Number or its subclasses
    public static double sum(List<? extends Number> list) {
        double sum = 0;
        for(Number num : list) {
            sum += num.doubleValue();
        }
        return sum;
    }
}

// Usage
List<Integer> intList = Arrays.asList(1, 2, 3);
List<Double> doubleList = Arrays.asList(1.1, 2.2, 3.3);

System.out.println(Util.sum(intList));     // 6.0
System.out.println(Util.sum(doubleList));  // 6.6

// Cannot add elements (except null)
List<? extends Number> list = new ArrayList<Integer>();
// list.add(10);  // Compile error!
Number num = list.get(0);  // Reading is OK
```

**Lower Bounded (<? super T>) - Write Only**
```java
class Util {
    // Accepts T or any superclass of T
    public static void addNumbers(List<? super Integer> list) {
        list.add(10);   // Can add Integer
        list.add(20);
        list.add(30);
        
        // Object obj = list.get(0);  // Can only read as Object
    }
}

// Usage
List<Number> numberList = new ArrayList<>();
List<Object> objectList = new ArrayList<>();

Util.addNumbers(numberList);  // Integer is subclass of Number
Util.addNumbers(objectList);  // Integer is subclass of Object

System.out.println(numberList);  // [10, 20, 30]
```

**Unbounded (<?>)  - Unknown Type**
```java
public static void printList(List<?> list) {
    for(Object obj : list) {
        System.out.println(obj);
    }
}

// Accepts any type
printList(Arrays.asList(1, 2, 3));
printList(Arrays.asList("A", "B", "C"));
printList(Arrays.asList(1.1, 2.2, 3.3));
```

**PECS Rule: Producer Extends, Consumer Super**
- **Extends** (Producer): Use when you only READ from structure (produces T values)
- **Super** (Consumer): Use when you only WRITE to structure (consumes T values)

```java
// Producer - extends (reading)
public void copyFrom(List<? extends T> source) {
    // Read from source
}

// Consumer - super (writing)
public void copyTo(List<? super T> destination) {
    // Write to destination
}
```

---

### Q14. What is Type Erasure in Generics?

**Answer:**

Type Erasure is the process by which the Java compiler removes all generic type information during compilation. At runtime, generics don't exist.

**Example:**
```java
// Source code
List<String> stringList = new ArrayList<>();
List<Integer> intList = new ArrayList<>();

// After type erasure (compiled bytecode)
List stringList = new ArrayList();
List intList = new ArrayList();

// This means at runtime:
System.out.println(stringList.getClass() == intList.getClass());  // true!
```

**Implications:**
```java
// 1. Cannot create generic array
// T[] array = new T[10];  // Compile error

// 2. Cannot use instanceof with generic type
// if(obj instanceof List<String>) { }  // Compile error
if(obj instanceof List) { }  // OK

// 3. Cannot create instance of type parameter
class Box<T> {
    // T obj = new T();  // Compile error
}

// 4. Cannot use primitive types
// List<int> list = new ArrayList<>();  // Error
List<Integer> list = new ArrayList<>();  // OK

// 5. Cannot overload with same erasure
class Test {
    void method(List<String> list) { }
    // void method(List<Integer> list) { }  // Error - same erasure
}
```

**Why Type Erasure?**
- Backward compatibility with pre-Java 5 code
- Same bytecode for all generic instantiations

---

## 🌊 SECTION 3: STREAMS API

### Q15. What is Stream API? What are its characteristics?

**Answer:**

Stream API (Java 8) is used to process collections of objects in a functional style. It's not a data structure but a pipeline of operations.

**Characteristics:**
1. **No Storage** - Doesn't store elements
2. **Functional** - Operations produce results, don't modify source
3. **Lazy** - Computation happens only when needed
4. **Possibly Unbounded** - Can work with infinite streams
5. **Consumable** - Elements visited only once

**Basic Example:**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Without Stream (imperative)
List<Integer> evenSquares = new ArrayList<>();
for(Integer num : numbers) {
    if(num % 2 == 0) {
        int square = num * num;
        evenSquares.add(square);
    }
}

// With Stream (functional)
List<Integer> result = numbers.stream()
    .filter(n -> n % 2 == 0)      // Keep even numbers
    .map(n -> n * n)               // Square them
    .collect(Collectors.toList()); // Collect results

System.out.println(result);  // [4, 16, 36, 64, 100]
```

---

### Q16. Explain Intermediate vs Terminal operations with examples.

**Answer:**

**Intermediate Operations** - Return Stream (lazy evaluation)
- filter(), map(), flatMap(), distinct(), sorted(), limit(), skip(), peek()

**Terminal Operations** - Trigger execution and return result
- collect(), forEach(), reduce(), count(), min(), max(), anyMatch(), allMatch(), noneMatch(), findFirst(), findAny()

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");

// INTERMEDIATE OPERATIONS

// 1. filter() - Select elements
names.stream()
    .filter(name -> name.length() > 3)
    .forEach(System.out::println);  // Charlie, David, Alice

// 2. map() - Transform elements
names.stream()
    .map(String::toUpperCase)
    .forEach(System.out::println);  // ALICE, BOB, CHARLIE...

// 3. sorted() - Sort elements
names.stream()
    .sorted()
    .forEach(System.out::println);  // Alice, Bob, Charlie...

// 4. distinct() - Remove duplicates
Arrays.asList(1, 2, 2, 3, 3, 4).stream()
    .distinct()
    .forEach(System.out::println);  // 1, 2, 3, 4

// 5. limit() - First n elements
names.stream()
    .limit(3)
    .forEach(System.out::println);  // Alice, Bob, Charlie

// 6. skip() - Skip first n elements
names.stream()
    .skip(2)
    .forEach(System.out::println);  // Charlie, David, Eve

// TERMINAL OPERATIONS

// 1. collect() - Convert to collection
List<String> upperNames = names.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());

// 2. count() - Count elements
long count = names.stream()
    .filter(name -> name.startsWith("A"))
    .count();  // 1

// 3. forEach() - Perform action
names.stream().forEach(System.out::println);

// 4. reduce() - Reduce to single value
Optional<String> concatenated = names.stream()
    .reduce((s1, s2) -> s1 + ", " + s2);
System.out.println(concatenated.get());  // Alice, Bob, Charlie, David, Eve

// 5. anyMatch() - Check if any element matches
boolean hasLongName = names.stream()
    .anyMatch(name -> name.length() > 6);  // true (Charlie)

// 6. allMatch() - Check if all elements match
boolean allShort = names.stream()
    .allMatch(name -> name.length() < 10);  // true

// 7. noneMatch() - Check if no elements match
boolean noneStartsWithZ = names.stream()
    .noneMatch(name -> name.startsWith("Z"));  // true

// 8. findFirst() - Get first element
Optional<String> first = names.stream()
    .filter(name -> name.startsWith("D"))
    .findFirst();  // David

// 9. min() / max() - Find min/max
Optional<String> shortest = names.stream()
    .min(Comparator.comparing(String::length));  // Bob, Eve
```

**Lazy Evaluation Example:**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// This doesn't execute!
Stream<Integer> stream = numbers.stream()
    .filter(n -> {
        System.out.println("Filtering: " + n);
        return n % 2 == 0;
    });

System.out.println("No output yet!");

// Execution starts here (terminal operation)
List<Integer> result = stream.collect(Collectors.toList());
```

---

### Q17. Explain map() vs flatMap() with examples.

**Answer:**

**map()** - One-to-one transformation (1 input → 1 output)
```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

// Transform each string to its length
List<Integer> lengths = names.stream()
    .map(String::length)
    .collect(Collectors.toList());
System.out.println(lengths);  // [5, 3, 7]

// Transform to uppercase
List<String> upper = names.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());
System.out.println(upper);  // [ALICE, BOB, CHARLIE]
```

**flatMap()** - One-to-many transformation (1 input → multiple outputs, then flatten)
```java
List<List<Integer>> nestedList = Arrays.asList(
    Arrays.asList(1, 2, 3),
    Arrays.asList(4, 5),
    Arrays.asList(6, 7, 8, 9)
);

// map() - Returns Stream<Stream<Integer>>
List<Stream<Integer>> withMap = nestedList.stream()
    .map(List::stream)
    .collect(Collectors.toList());
// Gives nested streams - not what we want!

// flatMap() - Returns Stream<Integer> (flattened)
List<Integer> flattened = nestedList.stream()
    .flatMap(List::stream)
    .collect(Collectors.toList());
System.out.println(flattened);  // [1, 2, 3, 4, 5, 6, 7, 8, 9]

// Practical example: Get all characters from strings
List<String> words = Arrays.asList("Hello", "World");

// map() - gives Stream<String[]>
words.stream()
    .map(word -> word.split(""))  // Each word → array
    .forEach(System.out::println);  // Prints arrays

// flatMap() - gives Stream<String>
List<String> chars = words.stream()
    .map(word -> word.split(""))     // Each word → array
    .flatMap(Arrays::stream)         // Flatten all arrays
    .distinct()
    .collect(Collectors.toList());
System.out.println(chars);  // [H, e, l, o, W, r, d]

// Another example: Students and their courses
class Student {
    String name;
    List<String> courses;
    
    Student(String name, List<String> courses) {
        this.name = name;
        this.courses = courses;
    }
    
    List<String> getCourses() {
        return courses;
    }
}

List<Student> students = Arrays.asList(
    new Student("Alice", Arrays.asList("Math", "Physics")),
    new Student("Bob", Arrays.asList("Chemistry", "Biology")),
    new Student("Charlie", Arrays.asList("Math", "Chemistry"))
);

// Get all unique courses
List<String> allCourses = students.stream()
    .flatMap(s -> s.getCourses().stream())
    .distinct()
    .collect(Collectors.toList());
System.out.println(allCourses);  // [Math, Physics, Chemistry, Biology]
```

**Key Difference:**
- **map()**: Stream<T> → Stream<R>
- **flatMap()**: Stream<T> → Stream<Stream<R>> → Stream<R> (flattens)

---

### Q18. Explain Collectors with examples.

**Answer:**

Collectors is a utility class that provides various implementations to accumulate stream elements.

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve", "Bob");

// 1. toList() - Collect to List
List<String> list = names.stream()
    .collect(Collectors.toList());

// 2. toSet() - Collect to Set (removes duplicates)
Set<String> set = names.stream()
    .collect(Collectors.toSet());
System.out.println(set);  // [Alice, Bob, Charlie, David, Eve]

// 3. toMap() - Collect to Map
Map<String, Integer> nameLength = names.stream()
    .distinct()
    .collect(Collectors.toMap(
        name -> name,           // Key
        String::length         // Value
    ));
System.out.println(nameLength);  // {Alice=5, Bob=3, Charlie=7, ...}

// 4. joining() - Concatenate strings
String joined = names.stream()
    .collect(Collectors.joining(", "));
System.out.println(joined);  // Alice, Bob, Charlie, David, Eve, Bob

String withPrefixSuffix = names.stream()
    .collect(Collectors.joining(", ", "[", "]"));
System.out.println(withPrefixSuffix);  // [Alice, Bob, Charlie, David, Eve, Bob]

// 5. counting() - Count elements
long count = names.stream()
    .collect(Collectors.counting());

// 6. summingInt() - Sum of integers
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
int sum = numbers.stream()
    .collect(Collectors.summingInt(Integer::intValue));
System.out.println(sum);  // 15

// 7. averagingInt() - Average
double average = numbers.stream()
    .collect(Collectors.averagingInt(Integer::intValue));
System.out.println(average);  // 3.0

// 8. groupingBy() - Group by criteria
Map<Integer, List<String>> groupedByLength = names.stream()
    .collect(Collectors.groupingBy(String::length));
System.out.println(groupedByLength);
// {3=[Bob, Eve, Bob], 5=[Alice, David], 7=[Charlie]}

// 9. partitioningBy() - Split into two groups (true/false)
Map<Boolean, List<String>> partitioned = names.stream()
    .collect(Collectors.partitioningBy(name -> name.length() > 4));
System.out.println(partitioned);
// {false=[Bob, Eve, Bob], true=[Alice, Charlie, David]}

// 10. maxBy() / minBy() - Find max/min
Optional<String> longest = names.stream()
    .collect(Collectors.maxBy(Comparator.comparing(String::length)));
System.out.println(longest.get());  // Charlie

// Complex example: Grouping and counting
Map<Integer, Long> lengthCount = names.stream()
    .collect(Collectors.groupingBy(
        String::length,
        Collectors.counting()
    ));
System.out.println(lengthCount);  // {3=3, 5=2, 7=1}

// Employee example
class Employee {
    String name;
    String department;
    double salary;
    
    Employee(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }
    
    String getDepartment() { return department; }
    double getSalary() { return salary; }
}

List<Employee> employees = Arrays.asList(
    new Employee("Alice", "IT", 80000),
    new Employee("Bob", "HR", 60000),
    new Employee("Charlie", "IT", 90000),
    new Employee("David", "HR", 65000)
);

// Average salary by department
Map<String, Double> avgSalaryByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.averagingDouble(Employee::getSalary)
    ));
System.out.println(avgSalaryByDept);  // {IT=85000.0, HR=62500.0}

// Highest paid employee in each department
Map<String, Optional<Employee>> highestPaid = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.maxBy(Comparator.comparing(Employee::getSalary))
    ));
```

---

### Q19. What is the difference between Stream and ParallelStream?

**Answer:**

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Sequential Stream - Single thread
long startTime = System.currentTimeMillis();
numbers.stream()
    .map(n -> {
        System.out.println("Sequential: " + n + " - " + 
                          Thread.currentThread().getName());
        return n * n;
    })
    .forEach(System.out::println);
long endTime = System.currentTimeMillis();
System.out.println("Sequential time: " + (endTime - startTime));

// Parallel Stream - Multiple threads (Fork-Join pool)
startTime = System.currentTimeMillis();
numbers.parallelStream()
    .map(n -> {
        System.out.println("Parallel: " + n + " - " + 
                          Thread.currentThread().getName());
        return n * n;
    })
    .forEach(System.out::println);
endTime = System.currentTimeMillis();
System.out.println("Parallel time: " + (endTime - startTime));

// Converting between stream types
Stream<Integer> sequentialStream = numbers.stream();
Stream<Integer> parallelStream = sequentialStream.parallel();
Stream<Integer> backToSequential = parallelStream.sequential();
```

**When to Use Parallel Streams:**
```java
// Good candidate - computationally expensive, large dataset
List<Integer> largeList = IntStream.range(0, 1_000_000)
    .boxed()
    .collect(Collectors.toList());

int sum = largeList.parallelStream()
    .map(n -> n * n)  // Expensive operation
    .reduce(0, Integer::sum);

// Bad candidate - small dataset, I/O operations
List<String> smallList = Arrays.asList("A", "B", "C");
smallList.parallelStream()  // Overhead > benefit
    .forEach(System.out::println);
```

**Comparison:**

| Stream | ParallelStream |
|--------|----------------|
| Sequential execution | Parallel execution |
| Single thread | Multiple threads (ForkJoinPool) |
| Maintains order | May not maintain order |
| Better for small data | Better for large data |
| Predictable results | Thread-safe operations needed |

**Caution with Parallel Streams:**
```java
// Thread-safety issue
List<Integer> list = new ArrayList<>();  // Not thread-safe

// Wrong - ConcurrentModificationException
IntStream.range(0, 100).parallel()
    .forEach(list::add);  // Multiple threads modifying

// Correct - Use concurrent collection or collect()
List<Integer> safeList = IntStream.range(0, 100)
    .parallel()
    .boxed()
    .collect(Collectors.toList());
```

---

## λ SECTION 4: LAMBDA EXPRESSIONS

### Q20. What are Lambda Expressions? What are Functional Interfaces?

**Answer:**

**Lambda Expression** - Anonymous function (function without name)

Syntax: `(parameters) -> expression` or `(parameters) -> { statements; }`

**Functional Interface** - Interface with exactly ONE abstract method (SAM - Single Abstract Method)

```java
// Functional Interface
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
    
    // Can have default and static methods
    default void display() {
        System.out.println("Calculator");
    }
    
    static void info() {
        System.out.println("Performs calculations");
    }
}

// Old way (Anonymous inner class)
Calculator add = new Calculator() {
    @Override
    public int calculate(int a, int b) {
        return a + b;
    }
};
System.out.println(add.calculate(5, 3));  // 8

// Lambda way
Calculator subtract = (a, b) -> a - b;
System.out.println(subtract.calculate(5, 3));  // 2

Calculator multiply = (a, b) -> {
    int result = a * b;
    return result;
};
System.out.println(multiply.calculate(5, 3));  // 15

// Using built-in functional interfaces
// 1. Predicate<T> - Takes input, returns boolean
Predicate<Integer> isEven = n -> n % 2 == 0;
System.out.println(isEven.test(4));  // true
System.out.println(isEven.test(5));  // false

// 2. Function<T, R> - Takes input T, returns R
Function<String, Integer> stringLength = s -> s.length();
System.out.println(stringLength.apply("Hello"));  // 5

// 3. Consumer<T> - Takes input, returns nothing
Consumer<String> printer = s -> System.out.println(s);
printer.accept("Hello World");

// 4. Supplier<T> - Takes no input, returns T
Supplier<Double> randomValue = () -> Math.random();
System.out.println(randomValue.get());

// 5. BiFunction<T, U, R> - Takes two inputs, returns R
BiFunction<Integer, Integer, Integer> power = (base, exp) -> (int) Math.pow(base, exp);
System.out.println(power.apply(2, 3));  // 8
```

---

### Q21. Explain Method References with examples.

**Answer:**

Method Reference is a shorthand notation of lambda expression to call a method.

**Types:**

**1. Static Method Reference** - `ClassName::staticMethod`
```java
// Lambda
Function<String, Integer> parseInt1 = s -> Integer.parseInt(s);

// Method reference
Function<String, Integer> parseInt2 = Integer::parseInt;

System.out.println(parseInt2.apply("123"));  // 123

// Another example
BiFunction<Integer, Integer, Integer> max1 = (a, b) -> Math.max(a, b);
BiFunction<Integer, Integer, Integer> max2 = Math::max;
```

**2. Instance Method Reference (on particular instance)** - `instance::instanceMethod`
```java
String str = "Hello World";

// Lambda
Supplier<String> upper1 = () -> str.toUpperCase();

// Method reference
Supplier<String> upper2 = str::toUpperCase;

System.out.println(upper2.get());  // HELLO WORLD

// Another example
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.forEach(System.out::println);  // println is instance method of System.out
```

**3. Instance Method Reference (on arbitrary instance)** - `ClassName::instanceMethod`
```java
// Lambda
Function<String, String> toLower1 = s -> s.toLowerCase();

// Method reference
Function<String, String> toLower2 = String::toLowerCase;

System.out.println(toLower2.apply("HELLO"));  // hello

// With streams
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
List<String> upperNames = names.stream()
    .map(String::toUpperCase)  // Method reference
    .collect(Collectors.toList());

// Sorting
names.sort(String::compareToIgnoreCase);
```

**4. Constructor Reference** - `ClassName::new`
```java
// Lambda
Supplier<List<String>> listSupplier1 = () -> new ArrayList<>();

// Constructor reference
Supplier<List<String>> listSupplier2 = ArrayList::new;

List<String> list = listSupplier2.get();

// With parameter
Function<Integer, List<String>> listWithSize = ArrayList::new;
List<String> list2 = listWithSize.apply(10);  // Creates ArrayList with initial capacity 10

// Custom class
class Person {
    String name;
    int age;
    
    Person() {
        this.name = "Unknown";
        this.age = 0;
    }
    
    Person(String name) {
        this.name = name;
        this.age = 0;
    }
    
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}

// Constructor references
Supplier<Person> personSupplier = Person::new;  // No-arg constructor
Function<String, Person> personWithName = Person::new;  // One-arg constructor
BiFunction<String, Integer, Person> personComplete = Person::new;  // Two-arg constructor

Person p1 = personSupplier.get();
Person p2 = personWithName.apply("Alice");
Person p3 = personComplete.apply("Bob", 25);

System.out.println(p1);  // Unknown (0)
System.out.println(p2);  // Alice (0)
System.out.println(p3);  // Bob (25)

// Array constructor reference
Function<Integer, int[]> arrayCreator = int[]::new;
int[] arr = arrayCreator.apply(5);  // Creates array of size 5
```

---

### Q22. What is Optional? How to avoid NullPointerException?

**Answer:**

Optional is a container object that may or may not contain a non-null value. Helps avoid NullPointerException.

```java
// Creating Optional
Optional<String> empty = Optional.empty();
Optional<String> nonEmpty = Optional.of("Hello");  // Throws NPE if null
Optional<String> nullable = Optional.ofNullable(null);  // Accepts null

// Checking if value present
if(nonEmpty.isPresent()) {
    System.out.println(nonEmpty.get());  // Hello
}

// Better way - ifPresent()
nonEmpty.ifPresent(System.out::println);  // Hello

// Getting value with default
String value1 = empty.orElse("Default");  // Default
String value2 = empty.orElseGet(() -> "Computed Default");  // Computed Default
// String value3 = empty.orElseThrow();  // NoSuchElementException

// Transformation
Optional<String> upper = nonEmpty.map(String::toUpperCase);
System.out.println(upper.get());  // HELLO

// Filtering
Optional<String> filtered = nonEmpty.filter(s -> s.length() > 3);
System.out.println(filtered.isPresent());  // true

// Real-world example
class User {
    private String name;
    private Address address;
    
    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }
}

class Address {
    private String street;
    private String city;
    
    public Optional<String> getCity() {
        return Optional.ofNullable(city);
    }
}

// Without Optional - Nested null checks
/*
String city = null;
if(user != null) {
    Address address = user.getAddress();
    if(address != null) {
        city = address.getCity();
    }
}
*/

// With Optional - Clean and safe
User user = new User();
String city = Optional.ofNullable(user)
    .flatMap(User::getAddress)
    .flatMap(Address::getCity)
    .orElse("Unknown");

// Stream integration
List<String> names = Arrays.asList("Alice", "Bob", null, "Charlie");
List<String> nonNullNames = names.stream()
    .map(Optional::ofNullable)
    .filter(Optional::isPresent)
    .map(Optional::get)
    .collect(Collectors.toList());

// Or simpler
List<String> nonNull = names.stream()
    .filter(Objects::nonNull)
    .collect(Collectors.toList());

// Finding first matching element
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
Optional<Integer> firstEven = numbers.stream()
    .filter(n -> n % 2 == 0)
    .findFirst();

firstEven.ifPresent(n -> System.out.println("First even: " + n));  // 2
```

**Best Practices:**
1. Never call `get()` without checking `isPresent()`
2. Use `orElse()`, `orElseGet()`, or `orElseThrow()`
3. Don't use Optional for fields (creates overhead)
4. Use Optional as return type for methods that might not return value

---

### Q23. Explain common Stream operations on a List of Objects.

**Answer:**

```java
class Employee {
    private int id;
    private String name;
    private int age;
    private String department;
    private double salary;
    
    // Constructor
    public Employee(int id, String name, int age, String department, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
        this.salary = salary;
    }
    
    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    
    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', age=" + age + 
               ", dept='" + department + "', salary=" + salary + "}";
    }
}

List<Employee> employees = Arrays.asList(
    new Employee(1, "Alice", 28, "IT", 80000),
    new Employee(2, "Bob", 35, "HR", 60000),
    new Employee(3, "Charlie", 30, "IT", 90000),
    new Employee(4, "David", 32, "Finance", 75000),
    new Employee(5, "Eve", 29, "HR", 65000),
    new Employee(6, "Frank", 40, "IT", 95000),
    new Employee(7, "Grace", 26, "Finance", 70000)
);

// 1. Filter employees from IT department
List<Employee> itEmployees = employees.stream()
    .filter(e -> e.getDepartment().equals("IT"))
    .collect(Collectors.toList());

// 2. Get names of all employees
List<String> names = employees.stream()
    .map(Employee::getName)
    .collect(Collectors.toList());

// 3. Find employees with salary > 70000
List<Employee> highPaid = employees.stream()
    .filter(e -> e.getSalary() > 70000)
    .collect(Collectors.toList());

// 4. Sort by salary (ascending)
List<Employee> sortedBySalary = employees.stream()
    .sorted(Comparator.comparing(Employee::getSalary))
    .collect(Collectors.toList());

// 5. Sort by salary (descending)
List<Employee> sortedDesc = employees.stream()
    .sorted(Comparator.comparing(Employee::getSalary).reversed())
    .collect(Collectors.toList());

// 6. Get top 3 highest paid employees
List<Employee> top3 = employees.stream()
    .sorted(Comparator.comparing(Employee::getSalary).reversed())
    .limit(3)
    .collect(Collectors.toList());

// 7. Get employee with minimum salary
Optional<Employee> minSalary = employees.stream()
    .min(Comparator.comparing(Employee::getSalary));
minSalary.ifPresent(System.out::println);

// 8. Get employee with maximum salary
Optional<Employee> maxSalary = employees.stream()
    .max(Comparator.comparing(Employee::getSalary));

// 9. Calculate total salary expense
double totalSalary = employees.stream()
    .mapToDouble(Employee::getSalary)
    .sum();
System.out.println("Total Salary: " + totalSalary);

// 10. Calculate average salary
double avgSalary = employees.stream()
    .mapToDouble(Employee::getSalary)
    .average()
    .orElse(0.0);
System.out.println("Average Salary: " + avgSalary);

// 11. Count employees in each department
Map<String, Long> deptCount = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.counting()
    ));
System.out.println(deptCount);  // {IT=3, HR=2, Finance=2}

// 12. Average salary by department
Map<String, Double> avgSalaryByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.averagingDouble(Employee::getSalary)
    ));
System.out.println(avgSalaryByDept);

// 13. Group employees by department
Map<String, List<Employee>> byDept = employees.stream()
    .collect(Collectors.groupingBy(Employee::getDepartment));

// 14. Get list of distinct departments
List<String> departments = employees.stream()
    .map(Employee::getDepartment)
    .distinct()
    .collect(Collectors.toList());

// 15. Check if any employee has salary > 100000
boolean hasHighEarner = employees.stream()
    .anyMatch(e -> e.getSalary() > 100000);

// 16. Check if all employees are above 25 years old
boolean allAbove25 = employees.stream()
    .allMatch(e -> e.getAge() > 25);

// 17. Partition employees by salary (above/below 75000)
Map<Boolean, List<Employee>> partitioned = employees.stream()
    .collect(Collectors.partitioningBy(e -> e.getSalary() > 75000));
System.out.println("High earners: " + partitioned.get(true).size());
System.out.println("Low earners: " + partitioned.get(false).size());

// 18. Get names of employees sorted by age
String namesSorted = employees.stream()
    .sorted(Comparator.comparing(Employee::getAge))
    .map(Employee::getName)
    .collect(Collectors.joining(", "));

// 19. Find first employee in IT department
Optional<Employee> firstIT = employees.stream()
    .filter(e -> e.getDepartment().equals("IT"))
    .findFirst();

// 20. Get employee with longest name
Optional<Employee> longestName = employees.stream()
    .max(Comparator.comparing(e -> e.getName().length()));

// 21. Increase salary by 10% for all employees
List<Employee> withRaise = employees.stream()
    .peek(e -> System.out.println("Before: " + e.getSalary()))
    .map(e -> new Employee(e.getId(), e.getName(), e.getAge(), 
                           e.getDepartment(), e.getSalary() * 1.1))
    .collect(Collectors.toList());

// 22. Get Map of employee name to salary
Map<String, Double> nameSalaryMap = employees.stream()
    .collect(Collectors.toMap(
        Employee::getName,
        Employee::getSalary
    ));

// 23. Get senior employees (age > 30) from IT with salary > 80000
List<Employee> seniorITHighPaid = employees.stream()
    .filter(e -> e.getDepartment().equals("IT"))
    .filter(e -> e.getAge() > 30)
    .filter(e -> e.getSalary() > 80000)
    .collect(Collectors.toList());

// 24. Get second highest salary
Optional<Double> secondHighest = employees.stream()
    .map(Employee::getSalary)
    .distinct()
    .sorted(Comparator.reverseOrder())
    .skip(1)
    .findFirst();

// 25. Statistics on salary
DoubleSummaryStatistics stats = employees.stream()
    .mapToDouble(Employee::getSalary)
    .summaryStatistics();
System.out.println("Max: " + stats.getMax());
System.out.println("Min: " + stats.getMin());
System.out.println("Average: " + stats.getAverage());
System.out.println("Count: " + stats.getCount());
System.out.println("Sum: " + stats.getSum());
```

---

### Q24. Difference between Intermediate and Terminal Operations - In-depth

**Answer:**

**Key Differences:**

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Intermediate operations are LAZY
Stream<Integer> stream = numbers.stream()
    .filter(n -> {
        System.out.println("Filter: " + n);
        return n % 2 == 0;
    })
    .map(n -> {
        System.out.println("Map: " + n);
        return n * n;
    });

System.out.println("No output yet - operations not executed!");

// Terminal operation triggers execution
List<Integer> result = stream.collect(Collectors.toList());
// Now you'll see the print statements

// Short-circuiting terminal operations
numbers.stream()
    .filter(n -> {
        System.out.println("Checking: " + n);
        return n > 2;
    })
    .findFirst();  // Stops after finding first match
// Output: Checking: 1, Checking: 2, Checking: 3 (stops here)

// vs non-short-circuiting
numbers.stream()
    .filter(n -> {
        System.out.println("Checking: " + n);
        return n > 2;
    })
    .collect(Collectors.toList());  // Processes all elements
// Output: Checking: 1, Checking: 2, Checking: 3, Checking: 4, Checking: 5
```

---

### Q25. Explain reduce() operation with examples.

**Answer:**

The `reduce()` operation performs a reduction on the elements using an associative accumulation function and returns an Optional or value.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// 1. Sum of all numbers
Optional<Integer> sum1 = numbers.stream()
    .reduce((a, b) -> a + b);
System.out.println(sum1.get());  // 15

// With identity (initial value)
int sum2 = numbers.stream()
    .reduce(0, (a, b) -> a + b);
System.out.println(sum2);  // 15

// Using method reference
int sum3 = numbers.stream()
    .reduce(0, Integer::sum);

// 2. Product of all numbers
int product = numbers.stream()
    .reduce(1, (a, b) -> a * b);
System.out.println(product);  // 120

// 3. Find maximum
Optional<Integer> max = numbers.stream()
    .reduce((a, b) -> a > b ? a : b);
System.out.println(max.get());  // 5

// Using Integer::max
Optional<Integer> max2 = numbers.stream()
    .reduce(Integer::max);

// 4. Concatenate strings
List<String> words = Arrays.asList("Hello", "World", "Java", "Streams");
String concatenated = words.stream()
    .reduce("", (a, b) -> a + " " + b);
System.out.println(concatenated.trim());  // Hello World Java Streams

// 5. Count characters
int totalChars = words.stream()
    .reduce(0, 
        (count, word) -> count + word.length(),
        Integer::sum);  // Combiner for parallel streams
System.out.println(totalChars);  // 22

// 6. Complex object reduction
class Employee {
    String name;
    double salary;
    
    Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
    
    double getSalary() { return salary; }
}

List<Employee> employees = Arrays.asList(
    new Employee("Alice", 80000),
    new Employee("Bob", 60000),
    new Employee("Charlie", 90000)
);

// Total salary
double totalSalary = employees.stream()
    .map(Employee::getSalary)
    .reduce(0.0, Double::sum);
System.out.println(totalSalary);  // 230000.0

// Or using mapToDouble
double total = employees.stream()
    .mapToDouble(Employee::getSalary)
    .sum();
```

**Three forms of reduce():**
```java
// 1. Optional<T> reduce(BinaryOperator<T> accumulator)
Optional<Integer> result1 = numbers.stream()
    .reduce((a, b) -> a + b);

// 2. T reduce(T identity, BinaryOperator<T> accumulator)
int result2 = numbers.stream()
    .reduce(0, (a, b) -> a + b);

// 3. <U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
int result3 = numbers.stream()
    .reduce(0, 
        (subtotal, element) -> subtotal + element,  // Accumulator
        Integer::sum);  // Combiner (for parallel)
```

---

## 🎓 ADVANCED QUESTIONS

### Q26. What is the difference between Iterator's remove() and Collection's remove()?

**Answer:**

```java
List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));

// Collection's remove() during iteration - WRONG
try {
    for(String item : list) {
        if(item.equals("B")) {
            list.remove(item);  // ConcurrentModificationException!
        }
    }
} catch(ConcurrentModificationException e) {
    System.out.println("Cannot modify during iteration");
}

// Iterator's remove() - CORRECT
list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
Iterator<String> iterator = list.iterator();
while(iterator.hasNext()) {
    String item = iterator.next();
    if(item.equals("B")) {
        iterator.remove();  // Safe removal
    }
}
System.out.println(list);  // [A, C, D]

// Using removeIf() - BEST (Java 8+)
list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
list.removeIf(item -> item.equals("B"));
System.out.println(list);  // [A, C, D]
```

---

### Q27. Explain peek() vs forEach() in Streams.

**Answer:**

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// peek() - Intermediate operation (returns Stream)
List<Integer> result = numbers.stream()
    .peek(n -> System.out.println("Before: " + n))
    .map(n -> n * 2)
    .peek(n -> System.out.println("After: " + n))
    .collect(Collectors.toList());

// forEach() - Terminal operation (returns void)
numbers.stream()
    .map(n -> n * 2)
    .forEach(n -> System.out.println("Result: " + n));

// Key difference: peek() is lazy, forEach() is eager
Stream<Integer> stream = numbers.stream()
    .peek(System.out::println);  // Nothing happens yet

System.out.println("Stream created but not executed");

stream.collect(Collectors.toList());  // Now peek() executes
```

**Comparison:**

| peek() | forEach() |
|--------|-----------|
| Intermediate | Terminal |
| Returns Stream | Returns void |
| Lazy | Eager |
| For debugging | For consuming elements |
| Can chain more operations | End of pipeline |

---

### Q28. How to convert Array to List and vice versa?

**Answer:**

```java
// Array to List
String[] array = {"A", "B", "C"};

// Method 1: Arrays.asList() - Fixed size
List<String> list1 = Arrays.asList(array);
// list1.add("D");  // UnsupportedOperationException

// Method 2: ArrayList constructor - Mutable
List<String> list2 = new ArrayList<>(Arrays.asList(array));
list2.add("D");  // Works fine

// Method 3: Stream (Java 8+)
List<String> list3 = Arrays.stream(array)
    .collect(Collectors.toList());

// Method 4: List.of() (Java 9+) - Immutable
List<String> list4 = List.of(array);
// list4.add("D");  // UnsupportedOperationException

// Primitive arrays
int[] intArray = {1, 2, 3, 4, 5};
List<Integer> intList = Arrays.stream(intArray)
    .boxed()
    .collect(Collectors.toList());

// List to Array
List<String> list = Arrays.asList("A", "B", "C");

// Method 1: toArray() - Object[]
Object[] objArray = list.toArray();

// Method 2: toArray(T[] a) - Specific type
String[] strArray = list.toArray(new String[0]);

// Method 3: toArray with generator (Java 11+)
String[] strArray2 = list.toArray(String[]::new);

// For primitives
List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
int[] primitiveArray = integerList.stream()
    .mapToInt(Integer::intValue)
    .toArray();
```

---

### Q29. What are default and static methods in interfaces? (Java 8)

**Answer:**

```java
interface Vehicle {
    // Abstract method
    void start();
    
    // Default method - provides default implementation
    default void honk() {
        System.out.println("Beep beep!");
    }
    
    default void stop() {
        System.out.println("Vehicle stopped");
    }
    
    // Static method
    static void cleanVehicle() {
        System.out.println("Cleaning vehicle...");
    }
    
    // Private method (Java 9+) - helper method
    private void log(String message) {
        System.out.println("Log: " + message);
    }
}

class Car implements Vehicle {
    @Override
    public void start() {
        System.out.println("Car started");
    }
    
    // Can override default method
    @Override
    public void honk() {
        System.out.println("Car horn: Beep beep beep!");
    }
}

// Usage
Car car = new Car();
car.start();  // Car started
car.honk();   // Car horn: Beep beep beep! (overridden)
car.stop();   // Vehicle stopped (default method)

Vehicle.cleanVehicle();  // Static method called on interface

// Multiple inheritance with default methods
interface Flyable {
    default void move() {
        System.out.println("Flying");
    }
}

interface Swimmable {
    default void move() {
        System.out.println("Swimming");
    }
}

class Duck implements Flyable, Swimmable {
    // Must override to resolve conflict
    @Override
    public void move() {
        Flyable.super.move();  // Call specific default method
        // Or provide own implementation
    }
}
```

---

### Q30. Explain Custom Collector example.

**Answer:**

```java
// Custom collector to join strings with custom format
class CustomStringCollector implements Collector<String, StringBuilder, String> {
    
    @Override
    public Supplier<StringBuilder> supplier() {
        return StringBuilder::new;
    }
    
    @Override
    public BiConsumer<StringBuilder, String> accumulator() {
        return (sb, s) -> {
            if(sb.length() > 0) {
                sb.append(" | ");
            }
            sb.append(s.toUpperCase());
        };
    }
    
    @Override
    public BinaryOperator<StringBuilder> combiner() {
        return (sb1, sb2) -> {
            if(sb1.length() > 0 && sb2.length() > 0) {
                sb1.append(" | ");
            }
            return sb1.append(sb2);
        };
    }
    
    @Override
    public Function<StringBuilder, String> finisher() {
        return StringBuilder::toString;
    }
    
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}

// Usage
List<String> words = Arrays.asList("hello", "world", "java", "streams");
String result = words.stream()
    .collect(new CustomStringCollector());
System.out.println(result);  // HELLO | WORLD | JAVA | STREAMS

// Simpler approach using Collector.of()
Collector<String, ?, String> customCollector = Collector.of(
    StringBuilder::new,  // Supplier
    (sb, s) -> {  // Accumulator
        if(sb.length() > 0) sb.append(" | ");
        sb.append(s.toUpperCase());
    },
    (sb1, sb2) -> {  // Combiner
        if(sb1.length() > 0 && sb2.length() > 0) sb1.append(" | ");
        return sb1.append(sb2);
    },
    StringBuilder::toString  // Finisher
);

String result2 = words.stream()
    .collect(customCollector);
```

---

## 📝 QUICK REVISION CHECKLIST

### Collections
- [ ] Collection hierarchy and interfaces
- [ ] ArrayList vs LinkedList
- [ ] HashSet vs LinkedHashSet vs TreeSet
- [ ] HashMap internal working
- [ ] HashMap vs Hashtable vs ConcurrentHashMap
- [ ] Fail-fast vs Fail-safe iterators
- [ ] Comparable vs Comparator
- [ ] Queue and Deque operations
- [ ] Iterator vs ListIterator

### Generics
- [ ] Generic class, method, interface
- [ ] Type parameters and wildcards
- [ ] Bounded types (extends, super)
- [ ] PECS rule
- [ ] Type erasure and limitations

### Streams
- [ ] Stream creation and characteristics
- [ ] Intermediate vs Terminal operations
- [ ] filter, map, flatMap
- [ ] reduce operations
- [ ] Collectors (grouping, partitioning, joining)
- [ ] parallel() vs sequential()
- [ ] Stream on collections vs arrays

### Lambda & Functional Programming
- [ ] Lambda expressions syntax
- [ ] Functional interfaces (Predicate, Function, Consumer, Supplier)
- [ ] Method references (4 types)
- [ ] Optional class usage
- [ ] forEach vs peek
- [ ] Default and static methods in interfaces

---

## 🎯 INTERVIEW PRO TIPS

1. **Always explain with examples** - Interviewers love practical code
2. **Mention performance** - Time complexity matters
3. **Discuss real-world use cases** - When to use what
4. **Know the "why"** - Not just "what" and "how"
5. **Compare alternatives** - ArrayList vs LinkedList scenarios
6. **Thread safety** - Always consider concurrent access
7. **Memory implications** - Understand object creation overhead
8. **Java version features** - Know what came in Java 8, 9, 11, 17

**Common Mistakes to Avoid:**
- Using `get()` on Optional without checking
- Modifying collection during iteration without iterator
- Creating parallel streams for small datasets
- Not overriding `equals()` and `hashCode()` for HashMap keys
- Using raw types instead of generics
- Confusing `peek()` with `forEach()`

**Best Practices:**
- Prefer `List.of()`, `Set.of()`, `Map.of()` for immutable collections (Java 9+)
- Use Streams for readability, not always for performance
- Choose right collection based on use case
- Always use generics for type safety
- Leverage functional interfaces and lambdas for cleaner code

Good luck with your interview! 🚀
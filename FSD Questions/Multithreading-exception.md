# Java Interview Questions - Easy Explanations

## MULTITHREADING SECTION

### 1. Difference between Thread and Process

**Process:**
- A **program in execution** (like opening Chrome, Word, etc.)
- Has its **own memory space** (separate house)
- Heavy - takes more resources
- If one process crashes, others are **not affected**
- Example: Chrome and Word are separate processes

**Thread:**
- A **lightweight sub-unit of a process** (rooms in a house)
- Shares **same memory space** within the process
- Lightweight - takes fewer resources
- If one thread crashes, it can **affect other threads**
- Example: Multiple tabs in Chrome are like threads

**Simple Analogy:**
- **Process** = A house with its own address
- **Thread** = Rooms inside that house

---

### 2. Difference between Thread, Runnable, and Callable

| Feature | Thread | Runnable | Callable |
|---------|--------|----------|----------|
| **Type** | Class | Interface | Interface |
| **Method** | `run()` | `run()` | `call()` |
| **Return Value** | No | No | **Yes** (returns a value) |
| **Exception** | No checked exception | No checked exception | **Can throw checked exception** |
| **Usage** | Extend Thread class | Implement Runnable | Implement Callable |

**Example:**
```java
// 1. Thread
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread");
    }
}

// 2. Runnable
class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Runnable");
    }
}

// 3. Callable (Returns a value!)
class MyCallable implements Callable<String> {
    public String call() throws Exception {
        return "Callable Result";
    }
}
```

**When to use what?**
- **Thread**: When you need to extend Thread class (rare)
- **Runnable**: Most common, when you don't need return value
- **Callable**: When you need a **return value** or need to throw exceptions

---

### 3. What is Multithreading in Java?

**Simple Definition:**
Running **multiple threads simultaneously** to perform multiple tasks at the same time.

**Real-Life Example:**
- While you're **typing** in Word (Thread 1)
- Auto-save is **running** in background (Thread 2)
- Spell-check is **running** (Thread 3)

**Benefits:**
- ✅ Better CPU utilization
- ✅ Faster execution
- ✅ Responsive applications
- ✅ Resource sharing

**Example:**
```java
class Task implements Runnable {
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is running");
    }
}

// Create and start multiple threads
Thread t1 = new Thread(new Task());
Thread t2 = new Thread(new Task());
t1.start();
t2.start();
```

---

### 4. How Java Handles Concurrency & Issues

**How Java Handles Concurrency:**
- **synchronized** keyword
- **Lock** objects
- **Atomic** variables
- **Concurrent collections** (ConcurrentHashMap, etc.)
- **Thread pools** (ExecutorService)

**Common Issues:**

**1. Race Condition**
- Multiple threads access shared data simultaneously
- Result depends on timing
```java
// Problem: counter++ is not atomic
class Counter {
    int count = 0;
    void increment() { count++; } // UNSAFE!
}
```

**2. Deadlock**
- Two threads wait for each other forever
- Thread A locks Resource 1, waits for Resource 2
- Thread B locks Resource 2, waits for Resource 1
- Both stuck! 🔒

**3. Starvation**
- A thread never gets CPU time
- Low-priority thread keeps waiting

**4. Livelock**
- Threads keep changing state but make no progress
- Like two people trying to pass each other in a hallway

---

### 5. How to Prevent Race Conditions?

**Solutions:**

**1. Use `synchronized` keyword**
```java
class Counter {
    int count = 0;
    
    synchronized void increment() {
        count++; // Now SAFE!
    }
}
```

**2. Use `synchronized` block**
```java
synchronized(this) {
    count++;
}
```

**3. Use Atomic classes**
```java
AtomicInteger count = new AtomicInteger(0);
count.incrementAndGet(); // Thread-safe!
```

**4. Use Locks**
```java
Lock lock = new ReentrantLock();
lock.lock();
try {
    count++;
} finally {
    lock.unlock();
}
```

**Simple Rule:** Make critical sections **thread-safe** by allowing only one thread at a time.

---

### 6. How to Prevent Deadlock?

**Deadlock Prevention Strategies:**

**1. Lock Ordering**
- Always acquire locks in the **same order**
```java
// Good: Both threads lock in same order
synchronized(resourceA) {
    synchronized(resourceB) {
        // work
    }
}
```

**2. Use Timeout**
```java
if (lock.tryLock(1, TimeUnit.SECONDS)) {
    try {
        // do work
    } finally {
        lock.unlock();
    }
} else {
    // couldn't get lock, handle it
}
```

**3. Avoid Nested Locks**
- Don't hold multiple locks if possible

**4. Use Thread.join() Carefully**
- Don't create circular wait conditions

**Simple Analogy:**
Imagine two people need Fork and Knife:
- **Deadlock**: Person A takes Fork, Person B takes Knife → both wait forever
- **Prevention**: Both agree to take Fork first, then Knife → no deadlock!

---

### 7. Mutex and Semaphores

**Mutex (Mutual Exclusion)**
- **Binary lock** (Only 0 or 1)
- Only **one thread** can access resource
- Like a **single-person bathroom** 🚻
- **Example**: synchronized keyword in Java

```java
// Mutex example
synchronized void accessResource() {
    // Only ONE thread can be here at a time
}
```

**Semaphore**
- **Counting lock** (0, 1, 2, 3, ...)
- **Multiple threads** can access (up to limit)
- Like a **parking lot with N spaces** 🅿️
- **Example**: Semaphore class in Java

```java
// Semaphore with 3 permits
Semaphore semaphore = new Semaphore(3);

semaphore.acquire(); // Get permit
try {
    // Max 3 threads can be here
} finally {
    semaphore.release(); // Return permit
}
```

**Key Difference:**
- **Mutex**: Only 1 thread (binary: 0 or 1)
- **Semaphore**: N threads (counter: 0, 1, 2, ..., N)

---

### 8. Inter-Thread Communication

**Definition:** Threads communicating with each other to coordinate work.

**Java Methods:**
- `wait()` - Thread waits and releases lock
- `notify()` - Wakes up one waiting thread
- `notifyAll()` - Wakes up all waiting threads

**Must be used inside `synchronized` block!**

**Example: Producer-Consumer Problem**
```java
class SharedResource {
    private int data;
    private boolean hasData = false;
    
    synchronized void produce(int value) throws InterruptedException {
        while (hasData) {
            wait(); // Wait if data already exists
        }
        data = value;
        hasData = true;
        System.out.println("Produced: " + value);
        notify(); // Notify consumer
    }
    
    synchronized int consume() throws InterruptedException {
        while (!hasData) {
            wait(); // Wait if no data
        }
        hasData = false;
        System.out.println("Consumed: " + data);
        notify(); // Notify producer
        return data;
    }
}
```

**Flow:**
1. Producer produces → Consumer waits
2. Producer calls `notify()` → Consumer wakes up
3. Consumer consumes → Producer waits
4. Consumer calls `notify()` → Producer wakes up

---

### 9. Use of `volatile` Keyword

**Purpose:** Ensures variable changes are **visible to all threads immediately**.

**Problem without `volatile`:**
```java
class Task {
    boolean running = true; // Cached in thread's memory
    
    void run() {
        while (running) { // May never see the change!
            // do work
        }
    }
    
    void stop() {
        running = false; // Change might not be visible!
    }
}
```

**Solution with `volatile`:**
```java
class Task {
    volatile boolean running = true; // Always read from main memory
    
    void run() {
        while (running) { // Will see the change immediately
            // do work
        }
    }
    
    void stop() {
        running = false; // Change visible to all threads
    }
}
```

**What `volatile` does:**
- ✅ Ensures **visibility** across threads
- ✅ Prevents **caching** in thread's local memory
- ✅ Reads/writes go to **main memory**
- ❌ Does NOT provide atomicity (use `synchronized` for that)

**When to use:**
- Boolean flags (stop/start)
- Status indicators
- When only ONE thread writes, others only read

---

### 10. Even-Odd Number Printing Using Two Threads

```java
class EvenOddPrinter {
    private int number = 1;
    private int max = 20;
    
    // Method for printing even numbers
    synchronized void printEven() {
        while (number <= max) {
            // Wait if number is odd
            while (number % 2 != 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            if (number <= max) {
                System.out.println(Thread.currentThread().getName() + ": " + number);
                number++;
                notify(); // Wake up odd thread
            }
        }
    }
    
    // Method for printing odd numbers
    synchronized void printOdd() {
        while (number <= max) {
            // Wait if number is even
            while (number % 2 == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            if (number <= max) {
                System.out.println(Thread.currentThread().getName() + ": " + number);
                number++;
                notify(); // Wake up even thread
            }
        }
    }
    
    public static void main(String[] args) {
        EvenOddPrinter printer = new EvenOddPrinter();
        
        Thread oddThread = new Thread(() -> printer.printOdd(), "ODD");
        Thread evenThread = new Thread(() -> printer.printEven(), "EVEN");
        
        oddThread.start();
        evenThread.start();
    }
}
```

**Output:**
```
ODD: 1
EVEN: 2
ODD: 3
EVEN: 4
...
```

---

## EXCEPTION HANDLING SECTION

### 1. Checked vs Unchecked Exceptions

**Checked Exceptions:**
- Checked **at compile time**
- **Must** handle with try-catch or throw
- Represents **expected** problems
- Examples: IOException, SQLException, FileNotFoundException

```java
// Must handle
try {
    FileReader file = new FileReader("file.txt");
} catch (FileNotFoundException e) {
    // Must catch or declare
}
```

**Unchecked Exceptions:**
- Checked **at runtime**
- **Optional** to handle
- Usually **programming errors**
- Examples: NullPointerException, ArithmeticException, ArrayIndexOutOfBoundsException

```java
// No need to handle (but can crash!)
int result = 10 / 0; // ArithmeticException
```

**Hierarchy:**
```
Throwable
├── Exception (Checked)
│   ├── IOException
│   ├── SQLException
│   └── RuntimeException (Unchecked)
│       ├── NullPointerException
│       ├── ArithmeticException
│       └── ArrayIndexOutOfBoundsException
└── Error (Unchecked)
    ├── OutOfMemoryError
    └── StackOverflowError
```

**Simple Rule:**
- **Checked** = Compiler forces you to handle
- **Unchecked** = Compiler doesn't care (your responsibility)

---

### 2. Output of Code with System.exit(0)

```java
try { 
    int a = 10 / 0; 
} 
catch (ArithmeticException e) { 
    System.out.println("Arithmetic"); 
    System.exit(0); 
} 
catch (Exception e) { 
    System.out.println("Exception"); 
} 
finally { 
    System.out.println("Finally"); 
} 
```

**Output:**
```
Arithmetic
```

**Explanation:**
1. `10 / 0` throws `ArithmeticException`
2. First catch block catches it → prints "Arithmetic"
3. `System.exit(0)` **terminates JVM immediately**
4. Finally block **does NOT execute** (only time finally is skipped!)

**Key Point:** `System.exit(0)` is the ONLY way to prevent finally block execution.

**If we remove System.exit(0):**
```
Arithmetic
Finally
```

---

## STRING COMPARISON SECTION

### 3. Output of String Comparison Code

```java
String s1 = "HELLO"; 
String s2 = "HELLO"; 
String s3 = new String("HELLO"); 
System.out.println(s1 == s2);        // ?
System.out.println(s1 == s3);        // ?
System.out.println(s1.equals(s2));   // ?
System.out.println(s1.equals(s3));   // ?
```

**Output:**
```
true
false
true
true
```

**Explanation:**

**String Pool Concept:**
- Java maintains a **String Pool** in memory
- Literals `"HELLO"` are stored in pool
- `new String()` creates object in **heap** (outside pool)

**Memory Layout:**
```
String Pool:
"HELLO" ← s1, s2 both point here

Heap:
new String("HELLO") ← s3 points here
```

**Line by Line:**

1. `s1 == s2` → **true**
   - Both reference **same object** in String Pool
   - `==` compares references (memory addresses)

2. `s1 == s3` → **false**
   - s1 points to String Pool
   - s3 points to Heap object
   - Different memory locations

3. `s1.equals(s2)` → **true**
   - `equals()` compares **content**
   - Both contain "HELLO"

4. `s1.equals(s3)` → **true**
   - `equals()` compares **content**
   - Both contain "HELLO"

**Key Rules:**
- `==` compares **references** (memory address)
- `.equals()` compares **content** (actual value)
- String literals share same object in String Pool
- `new String()` creates new object in Heap

---

## JPA/HIBERNATE SECTION

### 4. What This JPA Code Does

```java
@Entity 
class Employee { 
    @Id 
    @GeneratedValue 
    private Long id; 
    private String name; 
} 

// Test 
Employee e1 = new Employee(); 
e1.setName("Alice"); 
entityManager.persist(e1);  // Save to DB
e1.setName("Bob");           // Change name
System.out.println(e1.getName());
```

**Output:**
```
Bob
```

**Explanation:**

**What Happens:**

1. **Create Entity:** `new Employee()` → Creates object in memory (not in DB yet)

2. **Set Name:** `e1.setName("Alice")` → Object has name "Alice"

3. **Persist:** `entityManager.persist(e1)`
   - Entity enters **Persistent State**
   - Hibernate **tracks** this object
   - Changes made in memory (not yet in DB)

4. **Change Name:** `e1.setName("Bob")`
   - Since entity is persistent, Hibernate **detects** this change
   - Will update DB when transaction commits

5. **Print:** Prints current value = **"Bob"**

**JPA Entity States:**
```
New → Persistent → Detached → Removed
       ↑ (we are here)
```

**When is it saved to DB?**
- When transaction **commits** or `flush()` is called
- At commit time, DB will have name = "Bob" (not "Alice")

**Key Concepts:**

- `@Entity` - Marks class as JPA entity (DB table)
- `@Id` - Marks primary key
- `@GeneratedValue` - Auto-generates ID value
- `persist()` - Makes entity managed by JPA
- Once persisted, entity is **tracked** for changes
- Changes are synced to DB at transaction commit

**Final State:**
- Object in memory: name = "Bob"
- Database (after commit): name = "Bob"
- "Alice" was never saved to DB!

---

## SUMMARY CHEAT SHEET

**Multithreading:**
- Process = Program, Thread = Lightweight unit
- Use `synchronized` to prevent race conditions
- Use lock ordering to prevent deadlock
- `volatile` = visibility, `synchronized` = atomicity
- `wait/notify` for inter-thread communication

**Exceptions:**
- Checked = Compile-time, must handle
- Unchecked = Runtime, optional to handle
- `finally` always runs (except System.exit)

**Strings:**
- `==` compares references
- `.equals()` compares content
- Literals go to String Pool

**JPA:**
- `persist()` makes entity managed
- Changes are tracked and synced to DB
- Actual DB update happens at commit
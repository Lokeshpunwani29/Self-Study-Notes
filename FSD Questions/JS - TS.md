# JavaScript & TypeScript Interview Guide
## Complete Interview Preparation Document

---

# PART 1: JAVASCRIPT FUNDAMENTALS

---

## 1. Difference Between == and ===

### Simple Explanation:
- `==` checks if values are equal (converts types if needed)
- `===` checks if values AND types are both equal (no conversion)

### Interview Answer:
"The `==` operator performs type coercion before comparison, meaning it converts values to the same type before comparing. The `===` operator is a strict equality operator that checks both value and type without any conversion."

### Examples:

```javascript
// == (Loose Equality)
5 == "5"        // true (converts string to number)
true == 1       // true (converts boolean to number)
null == undefined  // true (special case)
0 == false      // true
"" == false     // true

// === (Strict Equality)
5 === "5"       // false (different types)
true === 1      // false (different types)
null === undefined  // false
0 === false     // false
"" === false    // false

// Real examples
const age = "25";
if (age == 25) {   // true - converts string to number
    console.log("Match with ==");
}
if (age === 25) {  // false - different types
    console.log("Match with ===");
}
```

### Cross Questions:

**Q: Which one should you use in production code?**
A: "Always use `===` (strict equality) unless you have a specific reason to use `==`. It prevents unexpected behavior from type coercion and makes code more predictable."

**Q: What about != vs !==?**
A: "Same concept - `!=` does type coercion, `!==` doesn't. Use `!==` for strict inequality checks."

**Q: When might you use ==?**
A: "One valid use case is checking for null or undefined: `if (value == null)` catches both null and undefined."

---

## 2. Difference Between let, var, and const

### Simple Explanation:
- `var`: Old way, function-scoped, can be redeclared
- `let`: New way, block-scoped, can be reassigned
- `const`: New way, block-scoped, cannot be reassigned

### Detailed Comparison Table:

| Feature | var | let | const |
|---------|-----|-----|-------|
| Scope | Function | Block | Block |
| Redeclaration | Allowed | Not allowed | Not allowed |
| Reassignment | Allowed | Allowed | Not allowed |
| Hoisting | Yes (undefined) | Yes (TDZ) | Yes (TDZ) |
| Global Object | Creates property | No | No |

### Examples:

```javascript
// ===== VAR =====
// Function scoped
function testVar() {
    if (true) {
        var x = 10;
    }
    console.log(x); // 10 - accessible outside block
}

// Can be redeclared
var name = "John";
var name = "Jane"; // No error

// Hoisting
console.log(age); // undefined (not error)
var age = 25;

// ===== LET =====
// Block scoped
function testLet() {
    if (true) {
        let y = 20;
    }
    console.log(y); // ReferenceError - not accessible
}

// Cannot be redeclared
let name = "John";
// let name = "Jane"; // SyntaxError

// Can be reassigned
let count = 1;
count = 2; // Works fine

// Hoisting (Temporal Dead Zone)
// console.log(score); // ReferenceError
let score = 100;

// ===== CONST =====
// Block scoped (same as let)
if (true) {
    const z = 30;
}
// console.log(z); // ReferenceError

// Cannot be reassigned
const PI = 3.14;
// PI = 3.14159; // TypeError

// Must be initialized
// const value; // SyntaxError

// Objects and arrays can be modified
const person = { name: "John" };
person.name = "Jane"; // Works - modifying property
// person = {}; // TypeError - reassigning variable

const arr = [1, 2, 3];
arr.push(4); // Works - modifying array
// arr = []; // TypeError - reassigning variable
```

### Real-World Example:

```javascript
// Good practice example
function calculateTotal(items) {
    const TAX_RATE = 0.08;  // Won't change - use const
    let total = 0;           // Will change - use let
    
    for (let i = 0; i < items.length; i++) {  // Loop variable - use let
        total += items[i].price;
    }
    
    const finalAmount = total * (1 + TAX_RATE);  // Calculated once - use const
    return finalAmount;
}
```

### Cross Questions:

**Q: What is Temporal Dead Zone (TDZ)?**
A: "It's the period between entering scope and declaration for let/const. Accessing variables in TDZ throws ReferenceError."

**Q: Why is var considered problematic?**
A: "Function scope can cause bugs, allows redeclaration which can lead to accidental overwrites, and hoisting behavior is confusing."

**Q: When would you use each?**
A: 
- `const`: Default choice for values that won't be reassigned (most cases)
- `let`: When you need to reassign values (counters, accumulations)
- `var`: Avoid in modern JavaScript

---

## 3. What are ES6 Features?

### Interview Answer:
"ES6 (ECMAScript 2015) introduced significant improvements to JavaScript, making it more powerful and developer-friendly."

### Major ES6 Features:

#### 1. **Arrow Functions**
```javascript
// Old way
function add(a, b) {
    return a + b;
}

// ES6 way
const add = (a, b) => a + b;
```

#### 2. **Let and Const**
```javascript
let variable = "can change";
const constant = "cannot change";
```

#### 3. **Template Literals**
```javascript
const name = "John";
const age = 30;

// Old way
const message = "Hello, " + name + ". You are " + age + " years old.";

// ES6 way
const message = `Hello, ${name}. You are ${age} years old.`;
```

#### 4. **Destructuring**
```javascript
// Array destructuring
const [first, second] = [1, 2, 3];

// Object destructuring
const person = { name: "John", age: 30 };
const { name, age } = person;
```

#### 5. **Default Parameters**
```javascript
function greet(name = "Guest") {
    return `Hello, ${name}!`;
}

greet(); // "Hello, Guest!"
greet("John"); // "Hello, John!"
```

#### 6. **Spread Operator**
```javascript
// Arrays
const arr1 = [1, 2, 3];
const arr2 = [...arr1, 4, 5]; // [1, 2, 3, 4, 5]

// Objects
const obj1 = { a: 1, b: 2 };
const obj2 = { ...obj1, c: 3 }; // { a: 1, b: 2, c: 3 }
```

#### 7. **Rest Parameters**
```javascript
function sum(...numbers) {
    return numbers.reduce((acc, num) => acc + num, 0);
}

sum(1, 2, 3, 4); // 10
```

#### 8. **Classes**
```javascript
class Person {
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }
    
    greet() {
        return `Hello, I'm ${this.name}`;
    }
}

const john = new Person("John", 30);
```

#### 9. **Promises**
```javascript
const fetchData = () => {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            resolve("Data loaded");
        }, 1000);
    });
};

fetchData().then(data => console.log(data));
```

#### 10. **Modules (Import/Export)**
```javascript
// export.js
export const PI = 3.14;
export function add(a, b) {
    return a + b;
}

// import.js
import { PI, add } from './export.js';
```

#### 11. **Map and Set**
```javascript
// Map
const map = new Map();
map.set('key', 'value');
map.get('key'); // 'value'

// Set
const set = new Set([1, 2, 2, 3]);
console.log(set); // Set {1, 2, 3}
```

#### 12. **For...of Loop**
```javascript
const arr = [1, 2, 3];
for (let value of arr) {
    console.log(value);
}
```

### Cross Questions:

**Q: What's the difference between for...of and for...in?**
A: 
- `for...of` iterates over values (arrays, strings, maps)
- `for...in` iterates over keys/properties (objects)

**Q: What are the benefits of ES6?**
A: "Cleaner syntax, better scoping, improved async handling, better OOP support, and more expressive code."

---

## 4. Arrow Functions in JavaScript

### Simple Explanation:
Arrow functions are a shorter way to write functions in JavaScript using the `=>` syntax.

### Interview Answer:
"Arrow functions are a concise syntax for writing function expressions introduced in ES6. They have a shorter syntax and handle the `this` keyword differently than traditional functions."

### Syntax:

```javascript
// Traditional function
function add(a, b) {
    return a + b;
}

// Arrow function - full syntax
const add = (a, b) => {
    return a + b;
};

// Arrow function - implicit return
const add = (a, b) => a + b;

// Single parameter - parentheses optional
const square = x => x * x;

// No parameters
const greet = () => "Hello!";

// Multiple statements
const calculate = (a, b) => {
    const sum = a + b;
    const product = a * b;
    return { sum, product };
};
```

### Real-World Examples:

```javascript
// 1. Array methods
const numbers = [1, 2, 3, 4, 5];

// Map
const doubled = numbers.map(n => n * 2);
// [2, 4, 6, 8, 10]

// Filter
const evens = numbers.filter(n => n % 2 === 0);
// [2, 4]

// Reduce
const sum = numbers.reduce((acc, n) => acc + n, 0);
// 15

// 2. Callbacks
setTimeout(() => {
    console.log("Delayed message");
}, 1000);

// 3. Event handlers
button.addEventListener('click', () => {
    console.log('Button clicked');
});
```

### Key Difference: The `this` Keyword

```javascript
// Traditional function - 'this' is dynamic
function Person() {
    this.age = 0;
    
    setInterval(function() {
        this.age++; // 'this' refers to global object
        console.log(this.age); // NaN
    }, 1000);
}

// Arrow function - 'this' is lexical
function Person() {
    this.age = 0;
    
    setInterval(() => {
        this.age++; // 'this' refers to Person instance
        console.log(this.age); // 1, 2, 3...
    }, 1000);
}

// Real example
const obj = {
    name: "Object",
    
    // Regular function
    regularFunc: function() {
        console.log(this.name); // "Object"
    },
    
    // Arrow function
    arrowFunc: () => {
        console.log(this.name); // undefined (inherits from outer scope)
    },
    
    // Method with arrow function inside
    method: function() {
        const inner = () => {
            console.log(this.name); // "Object" - inherits from method
        };
        inner();
    }
};
```

### When to Use Arrow Functions:

```javascript
// ✅ Good use cases
// 1. Short callbacks
const names = people.map(person => person.name);

// 2. When you need lexical 'this'
class Timer {
    constructor() {
        this.seconds = 0;
        setInterval(() => {
            this.seconds++;
        }, 1000);
    }
}

// 3. Function expressions
const add = (a, b) => a + b;

// ❌ Avoid arrow functions for
// 1. Object methods (need dynamic 'this')
const person = {
    name: "John",
    // Don't do this
    greet: () => {
        console.log(`Hello, ${this.name}`); // 'this' is not person
    },
    // Do this instead
    greet: function() {
        console.log(`Hello, ${this.name}`);
    }
};

// 2. Constructors
// const Person = (name) => { this.name = name; }; // Error!

// 3. Methods using 'arguments'
// const func = () => console.log(arguments); // 'arguments' not available
```

### Cross Questions:

**Q: Can arrow functions be used as constructors?**
A: "No, arrow functions cannot be used with the `new` keyword. They don't have their own `this` binding."

**Q: What's the difference between `() => {}` and `() => ({})`?**
A: "The first is a function body with curly braces. The second returns an object using parentheses."

**Q: Do arrow functions have their own `arguments` object?**
A: "No, they inherit `arguments` from their parent scope. Use rest parameters instead: `(...args) => {}`"

---

## 5. What is the Event Loop?

### Simple Explanation:
The event loop is JavaScript's way of handling multiple tasks. It continuously checks if there's work to do and executes it in order.

### Interview Answer:
"The event loop is JavaScript's mechanism for handling asynchronous operations in a single-threaded environment. It continuously monitors the call stack and callback queue, executing tasks one at a time."

### How JavaScript Executes Code:

```javascript
// JavaScript has:
// 1. Call Stack - where code executes
// 2. Web APIs - browser provided (setTimeout, fetch, etc.)
// 3. Callback Queue (Task Queue) - waiting callbacks
// 4. Microtask Queue - promises, async/await
// 5. Event Loop - coordinates everything
```

### Visual Example:

```javascript
console.log('1. Start');

setTimeout(() => {
    console.log('2. Timeout');
}, 0);

Promise.resolve().then(() => {
    console.log('3. Promise');
});

console.log('4. End');

// Output:
// 1. Start
// 4. End
// 3. Promise (microtask - higher priority)
// 2. Timeout (macrotask)
```

### Step-by-Step Execution:

```javascript
// Example code
function first() {
    console.log('First');
    second();
    console.log('After second');
}

function second() {
    console.log('Second');
}

first();

// Execution flow:
// 1. first() added to call stack
// 2. console.log('First') executes
// 3. second() added to call stack
// 4. console.log('Second') executes
// 5. second() removed from stack
// 6. console.log('After second') executes
// 7. first() removed from stack
```

### Async Example:

```javascript
console.log('Start');

setTimeout(() => {
    console.log('Timeout 1');
}, 0);

setTimeout(() => {
    console.log('Timeout 2');
}, 0);

Promise.resolve()
    .then(() => console.log('Promise 1'))
    .then(() => console.log('Promise 2'));

console.log('End');

// Execution order:
// 1. "Start" - synchronous
// 2. "End" - synchronous
// 3. "Promise 1" - microtask queue
// 4. "Promise 2" - microtask queue
// 5. "Timeout 1" - callback queue
// 6. "Timeout 2" - callback queue
```

### Event Loop Priority:

```javascript
// Priority order (highest to lowest):
// 1. Synchronous code (call stack)
// 2. Microtasks (Promises, queueMicrotask)
// 3. Macrotasks (setTimeout, setInterval, I/O)

// Example showing priority
console.log('Sync 1');

setTimeout(() => console.log('Macro 1'), 0);

Promise.resolve().then(() => {
    console.log('Micro 1');
    setTimeout(() => console.log('Macro 2'), 0);
});

Promise.resolve().then(() => console.log('Micro 2'));

console.log('Sync 2');

// Output:
// Sync 1
// Sync 2
// Micro 1
// Micro 2
// Macro 1
// Macro 2
```

### Real-World Example:

```javascript
// Fetching data
function fetchUserData(userId) {
    console.log('Starting fetch...');
    
    fetch(`/api/users/${userId}`)  // Goes to Web API
        .then(response => response.json())  // Microtask
        .then(data => {
            console.log('User data:', data);  // Microtask
            // Update UI here
        })
        .catch(error => {
            console.error('Error:', error);
        });
    
    console.log('Fetch initiated (non-blocking)');
}

// This doesn't block other code from running
fetchUserData(123);
console.log('Other code continues...');
```

### Cross Questions:

**Q: Why is JavaScript single-threaded but can handle async operations?**
A: "JavaScript uses the event loop with Web APIs. While JS itself is single-threaded, browser APIs handle async operations in the background, then place callbacks in queues for execution."

**Q: What's the difference between microtasks and macrotasks?**
A:
- Microtasks: Promises, async/await, queueMicrotask (higher priority)
- Macrotasks: setTimeout, setInterval, I/O operations (lower priority)

**Q: Can the event loop block?**
A: "Yes, if synchronous code takes too long (heavy computation), it blocks the event loop, making the app unresponsive. This is why we use Web Workers for heavy tasks."

---

## 6. What is DOM and How Does It Work? What Data Structure is Used?

### Simple Explanation:
DOM (Document Object Model) is how JavaScript sees and interacts with HTML. It's like a family tree of all elements on the webpage.

### Interview Answer:
"DOM is a programming interface that represents HTML/XML documents as a tree structure. It allows JavaScript to access, modify, add, or delete HTML elements and their attributes dynamically."

### DOM Structure - Tree Data Structure:

```
Document
  └── html
       ├── head
       │    ├── title
       │    └── meta
       └── body
            ├── div (id="header")
            │    ├── h1
            │    └── nav
            │         └── ul
            │              ├── li
            │              └── li
            └── div (id="content")
                 └── p
```

### Data Structure: Tree

**Answer:** "The DOM uses a **tree data structure**. Each element is a node, with parent-child relationships forming a hierarchy from the root (document) to leaves (text nodes)."

### How DOM Works:

```javascript
// 1. Browser parses HTML
// HTML:
// <div id="container">
//     <h1>Hello</h1>
//     <p class="text">World</p>
// </div>

// 2. Creates DOM tree (object representation)

// 3. JavaScript can manipulate it
const container = document.getElementById('container');
console.log(container); // <div id="container">...</div>

// Tree traversal
console.log(container.parentNode); // body
console.log(container.children); // [h1, p]
console.log(container.firstChild); // h1
console.log(container.lastChild); // p
```

### DOM Manipulation Examples:

```javascript
// ===== Accessing Elements =====

// By ID
const header = document.getElementById('header');

// By class
const buttons = document.getElementsByClassName('btn');

// By tag
const paragraphs = document.getElementsByTagName('p');

// Query selector (CSS selectors)
const firstButton = document.querySelector('.btn');
const allButtons = document.querySelectorAll('.btn');

// ===== Modifying Elements =====

// Change content
element.textContent = 'New text';
element.innerHTML = '<strong>Bold text</strong>';

// Change attributes
element.setAttribute('class', 'active');
element.id = 'newId';
element.style.color = 'red';

// Add/remove classes
element.classList.add('active');
element.classList.remove('hidden');
element.classList.toggle('visible');

// ===== Creating and Adding Elements =====

// Create new element
const newDiv = document.createElement('div');
newDiv.textContent = 'New content';
newDiv.className = 'box';

// Add to DOM
parentElement.appendChild(newDiv);
parentElement.insertBefore(newDiv, referenceElement);

// ===== Removing Elements =====

element.remove();
// or
parentElement.removeChild(childElement);

// ===== Event Handling =====

button.addEventListener('click', function(event) {
    console.log('Button clicked!');
    event.preventDefault(); // Prevent default behavior
});
```

### Real-World Example:

```javascript
// Todo List Application
<!DOCTYPE html>
<html>
<body>
    <div id="todo-app">
        <input type="text" id="task-input" placeholder="Enter task">
        <button id="add-btn">Add Task</button>
        <ul id="task-list"></ul>
    </div>

    <script>
        // DOM elements
        const input = document.getElementById('task-input');
        const addBtn = document.getElementById('add-btn');
        const taskList = document.getElementById('task-list');

        // Add task function
        function addTask() {
            const taskText = input.value.trim();
            
            if (taskText === '') return;

            // Create new list item
            const li = document.createElement('li');
            li.textContent = taskText;

            // Create delete button
            const deleteBtn = document.createElement('button');
            deleteBtn.textContent = 'Delete';
            deleteBtn.onclick = () => li.remove();

            // Assemble and add to DOM
            li.appendChild(deleteBtn);
            taskList.appendChild(li);

            // Clear input
            input.value = '';
        }

        // Event listeners
        addBtn.addEventListener('click', addTask);
        input.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') addTask();
        });
    </script>
</body>
</html>
```

### DOM Tree Traversal:

```javascript
const element = document.querySelector('.my-element');

// Moving up (parents)
element.parentNode
element.parentElement
element.closest('.ancestor-class')

// Moving down (children)
element.children
element.firstElementChild
element.lastElementChild
element.querySelector('.child-class')

// Moving sideways (siblings)
element.nextElementSibling
element.previousElementSibling

// Example
const list = document.querySelector('ul');
const firstItem = list.firstElementChild; // First <li>
const secondItem = firstItem.nextElementSibling; // Second <li>
const parent = firstItem.parentElement; // <ul>
```

### Cross Questions:

**Q: What's the difference between textContent and innerHTML?**
A:
- `textContent`: Gets/sets text only, safe from XSS
- `innerHTML`: Gets/sets HTML, can execute scripts, potential security risk

**Q: What are Node types in DOM?**
A: 
- Element nodes (1): HTML elements
- Text nodes (3): Text content
- Comment nodes (8): Comments
- Document node (9): Root of tree

**Q: How is the DOM tree similar to other tree structures?**
A: "It has a root (document), parent-child relationships, can be traversed (DFS, BFS), and allows hierarchical access - just like binary trees or file systems."

---

## 7. Explain Virtual DOM vs DOM

### Simple Explanation:
- **Real DOM**: The actual webpage structure that browser shows
- **Virtual DOM**: A JavaScript copy of the real DOM used by frameworks like React

### Interview Answer:
"The Virtual DOM is a lightweight JavaScript representation of the real DOM. Instead of directly manipulating the expensive real DOM, frameworks like React update the Virtual DOM first, calculate differences (diffing), and then efficiently update only the changed parts in the real DOM."

### Detailed Comparison:

| Aspect | Real DOM | Virtual DOM |
|--------|----------|-------------|
| Nature | Browser's tree structure | JavaScript object |
| Speed | Slow updates | Fast updates |
| Memory | More memory usage | Less memory usage |
| Update Method | Repaints entire tree | Updates only changed nodes |
| Direct Manipulation | Yes | No (abstracted) |
| Framework | Vanilla JS, jQuery | React, Vue |

### How It Works:

```javascript
// ===== REAL DOM =====
// Direct manipulation (slow for frequent updates)

// Before
<div id="container">
    <h1>Count: 0</h1>
    <button>Click</button>
</div>

// After clicking (entire subtree may re-render)
document.querySelector('h1').textContent = 'Count: 1';
// Browser recalculates layout, repaints, etc.

// ===== VIRTUAL DOM =====
// React example (optimized)

// 1. Initial Virtual DOM
{
    type: 'div',
    props: {
        id: 'container',
        children: [
            { type: 'h1', props: { children: 'Count: 0' } },
            { type: 'button', props: { children: 'Click' } }
        ]
    }
}

// 2. User clicks button, state changes

// 3. New Virtual DOM created
{
    type: 'div',
    props: {
        id: 'container',
        children: [
            { type: 'h1', props: { children: 'Count: 1' } }, // Changed
            { type: 'button', props: { children: 'Click' } }
        ]
    }
}

// 4. Diffing: Compare old vs new Virtual DOM
// Found: h1's text changed from 'Count: 0' to 'Count: 1'

// 5. Update only that specific node in Real DOM
// Much faster than rebuilding entire tree
```

### Visual Example:

```javascript
// ===== WITHOUT VIRTUAL DOM (Traditional) =====

// Updating a list of 1000 items
const list = document.getElementById('list');

function updateList(items) {
    // Clears and rebuilds entire list (SLOW)
    list.innerHTML = '';
    
    items.forEach(item => {
        const li = document.createElement('li');
        li.textContent = item;
        list.appendChild(li);
    });
    // Browser recalculates everything, repaints
}

// ===== WITH VIRTUAL DOM (React) =====

function TodoList({ items }) {
    // React creates Virtual DOM
    // Compares with previous version
    // Updates only changed items
    return (
        <ul>
            {items.map(item => (
                <li key={item.id}>{item.text}</li>
            ))}
        </ul>
    );
}

// When items change:
// 1. New Virtual DOM created
// 2. Diff with old Virtual DOM
// 3. Only changed <li> elements updated in Real DOM
```

### Step-by-Step Process:

```javascript
// Example: Updating a counter

// Step 1: Initial Render
// Virtual DOM: <div><h1>0</h1></div>
// Real DOM: <div><h1>0</h1></div>

// Step 2: State Changes (count = 1)
// New Virtual DOM: <div><h1>1</h1></div>

// Step 3: Diffing Algorithm
// Compares old Virtual DOM vs new Virtual DOM
// Finds: Only text in <h1> changed

// Step 4: Minimal Update
// Updates only the text node in Real DOM
// No need to recreate <div> or <h1> elements
```

### Performance Comparison:

```javascript
// Scenario: Update 1 item in a list of 1000 items

// ===== REAL DOM (Direct Manipulation) =====
// Option 1: Rebuild entire list
function updateWithRealDOM1(items, updatedItem) {
    list.innerHTML = ''; // Clear everything
    items.forEach(item => {
        const li = document.createElement('li');
        li.textContent = item.text;
        list.appendChild(li); // 1000 appendChild operations
    });
}
// Cost: Very High - entire list re-rendered

// Option 2: Find and update specific item
function updateWithRealDOM2(items, updatedItem) {
    const li = document.querySelector(`[data-id="${updatedItem.id}"]`);
    li.textContent = updatedItem.text;
}
// Cost: Low - but you need to track and find elements manually

// ===== VIRTUAL DOM (React) =====
function TodoList({ items }) {
    return (
        <ul>
            {items.map(item => (
                <li key={item.id}>{item.text}</li>
            ))}
        </ul>
    );
}
// Cost: Low - React automatically finds and updates only changed item
// Benefit: You write simple code, React optimizes performance
```

### Real-World Benefits:

```javascript
// Complex UI with multiple updates

// Without Virtual DOM - Manual optimization needed
function updateDashboard(data) {
    // Must manually track what changed
    if (data.userChanged) {
        document.getElementById('username').textContent = data.user.name;
    }
    if (data.notificationsChanged) {
        updateNotifications(data.notifications);
    }
    if (data.messagesChanged) {
        updateMessages(data.messages);
    }
    // Error-prone, hard to maintain
}

// With Virtual DOM (React) - Automatic optimization
function Dashboard({ data }) {
    return (
        <div>
            <UserInfo user={data.user} />
            <Notifications items={data.notifications} />
            <Messages items={data.messages} />
        </div>
    );
}
// React automatically figures out what changed and updates efficiently
```

### Limitations of Virtual DOM:

```javascript
// Not always faster
// 1. For very simple updates, direct DOM might be faster
document.getElementById('counter').textContent++; // Very fast

// 2. Initial render is same speed (nothing to diff)

// 3. Large lists without keys can be slow
// Bad
{items.map(item => <li>{item}</li>)} // React can't track items

// Good
{items.map(item => <li key={item.id}>{item}</li>)} // React knows which items changed
```

### Cross Questions:

**Q: Does Virtual DOM make React always faster?**
A: "No. For simple operations, direct DOM manipulation might be faster. Virtual DOM shines when handling frequent, complex updates across many components."

**Q: What is the diffing algorithm?**
A: "It's the algorithm that compares old and new Virtual DOM trees to find the minimum number of operations needed to update the real DOM. React uses a heuristic O(n) algorithm instead of O(n³)."

**Q: Can you use Virtual DOM without React?**
A: "Yes, libraries like Vue, Preact, and Snabbdom also use Virtual DOM. You can even create your own simple implementation."

**Q: What are the key benefits of Virtual DOM?**
A:
1. Performance optimization for complex UIs
2. Developer writes declarative code
3. Framework handles optimization
4. Enables server-side rendering
5. Makes testing easier

---

## 8. Implement an Automatic Counter in JavaScript

### Question:
Create a counter that automatically increments every second and displays on the page.

### Solution 1: Basic Implementation

```html
<!DOCTYPE html>
<html>
<head>
    <title>Auto Counter</title>
    <style>
        #counter {
            font-size: 48px;
            text-align: center;
            margin-top: 50px;
            color: #333;
        }
        .controls {
            text-align: center;
            margin-top: 20px;
        }
        button {
            font-size: 16px;
            padding: 10px 20px;
            margin: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div id="counter">0</div>
    <div class="controls">
        <button id="start">Start</button>
        <button id="stop">Stop</button>
        <button id="reset">Reset</button>
    </div>

    <script>
        let count = 0;
        let intervalId = null;
        
        const counterDisplay = document.getElementById('counter');
        const startBtn = document.getElementById('start');
        const stopBtn = document.getElementById('stop');
        const resetBtn = document.getElementById('reset');
        
        // Start counter
        function startCounter() {
            if (intervalId !== null) return; // Already running
            
            intervalId = setInterval(() => {
                count++;
                counterDisplay.textContent = count;
            }, 1000);
        }
        
        // Stop counter
        function stopCounter() {
            if (intervalId === null) return; // Not running
            
            clearInterval(intervalId);
            intervalId = null;
        }
        
        // Reset counter
        function resetCounter() {
            stopCounter();
            count = 0;
            counterDisplay.textContent = count;
        }
        
        // Event listeners
        startBtn.addEventListener('click', startCounter);
        stopBtn.addEventListener('click', stopCounter);
        resetBtn.addEventListener('click', resetCounter);
        
        // Auto-start
        startCounter();
    </script>
</body>
</html>
```

### Solution 2: Object-Oriented Approach

```javascript
class AutoCounter {
    constructor(elementId, interval = 1000) {
        this.element = document.getElementById(elementId);
        this.count = 0;
        this.interval = interval;
        this.intervalId = null;
        this.isRunning = false;
    }
    
    start() {
        if (this.isRunning) {
            console.log('Counter already running');
            return;
        }
        
        this.isRunning = true;
        this.intervalId = setInterval(() => {
            this.count++;
            this.updateDisplay();
        }, this.interval);
        
        console.log('Counter started');
    }
    
    stop() {
        if (!this.isRunning) {
            console.log('Counter not running');
            return;
        }
        
        clearInterval(this.intervalId);
        this.intervalId = null;
        this.isRunning = false;
        console.log('Counter stopped');
    }
    
    reset() {
        this.stop();
        this.count = 0;
        this.updateDisplay();
        console.log('Counter reset');
    }
    
    updateDisplay() {
        if (this.element) {
            this.element.textContent = this.count;
        }
    }
    
    getValue() {
        return this.count;
    }
    
    setValue(value) {
        this.count = value;
        this.updateDisplay();
    }
}

// Usage
const counter = new AutoCounter('counter', 1000);
counter.start();

// Later...
// counter.stop();
// counter.reset();
```

### Solution 3: With Custom Speed Control

```html
<!DOCTYPE html>
<html>
<head>
    <title>Advanced Counter</title>
</head>
<body>
    <div id="counter" style="font-size: 48px; text-align: center;">0</div>
    <div style="text-align: center; margin-top: 20px;">
        <label>Speed (ms): 
            <input type="number" id="speed" value="1000" min="100" max="5000">
        </label>
        <br><br>
        <button id="start">Start</button>
        <button id="pause">Pause</button>
        <button id="reset">Reset</button>
        <button id="increment">+1</button>
        <button id="decrement">-1</button>
    </div>

    <script>
        class Counter {
            constructor() {
                this.count = 0;
                this.intervalId = null;
                this.speed = 1000;
                this.isRunning = false;
                
                this.initElements();
                this.attachEvents();
            }
            
            initElements() {
                this.display = document.getElementById('counter');
                this.speedInput = document.getElementById('speed');
                this.startBtn = document.getElementById('start');
                this.pauseBtn = document.getElementById('pause');
                this.resetBtn = document.getElementById('reset');
                this.incBtn = document.getElementById('increment');
                this.decBtn = document.getElementById('decrement');
            }
            
            attachEvents() {
                this.startBtn.onclick = () => this.start();
                this.pauseBtn.onclick = () => this.pause();
                this.resetBtn.onclick = () => this.reset();
                this.incBtn.onclick = () => this.increment();
                this.decBtn.onclick = () => this.decrement();
                this.speedInput.oninput = (e) => this.changeSpeed(e.target.value);
            }
            
            start() {
                if (this.isRunning) return;
                
                this.isRunning = true;
                this.startBtn.disabled = true;
                this.pauseBtn.disabled = false;
                
                this.intervalId = setInterval(() => {
                    this.count++;
                    this.updateDisplay();
                }, this.speed);
            }
            
            pause() {
                if (!this.isRunning) return;
                
                clearInterval(this.intervalId);
                this.isRunning = false;
                this.startBtn.disabled = false;
                this.pauseBtn.disabled = true;
            }
            
            reset() {
                this.pause();
                this.count = 0;
                this.updateDisplay();
            }
            
            increment() {
                this.count++;
                this.updateDisplay();
            }
            
            decrement() {
                this.count--;
                this.updateDisplay();
            }
            
            changeSpeed(newSpeed) {
                this.speed = parseInt(newSpeed);
                if (this.isRunning) {
                    this.pause();
                    this.start();
                }
            }
            
            updateDisplay() {
                this.display.textContent = this.count;
            }
        }
        
        // Initialize
        const counter = new Counter();
    </script>
</body>
</html>
```

### Solution 4: Using React (Modern Approach)

```javascript
import React, { useState, useEffect, useRef } from 'react';

function AutoCounter() {
    const [count, setCount] = useState(0);
    const [isRunning, setIsRunning] = useState(false);
    const [speed, setSpeed] = useState(1000);
    const intervalRef = useRef(null);
    
    useEffect(() => {
        if (isRunning) {
            intervalRef.current = setInterval(() => {
                setCount(prevCount => prevCount + 1);
            }, speed);
        } else {
            if (intervalRef.current) {
                clearInterval(intervalRef.current);
            }
        }
        
        return () => {
            if (intervalRef.current) {
                clearInterval(intervalRef.current);
            }
        };
    }, [isRunning, speed]);
    
    const handleStart = () => setIsRunning(true);
    const handlePause = () => setIsRunning(false);
    const handleReset = () => {
        setIsRunning(false);
        setCount(0);
    };
    
    return (
        <div style={{ textAlign: 'center', marginTop: '50px' }}>
            <h1 style={{ fontSize: '48px' }}>{count}</h1>
            <div>
                <label>
                    Speed (ms): 
                    <input 
                        type="number" 
                        value={speed} 
                        onChange={(e) => setSpeed(Number(e.target.value))}
                        min="100"
                        max="5000"
                    />
                </label>
            </div>
            <div style={{ marginTop: '20px' }}>
                <button onClick={handleStart} disabled={isRunning}>
                    Start
                </button>
                <button onClick={handlePause} disabled={!isRunning}>
                    Pause
                </button>
                <button onClick={handleReset}>Reset</button>
                <button onClick={() => setCount(count + 1)}>+1</button>
                <button onClick={() => setCount(count - 1)}>-1</button>
            </div>
        </div>
    );
}

export default AutoCounter;
```

### Interview Tips:

**What to Mention:**
1. "I use `setInterval` to execute code repeatedly at fixed intervals"
2. "Store `intervalId` to control start/stop"
3. "Use `clearInterval` to stop the counter"
4. "Handle edge cases like multiple starts"
5. "Clean up intervals to prevent memory leaks"

**Common Mistakes to Avoid:**
- Not clearing intervals (memory leak)
- Not preventing multiple intervals
- Not handling edge cases

---

# PART 2: TYPESCRIPT

---

## 1. What is TypeScript and Why is it Used?

### Simple Explanation:
TypeScript is JavaScript with types. It helps catch errors before running the code.

### Interview Answer:
"TypeScript is a strongly-typed superset of JavaScript developed by Microsoft. It adds static typing, interfaces, and advanced features to JavaScript, then compiles to plain JavaScript. It's used to catch errors during development rather than at runtime."

### Basic Example:

```typescript
// JavaScript - No type checking
function add(a, b) {
    return a + b;
}

add(5, "10");  // Returns "510" - unexpected!
add(5, 10);    // Returns 15 - correct

// TypeScript - Type checking
function add(a: number, b: number): number {
    return a + b;
}

add(5, "10");  // ❌ Error: Argument of type 'string' not assignable to 'number'
add(5, 10);    // ✅ Returns 15
```

### Why TypeScript is Used:

```typescript
// 1. Type Safety
interface User {
    id: number;
    name: string;
    email: string;
}

function getUser(id: number): User {
    // TypeScript ensures we return correct shape
    return {
        id: id,
        name: "John",
        email: "john@example.com"
    };
}

const user = getUser(1);
console.log(user.name); // ✅ TypeScript knows 'name' exists
// console.log(user.phone); // ❌ Error: Property 'phone' doesn't exist

// 2. Better IDE Support
// TypeScript provides:
// - Autocomplete
// - IntelliSense
// - Refactoring tools
// - Go to definition

// 3. Early Error Detection
let count: number = 5;
// count = "five"; // ❌ Error caught at compile time!

// 4. Self-Documentation
function calculateDiscount(
    price: number, 
    discount: number
): number {
    // Function signature clearly shows types
    return price - (price * discount / 100);
}
```

### Real-World Benefits:

```typescript
// Without TypeScript - Runtime errors
class ShoppingCart {
    constructor() {
        this.items = [];
    }
    
    addItem(item) {
        this.items.push(item);
    }
    
    getTotal() {
        return this.items.reduce((sum, item) => sum + item.price, 0);
    }
}

const cart = new ShoppingCart();
cart.addItem({ name: "Book", price: 20 });
cart.addItem({ name: "Pen" }); // Missing price!
cart.getTotal(); // Runtime error: undefined + 20

// With TypeScript - Compile-time errors
interface CartItem {
    name: string;
    price: number;
    quantity?: number; // Optional
}

class ShoppingCart {
    private items: CartItem[] = [];
    
    addItem(item: CartItem): void {
        this.items.push(item);
    }
    
    getTotal(): number {
        return this.items.reduce((sum, item) => sum + item.price, 0);
    }
}

const cart = new ShoppingCart();
cart.addItem({ name: "Book", price: 20 });
// cart.addItem({ name: "Pen" }); // ❌ Error: Property 'price' is missing
```

### Cross Questions:

**Q: Is TypeScript a new language?**
A: "No, it's a superset of JavaScript. All valid JavaScript is valid TypeScript. It adds optional static typing and compiles to JavaScript."

**Q: Does TypeScript run in browsers?**
A: "No, TypeScript must be compiled (transpiled) to JavaScript first. Browsers only understand JavaScript."

**Q: What's the main advantage of TypeScript?**
A: "Catching errors at development time instead of runtime, better tooling support, and improved code maintainability in large projects."

---

## 2. Difference Between JavaScript and TypeScript

### Quick Comparison Table:

| Feature | JavaScript | TypeScript |
|---------|------------|------------|
| Type System | Dynamic typing | Static typing |
| Error Detection | Runtime | Compile-time |
| Learning Curve | Easier | Steeper |
| File Extension | .js | .ts |
| Compilation | Interpreted | Compiled to JS |
| Interfaces | No | Yes |
| Generics | No | Yes |
| IDE Support | Basic | Advanced |

### Detailed Examples:

```javascript
// ===== JAVASCRIPT =====

// 1. No type checking
let value = 5;
value = "string"; // Allowed
value = true;     // Allowed

// 2. No compile-time errors
function divide(a, b) {
    return a / b;
}
divide("10", "2"); // Returns NaN - bug not caught!

// 3. No interfaces
const user = {
    name: "John",
    age: 30
};
// Typo in property name
console.log(user.nmae); // undefined - no error!

// 4. No autocomplete for custom objects
function processUser(user) {
    // IDE doesn't know what properties user has
    user.   // No suggestions
}
```

```typescript
// ===== TYPESCRIPT =====

// 1. Strong typing
let value: number = 5;
// value = "string"; // ❌ Error: Type 'string' not assignable to 'number'
// value = true;     // ❌ Error: Type 'boolean' not assignable to 'number'

// 2. Compile-time errors
function divide(a: number, b: number): number {
    return a / b;
}
// divide("10", "2"); // ❌ Error: Argument of type 'string' not assignable

// 3. Interfaces for structure
interface User {
    name: string;
    age: number;
}

const user: User = {
    name: "John",
    age: 30
};
// console.log(user.nmae); // ❌ Error: Property 'nmae' doesn't exist

// 4. Full autocomplete
function processUser(user: User) {
    user.  // ✅ IDE shows: name, age
}
```

### Feature Comparison Examples:

```typescript
// ===== ENUMS =====
// TypeScript only
enum Status {
    Active,
    Inactive,
    Pending
}

let userStatus: Status = Status.Active;

// JavaScript equivalent
const Status = {
    Active: 0,
    Inactive: 1,
    Pending: 2
};

// ===== GENERICS =====
// TypeScript only
function identity<T>(arg: T): T {
    return arg;
}

let num = identity<number>(42);    // Type: number
let str = identity<string>("hi");  // Type: string

// JavaScript - no way to enforce this

// ===== ACCESS MODIFIERS =====
// TypeScript only
class BankAccount {
    private balance: number;  // Can't access outside class
    public accountNumber: string;  // Can access anywhere
    protected owner: string;  // Can access in subclasses
    
    constructor(initial: number) {
        this.balance = initial;
    }
}

// JavaScript - no true private before ES2022
class BankAccount {
    #balance;  // Private field (ES2022)
    
    constructor(initial) {
        this.#balance = initial;
    }
}
```

### Cross Questions:

**Q: Can I use JavaScript libraries in TypeScript?**
A: "Yes! TypeScript supports JavaScript libraries. For better type safety, you can use @types definitions (e.g., @types/react)."

**Q: Do I need to convert all JavaScript to TypeScript at once?**
A: "No, you can adopt TypeScript gradually. You can have both .js and .ts files in the same project."

---

## 3. Why Would You Use TypeScript Instead of JavaScript?

### Interview Answer:
"I would choose TypeScript for medium to large-scale applications because it provides type safety, better tooling, easier refactoring, and catches bugs during development. For small projects or prototypes, vanilla JavaScript might be sufficient."

### When to Use TypeScript:

```typescript
// ✅ USE TYPESCRIPT FOR:

// 1. Large-scale applications
// - Multiple developers
// - Complex business logic
// - Long-term maintenance

// 2. Complex data structures
interface APIResponse {
    data: {
        users: User[];
        pagination: {
            page: number;
            total: number;
        };
    };
    status: 'success' | 'error';
    message?: string;
}

// 3. Team collaboration
// TypeScript acts as live documentation
function calculateTax(income: number, deductions: number[]): number {
    // Clear what types are expected
}

// 4. Refactoring confidence
// If you rename a property, TypeScript shows all places that need updating

// 5. Third-party library integration
// Type definitions provide autocomplete and error checking
```

### When JavaScript is Enough:

```javascript
// ✅ USE JAVASCRIPT FOR:

// 1. Small scripts
// Simple automation or one-off scripts

// 2. Rapid prototyping
// Quick proof of concepts

// 3. Learning projects
// When focusing on learning JavaScript concepts

// 4. Simple websites
// Basic HTML/CSS/JS without complex logic
```

### Real-World Scenario:

```typescript
// E-commerce Application

// JavaScript approach - Error-prone
function processOrder(order) {
    // What properties does order have?
    // What types are they?
    const total = order.items.reduce((sum, item) => {
        return sum + (item.price * item.quantity);
    }, 0);
    
    if (order.coupon) {
        // What if coupon is wrong type?
        total = total - order.coupon.discount;
    }
    
    return total;
}

// TypeScript approach - Safe and clear
interface OrderItem {
    id: string;
    name: string;
    price: number;
    quantity: number;
}

interface Coupon {
    code: string;
    discount: number;
    type: 'percentage' | 'fixed';
}

interface Order {
    id: string;
    items: OrderItem[];
    coupon?: Coupon;
    shippingAddress: Address;
}

function processOrder(order: Order): number {
    // TypeScript ensures:
    // - order has all required properties
    // - items is an array
    // - each item has price and quantity as numbers
    // - coupon is optional but has correct structure
    
    let total = order.items.reduce((sum, item) => {
        return sum + (item.price * item.quantity);
    }, 0);
    
    if (order.coupon) {
        if (order.coupon.type === 'percentage') {
            total = total * (1 - order.coupon.discount / 100);
        } else {
            total = total - order.coupon.discount;
        }
    }
    
    return total;
}
```

### Benefits in Practice:

```typescript
// 1. Autocomplete everywhere
const user: User = {
    name: "John",
    email: "john@example.com"
};

user. // IDE shows: name, email (and nothing else)

// 2. Refactoring made easy
// Rename 'email' to 'emailAddress'
// TypeScript shows ALL places that need updating

// 3. Function contracts
function sendEmail(
    to: string,
    subject: string,
    body: string,
    attachments?: File[]
): Promise<boolean> {
    // Clear API contract
    // No guessing what parameters are needed
}

// 4. Prevention of common bugs
let items: string[] = [];
// items.push(123); // ❌ Error: number not assignable to string
items.push("valid"); // ✅ Correct
```

### Cross Questions:

**Q: Does TypeScript slow down development?**
A: "Initially, yes - there's a learning curve and you write more code. But it speeds up development long-term by catching bugs early and making refactoring safer."

**Q: Is TypeScript worth it for a 5-person team?**
A: "Yes, even small teams benefit from type safety and better tooling. The documentation aspect alone helps new team members understand the codebase faster."

---

## 4. How Does TypeScript Help at Compile Time?

### Simple Explanation:
TypeScript checks your code for errors BEFORE it runs, like a spell-checker for code.

### Interview Answer:
"TypeScript performs static type checking during compilation, catching type errors, typos, and logic issues before the code runs. This prevents runtime errors and provides immediate feedback during development."

### Examples of Compile-Time Checks:

```typescript
// 1. TYPE MISMATCH
function greet(name: string): string {
    return `Hello, ${name}`;
}

// greet(123); // ❌ Compile error: Argument of type 'number' not assignable
greet("John"); // ✅ Compiles successfully

// 2. MISSING PROPERTIES
interface Config {
    apiUrl: string;
    timeout: number;
    retries: number;
}

const config: Config = {
    apiUrl: "https://api.example.com",
    timeout: 5000
    // ❌ Compile error: Property 'retries' is missing
};

// 3. TYPOS IN PROPERTY NAMES
const user = {
    firstName: "John",
    lastName: "Doe"
};

// console.log(user.fristName); // ❌ Compile error: Property 'fristName' doesn't exist
console.log(user.firstName); // ✅ Correct

// 4. WRONG RETURN TYPE
function calculateAge(birthYear: number): number {
    // return "age"; // ❌ Compile error: Type 'string' not assignable to 'number'
    return 2024 - birthYear; // ✅ Correct
}

// 5. NULL/UNDEFINED CHECKS (with strictNullChecks)
function getLength(str: string): number {
    // return str.length; // ❌ If str could be null/undefined
    return str?.length ?? 0; // ✅ Safe
}

// let value: string;
// getLength(value); // ❌ Compile error: Variable 'value' used before assigned
```

### Real-World Example:

```typescript
// API Response Handler

interface ApiResponse<T> {
    success: boolean;
    data: T;
    error?: string;
}

interface User {
    id: number;
    email: string;
    name: string;
}

// TypeScript catches errors at compile time
function handleUserResponse(response: ApiResponse<User>): void {
    if (response.success) {
        console.log(response.data.email); // ✅ TypeScript knows 'email' exists
        // console.log(response.data.phone); // ❌ Compile error: Property 'phone' doesn't exist
        
        // ❌ Compile error: Cannot assign to 'id' because it's type 'number'
        // response.data.id = "123";
        
        response.data.id = 456; // ✅ Correct type
    } else {
        console.log(response.error); // ✅ TypeScript knows 'error' might be undefined
    }
}

// JavaScript equivalent - Errors only caught at runtime
function handleUserResponse(response) {
    if (response.success) {
        console.log(response.data.email); // Works
        console.log(response.data.phone); // undefined - No error!
        response.data.id = "123"; // Bug! - No error until runtime
    }
}
```

### Compile-Time vs Runtime:

```typescript
// COMPILE TIME (TypeScript catches):
function add(a: number, b: number): number {
    // return a + b + c; // ❌ Compile error: Cannot find name 'c'
    return a + b;
}

// add("5", "10"); // ❌ Compile error: Wrong types

// RUNTIME (JavaScript discovers):
function add(a, b) {
    return a + b + c; // ❌ ReferenceError: c is not defined (RUNTIME)
}

add("5", "10"); // Returns "510" - Not what you expected! (RUNTIME BUG)
```

### Advanced Compile-Time Features:

```typescript
// 1. UNION TYPE NARROWING
function processValue(value: string | number) {
    // ❌ Compile error: Property 'toUpperCase' doesn't exist on type 'number'
    // console.log(value.toUpperCase());
    
    // ✅ TypeScript narrows the type
    if (typeof value === "string") {
        console.log(value.toUpperCase()); // Now TypeScript knows it's string
    } else {
        console.log(value.toFixed(2)); // TypeScript knows it's number
    }
}

// 2. NON-NULL ASSERTION
function getFirstElement<T>(arr: T[]): T {
    // ❌ Compile error: Type 'T | undefined' not assignable to 'T'
    // return arr[0];
    
    // Options:
    if (arr.length === 0) {
        throw new Error("Array is empty");
    }
    return arr[0]; // ✅ TypeScript knows array is not empty
}

// 3. EXHAUSTIVE CHECKS
type Status = 'pending' | 'approved' | 'rejected';

function handleStatus(status: Status) {
    switch (status) {
        case 'pending':
            return 'Waiting';
        case 'approved':
            return 'Approved';
        case 'rejected':
            return 'Rejected';
        // If you add a new status, TypeScript will error here
    }
}

// 4. GENERICS TYPE SAFETY
function createArray<T>(item: T): T[] {
    return [item];
}

const numbers = createArray(5); // TypeScript infers: number[]
// numbers.push("string"); // ❌ Compile error

const strings = createArray("hello"); // TypeScript infers: string[]
strings.push("world"); // ✅ Correct
```

### Cross Questions:

**Q: What happens after TypeScript compiles?**
A: "TypeScript is transpiled to JavaScript, and all type information is removed. The generated JavaScript runs in browsers/Node.js."

**Q: Can runtime errors still occur in TypeScript?**
A: "Yes! TypeScript only catches type-related errors at compile time. Logic errors, network failures, and other runtime issues can still occur."

**Q: What is 'any' type and should you use it?**
A: "The 'any' type disables type checking for that variable. Avoid it - it defeats the purpose of TypeScript. Use 'unknown' if you truly don't know the type."

---

## 5. How Does TypeScript Help in Large-Scale Application Maintenance?

### Interview Answer:
"TypeScript significantly improves maintainability in large applications through type safety, better refactoring tools, self-documenting code, and catching integration issues early. It makes the codebase more predictable and easier for teams to work on together."

### Key Benefits for Maintenance:

```typescript
// 1. SAFE REFACTORING

// Before: Change User interface
interface User {
    name: string;
    email: string;
}

// After: Rename 'email' to 'emailAddress'
interface User {
    name: string;
    emailAddress: string; // Changed property name
}

// TypeScript immediately shows ALL 50 files that need updating
// No need to manually search for "email" everywhere

function sendNotification(user: User) {
    // ❌ Compile error: Property 'email' doesn't exist
    // sendEmail(user.email);
    
    sendEmail(user.emailAddress); // ✅ Fix guided by TypeScript
}

// 2. CONFIDENT CODE CHANGES

// Original function
function calculateDiscount(price: number, discount: number): number {
    return price - (price * discount / 100);
}

// 6 months later, team member wants to add currency
function calculateDiscount(
    price: number,
    discount: number,
    currency: string // Added parameter
): { amount: number; currency: string } { // Changed return type
    return {
        amount: price - (price * discount / 100),
        currency
    };
}

// TypeScript shows EVERY place calling this function needs updating
// No guessing, no runtime surprises

// 3. TEAM COLLABORATION

// Developer A creates a service
class UserService {
    async getUser(id: number): Promise<User> {
        // Implementation
    }
    
    async updateUser(id: number, data: Partial<User>): Promise<void> {
        // Implementation
    }
}

// Developer B uses it (6 months later)
const service = new UserService();
// TypeScript shows exact methods and their signatures
// No need to read documentation or source code
service. // Autocomplete shows: getUser, updateUser

// ❌ Compile error if used incorrectly
// service.getUser("123"); // Wrong type
// service.deleteUser(1); // Method doesn't exist

// 4. DOCUMENTATION THROUGH TYPES

// Self-documenting without comments
interface PaymentRequest {
    orderId: string;
    amount: number;
    currency: 'USD' | 'EUR' | 'GBP'; // Only these values allowed
    paymentMethod: 'credit_card' | 'paypal' | 'bank_transfer';
    customerEmail: string;
    billingAddress: Address;
    items: OrderItem[];
}

// Any developer can understand the structure immediately
function processPayment(request: PaymentRequest): Promise<PaymentResult> {
    // Clear what's needed and what's returned
}
```

### Real-World Maintenance Scenarios:

```typescript
// SCENARIO 1: API Changes

// V1: API returns simple user object
interface UserV1 {
    id: number;
    name: string;
}

function displayUser(user: UserV1) {
    console.log(user.name);
}

// V2: API changed - now returns detailed object
interface UserV2 {
    id: number;
    profile: {
        firstName: string;
        lastName: string;
    };
}

// TypeScript immediately shows errors in ALL affected components
function displayUser(user: UserV2) {
    // ❌ Compile error: Property 'name' doesn't exist
    // console.log(user.name);
    
    // ✅ Fix it
    console.log(`${user.profile.firstName} ${user.profile.lastName}`);
}

// SCENARIO 2: Adding New Features

// Original e-commerce system
interface Product {
    id: string;
    name: string;
    price: number;
}

// 1 year later: Add inventory tracking
interface Product {
    id: string;
    name: string;
    price: number;
    inventory: {
        inStock: number;
        reserved: number;
        warehouse: string;
    }; // New required property
}

// TypeScript shows all places creating Products need updating
const product: Product = {
    id: "123",
    name: "Laptop",
    price: 999
    // ❌ Compile error: Property 'inventory' is missing
};

// SCENARIO 3: Preventing Regressions

// Original working code
function calculateShipping(weight: number, distance: number): number {
    return weight * distance * 0.5;
}

// Someone accidentally changes it
function calculateShipping(weight: string, distance: number): number {
    // ❌ Compile error: Can't multiply string
    return weight * distance * 0.5;
}

// TypeScript prevents the bug before deployment

// SCENARIO 4: Understanding Legacy Code

// You join a project, see this function
function processOrder(
    orderId: string,
    items: OrderItem[],
    customer: Customer,
    payment: PaymentInfo,
    shipping: ShippingAddress
): Promise<OrderResult> {
    // Even without reading the implementation,
    // you know exactly what this function needs and returns
}

// vs JavaScript (nightmare)
function processOrder(orderId, items, customer, payment, shipping) {
    // What are the types? What properties do they have?
    // Need to read entire implementation to understand
}
```

### Maintenance Benefits Summary:

```typescript
// ✅ BENEFITS FOR LARGE TEAMS:

// 1. Onboarding new developers
// - Types serve as documentation
// - IDE shows what's available
// - Less time reading code

// 2. Reducing bugs
// - Catch integration issues at compile time
// - Prevent typos and wrong data types
// - Enforce contracts between modules

// 3. Faster debugging
// - Errors show exact location and type mismatch
// - Stack traces are more meaningful
// - Less time in production debugging

// 4. Confident refactoring
// - Know exactly what breaks when you change something
// - Automated refactoring tools work better
// - Less fear of changing code

// 5. Better collaboration
// - Clear APIs between team members
// - Less need for meetings about data structures
// - Code reviews focus on logic, not "what type is this?"

// 6. Easier testing
// - Mock data must match types
// - Test cases are clearer
// - Less setup code needed
```

### Cross Questions:

**Q: How does TypeScript help with code reviews?**
A: "Reviewers can focus on business logic instead of checking types. TypeScript enforces consistency, and type changes are clearly visible in diffs."

**Q: What about migration cost?**
A: "You can adopt TypeScript gradually. Start with .ts files for new code, keeping .js files as-is. Slowly convert critical paths when refactoring."

**Q: Does TypeScript guarantee no bugs?**
A: "No, it only catches type-related errors. Logic bugs, race conditions, and runtime errors can still occur. But it eliminates a whole category of bugs."

---

## 6. Difference Between Type and Interface in TypeScript

### Simple Explanation:
Both `type` and `interface` define the shape of objects, but interfaces are mainly for objects and can be extended, while types are more flexible.

### Interview Answer:
"Both `type` and `interface` are used to define custom types in TypeScript. Interfaces are primarily for defining object shapes and can be extended and implemented. Types are more versatile, supporting unions, intersections, and primitives, but can't be reopened to add new properties."

### Basic Comparison:

```typescript
// INTERFACE
interface User {
    name: string;
    age: number;
}

// TYPE
type User = {
    name: string;
    age: number;
};

// Both work the same for simple objects
const user1: User = { name: "John", age: 30 };
```

### Key Differences:

```typescript
// 1. EXTENDING

// Interface - uses extends
interface Animal {
    name: string;
}

interface Dog extends Animal {
    bark(): void;
}

const dog: Dog = {
    name: "Buddy",
    bark() {
        console.log("Woof!");
    }
};

// Type - uses intersection (&)
type Animal = {
    name: string;
};

type Dog = Animal & {
    bark(): void;
};

// 2. DECLARATION MERGING (Only Interfaces)

// Interface - can be declared multiple times
interface Window {
    title: string;
}

interface Window {
    version: number;
}

// Merged into one
const window: Window = {
    title: "My App",
    version: 1.0
};

// Type - cannot be declared multiple times
type Person = {
    name: string;
};

// ❌ Error: Duplicate identifier 'Person'
// type Person = {
//     age: number;
// };

// 3. UNIONS (Only Types)

// Type - can use unions
type Status = 'pending' | 'approved' | 'rejected';
type ID = string | number;

// Interface - cannot represent unions directly
// interface Status = 'pending' | 'approved' | 'rejected'; // ❌ Error

// But can use type for the union
type Status = 'pending' | 'approved' | 'rejected';
interface User {
    status: Status; // Use type here
}

// 4. PRIMITIVES AND TUPLES

// Type - can alias primitives
type Age = number;
type Name = string;

// Interface - cannot alias primitives
// interface Age = number; // ❌ Error

// Type - can define tuples
type Coordinates = [number, number];
const point: Coordinates = [10, 20];

// Interface - awkward for tuples
interface Coordinates {
    0: number;
    1: number;
    length: 2;
}

// 5. MAPPED TYPES (Only Types)

// Type - can use mapped types
type Readonly<T> = {
    readonly [K in keyof T]: T[K];
};

type User = {
    name: string;
    age: number;
};

type ReadonlyUser = Readonly<User>;
// Result: { readonly name: string; readonly age: number; }

// Interface - cannot do this directly

// 6. IMPLEMENTS (Both work)

// Both can be implemented by classes
interface Printable {
    print(): void;
}

class Document implements Printable {
    print() {
        console.log("Printing...");
    }
}

type Printable2 = {
    print(): void;
};

class Report implements Printable2 {
    print() {
        console.log("Printing report...");
    }
}
```

### When to Use What:

```typescript
// ✅ USE INTERFACE FOR:

// 1. Object shapes (especially public APIs)
interface ApiResponse {
    status: number;
    data: any;
}

// 2. When you might need to extend later
interface BaseConfig {
    timeout: number;
}

interface AdvancedConfig extends BaseConfig {
    retries: number;
}

// 3. When building libraries (allows consumers to extend)
interface CustomLogger {
    log(message: string): void;
}

// Users can extend it:
// interface CustomLogger {
//     logError(error: Error): void;
// }

// 4. Class contracts
interface Drawable {
    draw(): void;
    move(x: number, y: number): void;
}

class Circle implements Drawable {
    draw() { /* ... */ }
    move(x: number, y: number) { /* ... */ }
}

// ✅ USE TYPE FOR:

// 1. Unions and intersections
type Result = Success | Failure;
type Employee = Person & Worker;

// 2. Primitive aliases
type ID = string | number;
type Callback = () => void;

// 3. Tuples
type Point = [number, number];
type RGB = [number, number, number];

// 4. Mapped and conditional types
type Optional<T> = {
    [K in keyof T]?: T[K];
};

type NonNullable<T> = T extends null | undefined ? never : T;

// 5. Function types
type MathOperation = (a: number, b: number) => number;

const add: MathOperation = (a, b) => a + b;
```

### Real-World Example:

```typescript
// Library API Design

// Public API - use interface (extensible)
export interface UserServiceConfig {
    apiUrl: string;
    timeout: number;
}

export interface UserService {
    getUser(id: string): Promise<User>;
    updateUser(id: string, data: Partial<User>): Promise<User>;
}

// Internal types - use type (flexible)
type RequestMethod = 'GET' | 'POST' | 'PUT' | 'DELETE';
type ApiResponse<T> = {
    data: T;
    status: number;
    headers: Record<string, string>;
};

type HttpClient = {
    request<T>(
        method: RequestMethod,
        url: string,
        body?: unknown
    ): Promise<ApiResponse<T>>;
};

// Complex types
type DeepPartial<T> = {
    [K in keyof T]?: T[K] extends object ? DeepPartial<T[K]> : T[K];
};
```

### Cross Questions:

**Q: Can you convert a type to an interface and vice versa?**
A: "For simple object shapes, yes. But types supporting unions, tuples, or mapped types cannot be directly converted to interfaces."

**Q: Which is faster, type or interface?**
A: "Performance is virtually identical. The choice should be based on use case, not performance."

**Q: When do you use both in the same project?**
A: "Yes, commonly! Use interfaces for object shapes and public APIs, types for unions, utilities, and complex type manipulations."

---

## 7. What are Namespaces and Modules in TypeScript?

### Simple Explanation:
- **Modules**: Files that export and import code (modern approach, recommended)
- **Namespaces**: Way to group code within a file (older approach)

### Interview Answer:
"Modules use ES6 import/export syntax, with each file being a separate module. Namespaces are TypeScript-specific constructs for organizing code within a global scope. Modern TypeScript projects prefer modules over namespaces."

### Modules (Recommended Approach):

```typescript
// ===== user.ts =====
export interface User {
    id: number;
    name: string;
    email: string;
}

export class UserService {
    getUser(id: number): User {
        // Implementation
        return {
            id,
            name: "John",
            email: "john@example.com"
        };
    }
}

export function validateEmail(email: string): boolean {
    return email.includes('@');
}

// Default export
export default class UserRepository {
    // Implementation
}

// ===== app.ts =====
// Named imports
import { User, UserService, validateEmail } from './user';

// Default import
import UserRepository from './user';

// Import everything
import * as UserModule from './user';

const service = new UserService();
const user: User = service.getUser(1);

if (validateEmail(user.email)) {
    console.log('Valid email');
}
```

### Namespaces (Legacy Approach):

```typescript
// ===== Validation.ts =====
namespace Validation {
    export interface StringValidator {
        isValid(s: string): boolean;
    }

    export class EmailValidator implements StringValidator {
        isValid(email: string): boolean {
            return email.includes('@');
        }
    }

    export class URLValidator implements StringValidator {
        isValid(url: string): boolean {
            return url.startsWith('http');
        }
    }

    // Not exported - private to namespace
    function helperFunction() {
        return "helper";
    }
}

// Usage
const emailValidator = new Validation.EmailValidator();
console.log(emailValidator.isValid("test@example.com"));

// Nested namespaces
namespace App {
    export namespace Models {
        export class User {
            constructor(public name: string) {}
        }
    }

    export namespace Services {
        export class UserService {
            getUser() {
                return new Models.User("John");
            }
        }
    }
}

// Usage
const service = new App.Services.UserService();
const user = service.getUser();
```

### Comparison:

```typescript
// ===== MODULES (Modern) =====

// math.ts
export function add(a: number, b: number): number {
    return a + b;
}

export function subtract(a: number, b: number): number {
    return a - b;
}

// main.ts
import { add, subtract } from './math';

console.log(add(5, 3));

// ===== NAMESPACES (Legacy) =====

// All in one file or multiple files with reference
namespace Math {
    export function add(a: number, b: number): number {
        return a + b;
    }

    export function subtract(a: number, b: number): number {
        return a - b;
    }
}

// Usage (same file or loaded via <script> tags)
console.log(Math.add(5, 3));
```

### Real-World Example - Modules:

```typescript
// ===== models/user.model.ts =====
export interface User {
    id: number;
    name: string;
    email: string;
}

// ===== services/user.service.ts =====
import { User } from '../models/user.model';

export class UserService {
    private users: User[] = [];

    addUser(user: User): void {
        this.users.push(user);
    }

    getUser(id: number): User | undefined {
        return this.users.find(u => u.id === id);
    }
}

// ===== utils/validation.ts =====
export function isValidEmail(email: string): boolean {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

export function isValidPhone(phone: string): boolean {
    return /^\d{10}$/.test(phone);
}

// ===== app.ts =====
import { User } from './models/user.model';
import { UserService } from './services/user.service';
import { isValidEmail } from './utils/validation';

const service = new UserService();

const newUser: User = {
    id: 1,
    name: "John Doe",
    email: "john@example.com"
};

if (isValidEmail(newUser.email)) {
    service.addUser(newUser);
}
```

### Module Features:

```typescript
// 1. Re-exporting
// ===== models/index.ts =====
export { User } from './user.model';
export { Product } from './product.model';
export { Order } from './order.model';

// ===== app.ts =====
import { User, Product, Order } from './models';

// 2. Renaming imports
import { User as UserModel } from './models/user';
import { User as UserEntity } from './entities/user';

// 3. Side-effect imports
import './polyfills'; // Runs code but doesn't import anything

// 4. Dynamic imports
async function loadModule() {
    const { UserService } = await import('./services/user.service');
    const service = new UserService();
}
```

### When to Use Each:

```typescript
// ✅ USE MODULES:
// - All new TypeScript projects
// - When working with modern build tools (Webpack, Vite)
// - For better tree-shaking
// - Node.js applications
// - React/Angular/Vue applications

// ⚠️ USE NAMESPACES:
// - Legacy projects
// - When targeting older JavaScript versions
// - Simple HTML pages with <script> tags
// - Internal organization within a single file
```

### Cross Questions:

**Q: Can you use both modules and namespaces together?**
A: "Yes, but it's not recommended. It creates confusion. Stick to modules in modern projects."

**Q: How do modules help with code splitting?**
A: "Bundlers can analyze module imports and create separate bundles, loading only needed code. Namespaces don't support this."

**Q: What's the difference between export and export default?**
A:
- Named export: `export class User {}` → `import { User } from './file'`
- Default export: `export default class User {}` → `import User from './file'` (any name)

---

## 8. Explain Generics in TypeScript

### Simple Explanation:
Generics let you write reusable code that works with different types, like a placeholder for types.

### Interview Answer:
"Generics in TypeScript allow you to create reusable components that work with multiple types while maintaining type safety. Instead of using 'any', generics preserve type information, making code both flexible and type-safe."

### Basic Generic Example:

```typescript
// Without generics - not reusable
function getFirstNumber(arr: number[]): number {
    return arr[0];
}

function getFirstString(arr: string[]): string {
    return arr[0];
}

// With generics - reusable and type-safe
function getFirst<T>(arr: T[]): T {
    return arr[0];
}

// TypeScript infers the type
const firstNum = getFirst([1, 2, 3]); // Type: number
const firstStr = getFirst(["a", "b"]); // Type: string
const firstBool = getFirst([true, false]); // Type: boolean

// Explicit type
const first = getFirst<number>([1, 2, 3]);
```

### Generic Functions:

```typescript
// Identity function
function identity<T>(arg: T): T {
    return arg;
}

identity<string>("hello"); // Returns string
identity<number>(42);      // Returns number

// Array operations
function reverseArray<T>(arr: T[]): T[] {
    return arr.reverse();
}

const numbers = reverseArray([1, 2, 3]); // number[]
const strings = reverseArray(["a", "b"]); // string[]

// Multiple type parameters
function pair<T, U>(first: T, second: U): [T, U] {
    return [first, second];
}

const p1 = pair("age", 25);      // [string, number]
const p2 = pair(true, "yes");    // [boolean, string]
```

### Generic Interfaces:

```typescript
// Generic interface
interface Box<T> {
    value: T;
    getValue(): T;
    setValue(value: T): void;
}

// Usage with different types
const numberBox: Box<number> = {
    value: 42,
    getValue() {
        return this.value;
    },
    setValue(value: number) {
        this.value = value;
    }
};

const stringBox: Box<string> = {
    value: "hello",
    getValue() {
        return this.value;
    },
    setValue(value: string) {
        this.value = value;
    }
};

// API Response generic
interface ApiResponse<T> {
    data: T;
    status: number;
    message: string;
}

interface User {
    id: number;
    name: string;
}

const userResponse: ApiResponse<User> = {
    data: { id: 1, name: "John" },
    status: 200,
    message: "Success"
};

const usersResponse: ApiResponse<User[]> = {
    data: [
        { id: 1, name: "John" },
        { id: 2, name: "Jane" }
    ],
    status: 200,
    message: "Success"
};
```

### Generic Classes:

```typescript
// Generic class
class DataStore<T> {
    private data: T[] = [];

    add(item: T): void {
        this.data.push(item);
    }

    remove(item: T): void {
        const index = this.data.indexOf(item);
        if (index > -1) {
            this.data.splice(index, 1);
        }
    }

    getAll(): T[] {
        return [...this.data];
    }

    find(predicate: (item: T) => boolean): T | undefined {
        return this.data.find(predicate);
    }
}

// Usage
const numberStore = new DataStore<number>();
numberStore.add(1);
numberStore.add(2);
console.log(numberStore.getAll()); // [1, 2]

const stringStore = new DataStore<string>();
stringStore.add("hello");
stringStore.add("world");
console.log(stringStore.getAll()); // ["hello", "world"]

interface Product {
    id: number;
    name: string;
    price: number;
}

const productStore = new DataStore<Product>();
productStore
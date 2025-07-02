# Library Management System (Java)

This is a Java-based Library Management System that allows librarians to manage books, patrons, and lending processes using Object-Oriented Programming (OOP), SOLID principles, and key design patterns (Factory and Strategy).

---

## Features

### Book Management
- Add, remove, update books
- Search books by title, author, or ISBN

### Patron Management
- Register and update patrons
- Track borrowing history

### Lending Process
- Checkout and return books
- Check availability

---

## Technologies & Principles

- Java OOP (Abstraction, Encapsulation, Inheritance, Polymorphism)
- SOLID Design Principles
- Design Patterns:
  - Strategy Pattern (for search strategy)
  - Factory Pattern (to create search strategy objects)
- Java Collections (Map, List, Set)
- Java Logging API

---

## Project Structure
```
src/
├── Book.java
├── Patron.java
├── InventoryManager.java
├── PatronManager.java
├── SearchStrategy.java
├── SearchByTitle.java
├── SearchByAuthor.java
├── SearchByISBN.java
├── SearchFactory.java
```

---

## How to Run
1. Clone the repo
2. Compile and run with your preferred IDE or via terminal:

---

## Class Diagram

```plaintext
+----------------+       +-----------------+       +------------------+
|     Book       |       |    Inventory    |       |    SearchStrategy|
+----------------+       +-----------------+       +------------------+
| - isbn         |<>-----| - bookMap       |<>---->|<<interface>>     |
| - title        |       | - borrowedBooks |       +------------------+
| - author       |       +-----------------+              ^     ^     ^
| - pubYear      |                                      /       \     \
+----------------+                         +---------------+ +-------------+ +--------------+
                                            | SearchByTitle | | SearchByISBN | | SearchByAuthor|
                                            +---------------+ +-------------+ +--------------+

+----------------+        +----------------+
|    Patron      |<>------| PatronManager  |
+----------------+        +----------------+
| - id           |        | - patrons      |
| - name         |        +----------------+
| - history      |
+----------------+
```

---
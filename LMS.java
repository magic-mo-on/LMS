// --- Core Entities ---

public class Book {
    private final String isbn;
    private String title;
    private String author;
    private int publicationYear;

    public Book(String isbn, String title, String author, int publicationYear) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    // Getters and setters
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getPublicationYear() { return publicationYear; }
    public void setPublicationYear(int year) { this.publicationYear = year; }

    @Override
    public String toString() {
        return title + " by " + author + " (" + isbn + ", " + publicationYear + ")";
    }
}

public class Patron {
    private final String id;
    private String name;
    private final List<String> borrowingHistory = new ArrayList<>();

    public Patron(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<String> getBorrowingHistory() { return borrowingHistory; }
    public void addToHistory(String isbn) { borrowingHistory.add(isbn); }
}

// --- Strategy Pattern for Search ---
interface SearchStrategy {
    List<Book> search(List<Book> books, String query);
}

class SearchByTitle implements SearchStrategy {
    public List<Book> search(List<Book> books, String query) {
        return books.stream().filter(b -> b.getTitle().equalsIgnoreCase(query)).toList();
    }
}

class SearchByAuthor implements SearchStrategy {
    public List<Book> search(List<Book> books, String query) {
        return books.stream().filter(b -> b.getAuthor().equalsIgnoreCase(query)).toList();
    }
}

class SearchByISBN implements SearchStrategy {
    public List<Book> search(List<Book> books, String query) {
        return books.stream().filter(b -> b.getIsbn().equals(query)).toList();
    }
}

// --- Factory Pattern for Search Strategy ---
class SearchFactory {
    public static SearchStrategy getSearchStrategy(String type) {
        return switch (type.toLowerCase()) {
            case "title" -> new SearchByTitle();
            case "author" -> new SearchByAuthor();
            case "isbn" -> new SearchByISBN();
            default -> throw new IllegalArgumentException("Invalid search type");
        };
    }
}

// --- Library Inventory and Lending ---
class InventoryManager {
    private final Map<String, Book> bookMap = new HashMap<>();
    private final Set<String> borrowedBooks = new HashSet<>();
    private final Logger logger = Logger.getLogger(InventoryManager.class.getName());

    public void addBook(Book book) {
        bookMap.put(book.getIsbn(), book);
        logger.info("Added book: " + book);
    }

    public void removeBook(String isbn) {
        Book removed = bookMap.remove(isbn);
        if (removed != null) {
            logger.info("Removed book: " + removed);
        }
    }

    public void updateBook(String isbn, String title, String author, int year) {
        Book book = bookMap.get(isbn);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setPublicationYear(year);
            logger.info("Updated book: " + book);
        }
    }

    public List<Book> searchBooks(String type, String query) {
        SearchStrategy strategy = SearchFactory.getSearchStrategy(type);
        return strategy.search(new ArrayList<>(bookMap.values()), query);
    }

    public boolean checkoutBook(String isbn) {
        if (bookMap.containsKey(isbn) && !borrowedBooks.contains(isbn)) {
            borrowedBooks.add(isbn);
            logger.info("Checked out book: " + bookMap.get(isbn));
            return true;
        }
        return false;
    }

    public boolean returnBook(String isbn) {
        if (borrowedBooks.remove(isbn)) {
            logger.info("Returned book: " + bookMap.get(isbn));
            return true;
        }
        return false;
    }

    public boolean isAvailable(String isbn) {
        return bookMap.containsKey(isbn) && !borrowedBooks.contains(isbn);
    }
}

class PatronManager {
    private final Map<String, Patron> patrons = new HashMap<>();

    public void addPatron(Patron patron) {
        patrons.put(patron.getId(), patron);
    }

    public void updatePatron(String id, String name) {
        if (patrons.containsKey(id)) {
            patrons.get(id).setName(name);
        }
    }

    public void recordBorrow(String patronId, String isbn) {
        Patron patron = patrons.get(patronId);
        if (patron != null) {
            patron.addToHistory(isbn);
        }
    }

    public List<String> getBorrowHistory(String patronId) {
        return patrons.containsKey(patronId) ? patrons.get(patronId).getBorrowingHistory() : List.of();
    }
}

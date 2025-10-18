import java.io.*;
import java.time.LocalDate;
import java.util.*;

/*Ang Library System usa ka program nga nagdumala sa pagpahulam ug pagbalik sa mga libro.
 Ang user kinahanglan mag-login una. Kung user siya, pwede siya makakita, manghulam, mobalik, 
 ug mangita ug libro. Kung admin ang mag login maka add, update,  delete ug libro, ug maka add ug user. */


public class LibrarySystem {
    private List<Book> books;
    private List<User> users;
    private List<Transaction> transactions;
    private User loggedInUser;

    public LibrarySystem() throws IOException {
        books = FileHandler.loadBooks("books.txt");
        users = FileHandler.loadUsers("users.txt");
        transactions = FileHandler.loadTransactions("transactions.txt");
    }

    public void login() {
        Scanner sc = new Scanner(System.in);
        int attempts = 3;

        while (attempts > 0) {
            System.out.println("\nWelcome to the Library Management System\n");
            System.out.println("\nPlease log in to continue.\n");
            System.out.print("Username: ");
            String name = sc.nextLine();
            System.out.print("Password: ");
            String pass = sc.nextLine();

            for (User u : users) {
                if (u.getName().equalsIgnoreCase(name) && u.getPassword().equals(pass)) {
                    loggedInUser = u;
                    System.out.println("\nLogin successful! Welcome, " + u.getName() + " (" + u.getRole() + ")");
                    displayMenu();
                    return;
                }
            }

            attempts--;
            System.out.println("Invalid credentials. Attempts left: " + attempts);
        }
        System.out.println("Too many failed attempts. Exiting...");
    }

    public void displayMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. View All Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. Search Book");

            if (loggedInUser.getRole().equalsIgnoreCase("admin")) {
                System.out.println("5. Catalogue Management");
                System.out.println("6. Transaction Management");
                System.out.println("7. Add New User");
                System.out.println("8. Exit");
            } else {
                System.out.println("5. Exit");
            }

            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            if (loggedInUser.getRole().equalsIgnoreCase("admin")) {
                switch (choice) {
                    case 1 -> viewAllBooks();
                    case 2 -> borrowBook(sc);
                    case 3 -> returnBook(sc);
                    case 4 -> searchBook(sc);
                    case 5 -> catalogueMenu(sc);
                    case 6 -> transactionMenu(sc);
                    case 7 -> addNewUser(sc);
                    case 8 -> exitSystem();
                    default -> System.out.println("Invalid choice!");
                }
            } else {
                switch (choice) {
                    case 1 -> viewAllBooks();
                    case 2 -> borrowBook(sc);
                    case 3 -> returnBook(sc);
                    case 4 -> searchBook(sc);
                    case 5 -> exitSystem();
                    default -> System.out.println("Invalid choice!");
                }
            }

        } while (
                (!loggedInUser.getRole().equalsIgnoreCase("admin") && choice != 5) ||
                        (loggedInUser.getRole().equalsIgnoreCase("admin") && choice != 8)
        );
    }


    private void viewAllBooks() {
        System.out.println("\nBook List:");
        System.out.println("-----------------------------------------------");
        for (Book b : books) b.displayBookDetails();
    }

    private void borrowBook(Scanner sc) {
        // Limit: user can borrow max 3 books
        long borrowedCount = transactions.stream()
                .filter(t -> t.getUserId().equals(loggedInUser.getId()) && t.getDateReturned() == null)
                .count();

        if (borrowedCount >= 3) {
            System.out.println("You already borrowed 3 books. Please return one first.");
            return;
        }

        System.out.print("Enter Book ID to borrow: ");
        String bookId = sc.nextLine();

        for (Book b : books) {
            if (b.getBookId().equals(bookId) && b.isAvailable()) {
                b.setAvailable(false);
                String tid = String.format("T%03d", transactions.size() + 1); // auto ID
                transactions.add(new Transaction(tid, loggedInUser.getId(), bookId, LocalDate.now().toString(), null));
                System.out.println("Book borrowed successfully!");
                return;
            }
        }
        System.out.println("Book unavailable or invalid ID.");
    }

    private void returnBook(Scanner sc) {
        System.out.print("Enter Book ID to return: ");
        String bookId = sc.nextLine();

        for (Transaction t : transactions) {
            if (t.getUserId().equals(loggedInUser.getId()) &&
                    t.getBookId().equals(bookId) &&
                    t.getDateReturned() == null) {

                t.setDateReturned(LocalDate.now().toString());
                for (Book b : books) {
                    if (b.getBookId().equals(bookId)) {
                        b.setAvailable(true);
                        break;
                    }
                }
                System.out.println("Book returned successfully!");
                return;
            }
        }
        System.out.println("No matching borrowed book found.");
    }

    private void searchBook(Scanner sc) {
        System.out.print("Enter keyword (title or author): ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword) || 
                b.getAuthor().toLowerCase().contains(keyword)) {
                b.displayBookDetails();
                found = true;
            }
        }

        if (!found) System.out.println("No books found matching that keyword.");
    }


    private void catalogueMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n--- Catalogue Management ---");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. Display Books");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addBook(sc);
                case 2 -> updateBook(sc);
                case 3 -> deleteBook(sc);
                case 4 -> viewAllBooks();
                case 5 -> System.out.println("Exiting Catalogue...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    private void addBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();

        books.add(new Book(id, title, author, true));
        System.out.println("Book added successfully!");
    }

    private void updateBook(Scanner sc) {
        System.out.print("Enter Book ID to update: ");
        String id = sc.nextLine();

        for (Book b : books) {
            if (b.getBookId().equals(id)) {
                System.out.print("New Title: ");
                b.setTitle(sc.nextLine());
                System.out.print("New Author: ");
                b.setAuthor(sc.nextLine());
                System.out.println("Book updated successfully!");
                return;
            }
        }
        System.out.println("Book ID not found.");
    }

    private void deleteBook(Scanner sc) {
        System.out.print("Enter Book ID to delete: ");
        String id = sc.nextLine();

        Iterator<Book> it = books.iterator();
        while (it.hasNext()) {
            if (it.next().getBookId().equals(id)) {
                it.remove();
                System.out.println("Book deleted successfully!");
                return;
            }
        }
        System.out.println("Book ID not found.");
    }

    private void transactionMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n--- Transaction Management ---");
            System.out.println("1. View All Transactions");
            System.out.println("2. View Transactions by User");
            System.out.println("3. View Transactions by Book");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewAllTransactions();
                case 2 -> viewTransactionsByUser(sc);
                case 3 -> viewTransactionsByBook(sc);
                case 4 -> System.out.println("Exiting Transaction Menu...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    private void viewAllTransactions() {
        System.out.println("\n--- All Transactions ---");
        for (Transaction t : transactions) t.displayTransaction();
    }

    private void viewTransactionsByUser(Scanner sc) {
        System.out.print("Enter User ID: ");
        String uid = sc.nextLine();
        for (Transaction t : transactions)
            if (t.getUserId().equals(uid)) t.displayTransaction();
    }

    private void viewTransactionsByBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        String bid = sc.nextLine();
        for (Transaction t : transactions)
            if (t.getBookId().equals(bid)) t.displayTransaction();
    }

    private void addNewUser(Scanner sc) {
        System.out.println("\n--- Add New User ---");
        System.out.print("Enter User ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        System.out.print("Enter Role (admin/user): ");
        String role = sc.nextLine();

        users.add(new User(id, name, password, role));
        System.out.println("User added successfully!");
    }


    private void exitSystem() {
        try {
            FileHandler.saveBooks("books.txt", books);
            FileHandler.saveUsers("users.txt", users);
            FileHandler.saveTransactions("transactions.txt", transactions);
            System.out.println("All data saved. Goodbye!");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            LibrarySystem system = new LibrarySystem();
            system.login();
        } catch (FileNotFoundException e) {
            System.out.println("Required file missing: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}

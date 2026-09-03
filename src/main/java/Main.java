import com.airtribe.librarymanagementsystem.entity.Book;
import com.airtribe.librarymanagementsystem.entity.Branch;
import com.airtribe.librarymanagementsystem.entity.Loan;
import com.airtribe.librarymanagementsystem.entity.Patron;
import com.airtribe.librarymanagementsystem.observer.PatronNotifier;
import com.airtribe.librarymanagementsystem.service.LibrarySystem;
import com.airtribe.librarymanagementsystem.strategy.AuthorSearchStrategy;
import com.airtribe.librarymanagementsystem.strategy.ISBNSearchStrategy;
import com.airtribe.librarymanagementsystem.strategy.TitleSearchStrategy;

import java.time.Year;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // ===================================================================
        // SCENARIO 1: Set up the library system with two branches
        // ===================================================================
        System.out.println("\n===== SCENARIO 1: Creating branches =====");
        LibrarySystem librarySystem = new LibrarySystem();
        String downtownId = librarySystem.addBranch("Downtown Branch");
        String uptownId = librarySystem.addBranch("Uptown Branch");

        Branch downtown = librarySystem.getBranch(downtownId);
        Branch uptown = librarySystem.getBranch(uptownId);

        System.out.println("Downtown branch id: " + downtownId);
        System.out.println("Uptown branch id: " + uptownId);

        // Register a notifier so reservation notifications print to console
        downtown.getReservationService().addObserver(new PatronNotifier());
        uptown.getReservationService().addObserver(new PatronNotifier());

        // ===================================================================
        // SCENARIO 2: Add books to Downtown branch
        // ===================================================================
        System.out.println("\n===== SCENARIO 2: Adding books to Downtown =====");
        Book cleanCode = new Book("Clean Code", "Robert Martin", "ISBN-001", Year.of(2008));
        Book effectiveJava = new Book("Effective Java", "Joshua Bloch", "ISBN-002", Year.of(2018));

        downtown.getBookService().addBook(cleanCode, 1);       // only 1 copy — will run out fast
        downtown.getBookService().addBook(effectiveJava, 3);

        // Adding more copies of an already-existing title
        downtown.getBookService().addBook(cleanCode, 2);       // now total/available = 3
        System.out.println("Added Clean Code (3 total copies) and Effective Java (3 copies) to Downtown");

        // ===================================================================
        // SCENARIO 3: Register patrons
        // ===================================================================
        System.out.println("\n===== SCENARIO 3: Registering patrons =====");
        Patron mike = new Patron("Mike", "9876543210");
        Patron alice = new Patron("Alice", "9123456789");
        Patron bob = new Patron("Bob", "9988776655");

        downtown.getPatronService().addPatron(mike);
        downtown.getPatronService().addPatron(alice);
        downtown.getPatronService().addPatron(bob);
        System.out.println("Mike id: " + mike.getId());
        System.out.println("Alice id: " + alice.getId());
        System.out.println("Bob id: " + bob.getId());

        // ===================================================================
        // SCENARIO 4: Search for books (Strategy pattern)
        // ===================================================================
        System.out.println("\n===== SCENARIO 4: Searching books =====");
        List<Book> byTitle = downtown.getBookService().searchBook("clean", new TitleSearchStrategy());
        System.out.println("Search by title 'clean': " + byTitle);

        List<Book> byAuthor = downtown.getBookService().searchBook("bloch", new AuthorSearchStrategy());
        System.out.println("Search by author 'bloch': " + byAuthor);

        List<Book> byIsbn = downtown.getBookService().searchBook("ISBN-001", new ISBNSearchStrategy());
        System.out.println("Search by ISBN 'ISBN-001': " + byIsbn);

        // ===================================================================
        // SCENARIO 5: Normal checkout and return
        // ===================================================================
        System.out.println("\n===== SCENARIO 5: Checkout and return =====");
        Loan mikeLoan = downtown.getLendingService().checkoutBook("ISBN-002", mike.getId());
        System.out.println("Mike checked out Effective Java. Due date: " + mikeLoan.getDueDate());

        downtown.getLendingService().returnBook("ISBN-002", mike.getId());
        System.out.println("Mike returned Effective Java.");

        // ===================================================================
        // SCENARIO 6: Exception handling — invalid ISBN, book not available
        // ===================================================================
        System.out.println("\n===== SCENARIO 6: Exception handling =====");

        // 6a. Checkout with an ISBN that doesn't exist in the catalog
        try {
            downtown.getLendingService().checkoutBook("ISBN-999", mike.getId());
        } catch (Exception e) {
            System.out.println("Expected error (invalid ISBN): " + e.getMessage());
        }

        // 6b. Returning a book that was never issued to this patron
        try {
            downtown.getLendingService().returnBook("ISBN-001", bob.getId());
        } catch (Exception e) {
            System.out.println("Expected error (no such loan): " + e.getMessage());
        }

        // 6c. Exhaust Clean Code's only... wait, it has 3 copies now. Let's exhaust them.
        downtown.getLendingService().checkoutBook("ISBN-001", mike.getId());
        downtown.getLendingService().checkoutBook("ISBN-001", alice.getId());
        downtown.getLendingService().checkoutBook("ISBN-001", bob.getId());
        System.out.println("All 3 copies of Clean Code are now checked out.");

        // ===================================================================
        // SCENARIO 7: Reservation + Observer pattern
        // ===================================================================
        System.out.println("\n===== SCENARIO 7: Reservation flow =====");
        Patron carol = new Patron("Carol", "9001122334");
        downtown.getPatronService().addPatron(carol);

        try {
            downtown.getLendingService().checkoutBook("ISBN-001", carol.getId());
        } catch (Exception e) {
            System.out.println("Carol can't check out Clean Code right now: " + e.getMessage());
            System.out.println("Would you like to reserve it? (yes/no)");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();
            if (input.equalsIgnoreCase("yes")) {
                downtown.getReservationService().reserve("ISBN-001", carol.getId());
                System.out.println("Carol added to the waiting list for Clean Code.");
            } else {
                System.out.println("Carol chose not to reserve.");
            }
        }

        // Mike returns his copy — Carol (if she reserved) should get notified
        System.out.println("\nMike returns his copy of Clean Code...");
        downtown.getLendingService().returnBook("ISBN-001", mike.getId());

        // ===================================================================
        // SCENARIO 8: Update and remove book metadata
        // ===================================================================
        System.out.println("\n===== SCENARIO 8: Update / remove book =====");
        Book correctedEdition = new Book("Effective Java (3rd Ed.)", "Joshua Bloch", "ISBN-002", Year.of(2018));
        downtown.getBookService().updateBook("ISBN-002", correctedEdition);
        System.out.println("Updated title: " +
                downtown.getBookService().searchBook("ISBN-002", new ISBNSearchStrategy()));

        try {
            downtown.getBookService().removeBook("ISBN-001"); // still has active loans (Alice, Bob)
        } catch (Exception e) {
            System.out.println("Expected error (can't remove, copies issued): " + e.getMessage());
        }

        // ===================================================================
        // SCENARIO 9: Patron management — update and remove
        // ===================================================================
        System.out.println("\n===== SCENARIO 9: Patron update / remove =====");
        downtown.getPatronService().updatePatron(carol.getId(), "Carol Smith", "9001100000");
        System.out.println("Updated Carol: " + downtown.getPatronService().getPatronBy(carol.getId()));

        try {
            downtown.getPatronService().removePatron(alice.getId()); // Alice still has an active loan
        } catch (Exception e) {
            System.out.println("Expected error (patron has active loan): " + e.getMessage());
        }

        // Clean up Alice's loan, then removal should succeed
        downtown.getLendingService().returnBook("ISBN-001", alice.getId());
        downtown.getPatronService().removePatron(alice.getId());
        System.out.println("Alice removed successfully after returning her book.");

        // ===================================================================
        // SCENARIO 10: Multi-branch — add stock to Uptown, then transfer
        // ===================================================================
        System.out.println("\n===== SCENARIO 10: Branch transfer =====");
        uptown.getBookService().addBook(
                new Book("Design Patterns", "Gamma et al.", "ISBN-003", Year.of(1994)), 4);
        System.out.println("Uptown now has 4 copies of Design Patterns.");

        librarySystem.transferBook("ISBN-003", uptownId, downtownId, 2);
        System.out.println("Transferred 2 copies of Design Patterns from Uptown to Downtown.");
        System.out.println("Downtown catalog now includes: " +
                downtown.getBookService().searchBook("Design Patterns", new TitleSearchStrategy()));

        // Transfer failure — trying to move more copies than available
        try {
            librarySystem.transferBook("ISBN-003", uptownId, downtownId, 100);
        } catch (Exception e) {
            System.out.println("Expected error (not enough copies to transfer): " + e.getMessage());
        }

        // ===================================================================
        // SCENARIO 11: Remove a branch that still has active loans
        // ===================================================================
        System.out.println("\n===== SCENARIO 11: Branch removal guard =====");
        downtown.getLendingService().checkoutBook("ISBN-003", bob.getId());
        try {
            librarySystem.removeBranch(downtownId);
        } catch (Exception e) {
            System.out.println("Expected error (branch has active loans): " + e.getMessage());
        }

        // Clean up and remove Uptown (no loans there) to show the success path
        librarySystem.removeBranch(uptownId);
        System.out.println("Uptown branch removed successfully (no active loans).");

        System.out.println("\n===== All scenarios complete =====");
    }
}
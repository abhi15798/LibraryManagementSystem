import com.airtribe.librarymanagementsystem.entity.Book;
import com.airtribe.librarymanagementsystem.entity.Loan;
import com.airtribe.librarymanagementsystem.entity.Patron;
import com.airtribe.librarymanagementsystem.inventory.BookInventory;
import com.airtribe.librarymanagementsystem.inventory.LoanInventory;
import com.airtribe.librarymanagementsystem.inventory.PatronInventory;
import com.airtribe.librarymanagementsystem.inventory.ReservationInventory;
import com.airtribe.librarymanagementsystem.observer.PatronNotifier;
import com.airtribe.librarymanagementsystem.service.BookService;
import com.airtribe.librarymanagementsystem.service.LendingService;
import com.airtribe.librarymanagementsystem.service.PatronService;
import com.airtribe.librarymanagementsystem.service.ReservationService;

import java.time.Year;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        BookInventory bookInventory = new BookInventory() ;
        BookService bookService = new BookService(bookInventory);
        PatronInventory patronInventory = new PatronInventory();
        LoanInventory loanInventory = new LoanInventory();
        ReservationInventory reservationInventory = new ReservationInventory();
        ReservationService reservationService =new ReservationService(reservationInventory);
        PatronService patronService =new PatronService(patronInventory,loanInventory);
        LendingService lendingService=new LendingService(loanInventory,bookInventory,reservationService);
        Book book1 = new Book("Test","Abhij","1", Year.of(2025));
        Book book2 = new Book("book2","John","2", Year.of(2020));
        Patron mike = new Patron("Mike","9876567890");
        Patron alice = new Patron("Alice","9873947403");
        System.out.println("mike id ="+ mike.getId());
        System.out.println("alice id ="+ alice.getId());
        bookService.addBook(book1,1);
        bookService.addBook(book2,1);
        patronService.addPatron(mike);
        patronService.addPatron(alice);
        lendingService.checkoutBook(book1.getISBN(),mike.getId());
        for(Loan loan: loanInventory.getActiveLoansOfPatron(mike.getId())){
            System.out.println(loan);
        }
        lendingService.returnBook(book1.getISBN(), mike.getId());

        try{
            lendingService.returnBook("t3", mike.getId());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        PatronNotifier patronNotifier = new PatronNotifier();
        reservationService.addObserver(patronNotifier);
        lendingService.checkoutBook(book1.getISBN(),mike.getId());
        try {
            lendingService.checkoutBook(book1.getISBN(),alice.getId());
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Do you want to reserve this book \nYes \nNo");
            Scanner scan = new Scanner(System.in);
            String input = scan.next();
            if(input.equalsIgnoreCase("yes")){
                reservationService.reserve(book1.getISBN(),alice.getId());
                System.out.println("Added to reservation list");
            }else{
                System.out.println("Not opted for reservation");
            }
        }
        lendingService.returnBook(book1.getISBN(), mike.getId());




    }
}
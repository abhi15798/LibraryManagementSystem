package com.airtribe.librarymanagementsystem.factory;

import com.airtribe.librarymanagementsystem.entity.Branch;
import com.airtribe.librarymanagementsystem.inventory.BookInventory;
import com.airtribe.librarymanagementsystem.inventory.LoanInventory;
import com.airtribe.librarymanagementsystem.inventory.PatronInventory;
import com.airtribe.librarymanagementsystem.inventory.ReservationInventory;
import com.airtribe.librarymanagementsystem.service.BookService;
import com.airtribe.librarymanagementsystem.service.LendingService;
import com.airtribe.librarymanagementsystem.service.PatronService;
import com.airtribe.librarymanagementsystem.service.ReservationService;

public class BranchFactory {
    public static Branch createBranch(String branchName) {
        BookInventory bookInventory = new BookInventory();
        BookService bookService = new BookService(bookInventory);
        LoanInventory loanInventory = new LoanInventory();
        ReservationInventory reservationInventory = new ReservationInventory();
        ReservationService reservationService = new ReservationService(reservationInventory);
        LendingService lendingService = new LendingService(loanInventory, bookInventory, reservationService);
        PatronInventory patronInventory = new PatronInventory();
        PatronService patronService = new PatronService(patronInventory, loanInventory);


        return new Branch(branchName, bookInventory, bookService, loanInventory, lendingService, patronInventory, patronService,reservationService);
    }
}

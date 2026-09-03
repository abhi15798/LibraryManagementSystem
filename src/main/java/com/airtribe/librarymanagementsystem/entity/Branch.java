package com.airtribe.librarymanagementsystem.entity;

import com.airtribe.librarymanagementsystem.inventory.BookInventory;
import com.airtribe.librarymanagementsystem.inventory.LoanInventory;
import com.airtribe.librarymanagementsystem.inventory.PatronInventory;
import com.airtribe.librarymanagementsystem.service.BookService;
import com.airtribe.librarymanagementsystem.service.LendingService;
import com.airtribe.librarymanagementsystem.service.PatronService;
import com.airtribe.librarymanagementsystem.service.ReservationService;

import java.util.UUID;

public class Branch {
    private final String branchId;
    private final String branchName;
    private final BookInventory bookInventory;
    private final BookService bookService;
    private final LoanInventory loanInventory;
    private final LendingService lendingService;
    private final PatronInventory patronInventory;
    private final PatronService patronService;
    private final ReservationService reservationService;


    public Branch(String branchName, BookInventory bookInventory, BookService bookService, LoanInventory loanInventory, LendingService lendingService, PatronInventory patronInventory, PatronService patronService,ReservationService reservationService) {
        this.branchId = UUID.randomUUID().toString();
        this.branchName = branchName;
        this.bookInventory = bookInventory;
        this.bookService = bookService;
        this.loanInventory = loanInventory;
        this.lendingService = lendingService;
        this.patronInventory = patronInventory;
        this.patronService = patronService;
        this.reservationService=reservationService;
    }

    public String getBranchId() {
        return branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public BookInventory getBookInventory() {
        return bookInventory;
    }

    public BookService getBookService() {
        return bookService;
    }
    public LoanInventory getLoanInventory() {
        return loanInventory;
    }
    public LendingService getLendingService() {
        return lendingService;
    }

    public PatronInventory getPatronInventory() {
        return patronInventory;
    }


    public PatronService getPatronService() {
        return patronService;
    }

    public ReservationService getReservationService() {
        return reservationService;
    }
}

package com.airtribe.librarymanagementsystem.service;

import com.airtribe.librarymanagementsystem.entity.Loan;
import com.airtribe.librarymanagementsystem.exception.LoanNotFoundException;
import com.airtribe.librarymanagementsystem.inventory.BookInventory;
import com.airtribe.librarymanagementsystem.inventory.LoanInventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LendingService {
    private static final Logger logger = LoggerFactory.getLogger(LendingService.class);
    private static final int LOAN_PERIOD_DAYS = 14;
    private final LoanInventory loanInventory;
    private final BookInventory bookInventory;
    private final ReservationService reservationService;
    public LendingService(LoanInventory loanInventory,BookInventory bookInventory,ReservationService reservationService){
        this.loanInventory=loanInventory;
        this.bookInventory=bookInventory;
        this.reservationService = reservationService;
    }

    public Loan checkoutBook(String ISBN,String patronId){
        bookInventory.checkoutCopy(ISBN);
        Loan loan = new Loan(ISBN,patronId,LocalDate.now(),LocalDate.now().plusDays(LOAN_PERIOD_DAYS));
        loanInventory.addLoan(loan);
        logger.info("Book {} checked out to patron {}", ISBN, patronId);
        return loan;
    }
    public boolean returnBook(String ISBN,String patronId) throws LoanNotFoundException{
        List<Loan>loans = loanInventory.getActiveLoansOfPatron(patronId);
        Optional<Loan> activeLoan = loans.stream()
                .filter(loan -> loan.getISBN().equals(ISBN))
                .findFirst();

        if (!activeLoan.isPresent()) {
            logger.warn("Book {} not issued by patron {}", ISBN, patronId);
            throw new LoanNotFoundException("This patron does not have this book issued");
        }

        activeLoan.get().markAsReturned(LocalDate.now());
        bookInventory.returnCopy(ISBN);
        logger.info("Book {} returned by patron {}", ISBN, patronId);
        reservationService.notifyReserve(ISBN);
        return true;
    }
}

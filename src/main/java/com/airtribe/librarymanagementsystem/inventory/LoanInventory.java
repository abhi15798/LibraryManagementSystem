package com.airtribe.librarymanagementsystem.inventory;

import com.airtribe.librarymanagementsystem.entity.Loan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class LoanInventory {
    private List<Loan> loans;

    public LoanInventory(){
        this.loans = new ArrayList<>();
    }
    public void addLoan(Loan loan){
        loans.add(loan);
    }
    public List<Loan> getActiveLoansOfPatron(String patronId){
        return loans.stream().filter(loan -> loan.getPatronId().equals(patronId)&& loan.getReturnDate() == null).collect(Collectors.toList());
    }
    public List<Loan> getAllLoansForPatron(String patronId){
        return loans.stream().filter(loan -> loan.getPatronId().equals(patronId)).collect(Collectors.toList());
    }
    public List<Loan>getActiveLoanForBook(String ISBN){
        return loans.stream().filter(loan -> loan.getISBN().equals(ISBN) && loan.getReturnDate() ==null ).collect(Collectors.toList());
    }
}

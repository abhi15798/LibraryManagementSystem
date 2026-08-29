package com.airtribe.librarymanagementsystem.service;

import com.airtribe.librarymanagementsystem.entity.Patron;
import com.airtribe.librarymanagementsystem.exception.PatronHasActiveLoansException;
import com.airtribe.librarymanagementsystem.exception.PatronNotFoundException;
import com.airtribe.librarymanagementsystem.inventory.LoanInventory;
import com.airtribe.librarymanagementsystem.inventory.PatronInventory;

public class PatronService {
    private final PatronInventory patronInventory;
    private final LoanInventory loanInventory;
    public PatronService(PatronInventory patronInventory,LoanInventory loanInventory){
        this.patronInventory=patronInventory;
        this.loanInventory = loanInventory;
    }
    public void addPatron(Patron patron){
        patronInventory.addPatron(patron);
    }
    public Patron getPatronBy(String patronId){
        return patronInventory.getPatronById(patronId);
    }
    public void updatePatron(String patronId, String newName, String newContact){
        patronInventory.updatePatron(patronId,newName,newContact);
    }
    public void removePatron(String patronId){
        if(!loanInventory.getActiveLoansOfPatron(patronId).isEmpty()){
            throw new PatronHasActiveLoansException("Patron has active loans , so it cannot be deleted");
        }
        patronInventory.removePatron(patronId);
    }
}

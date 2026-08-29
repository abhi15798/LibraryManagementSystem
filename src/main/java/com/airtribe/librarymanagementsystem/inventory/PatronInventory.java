package com.airtribe.librarymanagementsystem.inventory;

import com.airtribe.librarymanagementsystem.entity.Patron;
import com.airtribe.librarymanagementsystem.exception.PatronNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class PatronInventory {
    private final Map<String, Patron> patrons;

    public PatronInventory(){
        this.patrons=new HashMap<>();
    }

    public void addPatron(Patron patron){
        patrons.put(patron.getId(),patron);
    }
    public Patron getPatronById(String patronId) throws PatronNotFoundException{
        if(patrons.get(patronId)!=null){
          return patrons.get(patronId);
        }
        else throw new PatronNotFoundException("Patron not found with id : "+patronId);
    }
    public void updatePatron(String patronId, String newName, String newContact) {
        Patron patron = patrons.get(patronId);
        if (patron == null) {
            throw new PatronNotFoundException("Patron not found with id : "+patronId);
        }
        patron.setName(newName);
        patron.setContact(newContact);
    }
    public void removePatron(String patronId){
        if(patrons.get(patronId)!=null){
             patrons.remove(patronId);
        }
        else throw new PatronNotFoundException("Patron not found with id : "+patronId);
    }
}

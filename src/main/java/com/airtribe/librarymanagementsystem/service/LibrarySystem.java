package com.airtribe.librarymanagementsystem.service;

import com.airtribe.librarymanagementsystem.entity.Book;
import com.airtribe.librarymanagementsystem.entity.Branch;
import com.airtribe.librarymanagementsystem.exception.BranchHasActiveLoansException;
import com.airtribe.librarymanagementsystem.exception.BranchNotFoundException;
import com.airtribe.librarymanagementsystem.factory.BranchFactory;

import java.util.HashMap;
import java.util.Map;

public class LibrarySystem {
    private  final Map<String, Branch> branches;
    public LibrarySystem(){
        this.branches = new HashMap<>();
    }

    public String addBranch(String branchName) {
        Branch branch = BranchFactory.createBranch(branchName);
        branches.put(branch.getBranchId(), branch);
        return branch.getBranchId();
    }
    public void removeBranch(String branchId) {
        Branch branch =getBranch(branchId);
        if(branch.getLoanInventory().hasAnyActiveLoans()){
            throw new BranchHasActiveLoansException("Branch has active loans , So cannot remove");
        }
        branches.remove(branchId);
    }
    public Branch getBranch(String branchId){
        if(!branches.containsKey(branchId)){
            throw  new BranchNotFoundException("Branch is not available");
        }
        return branches.get(branchId);
    }

    public void transferBook(String ISBN, String fromBranch, String toBranch, int copies){

        Branch from = getBranch(fromBranch);
        Branch to = getBranch(toBranch);
        Book book= from.getBookInventory().getBook(ISBN);
        from.getBookInventory().removeCopies(ISBN,copies);
        to.getBookInventory().addBook(book,copies);
    }
}

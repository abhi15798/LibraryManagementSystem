package com.airtribe.librarymanagementsystem.entity;

import java.time.LocalDate;

public class Loan {
    private final String ISBN;
    private final String patronId;
    private final LocalDate issueDate;
    private LocalDate returnDate;
    private final LocalDate dueDate;

    public Loan(String ISBN, String patronId, LocalDate issueDate,LocalDate dueDate) {
        this.ISBN = ISBN;
        this.patronId = patronId;
        this.issueDate = issueDate;
        this.returnDate = null;
        this.dueDate=dueDate;
    }


    public String getISBN() {
        return ISBN;
    }

    public String getPatronId() {
        return patronId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void markAsReturned(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "ISBN='" + ISBN + '\'' +
                ", patronId='" + patronId + '\'' +
                ", issueDate=" + issueDate +
                ", returnDate=" + returnDate +
                ", dueDate=" + dueDate +
                '}';
    }
}

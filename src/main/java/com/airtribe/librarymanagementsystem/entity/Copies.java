package com.airtribe.librarymanagementsystem.entity;

public class Copies {
    private int total;
    private int available;

    public Copies(int total, int available) {
        this.total = total;
        this.available = available;
    }

    public int getTotal() {
        return total;
    }

    public void addCopies(int copies) {
        total = this.total + copies;
        available = this.available+copies;
    }

    public int getAvailable() {
        return available;
    }

    public void incrementAvailable(){
        if(total == available){
            throw  new IllegalStateException("Return cannot be done for non issued book");
        }
            available++;

    }
    public void decrementAvailable(){
        if(available<=0) {
            throw new IllegalStateException("No of copies are less than what you are lending");
        }available--;

    }
}
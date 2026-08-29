package com.airtribe.librarymanagementsystem.service;

import com.airtribe.librarymanagementsystem.entity.Book;
import com.airtribe.librarymanagementsystem.strategy.SearchStrategy;
import com.airtribe.librarymanagementsystem.inventory.BookInventory;
import java.util.List;

public class BookService {
    private BookInventory bookInventory;
    public BookService(BookInventory bookInventory){
        this.bookInventory = bookInventory;
    }
    public void addBook(Book book,int copies){
        bookInventory.addBook(book,copies);
    }
    public List<Book> searchBook(String query,SearchStrategy searchStrategy){
        return searchStrategy.search(query,bookInventory.getAllBooks());
    }
    public void updateBook(String ISBN,Book book){
        bookInventory.updateBook(ISBN,book);
    }
    public void removeBook(String ISBN){
        bookInventory.removeBook(ISBN);
    }
}
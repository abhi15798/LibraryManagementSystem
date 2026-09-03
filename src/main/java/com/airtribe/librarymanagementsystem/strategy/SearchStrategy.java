package com.airtribe.librarymanagementsystem.strategy;

import com.airtribe.librarymanagementsystem.entity.Book;

import java.util.List;

public interface SearchStrategy {
    public List<Book> search(String query,List<Book> books);

}

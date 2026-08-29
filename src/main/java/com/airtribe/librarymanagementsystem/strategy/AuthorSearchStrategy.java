package com.airtribe.librarymanagementsystem.strategy;

import com.airtribe.librarymanagementsystem.entity.Book;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorSearchStrategy implements SearchStrategy{
    @Override
    public List<Book> search(String query, List<Book> books) {
        return books.stream().filter(book -> book.getAuthor().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
    }
}

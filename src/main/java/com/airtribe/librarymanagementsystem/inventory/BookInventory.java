package com.airtribe.librarymanagementsystem.inventory;

import com.airtribe.librarymanagementsystem.entity.Book;
import com.airtribe.librarymanagementsystem.entity.Copies;
import com.airtribe.librarymanagementsystem.exception.BookNotAvailableException;
import com.airtribe.librarymanagementsystem.exception.BookNotFoundException;

import java.util.*;

public class BookInventory {
    private final Map<String, Copies>availableCopies;
    private final Map<String, Book>catalog;
    public BookInventory(){
        availableCopies= new HashMap<>();
        catalog = new HashMap<>();
    }

    public void addBook(Book book,int copies){
        if(catalog.get(book.getISBN()) == null){
            catalog.put(book.getISBN(),book);
            availableCopies.put(book.getISBN(),new Copies(copies, copies));
        }
        else{
            Copies existingCopies =availableCopies.get(book.getISBN());
            existingCopies.addCopies(copies);
        }
    }
    public Book getBook(String ISBN){
        return catalog.get(ISBN);
    }
    public void checkoutCopy(String ISBN) throws BookNotFoundException, BookNotAvailableException {
        if(catalog.get(ISBN) == null){
            throw new BookNotFoundException("Book is not available in the Catalog");
        }
        else{
            if(availableCopies.get(ISBN).getAvailable()<=0){
                throw  new BookNotAvailableException("Book is currently not available");
            }
            else{
                availableCopies.get(ISBN).decrementAvailable();
            }
        }
    }
    public void returnCopy(String ISBN) throws BookNotFoundException {
        if(catalog.get(ISBN) == null){
            throw new BookNotFoundException("Book is not available in the Catalog");
        }
        else{
                availableCopies.get(ISBN).incrementAvailable();
        }
    }
    public void removeBook(String ISBN) throws BookNotFoundException{
        if(catalog.get(ISBN)==null){
            throw new BookNotFoundException("Please provide the correct ISBN");
        }
        if(availableCopies.get(ISBN).getTotal() > availableCopies.get(ISBN).getAvailable()){
            throw new IllegalStateException("This book is currently issued by Some Patron. Cannot remove");
        }
        catalog.remove(ISBN);
        availableCopies.remove(ISBN);
    }
    public  void updateBook(String ISBN,Book updatedBook) throws BookNotFoundException{
        if(catalog.get(ISBN)==null){
            throw new BookNotFoundException("Please provide the correct ISBN");
        }
        Book book = catalog.get(ISBN);
        book.setAuthor(updatedBook.getAuthor());
        book.setTitle(updatedBook.getTitle());
        book.setPublicationYear(updatedBook.getPublicationYear());
    }
    public List<Book> getAllBooks(){
        return new ArrayList<>(catalog.values());
    }
    public void removeCopies(String ISBN,int copies){
        if(!catalog.containsKey(ISBN)){
            throw new BookNotFoundException("This book is currently not available here");
        }
        else if(availableCopies.get(ISBN).getAvailable()<copies){
            throw new BookNotAvailableException("No of copies not available, Total copies available - "+availableCopies.get(ISBN).getAvailable());
        }
        else{
            Copies oldCopies = availableCopies.get(ISBN);
            oldCopies.removeCopies(copies);
        }
    }

}

package com.example.StudentLibrary.services;


import com.example.StudentLibrary.models.Book;
import com.example.StudentLibrary.repositories.AuthorRepository;
import com.example.StudentLibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {


    @Autowired
    BookRepository bookRepository2;

    @Autowired
    AuthorRepository ar;

    public void createBook(Book book){

        bookRepository2.save(book);

    }

    public List<Book> getBooks(String genre, boolean available, String author){
        List<Book> books = null; //find the elements of the list by yourself

        books.addAll(bookRepository2.findBooksByAuthor(author,available));
        books.addAll(bookRepository2.findBooksByGenre(genre,available));
        books.addAll(bookRepository2.findBooksByGenreAuthor(genre, author, available));
        books.addAll(bookRepository2.findByAvailability(available));


        return books;

    }
}

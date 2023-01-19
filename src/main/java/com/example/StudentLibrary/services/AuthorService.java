package com.example.StudentLibrary.services;

import com.example.StudentLibrary.models.Author;
import com.example.StudentLibrary.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository1;

    public void create(Author author){
        authorRepository1.save(author);
    }
}

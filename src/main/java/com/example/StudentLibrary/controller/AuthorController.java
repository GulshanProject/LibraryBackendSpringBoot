package com.example.StudentLibrary.controller;

import com.example.StudentLibrary.models.Author;
import com.example.StudentLibrary.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


//Add required annotations
@RestController
public class AuthorController  {

    @Autowired
    AuthorService as;
    @PostMapping("/author")
    public ResponseEntity<String> createAuthor(@RequestBody() Author a)
    {
        as.create(a);
        return new ResponseEntity("author is added successfully" , HttpStatus.CREATED);
    }
}

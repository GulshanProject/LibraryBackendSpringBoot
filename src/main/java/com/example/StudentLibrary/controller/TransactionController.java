package com.example.StudentLibrary.controller;


import com.example.StudentLibrary.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    @Autowired
    TransactionService ts;

    //Add required annotations
    @PostMapping("/issueBook")
    public ResponseEntity issueBook(@RequestParam("cardId") int cardId, @RequestParam("bookId") int bookId) throws Exception{
        ts.issueBook(cardId , bookId);
        return new ResponseEntity<>("transaction completed", HttpStatus.ACCEPTED);
    }

    //Add required annotations
    @PostMapping("/returnBook")
    public ResponseEntity returnBook(@RequestParam("cardId") int cardId, @RequestParam("bookId") int bookId) throws Exception{
        //Transaction t = ts.returnBook(cardId , bookId);
        ts.returnBook(cardId , bookId);
        // return new ResponseEntity<>(t, HttpStatus.ACCEPTED);
        return new ResponseEntity<>("transaction completed", HttpStatus.ACCEPTED);

    }
}

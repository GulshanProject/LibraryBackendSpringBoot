package com.example.StudentLibrary.services;


import com.example.StudentLibrary.models.Book;
import com.example.StudentLibrary.models.Card;
import com.example.StudentLibrary.models.Transaction;
import com.example.StudentLibrary.models.TransactionStatus;
import com.example.StudentLibrary.repositories.BookRepository;
import com.example.StudentLibrary.repositories.CardRepository;
import com.example.StudentLibrary.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    BookRepository bookRepository5;

    @Autowired
    CardRepository cardRepository5;

    @Autowired
    TransactionRepository transactionRepository5;

    @Value("${books.max_allowed}")
    public int max_allowed_books;

    @Value("${books.max_allowed_days}")
    public int getMax_allowed_days;

    @Value("${books.fine.per_day}")
    public int fine_per_day;

    public String issueBook(int cardId, int bookId) throws Exception {
        //check whether bookId and cardId already exist
        boolean checkBook = bookRepository5.existsById(bookId);
        boolean checkCard = cardRepository5.existsById(cardId);
        // System.out.println(checkBook);
        Book emptyBook = new Book();
        Book b = bookRepository5.findById(bookId).orElse(emptyBook);
        Card emptyCard = new Card();
        Card c = cardRepository5.findById(cardId).orElse(emptyCard);
        Transaction t = new Transaction();
        t.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        //conditions required for successful transaction of issue book:
        //1. book is present and available

        try{
            if( checkBook == false || b.isAvailable() == false)
            {
                t.setTransactionStatus(TransactionStatus.FAILED);
                throw new Exception("Book is either unavailable or not present");
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        // If it fails: throw new Exception("Book is either unavailable or not present");
        //2. card is present and activated
        // If it fails: throw new Exception("Card is invalid");

        try{
            if(checkCard == false || c.getCardStatus().toString().equals("DEACTIVATED"))
            {
                t.setTransactionStatus(TransactionStatus.FAILED);
                throw new Exception("Card is invalid");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        //3. number of books issued against the card is strictly less than max_allowed_books
        // If it fails: throw new Exception("Book limit has reached for this card");

        try{
            if(c.getBooks().size() >= max_allowed_books)
            {
                t.setTransactionStatus(TransactionStatus.FAILED);
                throw new Exception("Book limit has reached for this card");
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        //If the transaction is successful, save the transaction to the list of transactions and return the id

        //Note that the error message should match exactly in all cases
        // Transaction t = new Transaction();
        if(checkCard == true) t.setCard(c);
        else t.setCard(null);
        if(checkBook == true) t.setBook(b);
        else t.setBook(null);
        if(t.getTransactionStatus().toString().equals("SUCCESSFUL")) {
            t.setIssueOperation(true);
            //t.setTransactionStatus(TransactionStatus.SUCCESSFUL);

            List<Book> books = c.getBooks();
            books.add(b);
            c.setBooks(books);
            cardRepository5.save(c);

            List<Transaction> transactionList = b.getTransactions();
            transactionList.add(t);
            b.setTransactions(transactionList);
            b.setAvailable(false);
            b.setCard(c); // ab book table me jab book issue ho jaegi toh cardid show ho jaega in book table
            bookRepository5.save(b);// naya book nhi add hoga sirf update hoga, transaction(child) apne aap
            // save ho jaega kyuki parent(book) is saved
        }
        else {
            transactionRepository5.save(t);
        }
        return t.getTransactionId();
        //return null; //return transactionId instead
    }

    public Transaction returnBook(int cardId, int bookId) throws Exception{




        List<Transaction> transactions = transactionRepository5.find(cardId, bookId,TransactionStatus.SUCCESSFUL, true);

        Transaction transaction = transactions.get(transactions.size() - 1);

        Date issueDate = transaction.getTransactionDate();

        long timeIssuetime = Math.abs(System.currentTimeMillis() - issueDate.getTime());

        long no_of_days_passed = TimeUnit.DAYS.convert(timeIssuetime, TimeUnit.MILLISECONDS);

        int fine = 0;
        if(no_of_days_passed > getMax_allowed_days){
            fine = (int)((no_of_days_passed - getMax_allowed_days) * fine_per_day);
        }

        Book book = transaction.getBook();

        book.setAvailable(true);
        book.setCard(null);

        bookRepository5.updateBook(book);

        Transaction tr = new Transaction();
        tr.setBook(transaction.getBook());
        tr.setCard(transaction.getCard());
        tr.setIssueOperation(false);
        tr.setFineAmount(fine);
        tr.setTransactionStatus(TransactionStatus.SUCCESSFUL);

        transactionRepository5.save(tr);

        return tr;
    }
}

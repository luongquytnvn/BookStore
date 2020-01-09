package com.codegym.controllers;

import com.codegym.models.Book;
import com.codegym.services.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookServiceImpl bookServiceImpl;

    @GetMapping("home")
    public ResponseEntity<Iterable<Book>> showListBook() {
        Iterable<Book> books = bookServiceImpl.findAllBook();
        return new ResponseEntity<Iterable<Book>>(books, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity addNewBook(@Valid @RequestBody Book book){
        try {
            bookServiceImpl.save(book);
            return new ResponseEntity(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Optional<Book> book = bookServiceImpl.findById(id);
        if (book.isPresent()){
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book){
       Optional<Book> currentBook = bookServiceImpl.findById(id);
       if (currentBook.isPresent()){
           currentBook.get().setId(id);
           currentBook.get().setAmount(book.getAmount());
           currentBook.get().setAuthor(book.getAuthor());
           currentBook.get().setCategory(book.getCategory());
           currentBook.get().setComments(book.getComments());
           currentBook.get().setDescription(book.getDescription());
           currentBook.get().setLanguages(book.getLanguages());
           currentBook.get().setName(book.getName());
           currentBook.get().setPicture(book.getPicture());
           currentBook.get().setPrice(book.getPrice());
           currentBook.get().setPublishing(book.getPublishing());

           bookServiceImpl.save(currentBook.get());
           return new ResponseEntity<Book>(currentBook.get(), HttpStatus.OK);
       }
       return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id){
        Optional<Book> book = bookServiceImpl.findById(id);
        if (book.isPresent()){
            bookServiceImpl.remote(id);
            return new ResponseEntity<Book>(HttpStatus.OK);
        }
        return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
    }
}

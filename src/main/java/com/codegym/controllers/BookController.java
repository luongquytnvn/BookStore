package com.codegym.controllers;

import com.codegym.models.Book;
import com.codegym.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    Environment env;
    @GetMapping("/")
    public String allAccess() {
        return "Public Content.";
    }
    @GetMapping("/book")
    public ResponseEntity<List<Book>> listAllBooks() {
        List<Book> books = (List<Book>) bookService.findAll();
        if (books.isEmpty()) {
            return new ResponseEntity<List<Book>>(books, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Optional<Book>> getBook(@PathVariable("id") long id) {
        System.out.println("Fetching Customer with id " + id);
        Optional<Book> book = bookService.findById(id);
        if (!book.isPresent()) {
            System.out.println("Customer with id " + id + " not found");
            return new ResponseEntity<Optional<Book>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Book>>(book, HttpStatus.OK);
    }

    @PostMapping("/book")
    public ResponseEntity<Void> createBook(@RequestBody Book book, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Book " + book.getName());
        bookService.save(book);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/customers/{id}").buildAndExpand(book.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<Optional<Book>> updateBook(@PathVariable("id") long id, @RequestBody Book book) {
        System.out.println("Updating Book " + id);

        Optional<Book> currentBook = bookService.findById(id);

        if (!currentBook.isPresent()) {
            System.out.println("Book with id " + id + " not found");
            return new ResponseEntity<Optional<Book>>(HttpStatus.NOT_FOUND);
        }
        currentBook.get().setName(book.getName());
        currentBook.get().setDescription(book.getDescription());
        currentBook.get().setImage(book.getImage());
        currentBook.get().setPrice(book.getPrice());
        currentBook.get().setQuantity(book.getQuantity());
        bookService.save(book);
        return new ResponseEntity<Optional<Book>>(currentBook, HttpStatus.OK);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Book with id " + id);

        Optional<Book> book = bookService.findById(id);
        if (!book.isPresent()) {
            System.out.println("Unable to delete. Book with id " + id + " not found");
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }

        bookService.remove(id);
        return new ResponseEntity<Book>(HttpStatus.NO_CONTENT);
    }
}

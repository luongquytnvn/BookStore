package com.codegym.controllers;

import com.codegym.models.Book;
import com.codegym.models.Category;
import com.codegym.models.Publishing;
import com.codegym.services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.FileSystem;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    IBookService bookService;
    @Autowired
    Environment env;

    @GetMapping("")
    public ResponseEntity<List<Book>> listAllBooks() {
        List<Book> books = (List<Book>) bookService.findAllBook();
        if (books.isEmpty()) {
            return new ResponseEntity<List<Book>>(books, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Book>> getBook(@PathVariable("id") long id) {
        System.out.println("Fetching Book with id " + id);
        Optional<Book> book = bookService.findById(id);
        if (!book.isPresent()) {
            System.out.println("Book with id " + id + " not found");
            return new ResponseEntity<Optional<Book>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Book>>(book, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Optional<Book>> createBook(@RequestBody Book book) {
        System.out.println("Creating Book " + book.getName());
        Category category = book.getCategory();
        Publishing publishing = book.getPublishing();
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        Book currentBook = new Book(book.getName(), book.getPrice(), book.getDescription(), book.getAmount(), date, book.getBookPictures(), book.getAuthors(), book.getLanguages(), publishing, category);
        bookService.save(currentBook);
        return new ResponseEntity<Optional<Book>>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Book>> updateBook(@PathVariable("id") long id, @RequestBody Book book) {
        System.out.println("Updating Book " + id);

        Optional<Book> currentBook = bookService.findById(id);

        if (!currentBook.isPresent()) {
            System.out.println("Book with id " + id + " not found");
            return new ResponseEntity<Optional<Book>>(HttpStatus.NOT_FOUND);
        }
        currentBook.get().setName(book.getName());
        currentBook.get().setPrice(book.getPrice());
        currentBook.get().setDescription(book.getDescription());
        currentBook.get().setAmount(book.getAmount());
        currentBook.get().setBookPictures(book.getBookPictures());
        currentBook.get().setAuthors(book.getAuthors());
        currentBook.get().setLanguages(book.getLanguages());
        currentBook.get().setPublishing(book.getPublishing());
        currentBook.get().setCategory(book.getCategory());
        bookService.save(currentBook.get());
        return new ResponseEntity<Optional<Book>>(currentBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Book with id " + id);

        Optional<Book> book = bookService.findById(id);
        if (!book.isPresent()) {
            System.out.println("Unable to delete. Book with id " + id + " not found");
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }

        bookService.remote(id);
        return new ResponseEntity<Book>(HttpStatus.NO_CONTENT);
    }
}

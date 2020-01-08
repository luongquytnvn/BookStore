package com.codegym.controllers;

import com.codegym.models.Book;
import com.codegym.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BookController {
    @Autowired
    BookService bookService;
    @GetMapping("/api/admin/book-list")
    public ResponseEntity<Page<Book>> listAllBooks(Pageable pageable) {
        Page<Book> books = bookService.findAll(pageable);
        if (books.isEmpty()) {
            return new ResponseEntity<Page<Book>>(books, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<Book>>(books,HttpStatus.OK);
    }
}

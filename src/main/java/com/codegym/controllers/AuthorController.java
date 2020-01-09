package com.codegym.controllers;

import com.codegym.models.Author;
import com.codegym.services.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorServiceImpl authorServiceImpl;

    @GetMapping("home")
    public ResponseEntity<Iterable<Author>> showListAuthor() {
        Iterable<Author> authors = authorServiceImpl.findAllAuthor();
        return new ResponseEntity<Iterable<Author>>(authors, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity addNewAuthor(@Valid @RequestBody Author author){
        try {
            authorServiceImpl.save(author);
            return new ResponseEntity(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id){
        Optional<Author> author = authorServiceImpl.findById(id);
        if (author.isPresent()){
            return new ResponseEntity<>(author.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author){
        Optional<Author> currentAuthor = authorServiceImpl.findById(id);
        if (currentAuthor.isPresent()){
            currentAuthor.get().setId(id);
            currentAuthor.get().setBooks(author.getBooks());
            currentAuthor.get().setCountry(author.getCountry());
            currentAuthor.get().setInFor(author.getInFor());
            currentAuthor.get().setName(author.getName());

            authorServiceImpl.save(currentAuthor.get());
            return new ResponseEntity<Author>(currentAuthor.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Author>(HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable Long id){
        Optional<Author> author = authorServiceImpl.findById(id);
        if (author.isPresent()){
            authorServiceImpl.remote(id);
            return new ResponseEntity<Author>(HttpStatus.OK);
        }
        return new ResponseEntity<Author>(HttpStatus.NOT_FOUND);
    }
}

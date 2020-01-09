package com.codegym.controllers;

import com.codegym.models.Author;
import com.codegym.services.AuthorService;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/api/admin/author")
    public ResponseEntity<List<Author>> listAllAuthors() {
        List<Author> authors = (List<Author>) authorService.findAll();
        if (authors.isEmpty()) {
            return new ResponseEntity<List<Author>>(authors, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Author>>(authors, HttpStatus.OK);
    }

    @GetMapping("/api/admin/author/{id}")
    public ResponseEntity<Optional<Author>> getAuthor(@PathVariable("id") long id) {
        System.out.println("Author with id " + id);
        Optional<Author> author = authorService.findById(id);
        if (!author.isPresent()) {
            System.out.println("Author with id" + id + "not found");
            return new ResponseEntity<Optional<Author>>(author, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Optional<Author>>(author, HttpStatus.OK);
    }

    @PostMapping("/api/admin/author")
    public ResponseEntity<Void> createAuthor(@RequestBody Author author, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println("Create Author" + author.getName());
        authorService.save(author);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/authors/{id}").buildAndExpand(author.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/api/admin/author/{id}")
    public ResponseEntity<Optional<Author>> updateAuthor(@PathVariable("id") long id, @RequestBody Author author) {
        System.out.println("Update Author" + id);

        Optional<Author> currentAuthor = authorService.findById(id);

        if (!currentAuthor.isPresent()) {
            System.out.println("Author with id" + id + "not found");
            return new ResponseEntity<Optional<Author>>(HttpStatus.NOT_FOUND);
        }

        currentAuthor.get().setName(author.getName());
        currentAuthor.get().setInformation(author.getInformation());
        currentAuthor.get().setCountry(author.getCountry());
        authorService.save(author);
        return new ResponseEntity<Optional<Author>>(currentAuthor, HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/author/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable("id") long id) {
        System.out.println("Delete Author with id" + id);

        Optional<Author> author = authorService.findById(id);
        if (!author.isPresent()) {
            System.out.println("Unable to delete. Book with id" + id + "not found");
            return new ResponseEntity<Author>(HttpStatus.NOT_FOUND);
        }
        authorService.remove(id);
        return new ResponseEntity<Author>(HttpStatus.NO_CONTENT);
    }
}

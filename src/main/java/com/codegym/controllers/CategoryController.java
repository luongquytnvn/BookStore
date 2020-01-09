package com.codegym.controllers;

import com.codegym.models.Book;
import com.codegym.models.Category;
import com.codegym.services.BookServiceImpl;
import com.codegym.services.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryServiceImpl categoryServiceImpl;

    @GetMapping("home")
    public ResponseEntity<Iterable<Category>> showListCategory() {
        Iterable<Category> categories = categoryServiceImpl.findAllCategory();
        return new ResponseEntity<Iterable<Category>>(categories, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity addNewCategory(@Valid @RequestBody Category category) {
        try {
            categoryServiceImpl.save(category);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryServiceImpl.findById(id);
        if (category.isPresent()) {
            return new ResponseEntity<>(category.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Optional<Category> currentCategory = categoryServiceImpl.findById(id);
        if (currentCategory.isPresent()) {
            currentCategory.get().setId(id);
            currentCategory.get().setBooks(category.getBooks());
            currentCategory.get().setName(category.getName());


            categoryServiceImpl.save(currentCategory.get());
            return new ResponseEntity<Category>(currentCategory.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        Optional<Category> category = categoryServiceImpl.findById(id);
        if (category.isPresent()) {
            categoryServiceImpl.remote(id);
            return new ResponseEntity<Category>(HttpStatus.OK);
        }
        return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
    }
}


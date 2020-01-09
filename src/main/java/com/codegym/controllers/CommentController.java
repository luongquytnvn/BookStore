package com.codegym.controllers;

import com.codegym.models.Category;
import com.codegym.models.Comment;
import com.codegym.services.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentServiceImpl commentServiceImpl;
    @GetMapping("home")
    public ResponseEntity<Iterable<Comment>> showListComment() {
        Iterable<Comment> comments = commentServiceImpl.findAllComment();
        return new ResponseEntity<Iterable<Comment>>(comments, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity addNewComment(@Valid @RequestBody Comment comment) {
        try {
            commentServiceImpl.save(comment);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = commentServiceImpl.findById(id);
        if (comment.isPresent()) {
            return new ResponseEntity<>(comment.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        Optional<Comment> currentComment = commentServiceImpl.findById(id);
        if (currentComment.isPresent()) {
            currentComment.get().setId(id);
            currentComment.get().setDate(comment.getDate());
            currentComment.get().setComment(comment.getComment());
            currentComment.get().setContent(comment.getContent());
            currentComment.get().setName(comment.getName());

            commentServiceImpl.save(currentComment.get());
            return new ResponseEntity<Comment>(currentComment.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long id) {
        Optional<Comment> comment = commentServiceImpl.findById(id);
        if (comment.isPresent()) {
            commentServiceImpl.remote(id);
            return new ResponseEntity<Comment>(HttpStatus.OK);
        }
        return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
    }
}

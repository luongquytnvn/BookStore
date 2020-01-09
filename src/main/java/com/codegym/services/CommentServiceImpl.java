package com.codegym.services;

import com.codegym.models.Comment;
import com.codegym.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    CommentRepository commentRepository;
    @Override
    public Iterable<Comment> findAllComment() {
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void remote(Long id) {
        commentRepository.deleteById(id);
    }
}

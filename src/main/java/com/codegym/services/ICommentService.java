package com.codegym.services;


import com.codegym.models.Comment;

import java.util.Optional;

public interface ICommentService {
    Iterable<Comment> findAllComment();
    Optional<Comment> findById(Long id);
    void save(Comment comment);
    void remote(Long id);
}

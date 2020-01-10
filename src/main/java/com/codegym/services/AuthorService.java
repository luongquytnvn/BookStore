package com.codegym.services;

import com.codegym.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Iterable<Author> findAll();

    Optional<Author> findById(Long id);

    void save(Author author);

    void remove(Long id);


}

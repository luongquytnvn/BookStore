package com.codegym.services;

import com.codegym.models.Book;

import java.util.Optional;

public interface IBookService {
    Iterable<Book> findAllBook();
    Optional<Book> findById(Long id);
    void save(Book book);
    void remote(Long id);
}

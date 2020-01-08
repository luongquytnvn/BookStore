package com.codegym.services;

import com.codegym.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

public interface BookService {
    Iterable<Book> findAll();

    Optional<Book> findById(Long id);

    void save(Book book);

    void remove(Long id);
}

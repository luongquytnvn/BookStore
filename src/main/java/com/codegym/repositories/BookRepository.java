package com.codegym.repositories;

import com.codegym.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book , Long> {
    List<Book> findByNameContainingOrderByDateCreateDesc(String name);
}

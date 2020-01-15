package com.codegym.repositories;

import com.codegym.models.Author;
import com.codegym.models.Book;
import com.codegym.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book , Long> {
    List<Book> findByNameContainingOrderByDateCreateDesc(String name);
    List<Book> findAllByCategory_Id(Long id);
    @Query(value = "select book_id from book join book_authors on book.id = book_authors.book_id where authors_id = ?1", nativeQuery = true)
    List<Long> findBookByAuthor(Long id);
}

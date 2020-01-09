package com.codegym.repositories;

import com.codegym.models.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository  extends CrudRepository<Author, Long> {
}

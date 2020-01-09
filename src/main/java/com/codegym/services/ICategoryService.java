package com.codegym.services;


import com.codegym.models.Category;

import java.util.Optional;

public interface ICategoryService {
    Iterable<Category> findAllCategory();
    Optional<Category> findById(Long id);
    void save(Category category);
    void remote(Long id);
}

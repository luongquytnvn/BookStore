package com.codegym.services;


import com.codegym.models.Language;

import java.util.Optional;

public interface ILanguageService {
    Iterable<Language> findAllLanguage();
    Optional<Language> findById(Long id);
    void save(Language language);
    void remote(Long id);
}

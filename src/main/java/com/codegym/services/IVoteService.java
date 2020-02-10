package com.codegym.services;

import com.codegym.models.Payment;
import com.codegym.models.Vote;

import java.util.Optional;

public interface IVoteService {
    Iterable<Vote> findAll();

    Vote findById(Long id);

    void save(Vote vote);

    void remove(Long id);
}

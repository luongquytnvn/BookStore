package com.codegym.services.impl;

import com.codegym.models.Vote;
import com.codegym.repositories.VoteRepository;
import com.codegym.services.IVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements IVoteService {
    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Iterable<Vote> findAll() {
        return voteRepository.findAll();
    }

    @Override
    public Vote findById(Long id) {
        return voteRepository.getOne(id);
    }

    @Override
    public void save(Vote vote) {
        voteRepository.save(vote);
    }

    @Override
    public void remove(Long id) {
        voteRepository.deleteById(id);
    }
}

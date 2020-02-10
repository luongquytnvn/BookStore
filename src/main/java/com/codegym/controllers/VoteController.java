package com.codegym.controllers;

import com.codegym.models.Vote;
import com.codegym.services.IVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/vote")
public class VoteController {
    @Autowired
    private IVoteService voteService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        List<Vote> votes = (List<Vote>) voteService.findAll();
        if (votes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Vote>>(votes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Vote vote = voteService.findById(id);
        if (vote == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Vote>(vote, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createVote(@RequestBody Vote vote) {
        if (vote == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        voteService.save(vote);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVote(@PathVariable Long id, @RequestBody Vote vote) {
        Vote currentVote = voteService.findById(id);
        if (currentVote == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentVote.setVote(vote.isVote());
        voteService.save(currentVote);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVote(@PathVariable Long id) {
        Vote vote = voteService.findById(id);
        if (vote == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        voteService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

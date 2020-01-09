package com.codegym.services;



import com.codegym.models.signinSignup.User;

import java.util.Optional;

public interface IUserService {
  Optional<User> findById(Long id);
  Optional<User> findByEmail(String email);
  Optional<User> findByUsername(String username);
}

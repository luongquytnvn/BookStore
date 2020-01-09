package com.codegym.controllers;


import com.codegym.message.request.LoginForm;
import com.codegym.message.request.SignUpForm;
import com.codegym.message.response.JwtResponse;
import com.codegym.models.signinSignup.Role;
import com.codegym.models.signinSignup.RoleName;
import com.codegym.models.signinSignup.User;
import com.codegym.repositories.RoleRepository;
import com.codegym.repositories.UserRepository;
import com.codegym.security.jwt.JwtClazz;
import com.codegym.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtClazz jwtClazz;

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@RequestBody LoginForm loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = JwtClazz.generateJwtToken(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
  }
    @PostMapping(value = "/signup")
    public ResponseEntity registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        System.out.println("ok");
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/view/user/{name}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Optional<User>> userDetails(@PathVariable("name") String userName) {
        try {
            Optional<User> user = userRepository.findByUsername(userName);
            return new ResponseEntity<Optional<User>>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("update/user")
    public ResponseEntity<User> updateProfile(@RequestBody User user) {
        User currentUser = userDetailsService.getCurrentUser();
        if (user != null) {
            currentUser.setUsername(user.getUsername());
            currentUser.setEmail(user.getEmail());
            currentUser.setName(user.getName());
            userRepository.save(currentUser);
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/password/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity updatePasswordUser(@RequestBody User user) {
        try {
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
            roles.add(userRole);
            user.setRoles(roles);
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

    }





}

package com.codegym.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class BookController {
    @GetMapping("/")
    public String home() {
        return "hello";
    }
}

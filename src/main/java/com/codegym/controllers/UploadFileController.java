package com.codegym.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UploadFileController {
    @Autowired
    Environment env;

    @PostMapping("/admin/upload-file")
    public ResponseEntity<Void> createBook(@RequestParam("fileUpLoad") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        file.transferTo(new File(fileName));
        System.out.println(fileName);
        System.out.println("uploaded");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}

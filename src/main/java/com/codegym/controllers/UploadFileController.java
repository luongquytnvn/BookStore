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
@RequestMapping("/api/admin")
public class UploadFileController {
    @Autowired
    Environment env;

    @PostMapping("/upload-file")
    public ResponseEntity<Void> createBook(@RequestParam("file") MultipartFile file) throws IOException {
        long time = System.currentTimeMillis();
        String fileName = time + "-" + file.getOriginalFilename();
        String filePath = env.getProperty("fileLink") + fileName;
        file.transferTo(new File(filePath));
        System.out.println("uploaded");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}

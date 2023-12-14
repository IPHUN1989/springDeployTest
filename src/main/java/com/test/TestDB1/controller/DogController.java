package com.test.TestDB1.controller;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dogs")
public class DogController {
    private static final List<String> dogs = List.of("Dog1", "Dog2", "Dog3", "Dog4", "Dog5");


    @GetMapping
    public ResponseEntity<List<String>> getAllDogs() {
        System.out.println("The LOG_PATH env is: " + System.getenv("LOG_PATH"));
        if (dogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(dogs, HttpStatus.OK);
        }
    }

}



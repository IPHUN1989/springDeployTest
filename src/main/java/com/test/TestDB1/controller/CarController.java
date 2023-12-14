package com.test.TestDB1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private static final List<String> cars = List.of("Car1", "Car2", "Car3", "Car4", "Car5");


    @GetMapping
    public ResponseEntity<List<String>> getAllCars() {
        if (cars.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
    }
}

package com.example.bai2.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
    @GetMapping
    public String chaoxin() {
        return "Welcome to the Home Page!";
    }
}

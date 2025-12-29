package org.example.crypto_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiController {

    @GetMapping("/api/browser")
    public String apiBrowser() {
        return "api";
    }
}
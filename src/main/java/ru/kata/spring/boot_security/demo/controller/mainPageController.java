package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
public class mainPageController {


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}

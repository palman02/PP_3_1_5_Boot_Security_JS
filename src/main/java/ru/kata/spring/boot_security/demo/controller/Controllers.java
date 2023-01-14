package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;



@Controller
public class Controllers {

    @GetMapping("/admin")
    public String showAllUser(Model model) {
        model.addAttribute("newUser", new User());

        return "showAllUsers";
    }

    @GetMapping("/user")
    public String showOneUser() {
        return "showOneUser";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}

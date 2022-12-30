package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;


@Controller
public class mainPageController {

    private final ServiceUser serviceUser;

    private final UserValidator userValidator;


    @Autowired
    public mainPageController(ServiceUser serviceUser, UserValidator userValidator) {
        this.serviceUser = serviceUser;
        this.userValidator = userValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/mainPage/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "/mainPage/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user")  @Valid User user,
                                      BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "mainPage/registration";
        }
        serviceUser.saveUser(user);
        return "redirect:/login";
    }
}

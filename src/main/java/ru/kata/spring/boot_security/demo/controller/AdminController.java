package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;
import ru.kata.spring.boot_security.demo.util.UserValidator;


@Controller
public class AdminController {

    private final ServiceUser serviceUser;

    private final UserValidator userValidator;


    @Autowired
    public AdminController(ServiceUser serviceUser, UserValidator userValidator) {
        this.serviceUser = serviceUser;
        this.userValidator = userValidator;
    }

    @GetMapping("admin/users")
    public String showAllUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", serviceUser.findAll());
        model.addAttribute("currentUser", user);
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", serviceUser.getRole());
        return "showAllUsers";
    }

    @GetMapping("/user/{id}")
    public String showOneUser(@PathVariable int id, @AuthenticationPrincipal User user, Model model) {
//        model.addAttribute("user", serviceUser.findOne(id));
        model.addAttribute("currentUser", user);
        return "showOneUser";
    }

    @PostMapping("admin/new")
    public String createUser(@ModelAttribute User user, BindingResult bindingResult) {
//        userValidator.validate(user, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return "forAdmin/showAllUsers";
//        }
        serviceUser.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("editUser", serviceUser.findOne(id));
        return "showAllUsers";
    }

    @PatchMapping("/admin/user/edit/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute("user") User user, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "/forAdmin/editUser";
//        }
        serviceUser.update(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/user/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        serviceUser.deleteUser(id);
        return "redirect:/admin/users";
    }
}

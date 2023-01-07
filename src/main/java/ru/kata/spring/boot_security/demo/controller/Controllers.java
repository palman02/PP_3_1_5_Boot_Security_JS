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

    private final ServiceUser serviceUser;

    @Autowired
    public Controllers(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    @GetMapping("admin/users")
    public String showAllUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", serviceUser.findAll());
        model.addAttribute("currentUser", user);
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", serviceUser.getRole());
        return "showAllUsers";
    }

    @GetMapping("/user")
    public String showOneUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("currentUser", user);
        return "showOneUser";
    }

    @PostMapping("admin/new")
    public String createUser(@ModelAttribute User user) {
        serviceUser.saveUser(user);
        return "redirect:/admin/users";
    }

    @PatchMapping("/admin/user/edit/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute("user") User user) {
        serviceUser.update(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/user/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        serviceUser.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}

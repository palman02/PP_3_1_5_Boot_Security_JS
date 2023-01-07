package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;


@org.springframework.stereotype.Controller
public class Controller {

    private final ServiceUser serviceUser;

    @Autowired
    public Controller(ServiceUser serviceUser) {
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

    @GetMapping("/user/{id}")
    public String showOneUser(@PathVariable int id, @AuthenticationPrincipal User user, Model model) {
        model.addAttribute("currentUser", user);
        return "showOneUser";
    }

    @PostMapping("admin/new")
    public String createUser(@ModelAttribute User user, BindingResult bindingResult) {
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

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}

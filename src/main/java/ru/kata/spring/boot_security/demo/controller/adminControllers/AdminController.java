package ru.kata.spring.boot_security.demo.controller.adminControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;
import ru.kata.spring.boot_security.demo.util.UserValidator;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ServiceUser serviceUser;

    private final UserValidator userValidator;


    @Autowired
    public AdminController(ServiceUser serviceUser, UserValidator userValidator) {
        this.serviceUser = serviceUser;
        this.userValidator = userValidator;
    }

    @GetMapping("/users")
    public String showAllUser(Model model) {
        model.addAttribute("users", serviceUser.findAll());
        return "/forAdmin/showAllUsers";
    }

    @GetMapping("/user/{id}")
    public String showOneUser(@PathVariable int id, Model model) {
        model.addAttribute("user", serviceUser.findOne(id));
        return "/forAdmin/showOneUser";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "/forAdmin/newUser";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute User user) {
        serviceUser.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/user/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", serviceUser.findOne(id));
        return "/forAdmin/editUser";
    }

    @PatchMapping("/user/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable int id) {
        serviceUser.update(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        serviceUser.deleteUser(id);
        return "redirect:/admin/users";
    }
}

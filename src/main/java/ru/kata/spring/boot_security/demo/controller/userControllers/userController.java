package ru.kata.spring.boot_security.demo.controller.userControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;
import ru.kata.spring.boot_security.demo.util.UserValidator;


@Controller
@RequestMapping("/user")
public class userController {

    private final ServiceUser serviceUser;

    private final UserValidator userValidator;


    @Autowired
    public userController(ServiceUser serviceUser, UserValidator userValidator) {
        this.serviceUser = serviceUser;
        this.userValidator = userValidator;
    }

    @GetMapping("/{id}")
    public String showOneUser(@PathVariable int id, Model model) {
        model.addAttribute("user", serviceUser.findOne(id));
        return "/forUser/userPage";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", serviceUser.findOne(id));
        return "/forUser/settings";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable int id) {
        serviceUser.update(id, user);
        return "redirect:/user/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        serviceUser.deleteUser(id);
        return "redirect:/login";
    }

}

//package ru.kata.spring.boot_security.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_security.demo.models.User;
//import ru.kata.spring.boot_security.demo.service.ServiceUser;
//import javax.validation.Valid;
//
//
//@Controller
//@RequestMapping("/user")
//public class userController {
//
//    private final ServiceUser serviceUser;
//
//
//    @Autowired
//    public userController(ServiceUser serviceUser) {
//        this.serviceUser = serviceUser;
//    }
//
//
//    @PatchMapping("/{id}")
//    public String updateUser(@PathVariable int id, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors()) {
//            return "forUser/settings";
//        }
//        serviceUser.update(id, user);
//        return "redirect:/user/{id}";
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteUser(@PathVariable("id") int id) {
//        serviceUser.deleteUser(id);
//        return "redirect:/login";
//    }
//
//}

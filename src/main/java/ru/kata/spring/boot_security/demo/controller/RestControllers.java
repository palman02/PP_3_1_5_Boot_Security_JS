package ru.kata.spring.boot_security.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RestControllers {

    private final ModelMapper modelMapper;
    private final ServiceUser serviceUser;

    @Autowired
    public RestControllers(ModelMapper modelMapper, ServiceUser serviceUser) {
        this.modelMapper = modelMapper;
        this.serviceUser = serviceUser;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> list =  serviceUser.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        User user =serviceUser.findOne(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/user")
    public ResponseEntity<HttpStatus> createUser(@RequestBody User user) {
        serviceUser.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("user/{id}")
    public ResponseEntity<HttpStatus> editUser(@PathVariable int id, @RequestBody User user) {
        serviceUser.update(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable int id) {
        serviceUser.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/auth")
    public ResponseEntity<User> getAuthUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }
}

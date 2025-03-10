package com.kauadev.learning_spring_security.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kauadev.learning_spring_security.domain.entities.User;
import com.kauadev.learning_spring_security.services.UserService;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<List<User>> users() {
        List<User> users = this.userService.findUsers();

        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> user(@PathVariable("id") Integer id) {
        User user = this.userService.findUserById(id);

        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody User newUser) {
        User user = this.userService.updateUser(id, newUser);

        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) {
        this.userService.deleteUser(id);

        return ResponseEntity.ok().body("Deleted user with id " + id);
    }
}

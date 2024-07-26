package com.example.codingpractice.controller;


import com.example.codingpractice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.codingpractice.service.UserJpaService;

import java.util.List;

@RestController
public class UserController {
@Autowired
UserJpaService userJpaService;

@GetMapping("get-user")
public List<User> getUser() {
    return userJpaService.getUsers();
}

@PostMapping("/create-user")
    public String createUser(@RequestBody User user) {
    return userJpaService.createUser(user);
}

@GetMapping("/home")
    public  String getHome() {
    return "hey";
    }


@GetMapping("get_user/{identifier}")
public User getUserByIdentifier(@PathVariable String identifier) {
   return userJpaService.getUserByIdentifier(identifier);
 }

}

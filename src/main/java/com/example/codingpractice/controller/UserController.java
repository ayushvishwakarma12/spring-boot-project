package com.example.codingpractice.controller;


import com.example.codingpractice.model.Manager;
import com.example.codingpractice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.codingpractice.service.UserJpaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Controller class for handling HTTP requests related to User operations.
 */
@RestController
public class UserController {

    // Injecting the UserJpaService dependency to handle business logic
    @Autowired
    UserJpaService userJpaService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/get_users")
    public List<User> getUser() {
        return userJpaService.getUsers();
    }

    @PostMapping("/create_user")
        public String createUser(@RequestBody User user) {
        return userJpaService.createUser(user);
    }


    @GetMapping("/get_users/{identifier}")
    public User getUserByIdentifier(@PathVariable String identifier) {
        if (identifier.length() != 10) {
            logger.info(identifier);
            UUID id = UUID.fromString(identifier);
            return userJpaService.getUserByIdentifier(id);
        }
      else {
            logger.info("hey {}", identifier);
            return userJpaService.getUserByMobileNo(identifier);
        }
}

   @PostMapping("/get_users")
    public List<User> getUsersByData(@RequestBody User user) {
        return userJpaService.getUsersByData(user);
   }

   @PostMapping("/delete_user")
    public ResponseEntity<String> deleteUser(@RequestBody User user) {
        if (user.getUserId() == null && user.getMobNo() == null) {
            return new ResponseEntity<>("Either user_id or mob_num must be provided", HttpStatus.BAD_REQUEST);
        }
        return userJpaService.deleteUser(user);
   }

   @PostMapping("/update_user")
    public ResponseEntity<String> updateUsers(@RequestBody Map<String, Object> updateData) {
       List<UUID> userId = (List<UUID>) updateData.get("user_ids");

       logger.info(userId.toString());

       return userJpaService.updateUser(userId, updateData);
   }

}

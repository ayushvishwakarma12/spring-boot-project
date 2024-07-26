package com.example.codingpractice.repository;


import com.example.codingpractice.model.User;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface UserRepository {
    String createUser(User user);
    List<User> getUsers();
    User getUserByIdentifier(String identifier);
}

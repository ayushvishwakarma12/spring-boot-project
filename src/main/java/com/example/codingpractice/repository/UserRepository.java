package com.example.codingpractice.repository;


import com.example.codingpractice.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserRepository {
    String createUser(User user);
    List<User> getUsers();
    User getUserByIdentifier(UUID identifier);
    User getUserByMobileNo(String mobileNo);
    List<User> getUsersByData(User user);
    ResponseEntity<String> deleteUser(User user);
    ResponseEntity<String> updateUser(List<UUID> userId, Map<String, Object> updateData);
}

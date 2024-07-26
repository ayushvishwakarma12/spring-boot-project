package com.example.codingpractice.service;


import com.example.codingpractice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import com.example.codingpractice.repository.UserJpaRepository;
import com.example.codingpractice.repository.UserRepository;

import java.util.List;

import static com.example.codingpractice.utils.ValidationMethods.validateUser;

@Service
public class UserJpaService implements UserRepository {
    @Autowired
    UserJpaRepository userJpaRepository;



    @Override
    public String createUser(User user) {
        try {
            if (validateUser(user)) {
                userJpaRepository.save(user);
                return "User successfully created...";

            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect PAN Card");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect PAN Card");
        }

    }

    @Override
    public List<User> getUsers() {
        return userJpaRepository.findAll();
    }

    public User getUserByIdentifier(String identifier) {
        try {
            if (identifier.matches("\\d+")) {
                return userJpaRepository.findById(identifier);
            } else {
                return userJpaRepository.findByUser_id(identifier);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}

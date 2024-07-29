package com.example.codingpractice.service;


import com.example.codingpractice.model.Manager;
import com.example.codingpractice.model.User;
import com.example.codingpractice.repository.ManagerJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import com.example.codingpractice.repository.UserJpaRepository;
import com.example.codingpractice.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.codingpractice.utils.ValidationMethods;

@Service
public class UserJpaService implements UserRepository {
    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    ManagerJpaRepository managerJpaRepository;

    @Autowired
    private ValidationMethods validationMethods;
    private static final Logger logger = LoggerFactory.getLogger(UserJpaService.class);

    @Override
    public String createUser(User user) {
        try {
            if (validationMethods.validateUser(user)) {
                userJpaRepository.save(user);
                return "User successfully created...";
            } else {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Incorrect PAN Card");
            }
        } catch (ResponseStatusException e) {
            throw e;
        }
        catch (Exception e) {
            logger.info("validation failed: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public List<User> getUsers() {
        return userJpaRepository.findAll();
    }

    @Override
    public User getUserByIdentifier(UUID identifier) {
        try {
            return userJpaRepository.findByUserId(identifier);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public User getUserByMobileNo(String mobileNo) {
        try {
            return userJpaRepository.findByMobNo(mobileNo);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<User> getUsersByData(User user) {
        List<User> users = new ArrayList<>();
        if (user.getUserId() == null && user.getMobNo() == null && user.getManagerId() == null) {
            return userJpaRepository.findAll();
        } else if(user.getUserId() != null) {
            User existingUser = userJpaRepository.findByUserId(user.getUserId());
            if (existingUser != null) {
                users.add(userJpaRepository.findByUserId(user.getUserId()));
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with provided userId");
            }

        } else if (user.getMobNo() != null) {
            User existingUser = userJpaRepository.findByMobNo(user.getMobNo());
            if (existingUser != null) {
                logger.info(user.getMobNo());
                logger.info(String.valueOf(existingUser));
                users.add(existingUser);

            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with provided mobile number");
            }

        } else if (user.getManagerId() != null) {
            Manager existingManger = managerJpaRepository.findByManagerId(user.getManagerId());
            if (existingManger != null) {
                users.addAll(existingManger.getUsers());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid manager Id");
            }
        }

        return users;
    }

    @Override
    public ResponseEntity<String> deleteUser(User user) {
        User existingUser = null;

        if (user.getUserId() != null) {
            existingUser = userJpaRepository.findByUserId(user.getUserId());
        }
        if (existingUser != null) {
            userJpaRepository.delete(existingUser);
            return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
        }
        if (user.getMobNo() != null) {
            existingUser = userJpaRepository.findByMobNo(user.getMobNo());
        }
        if (existingUser != null) {
            userJpaRepository.delete(existingUser);
            return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
        }

        return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> updateUser(List<UUID> userId, Map<String, Object> updateData)  {
        Map<String, Object> data =  (Map<String, Object>) updateData.get("updateData");
        if (userId.size() > 1) {
            if (!data.containsKey("manager_id")) {
                return new ResponseEntity<>("Bulk update is allowed only for manager_id", HttpStatus.BAD_REQUEST);
            }
        }
        if (updateData.containsKey("manager_id")) {
            UUID newManagerId = UUID.fromString((String) updateData.get("manager_id"));
            if (!managerJpaRepository.existsById(newManagerId)) {
                return new ResponseEntity<>("Invalid manager ID", HttpStatus.BAD_REQUEST);
            }
        }

        List<User> usersToUpdate = userJpaRepository.findAllById(userId);


        if (usersToUpdate.size() != userId.size()) {
            return new ResponseEntity<>("Some user IDs were not found" , HttpStatus.NOT_FOUND);
        }

        for (User user : usersToUpdate) {
            if (data.containsKey("manager_id")) {
                handleManagerUpdate(user, UUID.fromString((String) data.get("manager_id")));
            } else {
                updateUserDetails(user, data);
            }
        }

        return new ResponseEntity<>("Users successfully updated", HttpStatus.OK);
    }

    private void handleManagerUpdate(User user, UUID newManagerId) {
        // Check if the user already has a manager
        if (user.getManagerId() != null) {
            // Deactivate the current user entry
            user.setIsActive(false);
            userJpaRepository.save(user);

            // Create a new entry with the updated manager_id
            User newUser = new User();
            newUser.setUserId(UUID.randomUUID());
            newUser.setFullName(user.getFullName());
            newUser.setMobNo(user.getMobNo());
            newUser.setPanNum(user.getPanNum());
            newUser.setManagerId(newManagerId);
            newUser.setCreatedAt(user.getCreatedAt());
            newUser.setUpdatedAt(LocalDateTime.now());
            newUser.setIsActive(true);
            userJpaRepository.save(newUser);
        } else {
            // Update the manager_id directly
            user.setManagerId(newManagerId);
            user.setUpdatedAt(LocalDateTime.now());
            userJpaRepository.save(user);
        }
    }

    private void updateUserDetails(User user, Map<String, Object> updateData) {
        // Update user details based on the provided updateData
        if (updateData.containsKey("full_name")) {
            user.setFullName((String) updateData.get("full_name"));
            userJpaRepository.save(user);
        }
        if (updateData.containsKey("mob_num")) {
            String newMobNum = (String) updateData.get("mob_num");
            if (validationMethods.adjustMobileNumber(user, newMobNum) == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid mobile number format.");
            }
        }
        if (updateData.containsKey("pan_num")) {
            String newPanNum = (String) updateData.get("pan_num");
            if (!validationMethods.validateAndFormatPAN(user)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid PAN number format.");
            }
        }

        user.setUpdatedAt(LocalDateTime.now());
        userJpaRepository.save(user);
    }
}




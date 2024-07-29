package com.example.codingpractice.utils;

import com.example.codingpractice.model.Manager;
import com.example.codingpractice.model.User;
import com.example.codingpractice.repository.ManagerJpaRepository;
import com.example.codingpractice.repository.UserJpaRepository;
import com.example.codingpractice.service.UserJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class ValidationMethods {
    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    ManagerJpaRepository managerJpaRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserJpaService.class);

    public boolean validateUser(User user) {

        if (Objects.equals(user.getFullName(), "")) {
            logger.info("Username is empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Full name must not be empty.");

        }
        if (adjustMobileNumber(user, user.getMobNo()) == null) {
            logger.info("Mobile No. is empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid mobile number.");
        }

        if (!validateAndFormatPAN(user)) {
            logger.info("PanCard is empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid PAN number format.");
        }

        if (!validateManagerId(user)) {
            logger.info("Manager Id is empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid manager ID.");
        }

     return  true;

    }

    public String adjustMobileNumber(User user, String mobileNumber) {

        String numericMobNum = mobileNumber.replaceAll("[^\\d]", "");
        logger.info("Mobile No. {}", numericMobNum);

        User existingUser = userJpaRepository.findByMobNo(mobileNumber);
        if (existingUser != null) {
            String existingMobileNumber = existingUser.getMobNo();
            if (existingMobileNumber != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mobile number already registered");
            }
        }


        if (numericMobNum.startsWith("0")) {
             numericMobNum = numericMobNum.substring(1);
        } else if (numericMobNum.startsWith("91") && numericMobNum.length() == 12) {
           numericMobNum = numericMobNum.substring(2);
        }
         else if (numericMobNum.length() != 10) {
            return null;
        }
        user.setMobNo(numericMobNum);
        return numericMobNum;
    }

    public  boolean validateAndFormatPAN(User user)  {
        if (!user.getPanNum().toUpperCase().matches("[A-Z]{5}[0-9]{4}[A-Z]")) {
            return false;
        }
        user.setPanNum(user.getPanNum().toUpperCase());
        return true;
    }

    public boolean validateManagerId(User user) {
        logger.info("Manager id {}", user.getManagerId());
        if (user.getManagerId() == null) {
           return true;
       }

        try {
            logger.info("try block");
            Manager manager = managerJpaRepository.findByManagerId(user.getManagerId());
            logger.info("Manager {}", manager.getFullName());
            if (manager != null) {
                return true;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            logger.info("catch block");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
}

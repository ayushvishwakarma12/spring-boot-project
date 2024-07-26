package com.example.codingpractice.utils;

import com.example.codingpractice.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ValidationMethods {

    public static boolean validateUser(User user) {

        if (user.getFull_name() == null) {
            return false;
        }
        else if (adjustMobileNumber(user, user.getMob_num()) == null) {
            return false;
        }

        else return validateAndFormatPAN(user.getPan_num()) != null;



    }

    public static String adjustMobileNumber(User user, String mobileNumber) {
        String numericMobNum = mobileNumber.replaceAll("[^\\d]", "");

        if (numericMobNum.startsWith("0")) {
             user.setMob_num("+91" + numericMobNum.substring(1));
             return user.getMob_num();
        } else if (numericMobNum.startsWith("+91")) {
            return  numericMobNum;
        }  else if (numericMobNum.startsWith("+91") && numericMobNum.length() > 10) {
            return null;
        }
        else {
            user.setMob_num(user.getMob_num());
            return mobileNumber;
        }
    }

    public static String validateAndFormatPAN(String pan)  {
        pan = pan.replaceAll("\\s", "");
        pan = pan.toUpperCase();
        if (!pan.matches("[A-Z]{5}[0-9]{4}[A-Z]")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PAN Number Should Follow Format: ABCDE1234F");
        }

        return pan;
    }
}

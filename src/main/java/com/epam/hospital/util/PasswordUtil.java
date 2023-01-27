package com.epam.hospital.util;

import com.epam.hospital.encoding.PasswordEncoder;
import com.epam.hospital.encoding.PasswordEncoderFactories;

import java.util.Random;

public class PasswordUtil {

    private PasswordUtil() {
    }

    public static String generatePassword(){
        String passwordChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder password = new StringBuilder();
        Random rnd = new Random();
        while (password.length() < 18) {
            int index = (int) (rnd.nextFloat() * passwordChars.length());
            password.append(passwordChars.charAt(index));
        }
        return password.toString();
    }

    public static String encryptPassword(String password){
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}

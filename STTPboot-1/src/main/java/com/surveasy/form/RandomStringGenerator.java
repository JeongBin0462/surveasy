package com.surveasy.form;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomStringGenerator {
    public static String generateRandomString() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
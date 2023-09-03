package com.surveasy.form;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomStringGenerator {
	public static String generateRandomString() {
	    SecureRandom random = new SecureRandom();
	    byte bytes[] = new byte[20];
	    String result;

	    do {
	        random.nextBytes(bytes);
	        result = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	    } while (result.charAt(0) == '0');

	    return result;
	}
}
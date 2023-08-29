package com.surveasy.model;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ModelAttribute("isAuthenticated")
	public boolean isAuthenticated() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    boolean authStatus = !(auth instanceof AnonymousAuthenticationToken);
	    System.out.println("Authentication status: " + authStatus);
	    return authStatus;
	}
}

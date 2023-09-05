package com.surveasy.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
	
	// 인증여부
	@ModelAttribute("isAuthenticated")
	public boolean isAuthenticated() {
		// Authentication : 저장소에 의해 인증된 사용자의 권한 목록
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    // 익명 사용자 토큰 생성
	    // 로그인 될 경우 권한을 부여 받게 된다.
	    boolean authStatus = !(auth instanceof AnonymousAuthenticationToken);
	    return authStatus;
	}
}

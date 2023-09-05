package com.surveasy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
				// 로그인 인증이 되어야 접속할 수 있는 url
				.requestMatchers(new AntPathRequestMatcher("/surveasy/inputsurvey")).authenticated()
				.requestMatchers(new AntPathRequestMatcher("/surveasy/makesurvey")).authenticated()
				.requestMatchers(new AntPathRequestMatcher("/surveasy/survey")).authenticated()
				// html, js, css 허용
				.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
				// CSRF: 웹 사이트 취약점 공격 방지를 위해 사용하는 기술
				.csrf((csrf) -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/surveasy/**")))
				// 헤더설정 -> clickjacking 공격을 막기위해 사용
				.headers((headers) -> headers.addHeaderWriter(
						new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
				// 로그인 설정
				.formLogin((formLogin) -> formLogin
                .loginPage("/surveasy/user/login")
                .defaultSuccessUrl("/surveasy/main"))
				// 로그아웃 설정
				.logout((logout) -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/surveasy/user/logout"))
                .logoutSuccessUrl("/surveasy/main")
                .deleteCookies("JSESSIONID")
                // 창을 닫았을 때 로그아웃
                .invalidateHttpSession(true))
				// 세션설정
                .sessionManagement(sessionManagement -> 
                sessionManagement
                    .maximumSessions(1)
                    // 새 아이디로 로그인 되면 기존 세션 제거
                    .maxSessionsPreventsLogin(false)
                    .expiredUrl("/surveasy/main")
            );
		return http.build();
	}
	
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	// 시큐리티의 인증을 담당
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
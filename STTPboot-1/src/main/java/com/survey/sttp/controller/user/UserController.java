package com.survey.sttp.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.survey.sttp.model.user.UserDTO;
import com.survey.sttp.service.user.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/STTP/user")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping(value = "/joinagree")
	public String joinagree() {
		
		return "/7.1join_agree";
	}
    
    @GetMapping(value = "/join")
	public String join() {
		
		return "/7.2join";
	}

    @PostMapping(value = "/join", consumes = "application/json")
    public String createUser(@RequestBody UserDTO userDTO) {
    	log.info(userDTO.toString());
        return userService.insertUser(userDTO);
    }
}
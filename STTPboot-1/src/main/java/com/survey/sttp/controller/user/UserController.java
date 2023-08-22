package com.survey.sttp.controller.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.survey.sttp.model.user.User;
import com.survey.sttp.service.user.UserService;

@RestController
@RequestMapping("/STTP/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/join")
    public int createUser(@RequestBody User user) {
        return userService.insertUser(user);
    }
}
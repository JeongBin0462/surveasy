package com.surveasy.user.service;

import com.surveasy.user.model.UserDTO;

public interface UserService {
    boolean insertUser(UserDTO userDTO);
    Integer checkUsername(String username);
    Integer checkEmail(String email);
    Integer checkPhonenumber(String phonenumber);
}
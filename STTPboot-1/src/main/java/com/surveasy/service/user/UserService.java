package com.surveasy.service.user;


import com.surveasy.model.user.UserDTO;

public interface UserService {
    boolean insertUser(UserDTO userDTO);
    Integer checkUsername(String username);
    Integer checkEmail(String email);
    Integer checkPhonenumber(String phonenumber);
}
package com.surveasy.user;

public interface UserService {
    boolean insertUser(UserDTO userDTO);
    Integer checkUsername(String username);
    Integer checkEmail(String email);
    Integer checkPhonenumber(String phonenumber);
}
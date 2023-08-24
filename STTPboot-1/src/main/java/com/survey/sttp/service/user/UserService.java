package com.survey.sttp.service.user;

import com.survey.sttp.model.user.UserDTO;

public interface UserService {
    boolean insertUser(UserDTO userDTO);
    Integer checkUsername(String username);
    Integer checkEmail(String email);
    Integer checkPhonenumber(String phonenumber);
}
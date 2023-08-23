package com.survey.sttp.service.user;

import com.survey.sttp.model.user.UserDTO;

public interface UserService {
    int insertUser(UserDTO userDTO);
}
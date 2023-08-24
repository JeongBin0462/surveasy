package com.survey.sttp.service.user;

import com.survey.sttp.model.user.UserDTO;

public interface UserService {
    boolean insertUser(UserDTO userDTO);
}
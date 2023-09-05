package com.surveasy.security;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.surveasy.user.model.User;

@Mapper
public interface UserSecurityService {
	@Select("SELECT * FROM user WHERE username=#{username}")
	Optional<User> findByusername(@Param("username") String username);
}

package com.surveasy.service.user;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.surveasy.model.user.User;

@Mapper
public interface UserRepository {
	@Select("SELECT * FROM user WHERE username=#{username}")
	Optional<User> findByusername(@Param("username") String username);
}

package com.surveasy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.surveasy.user.model.User;

@Mapper
public interface UserMapper {
	// 3-3 입력정보 구성 전 확인
		@Select("SELECT\r\n"
				+ "`userno`,\r\n"
				+ "`email`,\r\n"
				+ "`phonenumber`,\r\n"
				+ "`birth`,\r\n"
				+ "`gender`,\r\n"
				+ "`job`,\r\n"
				+ "`region`,\r\n"
				+ "`finaledu`,\r\n"
				+ "`incomelevel`\r\n"
				+ "FROM `user`\r\n"
				+ "WHERE `username`=#{username}")
		User getUserInfo(@Param("username") String username);
		
		// 회원 가입 - 유저 정보 insert
		@Insert({
	        "<script>",
	        "INSERT INTO user (username, password, email, phonenumber",
	        "<if test='birth != null'>, birth</if>",
	        "<if test='gender != null'>, gender</if>",
	        "<if test='job != null'>, job</if>",
	        "<if test='region != null'>, region</if>",
	        "<if test='finaledu != null'>, finaledu</if>",
	        "<if test='incomelevel != null'>, incomelevel</if>",
	        ") VALUES (",
	        "#{username}, #{password}, #{email}, #{phonenumber}",
	        "<if test='birth != null'>, #{birth}</if>",
	        "<if test='gender != null'>, #{gender}</if>",
	        "<if test='job != null'>, #{job}</if>",
	        "<if test='region != null'>, #{region}</if>",
	        "<if test='finaledu != null'>, #{finaledu}</if>",
	        "<if test='incomelevel != null'>, #{incomelevel}</if>",
	        ")",
	        "</script>"
	    })
		@Options(useGeneratedKeys = true, keyProperty = "userno")
	    int insertUser(User user);
		
		// 회원 가입 - 아이디 확인
		@Select("SELECT userno FROM user WHERE username=#{username}")
		Integer getHasUsername(@Param("username") String username);
		
		// 회원 가입 - 이메일 확인
		@Select("SELECT userno FROM user WHERE email=#{email}")
		Integer getHasEmail(@Param("email") String email);
		
		// 회원 가입 - 전화번호 확인
		@Select("SELECT userno FROM user WHERE phonenumber=#{phonenumber}")
		Integer getHasPhonenumber(@Param("phonenumber") String phonenumber);
		
}

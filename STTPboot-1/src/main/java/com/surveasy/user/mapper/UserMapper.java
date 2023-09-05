package com.surveasy.user.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.surveasy.user.model.Employees;
import com.surveasy.user.model.Student;
import com.surveasy.user.model.User;

@Mapper
public interface UserMapper {
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
	
	// 회원 가입 - 학생 정보 insert
	@Insert({
	    "<script>",
	    "INSERT INTO student (userno",
	    "<if test='grade != null'>, grade</if>",
	    "<if test='college != null'>, college</if>",
	    ") VALUES (",
	    "#{userno}",
	    "<if test='grade != null'>, #{grade}</if>",
	    "<if test='college != null'>, #{college}</if>",
	    ")",
	    "</script>"
	})
    int insertStudent(Student student);
	
	// 회원 가입 - 직장인 정보 insert
	@Insert({
	    "<script>",
	    "INSERT INTO employees (userno",
	    "<if test='department != null'>, department</if>",
	    "<if test='position != null'>, position</if>",
	    ") VALUES (",
	    "#{userno}",
	    "<if test='department != null'>, #{department}</if>",
	    "<if test='position != null'>, #{position}</if>",
	    ")",
	    "</script>"
	})
    int insertEmployees(Employees employees);
	
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
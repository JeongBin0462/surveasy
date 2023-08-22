package com.survey.sttp.mapper.user;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.survey.sttp.model.user.Employees;
import com.survey.sttp.model.user.Student;
import com.survey.sttp.model.user.User;

@Mapper
public interface UserMapper {
	@Insert({
        "<script>",
        "INSERT INTO user (username, password, email, phonenumber",
        "<if test='age != null'>, age</if>",
        "<if test='gender != null'>, gender</if>",
        "<if test='job != null'>, job</if>",
        "<if test='region != null'>, region</if>",
        "<if test='finaledu != null'>, finaledu</if>",
        "<if test='incomlevel != null'>, incomlevel</if>",
        ") VALUES (",
        "#{username}, #{password}, #{email}, #{phonenumber}",
        "<if test='age != null'>, #{age}</if>",
        "<if test='gender != null'>, #{gender}</if>",
        "<if test='job != null'>, #{job}</if>",
        "<if test='region != null'>, #{region}</if>",
        "<if test='finaledu != null'>, #{finaledu}</if>",
        "<if test='incomlevel != null'>, #{incomlevel}</if>",
        ")",
        "</script>"
    })
	@Options(useGeneratedKeys = true, keyProperty = "userno")
    int insertUser(User user);
	
	
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
}
package com.surveasy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.surveasy.user.model.Student;

@Mapper
public interface StudentMapper {
	@Select("SELECT * FROM student WHERE userno=#{userno}")
	Student getStudentInfo(@Param("userno") int userno);
	
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
}

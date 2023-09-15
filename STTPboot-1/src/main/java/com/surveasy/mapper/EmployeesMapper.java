package com.surveasy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.surveasy.user.model.Employees;

@Mapper
public interface EmployeesMapper {
	@Select("SELECT * FROM employees WHERE userno=#{userno}")
	Employees getEmployeesInfo(@Param("userno") int userno);
	
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
}

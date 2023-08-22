package com.survey.sttp.service.user;


import org.springframework.stereotype.Service;

import com.survey.sttp.mapper.user.UserMapper;
import com.survey.sttp.model.user.Employees;
import com.survey.sttp.model.user.Student;
import com.survey.sttp.model.user.User;

@Service
public class UserServiceImpl implements UserService {
	private final UserMapper userMapper;

	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public int insertUser(User user) {
	    int result = userMapper.insertUser(user);

	    if ("학생".equals(user.getJob())) {
	        Student student = new Student();
	        student.setUserno(user.getUserno());
	        student.setGrade(user.getGrade());
	        student.setCollege(user.getCollege());
	        userMapper.insertStudent(student);
	    }
	    
	    if ("직장인".equals(user.getJob())) {
	    	Employees employees = new Employees();
	    	employees.setUserno(user.getUserno());
	    	employees.setDepartment(user.getDepartment());
	    	employees.setPosition(user.getPosition());
	        userMapper.insertEmployees(employees);
	    }

	    return result;
	}
}

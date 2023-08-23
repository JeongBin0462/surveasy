package com.survey.sttp.service.user;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.survey.sttp.mapper.user.UserMapper;
import com.survey.sttp.model.user.Employees;
import com.survey.sttp.model.user.Student;
import com.survey.sttp.model.user.User;
import com.survey.sttp.model.user.UserDTO;

@Service
public class UserServiceImpl implements UserService {
	private final UserMapper userMapper;

	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public int insertUser(UserDTO userDTO) {
		User user = new User();
		Student student = new Student();
		Employees employees = new Employees();

		BeanUtils.copyProperties(userDTO, user);
		BeanUtils.copyProperties(userDTO, student);
		BeanUtils.copyProperties(userDTO, employees);
		
	    int result = userMapper.insertUser(user);

	    if ("학생".equals(user.getJob())) {
	        student.setUserno(user.getUserno());
	        userMapper.insertStudent(student);
	    }
	    
	    if ("직장인".equals(user.getJob())) {
	    	employees.setUserno(user.getUserno());
	        userMapper.insertEmployees(employees);
	    }
	    return result;
	}
}

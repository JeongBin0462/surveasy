package com.surveasy.service.user;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.surveasy.mapper.user.UserMapper;
import com.surveasy.model.user.Employees;
import com.surveasy.model.user.Student;
import com.surveasy.model.user.User;
import com.surveasy.model.user.UserDTO;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public boolean insertUser(UserDTO userDTO) {
		User user = new User();
		Student student = new Student();
		Employees employees = new Employees();

		BeanUtils.copyProperties(userDTO, user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
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
	    return result > 0;
	}

	@Override
	public Integer checkUsername(String username) {
		Integer count = userMapper.getUsername(username);
		if (count == null) {
			count = -1;
		}
		return count;
	}

	@Override
	public Integer checkEmail(String email) {
		Integer count = userMapper.getEmail(email);
		if (count == null) {
			count = -1;
		}
		return count;
	}

	@Override
	public Integer checkPhonenumber(String phonenumber) {
		Integer count = userMapper.getPhonenumber(phonenumber);
		if (count == null) {
			count = -1;
		}
		return count; 
	}
}

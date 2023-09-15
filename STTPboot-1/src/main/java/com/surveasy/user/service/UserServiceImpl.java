package com.surveasy.user.service;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.surveasy.mapper.EmployeesMapper;
import com.surveasy.mapper.StudentMapper;
import com.surveasy.mapper.UserMapper;
import com.surveasy.user.model.Employees;
import com.surveasy.user.model.Student;
import com.surveasy.user.model.User;
import com.surveasy.user.model.UserDTO;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	StudentMapper studentMapper;
	
	@Autowired
	EmployeesMapper employeesMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public boolean insertUser(UserDTO userDTO) {
		// db에 넣을 각 객체 생성
		User user = new User();
		Student student = new Student();
		Employees employees = new Employees();
		
		// userDTO에서 각 객체로 정보를 분리
		BeanUtils.copyProperties(userDTO, user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		BeanUtils.copyProperties(userDTO, student);
		BeanUtils.copyProperties(userDTO, employees);
		
		// 유저 정보 입력
	    int result = userMapper.insertUser(user);
	    
	    // 유저가 학생이면 학생 정보 입력
	    if ("학생".equals(user.getJob())) {
	        student.setUserno(user.getUserno());
	        studentMapper.insertStudent(student);
	    }
	    
	    // 유저가 직장인이면 직장인 정보 입력
	    if ("직장인".equals(user.getJob())) {
	    	employees.setUserno(user.getUserno());
	    	employeesMapper.insertEmployees(employees);
	    }
	    return result > 0;
	}

	@Override
	public Integer checkUsername(String username) {
		Integer count = userMapper.getHasUsername(username);
		if (count == null) {
			count = -1;
		}
		return count;
	}

	@Override
	public Integer checkEmail(String email) {
		Integer count = userMapper.getHasEmail(email);
		if (count == null) {
			count = -1;
		}
		return count;
	}

	@Override
	public Integer checkPhonenumber(String phonenumber) {
		Integer count = userMapper.getHasPhonenumber(phonenumber);
		if (count == null) {
			count = -1;
		}
		return count; 
	}
}

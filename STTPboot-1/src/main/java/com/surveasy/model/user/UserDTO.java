package com.surveasy.model.user;

import java.time.LocalDate;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private int userno;
	@Pattern(regexp = "^[가-힣A-Za-z0-9]{5,20}$"
			, message = "사용자 이름은 한글, 영어, 숫자를 이용하여 5~20자로 구성되어야 합니다.")
	private String username;
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{5,15}$"
			, message = "비밀번호는 5~15자의 길이를 가져야 하며, 특수문자, 숫자, 소문자, 대문자를 모두 포함해야 합니다.")
	private String password;
	private String passwordCheck;
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
			, message = "유효한 이메일 형식이 아닙니다.")
	private String email;
	@Pattern(regexp = "^010[0-9]{8}$"
			, message = "유효한 전화번호 형식이 아닙니다.")
	private String phonenumber;
	private LocalDate birth;
	private Boolean gender;
	private String job;
	private String region;
	private String finaledu;
	private String incomlevel;
	
	
	private Integer grade;
	private String college;
	
	
	private String department;
	private String position;
	
}
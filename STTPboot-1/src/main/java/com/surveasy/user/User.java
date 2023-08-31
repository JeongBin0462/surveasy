package com.surveasy.user;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private int userno;
	private String username;
	private String password;
	private String email;
	private String phonenumber;
	private LocalDate birth;
	private Boolean gender;
	private String job;
	private String region;
	private String finaledu;
	private String incomlevel;

}

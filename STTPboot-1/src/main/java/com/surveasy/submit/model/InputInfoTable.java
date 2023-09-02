package com.surveasy.submit.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputInfoTable {
	private Integer user_survey_no;
	private String email;
	private String phonenumber;
	private LocalDate birth;
	private Integer gender;
	private String department;
	private String position;
	private Integer grade;
	private String college;
	private String region;
	private String finaledu;
	private String incomelevel;
}

package com.surveasy.submit.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Require {
	private Integer surveyno;
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

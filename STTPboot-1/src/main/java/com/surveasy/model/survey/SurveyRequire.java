package com.surveasy.model.survey;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyRequire {
	private int surveyno;
	private String surveysubject;
	private Integer period;
	private boolean email_option;
	private boolean phone_option;
	private boolean age_option;
	private boolean gender_option;
	private String surveytarget;
	private boolean department_option;
	private boolean position_option;
	private boolean grade_option;
	private boolean college_option;
	private boolean region_option;
	private boolean finaledu_option;
	private boolean incomelevel_option;
}

package com.surveasy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.surveasy.survey.model.SurveyRequire;

@Mapper
public interface SurveyrequireMapper {

	// surveyrequire 테이블에 넣음
		@Insert({
			    "<script>",
			    "INSERT INTO surveyrequire (surveyno, period",
			    ", subject",
			    ", email_option, phone_option, age_option, gender_option",
			    ", target",
			    ", department_option, position_option, grade_option, college_option, region_option, finaledu_option, incomelevel_option",
			    ") VALUES (#{surveyno}, #{period}",
			    ", #{subject}",
			    ", #{email_option}, #{phone_option}, #{age_option}, #{gender_option}",
			    ", #{target}",
			    ", #{department_option}, #{position_option}, #{grade_option}, #{college_option}, #{region_option}, #{finaledu_option}, #{incomelevel_option}",
			    ")",
			    "ON DUPLICATE KEY UPDATE",
			    "period = VALUES(period),",
			    "subject = VALUES(subject),",
			    "email_option = VALUES(email_option),",
			    "phone_option = VALUES(phone_option),",
			    "age_option = VALUES(age_option),",
			    "gender_option = VALUES(gender_option),",
			    "target = VALUES(target),",
			    "department_option = VALUES(department_option),",
			    "position_option = VALUES(position_option),",
			    "grade_option = VALUES(grade_option),",
			    "college_option = VALUES(college_option),",
			    "region_option = VALUES(region_option),",
			    "finaledu_option = VALUES(finaledu_option),",
			    "incomelevel_option = VALUES(incomelevel_option)",
			    "</script>"
		})
		int insertSurveyRequire(SurveyRequire surveyRequire);
	
}

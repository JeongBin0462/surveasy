package com.surveasy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
	
		// 3-1 주제로 설문지번호 리스트 반환
		@Select("SELECT surveyno FROM surveyrequire WHERE subject=#{subject}")
		List<Integer> getSurveynoBySubject(String subject);
		
		// 3-3 설문지 생성을 위한 설문지 필수입력정보
		@Select("SELECT * FROM surveyrequire WHERE surveyno=#{surveyno}")
		SurveyRequire getSurveyRequire(@Param("surveyno") int surveyno);
		
		// question의 surveyno 리스트
		@Select("SELECT surveyno FROM surveyrequire WHERE subject=#{subject}")
		List<Integer> getSurveynoList(@Param("subject") String subject);
		
		
}

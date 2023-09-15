package com.surveasy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.surveasy.submit.model.UserSurvey;

@Mapper
public interface UsersurveyMapper {

	// 3-3 설문 중복 참여 방지
	@Select("SELECT user_survey_no FROM user_survey WHERE userno=#{userno} AND surveyno=#{surveyno}")
	Integer getUserSurveyBySurveyno(@Param("userno") int userno, @Param("surveyno") int surveyno);

	// user_survey 테이블
	@Insert({ "<script>", "INSERT INTO user_survey (userno, surveyno) VALUES (#{userno}, #{surveyno})", "</script>" })
	@Options(useGeneratedKeys = true, keyProperty = "user_survey_no")
	int insertUserSurvey(UserSurvey userSurvey);

	
}

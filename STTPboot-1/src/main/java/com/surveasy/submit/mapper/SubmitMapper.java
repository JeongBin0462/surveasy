package com.surveasy.submit.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.surveasy.submit.model.UserSurvey;

@Mapper
public interface SubmitMapper {

	@Insert({ "<script>"
			, "INSERT INTO user_survey (userno, surveyno) VALUES (#{userno}, #{surveyno})"
			, "</script>" })
	@Options(useGeneratedKeys = true, keyProperty = "user_survey_no")
	int insertUserSurvey(UserSurvey userSurvey);

}

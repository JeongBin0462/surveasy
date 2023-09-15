package com.surveasy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.surveasy.survey.model.SurveyOption;

@Mapper
public interface SurveyoptionMapper {

	
	// survey_option 테이블 입력
		@Insert({
		    "<script>",
		    "INSERT INTO survey_option (surveyno",
		    ", is_public_survey",
		    ", require_login",
		    ", is_public_result",
		    ", show_progress",
		    ") VALUES (",
		    "#{surveyno}",
		    ", #{is_public_survey}",
		    ", #{require_login}",
		    ", #{is_public_result}",
		    ", #{show_progress}",
		    ")",
		    "ON DUPLICATE KEY UPDATE",
		    " is_public_survey = VALUES(is_public_survey),",
		    " require_login = VALUES(require_login),",
		    " is_public_result = VALUES(is_public_result),",
		    " show_progress = VALUES(show_progress)",
		    "</script>"
		})
		int insertSurveyOption(SurveyOption surveyOption);
}

package com.surveasy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.surveasy.survey.model.SurveyOption;

@Mapper
public interface SurveyoptionMapper {

	// survey_option 테이블 입력
	@Insert({ "<script>", "INSERT INTO survey_option (surveyno", ", is_public_survey", ", require_login",
			", is_public_result", ", show_progress", ") VALUES (", "#{surveyno}", ", #{is_public_survey}",
			", #{require_login}", ", #{is_public_result}", ", #{show_progress}", ")", "ON DUPLICATE KEY UPDATE",
			" is_public_survey = VALUES(is_public_survey),", " require_login = VALUES(require_login),",
			" is_public_result = VALUES(is_public_result),", " show_progress = VALUES(show_progress)", "</script>" })
	int insertSurveyOption(SurveyOption surveyOption);

	// 3-1 설문지no로 설문지 옵션
	@Select("SELECT surveyno FROM survey_option WHERE surveyno=#{surveyno} AND is_public_survey = 0")
	Integer getSurveyOptionIsPublic(int surveyno);

	// 3-3 설문지 생성을 위한 설문지 옵션
	@Select("SELECT * FROM survey_option WHERE surveyno=#{surveyno}")
	SurveyOption getSurveyOption(@Param("surveyno") int surveyno);
}

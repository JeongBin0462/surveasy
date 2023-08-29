package com.survey.sttp.mapper.survey;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.survey.sttp.model.survey.AnswerCheck;
import com.survey.sttp.model.survey.AnswerRadio;
import com.survey.sttp.model.survey.SurveyOption;
import com.survey.sttp.model.survey.SurveyPaper;
import com.survey.sttp.model.survey.SurveyQuestion;
import com.survey.sttp.model.survey.SurveyRequire;

@Mapper
public interface SurveyMapper {
	@Select("SELECT * FROM surveypaper WHERE surveyno=#{surveyno}")
	SurveyPaper getSurvey(@Param("surveyno") int surveyno);
	
	@Select("SELECT * FROM survey_option WHERE surveyno=#{surveyno}")
	SurveyOption getSurveyOption(@Param("surveyno") int surveyno);
	
	@Select("SELECT * FROM surveyrequire WHERE surveyno=#{surveyno}")
	SurveyRequire getSurveyRequire(@Param("surveyno") int surveyno);
	
	@Select("SELECT * FROM question WHERE surveyno=#{surveyno}")
	List<SurveyQuestion> getQuestion(@Param("surveyno") int surveyno);
	
	@Select("SELECT * FROM answer_checkbox WHERE questionno=#{questionno}")
	AnswerCheck getCheckbox(@Param("questionno") int questionno);
	
	@Select("SELECT * FROM answer_radio WHERE questionno=#{questionno}")
	AnswerRadio getRadio(@Param("questionno") int questionno);
}

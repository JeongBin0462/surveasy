package com.surveasy.survey.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.surveasy.submit.model.UserSurvey;
import com.surveasy.survey.model.Answers;
import com.surveasy.survey.model.SurveyOption;
import com.surveasy.survey.model.SurveyPaper;
import com.surveasy.survey.model.SurveyQuestion;
import com.surveasy.survey.model.SurveyRequire;

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
	
	@Select("SELECT * FROM answers WHERE questionno=#{questionno}")
	Answers getAnswer(@Param("questionno") int questionno);
	
	@Select("SELECT * FROM surveypaper WHERE link=#{link}")
	SurveyPaper getSurveyByLink(@Param("link") String link);
}

package com.surveasy.survey.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.surveasy.survey.model.Answers;
import com.surveasy.survey.model.SurveyOption;
import com.surveasy.survey.model.SurveyPaper;
import com.surveasy.survey.model.SurveyQuestion;
import com.surveasy.survey.model.SurveyRequire;
import com.surveasy.user.model.Employees;
import com.surveasy.user.model.Student;
import com.surveasy.user.model.User;

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
	
	@Select("SELECT user_survey_no FROM user_survey WHERE userno=#{userno} AND surveyno=#{surveyno}")
	Integer getUserSurvey(@Param("userno") int userno, @Param("surveyno") int surveyno);
	
	@Select("SELECT\r\n"
			+ "`userno`,\r\n"
			+ "`email`,\r\n"
			+ "`phonenumber`,\r\n"
			+ "`birth`,\r\n"
			+ "`gender`,\r\n"
			+ "`job`,\r\n"
			+ "`region`,\r\n"
			+ "`finaledu`,\r\n"
			+ "`incomelevel`\r\n"
			+ "FROM `user`\r\n"
			+ "WHERE `username`=#{username}")
	User getUser(@Param("username") String username);
	
	@Select("SELECT * FROM student WHERE userno=#{userno}")
	Student getStudent(@Param("userno") int userno);
	
	@Select("SELECT * FROM employees WHERE userno=#{userno}")
	Employees getEmployees(@Param("userno") int userno);
}

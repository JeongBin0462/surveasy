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
	// 3-1 설문지 목록
	@Select("SELECT * FROM surveypaper WHERE deadline > now()")
	List<SurveyPaper> getSurveyList();
	
	// 3-1 설문지no로 설문지 옵션
	@Select("SELECT surveyno FROM survey_option WHERE surveyno=#{surveyno} AND is_public_survey = 0")
	Integer getSurveyOptionIsPublic(int surveyno);
	
	// 3-1 주제로 설문지번호 리스트 반환
	@Select("SELECT surveyno FROM surveyrequire WHERE subject=#{subject}")
	List<Integer> getSurveynoBySubject(String subject);
	
	// 3-2, 3-3 링크를 통해 접근 -> 어떤 설문지인지
	@Select("SELECT * FROM surveypaper WHERE link=#{link}")
	SurveyPaper getSurveyByLink(@Param("link") String link);
	
	// 3-3 설문지 생성을 위한 설문지 옵션
	@Select("SELECT * FROM survey_option WHERE surveyno=#{surveyno}")
	SurveyOption getSurveyOption(@Param("surveyno") int surveyno);
	
	// 3-3 설문지 생성을 위한 설문지 필수입력정보
	@Select("SELECT * FROM surveyrequire WHERE surveyno=#{surveyno}")
	SurveyRequire getSurveyRequire(@Param("surveyno") int surveyno);
	
	// 3-3 설문지 생성을 위한 설문지 문항정보 리스트
	@Select("SELECT * FROM question WHERE surveyno=#{surveyno}")
	List<SurveyQuestion> getQuestion(@Param("surveyno") int surveyno);
	
	// 3-3 설문지 생성을 위한 설문지 문항들
	@Select("SELECT * FROM answers WHERE questionno=#{questionno}")
	Answers getAnswer(@Param("questionno") int questionno);
	
	// 3-3 설문참여 정보 불러오기
	@Select("SELECT user_survey_no FROM user_survey WHERE userno=#{userno} AND surveyno=#{surveyno}")
	Integer getUserSurveyBySurveyno(@Param("userno") int userno, @Param("surveyno") int surveyno);
	
	// 3-3 입력정보 구성 전 확인
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
	User getUserInfo(@Param("username") String username);
	
	@Select("SELECT * FROM student WHERE userno=#{userno}")
	Student getStudentInfo(@Param("userno") int userno);
	
	@Select("SELECT * FROM employees WHERE userno=#{userno}")
	Employees getEmployeesInfo(@Param("userno") int userno);

}

package com.surveasy.survey.service;

import java.util.List;

import com.surveasy.survey.model.Answers;
import com.surveasy.survey.model.SurveyOption;
import com.surveasy.survey.model.SurveyPaper;
import com.surveasy.survey.model.SurveyQuestion;
import com.surveasy.survey.model.SurveyRequire;

public interface SurveyService {
	// 설문 불러오기
	SurveyPaper getSurveyPaper(int surveyno);
	SurveyOption getSurveyOption(int surveyno);
	SurveyRequire getSurveyRequire(int surveyno);
	List<SurveyQuestion> getSurveyQuestion(int surveyno);
	List<Answers> getAnswers(List<SurveyQuestion> list);
	
	// 답안 저장하기
	int insertUserSurvey(int userno, int surveyno);
	int insertEssay(int user_survey_no, int questionno, Answers answers);
	int insertRadio(int user_survey_no, int questionno, Answers answers);
	int insertCheck(int user_survey_no, int questionno, Answers answers);
}

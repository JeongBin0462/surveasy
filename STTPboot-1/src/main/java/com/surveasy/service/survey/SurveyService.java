package com.surveasy.service.survey;

import java.util.List;

import com.surveasy.model.survey.Answers;
import com.surveasy.model.survey.SurveyOption;
import com.surveasy.model.survey.SurveyPaper;
import com.surveasy.model.survey.SurveyQuestion;
import com.surveasy.model.survey.SurveyRequire;

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

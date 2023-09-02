package com.surveasy.submit.service;

import com.surveasy.survey.model.Answers;

public interface SubmitService {
	int insertUserSurvey(int userno, int surveyno);
	int insertEssay(int user_survey_no, int questionno, Answers answers);
	int insertRadio(int user_survey_no, int questionno, Answers answers);
	int insertCheck(int user_survey_no, int questionno, Answers answers);
}	

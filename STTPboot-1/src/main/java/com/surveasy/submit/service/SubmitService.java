package com.surveasy.submit.service;

import java.util.List;

import com.surveasy.submit.model.Require;
import com.surveasy.submit.model.SurveyAnswers;
import com.surveasy.submit.model.UserSurvey;
import com.surveasy.survey.model.SurveyPaper;

public interface SubmitService {
	UserSurvey insertUserSurvey(int surveyno);
	int insertInputInfo(int user_survey_no, Require require);
	int insertAnswers(int user_survey_no, List<SurveyAnswers> list);
	List<SurveyPaper> getSurveyPaperList(String subject, int surveyno);
}	

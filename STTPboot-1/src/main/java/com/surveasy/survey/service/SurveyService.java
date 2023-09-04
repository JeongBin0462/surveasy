package com.surveasy.survey.service;

import java.util.List;

import com.surveasy.survey.model.Answers;
import com.surveasy.survey.model.SurveyOption;
import com.surveasy.survey.model.SurveyPaper;
import com.surveasy.survey.model.SurveyQuestion;
import com.surveasy.survey.model.SurveyRequire;
import com.surveasy.user.model.User;
import com.surveasy.user.model.UserDTO;

public interface SurveyService {
	List<SurveyPaper> getSurveyPaperList();
	SurveyPaper getSurveyPaper(int surveyno);
	SurveyPaper getSurveyPaperByLink(String link);
	SurveyOption getSurveyOption(int surveyno);
	SurveyRequire getSurveyRequire(int surveyno);
	List<SurveyQuestion> getSurveyQuestion(int surveyno);
	List<Answers> getAnswers(List<SurveyQuestion> list);
	boolean getUserSurvey(int surveyno, int userno);
	UserDTO getUserInfo();
}

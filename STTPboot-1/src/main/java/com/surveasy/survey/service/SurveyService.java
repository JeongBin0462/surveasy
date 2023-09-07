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
	List<SurveyPaper> getSurveyPaperList(String subject, String sortOption, String search, int currentPage);
	int getTotalSurveyCount(String subject, String sort, String search);
	SurveyPaper getSurveyPaperByLink(String link);
	SurveyOption getSurveyOption(int surveyno);
	SurveyRequire getSurveyRequire(int surveyno);
	List<SurveyQuestion> getSurveyQuestion(int surveyno);
	List<Answers> getAnswers(List<SurveyQuestion> list);
	boolean getUserSurvey(int surveyno);
	boolean surveyMine(int surveyno);
	UserDTO getUserInfo();
	boolean checkBookmark(int surveyno);
	int insertBookmark(int surveyno);
	int countBookmark(int surveyno);
	int countAnswers(int surveyno);
}

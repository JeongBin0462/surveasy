package com.surveasy.service.survey;

import java.util.List;

import com.surveasy.model.survey.Answers;
import com.surveasy.model.survey.SurveyOption;
import com.surveasy.model.survey.SurveyPaper;
import com.surveasy.model.survey.SurveyQuestion;
import com.surveasy.model.survey.SurveyRequire;

public interface SurveyService {
	SurveyPaper getSurveyPaper(int surveyno);
	SurveyOption getSurveyOption(int surveyno);
	SurveyRequire getSurveyRequire(int surveyno);
	List<SurveyQuestion> getSurveyQuestion(int surveyno);
	List<Answers> getAnswers(List<SurveyQuestion> list);
}

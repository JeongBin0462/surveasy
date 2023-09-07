package com.surveasy.form.service;

import com.surveasy.form.model.SurveyDTO;

public interface FormService {
	Integer insertSurvey(SurveyDTO surveyDTO);
	
	boolean tempSaveNum();

	String getLinkOfSurveyNo(Integer surveyno);
}

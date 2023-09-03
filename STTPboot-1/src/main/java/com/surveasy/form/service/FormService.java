package com.surveasy.form.service;

import java.util.List;

import com.surveasy.form.model.SurveyDTO;

public interface FormService {
	boolean insertSurvey(SurveyDTO surveyDTO);

	
	boolean tempSaveNum();
}

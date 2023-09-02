package com.surveasy.submit.service;

import org.springframework.stereotype.Service;

import com.surveasy.survey.model.Answers;

@Service
public class SubmitServiceImpl implements SubmitService {

	@Override
	public int insertUserSurvey(int userno, int surveyno) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertEssay(int user_survey_no, int questionno, Answers answers) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertRadio(int user_survey_no, int questionno, Answers answers) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertCheck(int user_survey_no, int questionno, Answers answers) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

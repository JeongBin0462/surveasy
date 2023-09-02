package com.surveasy.survey.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surveasy.survey.mapper.SurveyMapper;
import com.surveasy.survey.model.Answers;
import com.surveasy.survey.model.SurveyOption;
import com.surveasy.survey.model.SurveyPaper;
import com.surveasy.survey.model.SurveyQuestion;
import com.surveasy.survey.model.SurveyRequire;

@Service
public class SurveyServiceImpl implements SurveyService {
	@Autowired 
	SurveyMapper surveyMapper;

	@Override
	public SurveyPaper getSurveyPaper(int surveyno) {
		SurveyPaper surveyPaper = surveyMapper.getSurvey(surveyno);
		System.out.println(surveyPaper.toString());
		System.out.println("--------------------");
		return surveyPaper;
	}

	@Override
	public SurveyOption getSurveyOption(int surveyno) {
		SurveyOption surveyOption = surveyMapper.getSurveyOption(surveyno);
		System.out.println(surveyOption.toString());
		System.out.println("--------------------");
		return surveyOption;
	}

	@Override
	public SurveyRequire getSurveyRequire(int surveyno) {
		SurveyRequire surveyRequire = surveyMapper.getSurveyRequire(surveyno);
		System.out.println(surveyRequire.toString());
		System.out.println("--------------------");
		return surveyRequire;
	}

	@Override
	public List<SurveyQuestion> getSurveyQuestion(int surveyno) {
		List<SurveyQuestion> surveyQuestion = surveyMapper.getQuestion(surveyno);
		System.out.println(surveyQuestion.toString());
		System.out.println("--------------------");
		return surveyQuestion;
	}

	@Override
	public List<Answers> getAnswers(List<SurveyQuestion> list) {
		Answers answers;
		List<Answers> answersList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			String type = list.get(i).getAnswer_types();
			int questionno = list.get(i).getQuestionno();
			System.out.println(type);
			
			if (!type.equals("서술형")) {
				answers = surveyMapper.getAnswer(questionno);
				
				System.out.println(answers);
				answersList.add(answers);
			}
		}
		System.out.println(answersList.toString());
		return answersList;
	}

	@Override
	public SurveyPaper getSurveyPaperByLink(String link) {
		return surveyMapper.getSurveyByLink(link);
	}
}

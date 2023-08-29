package com.surveasy.service.survey;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surveasy.mapper.survey.SurveyMapper;
import com.surveasy.model.survey.AnswerCheck;
import com.surveasy.model.survey.SurveyAnswers;
import com.surveasy.model.survey.SurveyOption;
import com.surveasy.model.survey.SurveyPaper;
import com.surveasy.model.survey.SurveyQuestion;
import com.surveasy.model.survey.SurveyRequire;

@Service
public class SurveyServiceImpl implements SurveyService {
	@Autowired 
	SurveyMapper surveyMapper;
	
	private SurveyAnswers answers;
	
	public SurveyServiceImpl() {
	    this.answers = new SurveyAnswers();
	}
	
	@Override
	public boolean getSurveyInfo(int surveyno) {
		SurveyPaper surveyPaper = surveyMapper.getSurvey(surveyno);
		System.out.println(surveyPaper.toString());
		System.out.println("--------------------");
		
		SurveyOption surveyOption = surveyMapper.getSurveyOption(surveyno);
		System.out.println(surveyOption.toString());
		System.out.println("--------------------");
		
		SurveyRequire surveyRequire = surveyMapper.getSurveyRequire(surveyno);
		System.out.println(surveyRequire.toString());
		System.out.println("--------------------");
		
		List<SurveyQuestion> surveyQuestion = surveyMapper.getQuestion(surveyno);
		System.out.println(surveyQuestion.toString());
		System.out.println("--------------------");
		
		AnswerCheck answerCheck;
		ArrayList<Object> list = new ArrayList<>();
		for (int i = 0; i < surveyQuestion.size(); i++) {
			String type = surveyQuestion.get(i).getAnswer_types();
			int questionno = surveyQuestion.get(i).getQuestionno();
			System.out.println(type);
			
			if (!type.equals("서술형")) {
				answerCheck = surveyMapper.getCheckbox(questionno);
				list.add(answerCheck);
			}
		}
		answers.setAnswers(list);
		System.out.println(answers.toString());
		
		return false;
	}

}

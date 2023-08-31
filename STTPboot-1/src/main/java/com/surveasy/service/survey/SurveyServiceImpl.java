package com.surveasy.service.survey;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surveasy.mapper.survey.SurveyMapper;
import com.surveasy.model.survey.Answers;
import com.surveasy.model.survey.SurveyOption;
import com.surveasy.model.survey.SurveyPaper;
import com.surveasy.model.survey.SurveyQuestion;
import com.surveasy.model.survey.SurveyRequire;

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

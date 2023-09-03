package com.surveasy.survey.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.surveasy.survey.mapper.SurveyMapper;
import com.surveasy.survey.model.Answers;
import com.surveasy.survey.model.SurveyOption;
import com.surveasy.survey.model.SurveyPaper;
import com.surveasy.survey.model.SurveyQuestion;
import com.surveasy.survey.model.SurveyRequire;
import com.surveasy.user.model.Employees;
import com.surveasy.user.model.Student;
import com.surveasy.user.model.User;
import com.surveasy.user.model.UserDTO;

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

	@Override
	public boolean getUserSurvey(int surveyno, int userno) {
		Integer userSurveyNo = surveyMapper.getUserSurvey(surveyno, userno);
		System.out.println("userSurveyNo : " + userSurveyNo);
		if (userSurveyNo != null) {
			return false;
		}
		return true;
	}

	@Override
	public UserDTO getUserInfo() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		UserDTO userInfo = new UserDTO();
		User user = surveyMapper.getUser(username);
		BeanUtils.copyProperties(user, userInfo);
		int userno = user.getUserno();

		if (user.getJob() != null) {
			if (user.getJob().equals("학생")) {
				Student student = surveyMapper.getStudent(userno);
				BeanUtils.copyProperties(student, userInfo);
			}
			if (user.getJob().equals("직장인")) {
				Employees employees = surveyMapper.getEmployees(userno);
				BeanUtils.copyProperties(employees, userInfo);
			}
		}

		return userInfo;
	}
}

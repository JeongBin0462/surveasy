package com.surveasy.survey.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.surveasy.security.UserSecurityServiceImpl;
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
	
	@Autowired
	UserSecurityServiceImpl userSecurityServiceImpl;

	// 3-1에 사용되는 전체 설문 목록
	@Override
	public List<SurveyPaper> getSurveyPaperList() {
		List<SurveyPaper> list = surveyMapper.getSurveyList();
		LocalDateTime now = LocalDateTime.now();

		List<SurveyPaper> removeList = new ArrayList<>();

		for (SurveyPaper surveyPaper : list) {
			LocalDateTime deadline = surveyPaper.getDeadline();

			if (deadline == null || now.isAfter(deadline)) {
				removeList.add(surveyPaper);
			}
		}
		list.removeAll(removeList);
		System.out.println(list.toString());

		removeList = new ArrayList<>();
		List<Integer> removeListPrivate = new ArrayList<>();
		for (SurveyPaper surveyPaper : list) {
			int surveyno = surveyPaper.getSurveyno();
			removeListPrivate.add(surveyMapper.getSurveyOptionIsPublic(surveyno));
		}

		for (SurveyPaper surveyPaper : list) {
			if (removeListPrivate == null) {
				break;
			}
			for (Integer numbers : removeListPrivate) {
				if (surveyPaper.getSurveyno() == numbers) {
					removeList.add(surveyPaper);
				}
			}
		}
		list.removeAll(removeList);

		return list;
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
	
	// 링크를 통한 설문지 정보 가져오기
	@Override
	public SurveyPaper getSurveyPaperByLink(String link) {
		return surveyMapper.getSurveyByLink(link);
	}
	
	// 설문 중복 참여 방지를 위한 설문 참여 정보
	@Override
	public boolean getUserSurvey(int surveyno) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		// 현재 userno
		Integer userno = userSecurityServiceImpl.getUserno(username);
		Integer userSurveyno = surveyMapper.getUserSurveyBySurveyno(userno, surveyno);
		
		System.out.println("surveyno: " + surveyno);
		System.out.println("userno: " + userno);
		System.out.println("userSurveyNo : " + userSurveyno);
		if (userSurveyno != null) {
			return false;
		}
		return true;
	}
	
	// 기본 데이터 미리 입력을 위해 회원가입 시 입력된 정보 불러오기
	@Override
	public UserDTO getUserInfo() {
		// 시큐리티를 통한 유저아이디 불러오기
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		
		// 유저 정보 중 필요한 정보만 객체로 생성
		UserDTO userInfo = new UserDTO();
		User user = surveyMapper.getUserInfo(username);
		BeanUtils.copyProperties(user, userInfo);
		int userno = user.getUserno();
		if (user.getJob() != null) {
			if (user.getJob().equals("학생")) {
				Student student = surveyMapper.getStudentInfo(userno);
				BeanUtils.copyProperties(student, userInfo);
			}
			if (user.getJob().equals("직장인")) {
				Employees employees = surveyMapper.getEmployeesInfo(userno);
				BeanUtils.copyProperties(employees, userInfo);
			}
		}
		return userInfo;
	}
}

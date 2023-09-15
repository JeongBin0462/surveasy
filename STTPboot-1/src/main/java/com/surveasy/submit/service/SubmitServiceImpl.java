package com.surveasy.submit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.surveasy.mapper.InputinfoMapper;
import com.surveasy.mapper.SurveypaperMapper;
import com.surveasy.mapper.SurveyrequireMapper;
import com.surveasy.mapper.UseranswersMapper;
import com.surveasy.mapper.UsersurveyMapper;
import com.surveasy.security.UserSecurityServiceImpl;
import com.surveasy.submit.model.InputInfoTable;
import com.surveasy.submit.model.Require;
import com.surveasy.submit.model.SurveyAnswers;
import com.surveasy.submit.model.UserAnswers;
import com.surveasy.submit.model.UserSurvey;
import com.surveasy.survey.model.SurveyPaper;

@Service
public class SubmitServiceImpl implements SubmitService {

	@Autowired
	UserSecurityServiceImpl userSecurityServiceImpl;

	@Autowired
	UsersurveyMapper usersurveyMapper;
	
	@Autowired
	InputinfoMapper inputinfoMapper;
	
	@Autowired
	UseranswersMapper useranswerMapper;
	
	@Autowired
	SurveyrequireMapper surveyrequireMapper;
	
	@Autowired
	SurveypaperMapper surveypaperMapper;
	
	// 설문지 submit
	@Override
	public UserSurvey insertUserSurvey(int surveyno) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		// 현재 userno
		Integer userNo = userSecurityServiceImpl.getUserno(username);

		// user_survey 객체
		UserSurvey userSurvey = new UserSurvey();
		userSurvey.setUserno(userNo);
		userSurvey.setSurveyno(surveyno);

		usersurveyMapper.insertUserSurvey(userSurvey);

		return userSurvey;
	}

	@Override
	public int insertInputInfo(int user_survey_no, Require require) {
		InputInfoTable inputInfo = new InputInfoTable();
		BeanUtils.copyProperties(require, inputInfo);
		inputInfo.setUser_survey_no(user_survey_no);

		return inputinfoMapper.insertInputInfo(inputInfo);
	}

	@Override
	public int insertAnswers(int user_survey_no, List<SurveyAnswers> list) {
		int insertCount = 0;
		for (int i = 0; i < list.size(); i++) {
			UserAnswers userAnswers = convertToUserAnswers(user_survey_no, list.get(i));
			insertCount += useranswerMapper.insertUserAnswer(userAnswers);
		}
		return insertCount;
	}

	private UserAnswers convertToUserAnswers(int user_survey_no, SurveyAnswers surveyAnswer) {
		UserAnswers userAnswers = new UserAnswers();
		userAnswers.setUser_survey_no(user_survey_no);
		userAnswers.setQuestionno(surveyAnswer.getQuestionno());

		if (surveyAnswer.getAnswerMap() != null) {
			String[] answers = surveyAnswer.getAnswerMap().values().toArray(new String[0]);
			for (int i = 0; i < answers.length; i++) {
				switch (i) {
				case 0:
					userAnswers.setUseranswer1(answers[i]);
					break;
				case 1:
					userAnswers.setUseranswer2(answers[i]);
					break;
				case 3:
					userAnswers.setUseranswer3(answers[i]);
					break;
				case 4:
					userAnswers.setUseranswer4(answers[i]);
					break;
				case 5:
					userAnswers.setUseranswer5(answers[i]);
					break;
				case 6:
					userAnswers.setUseranswer6(answers[i]);
					break;
				case 7:
					userAnswers.setUseranswer7(answers[i]);
					break;
				case 8:
					userAnswers.setUseranswer8(answers[i]);
					break;
				case 9:
					userAnswers.setUseranswer9(answers[i]);
					break;
				case 10:
					userAnswers.setUseranswer10(answers[i]);
					break;
				case 11:
					userAnswers.setUseranswer11(answers[i]);
					break;
				case 12:
					userAnswers.setUseranswer12(answers[i]);
					break;
				case 13:
					userAnswers.setUseranswer13(answers[i]);
					break;
				case 14:
					userAnswers.setUseranswer14(answers[i]);
					break;
				case 15:
					userAnswers.setUseranswer15(answers[i]);
					break;
				case 16:
					userAnswers.setUseranswer16(answers[i]);
					break;
				case 17:
					userAnswers.setUseranswer17(answers[i]);
					break;
				case 18:
					userAnswers.setUseranswer18(answers[i]);
					break;
				case 19:
					userAnswers.setUseranswer19(answers[i]);
					break;
				case 20:
					userAnswers.setUseranswer20(answers[i]);
					break;
				}
			}
		}

		return userAnswers;
	}
	
	// 리스트 랜덤 index 번호
	public List<Integer> getRandomElements(List<Integer> surveynoList, int surveyno) {
		// 3보다 작으면 바로 리턴
	    if (3 >= surveynoList.size()) {
	        return surveynoList;
	    }
	    List<Integer> randomNumbers = new ArrayList<>();
	    Random random = new Random();

	    while (randomNumbers.size() < 3) {
	        int randomIndex = random.nextInt(surveynoList.size());
	        Integer indexNum = surveynoList.get(randomIndex);

	        if (!randomNumbers.contains(indexNum) && indexNum != surveyno) {
	            randomNumbers.add(indexNum);
	        }
	    }
	    return randomNumbers;
	}

	@Override
	public List<SurveyPaper> getSurveyPaperList(String subject, int surveyno) {
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    String username;
	    if (principal instanceof UserDetails) {
	        username = ((UserDetails) principal).getUsername();
	    } else {
	        username = principal.toString();
	    }
	    // 현재 userno
	    Integer userNo = userSecurityServiceImpl.getUserno(username);

	    List<SurveyPaper> surveyPaperList = new ArrayList<>();
	    // 같은 주제의 설문지no 리스트
	    List<Integer> surveynoList = surveyrequireMapper.getSurveynoList(subject);

	    // 조건에 맞는 설문 필터링
	    List<Integer> filteredSurveynoList = new ArrayList<>();
	    for (Integer surveynoFilter : surveynoList) {
	        SurveyPaper surveyPaper = surveypaperMapper.getSurveyPaperBySurveyno(userNo, surveynoFilter);
	        if (surveyPaper != null) {
	            filteredSurveynoList.add(surveynoFilter);
	        }
	    }

	    // 필터링된 설문 중에서 랜덤하게 3개 선택
	    List<Integer> randomNumbers = getRandomElements(filteredSurveynoList, 3);

	    for (int i = 0; i < randomNumbers.size(); i++) {
	        System.out.println("userno : " + userNo);
	        System.out.println("surveyno : " + randomNumbers.get(i));
	        System.out.println("---------");

	        SurveyPaper surveyPaper = surveypaperMapper.getSurveyPaperBySurveyno(userNo, randomNumbers.get(i));
	        if (surveyPaper != null) {
	            surveyPaperList.add(surveyPaper);
	        }
	    }
	    return surveyPaperList;
	}
}
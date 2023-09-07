package com.surveasy.form.service;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.surveasy.form.RandomStringGenerator;
import com.surveasy.form.mapper.FormMapper;
import com.surveasy.form.model.Form;
import com.surveasy.form.model.Question;
import com.surveasy.form.model.Survey;
import com.surveasy.form.model.SurveyDTO;
import com.surveasy.security.UserSecurityServiceImpl;
import com.surveasy.survey.mapper.SurveyMapper;
import com.surveasy.survey.model.Answers;
import com.surveasy.survey.model.SurveyOption;
import com.surveasy.survey.model.SurveyQuestion;
import com.surveasy.survey.model.SurveyRequire;

@Service
public class FormServiceImpl implements FormService {

	@Autowired
	UserSecurityServiceImpl userSecurityServiceImpl;

	@Autowired
	FormMapper formMapper;

	@Autowired
	SurveyMapper surveyMapper;
	
	@Override
	public String getLinkOfSurveyNo(Integer surveyno) {
		return formMapper.getLink(surveyno);
	}

	@Transactional
	@Override
	public boolean tempSaveNum() {
		Integer userNo = getCurrentUserNo();
		int saveCount = formMapper.countTempSaveNum(userNo);
		if (saveCount >= 5) { 
			return false;
		} else {
			return true;
		}
	}

	@Transactional
	@Override
	public Integer insertSurvey(SurveyDTO surveyDTO) {
		Survey survey = surveyDTO.getSurvey();
		Form form = surveyDTO.getForm();

		Integer userno = getCurrentUserNo();

		Map<String, Object> params = generateSurveyPaper(userno, survey, form);
		Integer surveyno = getSurveyno(params);

		SurveyRequire surveyrequire = generateSurveyRequire(surveyno, form);

		// surveyrequire db입력
		int result2 = formMapper.insertSurveyRequire(surveyrequire);
		System.out.println("surveyRequire : " + result2);

		SurveyOption surveyOption = generateSurveyOption(surveyno, survey);

		// surveyoption db입력
		int result3 = formMapper.insertSurveyOption(surveyOption);
		System.out.println("surveyOption : " + result3);

		List<Question> questionList = generateQuestionList(survey);

		List<Answers> answersList = generateAnswersList(questionList);

		// Question 리스트를 SurveyQuestion 리스트로 변환
		List<SurveyQuestion> surveyQuestionList = generateSurveyQuestionList(surveyno, questionList);

		System.out.println("surveyQuestionList 크기 : " + surveyQuestionList.size());

		// SurveyQuestion 테이블에 넣음
		surveyQuestionListToDB(surveyQuestionList);

		// questionno 추출
		List<Integer> questionNoList = getQuestionNoList(surveyno);

		// answers 입력
		answersListToDB(surveyno, questionNoList, answersList);

		return surveyno;
	}

	private static void populateAnswersFromQuestion(Answers answers, List<String> questionAnswers) {
		// Answer 객체의 모든 필드를 순회하면서 값을 설정
		for (int i = 0; i < questionAnswers.size(); i++) {
			String fieldName = "answer" + (i + 1);
			try {
				Field field = Answers.class.getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(answers, questionAnswers.get(i));
			} catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	// userno 반환
	public Integer getCurrentUserNo() {
		// principal: SecurityContextHolder를 통해 현재 인증된 사용자의 정보를 저장하는 곳
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username;
		// UserDetails : Security에서 사용자 정보를 나타내는 인터페이스
		// instanceof : principal가 UserDetails타입인지?
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		// principal은 인증된 사용자를 나타내는 객체,
		// UserDetails은 사용자의 정보를 나타내기 위한 인터페이스

		// 현재 userno
		return userSecurityServiceImpl.getUserno(username);
	}

	public String generateRandomLink(Survey survey) {
		String ranLink = RandomStringGenerator.generateRandomString();
		// 공개/비공개 설정
		boolean isPublic = survey.is_public_result();
		if (!isPublic) {
			ranLink = "0" + ranLink.substring(1);
		}
		return ranLink;
	}

	public LocalDateTime setRegidate(Survey survey) {
		return survey.getCurrentTime();
	}

	public LocalDateTime setDeadLine(LocalDateTime regidate, Form form) {
		int period = Integer.parseInt(form.getPeriod());
		return regidate.plusDays(period);
	}

	public Map<String, Object> generateSurveyPaper(Integer userNo, Survey survey, Form form) {

		String ranLink = generateRandomLink(survey);

		// surveyPaper을 db에 입력
		Map<String, Object> params = new HashMap<>();
		params.put("userno", userNo);
		params.put("surveytitle", survey.getSurveytitle());
		params.put("surveycontent", survey.getSurveycontent());
		params.put("link", ranLink);
		params.put("surveyno", null); // 이 값이 업데이트됨

		LocalDateTime regidate = setRegidate(survey);
		if (regidate != null) {
			LocalDateTime deadline = setDeadLine(regidate, form);
			params.put("regidate", regidate);
			params.put("deadline", deadline);
		}
		return params;
	}

	public Integer getSurveyno(Map<String, Object> params) {
		int result1 = formMapper.insertSurveyPaperTemp(params);
		System.out.println("surveyPaper : " + result1);

		BigInteger surveynoBigInteger = (BigInteger) params.get("surveyno");
		return surveynoBigInteger.intValue();
	}

	public SurveyRequire generateSurveyRequire(Integer surveyno, Form form) {
		return SurveyRequire.builder().surveyno(surveyno).subject(form.getSubject())
				.period(Integer.parseInt(form.getPeriod())).email_option(form.isEmail())
				.phone_option(form.isPhonenumber()).age_option(form.isAge()).gender_option(form.isGender())
				.target(form.getTarget()).department_option(form.isDepartment()).position_option(form.isPosition())
				.grade_option(form.isGrade()).college_option(form.isCollege()).region_option(form.isRegion())
				.finaledu_option(form.isFinaledu()).incomelevel_option(form.isIncomelevel()).build();
	}

	public SurveyOption generateSurveyOption(Integer surveyno, Survey survey) {
		return SurveyOption.builder().surveyno(surveyno).is_public_survey(survey.is_public_survey())
				.require_login(survey.isRequire_login()).is_public_result(survey.is_public_result())
				.show_progress(survey.isShow_progress()).build();
	}

	public List<Question> generateQuestionList(Survey survey) {
		return survey.getQuestions();
	}

	public List<Answers> generateAnswersList(List<Question> questionList) {

		List<Answers> answersList = new ArrayList<>();

		// Question 내의 List<Answer>을 Answers 객체 하나로 변환, 리스트에 추가
		for (int i = 0; i < questionList.size(); i++) {
			Question q = questionList.get(i);
			Answers answers = new Answers();

			populateAnswersFromQuestion(answers, q.getAnswers());
			answersList.add(answers);
		}
		return answersList;
	}

	public List<SurveyQuestion> generateSurveyQuestionList(Integer surveyno, List<Question> questionList) {
		return questionList.stream().map(q -> new SurveyQuestion(null, surveyno, q.getQuestion_contents(),
				q.getAnswer_types(), q.isMandatory(), q.getAnswer_min(), q.getAnswer_max()))
				.collect(Collectors.toList());
	}

	public List<Integer> getQuestionNoList(Integer surveyno) {
		return formMapper.selectQuestionNo(surveyno);
	}

	public void surveyQuestionListToDB(List<SurveyQuestion> surveyQuestionList) {
		for (int i = 0; i < surveyQuestionList.size(); i++) {
			int result4 = formMapper.insertSurveyQuestion(surveyQuestionList.get(i));
			System.out.println("surveyQuestion : " + i + " 번째 - " + result4);
		}
	}

	public void answersListToDB(Integer surveyno, List<Integer> questionNoList, List<Answers> answersList) {
		for (int i = 0; i < answersList.size(); i++) {
			Integer questionno = questionNoList.get(i);

			answersList.get(i).setQuestionno(questionno);
			answersList.get(i).setSurveyno(surveyno);

			int result5 = formMapper.insertAnswers(answersList.get(i));

			System.out.println("Answers : " + i + "번째 - " + result5);
		}
	}
}

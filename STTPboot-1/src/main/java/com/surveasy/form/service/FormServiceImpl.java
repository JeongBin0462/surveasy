package com.surveasy.form.service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
public class FormServiceImpl implements FormService{
	
	@Autowired
	UserSecurityServiceImpl userSecurityServiceImpl;
	
	@Autowired
	FormMapper formMapper;
	
	@Autowired
	SurveyMapper surveyMapper;

	@Transactional
	@Override
	public boolean insertSurvey(SurveyDTO surveyDTO) {
		// principal: SecurityContextHolder를 통해 현재 인증된 사용자의 정보를 저장하는 곳
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
	    String username;
	    // UserDetails : Security에서 사용자 정보를 나타내는 인터페이스
	    // instanceof : principal가 UserDetails타입인지?
	    if (principal instanceof UserDetails) {
	    	username = ((UserDetails)principal).getUsername();
	    } else {
	    	username = principal.toString();
	    }
	    // principal은 인증된 사용자를 나타내는 객체,
	    // UserDetails은 사용자의 정보를 나타내기 위한 인터페이스

	    //surveyDTO 객체 쪼갬
	    Form form = surveyDTO.getForm();
	    Survey survey = surveyDTO.getSurvey();
	    
	    // 현재 userno
	    Integer userNo = userSecurityServiceImpl.getUserno(username);
		
	    // 난수 링크 생성
	    String ranLink = RandomStringGenerator.generateRandomString();
	    
	    // regidate 현재시간 설정(임시)
	    LocalDateTime regidate = LocalDateTime.now();
	    
	    // surveyPaper을 db에 입력
	    int result1 = formMapper.insertSurveyPaperTemp(userNo, surveyDTO.getSurvey().getSurveytitle(), surveyDTO.getSurvey().getSurveycontent(), ranLink);
	    System.out.println("surveyPaper : " + result1);
	    
	    // 만든 surveypaper 행의 surveyno를 가져옴
	    Integer surveyno = formMapper.getSurveyPaper(userNo);
	    System.out.println("surveyno : " + surveyno);
	    
	    // surveyrequire 객체 생성
	    SurveyRequire surveyrequire = SurveyRequire.builder()
	    										.surveyno(surveyno)
	    										.subject(form.getSubject())
	    										.period(Integer.parseInt(form.getPeriod()))
	    										.email_option(form.isEmail())
	    										.phone_option(form.isPhonenumber())
	    										.age_option(form.isAge())
	    										.gender_option(form.isGender())
	    										.target(form.getTarget())
	    										.department_option(form.isDepartment())
	    										.position_option(form.isPosition())
	    										.grade_option(form.isGrade())
	    										.college_option(form.isCollege())
	    										.region_option(form.isRegion())
	    										.finaledu_option(form.isFinaledu())
	    										.incomelevel_option(form.isIncomelevel())
	    										.build();
	    										
	    // surveyrequire db입력
	    int result2 = formMapper.insertSurveyRequire(surveyrequire);
	    System.out.println("surveyRequire : " + result2);
	    
	    // surveyoption 객체 생성
	    SurveyOption surveyOption = SurveyOption.builder()
	    										.surveyno(surveyno)
	    										.is_public_survey(survey.is_public_survey())
	    										.require_login(survey.isRequire_login())
	    										.is_public_result(survey.is_public_result())
	    										.show_progress(survey.isShow_progress())
	    										.build();
	    
	    // surveyoption db입력
	    int result3 = formMapper.insertSurveyOption(surveyOption);
	    System.out.println("surveyOption : " + result3);
	    
	    // Question 객체 리스트 생성
	    List<Question> questionList = survey.getQuestions();
	    
	    // Answers 객체 리스트 생성
	    List<Answers> answersList = new ArrayList<>();
	    
	    // Question 내의 List<Answer>을 Answers 객체 하나로 변환, 리스트에 추가
		for (int i = 0; i < questionList.size(); i++) {
			Question q = questionList.get(i);
			Answers answers = new Answers();
			
			populateAnswersFromQuestion(answers, q.getAnswers());
			answersList.add(answers);
		}
	    
	    // Question 리스트를 SurveyQuestion 리스트로 변환
        List<SurveyQuestion> surveyQuestionList = questionList.stream()
                .map(q -> new SurveyQuestion(null, surveyno, q.getQuestion_contents(), q.getAnswer_types(), q.isMandatory(), q.getAnswer_min(), q.getAnswer_max()))
                .collect(Collectors.toList());
        
        System.out.println("surveyQuestionList 크기 : " + surveyQuestionList.size());
        
	    // SurveyQuestion 테이블에 넣음
	    for (int i = 0; i < surveyQuestionList.size(); i++) {
	    	int result4 = formMapper.insertSurveyQuestion(surveyQuestionList.get(i));
	    	System.out.println("surveyQuestion : " + i + " 번째 - " + result4);
	    	
	    }
	    
	    // questionno 추출
	    List<Integer> questionNoList = formMapper.selectQuestionNo(surveyno);
	    
	    // answers 입력
	    for (int i = 0; i < answersList.size(); i++) {
	    	Integer questionno = questionNoList.get(i);
	    	
	    	answersList.get(i).setQuestionno(questionno);
	    	answersList.get(i).setSurveyno(surveyno);
	    	
	    	int result5 = formMapper.insertAnswers(answersList.get(i));
	    	
	    	System.out.println("Answers : " + i + "번째 - " + result5);
	    	
	    }
	    
		return true;
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
}

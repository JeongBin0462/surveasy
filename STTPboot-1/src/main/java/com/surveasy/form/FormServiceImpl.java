package com.surveasy.form;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.surveasy.security.UserSecurityServiceImpl;
import com.surveasy.survey.mapper.SurveyMapper;
import com.surveasy.survey.model.SurveyOption;
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
	    
	    // surveyDTO와 userNo로 데이터 입력용 객체를 생성
	    // 임시저장용 surveyPaper
//	    SurveyPaper surveyPaper = SurveyPaper.builder()
//	    									.userno(userNo)
//	    									.surveytitle(surveyDTO.getSurvey().getSurveytitle())
//	    									.surveycontent(surveyDTO.getSurvey().getSurveycontent())
//	    									.link(ranLink)
//	    									.build();
//	    
	    // surveyPaper을 db에 입력
	    int result1 = formMapper.insertSurveyPaperTemp(userNo, surveyDTO.getSurvey().getSurveytitle(), surveyDTO.getSurvey().getSurveycontent(), ranLink);
	    
	    // 만든 surveypaper의 surveyno를 가져옴
	    int surveyno = formMapper.getSurveyPaper(userNo);
	    
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
	    int resuit2 = formMapper.insertSurveyRequire(surveyrequire);
	    
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
	    
	    // question 객체 리스트 생성
//	    List<Question> questionList = survey.getQuestions();
	    
//	    for (int i = 0; i < questionList.size(); i++) {
//	    	formMapper.insertSurveyQuestion(questionList.get(i));
//	    }
	    
	    // questionno 추출
	    
	    // answers 입력
	    
	    
	    
	    
	    
	    
	    
		return false;
	}
}

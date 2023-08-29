package com.survey.sttp.service.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.survey.sttp.model.form.SurveyDTO;
import com.survey.sttp.model.form.SurveyPaperTable;
import com.survey.sttp.service.user.UserSecurityServiceImpl;

public class FormServiceImpl implements FormService{
	
	@Autowired
	UserSecurityServiceImpl userSecurityServiceImpl;
	
	@Autowired
	SurveyPaperTable surveyPaperTable;

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

	    Integer userNo = userSecurityServiceImpl.getUserno(username);
	    surveyPaperTable.setUserno(userNo);
		
		return false;
	}
}

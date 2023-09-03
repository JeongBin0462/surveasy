package com.surveasy.survey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.surveasy.survey.model.Answers;
import com.surveasy.survey.model.SurveyOption;
import com.surveasy.survey.model.SurveyPaper;
import com.surveasy.survey.model.SurveyQuestion;
import com.surveasy.survey.model.SurveyRequire;
import com.surveasy.survey.service.SurveyService;
import com.surveasy.user.model.UserDTO;

@Controller
@RequestMapping("/surveasy/survey")
public class SurveyController {

	@Autowired
	SurveyService surveyService;
	
	@GetMapping(value = "/board")
	public String boardSurvey() {
		return "/3.1survey_board";
	}

	@GetMapping(value = "/{link}")
	public String showSurveyByLink(@PathVariable("link") String link, Model model) {
	    SurveyPaper surveyPaper = surveyService.getSurveyPaperByLink(link);
	    
	    // 올바른 주소인지 확인
	    if (surveyPaper == null) {
	        return "/1.main";
	    }

	    int surveyNo = surveyPaper.getSurveyno();
	    int userNo = surveyPaper.getUserno();
	    
	    System.out.println(surveyNo);
	    System.out.println(userNo);
	    System.out.println(surveyService.getUserSurvey(userNo, surveyNo));
	    
	    // 이미 참여한 설문인지 확인
	    if (!surveyService.getUserSurvey(userNo, surveyNo)) {
	    	model.addAttribute("alertMessage", "이미 참여한 설문입니다");
	    	return "/0.error";
	    }
	    
	    // 동적페이지 구성을 위한 객체 생성
	    SurveyOption surveyOption = surveyService.getSurveyOption(surveyNo);
	    SurveyRequire surveyRequire = surveyService.getSurveyRequire(surveyNo);
	    List<SurveyQuestion> surveyQuestion = surveyService.getSurveyQuestion(surveyNo);
	    List<Answers> answers = surveyService.getAnswers(surveyQuestion);
	    UserDTO userInfo = surveyService.getUserInfo();
	    System.out.println(userInfo);
	    
	    // 요청 보내기
	    model.addAttribute("surveyPaper", surveyPaper);
	    model.addAttribute("surveyOption", surveyOption);
	    model.addAttribute("surveyRequire", surveyRequire);
	    model.addAttribute("surveyQuestion", surveyQuestion);
	    model.addAttribute("answers", answers);
	    model.addAttribute("userInfo", userInfo);

	    return "/3.3survey_board_participate";
	}
}

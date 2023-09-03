package com.surveasy.submit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.surveasy.submit.model.Require;
import com.surveasy.submit.model.SurveyAnswers;
import com.surveasy.submit.model.SurveySubmitDTO;
import com.surveasy.submit.model.UserSurvey;
import com.surveasy.submit.service.SubmitService;

@Controller
@RequestMapping("/surveasy/survey")
public class SubmitController {
	
	@Autowired
	private SubmitService submitService;
	
	@Transactional
	@PostMapping(value = "/show")
	public ResponseEntity<?> submitSurvey(@RequestBody SurveySubmitDTO surveyData) {
		System.out.println("요청도착!");
		System.out.println(surveyData.getRequire().toString());
		Require require = surveyData.getRequire();
		
		List<SurveyAnswers> list = surveyData.getSurveySubmits();
		
		for (int i = 0; i < list.size(); i++) {
		System.out.println(list.get(i).getQuestionno());
		System.out.println(list.get(i).getType());
		System.out.println(list.get(i).getAnswerMap().toString());
		
		System.out.println("------------");
		}
		
		UserSurvey userSurvey = submitService.insertUserSurvey(surveyData.getRequire().getSurveyno());
		System.out.println(userSurvey.getUser_survey_no());
		
		int userSurveyNo = userSurvey.getUser_survey_no();
		
		submitService.insertInputInfo(userSurveyNo, require);
		
		submitService.insertAnswers(userSurveyNo, list);
		
		return ResponseEntity.ok().body("{\"message\":\"success\"}");
	}
	
	@PostMapping(value = "/success")
	public String surveySuccess(@RequestParam String subject, @RequestParam String url, Model model) {

	    model.addAttribute("url", url);
	    model.addAttribute("subject", subject);

	    return "/3.4survey_board_result";
	}
}	

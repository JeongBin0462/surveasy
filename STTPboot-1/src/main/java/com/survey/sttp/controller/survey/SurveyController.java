package com.survey.sttp.controller.survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.survey.sttp.service.survey.SurveyService;

@Controller
@RequestMapping("/surveasy/survey")
public class SurveyController {
	
	@Autowired
	SurveyService surveyService;
	
	@GetMapping(value = "/test")
	public String test() {
		surveyService.getSurveyInfo(1);
		
		return "/1.main";
	}
}

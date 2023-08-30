package com.surveasy.controller.survey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.surveasy.model.survey.Answers;
import com.surveasy.model.survey.SurveyOption;
import com.surveasy.model.survey.SurveyPaper;
import com.surveasy.model.survey.SurveyQuestion;
import com.surveasy.model.survey.SurveyRequire;
import com.surveasy.service.survey.SurveyService;

@Controller
@RequestMapping("/surveasy/survey")
public class SurveyController {
	
	@Autowired
	SurveyService surveyService;
	
	@GetMapping(value = "/show")
	public String showSurvey(Model model) {
		SurveyPaper surveyPaper = surveyService.getSurveyPaper(1);
		SurveyOption surveyOption = surveyService.getSurveyOption(1);
		SurveyRequire surveyRequire = surveyService.getSurveyRequire(1);
		List<SurveyQuestion> surveyQuestion = surveyService.getSurveyQuestion(1);
		List<Answers> answers = surveyService.getAnswers(surveyQuestion);
		
		model.addAttribute("surveyPaper", surveyPaper);
		model.addAttribute("surveyOption", surveyOption);
		model.addAttribute("surveyRequire", surveyRequire);
		model.addAttribute("surveyQuestion", surveyQuestion);
		model.addAttribute("answers", answers);
		
		return "/3.3survey_board_participate";
	}
}

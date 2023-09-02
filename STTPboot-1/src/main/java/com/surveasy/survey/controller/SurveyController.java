package com.surveasy.survey.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.surveasy.submit.model.SurveySubmitDTO;
import com.surveasy.survey.model.Answers;
import com.surveasy.survey.model.SurveyOption;
import com.surveasy.survey.model.SurveyPaper;
import com.surveasy.survey.model.SurveyQuestion;
import com.surveasy.survey.model.SurveyRequire;
import com.surveasy.survey.service.SurveyService;

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

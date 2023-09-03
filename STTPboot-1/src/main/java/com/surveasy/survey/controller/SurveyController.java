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
	    
	    if (surveyPaper == null) {
	        return "/1.main";
	    }

	    int surveyId = surveyPaper.getSurveyno();

	    SurveyOption surveyOption = surveyService.getSurveyOption(surveyId);
	    SurveyRequire surveyRequire = surveyService.getSurveyRequire(surveyId);
	    List<SurveyQuestion> surveyQuestion = surveyService.getSurveyQuestion(surveyId);
	    List<Answers> answers = surveyService.getAnswers(surveyQuestion);

	    model.addAttribute("surveyPaper", surveyPaper);
	    model.addAttribute("surveyOption", surveyOption);
	    model.addAttribute("surveyRequire", surveyRequire);
	    model.addAttribute("surveyQuestion", surveyQuestion);
	    model.addAttribute("answers", answers);

	    return "/3.3survey_board_participate";
	}
}

package com.surveasy.submit.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.surveasy.submit.model.SurveySubmitDTO;

@Controller
@RequestMapping("/surveasy/survey")
public class SubmitController {
	
	@PostMapping(value = "/show")
	public ResponseEntity<?> submitSurvey(@RequestBody List<SurveySubmitDTO> surveyData) {
		System.out.println("요청도착!");
		for (int i = 0; i < surveyData.size(); i++) {
		System.out.println(surveyData.get(i).getSurveyno());
		System.out.println(surveyData.get(i).getQuestionno());
		System.out.println(surveyData.get(i).getType());
		System.out.println(surveyData.get(i).getAnswerMap().toString());
		
		System.out.println("------------");
		}
		return ResponseEntity.ok().build();
	}
}	

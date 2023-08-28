package com.survey.sttp.controller.form;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.survey.sttp.model.form.SurveyRequest;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/surveasy/makesurvey")
@Slf4j
public class FormController {
	
	@PostMapping
    public ResponseEntity<?> createSurvey(@RequestBody SurveyRequest surveyRequest) {
        // surveyRequest 객체로 로직 처리
		System.out.println(surveyRequest);
        return ResponseEntity.ok().build();
        
    }
}

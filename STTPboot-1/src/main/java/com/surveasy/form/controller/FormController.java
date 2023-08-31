package com.surveasy.form.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.surveasy.form.model.SurveyDTO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/surveasy/makesurvey")
@Slf4j
public class FormController {
	
	@PostMapping
    public ResponseEntity<?> createSurvey(@RequestBody SurveyDTO surveyDTO) {
		System.out.println(surveyDTO);
		// 제출버튼 누르면 검증해서 어느 하나라도 비어있거나 0이면 응답 반환
//		surveyDTO.getSurvey().getSurveytitle()
//		surveyDTO.getSurvey().getSurveycontent()
//		surveyDTO.getSurvey().getQuestions().size()
//		surveyDTO.getSurvey().getQuestions().get(0).getQuestion_contents().length()
//		surveyDTO.getSurvey().getQuestions().get(0).getAnswer_types()
//		surveyDTO.getSurvey().getQuestions().get(0).getAnswers().size()
//		surveyDTO.getSurvey().getQuestions().get(0).getAnswers().get(0).length()
		
        return ResponseEntity.ok().build();
        
    }
}

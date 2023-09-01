package com.surveasy.form.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.surveasy.form.model.SurveyDTO;
import com.surveasy.form.service.FormService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/surveasy/makesurvey")
@Slf4j
public class FormController {
	
	@Autowired
	private FormService formService;
	
	@ResponseBody
	@PostMapping("/submit")
    public Map<String, Object> createSurvey(@RequestBody SurveyDTO surveyDTO) {
		Map<String, Object> response = new HashMap<>();
		System.out.println(surveyDTO);
		// 제출버튼 누르면 검증해서 어느 하나라도 비어있거나 0이면 응답 반환
//		surveyDTO.getSurvey().getSurveytitle()
//		surveyDTO.getSurvey().getSurveycontent()
//		surveyDTO.getSurvey().getQuestions().size()
//		surveyDTO.getSurvey().getQuestions().get(0).getQuestion_contents().length()
//		surveyDTO.getSurvey().getQuestions().get(0).getAnswer_types()
//		surveyDTO.getSurvey().getQuestions().get(0).getAnswers().size()
//		surveyDTO.getSurvey().getQuestions().get(0).getAnswers().get(0).length()
		
		if (formService.insertSurvey(surveyDTO)) {
			response.put("success", true);
//			response.put("redirectUrl", "/surveasy/makesurvey/success");
		} else {
			response.put("success", false);
		}
		return response;
    }
}

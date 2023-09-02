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
		
		try {
		    formService.insertSurvey(surveyDTO);
		    response.put("success", true);
		} catch (Exception e) {
			response.put("success", false);
		}

		return response;
    }
}

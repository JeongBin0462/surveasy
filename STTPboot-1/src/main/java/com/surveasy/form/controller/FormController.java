package com.surveasy.form.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.surveasy.form.model.Form;
import com.surveasy.form.model.Survey;
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

		String message = "";

		if (surveyDTO.getSurvey().getCurrentTime() == null) {
			message = "임시저장";

			if (!formService.tempSaveNum()) {
				message = "임시저장은 한 회원당 최대 5번까지 가능합니다.";
				response.put("success", false);
				response.put("message", message);
				return response;
			}
		}

		try {
			formService.insertSurvey(surveyDTO);
			response.put("success", true);
			response.put("message", message);
			response.put("mapping", "http://localhost:8080/surveasy/makesurvey/success");
		} catch (Exception e) {
			message = "저장 실패";
			response.put("success", false);
			response.put("message", message);
		}

		return response;
	}

}

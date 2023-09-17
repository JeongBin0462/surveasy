package com.surveasy.form.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	private String currentSurveyTitle;
	private String currentLink;

	@Autowired
	private FormService formService;

	@ResponseBody
	@PostMapping("/submit")
	public Map<String, Object> createSurvey(@RequestBody SurveyDTO surveyDTO, Model model) {
		Map<String, Object> response = new HashMap<>();
		System.out.println("이거야?" + surveyDTO);

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
			Integer surveyno = formService.insertSurvey(surveyDTO);
			
			currentSurveyTitle = surveyDTO.getSurvey().getSurveytitle();
			currentLink = formService.getLinkOfSurveyNo(surveyno);
			
			System.out.println("surveyno: " + surveyno);
			System.out.println("link: " + currentLink);
			
			response.put("success", true);
			response.put("message", message);
			response.put("mapping", "http://localhost:8080/surveasy/makesurvey/success");
		} catch (Exception e) {
			message = "저장 실패: 관리자에게 문의하세요.";
			response.put("success", false);
			response.put("message", message);
		}
		return response;
	}
	
	@PostMapping("/success/req")
	public Map<String, String> successMakeSurvey() {
		
		System.out.println("success/req 2.4 매핑 성공");
		
		System.out.println("surveyTitle: " + currentSurveyTitle);
		System.out.println("link: " + currentLink);
		
		Map<String, String> response = new HashMap<>();
        response.put("surveyTitle", "설문지 제목 : " + currentSurveyTitle);
        response.put("link", "URL : http://localhost:8080/surveasy/survey/" + currentLink);
        
        return response;
	}

}

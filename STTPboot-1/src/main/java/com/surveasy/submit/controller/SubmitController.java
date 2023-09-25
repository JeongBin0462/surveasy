package com.surveasy.submit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.surveasy.submit.model.Require;
import com.surveasy.submit.model.SurveyAnswers;
import com.surveasy.submit.model.SurveySubmitDTO;
import com.surveasy.submit.model.UserSurvey;
import com.surveasy.submit.service.SubmitService;
import com.surveasy.survey.model.SurveyPaper;

@Controller
@RequestMapping("/surveasy/survey")
public class SubmitController {
	
	@Autowired
	private SubmitService submitService;
	
	// 설문지 작성 완료 버튼 클릭 시 이동 페이지
	@Transactional
	@PostMapping(value = "/show")
	public ResponseEntity<?> submitSurvey(@RequestBody SurveySubmitDTO surveyData) {
		// 필수입력 input
		Require require = surveyData.getRequire();
		// 답변입력 정보 input
		List<SurveyAnswers> list = surveyData.getSurveySubmits();
		// user_survey 테이블 insert
		UserSurvey userSurvey = submitService.insertUserSurvey(require.getSurveyno());
		
		int userSurveyNo = userSurvey.getUser_survey_no();
		submitService.insertInputInfo(userSurveyNo, require);
		submitService.insertAnswers(userSurveyNo, list);
		
		return ResponseEntity.ok().body("{\"message\":\"success\"}");
	}
	
	// 3-4 페이지 화면
	@PostMapping(value = "/success")
	public String surveySuccess(@RequestParam String subject, @RequestParam int surveyno, @RequestParam String url, Model model) {
		// 참여한 설문과 같은 주제의 설문 리스트(3개)
		List<SurveyPaper> surveyPaperList = submitService.getSurveyPaperList(subject, surveyno);

	    model.addAttribute("url", url);
	    model.addAttribute("surveyPaperList", surveyPaperList);

	    return "3.4survey_board_result";
	}
}	

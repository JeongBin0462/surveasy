package com.surveasy.survey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.surveasy.submit.service.SubmitService;
import com.surveasy.survey.model.Answers;
import com.surveasy.survey.model.SurveyOption;
import com.surveasy.survey.model.SurveyPaper;
import com.surveasy.survey.model.SurveyQuestion;
import com.surveasy.survey.model.SurveyRequire;
import com.surveasy.survey.service.SurveyService;
import com.surveasy.user.model.UserDTO;

@Controller
@RequestMapping("/surveasy/survey")
public class SurveyController {

	@Autowired
	private SurveyService surveyService;

	@Autowired
	private SubmitService submitService;

	// 3-1 전체 설문 리스트 화면
	@GetMapping(value = "/board")
	public Object boardSurvey(Model model, @RequestParam(required = false) String subject,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false, defaultValue = "1") int currentPage,
			@RequestParam(required = false, defaultValue = "false") boolean json) {
		String search = null;
		List<SurveyPaper> list = surveyService.getSurveyPaperList(subject, sort, search, currentPage);
		System.out.println(surveyService.getTotalSurveyCount(subject, sort, search));
		int totalPage = (surveyService.getTotalSurveyCount(subject, sort, search) / 10) + 1;
		System.out.println(totalPage);
		model.addAttribute("totalPage", totalPage);
		if (json) {
			System.out.println("여기로 오니?");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(list);
		}
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("surveyPapers", list);
		return "/3.1survey_board";
	}

	// 3-1 설문 리스트 검색
	@PostMapping(value = "board")
	public Object boardSearchSurvey(Model model,
			@RequestParam String search,
			@RequestParam(required = false) String subject,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false, defaultValue = "1") int currentPage,
			@RequestParam(required = false, defaultValue = "false") boolean json) {

		List<SurveyPaper> list = surveyService.getSurveyPaperList(subject, sort, search, currentPage);
		System.out.println(surveyService.getTotalSurveyCount(subject, sort, search));
		int totalPage = (surveyService.getTotalSurveyCount(subject, sort, search) / 10) + 1;
		System.out.println(totalPage);
		
		model.addAttribute("search", search);
		model.addAttribute("totalPage", totalPage);
		if (json) {
			System.out.println("여기로 오니?");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(list);
		}
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("surveyPapers", list);
		return "/3.1survey_board";
	}

	// 3-2 각 설문의 대략적인 정보 화면
	@GetMapping(value = "{link}")
	public String getSurveyPage(@PathVariable("link") String link, @RequestParam("no") int surveyno, Model model) {
		SurveyPaper surveyPaper = surveyService.getSurveyPaperByLink(link);
		SurveyRequire surveyRequire = surveyService.getSurveyRequire(surveyno);
		String subject = surveyRequire.getSubject();
		// 참여한 설문과 같은 주제의 설문 리스트(3개)
		List<SurveyPaper> surveyPaperList = submitService.getSurveyPaperList(subject, surveyno);

		model.addAttribute("surveyPaper", surveyPaper);
		model.addAttribute("surveyPaperList", surveyPaperList);
		return "/3.2survey_board_start";
	}

	// 3-3 설문 시작 화면
	@GetMapping(value = "/start/{link}")
	public String showSurveyByLink(@PathVariable("link") String link, Model model) {
		SurveyPaper surveyPaper = surveyService.getSurveyPaperByLink(link);

		// 올바른 주소인지 확인
		if (surveyPaper == null) {
			return "/1.main";
		}
		int surveyNo = surveyPaper.getSurveyno();

		// 이미 참여한 설문인지 확인
		if (!surveyService.getUserSurvey(surveyNo)) {
			model.addAttribute("alertMessage", "이미 참여한 설문입니다");
			return "/0.error";
		}

		// 동적페이지 구성을 위한 객체 생성
		SurveyOption surveyOption = surveyService.getSurveyOption(surveyNo);
		SurveyRequire surveyRequire = surveyService.getSurveyRequire(surveyNo);
		List<SurveyQuestion> surveyQuestion = surveyService.getSurveyQuestion(surveyNo);
		List<Answers> answers = surveyService.getAnswers(surveyQuestion);
		UserDTO userInfo = surveyService.getUserInfo();

		// 요청 보내기
		model.addAttribute("surveyPaper", surveyPaper);
		model.addAttribute("surveyOption", surveyOption);
		model.addAttribute("surveyRequire", surveyRequire);
		model.addAttribute("surveyQuestion", surveyQuestion);
		model.addAttribute("answers", answers);
		model.addAttribute("userInfo", userInfo);

		return "/3.3survey_board_participate";
	}
}

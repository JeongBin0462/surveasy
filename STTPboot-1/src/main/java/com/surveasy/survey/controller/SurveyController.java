package com.surveasy.survey.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	// 페이지에 나타낼 설문지 수
	private static final int PAGE_SIZE = 10;

	// 3-1 설문지 게시판 화면
	@GetMapping(value = "/board")
	public Object boardSurvey(Model model, @RequestParam(required = false) String search,
			@RequestParam(required = false) String subject, 
			@RequestParam(defaultValue = "최신순") String sort,
			@RequestParam(defaultValue = "1") int currentPage,
			@RequestParam(required = false, defaultValue = "false") boolean json) {
		return handleRequest(model, search, subject, sort, currentPage, json);
	}

	// 검색기능을 하는 post 매핑
	@PostMapping(value = "board")
	public Object boardSearchSurvey(Model model, @RequestParam String search,
			@RequestParam(required = false) String subject, @RequestParam(required = false) String sort,
			@RequestParam(required = false, defaultValue = "1") int currentPage,
			@RequestParam(required = false, defaultValue = "false") boolean json) {
		return handleRequest(model, search, subject, sort, currentPage, json);
	}

	private Object handleRequest(Model model, String search, String subject, String sort, int currentPage,
			boolean json) {
		List<SurveyPaper> list = surveyService.getSurveyPaperList(subject, sort, search, currentPage);
		int totalCount = surveyService.getTotalSurveyCount(subject, sort, search);
		int totalPage = (totalCount + PAGE_SIZE - 1) / PAGE_SIZE;

		System.out.println(totalCount);
		System.out.println("현재 페이지" + currentPage);
		System.out.println("전체 페이지" + totalPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("surveyPapers", list);

		if (search != null) {
			model.addAttribute("search", search);
		}

		if (subject != null) {
			model.addAttribute("subject", subject);
		}

		if (sort != null) {
			model.addAttribute("sort", sort);
		}

		if (json) {
			Map<String, Object> response = new HashMap<>();
			response.put("totalPage", totalPage);
			response.put("currentPage", currentPage);
			response.put("surveyPapers", list);
			response.put("search", search);
			response.put("subject", subject);
			response.put("sort", sort);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
		}

		return "3.1survey_board";
	}

	// 3-2 각 설문의 대략적인 정보 화면
	@GetMapping(value = "{link}")
	public String getSurveyPage(@PathVariable("link") String link, @RequestParam("no") int surveyno, Model model) {
		SurveyPaper surveyPaper = surveyService.getSurveyPaperByLink(link);
		SurveyRequire surveyRequire = surveyService.getSurveyRequire(surveyno);
		String subject = surveyRequire.getSubject();
		// 참여한 설문과 같은 주제의 설문 리스트(3개)
		List<SurveyPaper> surveyPaperList = submitService.getSurveyPaperList(subject, surveyno);
		
		boolean checkBookmark = surveyService.checkBookmark(surveyno);
		int count = surveyService.countBookmark(surveyno);
		System.out.println(count);
		model.addAttribute("bookmark", count);
		model.addAttribute("checkBookmark", checkBookmark);
		model.addAttribute("surveyPaper", surveyPaper);
		model.addAttribute("surveyPaperList", surveyPaperList);
		return "3.2survey_board_start";
	}
	
	// 3-2 즐겨찾기
	@PostMapping(value = "{link}")
	public ResponseEntity<?> handleBookmark(@PathVariable String link, @RequestBody Map<String, Integer> surveyMap) {
		Map<String, Object> responseMap = new HashMap<>();
		int surveyno = surveyMap.get("surveyKey");
		
		int bookmark = surveyService.insertBookmark(surveyno);
		boolean checkBookmark = surveyService.checkBookmark(surveyno);
		
		responseMap.put("bookmark", bookmark);
		responseMap.put("checkBookmark", checkBookmark);

		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	// 3-3 설문 시작 화면
	@GetMapping(value = "/start/{link}")
	public String showSurveyByLink(@PathVariable("link") String link, Model model) {
		SurveyPaper surveyPaper = surveyService.getSurveyPaperByLink(link);

		// 올바른 주소인지 확인
		if (surveyPaper == null) {
			return "1.main";
		}
		int surveyNo = surveyPaper.getSurveyno();

		// 이미 참여한 설문인지 확인
		if (!surveyService.getUserSurvey(surveyNo)) {
			model.addAttribute("alertMessage", "이미 참여한 설문입니다.");
			return "0.error";
		}
		
		// 본인이 만든 설문인지 확인
		if (!surveyService.surveyMine(surveyNo)) {
			model.addAttribute("alertMessage", "본인이 만든 설문은 참여할 수 없습니다.");
			return "0.error";
		}

		// 동적페이지 구성을 위한 객체 생성
		SurveyOption surveyOption = surveyService.getSurveyOption(surveyNo);
		SurveyRequire surveyRequire = surveyService.getSurveyRequire(surveyNo);
		List<SurveyQuestion> surveyQuestion = surveyService.getSurveyQuestion(surveyNo);
		List<Answers> answers = surveyService.getAnswers(surveyQuestion);
		UserDTO userInfo = surveyService.getUserInfo();
		
		// 전체 문항 수 확인
		int countAnswers = surveyService.countAnswers(surveyNo);

		// 요청 보내기
		model.addAttribute("surveyPaper", surveyPaper);
		model.addAttribute("surveyOption", surveyOption);
		model.addAttribute("surveyRequire", surveyRequire);
		model.addAttribute("surveyQuestion", surveyQuestion);
		model.addAttribute("answers", answers);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("countAnswers", countAnswers);
		model.addAttribute("count", 0);

		return "3.3survey_board_participate";
	}
}

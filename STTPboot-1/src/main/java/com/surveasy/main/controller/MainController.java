package com.surveasy.main.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.surveasy.main.model.MainSurveyObj;
import com.surveasy.main.service.MainService;

@Controller
@RequestMapping("/surveasy/main")
public class MainController {

	@Autowired
	MainService mainService;

	@GetMapping
	public String firstShow(Model model) {
		List<MainSurveyObj> topList = mainService.generateMainList();
		System.out.println(topList.toString());
		List<MainSurveyObj> bottomList = mainService.generateMainList();

		model.addAttribute("topList", topList);
		model.addAttribute("bottomList", bottomList);

		return "/1.main";
	}

	@PostMapping("/update")
	public String showMainBySelected(@RequestBody Map<String, String> request, Model model) {

		System.out.println(request);

		String selectedSort = request.get("selectedSort");
		String selectedSubject = request.get("selectedSubject");

		List<MainSurveyObj> topList = mainService.generateMainList();
		List<MainSurveyObj> bottomList = mainService.generateMainList();

//		switch(selectedSort) {
//		case "남은기간":
//			topList = mainService.sortByRemainTime(topList);
//			System.out.println("switch문 통과");
//			break;
//		case "최신순":
//			topList = mainService.sortByLatest(topList);
//			break;
//		case "참여순":
//			topList = mainService.sortByParticipants(topList);
//			break;
//		case "즐겨찾기순":
//			topList = mainService.sortByBookmark(topList);
//			break;
//		}
//		
//		bottomList = mainService.sortBySubject(bottomList, selectedSubject);
//		bottomList = mainService.sortByRemainTime(bottomList);

		model.addAttribute("topList", topList);
		model.addAttribute("bottomList", bottomList);

		return "/1.main";
	}
}

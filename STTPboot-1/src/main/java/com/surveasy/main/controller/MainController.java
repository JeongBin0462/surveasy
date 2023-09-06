package com.surveasy.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		List<MainSurveyObj> bottomList = mainService.generateMainList();

		topList = mainService.sortByRemainTime(topList);
		bottomList = mainService.sortBySubject(bottomList, "정치");
		bottomList = mainService.sortByRemainTime(bottomList);

		if (topList.size() > 5) {
			for (int i = topList.size() - 1; i > 4; i--) {
				topList.remove(i);
			}
		}
		
		if (bottomList.size() > 5) {
			for (int i = bottomList.size() - 1; i > 4; i--) {
				bottomList.remove(i);
			}
		}
		
		model.addAttribute("topList", topList);
		model.addAttribute("bottomList", bottomList);
		
		return "/1.main";
	}

	@PostMapping("/update")
	@ResponseBody
	public Map<String, Object> showMainBySelected(@RequestBody Map<String, String> request) {
		String selectedSort = request.get("selectedSort");
		String selectedSubject = request.get("selectedSubject");

		List<MainSurveyObj> topList = mainService.generateMainList();
		List<MainSurveyObj> bottomList = mainService.generateMainList();

		switch (selectedSort) {
		case "남은기간":
			topList = mainService.sortByRemainTime(topList);
			break;
		case "최신순":
			topList = mainService.sortByLatest(topList);
			break;
		case "참여순":
			topList = mainService.sortByParticipants(topList);
			break;
		case "즐겨찾기순":
			topList = mainService.sortByBookmark(topList);
			break;
		}

		bottomList = mainService.sortBySubject(bottomList, selectedSubject);
		bottomList = mainService.sortByRemainTime(bottomList);

		if (topList.size() > 5) {
			for (int i = topList.size() - 1; i > 4; i--) {
				topList.remove(i);
			}
		}
		
		if (bottomList.size() > 5) {
			for (int i = bottomList.size() - 1; i > 4; i--) {
				bottomList.remove(i);
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("topList", topList);
		response.put("bottomList", bottomList);

		return response;
	}
}

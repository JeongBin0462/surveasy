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
@RequestMapping("/surveasy")
public class MainController {

	@Autowired
	MainService mainService;

	@GetMapping(value = { "/", "", "/main" })
	public String firstShow(Model model) {
		List<MainSurveyObj> topList = mainService.generateMainList();
		List<MainSurveyObj> bottomList = mainService.generateMainList();

		topList = mainService.sortByRemainTime(topList);
		bottomList = mainService.sortBySubject(bottomList, "정치");
		bottomList = mainService.sortByRemainTime(bottomList);

		topList = mainService.listByNewPage(topList, 0);
		bottomList = mainService.listByNewPage(bottomList, 0);

		model.addAttribute("topList", topList);
		model.addAttribute("bottomList", bottomList);

		return "1.main";
	}

	@PostMapping("/update")
	@ResponseBody
	public Map<String, Object> showMainBySelected(@RequestBody Map<String, Object> request) {
		Map<String, Object> response = new HashMap<>();

		String selectedSort = (String) (request.get("selectedSort"));
		String selectedSubject = (String) (request.get("selectedSubject"));

		if (request.get("topPageNum") != null) {
			List<MainSurveyObj> topList = mainService.generateMainList();
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

			Object topPageNumObj = request.get("topPageNum");
			int topPageNum = Integer.parseInt(topPageNumObj.toString());
			
			String topPageDir = (String) (request.get("topPageDir"));


			int newTopPage = mainService.getNewPage(topList, topPageNum, topPageDir);

			topList = mainService.listByNewPage(topList, newTopPage);

			response.put("topList", topList);
			response.put("newTopPage", newTopPage);

		}

		if (request.get("bottomPageNum") != null) {
			System.out.println("bottomPage : " + request.get("bottomPageNum"));
			
			List<MainSurveyObj> bottomList = mainService.generateMainList();

			bottomList = mainService.sortBySubject(bottomList, selectedSubject);
			bottomList = mainService.sortByRemainTime(bottomList);

			
			Object bottomPageNumObj = request.get("bottomPageNum");
			int bottomPageNum = Integer.parseInt(bottomPageNumObj.toString());
			
			String bottomPageDir = (String) (request.get("bottomPageDir"));

			int newBottomPage = mainService.getNewPage(bottomList, bottomPageNum, bottomPageDir);

			bottomList = mainService.listByNewPage(bottomList, newBottomPage);

			response.put("bottomList", bottomList);
			response.put("newBottomPage", newBottomPage);

		}

		return response;
	}
}

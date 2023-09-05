package com.surveasy.main.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/surveasy/main")
public class MainController {
	
	@PostMapping("/view")
	public String  showMainBySelected(@RequestBody Map<String, String> request, Model model) {
		
		System.out.println(request);
		
		String selectedSort = request.get("selectedSort");
		String selectedSubject = request.get("selectedSubject");
		
		
		
		return "/1.main";
	}
}

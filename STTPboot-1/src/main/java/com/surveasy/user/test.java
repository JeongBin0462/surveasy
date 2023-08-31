package com.surveasy.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class test {
	
	@GetMapping(value = "/surveasy")
	public String start() {
		return "0.header_footer";
	}
	
	@GetMapping("/")
	public String home() {
		
		return "/1.main";
	}
	
	@GetMapping(value = "/surveasy/main")
	public String main() {
		
		return "/1.main";
	}
	
	@GetMapping(value = "/surveasy/inputsurvey")
	public String inputSurvey() {
		return "2.2survey_make_input";
	}
	
	@GetMapping(value = "/surveasy/makesurvey")
	public String makesurvey() {
		return "2.3survey_make_main";
	}
}

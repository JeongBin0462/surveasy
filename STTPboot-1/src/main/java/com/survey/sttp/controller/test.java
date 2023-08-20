package com.survey.sttp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class test {
	
	@GetMapping(value = "/STTP")
	public String start() {
		return "0.header_footer";
	}
	
	@GetMapping(value = "/STTP/main")
	public String main() {
		
		return "/1.main";
	}
	
	@GetMapping(value = "/STTP/inputsurvey")
	public String inputSurvey() {
		return "2.2survey_make_input";
	}
	
	@GetMapping(value = "/STTP/makesurvey")
	public String makesurvey() {
		return "2.3survey_make_main";
	}
}

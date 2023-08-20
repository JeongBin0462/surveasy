package com.survey.sttp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class test {
	
	@GetMapping(value = "/test")
	public String start() {
		return "0.header_footer";
	}
	
	@GetMapping(value = "/test/main")
	public String main() {
		
		return "/1.main";
	}
}

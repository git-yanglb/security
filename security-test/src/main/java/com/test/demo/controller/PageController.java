package com.test.demo.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/login.html")
	public String login(@RequestParam(name="loginType",defaultValue="form") String loginType, Model model) {
		if(StringUtils.endsWithIgnoreCase("form", loginType)){
			model.addAttribute("loginType","form");
		}else{
			model.addAttribute("loginType","mobile");
		}
		return "login";
	}

}

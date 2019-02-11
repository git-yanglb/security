package com.test.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping("/user")
	public String user(@AuthenticationPrincipal Authentication authentication, Model model) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof User) {
			model.addAttribute("username", ((User) principal).getUsername());
		} else {
			model.addAttribute("username", principal);
		}
		model.addAttribute("avatar", "/image/title.jpg");
		model.addAttribute("logout", "/logout");
		return "user/user.html";
	}

}

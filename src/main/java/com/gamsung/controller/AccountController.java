package com.gamsung.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountController {

	@GetMapping(path = "mypage")
	public String mypage() {
		
		return "account/mypage";
	}
	
}

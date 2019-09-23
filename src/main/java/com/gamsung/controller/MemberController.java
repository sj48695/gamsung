package com.gamsung.controller;



import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gamsung.service.MemberService;
import com.gamsung.vo.Member;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping(path= "/login")
	public ModelAndView loginpage() {
		return new ModelAndView("/member/login");
	}
	
	@GetMapping(path="/register")
	public ModelAndView registerpage() {
		return new ModelAndView("/member/register");
	}
	
	@PostMapping(path="/registerIn")
	public String register(Member member) {
		
		memberService.save(member);
		
		return "/member/login";
	}

}

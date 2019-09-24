package com.gamsung.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gamsung.service.MemberService;
import com.gamsung.vo.Member;

@Controller
@RequestMapping(value= "/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/login")
	public ModelAndView loginpage() {
		return new ModelAndView("/member/login");
	}
	
	@GetMapping("/register")
	public ModelAndView registerpage() {
		return new ModelAndView("member/register");
	}
	
	@PostMapping("/registerIn")
	public String register(Member member) {
		
		memberService.save(member);
		
		return "redirect:/member/login";
	}
	
//	@PostMapping("/login")
//	public String findById(@PathVariable String username) {
//		
//		memberService.findByUsername(username);
//		
//		return "redirect:/home";
//	}

}

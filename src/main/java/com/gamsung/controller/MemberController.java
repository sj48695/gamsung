package com.gamsung.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gamsung.service.MemberService;
import com.gamsung.service.ProductService;
import com.gamsung.vo.Member;
import com.gamsung.vo.Product;

@Controller
@RequestMapping(value= "/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ProductService productService;
	
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
	
	@GetMapping("/admin")
	@ResponseBody
	public ModelAndView adminpage(HttpServletRequest req) {
		Authentication auth = (Authentication)req.getUserPrincipal();
		auth.getPrincipal();
		
		return new ModelAndView("/member/admin");
	}
	
	// 마이페이지
	@GetMapping(path = "mypage")
	public String mypage(HttpServletRequest req, Model model) {
		Authentication auth = (Authentication)req.getUserPrincipal();
		String memberId = auth.getName();
		
		List<Product> products = productService.findMyProductList(memberId);
		List<Product> requestProducts = productService.findMyRequestProductList(memberId);
		
		//내가 찜한 목록
		List<Product> hearts = productService.findMyHeartList(memberId);

		model.addAttribute("products", products);
		model.addAttribute("hearts", hearts);		
		model.addAttribute("requestProducts", requestProducts);
		
		return "member/mypage";
	}
	
	@GetMapping(path = "/mypage/products")
	@ResponseBody
	public List<Product> mypageProducts(HttpServletRequest req) {
		Authentication auth = (Authentication)req.getUserPrincipal();
		String memberId = auth.getName();
		
		List<Product> products = productService.findMyProductList(memberId);
		
		return products;
	}
	
	@GetMapping(path = "/mypage/requestProducts")
	@ResponseBody
	public List<Product> mypageRequestProducts(HttpServletRequest req) {
		Authentication auth = (Authentication)req.getUserPrincipal();
		String memberId = auth.getName();
		
		List<Product> requestProducts = productService.findMyRequestProductList(memberId);
		
		return requestProducts;
	}
	
	@GetMapping(path= {"/list"})
	@ResponseBody
	public List<Member> userList() {
			
		List<Member> members = memberService.findUserList();
		return members;
	}
	
	@PostMapping(path= {"/delete"})
	@ResponseBody
	public String userDelete(@RequestBody Member member) {
		
		memberService.deleteById(member,member.getId());
		
		return "{ \"result\": \"sucess\"}";
	}

}

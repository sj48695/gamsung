package com.gamsung.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gamsung.service.MemberService;
import com.gamsung.service.ProductService;
import com.gamsung.vo.Deal;
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
		for(Product product : products) {
			List<Deal> deals = productService.findDealsByProductNo(product.getProductNo());
			product.setDeals(deals);//요청받은 거래
		}
System.out.println(memberId);
		List<Deal> mydeals = productService.findDealsByBuyer(memberId);//내 거래요청
		System.out.println(mydeals);
		model.addAttribute("products", products);
		model.addAttribute("mydeals", mydeals);
		
		
		return "member/mypage";
	}

}

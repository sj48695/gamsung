package com.gamsung.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamsung.service.DealService;
import com.gamsung.service.ProductService;
import com.gamsung.vo.Deal;
import com.gamsung.vo.Product;

@Controller
@RequestMapping(path = "/deal")
public class DealController {

	@Autowired
	ProductService productService;

	@Autowired
	DealService dealService;

	@PostMapping(path = "/requestForm")
	public String requestForm(int price, int productNo, Model model) {
		Product product = productService.findProductByProductNo(productNo);
		product.setPrice(price);
		model.addAttribute("product", product);
		return "deal/request";
	}

	@PostMapping(path = "/request")
	public String request(Deal deal, HttpServletRequest req) {
		Authentication auth = (Authentication) req.getUserPrincipal();
		deal.setBuyer(auth.getName());
		dealService.registerDeal(deal);
		return "redirect:/member/mypage/";
	}

	@PutMapping(path = "/active/{dealNo}/{active}")
	@ResponseBody
	public String accept(@PathVariable int dealNo, @PathVariable String active) {
		dealService.updateDealActive(dealNo, active);
		if(active.equals("판매완료")) {
			Deal deal = dealService.findDealByDealNo(dealNo);
			productService.updateProductCount(deal.getProductNo());
		}
		return "success";
	}

	@GetMapping(path = "/order/{dealNo}")
	public String orderForm(@PathVariable int dealNo, HttpServletRequest req, Model model) {
		Deal deal = dealService.findDealByDealNo(dealNo);//거래중인 
		Product product = productService.findProductByProductNo(deal.getProductNo());
		product.setPrice(deal.getPrice());
		model.addAttribute("product", product);
		model.addAttribute("deal", deal);
		return "deal/order";
	}

	@PostMapping(path = "/order")
	public String order(Deal deal, HttpServletRequest req) {
		dealService.updateDealActive(deal.getDealNo(), "결제완료");
		return "redirect:/member/mypage/";
	}
	
	@DeleteMapping(path="/delete/{dealNo}")
	@ResponseBody
	public String deleteDeal(@PathVariable int dealNo) {
		dealService.deleteDeal(dealNo);
		return "success";
	}
}

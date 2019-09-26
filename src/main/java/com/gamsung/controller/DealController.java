package com.gamsung.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@PostMapping(path = "/")
	public String dealForm(int price, int productNo, Model model) {
		Product product = productService.findProductByProductNo(productNo);
		product.setPrice(price);
		model.addAttribute("product", product);
		return "deal/deal";
	}

	@PostMapping(path = "/order")
	public String order(Deal deal, HttpServletRequest req) {
		System.out.println(deal);
		Authentication auth = (Authentication) req.getUserPrincipal();
		deal.setBuyer(auth.getName());
		dealService.registerDeal(deal);
		return "redirect:/home";
	}
}

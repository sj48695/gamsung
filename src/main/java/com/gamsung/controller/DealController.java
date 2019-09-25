package com.gamsung.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@GetMapping(path = "/{type}/{productNo}")
	public String dealForm(@PathVariable String type, @PathVariable int productNo, Model model) {
		Product product = productService.findProductByProductNo(productNo);
		String dealType=null;
		if(type.equals("buynow")) {
			dealType="직거래";
		}else if(type.equals("direct")) {
			dealType="바로구매";
		}
		model.addAttribute("dealType", dealType);
		model.addAttribute("product", product);
		return "deal/deal";
	}

	@PostMapping(path = "/order")
//	@RequestMapping(path="/order", method = RequestMethod.POST)
	public String order(Deal deal) {
		System.out.println(deal);
		deal.setBuyer("sj");
		dealService.registerDeal(deal);
		return "redirect:/home";
	}
}

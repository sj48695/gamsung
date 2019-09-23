package com.gamsung.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gamsung.service.ProductService;
import com.gamsung.vo.Product;

@Controller
@RequestMapping(path = "/deal")
public class DealController {
	@Autowired
	ProductService productService;

	@GetMapping(path = "/{productNo}")
	public String dealForm(@PathVariable int productNo, Model model) {
		System.out.println(productNo);
		Product product = productService.findProductByProductNo(productNo);
		model.addAttribute("product", product);
		return "deal/deal";
	}
}

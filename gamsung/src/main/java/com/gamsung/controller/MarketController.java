package com.gamsung.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MarketController {

	@GetMapping(path = "categories")
	public String categories() {
		return "categories";
	}
	
	@GetMapping(path = "cart")
	public String cart() {
		return "cart";
	}
	
	@GetMapping(path = "checkout")
	public String checkout() {
		return "checkout";
	}
	
	@GetMapping(path = "product")
	public String product() {
		return "product";
	}
}

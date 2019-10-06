package com.gamsung.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gamsung.service.ProductService;
import com.gamsung.vo.Product;

@Controller
public class HomeController {
	
	@Autowired
	private ProductService productService;

	@GetMapping(path = { "/", "/home" })
	public String home(Model model) {
		
		List<Product> main = productService.findMain();
		
		model.addAttribute("main", main);
		
		return "home";
	}
}

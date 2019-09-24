package com.gamsung.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gamsung.service.ProductService;
import com.gamsung.vo.Product;

@Controller
@RequestMapping(path="product")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@GetMapping(path = "categories")
	public String categories() {
		return "categories";
	}
	
	@GetMapping(path="detail/{productNo}")
	public String productDetail(@PathVariable int productNo, Model model) {
		
		Product product = productService.findProductByProductNo(productNo);
	     
		model.addAttribute("product", product);
		 
		return "product"; 
	}
	
}

package com.gamsung.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gamsung.service.ProductService;
import com.gamsung.vo.Product;
import com.gamsung.vo.Review;

@Controller
@RequestMapping(path="product")
public class ProductController {
	
	@Autowired
	private ProductService productService;


	
	@GetMapping(path="detail/{productNo}")
	public String productDetail(@PathVariable int productNo, Model model) {
		
		Product product = productService.findProductByProductNo(productNo);
	    ArrayList<Review> reviewlist = productService.findReviewsByProductNo(productNo);
		
		model.addAttribute("product", product);
		model.addAttribute("reviewlist", reviewlist);
		System.out.println(reviewlist);
		 
		return "product/detail"; 
	}
	
	@GetMapping(path = "/categories")
	public String productList(Model model) {
		
		ArrayList<Product> products = productService.findProducts();
		
		model.addAttribute("products", products);
		System.out.println(products);
		
		return "product/list"; 
	}
	
	@GetMapping(path = "/write")
	public String showProductWrite() {
		
		return "product/write";
	}	
	
	@PostMapping(path = "/write")
	public String write(Product product, Model model) {
		

		productService.writeProduct(product);
		model.addAttribute("product", product);
		
		
		
		return "redirect:/product/categories";
	}
	
	
	
}

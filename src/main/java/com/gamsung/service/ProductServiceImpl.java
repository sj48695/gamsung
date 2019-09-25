package com.gamsung.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamsung.mapper.ProductMapper;
import com.gamsung.mapper.ReviewMapper;
import com.gamsung.vo.Product;
import com.gamsung.vo.Review;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	ReviewMapper reviewMapper;

	@Override
	public Product findProductByProductNo(int productNo) {

		Product product = productMapper.selectProductByProductNo(productNo);
		return product;

	}

	@Override
	public ArrayList<Product> findProducts() {
		
		ArrayList<Product> products = productMapper.selectQuestions();
		return products;
	}

	@Override
	public void writeProduct(Product product) {
		productMapper.insertProduct(product);
		
	}
	

	@Override
	public ArrayList<Review> findReviewsByProductNo(int productNo) {
		ArrayList<Review> reviewlist = reviewMapper.selectReviewsByProductNo(productNo);
		return reviewlist;
	}

}

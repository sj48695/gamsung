package com.gamsung.service;

import java.util.ArrayList;

import com.gamsung.vo.Product;
import com.gamsung.vo.Review;

public interface ProductService {

	Product findProductByProductNo(int productNo);

	ArrayList<Product> findProducts();

	void writeProduct(Product product);

	ArrayList<Review> findReviewsByProductNo(int productNo);
	
	

}

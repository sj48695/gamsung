package com.gamsung.service;

import java.util.ArrayList;
import java.util.List;

import com.gamsung.vo.Product;

public interface ProductService {

	Product findProductByProductNo(int productNo);

	ArrayList<Product> findProducts();

	void writeProduct(Product product);

	List<Product> findMyProductList(String memberId);
	
	

}

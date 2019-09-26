package com.gamsung.service;

import java.util.ArrayList;

import com.gamsung.vo.Product;

public interface ProductService {

	Product findProductByProductNo(int productNo);

	ArrayList<Product> findProducts();

	//void writeProduct(Product product);

	Integer registerProductTx(Product product);

	void insertProductFiles(Product product, int productNo);
	
	

}

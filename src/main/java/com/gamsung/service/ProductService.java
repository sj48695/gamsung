package com.gamsung.service;

import java.util.ArrayList;
import java.util.List;

import com.gamsung.vo.Heart;
import com.gamsung.vo.Product;
import com.gamsung.vo.Review;

public interface ProductService {

	Product findProductByProductNo(int productNo);

	ArrayList<Product> findProducts();

	ArrayList<Review> findReviewsByProductNo(int productNo);
	
	Integer registerProductTx(Product product);

	void insertProductFiles(Product product, int productNo);

	List<Product> findMyProductList(String memberId);

	void insertHeart(Heart heart);
	
	void deleteHeart(String id, int productNo);

	Heart findHeart(String id, int productNo);

}

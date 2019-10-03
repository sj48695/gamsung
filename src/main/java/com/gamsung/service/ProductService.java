package com.gamsung.service;

import java.util.ArrayList;
import java.util.List;

import com.gamsung.vo.Deal;
import com.gamsung.vo.Heart;
import com.gamsung.vo.Product;
import com.gamsung.vo.Review;

public interface ProductService {

	/*	Product	*/
	
	Product findProductByProductNo(int productNo);

	ArrayList<Product> findProducts();
	
	Integer registerProductTx(Product product);

	void insertProductFiles(Product product, int productNo);

	List<Product> findMyProductList(String memberId);
	
	List<Product> findMyRequestProductList(String memberId);
	
	void updateProductCount(int productNo);
	
	/*	Heart	*/

	void insertHeart(Heart heart);
	
	void deleteHeart(String id, int productNo);

	Heart findHeart(String id, int productNo);
	
	boolean findHeartCount(String id, int productNo);

	List<Product> findMyHeartList(String memberId);

	/*	Review	*/
	ArrayList<Review> findReviewsByProductNo(int productNo);

	Integer insertReview(Review review);
	
	void insertReviewFiles(Review review, int dealNo);
	
	List<Review> selectReview(String memberId);

	void deleteProduct(int productNo);



}

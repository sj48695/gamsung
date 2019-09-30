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
	
	/*	Heart	*/

	void insertHeart(Heart heart);
	
	void deleteHeart(String id, int productNo);

	Heart findHeart(String id, int productNo);
	
	boolean findHeartCount(String id, int productNo);

	List<Product> findMyHeartList(String memberId);

	/*	Deal	*/
	List<Deal> findDealsByProductNo(int productNo);

	List<Deal> findDealsByBuyer(String memberId);

	/*	Review	*/
	ArrayList<Review> findReviewsByProductNo(int productNo);

	void insertReview(Review review);

}

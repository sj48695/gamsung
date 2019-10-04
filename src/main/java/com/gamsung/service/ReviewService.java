package com.gamsung.service;

import java.util.ArrayList;
import java.util.List;

import com.gamsung.vo.Review;

public interface ReviewService {

	/*	Review	*/
	ArrayList<Review> findReviewsByProductNo(int productNo);

	void insertReview(Review review);
	
	void insertReviewFiles(Review review, int dealNo);
	
	List<Review> selectReview(String memberId);

	void deleteReview(int dealNo);

	Review findReviewByDealNo(int dealNo);

	void updateReview(Review review);

	void deleteReviewFile(int reviewFileNo);	

}

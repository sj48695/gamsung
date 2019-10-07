package com.gamsung.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamsung.mapper.DealMapper;
import com.gamsung.mapper.MemberMapper;
import com.gamsung.mapper.ProductMapper;
import com.gamsung.mapper.ReviewMapper;
import com.gamsung.vo.Deal;
import com.gamsung.vo.Review;
import com.gamsung.vo.ReviewFile;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	ReviewMapper reviewMapper;

	@Autowired
	DealMapper dealMapper;
	
	@Autowired
	MemberMapper memberMapper;

	/* Review	*/
	
	@Override
	public ArrayList<Review> findReviewsByProductNo(int productNo) {
		ArrayList<Review> reviewlist = reviewMapper.selectReviewsByProductNo(productNo);
		for(Review review : reviewlist) {
			List<ReviewFile> files = reviewMapper.selectReviewFilesByDealNo(review.getDealNo());
			review.setFiles(files);
		}
		return reviewlist;
	}

	@Override
	public void insertReview(Review review) {
		reviewMapper.insertReview(review);
		
		insertReviewFiles(review, review.getDealNo());
	}
	
	@Override
	public void insertReviewFiles(Review review, int dealNo) {
		// 이미지
		for (ReviewFile file : review.getFiles()) {
			file.setDealNo(dealNo);
			reviewMapper.insertReviewFile(file);
		}
		
	}

	@Override
	public List<Review> selectReview(String memberId) {
		List<Review> reviewlist = reviewMapper.selectReview(memberId);
		for(Review review : reviewlist) {
			Deal deal = dealMapper.selectDealByDealNo(review.getDealNo());
			review.setProductNo(deal.getProductNo());
		}
		return reviewlist;
	}

	@Override
	public void deleteReview(int dealNo) {
		reviewMapper.deleteReview(dealNo);
	}

	@Override
	public Review findReviewByDealNo(int dealNo) {
		Review review = reviewMapper.selectReviewByDealNo(dealNo);
		List<ReviewFile> files = reviewMapper.selectReviewFilesByDealNo(dealNo);
		review.setFiles(files);
		return review;
	}

	@Override
	public void updateReview(Review review) {
		reviewMapper.updateReview(review);
		insertReviewFiles(review, review.getDealNo());
	}

	@Override
	public void deleteReviewFile(int reviewFileNo) {
		reviewMapper.deleteReviewFile(reviewFileNo);
		
	}
	
	/* 상점 */
	@Override
	public List<Review> findStoreReview(String id) {
		List<Review> reviews = reviewMapper.selectStoreReview(id);
		for(Review review : reviews) {
			String profile = memberMapper.selectProfileImgById(review.getBuyer());
			review.setImgFileName(profile);
		}
		return reviews;
	}

	@Override
	public float findStoreAvg(String id) {
		float avg = reviewMapper.selectStoreAvg(id);
		
		return avg;
	}

//	@Override
//	public Review findReviewBuyerImg(String id) {
//		Review review = reviewMapper.selectReviewBuyerImg(id);
//		
//		return review;
//	}

}

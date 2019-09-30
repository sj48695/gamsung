package com.gamsung.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.gamsung.vo.Review;

@Mapper
public interface ReviewMapper {

	ArrayList<Review> selectReviewsByProductNo(int productNo);

	void insertReview(Review review);
	

}

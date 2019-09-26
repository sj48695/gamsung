package com.gamsung.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gamsung.vo.Heart;
import com.gamsung.vo.Product;

@Mapper
public interface ProductMapper {

	Product selectProductByProductNo(int productNo);

	ArrayList<Product> selectQuestions();

	void insertProduct(Product product);

	List<Product> selectMyProductList(String memberId);

	void insertHeart(Heart heart);

	void deleteHeart(HashMap<String, Object> param);
	
	
}

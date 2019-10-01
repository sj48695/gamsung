package com.gamsung.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gamsung.vo.Heart;
import com.gamsung.vo.Product;
import com.gamsung.vo.ProductFile;

@Mapper
public interface ProductMapper {

	Product selectProductByProductNo(int productNo);

	ArrayList<Product> selectProducts();

	void insertProduct(Product product);

	void insertProductFile(ProductFile file);

	ProductFile selectFileByProductNo(int productNo); //대표이미지
	
	List<ProductFile> selectFilesByProductNo(int productNo); //다중이미지
	
	List<Product> selectMyProductList(String memberId);

	void insertHeart(Heart heart);

	void deleteHeart(HashMap<String, Object> param);

	Heart selectHeart(HashMap<String, Object> params);

	boolean selectHeartCount(HashMap<String, Object> params);

	

	
}

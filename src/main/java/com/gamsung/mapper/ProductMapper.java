package com.gamsung.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gamsung.vo.Heart;
import com.gamsung.vo.Product;
import com.gamsung.vo.ProductFile;
import com.gamsung.vo.Report;

@Mapper
public interface ProductMapper {

	Product selectProductByProductNo(int productNo);

	ArrayList<Product> selectProducts(HashMap<String, Object> param);

	void insertProduct(Product product);

	void insertProductFile(ProductFile file);

	ProductFile selectProductFileByProductNo(int productNo); //대표이미지
	
	List<ProductFile> selectProductFilesByProductNo(int productNo); //다중이미지
	
	List<Product> selectMyProductList(String memberId);
	
	List<Product> selectMyRequestProductList(String memberId);
	
	void updateProductCount(int productNo);

	void deleteProduct(int productNo);

	void deleteProductFile(int productFileNo);
	
	void updateProductFile(ProductFile productFile);
	
	void updateProduct(Product product);
	
	List<Product> selectMain();
	
	ArrayList<Product> selectProductSearch(String keyword);
	
	//찜

	void insertHeart(Heart heart);

	void deleteHeart(HashMap<String, Object> param);

	Heart selectHeart(HashMap<String, Object> params);

	boolean selectHeartCount(HashMap<String, Object> params);

	List<Product> selectMyHeartList(String memberId);

	Integer selectHeartCountByProductNo(int productNo);

	//거래
	List<Product> list(HashMap<String, Object> params);

	
}

package com.gamsung.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gamsung.vo.Product;
import com.gamsung.vo.ProductFile;

@Mapper
public interface ProductMapper {

	Product selectProductByProductNo(int productNo);

	ArrayList<Product> selectProducts();

	void insertProduct(Product product);

	void insertProductFile(ProductFile file);

	ProductFile selectFileByProductNo(int productNo);
	
	
	
}

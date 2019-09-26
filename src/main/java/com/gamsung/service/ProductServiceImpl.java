package com.gamsung.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamsung.mapper.ProductMapper;
import com.gamsung.vo.Heart;
import com.gamsung.vo.Product;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductMapper productMapper;

	public Product findProductByProductNo(int productNo) {

		Product product = productMapper.selectProductByProductNo(productNo);
		return product;

	}

	@Override
	public ArrayList<Product> findProducts() {
		
		ArrayList<Product> products = productMapper.selectQuestions();
		return products;
	}

	@Override
	public void writeProduct(Product product) {
		productMapper.insertProduct(product);
		
	}

	@Override
	public List<Product> findMyProductList(String memberId) {
		
		List<Product> products = productMapper.selectMyProductList(memberId);
		return products;
	}

	@Override
	public void insertHeart(Heart heart) {
		
		productMapper.insertHeart(heart);
		
	}

	@Override
	public void deleteHeart(String id, int productNo) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("productNo", productNo);
		
		productMapper.deleteHeart(params);
		
	}

}

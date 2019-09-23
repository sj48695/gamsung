package com.gamsung.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamsung.mapper.DealMapper;
import com.gamsung.vo.Product;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	DealMapper productMapper;

	public Product findProductByProductNo(int productNo) {

		Product product = productMapper.selectProductByProductNo(productNo);
		return product;

	}

}

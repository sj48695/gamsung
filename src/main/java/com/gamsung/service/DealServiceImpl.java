package com.gamsung.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamsung.mapper.DealMapper;
import com.gamsung.mapper.ProductMapper;
import com.gamsung.vo.Deal;
import com.gamsung.vo.Product;

@Service
public class DealServiceImpl implements DealService {
	
	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	DealMapper dealMapper;

	@Override
	public Product findProductByProductNo(int productNo) {
		Product product = productMapper.selectProductByProductNo(productNo);
		return product;
	}
	
	@Override
	public void registerDeal(Deal deal) {
		dealMapper.insertDeal(deal);
	}


}

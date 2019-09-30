package com.gamsung.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamsung.mapper.DealMapper;
import com.gamsung.mapper.ProductMapper;
import com.gamsung.vo.Deal;
import com.gamsung.vo.Product;

@Service
public class DealServiceImpl implements DealService {

	@Autowired
	DealMapper dealMapper;
	
	@Override
	public Deal findDealByDealNo(int dealNo) {
		Deal deal = dealMapper.selectDealByDealNo(dealNo);
		return deal;
	}

	@Override
	public void registerDeal(Deal deal) {
		dealMapper.insertDeal(deal);
	}

	@Override
	public void updateDealActive(int dealNo, String dealActive) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("dealNo", dealNo);
		params.put("dealActive", dealActive);
		dealMapper.updateDealActive(params);
	}

	@Override
	public void deleteDeal(int dealNo) {
		dealMapper.deleteDeal(dealNo);
	}


}

package com.gamsung.service;

import java.util.HashMap;
import java.util.List;

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
	
	@Autowired
	ProductMapper productMapper;
	
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

	@Override
	public List<Deal> list() {
		List<Deal> deals = dealMapper.list();
		for(Deal deal : deals) {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("productNo", deal.getProductNo());
			deal.setProducts(productMapper.list(params));
		}
		return deals;
	}

	@Override
	public void dealComplete(int dealNo) {
		dealMapper.dealComplete(dealNo);
		
	}

	@Override
	public List<Deal> findsaleCom() {
		List<Deal> deals = dealMapper.findsaleCom();
		for(Deal deal : deals) {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("productNo", deal.getProductNo());
			deal.setProducts(productMapper.list(params));
		}
		return deals;
	}

	@Override
	public List<Deal> findComplete() {
		List<Deal> deals = dealMapper.findComplete();
		for(Deal deal : deals) {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("productNo", deal.getProductNo());
			deal.setProducts(productMapper.list(params));
		}
		return deals;
	}


}

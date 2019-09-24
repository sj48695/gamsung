package com.gamsung.service;

import com.gamsung.vo.Deal;
import com.gamsung.vo.Product;

public interface DealService {

	Product findProductByProductNo(int productNo);

	void registerDeal(Deal deal);

}

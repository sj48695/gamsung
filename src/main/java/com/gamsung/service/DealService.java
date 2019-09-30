package com.gamsung.service;

import com.gamsung.vo.Deal;
import com.gamsung.vo.Product;

public interface DealService {

	void registerDeal(Deal deal);

	void updateDealActive(int dealNo, String dealActive);

	void deleteDeal(int dealNo);

	Deal findDealByDealNo(int dealNo);

}

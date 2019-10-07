package com.gamsung.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gamsung.vo.Deal;

@Mapper
public interface DealMapper {

	void insertDeal(Deal deal);

	List<Deal> selectDealsByProductNo(int productNo);

	List<Deal> selectDealsByBuyer(HashMap<String, Object> params);
	
	Deal selectDealByDealNo(int dealNo);

	void updateDealActive(HashMap<String, Object> params);

	void deleteDeal(int dealNo);

	List<Deal> list();

	void dealComplete(int dealNo);

	List<Deal> findsaleCom();

	List<Deal> findComplete();


}

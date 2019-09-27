package com.gamsung.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gamsung.vo.Deal;

@Mapper
public interface DealMapper {

	void insertDeal(Deal deal);

	List<Deal> selectDealsByProductNo(int productNo);

	List<Deal> selectDealsByBuyer(String memberId);

}

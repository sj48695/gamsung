package com.gamsung.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gamsung.vo.Deal;

@Mapper
public interface DealMapper {

	void insertDeal(Deal deal);

}

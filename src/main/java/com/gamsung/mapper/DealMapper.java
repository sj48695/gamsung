package com.gamsung.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gamsung.vo.Product;

@Mapper
public interface DealMapper {

	Product selectProductByProductNo(int productNo);
	
}

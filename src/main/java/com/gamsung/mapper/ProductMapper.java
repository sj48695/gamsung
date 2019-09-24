package com.gamsung.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gamsung.vo.Product;

@Mapper
public interface ProductMapper {

	Product selectProductByProductNo(int productNo);
	
}

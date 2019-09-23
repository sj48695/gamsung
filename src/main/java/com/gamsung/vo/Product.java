package com.gamsung.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("Product")
public class Product {

	private int productNo;
	private String seller;
	private String name;
	private String contents;
	private int count;
	private int price;
	private String category;
	private int view;
	private String type;
	private Date regDate;
	
}

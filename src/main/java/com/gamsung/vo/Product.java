
package com.gamsung.vo;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
public class Product {

	private int productNo; 
	private String seller; //판매자
	private String name; //제품이름
	private String contents; //제품상세
	private int count; // 제품수량
	private int price; //가격
	private String category; //거래상태
	private int view; //조회수
	private String type; //흥정or일반
	private Date regDate;
	
	private ProductFile file;
	private List<ProductFile> files;
	
	private String sellerNick;
	private Heart heart; 
	private List<Deal> deals;
	
}

package com.gamsung.vo;

import java.util.Date;

import lombok.Data;

@Data
public class Deal {

	private int dealNo;
	private String buyer;
	private int productNo;
	private String type;
	private String active;
	private int count;
	private int price;
	private String receiver;
	private String phone;
	private int postcode;
	private String roadAddr;
	private String jibunAddr;
	private String detailAddr;
	private String extraAddr;
	private boolean deleted;
	private Date regDate;
	
}

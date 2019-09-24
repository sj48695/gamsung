package com.gamsung.vo;

import java.util.Date;

import lombok.Data;

@Data
public class Deal {

	private int dealNo;
	private String buyer;
	private String productNo;
	private String active;
	private int count;
	private int price;
	private Date regDate;
	private boolean deleted;
	private String receiver;
	private String addr;
	private String phone;
	
}

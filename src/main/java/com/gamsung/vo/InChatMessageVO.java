package com.gamsung.vo;

import java.util.Date;

import lombok.Data;

@Data
public class InChatMessageVO {
	
	private int message_no;
	private int product_no;
	private String sender;
	private String contents;
	private String type;
	private Date send_date;
	@Override
	public String toString() {
		return "InChatMessageVO [ content=" + contents + 
				", type=" + type +
				", sender=" + sender
				+ "]";
	}
}

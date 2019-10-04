package com.gamsung.vo;

import java.util.Date;

import lombok.Data;

@Data
public class Reply {
	
	private int replyNo;
	private int productNo;
	private String id;
	private String contents;
	private int level;
	private int step;
	private int depth;
	private Date regDate;
	
	
}

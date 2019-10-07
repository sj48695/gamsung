package com.gamsung.vo;

import java.util.Date;

import lombok.Data;

@Data
public class Comment {
	
	private int commentNo;
	private int productNo;
	private String writer;
	private String contents;
	private int groupNo;
	private int step;
	private int depth;
	private Date regDate;
	
	
}

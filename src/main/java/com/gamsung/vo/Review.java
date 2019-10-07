package com.gamsung.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Review {

	private int dealNo;
	private String contents;
	private float rating;
	private Date regDate;
	
	private String buyer;
	private int productNo;
	private String name;
	private String seller;
	private String imgFileName;
	private float staravg;
	
	private List<ReviewFile> files;
}

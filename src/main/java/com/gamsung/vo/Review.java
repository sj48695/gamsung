package com.gamsung.vo;

import java.util.ArrayList;
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
	private String img_file_name;
	
	private ArrayList<ReviewFile> files;
}

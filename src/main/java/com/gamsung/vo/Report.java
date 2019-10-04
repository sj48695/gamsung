package com.gamsung.vo;

import java.util.Date;

import lombok.Data;

@Data
public class Report {

	private int reportNo; 
	private String user; 
	private String reporter; 
	private String title; 
	private String contents; 
	private Date regDate;

}

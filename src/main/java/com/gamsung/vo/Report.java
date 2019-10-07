package com.gamsung.vo;

import java.util.Date;

import lombok.Data;

@Data
public class Report {

	private int reportNo; 
	private String user; //신고대상
	private String reporter; //신고접수하는 유저
	private String title; 
	private String contents; 
	private Date regDate;
	private String confirm;
	private String answer;

}

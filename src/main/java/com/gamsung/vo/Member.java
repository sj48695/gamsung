package com.gamsung.vo;

import java.util.Date;

import lombok.Data;

@Data
public class Member {
	private String id;
	private String pwd;
	private String phone;
	private String type;
	private boolean active;
	private Date regDate;
	private String nickname;
}

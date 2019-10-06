package com.gamsung.vo;

import lombok.Data;

@Data
public class Heart {
	
	private String id; //회원 아이디
	private int productNo; //상품번호
	
	private Integer cnt;	//찜 갯수
	private ProductFile file;

}

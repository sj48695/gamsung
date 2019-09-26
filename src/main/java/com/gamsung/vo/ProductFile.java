
package com.gamsung.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
public class ProductFile {

	private int productFileNo;
	private int productNo;
	private String saveFileName;
	private String rawFileName;
	private boolean flag;
	
	
}

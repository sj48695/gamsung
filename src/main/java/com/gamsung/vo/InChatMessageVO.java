package com.gamsung.vo;

import java.util.Date;

import lombok.Data;

@Data
public class InChatMessageVO {
	
	private int message_no;
	private String receiver;
	private String sender;
	private String contents;
	private Date send_date;
	

	private String senderNickName;
	private String receiverNickName;
	private String align;
	@Override
	public String toString() {
		return "InChatMessageVO [ content=" + contents +
				", senderId=" + sender +
				", senderNickName=" + senderNickName+"]";
	}
}

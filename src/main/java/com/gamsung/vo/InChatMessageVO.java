package com.gamsung.vo;

import java.util.Date;

import lombok.Data;

@Data
public class InChatMessageVO {
	
	private int messageNo;
	private String receiver;
	private String sender;
	private String contents;
	private Date sendDate;
	

	private String senderNickName;
	private String receiverNickName;
	private String relativeId;//상대아이디
	private String relativeNick;//상대닉네임
	private String profile;//상대이미지
	private String align;
	
	@Override
	public String toString() {
		return "InChatMessageVO [ content=" + contents +
				", receiver=" + receiver +
				", sender=" + sender +
				", senderNickName=" + senderNickName+
				", receiverNickName=" + receiverNickName+
				", relativeId=" + relativeId+
				", relativeNick=" + relativeNick+"]";
	}
	
	public void setRelativeId(InChatMessageVO message,String me) {
		if (message.getReceiver().equals(me)) {
			this.relativeId = message.getSender();
		} else if (message.getSender().equals(me)) {
			this.relativeId = message.getReceiver();
		}
	}
	
	public void setRelativeNick(InChatMessageVO message,String me) {
		if (message.getReceiver().equals(me)) {
			this.relativeNick = message.getSenderNickName();
		} else if (message.getSender().equals(me)) {
			this.relativeNick = message.getReceiverNickName();
		}
	}
	
}

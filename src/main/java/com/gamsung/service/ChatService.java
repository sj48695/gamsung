package com.gamsung.service;

import java.util.List;

import com.gamsung.vo.InChatMessageVO;

public interface ChatService {

	List<InChatMessageVO> findMessageList(String receiver, String sender);

	void sendMessage(InChatMessageVO message);

}

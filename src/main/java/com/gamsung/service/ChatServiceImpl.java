package com.gamsung.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamsung.mapper.MessageMapper;
import com.gamsung.vo.InChatMessageVO;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	MessageMapper messageMapper;

	@Override
	public List<InChatMessageVO> findMessageList(String receiver, String sender) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("receiver", receiver);
		params.put("sender", sender);
		List<InChatMessageVO> messages = messageMapper.selectMessageList(params);

		return messages;
	}

	@Override
	public void sendMessage(InChatMessageVO message) {
		messageMapper.insertMessage(message);
	}

}

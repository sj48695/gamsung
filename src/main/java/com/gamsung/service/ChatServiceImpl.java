package com.gamsung.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamsung.mapper.MemberMapper;
import com.gamsung.mapper.MessageMapper;
import com.gamsung.vo.InChatMessageVO;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	MessageMapper messageMapper;

	@Autowired
	MemberMapper memberMapper;

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

	@Override
	public List<InChatMessageVO> findMyChatList(String me) {
		List<InChatMessageVO> messages = messageMapper.selectMyChatList(me);
		for (InChatMessageVO message : messages) {
			message.setRelativeId(message,me);
			message.setRelativeNick(message,me);
			String profile = memberMapper.selectProfileImgById(message.getRelativeId());
			message.setProfile(profile);
		}

		return messages;
	}

}

package com.gamsung.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamsung.service.ChatService;
import com.gamsung.service.MemberService;
import com.gamsung.vo.InChatMessageVO;
import com.gamsung.vo.Member;

@Controller
public class ChatController {

	protected static final Logger logger = LoggerFactory.getLogger(ChatController.class);

	@Autowired
	private ChatService chatService;

	@Autowired
	private MemberService memberService;

	@GetMapping(path = "/member/chatting/{receiverId}")
	public String chattingForm(@PathVariable String receiverId, Model model, HttpServletRequest req) {
		Member receiver = memberService.findMemberById(receiverId);
		Authentication auth = (Authentication) req.getUserPrincipal();
		String sender = auth.getName();
		List<InChatMessageVO> messages = chatService.findMessageList(receiverId, sender);
		for (InChatMessageVO msg : messages) {
			if (msg.getReceiver().equals(sender))
				msg.setAlign("left");

			else if (msg.getSender().equals(sender))
				msg.setAlign("right");
			String profile = memberService.findProfileImgById(msg.getSender());
			msg.setProfile(profile);
			
		}
		model.addAttribute("receiver", receiver);
		model.addAttribute("messages", messages);
		return "member/chatting";
	}

	@MessageMapping("/hello")
	@SendTo("/topic/roomId")
	public InChatMessageVO broadcasting(InChatMessageVO message) {
		chatService.sendMessage(message);
		System.out.println("message : " + message);
		return message;
	}

	
	@GetMapping(path = "/member/chattingList")
	@ResponseBody
	public List<InChatMessageVO> getMemberList(HttpServletRequest req) {
		Authentication auth = (Authentication) req.getUserPrincipal();
		String sender = auth.getName();
		List<InChatMessageVO> messages = chatService.findMyChatList(sender);
		return messages;
	}
}

package com.gamsung.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
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

	private static HashMap<String, String> clients = new HashMap<String, String>();

	@GetMapping(path = "/member/chatting/{receiverId}")
	public String chattingForm(@PathVariable String receiverId, Model model, HttpServletRequest req) {
		Member receiver = memberService.findMemberById(receiverId);
		Authentication auth = (Authentication) req.getUserPrincipal();
		String sender = auth.getName();
		List<InChatMessageVO> messages = chatService.findMessageList(receiverId, sender);
		for (InChatMessageVO msg : messages) {
			if (msg.getReceiver().equals(sender))
				msg.setAlign("start");

			else if (msg.getSender().equals(sender))
				msg.setAlign("end");
//			String profile = memberService.findProfileImgById(msg.getSender());
//			msg.setProfile(profile);

		}
		model.addAttribute("receiver", receiver);
		model.addAttribute("messages", messages);
		return "member/chatting";
	}

	@MessageMapping("/in")
	@SendTo("/topic/in")
	public String inroom(String message, SimpMessageHeaderAccessor session) {
		if (message != null && !message.equals("undefined")) {
			clients.put(session.getSessionId(), message);
			System.out.println(session.getSessionId());
			System.out.println("in User : " + message);
		}
		return message;
	}

	@MessageMapping("/hello")
	@SendTo("/topic/roomId")
	public InChatMessageVO broadcasting(InChatMessageVO message, SimpMessageHeaderAccessor session) {

		System.out.println(clients.get(session.getSessionId()));

		System.out.println("message : " + message);
		return message;
	}
	
	@MessageMapping("/receive")
	@SendTo("/topic/receive")
	public InChatMessageVO receiver(InChatMessageVO message, SimpMessageHeaderAccessor session) {

		chatService.sendMessage(message);
		System.out.println("receive message : " + message);
		return message;
	}

	@MessageMapping("/out")
	@SendTo("/topic/out")
	public String outroom(String message) {
		System.out.println("out message : " + message);
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

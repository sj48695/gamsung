package com.gamsung.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.gamsung.vo.InChatMessageVO;
import com.gamsung.vo.Member;


@Controller
public class ChatController {
	
	
	protected static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    
    @MessageMapping("/hello")
    @SendTo("/topic/roomId")
    public InChatMessageVO broadcasting(InChatMessageVO message) {
    	
    	System.out.println("message : "+message.getContents());
		return message;
    }

    @MessageMapping("/out")
    @SendTo("/topic/out")
    public String outroom(String message) {
    	System.out.println("out message : "+message);
    	return message;
    }
    @MessageMapping("/in")
    @SendTo("/topic/in")
    public String inroom(String message) {
    	System.out.println("in message : "+message);
    	return message;
    }
    
    
//    1:1
    @MessageMapping("/info")
    @SendToUser("/queue/info")
    public InChatMessageVO info(InChatMessageVO message, SimpMessageHeaderAccessor messageHeaderAccessor) {
//    	Member talker= messageHeaderAccessor.getSessionAttributes().get(SESSION).get(USER_SESSION_KEY);
    	System.out.println("info message : "+message);
    	return message;
    }
    
    
}

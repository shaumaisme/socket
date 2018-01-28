package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
@Controller
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

//    @MessageMapping({"/chat.sendMessage"})
//    @SendTo("/topic/public")
//    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
//        logger.info("Pesan baru dari " + chatMessage.getSender() + " - [ " + chatMessage.getType() + "] : " + chatMessage.getContent());
//        return chatMessage;
//    }

//    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/public")
//    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
//        // Add username in web socket session
//        logger.info("User connected : " + chatMessage.getSender());
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//        return chatMessage;
//    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        logger.info("User connected : " + chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        messagingTemplate.convertAndSend("/topic/"+chatMessage.getTopic(), chatMessage);
    }


    @MessageMapping("/chat.sendMessage")
    public void kirimPesan(@Payload ChatMessage chatMessage) {
        logger.info("Message From : " + chatMessage.getSender()+" content :"+chatMessage.getContent());
        messagingTemplate.convertAndSend("/topic/"+chatMessage.getTopic(), chatMessage);
    }

}

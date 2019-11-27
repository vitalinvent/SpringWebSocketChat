package com.vitalinvent.springwebsocketchat.controller;

import com.vitalinvent.springwebsocketchat.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

@org.springframework.stereotype.Controller
public class Controller {

    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public Message register(@Payload Message message, SimpMessageHeaderAccessor simpMessageHeaderAccessor){
        simpMessageHeaderAccessor.getSessionAttributes().put("username",message.getAuthor());
        return message;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public  Message sendMessage(@Payload Message message){
        return message;
    }
}

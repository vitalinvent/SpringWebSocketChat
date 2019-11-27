package com.vitalinvent.springwebsocketchat.controller;

import com.vitalinvent.springwebsocketchat.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@org.springframework.stereotype.Controller
public class Controller {

    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public Message register(@Payload Message message, SimpMessageHeaderAccessor simpMessageHeaderAccessor){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername(); //get logged in username
        simpMessageHeaderAccessor.getSessionAttributes().put("username",message.getAuthor());
        return message;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public  Message sendMessage(@Payload Message message){
        return message;
    }
}

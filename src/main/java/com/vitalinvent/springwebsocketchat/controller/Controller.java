package com.vitalinvent.springwebsocketchat.controller;

import com.vitalinvent.springwebsocketchat.model.Message;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.io.IOException;
import java.util.Random;

@org.springframework.stereotype.Controller
public class Controller {

    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public Message register(@Payload Message message, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String name = user.getUsername(); //get logged in username
//        simpMessageHeaderAccessor.getSessionAttributes().put("username",message.getAuthor());
        simpMessageHeaderAccessor.getSessionAttributes().put("username", message.getAuthor());
        return message;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        return message;
    }

    @MessageMapping("/chat.sendtobot")
    @SendTo("/topic/public")
    public Message sendMessageToBot(@Payload Message message) {
        message.setAuthor("bot:");

        boolean getAgain = true;
        Document doc = null;
        String url = null;
        String description = null;

        while (getAgain) {// FIXME: 27.11.2019 make some method for fast articles number search or getting
            url = "https://habr.com/en/post/" + new Random().nextInt(500000) + "/";
            try {
                doc = Jsoup.connect(url).get();
                description = doc.select("meta[name=description]").get(0).attr("content");
            } catch (IOException e) {
                e.printStackTrace();
                getAgain = true;
            }
            getAgain = false;
        }

        message.setContent("<h4>" + getLeftString(description, 30) + "...</h4><a href=\"" + url + ">" + url + "</a>");
        return message;
    }

    public static String getLeftString(String st, int length) {
        int stringlength = st.length();

        if (stringlength <= length) {
            return st;
        }

        return st.substring((stringlength - length));
    }

}

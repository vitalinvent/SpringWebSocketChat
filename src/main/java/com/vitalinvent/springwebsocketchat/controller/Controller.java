package com.vitalinvent.springwebsocketchat.controller;

import com.vitalinvent.springwebsocketchat.model.Message;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
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
        // FIXME: 27.11.2019 Here can add command to bot
        boolean getAgain = true;
        Document doc = null;
        String url = null;
        String description = null;
        org.w3c.dom.Document docXml = null;

//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        DocumentBuilder db = null;
//        try {
//            db = dbf.newDocumentBuilder();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        }
//        try {
//            docXml = db.parse(new URL("https://habr.com/ru/rss/all/all/").openStream());
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        do {// FIXME: 27.11.2019 make some method for fast articles number search or getting
            url = "https://habr.com/en/post/" + new Random().nextInt(500000) + "/";
            try {
                doc = Jsoup.connect(url).get();
                description = doc.select("meta[name=description]").get(0).attr("content");
            } catch (IOException e) {
                getAgain = true;
            }
            if (description == null) {
                getAgain = true;
            } else {
                getAgain = false;
            }
        } while (getAgain);

        message.setContent("<b>" + getLeftString(description, 25) + "...</b><a target=\"_blank\" rel=\"noopener noreferrer\"  href=\"" + url + "\">" + url + "</a>");
        return message;
    }

    public static String getLeftString(String st, int length) {
        int stringlength = st.length();

        if (stringlength <= length) {
            return st;
        }

        return st.substring(0,(stringlength - length));
    }

}

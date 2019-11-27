package com.vitalinvent.springwebsocketchat.model;

public class Message {
    private String content;
    private String author;
    private Type type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public  enum Type{
        CHAT,LEAVE,JOIN
    }
}

package com.example.rentatease.model;


public class Chat {

    String chatId, msgFrom, msgTo, msgFromName, msgToName, message, datetime;

    public Chat() {
    }

    public Chat(String chatId, String msgFrom, String msgTo, String msgFromName, String msgToName, String message, String datetime) {
        this.chatId = chatId;
        this.msgFrom = msgFrom;
        this.msgTo = msgTo;
        this.msgFromName = msgFromName;
        this.msgToName = msgToName;
        this.message = message;
        this.datetime = datetime;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getMsgFrom() {
        return msgFrom;
    }

    public void setMsgFrom(String msgFrom) {
        this.msgFrom = msgFrom;
    }

    public String getMsgTo() {
        return msgTo;
    }

    public void setMsgTo(String msgTo) {
        this.msgTo = msgTo;
    }

    public String getMsgFromName() {
        return msgFromName;
    }

    public void setMsgFromName(String msgFromName) {
        this.msgFromName = msgFromName;
    }

    public String getMsgToName() {
        return msgToName;
    }

    public void setMsgToName(String msgToName) {
        this.msgToName = msgToName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}

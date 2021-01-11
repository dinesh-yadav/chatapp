package com.chat.chatApplication.service;

import com.chat.chatApplication.mapper.MessageMapper;
import com.chat.chatApplication.model.ChatForm;
import com.chat.chatApplication.model.ChatMessage;

import javax.annotation.PostConstruct;
import java.util.List;

public class MessageService {
    private MessageMapper messageMapper;

    public MessageService(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating MessageService bean");
    }

    public void addMessage(ChatForm chatForm) {
        ChatMessage newMessage = new ChatMessage();
        newMessage.setUsername(chatForm.getUsername());
        switch (chatForm.getMessageType()) {
            case "Say":
                newMessage.setMessageText(chatForm.getMessageText());
                break;
            case "Shout":
                newMessage.setMessageText(chatForm.getMessageText().toUpperCase());
                break;
            case "Whisper":
                newMessage.setMessageText(chatForm.getMessageText().toLowerCase());
                break;
        }
        messageMapper.addMessage(newMessage);
    }

    public List<ChatMessage> getChatMessages() {
        return messageMapper.getAllMessages();
    }
}

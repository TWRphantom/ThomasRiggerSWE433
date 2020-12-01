package com.chatwindow.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Used for data persistance, to keep track of user messages
public class User {

    private String id;
    private List <String> messages;
    private List <String> friends;


    public User(String id){
        this.id = id;
        this.messages = new ArrayList<String>();
        this.friends = new ArrayList<String>();
    };

    public List<String> getFriends() {
        return friends;
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getId() {
        return id;
    }

    public void addMessage(String message){
        this.messages.add(message);
    }

}

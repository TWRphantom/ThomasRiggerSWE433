package com.chatwindow.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.chatwindow.domain.WebSocketChatMessage;

//Listens to the html/js code for user actions
//and acts accordingly
@Component
public class WebSocketChatEventListener {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        //StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        //String username = (String) headerAccessor.getSessionAttributes().get("username");

        System.out.println("Received a new web socket connection");
        //System.out.println("\n THERES NO WAY IM THIS STUPID " + username);
        //userController.createNewUser(username);
    }

    /*@RequestMapping(value="/welcomeForm", method = RequestMethod.POST)
    public void createUser(@RequestParam("welcomeForm") String username){

        System.out.println(username);
        userController.createNewUser(username);
    }*/

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        System.out.println("\n Disconnect: " + username);
        if(username != null) {

            WebSocketChatMessage chatMessage = new WebSocketChatMessage();
            chatMessage.setType("Leave");
            chatMessage.setSender(username);

            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
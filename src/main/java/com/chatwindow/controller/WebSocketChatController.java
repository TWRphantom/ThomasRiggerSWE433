package com.chatwindow.controller;

import com.chatwindow.entity.User;
import com.chatwindow.service.ImageService;
import com.chatwindow.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.chatwindow.domain.WebSocketChatMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Scanner;

//Gets called upon by the script.js file
//to take action based on user action
@Controller
public class WebSocketChatController {
    @Autowired
    private UserService userService = new UserService();
    private ImageService imageService = new ImageService();

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/javainuse")
    public WebSocketChatMessage sendMessage(@Payload WebSocketChatMessage webSocketChatMessage) throws Exception {

        String message = webSocketChatMessage.getContent();
        System.out.println(message);
        userService.updateMessageList(userService.getUserByName(webSocketChatMessage.getSender()), message);
        System.out.println(message.compareTo("/sendImage"));
        //Send images using built in command
        if(message.compareTo("/sendImage") == 0){
            imageService.getImage();
        }
        else if(message.compareTo("/decrypt") == 0){
            //Proof of concept for decryption
            //We had connection issues between microservices
            //Due to GMU not allowing port forwarding so we'll
            //just get the decrypted message from a url
            URL url = new URL("http://69.244.76.36:8080/message?user=tylers&file=theimagefilename&enc=none");
            Scanner sc = new Scanner(url.openStream());
            StringBuffer sb = new StringBuffer();
            while(sc.hasNext()) {
                sb.append(sc.next());
            }
            String result = sb.toString();
            result = result.replaceAll("<[^>]*>", "");
            System.out.println(result);
            webSocketChatMessage.setContent(result);
        }
        return webSocketChatMessage;
    }

    /*@MessageMapping("/chat.sendMessage")
    @SendTo("/topic/javainuse")
    public WebSocketImage sendMessage(@Payload WebSocketImage webSocketImageMessage) {
        return webSocketImageMessage;
    }*/

    @MessageMapping("/chat.newUser")
    @SendTo("/topic/javainuse")
    public WebSocketChatMessage newUser(@Payload WebSocketChatMessage webSocketChatMessage,
                                        SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", webSocketChatMessage.getSender());
        userService.checkForNewUser((String) webSocketChatMessage.getSender());

        return webSocketChatMessage;
    }

    //These were going to be used for downloading/uploading images
    //Upload image to database
    @PostMapping("/uploadImage")
    public void uploadImage(@RequestParam("imageFile") MultipartFile imageFile) throws Exception {
        System.out.println("Posting Image");
        //String returnValue = "";
        //userService.save(imageFile);
        imageService.getImage();//uploading is not working. this is a placeholder
        //return returnValue;
    }

    //Download image from database
    @GetMapping("/getImage")
    public void getImage() throws Exception {//@RequestParam("getImageFile") MultipartFile imageFile) throws Exception {
        System.out.println("Getting Image");
        //String returnValue = "";
        //userService.save(imageFile);
        imageService.getImage();
        //return returnValue;
    }
}
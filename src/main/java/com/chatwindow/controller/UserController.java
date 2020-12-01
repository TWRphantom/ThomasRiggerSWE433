package com.chatwindow.controller;

import com.chatwindow.entity.User;
import com.chatwindow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

//UserController is used for keeping track of all of our USER data
//It is not a controller in the same way WebSocketChatController is.
//This class ensures that user data is available on http://localhost:8080/users
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService = new UserService();

    @RequestMapping(method = RequestMethod.GET)
    public Collection<User> getAll(){
        return userService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertUser(@RequestBody User user){
        userService.addNewUser(user);
    }
}

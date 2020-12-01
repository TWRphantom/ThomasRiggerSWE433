package com.chatwindow.service;

import com.chatwindow.data.UsersData;
import com.chatwindow.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

//The bridge between front and backend.
//Most of the "presenter" actions take place here
@Service
public class UserService {
    @Autowired
    private UsersData usersData;

    public Collection<User> getAllUsers(){
        return usersData.getAll();
    }

    public void updateMessageList(User user, String message){
        usersData.updateMessageListByName(user, message);
    }

    public User getUserByName(String id){
        return usersData.getUserByName(id);
    }

    public void addNewUser(User user){
        usersData.addNewUser(user);
    }

    public void checkForNewUser(String username){
        System.out.println("\nUser... " + username);
        if(userExists(username) != true) {
            User newUser = new User(username);
            System.out.println("\nNew User... " + newUser.getId());
            addNewUser(newUser);
        }
    }

    public boolean userExists(String username){
        return usersData.userExists(username);
    }
}

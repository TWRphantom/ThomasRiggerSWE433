package com.chatwindow.data;
import com.chatwindow.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.*;

//Keeps track of messaging and user data. This is where our
//"model" part of the Component Diagram comes from
@Repository
public class UsersData {
    private ObjectMapper mapper;
    private ObjectWriter writer;

    private static Map<String,User> allUsers;

    {
        allUsers = new HashMap<String,User>(){
            {
                put("default", new User("Default1"));
            }
        };
        //Make sure allUsers is initalized
        allUsers = new HashMap<String,User>(){};
        //Load data from data file
        loadData();
    }

    public Collection<User> getAll(){
        return allUsers.values();
    }

    public boolean userExists(String username){
        return (allUsers.get(username) != null);
    }

    public User getUserByName(String username) {
        System.out.println(allUsers.get(username));
        return allUsers.get(username);
    }

    public void updateMessageListByName(User user, String message){
        User ud = allUsers.get(user.getId());
        ud.addMessage(message);
        saveData();
    }

    public void addNewUser(User user){
        allUsers.put(user.getId(), user);
        System.out.println("User Added: "+ getAll().toString());
        saveData();
    }

    //Save data for persistance
    public void saveData() {
        System.out.println("Saving...");
        mapper = new ObjectMapper();
        writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(new File("data.json"), allUsers);//new File("data.json"), allUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Load data from previous sessions
    public void loadData() {
        System.out.println("Loading...");
        mapper = new ObjectMapper();
        try {

            File f = new File("data.json");

            allUsers = mapper.readValue(f, new TypeReference<Map<String,Object>>() {});
        } catch (IOException e) {
            allUsers = null;
            e.printStackTrace();
        }
    }
}

package com.csgame.api.csgameapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.csgame.api.csgameapi.model.User;

public class UserFileDAO implements UserDAO {
    Map<String, User> users;

    private ObjectMapper objectMapper;

    private String filename;

    public UserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        
        load();
    }

    private User[] getUsersArray() {
        ArrayList<User> userArray = new ArrayList<>();

        for (User u : users.values())
            userArray.add(e);
        
        return userArray;
    }

    public User getUser(String username) {
        synchronized(users) {
            if (users.containsKey(username))
                return users.get(username);
            else
                return null;
        }
    }

    public User[] getUsers() {
        synchronized(users) {
            return getUsersArray();
        }
    }

    private boolean save() throws IOException {
        User[] userArray = getUsersArray();

        objectMapper.writeValue(new File(filename), userArray);
        return true;
    }

    private boolean load() throws IOException {
        users = new TreeMap<>();

        User[] userArray = objectMapper.readValue(new File(filename), User[].class);

        for (User user : userArray)
            users.put(user.getUsername(), user);
        
        return true;
    }

    public User login(String username, String password) {
        for (org.apache.catalina.User u : users.values())
            if (u.getUsername() == username && u.getPassword() == password)
                return u;
        
        return null;
    }
}

package com.csgame.api.csgameapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.csgame.api.csgameapi.model.NPOUser;

@Component
public class NPOUserFileDAO implements NPOUserDAO {
    Map<String, NPOUser> users;

    private ObjectMapper objectMapper;

    private String filename;

    public NPOUserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;

        load();
    }

    private NPOUser[] getUsersArray() {
        ArrayList<NPOUser> userArray = new ArrayList<>();

        for (NPOUser u : users.values())
            userArray.add(u);

        NPOUser[] array = (NPOUser[]) userArray.toArray();

        return array;
    }

    public NPOUser getUser(String UID) {
        synchronized (users) {
            for (NPOUser u : users.values())
                if (u.getUID().equals(UID))
                    return u;

            return null;
        }
    }

    public NPOUser[] getUsers() {
        synchronized (users) {
            return getUsersArray();
        }
    }

    private boolean save() throws IOException {
        NPOUser[] userArray = getUsersArray();

        objectMapper.writeValue(new File(filename), userArray);
        return true;
    }

    private boolean load() throws IOException {
        users = new TreeMap<>();

        NPOUser[] userArray = objectMapper.readValue(new File(filename), NPOUser[].class);

        for (NPOUser user : userArray)
            if (user.getUID().substring(0, 1).equals("O"))
                users.put(user.getUsername(), user);

        return true;
    }

    public NPOUser login(String username, String password) {
        for (NPOUser u : users.values()) {
            if (u.getUsername() == username && u.getPassword() == password)
                return u;
        }

        return null;
    }

    public NPOUser updateUser(NPOUser user) {
        return user;
    }
}

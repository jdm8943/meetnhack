package com.csgame.api.csgameapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.csgame.api.csgameapi.model.CompanyUser;

@Component
public class CompanyUserFileDAO {
    Map<String, CompanyUser> users;

    private ObjectMapper objectMapper;

    private String filename;

    public CompanyUserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;

        load();
    }

    private CompanyUser[] getUsersArray() {
        ArrayList<CompanyUser> userArray = new ArrayList<>();

        for (CompanyUser u : users.values())
            userArray.add(u);

        CompanyUser[] array = (CompanyUser[]) userArray.toArray();

        return array;
    }

    public CompanyUser getUser(String UID) {
        synchronized (users) {
            for (CompanyUser u : users.values())
                if (u.getUID().equals(UID))
                    return u;

            return null;
        }
    }

    public CompanyUser[] getUsers() {
        synchronized (users) {
            return getUsersArray();
        }
    }

    private boolean save() throws IOException {
        CompanyUser[] userArray = getUsersArray();

        objectMapper.writeValue(new File(filename), userArray);
        return true;
    }

    private boolean load() throws IOException {
        users = new TreeMap<>();

        CompanyUser[] userArray = objectMapper.readValue(new File(filename), CompanyUser[].class);

        for (CompanyUser user : userArray)
            if (user.getUID().substring(0, 1).equals("C"))
                users.put(user.getUsername(), user);

        return true;
    }

    public CompanyUser login(String username, String password) {
        for (CompanyUser u : users.values()) {
            if (u.getUsername() == username && u.getPassword() == password)
                return u;
        }

        return null;
    }

    public CompanyUser updateUser(CompanyUser user) {
        return user;
    }
}
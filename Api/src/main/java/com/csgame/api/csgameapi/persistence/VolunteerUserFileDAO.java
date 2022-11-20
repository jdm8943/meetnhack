package com.csgame.api.csgameapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.csgame.api.csgameapi.model.VolunteerUser;

@Component
public class VolunteerUserFileDAO implements VolunteerUserDAO {
    Map<String, VolunteerUser> users;

    private ObjectMapper objectMapper;

    private static int nextID;
    private String filename;

    public VolunteerUserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;

        load();
    }

    private VolunteerUser[] getUsersArray() {
        ArrayList<VolunteerUser> userArray = new ArrayList<>();

        for (VolunteerUser u : users.values())
            userArray.add(u);

        VolunteerUser[] array = (VolunteerUser[]) userArray.toArray();

        return array;
    }

    public VolunteerUser getUser(String UID) throws IOException {
        synchronized (users) {
            for (VolunteerUser u : users.values())
                if (u.getUID().equals(UID))
                    return u;

            return null;
        }
    }


    public VolunteerUser getVUser(String UID) throws IOException {
        synchronized (users) {
            VolunteerUser gotUser = users.get(UID);
            // System.out.println(gotUser.getName());
            // System.out.println(gotUser.getCurrentPoints());
            // System.out.println(gotUser.getLevel());
            // System.out.println(gotUser.getClaimedDiscounts());
            // System.out.println(gotUser.getEventsJoined());
            return gotUser;
        }
    }



    public VolunteerUser[] getUsers() {
        synchronized (users) {
            return getUsersArray();
        }
    }

    // private boolean save() throws IOException {
    //     VolunteerUser[] userArray = getUsersArray();

    //     objectMapper.writeValue(new File(filename), userArray);
    //     return true;
    // }

    private boolean load() throws IOException {
        users = new TreeMap<>();
        nextID = 0;

        VolunteerUser[] userArray = objectMapper.readValue(new File(filename), VolunteerUser[].class);

        for (VolunteerUser user : userArray) {
            System.out.println(user.getUID().substring(0, 1).equals("V"));
            if (user.getUID().substring(0, 1).equals("V")){
                users.put(user.getUID(), user);
                System.out.println(user.getUID());
                System.out.println(user.getName());
                System.out.println(user.getCurrentPoints());
                System.out.println(user.getLevel());
                System.out.println(user.getClaimedDiscounts());
                System.out.println(user.getEventsJoined());
            }
        }

        return true;
    }

    private synchronized static int nextID() {
        int id = nextID;
        nextID += 1;
        return id;
    }
    
    public VolunteerUser createUser(VolunteerUser u) throws IOException {
        synchronized(users) {
            VolunteerUser user = new VolunteerUser("V" + nextID(), u.getUsername(), u.getPassword(),
                                        u.getName(), u.getCurrentPoints(), u.getLevel(),
                                        u.getClaimedDiscounts(), u.getEventsJoined());
            users.put(user.getUID(), user);
            save();
            return user;
        }
    }

    private boolean save() throws IOException {
        VolunteerUser[] userArray = getUsersArray();

        objectMapper.writeValue(new File(filename), userArray);
        return true;
    }

    public VolunteerUser login(String username, String password) {
        for (VolunteerUser u : users.values()) {
            if (u.getUsername() == username && u.getPassword() == password)
                return u;
        }

        return null;
    }

    public VolunteerUser updateUser(VolunteerUser user) throws IOException {
        synchronized(users) {
            if (users.containsKey(user.getUID()) == false)
                return null;  // user does not exist

            users.put(user.getUID(),user);
            save(); // may throw an IOException
            return user;
        }
    }
}

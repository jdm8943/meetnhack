package com.csgame.api.csgameapi.persistence;

import com.csgame.api.csgameapi.model.User;
import com.csgame.api.csgameapi.model.VolunteerUser;

import java.io.IOException;

public interface UserDAO {
    User[] getUsers() throws IOException;

    // VolunteerUser getVUser(String username) throws IOException;

    User getUser(String username) throws IOException;

    User updateUser(User updatedUser) throws IOException;

    User login(String username, String password) throws IOException;
}

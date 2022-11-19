package com.csgame.api.csgameapi.persistence;

import com.csgame.api.csgameapi.model.User;
import java.io.IOException;

public interface UserDAO {
    User[] getUsers() throws IOException;

    User getUser(String username) throws IOException;

    User updateUser(User updatedUser) throws IOException;

    User login(String username, String password) throws IOException;
}

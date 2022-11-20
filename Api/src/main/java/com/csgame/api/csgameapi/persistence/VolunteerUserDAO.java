package com.csgame.api.csgameapi.persistence;

import java.io.IOException;

import com.csgame.api.csgameapi.model.VolunteerUser;

public interface VolunteerUserDAO {
    VolunteerUser getUser(String username) throws IOException;

    VolunteerUser getVUser(String UID) throws IOException;

    VolunteerUser updateUser(VolunteerUser updatedUser) throws IOException;

    VolunteerUser login(String username, String password);
}

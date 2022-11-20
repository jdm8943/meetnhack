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

public interface VolunteerUserDAO {
    VolunteerUser getUser(String username) throws IOException;

    VolunteerUser updateUser(VolunteerUser updatedUser) throws IOException;

    VolunteerUser login(String username, String password);
}

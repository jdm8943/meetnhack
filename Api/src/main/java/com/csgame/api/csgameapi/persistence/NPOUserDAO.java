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

public interface NPOUserDAO {
    NPOUser getUser(String username) throws IOException;

    NPOUser updateUser(NPOUser updatedUser) throws IOException;

    NPOUser login(String username, String password);
    
    NPOUser createUser(NPOUser u) throws IOException;
}

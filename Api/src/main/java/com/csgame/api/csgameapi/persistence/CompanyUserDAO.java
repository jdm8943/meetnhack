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

public interface CompanyUserDAO {
    CompanyUser getUser(String username) throws IOException;

    CompanyUser updateUser(CompanyUser updatedUser) throws IOException;

    CompanyUser login(String username, String password);
}

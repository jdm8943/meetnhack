package com.csgame.api.csgameapi.persistence;

import java.io.IOException;

import com.csgame.api.csgameapi.model.CompanyUser;

public interface CompanyUserDAO {
    CompanyUser getUser(String username) throws IOException;

    CompanyUser updateUser(CompanyUser updatedUser) throws IOException;

    CompanyUser login(String username, String password);
}

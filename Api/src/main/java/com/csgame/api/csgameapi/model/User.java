package com.csgame.api.csgameapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class User {
    protected String STRING_FORMAT = "USER [UID=%d, username=%s, password=%s]";

    @JsonProperty("UID")
    private String UID;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

    public User(@JsonProperty("UID") String UID, @JsonProperty("username") String username,
            @JsonProperty("password") String password) {
        this.UID = UID;
        this.username = username;
        this.password = password;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String uID) {
        UID = uID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

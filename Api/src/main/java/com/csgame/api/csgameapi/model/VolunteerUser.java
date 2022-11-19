package com.csgame.api.csgameapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VolunteerUser extends User {

    @JsonProperty("name")
    private String name;
    @JsonProperty("currentPoints") 
    private int currentPoints;
    @JsonProperty("level") 
    private double level;
    @JsonProperty("claimedDiscountID") 
    private Discount[] claimedDiscountID;

    public VolunteerUser(String UID, String username, String password, 
                        @JsonProperty("name") String name,
                        @JsonProperty("currentPoints") int currentPoints,
                        @JsonProperty("level") double level,
                        @JsonProperty("claimedDiscountID") Discount[] claimedDiscountID) {
        super(UID, username, password);
        this.name = name;
        this.currentPoints = currentPoints;
        this.level = level;
        this.claimedDiscountID = claimedDiscountID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public Discount[] getClaimedDiscountID() {
        return claimedDiscountID;
    }

    public void setClaimedDiscountID(Discount[] claimedDiscountID) {
        this.claimedDiscountID = claimedDiscountID;
    }
}

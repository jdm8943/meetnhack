package com.csgame.api.csgameapi.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VolunteerUser extends User {

    @JsonProperty("name")
    private String name;
    @JsonProperty("currentPoints") 
    private int currentPoints;
    @JsonProperty("level") 
    private double level;
    @JsonProperty("claimedDiscounts") 
    private ArrayList<Discount> claimedDiscounts;
    @JsonProperty("eventsJoined")
    private ArrayList<Event> eventsJoined; 

    public VolunteerUser(String UID, String username, String password, 
                        @JsonProperty("name") String name) {
        super(UID, username, password);
        this.name = name;
        this.currentPoints = 0;
        this.level = 0;
        this.claimedDiscounts = new ArrayList<>();
        this.eventsJoined = new ArrayList<>();
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

    public boolean addEvent(Event event) {
        return eventsJoined.add(event);
    }

    //Returns -1 if not added
    public int completeEvent(Event event) {
        int added = -1;
        for (Event e : eventsJoined){
            if (e.getEventID() == event.getEventID()){
                currentPoints += event.getPoints();
                level += event.getPoints()/1000;
                added = event.getPoints();
                break;
            }
        }
        return added;
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

    public ArrayList<Discount> getClaimedDiscounts() {
        return this.claimedDiscounts;
    }

    public void setClaimedDiscountID(ArrayList<Discount> claimedDiscounts) {
        this.claimedDiscounts = claimedDiscounts;
    }


}

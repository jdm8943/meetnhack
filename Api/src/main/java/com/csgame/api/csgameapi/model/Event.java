package com.csgame.api.csgameapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class Event {
    protected String STRING_FORMAT = "EVENT [eventID=%d, orgID=%s, eventName=%s, Description=%s, points=%d]";

    @JsonProperty("eventID")
    private int eventID;
    @JsonProperty("orgID")
    private String orgID;
    @JsonProperty("eventName")
    private String eventName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("points")
    private int points;

    public Event(@JsonProperty("eventID") int eventID, @JsonProperty("orgID") String orgID,
            @JsonProperty("eventName") String eventName, @JsonProperty("description") String description,
            @JsonProperty("points") int points) {
        this.eventID = eventID;
        this.orgID = orgID;
        this.eventName = eventName;
        this.description = description;
        this.points = points;
    }

    public int getEventID() {
        return this.eventID;
    }

    public String getOrgID() {
        return this.orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}

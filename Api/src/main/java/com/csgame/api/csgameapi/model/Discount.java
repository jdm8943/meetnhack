package com.csgame.api.csgameapi.model.Discount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Discount {

    public static final String STRING_FORMAT = "Discount [id=%d, name=%s, levelRequired=%d, pointsRequired=%d, companyId]";

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("levelRequired")
    private int levelRequired;
    @JsonProperty("pointsRequired")
    private int pointsRequired;
    @JsonProperty("companyId")
    private int companyId;


    @JsonCreator
    public Discount(@JsonProperty("id") int id,
    @JsonProperty("name") String name,
    @JsonProperty("levelRequired") int levelRequired,
    @JsonProperty("pointsRequired") int pointsRequired,
    @JsonProperty("companyId") int companyId){
        this.id = id;
        this.name = name;
        this.levelRequired = levelRequired;
        this.pointsRequired = pointsRequired;
        this.companyId = companyId;
    }

    public int getId(){ return this.id(); }

    public String geName(){ return this.name(); }
    
    public int getLevelRequired(){ return this.levelRequired(); }

    public int getPointsRequired(){ return this.pointsRequired(); }

    public int getCompanyId(){ return this.companyId(); }
}

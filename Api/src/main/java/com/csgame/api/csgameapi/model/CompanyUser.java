package com.csgame.api.csgameapi.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyUser extends User {
    static final String STRING_FORMAT = "CompanyUser [UID=%s, username=%s, password=%s, compnayName=%s]";

    @JsonProperty("companyName")
    private String companyName;

    /**
     * Create a NPO User with the given id and name
     * 
     * @param UID      The id of the user
     * @param username The name of the user
     * 
     *                 {@literal @}JsonProperty is used in serialization and
     *                 deserialization
     *                 of the JSON object to the Java object in mapping the fields.
     *                 If a field
     *                 is not provided in the JSON object, the Java field gets the
     *                 default Java
     *                 value, i.e. 0 for int
     */
    public CompanyUser(@JsonProperty("UID") String UID,
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("companyName") String companyName) {
        super(UID, username, password);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}

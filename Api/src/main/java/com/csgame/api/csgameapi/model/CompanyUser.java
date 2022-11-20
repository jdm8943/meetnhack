package com.csgame.api.csgameapi.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyUser extends User {
    static final String STRING_FORMAT = "CompanyUser [UID=%s, username=%s, password=%s, companyName=%s]";

    @JsonProperty("companyName")
    private String companyName;
    private ArrayList<Discount> companyDiscounts;

    /**
     * Create a company User with the given id and name
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
            @JsonProperty("companyName") String companyName,
            @JsonProperty("companyDiscounts") ArrayList<Discount> companyDiscounts) {
        super(UID, username, password);
        this.companyName = companyName;
        this.companyDiscounts = companyDiscounts;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Discount addDiscount(int id, String name, int levelRequired, int pointsRequired) {
        Discount discount = new Discount(id, name, levelRequired, pointsRequired, Integer.parseInt(this.getUID().substring(1)));
        companyDiscounts.add(discount);
        return discount;
    }

    public ArrayList<Discount> getCompanyDiscounts(){
        return companyDiscounts;
    }
}

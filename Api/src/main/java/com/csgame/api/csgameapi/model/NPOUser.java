import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Non-profit Oraganization User
 * One-user login for every organization
 */

public class NPOUser extends User{ 
    // Package private for tests
    static final String STRING_FORMAT = "NPOUser [UID=%s, username=%s, password=%s, orgName=%s]";

    @JsonProperty("orgName")
    private String orgName;

    /**
     * Create a NPO User with the given id and name
     * @param UID The id of the user
     * @param username The name of the user
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public NPOUser(@JsonProperty("UID") String UID, 
                @JsonProperty("username") String username, 
                @JsonProperty("password") String password, 
                @JsonProperty("orgName") String orgName) {
        this.super(UID, username, password);
        this.orgName = UID;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
}

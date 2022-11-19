import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Non-profit Oraganization User
 * One-user login for every organization
 */

public class NPOUser { 
    // Package private for tests
    static final String STRING_FORMAT = "User [username=%s, password=%s, street=%s, street2=%s, city=%s, zipcode=%s, state=%s]";

    @JsonProperty("UID") private String UID;            // EX: O3
    @JsonProperty("username") private String username;  // EX: 
    @JsonProperty("password") private String password;
    @JsonProperty("orgName") private String orgName;

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
    public User(@JsonProperty("UID") String UID,
                @JsonProperty("username") String username,
                @JsonProperty("password") String password,
                @JsonProperty("orgName") String orgName) {
        this.UID = UID;
        this.username = username;
        this.password = password;
        this.orgName = UID;
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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
}

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class User {
    protected String STRING_FORMAT = "USER [uid=%d, username=%s]";

    @JsonProperty("UID")
    private String UID;
    @JsonProperty("username")
    private String name;
    @JsonProperty("password")
    private String password;

    public User(@JsonProperty("uid") int uid, @JsonProperty("username") String username) {
        this.uid = uid;
        this.username = username;
    }
}

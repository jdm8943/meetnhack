import java.io.IOException;

public interface UserDAO {
    User[] getUsers() throws IOException;

    User getUser(String UID) throws IOException;

    User updateUser(User updatedUser) throws IOException;

    void login(User user) throws IOException;
}

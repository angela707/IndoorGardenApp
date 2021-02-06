package angela.example.indoorgardenapp;


public class UserProfile {
    public String uid;
    public String first_name;
    public String last_name;
    public String email;
    public String username;
    public String password;

    public UserProfile(String uid, String first_name, String last_name, String email, String username, String password)
    {
        this.uid = uid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}

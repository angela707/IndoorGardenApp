package angela.example.indoorgardenapp;


public class UserProfile {
    public String uid;
    public String first_name;
    public String last_name;
    public String email;
    public String username;
    public String password;
    public int current_total;
    public int num_orders;

    UserProfile () {}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getCurrent_total() {
        return current_total;
    }

    public void setCurrent_total(int current_total) {
        this.current_total = current_total;
    }

    public int getNum_orders() {
        return num_orders;
    }

    public void setNum_orders(int num_orders) {
        this.num_orders = num_orders;
    }


    public UserProfile(String uid, String first_name, String last_name, String email, String username, String password, int current_total, int num_orders)
    {
        this.uid = uid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.current_total = current_total;
        this.num_orders = num_orders;
    }
}

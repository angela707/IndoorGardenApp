package angela.example.indoorgardenapp;

public class WishlistModel {
    public String user_uid;
    public String plant_uid;

    WishlistModel () {}

    public WishlistModel(String user_uid, String plant_uid) {
        this.user_uid = user_uid;
        this.plant_uid = plant_uid;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public String getPlant_uid() {
        return plant_uid;
    }

    public void setPlant_uid(String plant_uid) {
        this.plant_uid = plant_uid;
    }
}


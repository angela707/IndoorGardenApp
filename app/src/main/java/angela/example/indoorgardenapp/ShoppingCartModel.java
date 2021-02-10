package angela.example.indoorgardenapp;

public class ShoppingCartModel {

    String user_uid;
    String plant_uid;
    int amount;

    ShoppingCartModel() {}

    public ShoppingCartModel(String user_uid, String plant_uid, int amount) {
        this.user_uid = user_uid;
        this.plant_uid = plant_uid;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

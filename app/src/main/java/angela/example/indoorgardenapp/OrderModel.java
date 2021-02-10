package angela.example.indoorgardenapp;

import java.util.Map;

public class OrderModel {

    String user_id;
    String first_name;
    String last_name;
    String email;
    String number;
    String address;
    String city;
    String country;
    int full_price;
    Map<String, Integer> items;

    OrderModel () {}

    public OrderModel(String user_id, String first_name, String last_name, String email, String number, String address, String city, String country, int full_price, Map<String, Integer> items) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.number = number;
        this.address = address;
        this.city = city;
        this.country = country;
        this.full_price = full_price;
        this.items = items;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getFull_price() {
        return full_price;
    }

    public void setFull_price(int full_price) {
        this.full_price = full_price;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }
}

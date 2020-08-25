package com.example.exploremyindia;

public class TourCardModel {

    private String Name;
    private String Username;
    private String Rating;
    private String key_id;

    public TourCardModel(String name, String username, String rating, String key_id) {
        Name = name;
        Username = username;
        Rating = rating;
        this.key_id = key_id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }
}

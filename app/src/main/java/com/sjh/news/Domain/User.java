package com.sjh.news.Domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 25235 on 2018/7/13.
 */

public class User {
    private String username;
    private String password;
    private String email;
    private Map<String, Integer> userInterest;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userInterest = new HashMap<>();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Integer> getUserInterest() {
        return userInterest;
    }

    public void setUserInterest(Map<String, Integer> userInterest) {
        this.userInterest = userInterest;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userInterest=" + userInterest.keySet().toString() + "," + userInterest.values() +
                '}';
    }
}

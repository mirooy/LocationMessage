package com.example.cs160_sp18.prog3;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// custom class made for storing a message. you can update this class
public class Comment {

    public String text;
    public String username;
    public Date date;

    Comment(String text, String username, Date date) {
        this.text = text;
        this.username = username;
        this.date = date;
    }

    // returns a string indicating how long ago this post was made
    protected String elapsedTimeString() {
        long diff = new Date().getTime() - date.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        int daysInt = Math.round(days);
        int hoursInt = Math.round(hours);
        int minutesInt = Math.round(minutes);
        if (daysInt == 1) {
            return "1 day";
        } else if (daysInt > 1) {
            return Integer.toString(daysInt) + " days";
        } else if (hoursInt == 1) {
            return "1 hour";
        } else if (hoursInt > 1) {
            return Integer.toString(hoursInt) + " hours";
        } else {
            return "less than an hour";
        }
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("text", text);
        maps.put("username", username);
        maps.put("date", date);
        return maps;
    }
}


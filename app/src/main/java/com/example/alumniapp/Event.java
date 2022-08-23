package com.example.alumniapp;

public class Event {
    String imageUrl,uid,First_Name,Last_Name,Title,Details,Date,Time,randomKey;

    public Event() {
    }

    public Event(String imageUrl, String uid, String first_Name, String last_Name, String title, String details, String date, String time, String randomKey) {
        this.imageUrl = imageUrl;
        this.uid = uid;
        First_Name = first_Name;
        Last_Name = last_Name;
        Title = title;
        Details = details;
        Date = date;
        Time = time;
        this.randomKey = randomKey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }
}

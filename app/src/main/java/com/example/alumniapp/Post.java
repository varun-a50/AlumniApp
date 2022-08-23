package com.example.alumniapp;

public class Post {

    String imageUrl,uid,First_Name,Last_Name,Post,randomkey;

    public Post() {
    }


    public Post(String imageUrl, String uid, String first_Name, String last_Name, String post,String randomkey) {
        this.imageUrl = imageUrl;
        this.uid = uid;
        First_Name = first_Name;
        Last_Name = last_Name;
        Post = post;
        this.randomkey = randomkey;
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

    public String getPost() {
        return Post;
    }

    public void setPost(String post) {
        Post = post;
    }

    public String getRandomkey() {
        return randomkey;
    }

    public void setRandomkey(String randomkey) {
        this.randomkey = randomkey;
    }


}

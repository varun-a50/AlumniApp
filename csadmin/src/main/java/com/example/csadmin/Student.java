package com.example.csadmin;

public class Student {
    public String first_name,mid_name,last_name,email_ID,mobile_no,class_year,password,randomKey,profileUrl;


    public Student() {
    }

    public Student(String first_name, String mid_name, String last_name, String email_ID, String mobile_no, String class_year, String password,String randomKey,String profileUrl) {
        this.first_name = first_name;
        this.mid_name = mid_name;
        this.last_name = last_name;
        this.email_ID = email_ID;
        this.mobile_no = mobile_no;
        this.class_year = class_year;
        this.password = password;
        this.randomKey = randomKey;
        this.profileUrl = profileUrl;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMid_name() {
        return mid_name;
    }

    public void setMid_name(String mid_name) {
        this.mid_name = mid_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail_ID() {
        return email_ID;
    }

    public void setEmail_ID(String email_ID) {
        this.email_ID = email_ID;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getClass_year() {
        return class_year;
    }

    public void setClass_year(String class_year) {
        this.class_year = class_year;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}

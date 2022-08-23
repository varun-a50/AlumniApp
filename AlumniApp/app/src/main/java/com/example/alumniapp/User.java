package com.example.alumniapp;

public class User {
    public String first_name,mid_name,last_name,email_ID,mobile_no,grade,year,password;


    public User() {
    }

    public User(String first_name, String mid_name, String last_name, String email_ID, String mobile_no, String grade, String year, String password) {
        this.first_name = first_name;
        this.mid_name = mid_name;
        this.last_name = last_name;
        this.email_ID = email_ID;
        this.mobile_no = mobile_no;
        this.grade = grade;
        this.year = year;
        this.password = password;
    }
}

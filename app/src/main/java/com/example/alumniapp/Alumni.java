package com.example.alumniapp;

public class Alumni {
    public String first_name,mid_name,last_name,email_ID,mobile_no,grade,year,password,company_name,work_experience,something,randomKey,profileUrl;


    public Alumni() {
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getWork_experience() {
        return work_experience;
    }

    public void setWork_experience(String work_experience) {
        this.work_experience = work_experience;
    }

    public String getSomething() {
        return something;
    }

    public void setSomething(String something) {
        this.something = something;
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

    public Alumni(String first_name, String mid_name, String last_name, String email_ID, String mobile_no, String grade, String year, String password, String company_name, String work_experience, String something, String randomKey,String profileUrl) {
        this.first_name = first_name;
        this.mid_name = mid_name;
        this.last_name = last_name;
        this.email_ID = email_ID;
        this.mobile_no = mobile_no;
        this.grade = grade;
        this.year = year;
        this.password = password;
        this.company_name = company_name;
        this.work_experience = work_experience;
        this.something = something;
        this.randomKey = randomKey;
        this.profileUrl = profileUrl;


    }

}

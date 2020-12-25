package com.sail.exp.freevoteapp.data.model;

import com.orm.SugarRecord;

/**
 * Data class that captures user information for users
 */
public class AppUser extends SugarRecord {

    private String django;
    private String userName;
    private String passWord;
    private String userEmail;
    private String userPhone;
    private boolean gender;
    private String birth;
    private String education;
    private String regDate;

    public AppUser() {
    }

    public AppUser(String django, String userName, String passWord, String userEmail, String userPhone, boolean gender, String birth, String education, String regDate) {
        this.django = django;
        this.userName = userName;
        this.passWord = passWord;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.gender = gender;
        this.birth = birth;
        this.education = education;
        this.regDate = regDate;
    }

    public String getDjango() {
        return django;
    }

    public void setDjango(String django) {
        this.django = django;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "django='" + django + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", gender=" + gender +
                ", birth='" + birth + '\'' +
                ", education='" + education + '\'' +
                ", regDate='" + regDate + '\'' +
                '}';
    }
}

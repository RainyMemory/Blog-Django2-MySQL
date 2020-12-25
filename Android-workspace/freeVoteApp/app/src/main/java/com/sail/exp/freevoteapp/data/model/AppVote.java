package com.sail.exp.freevoteapp.data.model;

import com.google.gson.Gson;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Map;

public class AppVote extends SugarRecord {

    private String django;
    private String starterId;
    private String starterEmail;
    private String startDate;
    private String endDate;
    private String voteKey;
    private String contentJson;

    public AppVote(String django, String starterId, String starterEmail, String startDate, String endDate, String voteKey, String contentJson) {
        this.django = django;
        this.starterId = starterId;
        this.starterEmail = starterEmail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.voteKey = voteKey;
        this.contentJson = contentJson;
    }

    public AppVote() {
    }

    public String getDjango() {
        return django;
    }

    public void setDjango(String django) {
        this.django = django;
    }

    public String getStarterId() {
        return starterId;
    }

    public void setStarterId(String starterId) {
        this.starterId = starterId;
    }

    public String getStarterEmail() {
        return starterEmail;
    }

    public void setStarterEmail(String starterEmail) {
        this.starterEmail = starterEmail;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getVoteKey() {
        return voteKey;
    }

    public void setVoteKey(String voteKey) {
        this.voteKey = voteKey;
    }

    public String getContentJson() {
        return contentJson;
    }

    public void setContentJson(String contentJson) {
        this.contentJson = contentJson;
    }

    public void setContentJson(ArrayList<VoteContent> contentList) {
        Gson gson = new Gson();
        this.contentJson = gson.toJson(contentList);
    }

    public String getTitleQuestion() {
        Gson gson = new Gson();
        ArrayList<Map> voteList = gson.fromJson(this.contentJson, ArrayList.class);
        return (String) voteList.get(0).get("Question");
    }

    @Override
    public String toString() {
        return "AppVote{" +
                "django='" + django + '\'' +
                ", starterId='" + starterId + '\'' +
                ", starterEmail='" + starterEmail + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", voteKey='" + voteKey + '\'' +
                ", contentJson='" + contentJson + '\'' +
                '}';
    }
}

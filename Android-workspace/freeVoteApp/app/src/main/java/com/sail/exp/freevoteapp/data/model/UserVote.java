package com.sail.exp.freevoteapp.data.model;

import com.google.gson.Gson;
import com.orm.SugarRecord;

import java.util.ArrayList;

public class UserVote extends SugarRecord {

    private String django;
    private String userId;
    private String userEmail;
    private String voteId;
    private String voteKey;
    private String resDate;
    private String voteRes;

    public UserVote(String django, String userId, String userEmail, String voteId, String voteKey, String resDate, String voteRes) {
        this.django = django;
        this.userId = userId;
        this.userEmail = userEmail;
        this.voteId = voteId;
        this.voteKey = voteKey;
        this.resDate = resDate;
        this.voteRes = voteRes;
    }

    public UserVote() {
    }

    public String getDjango() {
        return django;
    }

    public void setDjango(String django) {
        this.django = django;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getVoteKey() {
        return voteKey;
    }

    public void setVoteKey(String voteKey) {
        this.voteKey = voteKey;
    }

    public String getResDate() {
        return resDate;
    }

    public void setResDate(String resDate) {
        this.resDate = resDate;
    }

    public String getVoteRes() {
        return voteRes;
    }

    public void setVoteRes(String voteRes) {
        this.voteRes = voteRes;
    }

    public void setVoteRes(VoteResult result) {
        Gson gson = new Gson();
        this.voteRes = gson.toJson(result);
    }

    public String getVoteStr() {
        Gson gson = new Gson();
        VoteResult result = gson.fromJson(this.voteRes, VoteResult.class);
        return result.getChoices().toString();
    }

    @Override
    public String toString() {
        return "userVote{" +
                "django='" + django + '\'' +
                ", userId='" + userId + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", voteId='" + voteId + '\'' +
                ", voteKey='" + voteKey + '\'' +
                ", resDate='" + resDate + '\'' +
                ", voteRes='" + voteRes + '\'' +
                '}';
    }
}

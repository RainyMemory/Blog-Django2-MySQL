package com.sail.exp.freevoteapp.data.model;

import java.util.ArrayList;

public class VoteContent {

    private String Question;
    private ArrayList<String> Options;

    public VoteContent() {
    }

    public VoteContent(String question) {
        this.Question = question;
        this.Options = new ArrayList<>();
    }

    public void setQuestion(String question) {
        this.Question = question;
    }

    public VoteContent addOption(String option) {
        this.Options.add(option);
        return this;
    }

    public String getQuestion() {
        return Question;
    }

    public void setOptions(ArrayList<String> options) {
        Options = options;
    }

    public ArrayList<String> getOptions() {
        return this.Options;
    }
}

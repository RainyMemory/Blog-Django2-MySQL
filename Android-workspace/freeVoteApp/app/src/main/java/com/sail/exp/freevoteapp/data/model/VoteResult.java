package com.sail.exp.freevoteapp.data.model;

import java.util.ArrayList;

public class VoteResult {

    private ArrayList<String> Choices;

    public VoteResult() {
        this.Choices = new ArrayList<>();
    }

    public void setChoices(ArrayList<String> choices) {
        Choices = choices;
    }

    public VoteResult(ArrayList<String> choices) {
        Choices = choices;
    }

    public ArrayList<String> addChoice(String Choice) {
        this.Choices.add(Choice);
        return this.Choices;
    }

    public ArrayList<String> getChoices() {
        return this.Choices;
    }
}

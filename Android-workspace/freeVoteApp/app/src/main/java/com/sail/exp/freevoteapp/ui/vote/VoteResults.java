package com.sail.exp.freevoteapp.ui.vote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.sail.exp.freevoteapp.R;
import com.sail.exp.freevoteapp.data.util.Const;

import java.util.ArrayList;
import java.util.Map;

public class VoteResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_results);

        Intent voteResIntent = getIntent();
        String voteList = voteResIntent.getStringExtra(Const.RES_STR);

        final Button voteResBackButton = findViewById(R.id.vote_res_back);
        final TextView voteResShowPlainText = findViewById(R.id.vote_results_content);

        voteResBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Gson gson = new Gson();
        ArrayList<Map<String, String>> voteResultsList = gson.fromJson(voteList, ArrayList.class);
        ArrayList<ArrayList> choicesList = new ArrayList<>();
        int voteLength = 0;
        for (Map map : voteResultsList) {
            String choiceContent = (String) map.get("voteRes");
            Map<String, ArrayList> choice = gson.fromJson(choiceContent, Map.class);
            choicesList.add(choice.get("Choices"));
            voteLength = choice.get("Choices").size();
        }
        ArrayList<ArrayList> answerList = new ArrayList<>();
        for (int index = 0; index < voteLength; index++) {
            ArrayList<String> currentQesAns = new ArrayList<>();
            for (ArrayList userVote : choicesList) {
                currentQesAns.add((String) userVote.get(index));
            }
            answerList.add(currentQesAns);
        }
        String output = "";
        int count = 1;
        for (ArrayList questionResults : answerList) {
            output += "Question" + count + ", get votes :" + questionResults + "\n";
            count++;
        }
        voteResShowPlainText.setText(output);
    }
}

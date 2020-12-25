package com.sail.exp.freevoteapp.ui.vote;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.sail.exp.freevoteapp.R;
import com.sail.exp.freevoteapp.data.model.UserVote;
import com.sail.exp.freevoteapp.data.util.Const;

import java.util.ArrayList;
import java.util.Map;

public class ListResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_votes_res);

        Gson gson = new Gson();
        Intent resIntent = getIntent();
        String response = resIntent.getStringExtra(Const.RES_STR);
        ArrayList<Map> resultMap = gson.fromJson(response, ArrayList.class);
        final ArrayList<UserVote> resultList = new ArrayList<>();
        for (Map<String, Object> result : resultMap) {
            UserVote myRes = new UserVote();
            myRes.setVoteRes((String) result.get("voteRes"));
            myRes.setDjango("" + result.get("id"));
            myRes.setResDate((String) result.get("resDate"));
            myRes.setUserId((String) result.get("userId"));
            myRes.setUserEmail((String) result.get("userEmail"));
            myRes.setVoteId((String) result.get("voteId"));
            myRes.setVoteKey((String) result.get("voteKey"));
            resultList.add(myRes);
        }
        initUi(resultList);

    }

    public void initUi(final ArrayList<UserVote> resultsList) {
        final LayoutInflater voteInflater = LayoutInflater.from(this);
        final LinearLayout voteListContainer = findViewById(R.id.vote_layout);
        final ArrayList<LinearLayout> voteList = new ArrayList<>();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (UserVote result : resultsList) {
                    LinearLayout nextChild = (LinearLayout) voteInflater.inflate(R.layout.vote_card, null);
                    voteList.add(nextChild);
                    voteListContainer.addView(nextChild);
                    LinearLayout ek = (LinearLayout) nextChild.getChildAt(0);
                    View e = nextChild.getChildAt(1);
                    ((TextView) e).setText(result.getVoteStr());
                    ((TextView) ek.getChildAt(0)).setText(result.getVoteKey());
                    ((TextView) ek.getChildAt(1)).setText(result.getResDate());
                    final String tex = ((TextView) e).getText().toString();
                    e.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "e: " + tex, Toast.LENGTH_LONG).show();
                        }
                    });
                    ek.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "ek: " + tex, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }, Const.TR_SLEEP);
    }
}

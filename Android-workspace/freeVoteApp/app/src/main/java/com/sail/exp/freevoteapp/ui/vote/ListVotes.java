package com.sail.exp.freevoteapp.ui.vote;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sail.exp.freevoteapp.R;
import com.sail.exp.freevoteapp.data.httpapi.ResponseHandler;
import com.sail.exp.freevoteapp.data.model.AppVote;
import com.sail.exp.freevoteapp.data.model.VoteResult;
import com.sail.exp.freevoteapp.data.util.Const;
import com.sail.exp.freevoteapp.data.util.HttpReqThread;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ListVotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_votes_res);

        Gson gson = new Gson();
        Intent resIntent = getIntent();
        String response = resIntent.getStringExtra(Const.RES_STR);
        ArrayList<Map> voteMaps = gson.fromJson(response, ArrayList.class);
        final ArrayList<AppVote> voteLists = new ArrayList<>();
        for (Map<String, Object> vote : voteMaps) {
            AppVote myVote = new AppVote();
            myVote.setContentJson((String) vote.get("contentJson"));
            myVote.setDjango("" + vote.get("id"));
            myVote.setEndDate((String) vote.get("endDate"));
            myVote.setStartDate((String) vote.get("startDate"));
            myVote.setStarterId((String) vote.get("starterId"));
            myVote.setStarterEmail((String) vote.get("starterEmail"));
            myVote.setVoteKey((String) vote.get("voteKey"));
            voteLists.add(myVote);
        }
        initUi(voteLists);

    }

    public void initUi(final ArrayList<AppVote> voteLists) {
        final LayoutInflater voteInflater = LayoutInflater.from(this);
        final LinearLayout voteListContainer = findViewById(R.id.vote_layout);
        final ArrayList<LinearLayout> voteList = new ArrayList<>();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (AppVote vote : voteLists) {
                    LinearLayout nextChild = (LinearLayout) voteInflater.inflate(R.layout.vote_card, null);
                    voteList.add(nextChild);
                    voteListContainer.addView(nextChild);
                    LinearLayout ek = (LinearLayout) nextChild.getChildAt(0);
                    View e = nextChild.getChildAt(1);
                    ((TextView) e).setText(vote.getTitleQuestion());
                    ((TextView) ek.getChildAt(0)).setText(vote.getVoteKey());
                    ((TextView) ek.getChildAt(1)).setText(vote.getEndDate());
                    final String voteKey = vote.getVoteKey();
                    final String tex = ((TextView) e).getText().toString();
                    e.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent getRIntent = new Intent(ListVotes.this, VoteResults.class);
                                    ExecutorService executor = Executors.newCachedThreadPool();
                                    HttpReqThread thread = new HttpReqThread(voteKey, Const.GET_VOTE_RES);
                                    Future<String> future = executor.submit(thread);
                                    executor.shutdown();
                                    try {
                                        getRIntent.putExtra(Const.RES_STR, future.get());
                                    } catch (ExecutionException | InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(getRIntent);
                                }
                            }, Const.TR_SLEEP);
                        }
                    });
                    ek.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent getRIntent = new Intent(ListVotes.this, VoteResults.class);
                                    ExecutorService executor = Executors.newCachedThreadPool();
                                    HttpReqThread thread = new HttpReqThread(voteKey, Const.GET_VOTE_RES);
                                    Future<String> future = executor.submit(thread);
                                    executor.shutdown();
                                    try {
                                        getRIntent.putExtra(Const.RES_STR, future.get());
                                    } catch (ExecutionException | InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(getRIntent);
                                }
                            }, Const.TR_SLEEP);
                        }
                    });
                }
            }
        }, Const.TR_SLEEP);
    }
}

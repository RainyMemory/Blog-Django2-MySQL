package com.sail.exp.freevoteapp.ui.vote;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sail.exp.freevoteapp.R;
import com.sail.exp.freevoteapp.data.httpapi.ResponseHandler;
import com.sail.exp.freevoteapp.data.model.AppVote;
import com.sail.exp.freevoteapp.data.model.UserVote;
import com.sail.exp.freevoteapp.data.model.VoteContent;
import com.sail.exp.freevoteapp.data.model.VoteResult;
import com.sail.exp.freevoteapp.data.service.AutoFillService;
import com.sail.exp.freevoteapp.data.util.Const;
import com.sail.exp.freevoteapp.data.util.HttpReqThread;
import com.sail.exp.freevoteapp.data.util.ResponseCheckUtil;
import com.sail.exp.freevoteapp.ui.mainAct.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VoteSubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_sub);
        final LinearLayout voteListContainer = findViewById(R.id.vote_layout);
        Button submitVote = findViewById(R.id.vote_sub_button);

        Gson gson = new Gson();
        Intent resIntent = getIntent();
        String response = ResponseHandler.id2Django(resIntent.getStringExtra(Const.RES_STR));
        final AppVote tarVote = gson.fromJson(response, AppVote.class);
        String quesJson = tarVote.getContentJson();
        ArrayList<Map> quesContent = gson.fromJson(quesJson, ArrayList.class);
        final ArrayList<VoteContent> voteList = new ArrayList<>();
        for (Map question : quesContent) {
            VoteContent vote = new VoteContent();
            vote.setQuestion((String) question.get("Question"));
            vote.setOptions((ArrayList<String>) question.get("Options"));
            voteList.add(vote);
        }
        initUi(voteList);

        submitVote.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                int quesCount = 1;
                ArrayList<String> choices = new ArrayList<>();
                while (null != voteListContainer.getChildAt(quesCount)) {
                    int optIndex = 1;
                    LinearLayout quesCard = (LinearLayout) voteListContainer.getChildAt(quesCount);
                    while (null != quesCard.getChildAt(optIndex)) {
                        View e = quesCard.getChildAt(optIndex);
                        if (null != e.getBackground() && Color.GRAY == ((ColorDrawable) e.getBackground()).getColor()) {
                            choices.add(optIndex + "");
                        }
                        optIndex++;
                    }
                    quesCount++;
                }
                Random rand = new Random(System.currentTimeMillis());
                VoteResult voteResult = new VoteResult(choices);
                Gson gson = new Gson();
                String userId = AutoFillService.getCurrentUserId();
                String userEmail = AutoFillService.getCurrentUserEmail();

                UserVote userVote = new UserVote();
                if (null != userId && null != userEmail) {
                    userVote.setUserId(userId);
                    userVote.setUserEmail(userEmail);
                } else {
                    userVote.setUserId(Const.VISITOR_ID);
                    userVote.setUserEmail(rand.nextInt() + rand.nextInt() + rand.nextInt() + Const.VISITOR_EMAIL);
                }
                userVote.setVoteId(tarVote.getDjango());
                userVote.setVoteKey(tarVote.getVoteKey());
                userVote.setVoteRes(voteResult);
                final String paramJson = gson.toJson(userVote);

                /*
                 * SUB_NEW_RES
                 * the param Json string contains submission details
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent getVIntent = new Intent(VoteSubActivity.this, MainActivity.class);
                        ExecutorService executor = Executors.newCachedThreadPool();
                        HttpReqThread thread = new HttpReqThread(null, Const.SUB_NEW_RES, paramJson);
                        Future<String> future = executor.submit(thread);
                        executor.shutdown();
                        try {
                            getVIntent.putExtra(Const.RES_STR, future.get());
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        String resStr = getVIntent.getStringExtra(Const.RES_STR);
                        boolean sucResponse = ResponseCheckUtil.CheckResMessage(resStr);
                        if (!sucResponse) {
                            Toast.makeText(getApplicationContext(), Const.MUL_USERVOTE, Toast.LENGTH_LONG).show();
                        }
                        finish();
                    }

                }, Const.TR_SLEEP);
            }
        });
    }

    public void initUi(final ArrayList<VoteContent> voteList) {
        final LayoutInflater voteInflater = LayoutInflater.from(this);
        final LinearLayout voteListContainer = findViewById(R.id.vote_layout);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.leftMargin = 30;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (VoteContent vote : voteList) {
                    LinearLayout nextChild = (LinearLayout) voteInflater.inflate(R.layout.question_card, null);
                    String question = vote.getQuestion();
                    ((TextView) nextChild.getChildAt(0)).setText(question);
                    ArrayList<String> options = vote.getOptions();
                    for (String option : options) {
                        TextView addOpt = new TextView(VoteSubActivity.this);
                        addOpt.setText(option);
                        addOpt.setLayoutParams(params);
                        addOpt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), ((TextView) v).getText().toString(), Toast.LENGTH_LONG).show();
                                LinearLayout ek = (LinearLayout) v.getParent();
                                int j = 0;
                                while (null != ek.getChildAt(j)) {
                                    ek.getChildAt(j).setBackgroundColor(Color.WHITE);
                                    j++;
                                }
                                v.setBackgroundColor(Color.GRAY);
                            }

                        });
                        nextChild.addView(addOpt);
                    }
                    voteListContainer.addView(nextChild);
                }
            }
        }, Const.TR_SLEEP);
    }
}

package com.sail.exp.freevoteapp.ui.maintest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.orm.SugarContext;
import com.sail.exp.freevoteapp.R;
import com.sail.exp.freevoteapp.data.httpapi.ResponseHandler;
import com.sail.exp.freevoteapp.data.model.AppVote;
import com.sail.exp.freevoteapp.data.model.VoteContent;
import com.sail.exp.freevoteapp.data.model.VoteResult;
import com.sail.exp.freevoteapp.data.util.Const;
import com.sail.exp.freevoteapp.data.util.HttpReqThread;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test_layout);
        SugarContext.init(this);
    }

    public void testOnClick(View view) {
        System.out.println("Now here");
        try {
            // Open another thread to handle the internet communication
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent myintent = new Intent(TestActivity.this, Test2Act.class);
                    // call the executor to configure and run the thread
                    ExecutorService executor = Executors.newCachedThreadPool();
//                    HttpReqThread thread = new HttpReqThread("8785673@8mail.com", Const.GET_TAR_USER);
                    HttpReqThread thread = new HttpReqThread("", Const.GET_VOTE_RES);
                    Future<String> future = executor.submit(thread);
                    executor.shutdown();
                    try {
                        myintent.putExtra(Const.RES_STR, future.get());
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    startActivity(myintent);
                    finish();
                }
            }, Const.TR_SLEEP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // test if the json strings in the entities are defined properly
//    public void showMe(View view) {
//        Gson gson = new Gson();
//        VoteContent myVote = new VoteContent("Nice!");
//        myVote.addOption("opt1");
//        myVote.addOption("opt2");
//        myVote.addOption("opt3");
//        VoteContent myVote2 = new VoteContent("Nice!2");
//        myVote2.addOption("op1");
//        myVote2.addOption("op2");
//        myVote2.addOption("op3");
//        VoteResult res = new VoteResult();
//        res.addChoice("1");
//        res.addChoice("3");
//        res.addChoice("1");
//        ArrayList<VoteContent> votes = new ArrayList<>();
//        votes.add(myVote);
//        votes.add(myVote2);
////        TextView myView = findViewById(R.id.textView);
//        String contentJson = gson.toJson(votes);
//        AppVote apv = new AppVote("4", "2", "2341@dsaf.com", "2019-03-03", "2020-04-04", "EAFDSAFD", contentJson);
//        myView.setText(gson.toJson(apv));
////        apv.save();
//        ArrayList<AppVote> results = (ArrayList<AppVote>) AppVote.find(AppVote.class, "django = ?", "4");
//        String rawStr = gson.toJson(results.get(0));
//        String readyToSend = ResponseHandler.django2Id(rawStr);
//        System.out.println(rawStr + "\n" + readyToSend);
////        System.out.println(AppVote.listAll(AppVote.class));
//    }

    /**
     * GET_TAR_USER
     * @param param the pk for user retrieve, contains user email
     * @param jsonParam null
     */
    public void getTarUser(final String param, String jsonParam) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Configure for the next activity
                Intent loginIntent = new Intent();
                // Set up the thread executor
                ExecutorService executor = Executors.newCachedThreadPool();
                // Init the callable thread
                HttpReqThread thread = new HttpReqThread(param, Const.GET_TAR_USER);
                // Execute the thread and wait for its response
                Future<String> future = executor.submit(thread);
                // Shut down the executor
                executor.shutdown();
                // Put the response into intent
                try {
                    loginIntent.putExtra(Const.RES_STR, future.get());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                // Start the next activity
                startActivity(loginIntent);
                // Terminal the current activity if needed
                finish();
            }
        }, Const.TR_SLEEP);
    }

    /**
     * PAT_TAR_USER
     * @param param pk for the backend server, contains user id
     * @param jsonParam partial info of a user that need to be modified
     */
    public void parTarUser(final String param, final String jsonParam) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, Const.TR_SLEEP);
    }

    /**
     * REG_NEW_USER
     * @param param null
     * @param jsonParam specific info of user
     */
    public void regNewUser(final String param, final String jsonParam) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent regUIntent = new Intent();
                ExecutorService executor = Executors.newCachedThreadPool();
                HttpReqThread thread = new HttpReqThread(param, Const.REG_NEW_USER);
                thread.setJsonParam(jsonParam);
                Future<String> future = executor.submit(thread);
                executor.shutdown();
                try {
                    regUIntent.putExtra(Const.RES_STR, future.get());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(regUIntent);
            }
        }, Const.TR_SLEEP);
    }

    /**
     * CRE_NEW_VOTE
     * @param param null
     * @param jsonParam specific info of a vote
     */
    public void creNewVote(final String param, final String jsonParam) {

    }

    /**
     * MOD_TAR_VOTE
     * @param param pk, contains voteKey
     * @param jsonParam partial info of a vote that need to be changed
     */
    public void modTarVote(final String param, final String jsonParam) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent modVIntent = new Intent();
                ExecutorService executor = Executors.newCachedThreadPool();
                HttpReqThread thread = new HttpReqThread(param, Const.MOD_TAR_VOTE);
                thread.setJsonParam(jsonParam);
                Future<String> future = executor.submit(thread);
                executor.shutdown();
                try {
                    modVIntent.putExtra(Const.RES_STR, future.get());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(modVIntent);
            }
        }, Const.TR_SLEEP);
    }



    /**
     * SUB_NEW_RES
     * @param param null
     * @param jsonParam specific info of a submit
     */
    public void subNewRes(final String param, final String jsonParam) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent subRIntent = new Intent();
                ExecutorService executor = Executors.newCachedThreadPool();
                HttpReqThread thread = new HttpReqThread(param, Const.SUB_NEW_RES);
                thread.setJsonParam(jsonParam);
                Future<String> future = executor.submit(thread);
                executor.shutdown();
                try {
                    subRIntent.putExtra(Const.RES_STR, future.get());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(subRIntent);
            }
        }, Const.TR_SLEEP);
    }

    /**
     * GET_VOTE_RES
     * @param param pk, the voteKey
     * @param jsonParam contains other params for the GET method, like user id
     */
    public void getVoteRes(final String param, final String jsonParam) {

    }

}

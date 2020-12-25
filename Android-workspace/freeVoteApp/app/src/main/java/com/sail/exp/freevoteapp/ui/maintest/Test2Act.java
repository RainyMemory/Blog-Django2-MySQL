package com.sail.exp.freevoteapp.ui.maintest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sail.exp.freevoteapp.R;
import com.sail.exp.freevoteapp.data.httpapi.ResponseHandler;
import com.sail.exp.freevoteapp.data.model.AppUser;
import com.sail.exp.freevoteapp.data.model.AppVote;
import com.sail.exp.freevoteapp.data.model.UserVote;
import com.sail.exp.freevoteapp.data.util.Const;

import java.util.ArrayList;

public class Test2Act extends AppCompatActivity {

    private Handler handler;

    @SuppressLint({"HandlerLeak"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test2);
//        handler = new Handler() {
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg);
//                if (null != msg.obj) {
//                    EditText text = findViewById(R.id.editText);
//                    AppUser textStr = (AppUser) msg.obj;
//                    text.setText(textStr.getUserPhone());
//                }
//            }
//        };

        Intent myIntent = getIntent();
        String res = myIntent.getStringExtra(Const.RES_STR);
        Gson gson = new Gson();
//        AppUser tarUser = gson.fromJson(res, AppUser.class);
//        Message message = Message.obtain();
//        message.obj = tarUser;
//        handler.sendMessage(message);
        System.out.println(res);
    }



    public void parTarUserR() {
        Intent resIntent = getIntent();
        String response = resIntent.getStringExtra(Const.RES_STR);

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (null != msg.obj) {
                    AppUser updateUser = (AppUser) msg.obj;



                    AppUser.deleteAll(AppUser.class);
                    updateUser.save();
                }
            }
        };

        Gson gson = new Gson();
        response = ResponseHandler.id2Django(response);
        AppUser updateUser = gson.fromJson(response, AppUser.class);

        Message message = Message.obtain();
        message.obj = updateUser;
        handler.sendMessage(message);
    }

    public void regNewUserR() {
        Intent resIntent = getIntent();
        String response = resIntent.getStringExtra(Const.RES_STR);

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (null != msg.obj) {
                    AppUser newUser = (AppUser) msg.obj;



                    AppUser.deleteAll(AppUser.class);
                    newUser.save();
                }
            }
        };

        Gson gson = new Gson();
        response = ResponseHandler.id2Django(response);
        AppUser newUser = gson.fromJson(response, AppUser.class);

        Message message = Message.obtain();
        message.obj = newUser;
        handler.sendMessage(message);
    }

    public void creNewVoteR() {
        Intent resIntent = getIntent();
        String response = resIntent.getStringExtra(Const.RES_STR);

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (null != msg.obj) {
                    AppVote newVote = (AppVote) msg.obj;



                    newVote.save();
                }
            }
        };

        Gson gson = new Gson();
        response = ResponseHandler.id2Django(response);
        AppVote newVote = gson.fromJson(response, AppVote.class);

        Message message = Message.obtain();
        message.obj = newVote;
        handler.sendMessage(message);
    }

    public void modTarVoteR() {
        Intent resIntent = getIntent();
        String response = resIntent.getStringExtra(Const.RES_STR);

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (null != msg.obj) {
                    AppVote updateVote = (AppVote) msg.obj;



                    updateVote.update();
                }
            }
        };

        Gson gson = new Gson();
        response = ResponseHandler.id2Django(response);
        AppVote updateVote = gson.fromJson(response, AppVote.class);

        Message message = Message.obtain();
        message.obj = updateVote;
        handler.sendMessage(message);
    }

    public void getTarVoteR() {
        Intent resIntent = getIntent();
        String response = resIntent.getStringExtra(Const.RES_STR);

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (null != msg.obj) {
                    AppVote tarVote = (AppVote) msg.obj;


                }
            }
        };

        Gson gson = new Gson();
        response = ResponseHandler.id2Django(response);
        AppVote tarVote = gson.fromJson(response, AppVote.class);

        Message message = Message.obtain();
        message.obj = tarVote;
        handler.sendMessage(message);
    }

    public void subNewResR() {
        Intent resIntent = getIntent();
        String response = resIntent.getStringExtra(Const.RES_STR);

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (null != msg.obj) {
                    UserVote voteResult = (UserVote) msg.obj;



                    voteResult.save();
                }
            }
        };

        Gson gson = new Gson();
        response = ResponseHandler.id2Django(response);
        UserVote voteResult = gson.fromJson(response, UserVote.class);

        Message message = Message.obtain();
        message.obj = voteResult;
        handler.sendMessage(message);
    }

    public void getTarResR() {
        Intent resIntent = getIntent();
        String response = resIntent.getStringExtra(Const.RES_STR);

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (null != msg.obj) {
                    ArrayList<UserVote> voteResults = (ArrayList<UserVote>) msg.obj;
                    for (int iter = 0; iter < voteResults.size(); iter++) {

                    }
                }
            }
        };

        Gson gson = new Gson();
        // May retrieve one or more results
        ArrayList responseList = gson.fromJson(response, ArrayList.class);
        ArrayList<UserVote> voteResults = new ArrayList<>();
        for (Object result : responseList) {
            result = ResponseHandler.django2Id((String) result);
            voteResults.add(gson.fromJson((JsonElement) result, UserVote.class));
        }

        Message message = Message.obtain();
        message.obj = voteResults;
        handler.sendMessage(message);
    }

}

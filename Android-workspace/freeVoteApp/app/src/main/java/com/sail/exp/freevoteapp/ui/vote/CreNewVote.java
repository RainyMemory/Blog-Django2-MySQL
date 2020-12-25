package com.sail.exp.freevoteapp.ui.vote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sail.exp.freevoteapp.R;
import com.sail.exp.freevoteapp.data.service.AutoFillService;
import com.sail.exp.freevoteapp.data.util.Const;
import com.sail.exp.freevoteapp.data.util.HttpReqThread;
import com.sail.exp.freevoteapp.data.util.ResponseCheckUtil;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CreNewVote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cre_new_vote);

        final EditText voteTitleEditText = findViewById(R.id.new_vote_title);
        final EditText voteDateEditText = findViewById(R.id.new_vote_date);
        final EditText voteContentEditText = findViewById(R.id.new_vote_content);
        final Button newVoteSubmitButton = findViewById(R.id.new_vote_submit);

        newVoteSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = voteTitleEditText.getText().toString();
                String date = voteDateEditText.getText().toString();
                String content = voteContentEditText.getText().toString();
                if (!title.isEmpty() && !date.isEmpty() && !content.isEmpty()) {
                    final HashMap<String, String> paramMap = new HashMap<>();
                    paramMap.put("starterId", AutoFillService.getCurrentUserId());
                    paramMap.put("starterEmail", AutoFillService.getCurrentUserEmail());
                    paramMap.put("contentJson", content);
                    paramMap.put("endDate", date);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ExecutorService executor = Executors.newCachedThreadPool();
                            HttpReqThread thread = new HttpReqThread("", Const.CRE_NEW_VOTE);
                            Gson gson = new Gson();
                            thread.setJsonParam(gson.toJson(paramMap));
                            Future<String> future = executor.submit(thread);
                            executor.shutdown();
                            String response = "";
                            try {
                                response = future.get();
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                            boolean sucflag = ResponseCheckUtil.CheckResMessage(response);
                            if (sucflag) {
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), Const.REG_FAIL, Toast.LENGTH_LONG).show();
                            }
                        }
                    }, Const.TR_SLEEP);
                }
            }
        });
    }
}

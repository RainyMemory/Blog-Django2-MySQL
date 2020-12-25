package com.sail.exp.freevoteapp.ui.mainAct;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.orm.SugarContext;
import com.sail.exp.freevoteapp.R;
import com.sail.exp.freevoteapp.data.model.AppUser;
import com.sail.exp.freevoteapp.data.service.AutoFillService;
import com.sail.exp.freevoteapp.data.util.Const;
import com.sail.exp.freevoteapp.data.util.HttpReqThread;
import com.sail.exp.freevoteapp.data.util.ResponseCheckUtil;
import com.sail.exp.freevoteapp.ui.userProfile.UserProfile;
import com.sail.exp.freevoteapp.ui.vote.CreNewVote;
import com.sail.exp.freevoteapp.ui.vote.ListResults;
import com.sail.exp.freevoteapp.ui.vote.ListVotes;
import com.sail.exp.freevoteapp.ui.vote.VoteSubActivity;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SugarContext.init(this);
        // Extract the info out of intent
        Intent resIntent = getIntent();
        final String response = resIntent.getStringExtra(Const.RES_STR);
        // Get login history
        String userId = AutoFillService.getCurrentUserId();

        final Button userProfileButton = findViewById(R.id.user_info);
        final Button voteInfoButton = findViewById(R.id.vote_info);
        final Button resultInfoButton = findViewById(R.id.result_info);
        final Button searchButton = findViewById(R.id.action_search);
        final Button creVoteButton = findViewById(R.id.add_vote_but);
        final EditText voteKeyText = findViewById(R.id.search_bar);

        if (null == response || response.isEmpty()) {
            userProfileButton.setEnabled(false);
            voteInfoButton.setEnabled(false);
            resultInfoButton.setEnabled(false);
            creVoteButton.setEnabled(false);
            if (null != userId && !userId.isEmpty()) {
                AppUser.deleteAll(AppUser.class);
            }
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String voteKey = voteKeyText.getText().toString();
                /*
                 * GET_TAR_VOTE
                 * the param is pk, has voteKey
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent getVIntent = new Intent(MainActivity.this, VoteSubActivity.class);
                        ExecutorService executor = Executors.newCachedThreadPool();
                        if (!voteKey.isEmpty()) {
                            HttpReqThread thread = new HttpReqThread(voteKey, Const.GET_TAR_VOTE);
                            Future<String> future = executor.submit(thread);
                            executor.shutdown();
                            try {
                                getVIntent.putExtra(Const.RES_STR, future.get());
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                            String resStr = getVIntent.getStringExtra(Const.RES_STR);
                            boolean sucResponse = ResponseCheckUtil.CheckResMessage(resStr);
                            if (sucResponse) {
                                startActivity(getVIntent);
                            } else {
                                Toast.makeText(getApplicationContext(), Const.ERR_VOTEKEY, Toast.LENGTH_LONG).show();
                                voteKeyText.setText("");
                            }
                        }
                    }
                }, Const.TR_SLEEP);
            }
        });

        userProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modUserIntent = new Intent(MainActivity.this, UserProfile.class);
                Toast.makeText(getApplicationContext(), "USER PROFILE", Toast.LENGTH_LONG).show();
                startActivity(modUserIntent);
            }
        });

        voteInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "VOTE INFO", Toast.LENGTH_LONG).show();
                final String userId = AutoFillService.getCurrentUserId();
                if (null != userId) {
                    /*
                     * GET_TAR_VOTE
                     * the param Json string contains user Id
                     */
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent getVIntent = new Intent(MainActivity.this, ListVotes.class);
                            ExecutorService executor = Executors.newCachedThreadPool();
                            HttpReqThread thread = new HttpReqThread(userId, Const.GET_TAR_VOTE);
                            Future<String> future = executor.submit(thread);
                            executor.shutdown();
                            try {
                                getVIntent.putExtra(Const.RES_STR, future.get());
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                            startActivity(getVIntent);
                        }

                    }, Const.TR_SLEEP);
                }
            }
        });

        resultInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "SUBMISSION RESULTS", Toast.LENGTH_LONG).show();
                final String userId = AutoFillService.getCurrentUserId();
                if (null != userId) {
                    /*
                     * GET_VOTE_RES
                     * the param Json string contains user Id
                     */
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent getVIntent = new Intent(MainActivity.this, ListResults.class);
                            ExecutorService executor = Executors.newCachedThreadPool();
                            HttpReqThread thread = new HttpReqThread(userId, Const.GET_VOTE_RES);
                            Future<String> future = executor.submit(thread);
                            executor.shutdown();
                            try {
                                getVIntent.putExtra(Const.RES_STR, future.get());
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                            startActivity(getVIntent);
                        }
                    }, Const.TR_SLEEP);
                }
            }
        });

        creVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newVoteIntent = new Intent(MainActivity.this, CreNewVote.class);
                Toast.makeText(getApplicationContext(), "ADD VOTE", Toast.LENGTH_LONG).show();
                startActivity(newVoteIntent);
            }
        });
    }
}

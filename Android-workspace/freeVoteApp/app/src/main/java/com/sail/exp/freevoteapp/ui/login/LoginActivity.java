package com.sail.exp.freevoteapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.orm.SugarContext;
import com.sail.exp.freevoteapp.R;
import com.sail.exp.freevoteapp.data.httpapi.ResponseHandler;
import com.sail.exp.freevoteapp.data.model.AppUser;
import com.sail.exp.freevoteapp.data.service.AutoFillService;
import com.sail.exp.freevoteapp.data.util.Const;
import com.sail.exp.freevoteapp.data.util.HttpReqThread;
import com.sail.exp.freevoteapp.data.util.ResponseCheckUtil;
import com.sail.exp.freevoteapp.ui.mainAct.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SugarContext.init(this);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final Button registerButton = findViewById(R.id.register);
        final Button visitorButton = findViewById(R.id.visitor);

        if (AutoFillService.userAutoLogin()) {
            ArrayList<AppUser> hisUsers = (ArrayList<AppUser>) AppUser.listAll(AppUser.class);
            AppUser loginUser = hisUsers.get(0);
            Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
            Gson gson = new Gson();
            loginIntent.putExtra(Const.RES_STR, ResponseHandler.django2Id(gson.toJson(loginUser)));
            startActivity(loginIntent);
            finish();
        }

        /*
         * GET_TAR_USER
         * param is the pk for user retrieve, contains user email
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Get the user input
                        String param = usernameEditText.getText().toString();
                        String password = passwordEditText.getText().toString();
                        if (!param.isEmpty() && !password.isEmpty()) {
                            // Configure for the next activity
                            Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
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
                            // Check if the user info is valid
                            String jsonUser = loginIntent.getStringExtra(Const.RES_STR);
                            AppUser loginUser = null;
                            boolean sucLogin = ResponseCheckUtil.CheckResMessage(jsonUser);
                            if (sucLogin) {
                                Gson gson = new Gson();
                                jsonUser = ResponseHandler.id2Django(jsonUser);
                                loginUser = gson.fromJson(jsonUser, AppUser.class);
                                if (!password.equals(loginUser.getPassWord())) {
                                    sucLogin = false;
                                }
                            }
                            if (sucLogin) {
                                welcomeToast(loginUser.getUserName());
                                // Store the user info locally
                                AppUser.deleteAll(AppUser.class);
                                loginUser.save();
                                // Start the next activity
                                startActivity(loginIntent);
                                // Terminal the current activity if needed
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), Const.LOG_FAIL, Toast.LENGTH_LONG).show();
                                passwordEditText.setText("");
                                loadingProgressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }, Const.TR_SLEEP);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regIntent);
                finish();
            }
        });

        visitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent visIntent = new Intent(LoginActivity.this, MainActivity.class);
                Intent visIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(visIntent);
                welcomeToast("");
                finish();
            }
        });
    }

    private void welcomeToast(String username) {
        String welcome = getString(R.string.welcome);
        Toast.makeText(getApplicationContext(), welcome + username, Toast.LENGTH_LONG).show();
    }

}

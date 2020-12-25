package com.sail.exp.freevoteapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.orm.SugarContext;
import com.sail.exp.freevoteapp.R;
import com.sail.exp.freevoteapp.data.httpapi.ResponseHandler;
import com.sail.exp.freevoteapp.data.model.AppUser;
import com.sail.exp.freevoteapp.data.util.Const;
import com.sail.exp.freevoteapp.data.util.HttpReqThread;
import com.sail.exp.freevoteapp.data.util.ResponseCheckUtil;
import com.sail.exp.freevoteapp.ui.mainAct.MainActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SugarContext.init(this);

        final EditText userEmailEditText = findViewById(R.id.reg_email_txt);
        final EditText usernameEditText = findViewById(R.id.reg_username_txt);
        final EditText passwordEditText = findViewById(R.id.reg_password_txt);
        final EditText userPhoneEditText = findViewById(R.id.reg_phone_txt);
        final Button registerButton = findViewById(R.id.reg_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Get the user input
                        HashMap<String, String> paramap = new HashMap<>();
                        String username = usernameEditText.getText().toString();
                        String password = passwordEditText.getText().toString();
                        String email = userEmailEditText.getText().toString();
                        String phone = userPhoneEditText.getText().toString();
                        if (!email.isEmpty() && !password.isEmpty() && !phone.isEmpty()) {
                            Intent loginIntent = new Intent(RegisterActivity.this, MainActivity.class);
                            paramap.put("passWord", password);
                            paramap.put("userEmail", email);
                            paramap.put("userPhone", phone);
                            if (!username.isEmpty()) {
                                paramap.put("userName", username);
                            }

                            ExecutorService executor = Executors.newCachedThreadPool();
                            Gson gson = new Gson();
                            HttpReqThread thread = new HttpReqThread("", Const.REG_NEW_USER, gson.toJson(paramap));
                            Future<String> future = executor.submit(thread);
                            executor.shutdown();

                            try {
                                loginIntent.putExtra(Const.RES_STR, future.get());
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }

                            String jsonUser = loginIntent.getStringExtra(Const.RES_STR);
                            AppUser loginUser = null;
                            boolean sucLogin = ResponseCheckUtil.CheckResMessage(jsonUser);
                            if (sucLogin) {
                                jsonUser = ResponseHandler.id2Django(jsonUser);
                                loginUser = gson.fromJson(jsonUser, AppUser.class);
                                if (!password.equals(loginUser.getPassWord())) {
                                    sucLogin = false;
                                }
                            }
                            if (sucLogin) {
                                AppUser.deleteAll(AppUser.class);
                                loginUser.save();
                                startActivity(loginIntent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), Const.REG_FAIL, Toast.LENGTH_LONG).show();
                                passwordEditText.setText("");
                            }
                        }
                    }
                }, Const.TR_SLEEP);
            }
        });

    }
}

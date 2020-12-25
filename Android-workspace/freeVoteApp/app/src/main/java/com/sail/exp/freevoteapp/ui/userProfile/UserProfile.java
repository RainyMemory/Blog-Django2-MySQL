package com.sail.exp.freevoteapp.ui.userProfile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.sail.exp.freevoteapp.R;
import com.sail.exp.freevoteapp.data.model.AppUser;
import com.sail.exp.freevoteapp.data.service.AutoFillService;
import com.sail.exp.freevoteapp.data.util.Const;
import com.sail.exp.freevoteapp.data.util.HttpReqThread;
import com.sail.exp.freevoteapp.data.util.ResponseCheckUtil;
import com.sail.exp.freevoteapp.ui.mainAct.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        AppUser user = null;
        String userId = AutoFillService.getCurrentUserId();
        if (null != userId) {
            ArrayList<AppUser> hisUsers = (ArrayList<AppUser>) AppUser.listAll(AppUser.class);
            user = hisUsers.get(0);
        }
        if (null != user) {
            initUI(user);
        } else {
            finish();
        }

        final EditText modUsernameEditText = findViewById(R.id.mod_username_txt);
        final EditText modEmailEditText = findViewById(R.id.mod_email_txt);
        final EditText modPhoneEditText = findViewById(R.id.mod_phone_txt);
        final EditText modPasswordEditText = findViewById(R.id.mod_password_txt);
        final EditText modBirthEditText = findViewById(R.id.mod_birth_txt);
        final EditText modEduEditText = findViewById(R.id.mod_edu_txt);
        final Button modUserSubButton = findViewById(R.id.mod_sub_button);

        modUserSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<String, String> paramMap = new HashMap<>();
                String username = modUsernameEditText.getText().toString();
                String email = modEmailEditText.getText().toString();
                String phone = modPhoneEditText.getText().toString();
                String password = modPasswordEditText.getText().toString();
                String birth = modBirthEditText.getText().toString();
                String edu = modEduEditText.getText().toString();
                if (!username.isEmpty()) {
                    paramMap.put("userName", username);
                }
                if (!email.isEmpty()) {
                    paramMap.put("userEmail", email);
                }
                if (!phone.isEmpty()) {
                    paramMap.put("userPhone", phone);
                }
                if (!password.isEmpty()) {
                    paramMap.put("passWord", password);
                }
                if (!birth.isEmpty()) {
                    paramMap.put("birth", birth);
                }
                if (!edu.isEmpty()) {
                    paramMap.put("education", edu);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ExecutorService executor = Executors.newCachedThreadPool();
                        HttpReqThread thread = new HttpReqThread(AutoFillService.getCurrentUserId(), Const.PAT_TAR_USER);
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
        });

    }

    public void initUI(AppUser user) {
        final EditText modUsernameEditText = findViewById(R.id.mod_username_txt);
        final EditText modEmailEditText = findViewById(R.id.mod_email_txt);
        final EditText modPhoneEditText = findViewById(R.id.mod_phone_txt);
        final EditText modPasswordEditText = findViewById(R.id.mod_password_txt);
        final EditText modBirthEditText = findViewById(R.id.mod_birth_txt);
        final EditText modEduEditText = findViewById(R.id.mod_edu_txt);
        modUsernameEditText.setText(user.getUserName());
        modEmailEditText.setText(user.getUserEmail());
        modPhoneEditText.setText(user.getUserPhone());
        modPasswordEditText.setText(user.getPassWord());
        modBirthEditText.setText(user.getBirth());
        modEduEditText.setText(user.getEducation());
    }
}

package com.sail.exp.comp6442lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShowDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        TextView myText = findViewById(R.id.res_text);
        Intent myIntent = getIntent();
        myText.setText("STEP1 :" + myIntent.getStringExtra("STEP1") + "\nSTEP2 :" + myIntent.getStringExtra("STEP2") + "\nSTEP3 :" + myIntent.getStringExtra("STEP3"));
    }

    public void getBack(View view) {
        finish();
    }
}

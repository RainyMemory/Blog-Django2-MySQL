package com.sail.exp.comp6442lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView myViiew = null;
    Button step1 = null;
    Button step2 = null;
    Button step3 = null;
    int count1 = 0;
    int count2 = 0;
    int count3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myViiew = findViewById(R.id.Text);
        step1 = findViewById(R.id.but_step1);
        step2 = findViewById(R.id.but_setp2);
        step3 = findViewById(R.id.but_step3);
    }

    public void choice1Pressed(View view) {
        myViiew.setText("Step 1, confirmed");
        count1++;
        step1.setText("STEP1 " + count1);
    }

    public void choice2Pressed(View view) {
        myViiew.setText("Step 2, confirmed");
        count2++;
        step2.setText("STEP2 " + count2);
    }

    public void choice3Pressed(View view) {
        myViiew.setText("Step 3, confirmed");
        count3++;
        step3.setText("STEP3 " + count3);
    }

    public void jumpNext(View view) {
        Intent myIntent = new Intent(MainActivity.this, ShowDetailActivity.class);
        myIntent.putExtra("STEP1", ""+count1);
        myIntent.putExtra("STEP2", ""+count2);
        myIntent.putExtra("STEP3", ""+count3);
        startActivity(myIntent);
    }

}

package com.ecnu.broadcastapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        show=findViewById(R.id.show);

        Intent intent=getIntent();
        String info=intent.getStringExtra("word")+" "+intent.getIntExtra("num",-2);

        show.setText(info);
    }
}
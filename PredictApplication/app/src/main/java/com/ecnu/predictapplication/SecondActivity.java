package com.ecnu.predictapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView mGroup=findViewById(R.id.group);
        TextView mZhu=findViewById(R.id.zhu);
        TextView mKe=findViewById(R.id.ke);
        TextView mRes=findViewById(R.id.res);

        Bundle bundle=getIntent().getExtras();
        mGroup.setText(bundle.getString("group"));
        mZhu.setText(bundle.getString("zhu"));
        mKe.setText(bundle.getString("ke"));

        String res=bundle.getString("res").trim().replace(" ","或");
        mRes.setText(res);

    }
}
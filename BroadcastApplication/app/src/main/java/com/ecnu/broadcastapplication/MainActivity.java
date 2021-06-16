package com.ecnu.broadcastapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        send=findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.ecnu.broadcastapplication.MY_BROADCAST");
                intent.putExtra("num",100);
                intent.putExtra("word","hello");


                // 解决高版本安卓无法正常发送自定义广播问题
                intent.setComponent(new ComponentName("com.ecnu.broadcastapplication",
                        "com.ecnu.broadcastapplication.ParamReceiver"));

                sendBroadcast(intent);
            }
        });
    }
}
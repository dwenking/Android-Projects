package com.ecnu.httpapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private MyHandler mHandler;

    public static final int UPDATE=1;
    public static final int ERROR=0;

    TextView returnCode;
    TextView returnValue;
    TextView returnMsg;
    Button web;

    int code=-1;
    String value="";
    String msg="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        returnCode=findViewById(R.id.returnCode);
        returnValue=findViewById(R.id.returnValue);
        returnMsg=findViewById(R.id.returnMsg);
        web=findViewById(R.id.web);

        returnCode.setText("code:");
        returnValue.setText("value:");
        returnMsg.setText("msg:");

        mHandler = new MyHandler(this);

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkHttpClient client=new OkHttpClient();
                            RequestBody requestBody=new FormBody.Builder()
                                    .add("userId","10195101525")
                                    .add("style","json").build();

                            Request request=new Request.Builder()
                                    .url("http://115.29.231.93:8080/CkeditorTest/AndroidTest")
                                    .post(requestBody)
                                    .build();

                            Message message=new Message();

                            try {
                                Response response = client.newCall(request).execute();
                                String responseData=response.body().string();
                                Log.d("back",responseData);
                                JSONObject jsonObject=new JSONObject(responseData);

                                code=jsonObject.getInt("returnCode");

                                if(code>=0){
                                    value=jsonObject.getString("returnValue");
                                    msg="null";
                                }
                                else{
                                    value="null";
                                    msg=jsonObject.getString("returnMsg");
                                }

                                message.what=UPDATE;
                                mHandler.sendMessage(message);
                            } catch (IOException | JSONException e) {
                                message.what=ERROR;
                                mHandler.sendMessage(message);

                                e.printStackTrace();
                            }
                        }
                    }).start();

            }
        });

    }

    static class MyHandler extends Handler {
        // WeakReference to the outer class's instance.
        private WeakReference<MainActivity> mOuter;

        public MyHandler(MainActivity activity) {
            mOuter = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message message) {
            MainActivity outer = mOuter.get();
            if(outer != null) {
                // Do something with outer as your wish.
                switch (message.what){
                    case UPDATE:
                        outer.returnCode.setText("code:"+outer.code);
                        outer.returnValue.setText("value:"+outer.value);
                        outer.returnMsg.setText("msg:"+outer.msg);
                        break;
                    case ERROR:
                        Toast.makeText(outer,"error happened!",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        // Remove all Runnable and Message.
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
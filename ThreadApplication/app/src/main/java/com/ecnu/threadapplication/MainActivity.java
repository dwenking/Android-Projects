package com.ecnu.threadapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    String content;

    public static final int RUNNING=1;
    public static final int FINISH=0;
    public static final int ERROR=-1;

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case RUNNING:
                    textView.setText("数据等待中");
                    break;
                case FINISH:

                    List<StringEntity> products = SQLite.select()
                            .from(StringEntity.class)
                            .orderBy(StringEntity_Table.id, false)// 按照 id 升序
                            .limit(1)// 限制 3 条
                            .queryList();// 返回的 list 不为 null，但是可能为 empty

                    if(products==null||products.size()==0){
                        Message mess=new Message();
                        mess.what=ERROR;
                        handler.sendMessage(mess);
                        break;
                    }
                    textView.setText(products.get(0).getContent());
                    break;
                case ERROR:
                    Toast.makeText(MainActivity.this,"error happened!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.button);
        textView=findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Message message=new Message();
                            message.what=RUNNING;
                            handler.sendMessage(message);

                            sleep(10*1000);

                            StringEntity s=new StringEntity();
                            s.setContent(getRandomString(10));
                            s.save();

                            Message message1=new Message();
                            message1.what=FINISH;
                            handler.sendMessage(message1);
                        }catch (Exception e){
                            Message message=new Message();
                            message.what=ERROR;
                            handler.sendMessage(message);

                            e.printStackTrace();
                        }


                    }
                }).start();
            }
        });
    }

    //length用户要求产生字符串的长度
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
package com.ecnu.servicenumapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button begin;
    Button end;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        begin=findViewById(R.id.begin);
        end=findViewById(R.id.end);
        textView=findViewById(R.id.textView);

        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLite.delete(RandomNum.class)
                        .execute();
                Intent intent=new Intent(MainActivity.this,GenerateService.class);
                intent.putExtra("type",GenerateService.ON);
                startService(intent);
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,GenerateService.class);
                intent.putExtra("type",GenerateService.OFF);
                startService(intent);

                List<RandomNum> products = SQLite.select()
                        .from(RandomNum.class)
                        .queryList();// 返回的 list 不为 null，但是可能为 empty

                if(products==null||products.size()==0){
                    textView.setText("null");
                }
                else{
                    long res=0;
                    for(RandomNum num:products){
                        res+=num.getNum();
                    }
                    textView.setText(String.valueOf(res));
                }
            }
        });

    }
}
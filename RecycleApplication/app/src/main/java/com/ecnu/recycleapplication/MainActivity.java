package com.ecnu.recycleapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Data> commonSenseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.data_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); 实现水平滑动
        recyclerView.setLayoutManager(layoutManager);
        DataAdapter adapter = new DataAdapter(commonSenseList);
        recyclerView.setAdapter(adapter);
        recyclerView.getItemAnimator().setChangeDuration(0);// 设置删除条目时刷新动画不显示

        adapter.setOnItemClickListener(new DataAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Data tmp = commonSenseList.get(position);
                String content = tmp.getDes();
                AlertDialog aldg;
                AlertDialog.Builder adBd=new AlertDialog.Builder(MainActivity.this);
                adBd.setTitle("日志记录");
                adBd.setMessage(content);
                adBd.setNegativeButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        commonSenseList.remove(position); //从列表中移除数据
                        adapter.notifyItemRemoved(position); //通知移除item

                        adapter.notifyItemRangeChanged(0,commonSenseList.size()); //刷新数据（不加偶尔会删除 item 的位置错误）

                    }
                });

                aldg=adBd.create();
                aldg.show();

            }
        });
    }

    private void init() {
        List<Data> tmp = new ArrayList<>();
        for(int i=0;i<10;i++){
            Data data=new Data();
            data.setTime(new Date());
            data.setDes("This is test");
            data.setEvent("Event"+String.valueOf(i));
            tmp.add(data);
        }
        commonSenseList = tmp;
    }
}
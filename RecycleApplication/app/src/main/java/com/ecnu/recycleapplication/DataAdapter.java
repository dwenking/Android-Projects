package com.ecnu.recycleapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>{
    private List<Data> diaryList;// 必须


    static class ViewHolder extends RecyclerView.ViewHolder{
        // 根据布局文件内容来指定
        TextView time;
        TextView title;

        public ViewHolder(View view){
            super(view);
            time= view.findViewById(R.id.time);
            title= view.findViewById(R.id.title);
        }
    }
    public DataAdapter(List<Data> tDiaryList){
        diaryList = tDiaryList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item,parent,false); // 修改成对应item
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Data data = diaryList.get(position);
        holder.time.setText(String.valueOf(data.getTime()));
        holder.title.setText(data.getEvent());

        // 点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }
    @Override
    public int getItemCount(){
        return diaryList.size();
    }
    //点击事件
    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(int position);
    }
    private OnItemClickListener listener;
    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
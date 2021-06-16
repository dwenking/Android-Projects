package com.ecnu.listviewapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PersonAdapter extends ArrayAdapter<Person> {

    private int resourceId;

    public PersonAdapter(Context context, int textViewResourceId, List<Person> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Person person=getItem(position);
        View view;
        ViewHolder viewHolder;

        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            viewHolder=new ViewHolder();
            viewHolder.name=view.findViewById(R.id.name);
            viewHolder.phone=view.findViewById(R.id.phone);

            view.setTag(viewHolder); // 将viewholder存在view中
        }else{
            view=convertView; // 之前的布局缓存
            viewHolder=(ViewHolder)view.getTag();
        }

        viewHolder.name.setText(person.getName());
        viewHolder.phone.setText(person.getPhone());

        return view;
    }

    // 内部类，用于缓存实例
    class ViewHolder{
        TextView name;
        TextView phone;
    }

}

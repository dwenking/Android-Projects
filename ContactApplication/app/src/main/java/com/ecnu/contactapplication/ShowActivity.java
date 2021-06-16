package com.ecnu.contactapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Contacts;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

public class ShowActivity extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        text=findViewById(R.id.text);

        List<Person> people = new Select().
                from(Person.class).
                orderBy(Person_Table.name, true).
                queryList();

        String content="";
        for(Person p:people){
           content+=p.getName()+" "+p.getPhone()+"\n";
        }

        text.setText(content);
    }
}
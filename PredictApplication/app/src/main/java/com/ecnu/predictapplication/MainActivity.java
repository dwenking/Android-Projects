package com.ecnu.predictapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Spinner group;
    Spinner zhu;
    Spinner ke;
    Button submit;

    String[] mItems_group = {"Item 1", "Item 2"};

    String[] mItems_res1 = {"a", "b"};
    String[] mItems_res2 = {"c", "d"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        group=findViewById(R.id.group);
        zhu=findViewById(R.id.zhu);
        ke=findViewById(R.id.ke);
        submit=findViewById(R.id.submit);

        ArrayAdapter<String> adapter_group = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems_group);
        adapter_group.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        group.setAdapter(adapter_group);

        // 初始化Adapter
        ArrayAdapter<String> adapter_res1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems_res1);
        adapter_res1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter_res2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems_res2);
        adapter_res2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Bundle bundle =new Bundle();

        group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos){
                    case 0:
                        zhu.setAdapter(adapter_res1);
                        ke.setAdapter(adapter_res1);
                        break;
                    case 1:
                        zhu.setAdapter(adapter_res2);
                        ke.setAdapter(adapter_res2);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO
                Log.d("MainActivity","group: nothing selected!");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);

                String zhu_res=(String)zhu.getSelectedItem();
                String ke_res=(String)ke.getSelectedItem();

                if(zhu_res.equals(ke_res)){
                    Toast.makeText(MainActivity.this,"主客场队伍不能相同！",Toast.LENGTH_SHORT).show();
                    return;
                }

                bundle.putString("group",(String)group.getSelectedItem());
                bundle.putString("zhu",(String)zhu.getSelectedItem());
                bundle.putString("ke",(String)ke.getSelectedItem());

                LinearLayout mLayout=findViewById(R.id.checkbox);
                int count=mLayout.getChildCount();
                StringBuilder tmp = new StringBuilder();
                for(int i=0;i<count;i++){
                    View child=mLayout.getChildAt(i);

                    if(child instanceof CheckBox){
                        CheckBox cb=(CheckBox) child;
                        if(cb.isChecked()){
                            tmp.append(cb.getText()+" ");
                        }
                    }
                    bundle.putString("res",String.valueOf(tmp));
                }

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
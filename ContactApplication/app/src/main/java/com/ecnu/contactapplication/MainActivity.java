package com.ecnu.contactapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button load;
    Button show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load=findViewById(R.id.load);
        show=findViewById(R.id.show);

        load.setOnClickListener(this);
        show.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else{
            switch (v.getId()){
                case R.id.load:
                    testReadAllContacts();
                    Toast.makeText(this,"success",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.show:
                    Intent intent=new Intent(MainActivity.this,ShowActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    private void testReadAllContacts() {
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        int contactIdIndex = 0;
        int nameIndex = 0;

        if(cursor.getCount() > 0) {
            contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        }
        while(cursor.moveToNext()) {
            String contactId = cursor.getString(contactIdIndex);
            String name = cursor.getString(nameIndex);
            StringBuffer phoneNumber=new StringBuffer("");
            StringBuffer email=new StringBuffer("");

            /*
             * 查找该联系人的phone信息
             */
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                    null, null);
            int phoneIndex = 0;
            if(phones.getCount() > 0) {
                phoneIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            }
            while(phones.moveToNext()) {
                phoneNumber.append(phones.getString(phoneIndex)+"\n");
            }

            /*
             * 查找该联系人的email信息
             */
            Cursor emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + contactId,
                    null, null);
            int emailIndex = 0;
            if(emails.getCount() > 0) {
                emailIndex = emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
            }
            while(emails.moveToNext()) {
                email.append(emails.getString(emailIndex)+"\n");
            }

            phoneNumber.deleteCharAt(phoneNumber.length()-1);
            email.deleteCharAt(email.length()-1);

            // 将获取的参数传递给对象person
            Person person=new Person();
            person.setEmail(String.valueOf(email));
            person.setName(name);
            person.setPhone(String.valueOf(phoneNumber));
            person.save();

        }

        cursor.close();
    }


    // 在该函数里处理多个危险权限的逻辑
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                switch (permissions[0]){
                    // 访问联系人
                    case Manifest.permission.READ_CONTACTS:
                        if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                            testReadAllContacts();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"You denied the permission",Toast.LENGTH_SHORT).show();
                        }
                        break;

                    default:
                }
                break;
            default:
        }
    }
}
package com.ecnu.listviewapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Person> personList=new ArrayList<>();
    String now_phone="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else{
            testReadAllContacts();
        }

        PersonAdapter adapter=new PersonAdapter(MainActivity.this,R.layout.list_item,personList);
        ListView listView=findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        // 点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position,long id){
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                b.setTitle("联系人信息");
                Person p=personList.get(position);

                b.setMessage("姓名："+p.getName()+"\n"+"电话："+p.getPhone()+"\n"+"邮箱："+p.getEmail());
                now_phone="tel:"+p.getPhone();

                b.setNegativeButton("返回",null);
                b.setPositiveButton("拨打",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!=
                                PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
                        }else{
                            try{
                                call(now_phone);
                            }catch (Exception e){
                                Toast.makeText(MainActivity.this,"wrong phone number",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                b.show();
            }
        });
    }

    private void call(String phone){
        try{
            Intent intent=new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(phone));
            startActivity(intent);
        }catch(Exception e){
            e.printStackTrace();
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

            Log.d("email",String.valueOf(email));

            Person person=new Person();
            person.setEmail(String.valueOf(email));
            person.setName(name);
            person.setPhone(String.valueOf(phoneNumber));

            personList.add(person);
        }

        cursor.close();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                switch (permissions[0]){
                    case Manifest.permission.READ_CONTACTS:
                        if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                            testReadAllContacts();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"You denied the permission",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case Manifest.permission.CALL_PHONE:
                        if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                            try{
                                call(now_phone);
                            }catch (Exception e){
                                Toast.makeText(MainActivity.this,"wrong phone number",Toast.LENGTH_SHORT).show();
                            }
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
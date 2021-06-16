package com.ecnu.broadcastapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class ParamReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        //Toast.makeText(context,"get",Toast.LENGTH_SHORT).show();
        String word=intent.getStringExtra("word");
        int num=intent.getIntExtra("num",-1);

        //获取通知管理实例，因为是在广播接收器中所以加了context
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        //8.0+版本判断
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("important","Important",NotificationManager.IMPORTANCE_HIGH);
            assert manager != null;
            manager.createNotificationChannel(channel);
        }

        //通知点击事项
        Intent intent1=new Intent(context,ShowActivity.class);
        intent1.putExtra("word",word);
        intent1.putExtra("num",num);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent1,0);

        Notification notification= new NotificationCompat.Builder(context,"important")
                .setContentTitle("通知")
                .setContentText("点击查看详情")
                .setSmallIcon(R.mipmap.ic_launcher)//通知图标
                .setContentIntent(pendingIntent)//点击跳到通知详情
                .setAutoCancel(true)//当点击通知后显示栏的通知不再显示
                .build();
        assert manager != null;
        manager.notify(1,notification);
    }
}
package com.ecnu.serviceapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.widget.Toast;

public class TimerService extends Service {


    public TimerService() {
    }

    // 与活动进行通信
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // 服务初始化
    @Override
    public void onCreate(){
        super.onCreate();

    }

    // 每次服务启动时调用
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                long sysTime = System.currentTimeMillis();
                CharSequence sysTimeStr = DateFormat.format("yyyy.MM.dd hh:mm:ss", sysTime); // 获得系统时间

                Looper.prepare();
                Toast.makeText(getApplicationContext(),"hello\n"+sysTimeStr,Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
        AlarmManager manager=(AlarmManager) getSystemService(ALARM_SERVICE);
        int interval=10*1000; // 十秒
        long trigger= SystemClock.elapsedRealtime()+interval; // elapsedRealtime()开机到现在的毫秒数

        Intent i=new Intent(this,TimerService.class);
        PendingIntent pi=PendingIntent.getService(this,0,i,0);

        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,trigger,pi); // 从系统开机开始算起，并唤醒CPU

        return super.onStartCommand(intent, flags, startId);
    }

    // 回收资源
    @Override
    public void onDestroy(){
        super.onDestroy();
    }


}
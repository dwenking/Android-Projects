package com.ecnu.servicenumapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import java.util.Random;

public class GenerateService extends Service {

    public static final int ON=1;
    public static final int OFF=0;

    public GenerateService() {
    }

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

        AlarmManager manager=(AlarmManager) getSystemService(ALARM_SERVICE);

        Intent i=new Intent(this,GenerateService.class);
        i.putExtra("type",ON); // 注意这里自启动时也要设置type
        PendingIntent pi=PendingIntent.getService(this,0,i,0);

        switch (intent.getIntExtra("type",-1)){
            case OFF:
                manager.cancel(pi); // 取消定时任务
                stopSelf();
                break;
            case ON:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 任务内容
                        Random r = new Random();
                        int ran = r.nextInt(1000);
                        RandomNum tmp=new RandomNum();
                        tmp.setNum(ran);
                        tmp.save();
                    }
                }).start();
                int interval=20*1000; // 十秒
                long trigger= SystemClock.elapsedRealtime()+interval; // elapsedRealtime()开机到现在的毫秒数
                manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,trigger,pi); // 从系统开机开始算起，并唤醒CPU
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    // 回收资源
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
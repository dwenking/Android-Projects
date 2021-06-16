package com.ecnu.threadapplication;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
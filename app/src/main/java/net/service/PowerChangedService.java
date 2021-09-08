package net.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;

import net.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Copyright (C) 2021,2021/9/8, a Tencent company. All rights reserved.
 * <p>
 * User : v_xhangxie
 * <p>
 * Desc :
 */
public class PowerChangedService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intent = new IntentFilter();
        intent.addAction(Intent.ACTION_BATTERY_CHANGED);
        intent.addAction(Intent.ACTION_POWER_CONNECTED);
        registerReceiver(receiver, intent);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int rawLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int percentage = rawLevel / scale;
                int level = percentage * 100;
                EventBus.getDefault().post(new MessageEvent("BATTERY_CHANGED", level + ""));
            }
            if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
                EventBus.getDefault().post(new MessageEvent("POWER_CONNECTED"));
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}

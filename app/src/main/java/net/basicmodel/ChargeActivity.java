package net.basicmodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.don.clockviewlibrary.ClockView;
import com.tencent.mmkv.MMKV;

import net.utils.ResourceManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (C) 2021,2021/9/8, a Tencent company. All rights reserved.
 * <p>
 * User : v_xhangxie
 * <p>
 * Desc :
 */
public class ChargeActivity extends AppCompatActivity {

    @BindView(R.id.power)
    TextView power;
    @BindView(R.id.bg)
    ImageView bg;
    @BindView(R.id.anim)
    ImageView anim;

    String defaultUlr = "https://magichua.club/preview/img/bg_1.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_charge);
        ButterKnife.bind(this);
        initView();
        addReceiver();
    }

    private void initView() {
        String bg = MMKV.defaultMMKV().decodeString("key_bg", defaultUlr);
        String anim = MMKV.defaultMMKV().decodeString("anim", ResourceManager.Companion.get().getResId2Str(this, R.drawable.anim1));
        Glide.with(this).load(bg).into(this.bg);
        Glide.with(this).load(anim).into(this.anim);
    }

    private void addReceiver() {
        IntentFilter intent = new IntentFilter();
        intent.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intent.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, intent);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int rawLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int percentage = rawLevel / scale;
                int level = percentage * 100;
                power.setText("current power" + "\n" + level + "%");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}

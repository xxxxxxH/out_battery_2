package net.basicmodel;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.weeboos.permissionlib.PermissionRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tencent.mmkv.MMKV;

import net.utils.GlideEngine;
import net.utils.ResourceManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.current)
    TextView current;
    @BindView(R.id.rootViewBg)
    ImageView rootBg;
    @BindView(R.id.anim)
    ImageView anim;
    @BindView(R.id.btn)
    TextView btn;
    String[] permission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    String defaultUrl = "https://magichua.club/preview/img/bg_1.jpg";
    BottomSheetDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MMKV.initialize(this);
        requestPermissions();
    }

    private void requestPermissions() {
        PermissionRequest.getInstance().build(this)
                .requestPermission(new PermissionRequest.PermissionListener() {
                    @Override
                    public void permissionGranted() {
                        initView();
                    }

                    @Override
                    public void permissionDenied(ArrayList<String> permissions) {
                        finish();
                    }

                    @Override
                    public void permissionNeverAsk(ArrayList<String> permissions) {
                        finish();
                    }
                }, permission);
    }

    private void initView() {
        String bgUrl = MMKV.defaultMMKV().decodeString("key_bg", defaultUrl);
        String animUrl = MMKV.defaultMMKV().decodeString("anim", new ResourceManager().getResId2Str(this, R.drawable.anim1));
        Glide.with(this).load(bgUrl).into(rootBg);
        Glide.with(this).load(animUrl).into(anim);
        btn.setOnClickListener(this);
    }

    private void addReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
    }

    private BottomSheetDialog createBottomSheet() {
        BottomSheetDialog d = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null);
        view.findViewById(R.id.btn1).setOnClickListener(this);
        view.findViewById(R.id.btn2).setOnClickListener(this);
        view.findViewById(R.id.btn3).setOnClickListener(this);
        view.findViewById(R.id.btn4).setOnClickListener(this);
        d.setContentView(view);
        return d;
    }

    private void startActivity(String type) {
        Intent i = new Intent();
        i.putExtra("type", type);
        startActivity(i);
    }

    private void openGallery() {
        PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine()).forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
            String path = list.get(0).getPath();
            Glide.with(this).load(path).into(rootBg);
            MMKV.defaultMMKV() !!.encode("key_bg", path);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn:
                dialog = createBottomSheet();
                dialog.show();
                break;
            case R.id.btn1:
                startActivity("back");
                break;
            case R.id.btn2:
                break;
            case R.id.btn3:
                startActivity("anim");
                break;
            case R.id.btn4:
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                break;
        }

    }
}

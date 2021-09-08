package net.basicmodel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import net.adapter.CommonAdapter;
import net.event.MessageEvent;
import net.utils.ResourceManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BackgroundActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    String type;
    ArrayList<String> data = null;
    CommonAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_background);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if (type.equals("back")) {
            data = new ResourceManager().getBackgroundRes();
        } else {
            data = ResourceManager.Companion.get().getAnimRes(this);
        }
    }

    private void initView() {
        adapter1 = new CommonAdapter(this, data, type);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter1);
        adapter1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter<?, ?> adapter, View view, int position) {
                EventBus.getDefault().post(new MessageEvent(adapter1.getType(), data.get(position)));
                finish();
            }
        });
    }
}

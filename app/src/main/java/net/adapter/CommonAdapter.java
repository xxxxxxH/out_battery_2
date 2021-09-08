package net.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import net.basicmodel.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2021,2021/9/8, a Tencent company. All rights reserved.
 * <p>
 * User : v_xhangxie
 * <p>
 * Desc :
 */
public class CommonAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    Context context;
    ArrayList<String> data;
    String type;


    public CommonAdapter(Context context, ArrayList<String> data, String type) {
        super(R.layout.layout_item,data);
        this.context = context;
        this.data = data;
        this.type = type;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        ImageView imageView = baseViewHolder.getView(R.id.item_img);
        Glide.with(context).load(s).placeholder(R.mipmap.ic_launcher_round).into(imageView);
    }

    public String getType() {
        return type;
    }
}

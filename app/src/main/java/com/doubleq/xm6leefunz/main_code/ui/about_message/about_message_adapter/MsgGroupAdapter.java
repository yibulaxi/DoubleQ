package com.doubleq.xm6leefunz.main_code.ui.about_message.about_message_adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.doubleq.model.home_msg.DataMsgGroup;
import com.doubleq.xm6leefunz.R;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MsgGroupAdapter extends BaseQuickAdapter<DataMsgGroup,BaseViewHolder> {

    Context context;
    public MsgGroupAdapter(List<DataMsgGroup> data, Context context)
    {
        super(R.layout.item_g_msg,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DataMsgGroup item)
    {
        Glide.with(context).load(item.getMsg_g_image())
                .bitmapTransform(new CropCircleTransformation(context))
                .crossFade(1000)
                .into((ImageView) helper.getView(R.id.frag_img_msg_g_head));
        helper.setText(R.id.frag_img_msg_g_name,item.getMsg_g_name());
        helper.setText(R.id.frag_img_msg_g_contants,item.getMsg_g_contants());
        helper.setText(R.id.frag_img_msg_g_time,item.getMsg_g_time());
        helper.setText(R.id.frag_img_msg_g_num,item.getMsg_g_num());

    }

}

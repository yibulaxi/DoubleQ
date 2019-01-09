package com.doubleq.xm6leefunz.about_chat.adapter.holder;

import android.graphics.Color;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doubleq.model.DataJieShou;
import com.doubleq.xm6leefunz.about_base.web_base.SplitWeb;
import com.doubleq.xm6leefunz.about_chat.ChatActivity;
import com.doubleq.xm6leefunz.about_chat.adapter.ChatAdapter;
import com.doubleq.xm6leefunz.about_utils.GlideCacheUtil;
import com.doubleq.xm6leefunz.about_utils.TimeUtil;
import com.doubleq.xm6leefunz.about_utils.about_file.FilePath;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.projects.zll.utilslibrarybyzll.aboututils.StrUtils;
import com.rance.chatui.R;
import com.rance.chatui.enity.MessageInfo;
import com.rance.chatui.util.Constants;
import com.rance.chatui.util.Utils;
import com.rance.chatui.widget.BubbleImageView;
import com.rance.chatui.widget.GifTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 */
public class ChatSendViewHolder extends BaseViewHolder<DataJieShou.RecordBean> {

    @BindView(R.id.chat_item_date)
    TextView chatItemDate;
    @BindView(R.id.chat_item_header)
    ImageView chatItemHeader;
    @BindView(R.id.chat_item_content_text)
    GifTextView chatItemContentText;
    @BindView(R.id.chat_item_content_image)
    BubbleImageView chatItemContentImage;
    @BindView(R.id.chat_item_fail)
    ImageView chatItemFail;
    @BindView (R.id.chat_item_progress)
    ProgressBar chatItemProgress;
    @BindView (R.id.chat_item_voice)
    ImageView chatItemVoice;
    @BindView(R.id.chat_item_layout_content)
    LinearLayout chatItemLayoutContent;
    @BindView(R.id.chat_item_voice_time)
    TextView chatItemVoiceTime;
    private ChatAdapter.onItemClickListener onItemClickListener;
    private Handler handler;
    MotionEvent event;
    public ChatSendViewHolder(ViewGroup parent, ChatAdapter.onItemClickListener onItemClickListener, Handler handler) {
        super(parent, R.layout.item_chat_send);
        ButterKnife.bind(this, itemView);
        this.onItemClickListener = onItemClickListener;
        this.handler = handler;
    }
    @Override
    public void setData(final  DataJieShou.RecordBean data) {
//        chatItemDate.setText("2018-08-08");
        if (StrUtils.isEmpty(data.getRequestTime()))
        {
            chatItemDate.setVisibility(View.GONE);
        }else
        {
            chatItemDate.setText(TimeUtil.formatDisplayTime(data.getRequestTime(),null));
            chatItemDate.setVisibility(View.VISIBLE);
        }
//        chatItemDate.setText(data.getTime() != null ? data.getTime() : "");
//        Glide.with(getContext()).load(SplitWeb.USER_HEADER).into(chatItemHeader);

        GlideCacheUtil.getInstance().clearImageAllCache(getContext());
        List<String> fileName = FilePath.getFilesAllName(FilePath.getAbsPath()+"chatHead/");
        if (fileName!=null&&fileName.size()>0)
        {
            String path=fileName.get(fileName.size()-1);
            Glide.with(getContext()).load(path)
                    .dontAnimate()
                    .bitmapTransform(new CropCircleTransformation(getContext()))
                    .into(chatItemHeader);
        }

//        Glide.with(getContext()).load(SplitWeb.USER_HEADER)
//                .bitmapTransform(new CropCircleTransformation(getContext()))
//                .into(chatItemHeader);


        chatItemHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onHeaderClick(getDataPosition(),Constants.CHAT_ITEM_TYPE_RIGHT,data.getUserId());
            }
        });


        chatItemContentText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        event = e;
                        break;
                    default:
                        break;
                }
                // 如果onTouch返回false,首先是onTouch事件的down事件发生，此时，如果长按，触发onLongClick事件；
                // 然后是onTouch事件的up事件发生，up完毕，最后触发onClick事件。
                return false;
            }
        });
        chatItemContentText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.onConClick(v,event,getAdapterPosition(),data.getMessage());
                return true;
            }
        });
//        chatItemContentText.setTextIsSelectable(true);
        try {
            switch (data.getMessageType())
            {
                case Constants.CHAT_TEXT:
                    chatItemContentText.setTextColor(Color.BLACK);
                    chatItemContentText.setSpanText(handler, data.getMessage(), true);
                    chatItemVoice.setVisibility(View.GONE);
                    chatItemContentText.setVisibility(View.VISIBLE);
                    chatItemLayoutContent.setVisibility(View.VISIBLE);
                    chatItemVoiceTime.setVisibility(View.GONE);
                    chatItemContentImage.setVisibility(View.GONE);
                    break;
                case Constants.CHAT_PICTURE:
                    chatItemVoice.setVisibility(View.GONE);
                    chatItemLayoutContent.setVisibility(View.GONE);
                    chatItemVoiceTime.setVisibility(View.GONE);
                    chatItemContentText.setVisibility(View.GONE);
                    chatItemContentImage.setVisibility(View.VISIBLE);
                    Glide.with(getContext()).load(data.getMessage()).into(chatItemContentImage);
                    chatItemContentImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onImageClick(chatItemContentImage, getDataPosition());
                        }
                    });
                    break;
                case Constants.CHAT_EMOTION:
                    chatItemContentText.setSpanText(handler, data.getMessage(), true);
                    chatItemVoice.setVisibility(View.GONE);
                    chatItemContentText.setVisibility(View.VISIBLE);
                    chatItemLayoutContent.setVisibility(View.VISIBLE);
                    chatItemVoiceTime.setVisibility(View.GONE);
                    chatItemContentImage.setVisibility(View.GONE);
                    break;
                case Constants.CHAT_FILE:
                    chatItemVoice.setVisibility(View.VISIBLE);
                    chatItemLayoutContent.setVisibility(View.VISIBLE);
                    chatItemContentText.setVisibility(View.GONE);
                    chatItemVoiceTime.setVisibility(View.VISIBLE);
                    chatItemContentImage.setVisibility(View.GONE);
                    chatItemVoiceTime.setText("文件");
    //                chatItemVoiceTime.setText(Utils.formatTime(data.getVoiceTime()));
                    chatItemLayoutContent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onVoiceClick(chatItemVoice, getDataPosition());
                        }
                    });
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (data.getMessage() != null) {
//            chatItemContentText.setSpanText(handler, data.getContent(), true);
//            chatItemVoice.setVisibility(View.GONE);
//            chatItemContentText.setVisibility(View.VISIBLE);
//            chatItemLayoutContent.setVisibility(View.VISIBLE);
//            chatItemVoiceTime.setVisibility(View.GONE);
//            chatItemContentImage.setVisibility(View.GONE);
//        } else if (data.getImageUrl() != null) {
//            chatItemVoice.setVisibility(View.GONE);
//            chatItemLayoutContent.setVisibility(View.GONE);
//            chatItemVoiceTime.setVisibility(View.GONE);
//            chatItemContentText.setVisibility(View.GONE);
//            chatItemContentImage.setVisibility(View.VISIBLE);
//            Glide.with(getContext()).load(data.getImageUrl()).into(chatItemContentImage);
//            chatItemContentImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClickListener.onImageClick(chatItemContentImage, getDataPosition());
//                }
//            });
//        } else if (data.getFilepath() != null) {
//            chatItemVoice.setVisibility(View.VISIBLE);
//            chatItemLayoutContent.setVisibility(View.VISIBLE);
//            chatItemContentText.setVisibility(View.GONE);
//            chatItemVoiceTime.setVisibility(View.VISIBLE);
//            chatItemContentImage.setVisibility(View.GONE);
//            chatItemVoiceTime.setText(Utils.formatTime(data.getVoiceTime()));
//            chatItemLayoutContent.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClickListener.onVoiceClick(chatItemVoice, getDataPosition());
//                }
//            });
//        }
        switch (data.getSendState()) {
            case Constants.CHAT_ITEM_SENDING:
                chatItemProgress.setVisibility(View.VISIBLE);
                chatItemFail.setVisibility(View.GONE);
                break;
            case Constants.CHAT_ITEM_SEND_ERROR:
                chatItemProgress.setVisibility(View.GONE);
                chatItemFail.setVisibility(View.VISIBLE);
                break;
            case Constants.CHAT_ITEM_SEND_SUCCESS:
                chatItemProgress.setVisibility(View.GONE);
                chatItemFail.setVisibility(View.GONE);
                break;
        }
    }
}

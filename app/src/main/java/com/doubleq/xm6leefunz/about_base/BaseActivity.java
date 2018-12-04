package com.doubleq.xm6leefunz.about_base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;


import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.doubleq.model.CusJumpChatData;
import com.doubleq.model.DataJieShou;
import com.doubleq.model.DataNoticAddFriendNews;
import com.doubleq.xm6leefunz.R;
import com.doubleq.xm6leefunz.about_base.web_base.MessageEvent;
import com.doubleq.xm6leefunz.about_base.web_base.SplitWeb;
import com.doubleq.xm6leefunz.about_custom.loding.LoadingDialog;
import com.doubleq.xm6leefunz.about_utils.HelpUtils;
import com.doubleq.xm6leefunz.about_utils.NotificationUtil;
import com.doubleq.xm6leefunz.about_utils.SysRunUtils;
import com.doubleq.xm6leefunz.about_utils.TimeUtil;
import com.doubleq.xm6leefunz.about_utils.about_realm.new_home.CusChatData;
import com.doubleq.xm6leefunz.about_utils.about_realm.new_home.CusHomeRealmData;
import com.doubleq.xm6leefunz.about_utils.about_realm.new_home.RealmChatHelper;
import com.doubleq.xm6leefunz.about_utils.about_realm.new_home.RealmHomeHelper;
import com.projects.zll.utilslibrarybyzll.about_dialog.DialogUtils;
import com.projects.zll.utilslibrarybyzll.about_key.AppAllKey;
import com.projects.zll.utilslibrarybyzll.aboutsystem.AppManager;
import com.projects.zll.utilslibrarybyzll.aboutsystem.ScreenUtils;
import com.projects.zll.utilslibrarybyzll.aboutsystem.WindowBugDeal;
import com.projects.zll.utilslibrarybyzll.aboututils.StrUtils;
import com.projects.zll.utilslibrarybyzll.aboututils.ToastUtil;
import com.rance.chatui.util.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseActivity extends AppCompatActivity  {

    LoadingDialog.Speed speed = LoadingDialog.Speed.SPEED_TWO;
    private int repeatTime = 0;
    private long delayedTime = 200L;
    private long closeTime = 5000L;
    private int style = LoadingDialog.STYLE_LINE;
    String loadText = "加载中...";
    public static final int LOAD_SUCCESS = 1;
    public static final int LOAD_FAILED = 2;
    public static final int LOADING = 3;
    public static final int LOAD_WITHOUT_ANIM_SUCCESS = 4;
    public static final int LOAD_WITHOUT_ANIM_FAILED = 5;
    public static final int SAVE_YOU = 6;
    public static final int DOWN_DATA = 520;
    private LoadingDialog ld =null;
    //    是否拦截返回键  true 拦截
    private boolean intercept_back_event = true;
    //
    public Handler mHandler = null;
    public Handler mMsgHandler = null;
    Unbinder bind =null;
    View view;
    String simpleName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        simpleName = getClass().getSimpleName();
        ScreenUtils.setWindowStatusBarColor(AppManager.getAppManager().currentActivity(),R.color.app_theme);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            显示 内屏返回键
            if (!simpleName.equals("MainActivity")) {
                WindowBugDeal.checkDeviceHasNavigationBar(AppManager.getAppManager().currentActivity());
            } else
                WindowBugDeal.SetTop(AppManager.getAppManager().currentActivity());
        }
        initSwipeBackFinish();
        initBeforeContentView();
        setContentView(getLayoutView());
        initStateBar();
        bind = ButterKnife.bind(this);

        if (mHandler==null)
            mHandler= new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (msg != null)
                    {
                        switch (msg.what) {
                            case LOAD_SUCCESS:
                                ld.loadSuccess();
//                                ld.close();
                                break;
                            case LOAD_FAILED:
                                ld.loadFailed();
//                                ld.close();
                                break;
                            case LOADING:
                                ld.show();
                                break;
                            case LOAD_WITHOUT_ANIM_FAILED:
                                ld.loadFailed();
                                break;
                            case LOAD_WITHOUT_ANIM_SUCCESS:
                                ld.loadSuccess();
                                break;
                            case SAVE_YOU:
//                    ld.loadSuccess();
                                ld.close();
//                    ld.loadFailed();
                                break;
//                            case DOWN_DATA:
//                                if (ld!=null)
//                                {
//                                    ld.close();
//                                }
//                                dealObs(msg.obj.toString());
////                                onHandleMessage(msg);
//                                break;
                            default:
//                                onHandleMessage(msg);
                                break;
                        }
                    }
                    return false;
                }
            });
        realmHelper = new RealmHomeHelper(this);
//        realmChatHelper = new RealmChatHelper(this);

        initBaseView();
        EventBus.getDefault().register(this);

    }

    private void dealObs(String data) {

        //        判断返回成功的  字段
        String isSucess = HelpUtils.HttpIsSucess(data.toString());
//        Log.e("onEvent","activity"+messageEvent.getMessage());
        if (isSucess.equals(AppAllKey.CODE_OK)) {
//            判断返回的方法名
            String s = HelpUtils.backMethod(data.toString());
//            父类全局处理
            switch (s)
            {
//                接收消息
                case "privateReceive":
                    Log.e("ChatActivity","privateReceive="+data.toString());
                    initReceive(data.toString());
                    break;
                case "privateSend":
                    DataJieShou dataJieShou = JSON.parseObject(data.toString(), DataJieShou.class);
                    DataJieShou.RecordBean record = dataJieShou.getRecord();
                    realmHelper.updateMsg(record.getFriendsId()+SplitWeb.USER_ID,record.getMessage(),record.getRequestTime());//更新首页聊天界面数据（消息和时间）
                    receiveResultMsg(data.toString());
                    break;
//                    添加好友通知
                case "addFriendSend":
                    dealFriendData(data.toString());
                    break;
//                    重连后的连接成功
                case "coroutineUid":
                    ToastUtil.show("连接成功");
                    break;
//                    其他情况返回给子类
                default:
                    receiveResultMsg(data.toString());
                    break;
            }
        }
        else if (isSucess.equals("10086"))
        {
//            返回的自定义判断 ，则重连（返回的字段不可预测）
            sendWeb(SplitWeb.coroutineUid());
        }
        else {
            ToastUtil.show(isSucess);
        }
//        如果打开弹窗加载显示，收到服务器的返回0.5秒后自动关闭（防止反应太快还没显示清楚就隐藏）
        if (isSendDialog)
            mHandler.sendEmptyMessageDelayed(LOAD_SUCCESS, 500);
//        textView.setText(messageEvent.getMessage());
    }

    public void initStateBar() {
    }

    public RealmHomeHelper getRealm() {
        return realmHelper;
    }

    RealmHomeHelper realmHelper;
//    RealmChatHelper realmChatHelper;
    boolean isSendDialog=false;
    protected void sendWeb(String text) {
        isSendDialog=false;
        MyApplication.getmConnectManager().sendText(text);
    }
    protected void reconnect() {
        MyApplication.getmConnectManager().reconnect();
    }
    public static  void send(String text) {
        MyApplication.getmConnectManager().sendText(text);
    }
    protected void sendWebHaveDialog(String text,String loadText,String loadSuccessText) {
        isSendDialog=true;
        if ((ld != null))
            ld.close();
        ld = new LoadingDialog(this);
        ld.setLoadingText(loadText)
                .setSuccessText(loadSuccessText)
                .setInterceptBack(intercept_back_event)
                .setLoadSpeed(speed)
                .setRepeatCount(repeatTime)
//                .setDrawColor(Color.WHITE)
                .setLoadStyle(style)
                .show();
        saveForThesePeopleWhoDoNotCallCloseAndUseInterceptBackMethod(intercept_back_event);
        MyApplication.getmConnectManager().sendText(text);
    }
    private void saveForThesePeopleWhoDoNotCallCloseAndUseInterceptBackMethod(boolean intercept_back_event) {
        if (intercept_back_event) {
            mHandler.sendEmptyMessageDelayed(LOAD_FAILED, closeTime);
        }
    }


    protected void initBeforeContentView() {
    }

    protected void initBaseView() {
    }
    protected int getLayoutView() {
        return 0;
    }

    public void onHandleMessage(Message msg) {
    }

    protected boolean isGones() {
        return false;
    }

    @Override
    protected void onDestroy() {
//        realmChatHelper.close();
//        realmHelper.close();
        super.onDestroy();
        if (bind!=null) {
            bind.unbind();
            bind=null;
        }
        if ((ld != null))
            ld.close();
//        关闭eventbus
        EventBus.getDefault().unregister(this);
//        关闭弹窗
        DialogUtils.isShow();
    }
    //订阅方法，接收到服务器返回事件处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent){
        Stack<AppCompatActivity> stack = AppManager.getAppManager().getStack();
        if (stack!=null&&stack.size()!=0) {
            String stackLast = stack.get(stack.size() - 1).getClass().getSimpleName();
            String current = AppManager.getAppManager().currentActivity().getClass().getSimpleName();
            if (stackLast.equals(current))
            {
                dealObs(messageEvent.getMessage());
            }
        }
    }
    Bitmap bitmap;
    //    处理好友的信息
    private void dealFriendData(String message) {
        DataNoticAddFriendNews dataNoticAddFriendNews = JSON.parseObject(message, DataNoticAddFriendNews.class);
        final    DataNoticAddFriendNews.RecordBean record = dataNoticAddFriendNews.getRecord();
        if (record!=null)
        {
//            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.dou_logo );
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        bitmap = Glide.with(MyApplication.getAppContext())
                                .load(record.getHeadImg())
                                .asBitmap() //必须
                                .centerCrop()
                                .into(500, 500)
                                .get();
                        NotificationUtil notificationUtils = new NotificationUtil(getApplicationContext());
                        String remark = (!StrUtils.isEmpty(record.getRemark()))?record.getRemark():"没有备注";
                        notificationUtils.sendNotification(record.getNickName()+"加您为好友", "备注："+remark,bitmap,AppConfig.TYPE_NOTICE);
//                        NotificationUtil notificationUtils = new NotificationUtil(getApplicationContext());
//                        notificationUtils.sendNotification(cusJumpChatData, record.getFriendsName(), record.getMessage(), bitmap, AppConfig.TYPE_CHAT);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    //处理接收到消息的显示
    private void initReceive(String message) {
        DataJieShou dataJieShou = JSON.parseObject(message, DataJieShou.class);
        final DataJieShou.RecordBean record = dataJieShou.getRecord();
        AppConfig.CHAT_FRIEND_ID = record.getFriendsId();
        record.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
        record.setType(Constants.CHAT_ITEM_TYPE_LEFT);
//        CusChatData cusRealmChatMsg = new CusChatData();
//        String format = TimeUtil.sf.format(new Date());
//        cusRealmChatMsg.setCreated(format);
//        cusRealmChatMsg.setMessage(record.getMessage());
//        cusRealmChatMsg.setMessageType(record.getMessageType());
//        cusRealmChatMsg.setReceiveId(record.getFriendsId());
//        cusRealmChatMsg.setSendId(record.getUserId());
//        cusRealmChatMsg.setUserMessageType(record.getType());
//        cusRealmChatMsg.setTotalId(record.getFriendsId()+SplitWeb.USER_ID);

//        realmChatHelper.addRealmChat(cusRealmChatMsg);//更新聊天数据
//        Log.e("realmChatHelper","msg="+record.getMessage());
//
//        CusHomeRealmData homeRealmData = realmHelper.queryAllRealmChat(record.getFriendsId());
//
//        if (homeRealmData!=null) {
//            realmHelper.updateMsg(record.getFriendsId(), record.getMessage(), record.getRequestTime());//更新首页聊天界面数据（消息和时间）
//            realmHelper.updateNum(record.getFriendsId());//更新首页聊天界面数据（未读消息数目）
//        }
//        else
//        {
//            final CusHomeRealmData cusJumpChatData = new CusHomeRealmData();
//            cusJumpChatData.setHeadImg(record.getHeadImg());
//            cusJumpChatData.setFriendId(record.getFriendsId());
//            cusJumpChatData.setNickName(record.getFriendsName());
//            cusJumpChatData.setMsg(record.getMessage());
//            cusJumpChatData.setTime(record.getRequestTime());
//            cusJumpChatData.setNum(0);
////            realmHelper.updateNum(record.getFriendsId());
//            realmHelper.addRealmMsg(cusJumpChatData);
//        }
//                1代表现在是聊天界面
        if (!SplitWeb.IS_CHAT.equals("1"))
        {
//            不在聊天界面收到消息时候的处理
            noChatUI(record);

        }else
        {
//            聊天界面就直接下传数据
            receiveResultMsg(message);
        }
    }

    private void noChatUI(final DataJieShou.RecordBean record) {
        final CusJumpChatData cusJumpChatData = new CusJumpChatData();
        cusJumpChatData.setFriendHeader(record.getHeadImg());
        cusJumpChatData.setFriendId(record.getFriendsId());
        cusJumpChatData.setFriendName(record.getFriendsName());

//        发送广播更新首页
        Intent intent = new Intent();
        intent.putExtra("message",record.getMessage());
        intent.putExtra("id",record.getFriendsId());
        intent.setAction("action.refreshMsgFragment");
        sendBroadcast(intent);
//在前台的时候处理接收到消息的事件
        if (SysRunUtils.isAppOnForeground(MyApplication.getAppContext()))
        {
//            TODO 弄成popwindow   弹框
            ToastUtil.show("收到来自"+record.getFriendsName()+"的一条新消息");

        }else {
            //APP在后台的时候处理接收到消息的事件
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        bitmap = Glide.with(MyApplication.getAppContext())
                                .load(record.getHeadImg())
                                .asBitmap() //必须
                                .centerCrop()
                                .into(500, 500)
                                .get();
                        NotificationUtil notificationUtils = new NotificationUtil(getApplicationContext());
                        notificationUtils.sendNotification(cusJumpChatData, record.getFriendsName(), record.getMessage(), bitmap, AppConfig.TYPE_CHAT);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    //是否开启右滑返回
    private void initSwipeBackFinish() {
        if (isSupportSwipeBack()) {
            initSwiBack();
        }
    }
    private void initview(){
        if (isTopBack())
        {
            try {
                findViewById(R.id.include_top_iv_back).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppManager.getAppManager().finishActivity();
                    }
                });
                isGone();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (isGones())
        {
            isGone();
        }
    }

    private void isGone() {
        View mtv;
        mtv = (View) AppManager.getAppManager().currentActivity().findViewById(R.id.include_top_margin10);
          // 设置状态栏高度
        int statusBarHeight = WindowBugDeal.getStatusBarHeight(this);
        //这里我用RelativeLayout布局为列，其他布局设置方法一样，只需改变布局名就行
        LinearLayout.LayoutParams layout=(LinearLayout.LayoutParams)mtv.getLayoutParams();
       //获得button控件的位置属性，需要注意的是，可以将button换成想变化位置的其它控件
//        layout.setMargins(0,-statusBarHeight,0,0);
        layout.height=statusBarHeight;
      //设置button的新位置属性,left，top，right，bottom
        mtv.setLayoutParams(layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mtv.setVisibility(View.VISIBLE);
        }else
        {
            mtv.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        initview();
    }

    protected boolean isSupportSwipeBack() {
        return true;
    }
    private void initSwiBack() {
        SlidingPaneLayout slidingPaneLayout =  new SlidingPaneLayout(this);
        //通过反射改变mOverhangSize的值为0，这个mOverhangSize值为菜单到右边屏幕的最短距离，默认是32dp，现在给它改成0
        try {
            //属性
            Field f_overHang = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
            f_overHang.setAccessible(true);
            f_overHang.set(slidingPaneLayout, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelOpened(View panel) {
                AppManager.getAppManager().finishActivity();
                overridePendingTransition(0, R.anim.slide_out_right);
            }

            @Override
            public void onPanelClosed(View panel) {

            }
        });
        slidingPaneLayout.setSliderFadeColor(getResources().getColor(android.R.color.transparent));

        View leftView = new View(this);
        leftView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        slidingPaneLayout.addView(leftView, 0);

        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundColor(getResources().getColor(android.R.color.white));
        decor.removeView(decorChild);
        decor.addView(slidingPaneLayout);
        slidingPaneLayout.addView(decorChild, 1);
    }
    protected boolean isTopBack() {
        return true;
    }
    protected boolean isChatActivity() {
        return false;
    }

    //接收到消息，传递给子类
    public void receiveResultMsg(String responseText) {

    }

}
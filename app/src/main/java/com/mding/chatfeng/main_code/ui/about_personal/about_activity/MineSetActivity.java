package com.mding.chatfeng.main_code.ui.about_personal.about_activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mding.chatfeng.R;
import com.mding.chatfeng.about_base.web_base.SplitWeb;
import com.mding.chatfeng.about_custom.about_cus_dialog.DialogExitUtils;
import com.mding.chatfeng.about_utils.HelpUtils;
import com.mding.chatfeng.about_utils.IntentUtils;
import com.mding.chatfeng.about_utils.VersionCheckUtils;
import com.mding.chatfeng.about_utils.about_realm.RealmGroupHelper;
import com.mding.chatfeng.about_utils.about_realm.new_home.RealmChatHelper;
import com.mding.chatfeng.about_utils.about_realm.new_home.RealmHomeHelper;
import com.mding.chatfeng.main_code.ui.about_personal.about_set.CountAndSafeActivity;
import com.mding.chatfeng.main_code.ui.about_personal.about_set.DiscoverSetActivity;
import com.mding.chatfeng.main_code.ui.about_personal.about_set.LaBlackActivity;
import com.mding.chatfeng.main_code.ui.about_personal.about_set.NewsRemindActivity;
import com.mding.chatfeng.main_code.ui.about_personal.about_set.ShareSetActivity;
import com.mding.chatfeng.main_code.ui.about_personal.about_set.YinSiActivity;
import com.mding.chatfeng.about_base.BaseActivity;
import com.mding.chatfeng.about_base.MyApplication;
import com.mding.chatfeng.main_code.about_login.LoginActivity;
import com.projects.zll.utilslibrarybyzll.about_dialog.DialogUtils;
import com.projects.zll.utilslibrarybyzll.about_key.AppAllKey;
import com.projects.zll.utilslibrarybyzll.aboutsystem.AppManager;
import com.projects.zll.utilslibrarybyzll.aboutsystem.DataCleanManager;
import com.projects.zll.utilslibrarybyzll.aboututils.ACache;
import com.projects.zll.utilslibrarybyzll.aboututils.NoDoubleClickUtils;
import com.projects.zll.utilslibrarybyzll.aboututils.SPUtils;
import com.projects.zll.utilslibrarybyzll.aboututils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MineSetActivity extends BaseActivity {

    @BindView(R.id.include_top_tv_tital)
    TextView includeTopTvTital;
    @BindView(R.id.include_top_lin_back)
    LinearLayout includeTopLin;
    @BindView(R.id.set_tv_cache)
    TextView setTvCache;
    @BindView(R.id.set_tv_versition)
    TextView setTvVersition;

    @Override
    public int getLayoutView() {
        return R.layout.activity_mine_set;
    }
    RealmHomeHelper realmHelper;
    RealmChatHelper realmChatHelper;
    RealmGroupHelper realmGroupHelper;
    String userPhone;
    @Override
    protected void initBaseView() {
        super.initBaseView();
        SplitWeb.IS_SET_ACTIVITY="1";
        realmHelper = new RealmHomeHelper(this);
        realmChatHelper = new RealmChatHelper(this);
        realmGroupHelper = new RealmGroupHelper(this);
        includeTopTvTital.setText("设置");
        includeTopLin.setBackgroundColor(getResources().getColor(R.color.app_theme));
        try {
            String totalCacheSize = DataCleanManager.getTotalCacheSize(MyApplication.getAppContext());
            setTvCache.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String versionName = HelpUtils.getLocalVersionName();
            setTvVersition.setText("v" + versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = getIntent();
        if (intent != null){
            userPhone = intent.getStringExtra("phone");
        }
    }
    @Override
    public void receiveResultMsg(String responseText) {
        super.receiveResultMsg(responseText);
        if(SplitWeb.IS_SET_ACTIVITY.equals("1")) {
            String method = HelpUtils.backMethod(responseText);
            switch (method) {
                case "appUpdate":
                    VersionCheckUtils.initUpdata(responseText, false);
                    break;
                case "kickUid":
                    Log.e("kickUid","----------------------kickUid-----------------");
                    break;
            }
        }
    }

    //    清理缓存
    private void cleanCaChe() {
//        ACache mCache = ACache.get(this);
//        String asString = mCache.getAsString(AppAllKey.TOKEN_KEY);
//        DataCleanManager.clearAllCache(MyApplication.getAppContext());
        try {
            String totalCacheSize = DataCleanManager.getTotalCacheSize(MyApplication.getAppContext());
            setTvCache.setText(totalCacheSize);
            ToastUtil.show("清理缓存成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        ACache mCache2 = ACache.get(this);
//        mCache2.put(AppAllKey.TOKEN_KEY, asString);
//        String asString2 = mCache.getAsString(AppAllKey.TOKEN_KEY);
    }


    @OnClick({R.id.set_lin_pingbi,R.id.set_lin_share, R.id.set_lin_count, R.id.set_lin_yinsi, R.id.set_lin_message,R.id.set_lin_discover,
            R.id.set_lin_clear_cache, R.id.set_lin_versition, R.id.set_lin_about_me, R.id.set_btn_esc})
    public void onViewClicked(View view) {
        switch (view.getId()) {

//            打开  名片分享  界面
            case R.id.set_lin_share:
                if (NoDoubleClickUtils.isDoubleClick())
                    IntentUtils.JumpTo(ShareSetActivity.class);
                break;

//                打开  帐号与安全  界面
            case R.id.set_lin_count:
                if (NoDoubleClickUtils.isDoubleClick())
                    IntentUtils.JumpTo(CountAndSafeActivity.class);
                break;

//                打开 隐私设置 界面
            case R.id.set_lin_yinsi:
                if (NoDoubleClickUtils.isDoubleClick())
                    IntentUtils.JumpTo(YinSiActivity.class);
                break;

//                打开消息提醒界面
            case R.id.set_lin_message:
                if (NoDoubleClickUtils.isDoubleClick())
                    IntentUtils.JumpTo(NewsRemindActivity.class);
                break;

//                打开 屏蔽设置  界面
            case R.id.set_lin_pingbi:
                if (NoDoubleClickUtils.isDoubleClick())
                    IntentUtils.JumpTo(LaBlackActivity.class);
                break;

//                打开 朋友圈设置 界面
            case R.id.set_lin_discover:
                if (NoDoubleClickUtils.isDoubleClick())
                    IntentUtils.JumpTo(DiscoverSetActivity.class);
                break;


//                清理缓存
            case R.id.set_lin_clear_cache:
                if (NoDoubleClickUtils.isDoubleClick()) {
                    if (!setTvCache.getText().toString().equals("0KB")) {
                        DialogUtils.showDialog("确认清理" + setTvCache.getText().toString() + "缓存？", new DialogUtils.OnClickSureListener() {
                            @Override
                            public void onClickSure() {
                                cleanCaChe();
                            }
                        });
                    } else {
                        ToastUtil.show("暂无缓存");
                    }
                }
                break;
//                检查更新
            case R.id.set_lin_versition:
                if (NoDoubleClickUtils.isDoubleClick()) {
                    //        版本更新
                    int localVersion = HelpUtils.getLocalVersion(this);
                    sendWeb(SplitWeb.appUpdate("" + localVersion));
                }
//                ToastUtil.show("已经是最新版本");
                break;

//                关于我们界面
            case R.id.set_lin_about_me:
                ToastUtil.show("这里是关于我们");
//                if (NoDoubleClickUtils.isDoubleClick())
//                    IntentUtils.JumpTo(ShareSetActivity.class);
                break;

//                退出帐号    回到登录界面
            case R.id.set_btn_esc:
                if (NoDoubleClickUtils.isDoubleClick()) {
//                    DialogExitUtils.setOnClickSureListener(new CusExitDialog.ClickSure(){
//                        @Override
//                        public void clickSure(String checkedId) {
//                            if (checkedId != null)
//                                checkId = checkedId;
//                        }
//                    });
                    DialogExitUtils.showDialog("仅退出帐号", "退出并删除帐号信息", new DialogExitUtils.OnClickSureListener() {
                        @Override
                        public void onClickSure(String checkingId) {
                            switch (checkingId){
                                case "1":
//                                    ToastUtil.show("1");
                                    SplitWeb.IS_SET_PERSON_HEAD=true;
                                    sendWeb(SplitWeb.kickUid());
                                    SplitWeb.USER_ID="";
                                    ACache.get(MineSetActivity.this).clear();
                                    SPUtils.put(MineSetActivity.this,AppAllKey.USER_ID_KEY,"");
                                    SPUtils.clear(MineSetActivity.this);
                                    AppManager.getAppManager().finishAllActivity();
//                                    Intent intent_recharge = new Intent(MineSetActivity.this, LoginActivity.class);
//                                    startActivity(intent_recharge);
//                                    Log.e("userPhone","-------------mineSet-----------------"+userPhone);
                                    IntentUtils.JumpToHaveOne(LoginActivity.class,"phone",userPhone);
                                    overridePendingTransition(0,0);

                                    if (MyApplication.getmConnectManager()!=null) {
                                        MyApplication.getmConnectManager().onDestroy();
                                        MyApplication.mConnectManager = null;
                                    }
//                                    MyApplication.getmConnectManager().onDestroyService();
                                    break;
                                case "2":
                                    SplitWeb.IS_SET_PERSON_HEAD=true;
//                                    ToastUtil.show("2");
                                    sendWeb(SplitWeb.kickUid());
                                    SplitWeb.USER_ID="";
                                    ACache.get(MineSetActivity.this).clear();
                                    SPUtils.clear(MineSetActivity.this);

                                    AppManager.getAppManager().finishAllActivity();
                                    Intent intent = new Intent(MineSetActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(0,0);

                                    realmHelper.deleteAll();
                                    realmChatHelper.deleteAll();
                                    realmGroupHelper.deleteAll();
                                    if (MyApplication.getmConnectManager()!=null) {
                                        MyApplication.getmConnectManager().onDestroy();
                                        MyApplication.mConnectManager = null;
                                    }
                                    break;
                            }

                        }
                    });
                }
                break;
        }
    }
}

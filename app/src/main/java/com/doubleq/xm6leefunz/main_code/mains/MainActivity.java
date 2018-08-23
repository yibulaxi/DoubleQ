package com.doubleq.xm6leefunz.main_code.mains;

import android.os.Handler;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.doubleq.xm6leefunz.R;
import com.doubleq.xm6leefunz.about_base.BaseActivity;
import com.doubleq.xm6leefunz.main_code.about_fragment.about_contacts.ContactsFragment;
import com.doubleq.xm6leefunz.main_code.about_fragment.about_discovery.DiscoveryFragment;
import com.doubleq.xm6leefunz.main_code.about_fragment.about_message.MsgFragment;
import com.doubleq.xm6leefunz.main_code.about_fragment.about_personal.PersonalFragment;
import com.projects.zll.utilslibrarybyzll.aboutsystem.AppManager;

public class MainActivity extends BaseActivity {

    FragmentTabHost mTabHost;
    TabWidget tabs;
//    View ac_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }
    @Override
    protected void initBaseView() {
        super.initBaseView();
        mTabHost= findViewById(android.R.id.tabhost);
        tabs= findViewById(android.R.id.tabs);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
//        去掉分割线
        mTabHost.getTabWidget().setDividerDrawable(android.R.color.transparent);
        mTabHost.addTab(mTabHost.newTabSpec("message").setIndicator(getIndecator(0)), MsgFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("contacts").setIndicator(getIndecator(1)), ContactsFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("discovery").setIndicator(getIndecator(2)), DiscoveryFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("personal").setIndicator(getIndecator(3)), PersonalFragment.class, null);
    }
    private String[] tvtab ={"消息","联系人","发现", "个人中心" };
    int[] imgs = {R.drawable.tab_ac_main_msg,R.drawable.tab_ac_main_contacts, R.drawable.tab_ac_main_discovery,R.drawable.tab_ac_main_pesonal};
    private View getIndecator(int index) {
        View view = getLayoutInflater().inflate(R.layout.layout_tabin, null);
        ImageView mImageView = view.findViewById(R.id.img_main_tab);
        TextView mTextView =  view.findViewById(R.id.tv_main_tab);
        try {
            mTextView.setText(tvtab[index]);
            mImageView.setImageResource(imgs[index]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    boolean isExit;
    Handler mHandler = new Handler();
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // 双击退出
            if (isExit)
            {
                AppManager.getAppManager().finishActivity();
            } else
            {
                isExit = true;

//                ToastUtil.show("再按一次退出应用");
                Toast.makeText(this, "再按一次离开我", Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(new Runnable()
                {
                    public void run()
                    {
                        isExit = false;
                    }
                }, 2000);
            }
            // return super.onKeyDown(keyCode, event);
            // 拦截系统按键
        }
        return true;
    }
}

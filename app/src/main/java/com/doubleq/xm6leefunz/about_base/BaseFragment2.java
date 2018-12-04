package com.doubleq.xm6leefunz.about_base;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.doubleq.xm6leefunz.R;
import com.doubleq.xm6leefunz.about_base.web_base.MessageEvent;
import com.doubleq.xm6leefunz.about_utils.HelpUtils;
import com.projects.zll.utilslibrarybyzll.about_key.AppAllKey;
import com.projects.zll.utilslibrarybyzll.aboutsystem.WindowBugDeal;
import com.projects.zll.utilslibrarybyzll.aboututils.ACache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * 公共基类
 *   zll
 * @Time 2017-11-01
 */
public abstract class BaseFragment2 extends Fragment  {

	private View mContentView;
	private Context mContext;
	public ACache mFragCache;
    String simpleName;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mContentView = inflater.inflate(setLayoutResourceID(),container,false);

		ButterKnife.bind(this,mContentView);
		mContext = getContext();
		init();
		setUpView();
		setUpData();
		return mContentView;
	}
	/**
	 * 此方法用于返回Fragment设置ContentView的布局文件资源ID
	 *
	 * @return 布局文件资源ID
	 */
	protected abstract int setLayoutResourceID();

	/**
	 * 一些View的相关操作
	 */
	protected abstract void setUpView();

	/**
	 * 一些Data的相关操作
	 */
	protected abstract void setUpData();

	/**
	 * 此方法用于初始化成员变量及获取Intent传递过来的数据
	 * 注意：这个方法中不能调用所有的View，因为View还没有被初始化，要使用View在initView方法中调用
	 */
	protected void init() {}

	public View getContentView() {
		return mContentView;
	}

	public Context getMContext() {
		return mContext;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFragCache = ACache.get(getActivity());
        simpleName = getClass().getSimpleName();
        
		EventBus.getDefault().register(this);
    }
	protected void sendWeb(String text) {
		MyApplication.getmConnectManager().sendText(text);
	}
	@Override
	public void onStart() {
		super.onStart();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				View viewById = getActivity().findViewById(R.id.main_view_top);
			// 设置状态栏高度
			int statusBarHeight = WindowBugDeal.getStatusBarHeight(getActivity());
			//这里我用RelativeLayout布局为列，其他布局设置方法一样，只需改变布局名就行
			LinearLayout.LayoutParams layout=(LinearLayout.LayoutParams)viewById.getLayoutParams();
			//获得button控件的位置属性，需要注意的是，可以将button换成想变化位置的其它控件
//        layout.setMargins(0,-statusBarHeight,0,0);
			layout.height=statusBarHeight;
			//设置button的新位置属性,left，top，right，bottom
			viewById.setLayoutParams(layout);
				if (viewById!=null)
				viewById.setVisibility(View.VISIBLE);
//				else
//					viewById.setVisibility(View.GONE);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	protected String GetsimpleName() {
        return getClass().getSimpleName().toString();
    }
	protected boolean FragUseCaChe() {
		return false;
	}
	protected void onFragmentHandleMessage(Message msg) {
	}
	//订阅方法，当接收到事件的时候，会调用该方法
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(MessageEvent messageEvent){
		String isSucess = HelpUtils.HttpIsSucess(messageEvent.getMessage());
//		Log.e("onEvent","fragment"+messageEvent.getMessage());
		if (isSucess.equals(AppAllKey.CODE_OK))
			receiveResultMsg(messageEvent.getMessage());
//        textView.setText(messageEvent.getMessage());
	}
	public void receiveResultMsg(String responseText) {

	}
//	@Override
//	public void onNetSuccess(String msg) {
//		Message message = new Message();
//		if (!StrUtils.isEmpty(msg)&&!msg.equals(AppAllKey.NO_RESULT)) {
//			message.what = AppAllKey.HANDLE_MSG_SUCCESS;
//			if (FragUseCaChe())
//				mFragCache.put(simpleName, msg);
//		}
//		else
//			message.what = AppAllKey.HANDLE_MSG_FAILED;
//		message.obj = msg;
//		mHandlers.sendMessage(message);
//	}
}

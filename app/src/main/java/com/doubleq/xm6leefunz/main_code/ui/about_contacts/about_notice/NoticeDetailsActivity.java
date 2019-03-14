package com.doubleq.xm6leefunz.main_code.ui.about_contacts.about_notice;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.doubleq.model.DataNews;
import com.doubleq.model.DataNoticeDetails;
import com.doubleq.xm6leefunz.R;
import com.doubleq.xm6leefunz.about_base.BaseActivity;
import com.doubleq.xm6leefunz.about_base.web_base.SplitWeb;
import com.doubleq.xm6leefunz.about_utils.HelpUtils;
import com.doubleq.xm6leefunz.about_utils.IntentUtils;
import com.doubleq.xm6leefunz.about_utils.about_realm.new_home.RealmChatHelper;
import com.doubleq.xm6leefunz.about_utils.about_realm.new_home.RealmHomeHelper;
import com.doubleq.xm6leefunz.main_code.ui.about_contacts.NoticeActivity;
import com.doubleq.xm6leefunz.main_code.ui.about_contacts.PersonData;
import com.doubleq.xm6leefunz.main_code.ui.about_contacts.about_contacts_adapter.FriendNoticeDetailsAdapter;
import com.doubleq.xm6leefunz.main_code.ui.about_contacts.about_search.DataSearch;
import com.doubleq.xm6leefunz.main_code.ui.about_personal.about_activity.ChangeInfoWindow;
import com.doubleq.xm6leefunz.main_code.ui.about_personal.about_activity.MyAccountActivity;
import com.projects.zll.utilslibrarybyzll.about_dialog.DialogUtils;
import com.projects.zll.utilslibrarybyzll.aboutsystem.AppManager;
import com.projects.zll.utilslibrarybyzll.aboututils.NoDoubleClickUtils;
import com.projects.zll.utilslibrarybyzll.aboututils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 位置：好友添加回馈界面
 */
public class NoticeDetailsActivity extends BaseActivity implements ChangeInfoWindow.OnAddContantClickListener {

    @BindView(R.id.include_top_tv_tital)
    TextView includeTopTvTital;
    @BindView(R.id.include_top_iv_more)
    ImageView includeTopIvMore;
    @BindView(R.id.inclu_tv_right)
    TextView incluTvRight;
    @BindView(R.id.notice_tv_name)
    TextView mTvName;
    @BindView(R.id.notice_iv_qrcode)
    ImageView noticeIvQrcode;
    @BindView(R.id.notice_iv_head)
    ImageView mIvHead;
    @BindView(R.id.notice_tv_num)
    TextView noticeTvNum;
    @BindView(R.id.notice_tv_sign)
    TextView noticeTvSign;
    @BindView(R.id.include_top_lin_background)
    LinearLayout includeTopLinBackground;
    @BindView(R.id.notice_detail_recyc)
    RecyclerView mRecyclerView;
    @BindView(R.id.notice_details_lin_main)
    LinearLayout mLinMain;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    RealmHomeHelper realmHelper;
    RealmChatHelper realmChatHelper;
    FriendNoticeDetailsAdapter friendNoticeDetailsAdapter = null;
    List<DataNoticeDetails> mList = new ArrayList<>();

    @Override
    protected void initBaseView() {
        super.initBaseView();
        realmHelper = new RealmHomeHelper(this);
        realmChatHelper = new RealmChatHelper(this);
        includeTopTvTital.setText("好友资料");
        includeTopLinBackground.setBackgroundColor(getResources().getColor(R.color.app_theme));

        mRecyclerView.setLayoutManager(new GridLayoutManager(NoticeDetailsActivity.this,1));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(NoticeDetailsActivity.this));
//        friendNoticeDetailsAdapter = new FriendNoticeDetailsAdapter(this, mList);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        mRecyclerView.setAdapter(friendNoticeDetailsAdapter);
        initAdapter(mList);
//        friendNoticeDetailsAdapter.notifyDataSetChanged();

        Intent intent = getIntent();
        item = (DataNews.RecordBean.ListInfoBean) intent.getSerializableExtra("id");
        sendWeb(SplitWeb.messageDetail(item.getId()));
    }

    private void initAdapter(List<DataNoticeDetails> mList) {
//        DataNoticeDetail dataNoticeDetail = new DataNoticeDetail();
//        dataNoticeDetail.setNoticeDetail("第一次请求添加好友！");
//        DataNoticeDetail dataNoticeDetail2 = new DataNoticeDetail();
//        dataNoticeDetail2.setNoticeDetail("第二次请求添加好友！");
//        DataNoticeDetail dataNoticeDetail3 = new DataNoticeDetail();
//        dataNoticeDetail3.setNoticeDetail("第三次请求添加好友！");
//        mList.add(dataNoticeDetail);
//        mList.add(dataNoticeDetail2);
//        mList.add(dataNoticeDetail3);


//        if (mList.size() > 3) {
//            mList.remove(0);
//            friendNoticeDetailsAdapter.notifyItemChanged(0);
//            DataNoticeDetail dataNoticeDetail4 = new DataNoticeDetail();
//            dataNoticeDetail4.setNoticeDetail(reply);
//            mList.add(dataNoticeDetail4);
////            friendNoticeDetailsAdapter.notifyDataSetChanged();
//        }
        friendNoticeDetailsAdapter = new FriendNoticeDetailsAdapter(NoticeDetailsActivity.this,mList );
//        增加分割线
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(friendNoticeDetailsAdapter);
        friendNoticeDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    public void receiveResultMsg(String responseText) {
        super.receiveResultMsg(responseText);
        String method = HelpUtils.backMethod(responseText);
        switch (method) {
//          拒绝好友请求
            case "refuseFriend":
                DialogUtils.isShow();
//                DialogUtils.showDialogOne("拒绝请求成功", new DialogUtils.OnClickSureListener() {
//                    @Override
//                    public void onClickSure() {
                ToastUtil.show("拒绝请求成功");
                AppManager.getAppManager().finishActivity();
//                    }
//                });
                break;
//                同意
            case "agreeFriend":
//                final CusHomeRealmData cusJumpChatData = new CusHomeRealmData();
//                cusJumpChatData.setHeadImg(item.getHeadImg());
//                cusJumpChatData.setFriendId(item.getSendUserId());
//                cusJumpChatData.setNickName(item.getNickName());
//                cusJumpChatData.setMsg("新添加的好友");
//                cusJumpChatData.setTime(TimeUtil.getTime());
//                cusJumpChatData.setNum(0);
//                realmHelper.addRealmMsg(cusJumpChatData);
//                dealAgreeFriend();
                DialogUtils.isShow();
//                DialogUtils.showDialogOne("同意好友请求成功", new DialogUtils.OnClickSureListener() {
//                    @Override
//                    public void onClickSure() {
//                        AppManager.getAppManager().finishActivity(NoticeDetailsActivity.this);
//                        AppManager.getAppManager().finishActivity(NoticeActivity.class);
//                    }
//                });
                ToastUtil.show("同意好友请求成功");
                AppManager.getAppManager().finishActivity(NoticeDetailsActivity.this);
                AppManager.getAppManager().finishActivity(NoticeActivity.class);

                break;
//                消息通知详情页面接口
            case "messageDetail":
                DataNoticeDetails dataNoticeDetails = JSON.parseObject(responseText, DataNoticeDetails.class);
                DataNoticeDetails.RecordBean record = dataNoticeDetails.getRecord();
                Log.e("mgsdetail","----------------------------前------------------------------------");
                if (record != null) {
                    Log.e("mgsdetail","----------------------------record != null------------------------------------");
                    DataNoticeDetails.RecordBean.UserDetailInfoBean userDetailInfo = record.getUserDetailInfo();

                    if (userDetailInfo != null) {
                        initData(userDetailInfo);
                    }
                }
                break;
        }
    }

    String friendsName;
    private void initData(DataNoticeDetails.RecordBean.UserDetailInfoBean userDetailInfo) {
        Glide.with(this).load(userDetailInfo.getHeadImg())
                .bitmapTransform(new CropCircleTransformation(this))
                .error(R.drawable.mine_head)
                .into(mIvHead);
        mTvName.setText(userDetailInfo.getNickName());
        friendsName = userDetailInfo.getNickName();
//        noticeTvRemark.setText("备注:" + userDetailInfo.getRemark());

        dataSearch = new DataSearch();
        dataSearch.setName(userDetailInfo.getNickName());
        dataSearch.setQrcode(userDetailInfo.getQrcode());
        dataSearch.setHeadImg(userDetailInfo.getHeadImg());

        List<DataNoticeDetails.RecordBean.UserDetailInfoBean.RemarkBean> remarkBeanList = userDetailInfo.getRemark();
//        位置0是最新的消息
        String msg = remarkBeanList.get(0).getMessage();
        if (msg != null){
            if (remarkBeanList.size() == 3) {  //备注信息等于三条时
                mList.remove(0);
                friendNoticeDetailsAdapter.notifyItemChanged(0);
                DataNoticeDetails dataNoticeDetails = new DataNoticeDetails();
                DataNoticeDetails.RecordBean recordBean = dataNoticeDetails.getRecord();
                if (recordBean != null){
                    DataNoticeDetails.RecordBean.UserDetailInfoBean userDetailInfoBean = recordBean.getUserDetailInfo();
                    if (userDetailInfoBean != null){
                        List<DataNoticeDetails.RecordBean.UserDetailInfoBean.RemarkBean> remarkBean = userDetailInfoBean.getRemark();
                        remarkBean.get(2).setMessage(friendsName + "：" + msg);
                        mList.add(dataNoticeDetails);
                        friendNoticeDetailsAdapter.notifyDataSetChanged();
                    }
                }
            }
            else {  //备注信息小于三条时
                DataNoticeDetails dataNoticeDetails = new DataNoticeDetails();
                DataNoticeDetails.RecordBean recordBean = dataNoticeDetails.getRecord();
                if (recordBean != null){
                    DataNoticeDetails.RecordBean.UserDetailInfoBean userDetailInfoBean = recordBean.getUserDetailInfo();
                    if (userDetailInfoBean != null){
                        List<DataNoticeDetails.RecordBean.UserDetailInfoBean.RemarkBean> remarkBean = userDetailInfoBean.getRemark();
                        remarkBean.get(mList.size()).setMessage(friendsName + "：" + msg);
                        mList.add(dataNoticeDetails);
                        friendNoticeDetailsAdapter.notifyDataSetChanged();
                    }
                }
            }
            friendNoticeDetailsAdapter = new FriendNoticeDetailsAdapter(NoticeDetailsActivity.this,mList);
//        增加分割线
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            mRecyclerView.setAdapter(friendNoticeDetailsAdapter);
            friendNoticeDetailsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_notice_details;
    }

    DataNews.RecordBean.ListInfoBean item;
    DataSearch dataSearch = null;
    @OnClick({R.id.notice_iv_qrcode, R.id.notice_tv_jujue, R.id.notice_tv_ok, R.id.notice_tv_reply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.notice_iv_qrcode:
                if (NoDoubleClickUtils.isDoubleClick()) {
                    if (dataSearch != null) {
                        PersonData personData = new PersonData();
                        personData.setHeadImg(dataSearch.getHeadImg());
                        personData.setName(dataSearch.getName());
                        personData.setScanTital("扫一扫,添加" + dataSearch.getName() + "为好友");
                        personData.setTital("好友二维码");

//                        if (dataSearch.getId() != null) {
//                            String string = type + "_xm6leefun_" + dataSearch.getId();
//                            Log.e("qrcode", "----------FriendDataAddActivity--------------" + string);
//                            personData.setQrCode(string);
//                        }
                        personData.setQrCode(dataSearch.getQrcode());
                        IntentUtils.JumpToHaveObj(MyAccountActivity.class, MyAccountActivity.TITAL_NAME, personData);
                    }
                }
                break;
            case R.id.notice_tv_jujue:
                sendWeb(SplitWeb.refuseFriend(item.getId(), "1"));
                break;
            case R.id.notice_tv_ok:
                sendWeb(SplitWeb.agreeFriend(item.getId()));
                break;
            case R.id.notice_tv_reply:
                doReply();
                break;
        }
    }

    private void doReply() {
        ChangeInfoWindow changeInfoWindow = new ChangeInfoWindow(NoticeDetailsActivity.this, "回复", "");
        changeInfoWindow.showAtLocation(mLinMain, Gravity.CENTER, 0, 0);
        changeInfoWindow.setOnAddpopClickListener(this);
    }

    @Override
    public void onSure(String content) {
        if (content != null){
            if (mList.size() == 3) {
                mList.remove(0);
                friendNoticeDetailsAdapter.notifyItemChanged(0);
                DataNoticeDetails dataNoticeDetails = new DataNoticeDetails();
                DataNoticeDetails.RecordBean recordBean = dataNoticeDetails.getRecord();
                if (recordBean != null){
                    DataNoticeDetails.RecordBean.UserDetailInfoBean userDetailInfoBean = recordBean.getUserDetailInfo();
                    if (userDetailInfoBean != null){
                        List<DataNoticeDetails.RecordBean.UserDetailInfoBean.RemarkBean> remarkBean = userDetailInfoBean.getRemark();
                        remarkBean.get(2).setMessage("我：" + content);
                        mList.add(dataNoticeDetails);
                        friendNoticeDetailsAdapter.notifyDataSetChanged();
                    }
                }
            }
            else {
                DataNoticeDetails dataNoticeDetails = new DataNoticeDetails();
                DataNoticeDetails.RecordBean recordBean = dataNoticeDetails.getRecord();
                if (recordBean != null){
                    DataNoticeDetails.RecordBean.UserDetailInfoBean userDetailInfoBean = recordBean.getUserDetailInfo();
                    if (userDetailInfoBean != null){
                        List<DataNoticeDetails.RecordBean.UserDetailInfoBean.RemarkBean> remarkBean = userDetailInfoBean.getRemark();
                        remarkBean.get(mList.size()).setMessage("我：" + content);
                        mList.add(dataNoticeDetails);
                        friendNoticeDetailsAdapter.notifyDataSetChanged();
                    }
                }
            }
            friendNoticeDetailsAdapter = new FriendNoticeDetailsAdapter(NoticeDetailsActivity.this,mList);
//        增加分割线
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            mRecyclerView.setAdapter(friendNoticeDetailsAdapter);
            friendNoticeDetailsAdapter.notifyDataSetChanged();
        }
        else {
            ToastUtil.show("回复内容不能为空！");
        }

    }

    @Override
    public void onCancle() {

    }

}

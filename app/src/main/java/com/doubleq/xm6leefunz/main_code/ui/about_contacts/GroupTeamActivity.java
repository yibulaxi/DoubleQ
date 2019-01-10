package com.doubleq.xm6leefunz.main_code.ui.about_contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.doubleq.model.DataAddQunDetails;
import com.doubleq.model.DataBlack;
import com.doubleq.model.DataGroupMember;
import com.doubleq.model.DataLinkManList;
import com.doubleq.xm6leefunz.R;
import com.doubleq.xm6leefunz.about_base.AppConfig;
import com.doubleq.xm6leefunz.about_base.BaseActivity;
import com.doubleq.xm6leefunz.about_base.web_base.SplitWeb;
import com.doubleq.xm6leefunz.about_chat.chat_group.FriendDataGroupMemberActivity;
import com.doubleq.xm6leefunz.about_chat.chat_group.sub_group.InvitationGroupChatActivity;
import com.doubleq.xm6leefunz.about_chat.chat_group.sub_group.about_intent_data.IntentDataInvitation;
import com.doubleq.xm6leefunz.about_utils.HelpUtils;
import com.doubleq.xm6leefunz.about_utils.IntentUtils;
import com.doubleq.xm6leefunz.about_utils.NetWorkUtlis;
import com.doubleq.xm6leefunz.main_code.ui.about_contacts.about_contacts_adapter.GroupMemberQunzhuAdapter;
import com.doubleq.xm6leefunz.main_code.ui.about_contacts.about_contacts_adapter.GroupTeamAdapter;
import com.doubleq.xm6leefunz.main_code.ui.about_contacts.about_contacts_adapter.GroupTeamMemberAdapter;
import com.doubleq.xm6leefunz.main_code.ui.about_contacts.about_contacts_adapter.SeachAdapter;
import com.doubleq.xm6leefunz.main_code.ui.about_contacts.about_custom.Allcity;
import com.doubleq.xm6leefunz.main_code.ui.about_contacts.about_custom.Cities;
import com.doubleq.xm6leefunz.main_code.ui.about_contacts.about_custom.LetterBar;
import com.doubleq.xm6leefunz.main_code.ui.about_contacts.about_notice.NoticeAdapter;
import com.doubleq.xm6leefunz.main_code.ui.about_personal.about_activity.ChangeInfoActivity;
import com.projects.zll.utilslibrarybyzll.aboututils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//位置：群聊成员
public class GroupTeamActivity extends BaseActivity {
    @BindView(R.id.include_top_tv_tital)
    TextView includeTopTvTital;
    @BindView(R.id.inclu_tv_right)
    TextView includeTopTvRight;
    @BindView(R.id.include_top_lin_back)
    LinearLayout includeTopLin;
    @BindView(R.id.tv_abc)
    TextView mTvAbc;

    //    @BindView(R.id.group_team_recyc)
//    RecyclerView mRecyclerView;
    @BindView(R.id.group_team_expan)
    ExpandableListView mExpanList;
    @BindView(R.id.group_team_letter)
    LetterBar mLetterBar;
    //    @BindView(R.id.seach_ed_input)
//    EditText seachEdInput;
//    @BindView(R.id.seach_iv_close)
//    ImageView seachIvClose;
//    @BindView(R.id.seach_iv_find)
//    ImageView seachIvFind;
//    @BindView(R.id.seach_recyc)
//    RecyclerView seachRecyc;
//    @BindView(R.id.seach_lin_list)
//    LinearLayout seachLinList;
//    @BindView(R.id.group_lin_list)
//    LinearLayout groupLinList;

    //    未搜索到东西
//    @BindView(R.id.seach_tv_noSearch)
//    TextView seachTvNoSearch;
//    @BindView(R.id.seach_lin_noSearch)
//    RelativeLayout seachLinNoSearch;

    public static final String FRIENG_ID_KEY = "friendId";
    private Runnable runnable;
    String mShare = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    LinearLayoutManager linearLayoutManager;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_group_team;
    }

    @Override
    protected void initBaseView() {
        super.initBaseView();
        includeTopTvTital.setText("群聊成员");
        includeTopLin.setBackgroundColor(getResources().getColor(R.color.app_theme));
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setNestedScrollingEnabled(false);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));

//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setNestedScrollingEnabled(false);
//        linearLayoutManager = new LinearLayoutManager(GroupTeamActivity.this);
//        mRecyclerView.setLayoutManager(linearLayoutManager);
//        seachRecyc.setLayoutManager( new LinearLayoutManager(GroupTeamActivity.this));
//        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(GroupTeamActivity.this));
        initGroup();
        initIntent();
    }
    public  static  String GROUP_ID="groupId";
    String groupId;
    private void initIntent() {
        Intent intent = getIntent();
        if (intent!=null) {
            groupId = intent.getStringExtra(GROUP_ID);
            if (groupId != null)
                sendWeb(SplitWeb.getGroupMemberList(groupId));
        }


    }

    private void initGroup() {
//        initUI();
        initABC2();

    }
    //    private ArrayList<Allcity> allCityList = new ArrayList<Allcity>();
    SeachAdapter mSeachAdapter;
    private ArrayList<Allcity> searchCityList = new ArrayList<Allcity>();

    private ArrayList<DataGroupMember.RecordBean.MemberListBean> allCusList = new ArrayList<>();


    List<String> ABCList = new ArrayList<>();

    public void initABC2() {
        ABCList.clear();
//        ABCList.add("");
//        ABCList.add("⇧");
        for (char i = 'A'; i <= 'Z'; i++) {
            ABCList.add(i + "");
        }
        runnable = new Runnable() {
            public void run() {
                mTvAbc.setVisibility(View.INVISIBLE);
            }
        };
        mLetterBar.setonTouchLetterListener(new LetterBar.onTouchLetterListener() {
            @Override
            public void onTouuchDown(String letter) {
                mTvAbc.removeCallbacks(runnable);
                mTvAbc.setVisibility(View.VISIBLE);
                mTvAbc.setText(letter);
                if (letter.equals("⇧"))
                {
                    mExpanList.setSelection(0);
                    return;
                }
                for (int i = 0; i < allCusList.size(); i++) {
//                for (int i = 1; i < allCusList.size(); i++) {
                    //获取所有城市的首字母
                    String firstLetter = getFirstABC
                            (allCusList.get(i).getGroupName());
//                        String firstLetter = getFirstABC
//                                (allCusList.get(i).getGroupName());
                    if (firstLetter.equals("~"))
                    {
                        firstLetter="#";
                    }
                    if (letter.equals(firstLetter)) {
                        mExpanList.setSelectedGroup(i);
                        break;
                    }
                }
            }

            @Override
            public void onTouchUp() {
                mTvAbc.postDelayed(runnable, 1000);
            }
        });
    }

    public String getFirstABC(String pinyin) {
        String upperCase = pinyin.substring(0, 1).toUpperCase();
        return upperCase;
    }

    @Override
    public void receiveResultMsg(String responseText) {
        super.receiveResultMsg(responseText);
        String method = HelpUtils.backMethod(responseText);
        switch (method) {
            case "getGroupMemberList":
                DataGroupMember dataGroupMember = JSON.parseObject(responseText, DataGroupMember.class);
                DataGroupMember.RecordBean record = dataGroupMember.getRecord();
                List<DataGroupMember.RecordBean.MemberListBean> memberList = record.getMemberList();
                allCusList.clear();
                allCusList.addAll(memberList);
//                String groupName = record.getMemberList().get(0).getGroupName();
//                List<DataGroupMember.RecordBean.MemberListBean.GroupListBean> groupList = record.getMemberList().get(0).getGroupList();
//                initABCByGroup();
                initGroupTeamAdapter();
                break;
        }
    }

    private void initGroupTeamAdapter() {
        final GroupTeamMemberAdapter groupTeamAdapter = new GroupTeamMemberAdapter(GroupTeamActivity.this, allCusList);
        mExpanList.setAdapter(groupTeamAdapter);

        groupTeamAdapter.notifyDataSetChanged();
        for (int i=0; i<allCusList.size(); i++)
        {
            mExpanList.expandGroup(i);
        }
        mExpanList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                //设置点击不关闭（不收回）
                v.setClickable(true);
                return true;
            }
        });
        mExpanList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                DataLinkManList.RecordBean.FriendListBean.GroupListBean groupListBean = mFriendList.get(groupPosition).getGroupList().get(childPosition);

                String memberId = allCusList.get(groupPosition).getGroupList().get(childPosition).getMemberId();
//                DataAddQunDetails.RecordBean.GroupDetailInfoBean.GroupUserInfoBean item = (DataAddQunDetails.RecordBean.GroupDetailInfoBean.GroupUserInfoBean) adapter.getItem(position);
                DataGroupMember.RecordBean.MemberListBean.GroupListBean item = (DataGroupMember.RecordBean.MemberListBean.GroupListBean) groupTeamAdapter.getChild(groupPosition,childPosition);
                //好友关系 1是未添加 2是已添加
                //群成员关系  1是未添加 2是已添加 3是自己
                if (item != null) {
                    Log.e("Relation", "-----------------------" + item.getIsRelation());
                    switch (item.getIsRelation()) {
                        case 1:
//                            跳转陌生人显示界面
                            IntentUtils.JumpToHaveTwo(FriendDataGroupMemberActivity.class, FriendDataGroupMemberActivity.FRIENG_ID_KEY, memberId, FriendDataGroupMemberActivity.GROUP_ID_KEY, groupId);
                            break;
                        case 2:
                            IntentUtils.JumpToHaveOne(FriendDataActivity.class, "id", memberId);
                            break;
                        case 3:
                            // 自己，跳转个人资料界面
                            IntentUtils.JumpTo(ChangeInfoActivity.class);
                            break;
                    }
                }
//                IntentUtils.JumpToHaveOne(FriendDataActivity.class,"id",memberId);
                return false;
            }
        });

    }
    public int positions;

}

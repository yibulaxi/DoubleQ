package com.doubleq.xm6leefunz.main_code.ui.about_personal.about_set;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doubleq.xm6leefunz.R;
import com.doubleq.xm6leefunz.about_base.BaseActivity;
import com.doubleq.xm6leefunz.about_utils.IntentUtils;
import com.projects.zll.utilslibrarybyzll.aboututils.NoDoubleClickUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class CountAndSafeActivity extends BaseActivity {

    @BindView(R.id.include_top_iv_back)
    ImageView includeTopIvBack;
    @BindView(R.id.include_top_tv_tital)
    TextView includeTopTvTital;
    @BindView(R.id.count_lin_changePWD)
    LinearLayout countLinChangePWD;
    @BindView(R.id.count_lin_changeBind)
    LinearLayout countLinChangeBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initBaseView() {
        super.initBaseView();
        includeTopTvTital.setText("帐号与安全");
        includeTopIvBack.setVisibility(View.VISIBLE);
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_count_and_safe;
    }

    @OnClick({R.id.count_lin_changePWD,R.id.count_lin_changeBind})
    public void onViewClicked(View view) {
        switch (view.getId()) {

//            打开  更换密码  界面
            case R.id.count_lin_changePWD:
                if (NoDoubleClickUtils.isDoubleClick())
                    IntentUtils.JumpTo(ChangePwdActivity.class);
                break;
            case R.id.count_lin_changeBind:
                if (NoDoubleClickUtils.isDoubleClick())
                    IntentUtils.JumpTo(ChangeBindOldActivity.class);
                break;
        }
    }
}

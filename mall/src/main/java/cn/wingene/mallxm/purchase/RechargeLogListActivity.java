package cn.wingene.mallxm.purchase;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import junze.java.util.StringUtil;

import junze.widget.Tile;

import junze.androidxf.core.Agent;

import cn.wingene.mall.R;
import cn.wingene.mallxf.cacheData.UserData;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.purchase.fragment.AmountLogListFragment;
import cn.wingene.mallxm.purchase.fragment.IntegralLogListFragment;

/**
 * Created by Wingene on 2017/9/5.
 */

public class RechargeLogListActivity extends MyBaseActivity {
    public static Major major = new Major(RechargeLogListActivity.class);

    private Tile tlBack;
    private TextView tvTitle;
    private FrameLayout lvContent;

    protected void initComponent() {
        tlBack = (Tile) super.findViewById(R.id.tl_back);
        tvTitle = (TextView) super.findViewById(R.id.tv_title);
        lvContent = (FrameLayout) super.findViewById(R.id.lvContent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_log_list);
        tlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initFragmentParentId(R.id.lvContent);
        if (getMajor() == 0) {
            turntoFragment(AmountLogListFragment.class, null);
        } else {
            turntoFragment(IntegralLogListFragment.class, null);
        }
    }

    public static class Major extends Agent.Major {
        public static final int MAJOR_AMOUNT = 0;
        public static final int MAJOR_INTEGRAL = 1;

        public Major(Class<? extends RechargeLogListActivity> clazz) {
            super(clazz);
        }

        public void startForMajor(Context src, int major) {
            if (!StringUtil.isValid(UserData.getverifiCode())) {
                JumpHelper.startLoginActivity(src);
                return;
            }
            builder(src).setMajor(major).startActivity();
        }

    }


}

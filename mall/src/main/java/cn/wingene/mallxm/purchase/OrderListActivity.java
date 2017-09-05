package cn.wingene.mallxm.purchase;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import junze.java.util.StringUtil;

import junze.androidxf.core.Agent;

import cn.wingene.mall.R;
import cn.wingene.mallxf.cacheData.UserData;
import cn.wingene.mallxf.model.IndexModel;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.purchase.adapter.OrderFragmentPagerAdapter;
import cn.wingene.mallxm.purchase.fragment.OrderListFragment;

/**
 * Created by Wingene on 2017/8/27.
 */

public class OrderListActivity extends MyBaseActivity {
    public static Major major = new Major(OrderListActivity.class);
    private ImageView ivBack;
    private TabLayout tlContent;
    private ViewPager vpContent;

    protected void initComponent() {
        ivBack = (ImageView) super.findViewById(R.id.iv_back);
        tlContent = (TabLayout) super.findViewById(R.id.tl_content);
        vpContent = (ViewPager) super.findViewById(R.id.vp_content);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initComponent();
        int state = major.parseParams(this);
        initViews(state);
    }

    private void initViews(int state) {
        List<IndexModel> fragmentList = new ArrayList<>();
        fragmentList.add(new IndexModel("全部", OrderListFragment.newInstance(-1)));
        fragmentList.add(new IndexModel("待付款", OrderListFragment.newInstance(0)));
        fragmentList.add(new IndexModel("待发货", OrderListFragment.newInstance(1)));
        fragmentList.add(new IndexModel("已发货", OrderListFragment.newInstance(3)));
        fragmentList.add(new IndexModel("已完成", OrderListFragment.newInstance(4)));
        fragmentList.add(new IndexModel("已评价", OrderListFragment.newInstance(5)));

        OrderFragmentPagerAdapter mMailFragmentPagerAdapter = new OrderFragmentPagerAdapter
                (getSupportFragmentManager(), fragmentList);
        vpContent.setAdapter(mMailFragmentPagerAdapter);
        tlContent.setupWithViewPager(vpContent, true);//同步
        List<Integer> list = new ArrayList<>();
        list.add(-1);
        list.add(0);
        list.add(1);
        list.add(3);
        list.add(4);
        list.add(5);
        int index = list.indexOf(state);
        index = index != -1 ? index : 0;
        vpContent.setCurrentItem(index);
    }

    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.iv_back:
            onBackPressed();
            break;
        }
    }
    public static class Major extends Agent.Major{
        public Major(Class<? extends OrderListActivity> clazz) {
            super(clazz);
        }

        public void startForOrderState(Context context,int state){
            if (!StringUtil.isValid(UserData.getverifiCode())) {
                JumpHelper.startLoginActivity(context);
                return;
            }
            buildParams(context,state).startActivity();
        }

        public int parseParams(Activity target){
            return parseParam(target,Integer.class);
        }
    }
}

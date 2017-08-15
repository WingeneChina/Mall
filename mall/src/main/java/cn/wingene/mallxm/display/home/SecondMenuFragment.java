package cn.wingene.mallxm.display.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxf.adapter.MailFragmentPagerAdapter;
import cn.wingene.mallxf.model.IndexModel;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxm.display.home.secondMenu.SelectedFragment;

/**
 * Created by wangcq on 2017/8/7.
 * 专题
 */

public class SecondMenuFragment extends MyBaseFragment {

    private View mRootView;
    private ViewPager specialPagerV;
    private TabLayout selectTabLayout;
    private MailFragmentPagerAdapter mMailFragmentPagerAdapter;


    public static SecondMenuFragment newInstance(Bundle args) {
        SecondMenuFragment secondMenuFragment = new SecondMenuFragment();
        secondMenuFragment.setArguments(args);

        return secondMenuFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_secondmenu_layout, container, false);
//        mRootView = view;
//        initViews(view);
//        initViewPagerData();
        return view;
    }


    private void initViews(View root) {
//        specialPagerV = (ViewPager) root.findViewById(R.id.specialPagerV);
//        selectTabLayout = (TabLayout) root.findViewById(R.id.selectTabLayout);

    }

    private void initViewPagerData() {
        try {

            List<IndexModel> indexModelList = new ArrayList<>();
            indexModelList.add(new IndexModel("精选", SelectedFragment.newInstance(null)));
            indexModelList.add(new IndexModel("家居", SelectedFragment.newInstance(null)));
            indexModelList.add(new IndexModel("家电", SelectedFragment.newInstance(null)));
            indexModelList.add(new IndexModel("汽车", SelectedFragment.newInstance(null)));

            Log.e(this.getClass().getName(), "indexList.size() = " + indexModelList.size());
            mMailFragmentPagerAdapter = new MailFragmentPagerAdapter(getChildFragmentManager(), indexModelList);
            specialPagerV.setAdapter(mMailFragmentPagerAdapter);

            selectTabLayout.setupWithViewPager(specialPagerV, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(this.getClass().getName(), "hidden = " + hidden);
    }
}

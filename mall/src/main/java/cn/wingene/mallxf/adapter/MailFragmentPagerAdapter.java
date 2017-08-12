package cn.wingene.mallxf.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

import cn.wingene.mallxf.model.IndexModel;

/**
 * Created by wangcq on 2017/4/10.
 * fragment pagerAdapter
 */

public class MailFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private List<IndexModel> mFragmentList;

    public MailFragmentPagerAdapter(FragmentManager fm, List<IndexModel> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position).mFragment;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentList.get(position).title;
    }
}

package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;

import cn.wingene.mallxf.ui.MyBaseFragment;

/**
 * Created by wangcq on 2017/8/8.
 * 电竞
 */

public class GamingFragment extends MyBaseFragment {

    public static GamingFragment newInstance(Bundle bundle) {
        GamingFragment gamingFragment = new GamingFragment();
        gamingFragment.setArguments(bundle);

        return gamingFragment;
    }
}

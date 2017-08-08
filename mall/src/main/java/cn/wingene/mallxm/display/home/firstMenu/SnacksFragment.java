package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;

import cn.wingene.mallxf.ui.MyBaseFragment;

/**
 * Created by wangcq on 2017/8/8.
 * 零食
 */

public class SnacksFragment extends MyBaseFragment {
    public static SnacksFragment newInstance(Bundle bundle) {
        SnacksFragment snacksFragment = new SnacksFragment();
        snacksFragment.setArguments(bundle);

        return snacksFragment;
    }
}

package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;

import cn.wingene.mallxf.ui.MyBaseFragment;

/**
 * Created by wangcq on 2017/8/8.
 * 居家
 */

public class IndoorFragment extends MyBaseFragment {
    public static IndoorFragment newInstance(Bundle bundle) {
        IndoorFragment indoorFragment = new IndoorFragment();
        indoorFragment.setArguments(bundle);

        return indoorFragment;
    }
}

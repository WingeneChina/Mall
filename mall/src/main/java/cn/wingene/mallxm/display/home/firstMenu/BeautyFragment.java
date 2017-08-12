package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;

import cn.wingene.mallxf.ui.MyBaseFragment;

/**
 * Created by wangcq on 2017/8/8.
 * 美妆
 */

public class BeautyFragment extends MyBaseFragment {

    public static BeautyFragment newInstance(Bundle bundle) {
        BeautyFragment beautyFragment = new BeautyFragment();
        beautyFragment.setArguments(bundle);

        return beautyFragment;
    }
}

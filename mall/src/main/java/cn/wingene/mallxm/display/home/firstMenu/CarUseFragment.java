package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;

import cn.wingene.mallxf.ui.MyBaseFragment;

/**
 * Created by wangcq on 2017/8/8.
 * 汽车用品
 */

public class CarUseFragment extends MyBaseFragment {

    public static CarUseFragment newInstance(Bundle bundle) {
        CarUseFragment carUseFragment = new CarUseFragment();
        carUseFragment.setArguments(bundle);

        return carUseFragment;
    }
}

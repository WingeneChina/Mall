package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;

import cn.wingene.mallxf.ui.MyBaseFragment;

/**
 * Created by wangcq on 2017/8/8.
 * 新品
 */

public class NewProductFragment extends MyBaseFragment {

    public static NewProductFragment newInstance(Bundle bundle) {
        NewProductFragment newProductFragment = new NewProductFragment();
        newProductFragment.setArguments(bundle);

        return newProductFragment;
    }
}

package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;

import cn.wingene.mallxf.ui.MyBaseFragment;

/**
 * Created by wangcq on 2017/8/8.
 * 服饰
 */

public class ClothesFragment extends MyBaseFragment {
    public static ClothesFragment newInstance(Bundle bundle) {
        ClothesFragment clothesFragment = new ClothesFragment();
        clothesFragment.setArguments(bundle);

        return clothesFragment;
    }
}

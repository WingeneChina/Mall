package cn.wingene.mallxm.display.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.wingene.mall.R;
import cn.wingene.mallxf.ui.MyBaseFragment;

/**
 * Created by wangcq on 2017/8/7.
 */

public class FiveMenuFragment extends MyBaseFragment {

    public static FiveMenuFragment newInstance(Bundle args) {
        FiveMenuFragment fiveMenuFragment = new FiveMenuFragment();
        fiveMenuFragment.setArguments(args);

        return fiveMenuFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_five_menu_layout, container, false);

        return view;
    }
}

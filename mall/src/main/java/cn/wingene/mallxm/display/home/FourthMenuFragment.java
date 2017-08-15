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

public class FourthMenuFragment extends MyBaseFragment {

    public static FourthMenuFragment newInstance(Bundle args) {
        FourthMenuFragment fourthMenuFragment = new FourthMenuFragment();
        fourthMenuFragment.setArguments(args);

        return fourthMenuFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four_menu_layout, container, false);

        return view;
    }
}

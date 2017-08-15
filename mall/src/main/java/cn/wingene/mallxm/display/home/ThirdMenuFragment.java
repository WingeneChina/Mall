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

public class ThirdMenuFragment extends MyBaseFragment {

    public static ThirdMenuFragment newInstance(Bundle args) {
        ThirdMenuFragment thirdMenuFragment = new ThirdMenuFragment();
        thirdMenuFragment.setArguments(args);

        return thirdMenuFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three_menu_layout, container, false);
        return view;
    }
}

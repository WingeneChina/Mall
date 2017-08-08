package cn.wingene.mallxm.display.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.wingene.mallxf.ui.MyBaseFragment;

/**
 * Created by wangcq on 2017/8/7.
 */

public class SecondMenuFragment extends MyBaseFragment {

    public static SecondMenuFragment newInstance(Bundle args) {
        SecondMenuFragment secondMenuFragment = new SecondMenuFragment();
        secondMenuFragment.setArguments(args);

        return secondMenuFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.wingene.mall.R;
import cn.wingene.mallxf.ui.MyBaseFragment;

/**
 * Created by wangcq on 2017/8/8.
 * 电竞
 */

public class GamingFragment extends MyBaseFragment {

    public static GamingFragment newInstance(Bundle bundle) {
        GamingFragment gamingFragment = new GamingFragment();
        gamingFragment.setArguments(bundle);

        return gamingFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_layout, container, false);
        return view;
    }
}

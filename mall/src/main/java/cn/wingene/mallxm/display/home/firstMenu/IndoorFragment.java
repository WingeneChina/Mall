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
 * 居家
 */

public class IndoorFragment extends MyBaseFragment {
    public static IndoorFragment newInstance(Bundle bundle) {
        IndoorFragment indoorFragment = new IndoorFragment();
        indoorFragment.setArguments(bundle);

        return indoorFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_indoor_layout,container,false);

        return rootView;
    }
}

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
 * 美妆
 */

public class BeautyFragment extends MyBaseFragment {

    public static BeautyFragment newInstance(Bundle bundle) {
        BeautyFragment beautyFragment = new BeautyFragment();
        beautyFragment.setArguments(bundle);

        return beautyFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beauty_layout, container, false);
        return rootView;
    }
}

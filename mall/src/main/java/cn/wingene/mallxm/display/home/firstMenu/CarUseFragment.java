package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limecn.ghmall.R;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_car_use_layout,container,false);
        return rootView;
    }
}

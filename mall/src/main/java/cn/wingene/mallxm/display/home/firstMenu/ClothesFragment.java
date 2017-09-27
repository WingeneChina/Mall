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
 * 服饰
 */

public class ClothesFragment extends MyBaseFragment {
    public static ClothesFragment newInstance(Bundle bundle) {
        ClothesFragment clothesFragment = new ClothesFragment();
        clothesFragment.setArguments(bundle);

        return clothesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_person_care_layout, container, false);
        return rootView;
    }
}

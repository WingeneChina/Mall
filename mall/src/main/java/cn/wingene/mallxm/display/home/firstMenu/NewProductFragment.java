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
 * 新品
 */

public class NewProductFragment extends MyBaseFragment {

    public static NewProductFragment newInstance(Bundle bundle) {
        NewProductFragment newProductFragment = new NewProductFragment();
        newProductFragment.setArguments(bundle);

        return newProductFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_product_layout,container,false);
        return rootView;
    }
}

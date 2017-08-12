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
 * 天天特价
 */

public class SpecialOfferFragment extends MyBaseFragment {

    public static SpecialOfferFragment newInstance(Bundle bundle) {
        SpecialOfferFragment specialOfferFragment = new SpecialOfferFragment();
        specialOfferFragment.setArguments(bundle);

        return specialOfferFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmen_special_offer_layout, container, false);
        return rootView;
    }
}

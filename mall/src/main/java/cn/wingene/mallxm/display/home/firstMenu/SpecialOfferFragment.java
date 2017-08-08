package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;

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

}

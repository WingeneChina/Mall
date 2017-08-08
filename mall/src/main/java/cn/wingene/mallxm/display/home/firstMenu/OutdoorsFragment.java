package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;

import cn.wingene.mallxf.ui.MyBaseFragment;

/**
 * Created by wangcq on 2017/8/8.
 * 户外
 */

public class OutdoorsFragment extends MyBaseFragment {

    public static OutdoorsFragment newInstance(Bundle bundle) {
        OutdoorsFragment outdoorsFragment = new OutdoorsFragment();
        outdoorsFragment.setArguments(bundle);

        return outdoorsFragment;
    }
}

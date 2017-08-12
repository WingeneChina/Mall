package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;

import cn.wingene.mallxf.ui.MyBaseFragment;

/**
 * Created by wangcq on 2017/8/8.
 * 洗护
 */

public class PersonalCareFragment extends MyBaseFragment {

    public static PersonalCareFragment newInstance(Bundle bundle) {
        PersonalCareFragment personalCareFragment = new PersonalCareFragment();
        personalCareFragment.setArguments(bundle);

        return personalCareFragment;
    }
}

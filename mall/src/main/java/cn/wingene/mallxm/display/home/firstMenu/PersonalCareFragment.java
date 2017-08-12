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
 * 洗护
 */

public class PersonalCareFragment extends MyBaseFragment {

    public static PersonalCareFragment newInstance(Bundle bundle) {
        PersonalCareFragment personalCareFragment = new PersonalCareFragment();
        personalCareFragment.setArguments(bundle);

        return personalCareFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_person_care_layout,container,false);
        return rootView;
    }
}

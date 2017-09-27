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
 * 户外
 */

public class OutdoorsFragment extends MyBaseFragment {

    public static OutdoorsFragment newInstance(Bundle bundle) {
        OutdoorsFragment outdoorsFragment = new OutdoorsFragment();
        outdoorsFragment.setArguments(bundle);

        return outdoorsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_outdoors_layout, container, false);

        return rootView;
    }
}

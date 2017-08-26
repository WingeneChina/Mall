package cn.wingene.mallxf.ui;

import android.app.Activity;

import junze.androidxf.core.Agent;
import junze.androidxf.ui.activity.BaseFragment;

import cn.wingene.mallxf.MyAgent;

/**
 * Created by Wingene on 2017/8/5.
 */

public class MyBaseFragment extends BaseFragment{
    MyAgent mMyAgent;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMyAgent = new MyAgent(activity);
    }

    @Override
    public MyAgent agent() {
        return (MyAgent) mMyAgent;
    }
}

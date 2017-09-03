package cn.wingene.mallxf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import junze.androidxf.core.Agent;
import junze.androidxf.ui.activity.BaseFragment;

import cn.wingene.mallxf.MyAgent;

/**
 * Created by Wingene on 2017/8/5.
 */

public class MyBaseFragment extends BaseFragment implements View.OnTouchListener {
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // 拦截触摸事件，防止泄露下去
        view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}

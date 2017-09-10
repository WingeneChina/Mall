package cn.wingene.mallxf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import junze.android.ui.ViewHolder;
import junze.androidxf.ui.activity.BaseFragment;

import cn.wingene.mall.R;
import cn.wingene.mall.util.LayoutSwitcher;
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
        mMyAgent.initLayoutSwitch(initLayoutSwitch(view));
    }

    protected LayoutSwitcher initLayoutSwitch(View view) {
        View normal = view.findViewById(R.id.layoutNormal);
        if (normal != null) {
            return new LayoutSwitcher(normal);
        }
        return null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    public void switchLayoutNormal() {
        agent().switchLayoutNormal();
    }

    public <T extends ViewHolder> T switchLayoutOther(Class<T> clazz) {
        return agent().switchLayoutOther(clazz);
    }

    public <T extends ViewHolder> T switchLayoutOther(String key, Class<T> clazz) {
        return agent().switchLayoutOther(key, clazz);
    }
}

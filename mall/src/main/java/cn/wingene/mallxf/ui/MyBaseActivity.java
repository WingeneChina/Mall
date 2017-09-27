package cn.wingene.mallxf.ui;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.limecn.ghmall.R;

import junze.android.ui.ViewHolder;

import cn.wingene.mall.util.LayoutSwitcher;
import cn.wingene.mallx.frame.activity.BaseAppCompatActivity;
import cn.wingene.mallxf.MyAgent;

/**
 * Created by Wingene on 2017/8/5.
 */

public class MyBaseActivity extends BaseAppCompatActivity{

    MyAgent mMyAgent = new MyAgent(this);



    @Override
    public MyAgent getAgent() {
        return mMyAgent;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        getAgent().initLayoutSwitch(initLayoutSwitch());
    }

    protected LayoutSwitcher initLayoutSwitch() {
        View normal = findViewById(R.id.layoutNormal);
        if (normal != null) {
            return new LayoutSwitcher(normal);
        }
        return null;
    }

    public void switchLayoutNormal() {
        getAgent().switchLayoutNormal();
    }

    public <T extends ViewHolder> T switchLayoutOther(Class<T> clazz) {
        return getAgent().switchLayoutOther(clazz);
    }

    public <T extends ViewHolder> T switchLayoutOther(String key, Class<T> clazz) {
        return getAgent().switchLayoutOther(key, clazz);
    }
}

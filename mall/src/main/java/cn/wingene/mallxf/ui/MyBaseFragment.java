package cn.wingene.mallxf.ui;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import junze.java.util.StringUtil;

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

    LayoutSwitcher mLayoutSwitcher;

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
        mLayoutSwitcher = initLayoutSwitch(view);
    }

    protected LayoutSwitcher initLayoutSwitch(View view) {
        View normal = view.findViewById(R.id.layoutNormal);
        if (normal != null) {
            return new LayoutSwitcher(normal);
        }
        return null;
    }


    public Map<String, ViewHolder> mLayoutMap = new HashMap<>();


    public void switchLayoutNormal() {
        mLayoutSwitcher.switchNormal();
    }

    /**
     *
     * @param clazz
     * @param <T>
     * @return possible null
     */
    public <T extends ViewHolder> T switchLayoutOther(Class<T> clazz) {
        return switchLayoutOther(null, clazz);
    }

    /**
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return possible null
     */
    public <T extends ViewHolder> T switchLayoutOther(String key, Class<T> clazz) {
        try {
            key = key != null ? key : "";
            String mapKey = StringUtil.spellBy(new String[]{key, clazz.getName()},"#");
            T holder = (T) mLayoutMap.get(mapKey);
            if (holder != null) {
                return holder;
            }
            Constructor<T> constructor = clazz.getConstructor(Context.class);
            holder = constructor.newInstance(getActivity());
            mLayoutSwitcher.switchOther(holder.getView());
            return holder;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}

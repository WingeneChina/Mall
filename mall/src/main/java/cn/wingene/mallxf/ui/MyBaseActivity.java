package cn.wingene.mallxf.ui;

import junze.androidxf.core.Agent;

import cn.wingene.mallx.frame.activity.BaseAppCompatActivity;
import cn.wingene.mallxf.MyAgent;

/**
 * Created by Wingene on 2017/8/5.
 */

public class MyBaseActivity extends BaseAppCompatActivity{

    MyAgent mMyAgent = new MyAgent(this);


    @Override
    public Agent getAgent() {
        return mMyAgent;
    }
}

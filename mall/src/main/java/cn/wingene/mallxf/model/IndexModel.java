package cn.wingene.mallxf.model;

import android.support.v4.app.Fragment;

/**
 * Created by wangcq on 2017/4/10.
 *  fragment 和 title
 */

public class IndexModel {

    public String title;
    public Fragment mFragment;

    public IndexModel(String title, Fragment fragment) {
        this.title = title;
        mFragment = fragment;
    }
}

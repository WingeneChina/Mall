package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;

import cn.wingene.mallxf.ui.MyBaseFragment;

/**
 * Created by wangcq on 2017/8/8.
 * 推荐
 */

public class RecommendFragment extends MyBaseFragment {

    public static RecommendFragment newInstance(Bundle bundle) {
        RecommendFragment recommendFragment = new RecommendFragment();
        recommendFragment.setArguments(bundle);

        return recommendFragment;
    }
}

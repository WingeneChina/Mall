package cn.wingene.mallxm.display.home;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.wingene.mall.R;
import cn.wingene.mallxf.ui.MyBaseFragment;

/**
 * Created by wangcq on 2017/8/7.
 */

public class SecondMenuFragment extends MyBaseFragment {

    private SimpleDraweeView rollPagerItemV;

    public static SecondMenuFragment newInstance(Bundle args) {
        SecondMenuFragment secondMenuFragment = new SecondMenuFragment();
        secondMenuFragment.setArguments(args);

        return secondMenuFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_secondmenu_layout, container, false);
        initViews(view);

        return view;
    }

    private void initViews(View root) {
        rollPagerItemV = (SimpleDraweeView) root.findViewById(R.id.rollPagerItemV);
        Uri uri = Uri.parse("https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1502895322&di=228c7770fb082ec620cf1f373649b161" +
                "&imgtype=jpg&er=1&src=http%3A%2F%2Fimgcache.cjmx.com%2Ffilm%2F201608%2F20160830144736364.jpg");
        rollPagerItemV.setImageURI(uri);
    }
}

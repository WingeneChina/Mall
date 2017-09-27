package cn.wingene.mallxm.display.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.limecn.ghmall.R;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;

import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.ui.MyBaseFragment;

/**
 * Created by wangcq on 2017/9/27.
 * 第四个菜单
 */

public class FourthMenusFragment extends MyBaseFragment implements HttpListener<String> {

    private ImageView backIcon;
    private TextView titleV;
    private TabLayout tabLayout;
    private ViewPager contentPagerV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth_menu_layout, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View root) {
        backIcon = (ImageView) root.findViewById(R.id.backIcon);
        titleV = (TextView) root.findViewById(R.id.titleV);
        tabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        contentPagerV = (ViewPager) root.findViewById(R.id.contentPagerV);
    }

    @Override
    public void onStart() {
        super.onStart();
        requestData();
    }

    /**
     * 请求数据
     */
    private void requestData() {
        NoHttpRequest<BaseResponse> noHttpRequest = new NoHttpRequest<>(BaseResponse.class);
        noHttpRequest.request(getActivity(), HttpConstant.DRIVER_URL, null, 1, this, false, null, false, false);

    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Log.e(this.getClass().getName(),"driver result  ="+response.get());
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}

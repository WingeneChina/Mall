package cn.wingene.mallxm.display.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limecn.ghmall.R;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import cn.wingene.mallxf.MyApp;
import cn.wingene.mallxf.adapter.MailFragmentPagerAdapter;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.model.IndexModel;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxm.display.home.firstMenu.BeautyFragment;
import cn.wingene.mallxm.display.home.firstMenu.data.RecommendModel;
import cn.wingene.mallxm.display.home.fourthMenu.FouthItemMenuFragment;
import cn.wingene.mallxm.display.home.fourthMenu.data.DriveModel;

/**
 * Created by wangcq on 2017/9/27.
 * 第四个菜单
 */

public class FourthMenusFragment extends MyBaseFragment implements HttpListener<String> {

    private TextView locationMarkV;
    private TextView titleV;
    private TabLayout tabLayout;
    private ViewPager contentPagerV;
    private MailFragmentPagerAdapter mMailFragmentPagerAdapter;


    public static FourthMenusFragment newInstance(Bundle bundle) {
        FourthMenusFragment fourthMenusFragment = new FourthMenusFragment();
        fourthMenusFragment.setArguments(bundle);

        return fourthMenusFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth_menu_layout, container, false);
        initViews(view);
        if(MyApp.getBdLocation()!=null) {
            locationMarkV.setVisibility(View.GONE);
            locationMarkV.setText(MyApp.getBdLocation().getCity());
        }else{
            locationMarkV.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    private void initViews(View root) {
        locationMarkV = (TextView) root.findViewById(R.id.locationMarkV);
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

    /**
     * 显示获取的数据
     */
    private void showData(DriveModel driveModel, String dataJson) {
        List<IndexModel> listMenus = new ArrayList<>();
        Bundle bundle;
        for (RecommendModel.DataBean.HeadMenuListBean headMenuListBean : driveModel.HeadMenuList) {
            if (TextUtils.isEmpty(headMenuListBean.getParam())) {
                bundle = new Bundle();
                bundle.putParcelable("resultModel", driveModel);
                bundle.putString("dataString", dataJson);
                listMenus.add(new IndexModel(headMenuListBean.getTitle(), FouthItemMenuFragment.newInstance(bundle)));

            } else {
                bundle = new Bundle();
                bundle.putString("key", headMenuListBean.getParam());
                bundle.putString("type", headMenuListBean.getType());
                bundle.putString("title", headMenuListBean.getTitle());
                listMenus.add(new IndexModel(headMenuListBean.getTitle(), BeautyFragment.newInstance(bundle)));

            }
        }
        if (listMenus.size() <= 4) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        mMailFragmentPagerAdapter = new MailFragmentPagerAdapter(getChildFragmentManager(), listMenus);
        contentPagerV.setAdapter(mMailFragmentPagerAdapter);
        tabLayout.setupWithViewPager(contentPagerV);

    }

    @Override
    public void onSucceed(int what, Response<String> response) {

        Log.e(this.getClass().getName(), "driver result  =" + response.get());
        try {
            GsonUtil<BaseResponse> gsonUtil = new GsonUtil<>(BaseResponse.class);
            BaseResponse baseResponse = gsonUtil.fromJson(response.get());
            if (baseResponse.err == 0) {
                GsonUtil<DriveModel> driveModelGsonUtil = new GsonUtil<>(DriveModel.class);
                DriveModel driveModel = driveModelGsonUtil.fromJson(baseResponse.data.toString());
                showData(driveModel, baseResponse.data.toString());

            } else {
                agent().showToast(baseResponse.msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}

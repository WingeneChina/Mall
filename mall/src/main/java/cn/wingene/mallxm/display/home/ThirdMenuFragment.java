package cn.wingene.mallxm.display.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxf.adapter.MailFragmentPagerAdapter;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.model.IndexModel;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxm.display.home.secondMenu.SelectedFragment;
import cn.wingene.mallxm.display.home.secondMenu.data.MenuItemModel;

/**
 * Created by wangcq on 2017/8/7.
 * 第三个周边菜单
 */

public class ThirdMenuFragment extends MyBaseFragment implements HttpListener<String> {
    public static final String MENU_CODE_ARG = "menuCode";
    public static final String REQUEST_TYPE = "requestType";

    private TextView titleV;
    private TabLayout selectTabLayout;
    private ViewPager nearbyPagerV;

    private MailFragmentPagerAdapter mMailFragmentPagerAdapter;


    public static ThirdMenuFragment newInstance(Bundle args) {
        ThirdMenuFragment thirdMenuFragment = new ThirdMenuFragment();
        thirdMenuFragment.setArguments(args);

        return thirdMenuFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three_menu_layout, container, false);
        initViews(view);
        requestData();
        return view;
    }

    private void initViews(View root) {
        titleV = (TextView) root.findViewById(R.id.titleV);
        selectTabLayout = (TabLayout) root.findViewById(R.id.selectTabLayout);
        nearbyPagerV = (ViewPager) root.findViewById(R.id.nearbyPagerV);
    }

    private void requestData() {
        NoHttpRequest<MenuItemModel> noHttpRequest = new NoHttpRequest<>(MenuItemModel.class);
        noHttpRequest.request(getActivity(), HttpConstant.NEARBY_MENU, null, 1, this, false, "thridMenu", false,
                true);
    }

    private void initViewPagerData(List<MenuItemModel.DataBean> menuItemList) {
        try {
            List<IndexModel> indexModelList = new ArrayList<>();
            for (MenuItemModel.DataBean dataBean : menuItemList) {
                Bundle bundle = new Bundle();
                bundle.putString(MENU_CODE_ARG, dataBean.getCode());
                bundle.putString(REQUEST_TYPE, "");
                indexModelList.add(new IndexModel(dataBean.getName(), SelectedFragment.newInstance(bundle)));
            }

            Log.e(this.getClass().getName(), "indexList.size() = " + indexModelList.size());
            mMailFragmentPagerAdapter = new MailFragmentPagerAdapter(getChildFragmentManager(), indexModelList);
            nearbyPagerV.setAdapter(mMailFragmentPagerAdapter);
            selectTabLayout.setupWithViewPager(nearbyPagerV, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            GsonUtil<MenuItemModel> gsonUtil = new GsonUtil<>(MenuItemModel.class);
            MenuItemModel menuItemModel = gsonUtil.fromJson(response.get());
            if (menuItemModel.getErr() == 0) {
                initViewPagerData(menuItemModel.getData());
            } else {
                ToastUtil.show("获取数据失败", this.getContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}

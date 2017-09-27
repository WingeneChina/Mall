package cn.wingene.mallxm.display.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.limecn.ghmall.R;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import cn.wingene.mallxf.adapter.MailFragmentPagerAdapter;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.model.IndexModel;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxm.display.home.secondMenu.SecondMenuItemFragment;
import cn.wingene.mallxm.display.home.secondMenu.data.MenuItemModel;

/**
 * Created by wangcq on 2017/8/7.
 * 专题
 */

public class SecondMenuFragment extends MyBaseFragment implements HttpListener<String> {
    public static final String MENU_CODE_ARG = "menuCode";
    private View mRootView;
    private ViewPager specialPagerV;
    private TabLayout selectTabLayout;
    private MailFragmentPagerAdapter mMailFragmentPagerAdapter;

    private LinearLayout not_data_layout;
    private LinearLayout haveDataGroupV;


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
        mRootView = view;
        initViews(view);
        requestData();
        return view;
    }


    private void initViews(View root) {
        specialPagerV = (ViewPager) root.findViewById(R.id.specialPagerV);
        selectTabLayout = (TabLayout) root.findViewById(R.id.selectTabLayout);

        not_data_layout = (LinearLayout) root.findViewById(R.id.noDataGroup);
        haveDataGroupV = (LinearLayout) root.findViewById(R.id.haveDataGroupV);

        TextView errorView = (TextView) not_data_layout.findViewById(R.id.errorTextV);
        errorView.setText("sorry,还没有相关数据");

    }

    private void initViewPagerData(List<MenuItemModel.DataBean> menuItemLsit) {
        try {
            List<IndexModel> indexModelList = new ArrayList<>();
            for (MenuItemModel.DataBean dataBean : menuItemLsit) {
                Bundle bundle = new Bundle();
                bundle.putString(MENU_CODE_ARG, dataBean.getCode());
                indexModelList.add(new IndexModel(dataBean.getName(), SecondMenuItemFragment.newInstance(bundle)));
            }

            Log.e(this.getClass().getName(), "indexList.size() = " + indexModelList.size());
            mMailFragmentPagerAdapter = new MailFragmentPagerAdapter(getChildFragmentManager(), indexModelList);
            specialPagerV.setAdapter(mMailFragmentPagerAdapter);

            selectTabLayout.setupWithViewPager(specialPagerV, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    /**
     * 请求数据
     */
    protected void requestData() {
        NoHttpRequest<BaseResponse> noHttpRequest = new NoHttpRequest<BaseResponse>(BaseResponse.class);
        noHttpRequest.request(getActivity(), HttpConstant.SPECIAL_MENU, null, 1, this, false, "secondMenu", false,
                true);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            GsonUtil<MenuItemModel> gsonUtil = new GsonUtil<>(MenuItemModel.class);
            MenuItemModel menuItemModel = gsonUtil.fromJson(response.get());
            if (menuItemModel.getErr() == 0) {
                if (menuItemModel.getData().size() > 0) {
                    haveDataGroupV.setVisibility(View.VISIBLE);
                    not_data_layout.setVisibility(View.GONE);
                    initViewPagerData(menuItemModel.getData());
                } else {
                    haveDataGroupV.setVisibility(View.GONE);
                    not_data_layout.setVisibility(View.VISIBLE);
                }
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

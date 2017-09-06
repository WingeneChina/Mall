package cn.wingene.mallxm.display.home.thridMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static cn.wingene.mallxm.display.home.SecondMenuFragment.MENU_CODE_ARG;
import com.baidu.location.BDLocation;
import com.dalong.refreshlayout.OnRefreshListener;
import com.yanzhenjie.nohttp.rest.Response;

import junze.androidx.baidu.LocationHelper;
import junze.androidx.baidu.OnReceiveLoactionListener;

import cn.wingene.mall.R;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxf.ui.jd_refresh.JDRefreshLayout;
import cn.wingene.mallxm.display.home.secondMenu.data.MenuItemContentModel;
import cn.wingene.mallxm.display.home.thridMenu.adapter.ThirdMenuItemAdatper;
import cn.wingene.mallxm.display.home.thridMenu.data.ThridItemModel;

/**
 * Created by wangcq on 2017/8/27.
 * 周边界面的子界面
 */

public class ThridMenuItemFragment extends MyBaseFragment implements HttpListener<String>, OnReceiveLoactionListener {
    private RecyclerView selectRecyclerV;
    private LinearLayout noDataGroup;
    private JDRefreshLayout mJDRefreshLayout;

    private ThirdMenuItemAdatper mSelectItemAdapter;

    private int mOrderBy = 0;
    private int mPagerIndex = 1;//分页索引
    private List<ThridItemModel.DataBean.ListBean> mListBean = new ArrayList<>();
    private String mLat = "";//"26.134389";
    private String mLong = "";//"119.328601";


    public static ThridMenuItemFragment newInstance(Bundle bundle) {
        ThridMenuItemFragment thirdMenuItemFragment = new ThridMenuItemFragment();
        thirdMenuItemFragment.setArguments(bundle);

        return thirdMenuItemFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_layout, container, false);
        initViews(view);
        initRecyclerV();
        mPagerIndex = 1;
        LocationHelper.getInstance().start(this);
        BDLocation bdLocation = LocationHelper.getInstance().getLocation();
        if(LocationHelper.isLocationSuccess(bdLocation)){
            mLat = bdLocation.getLatitude() + "";
            mLong = "" + bdLocation.getLongitude();
        }
        requestData(mLat,mLong);
        return view;
    }

    private void initViews(View root) {
        mJDRefreshLayout = (JDRefreshLayout) root.findViewById(R.id.secondMenuRefreshV);

        selectRecyclerV = (RecyclerView) root.findViewById(R.id.selectRecyclerV);
        noDataGroup = (LinearLayout) root.findViewById(R.id.noDataGroup);
        TextView errorTextV = (TextView) noDataGroup.findViewById(R.id.errorTextV);
        errorTextV.setText("sorry,还没有相关信息");

        mJDRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPagerIndex = 1;
//                LocationHelper.getInstance().start(ThridMenuItemFragment.this);
                BDLocation bdLocation = LocationHelper.getInstance().getLocation();
                if(LocationHelper.isLocationSuccess(bdLocation)){
                    mLat = bdLocation.getLatitude() + "";
                    mLong = "" + bdLocation.getLongitude();
                }
                requestData(mLat, mLong);
            }

            @Override
            public void onLoadMore() {
                mPagerIndex++;
                BDLocation bdLocation = LocationHelper.getInstance().getLocation();
                if(LocationHelper.isLocationSuccess(bdLocation)){
                    mLat = bdLocation.getLatitude() + "";
                    mLong = "" + bdLocation.getLongitude();
                }
                requestData(mLat, mLong);

            }
        });
    }

    private void initRecyclerV() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        selectRecyclerV.setLayoutManager(linearLayoutManager);
        mSelectItemAdapter = new ThirdMenuItemAdatper(mListBean);//menuItemContentModel.getData().getList()
        selectRecyclerV.setAdapter(mSelectItemAdapter);

    }

    private void showResult(ThridItemModel menuItemContentModel) {
        if (mListBean.size() == 0) {
            if (menuItemContentModel.getData().getList().size() == 0) {
                noDataGroup.setVisibility(View.VISIBLE);
                selectRecyclerV.setVisibility(View.GONE);
            } else {
                noDataGroup.setVisibility(View.GONE);
                selectRecyclerV.setVisibility(View.VISIBLE);
            }
        }
        if (mPagerIndex == 1) {
            mListBean.clear();
        }
        mListBean.addAll(menuItemContentModel.getData().getList());
        mSelectItemAdapter.notifyDataSetChanged();
    }

    private void requestData(String lat, String lng) {
        if (getArguments() != null) {
            NoHttpRequest<MenuItemContentModel> noHttpRequest = new NoHttpRequest<>(MenuItemContentModel
                    .class);
            HashMap<String, Object> hashMapParams = new HashMap<>();
            hashMapParams.put("CategoryCode", getArguments().getString(MENU_CODE_ARG));
            hashMapParams.put("Key", "");//直接置空
            hashMapParams.put("PageIndex", mPagerIndex);
            hashMapParams.put("Lat", lat);
            hashMapParams.put("Lng", lng);

            noHttpRequest.request(getActivity(), HttpConstant.NEARBY_LIST, hashMapParams, 1,
                    ThridMenuItemFragment.this, false,
                    "thirdMenuList", false, true);
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.e("WG0906", response.get());
            mJDRefreshLayout.stopLoadMore(true);
            mJDRefreshLayout.stopRefresh(true);

            GsonUtil<ThridItemModel> gsonUtil = new GsonUtil<>(ThridItemModel.class);
            ThridItemModel menuItemContentModel = gsonUtil.fromJson(response.get());
            showResult(menuItemContentModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }

    @Override
    public void onReceiveLocationListener(BDLocation bdLocation) {
        Log.e(this.getClass().getName(), "bdLocation type = " + bdLocation.getLocType());
        Log.e(this.getClass().getName(), "bdLocationMsg = " + bdLocation.toString());
        if (LocationHelper.isLocationSuccess(bdLocation)) {
//            NoHttpRequest<MenuItemContentModel> noHttpRequest = new NoHttpRequest<>(MenuItemContentModel
//                    .class);
//            HashMap<String, Object> hashMapParams = new HashMap<>();
//            hashMapParams.put("CategoryCode", getArguments().getString(MENU_CODE_ARG));
//            hashMapParams.put("Key", "");//直接置空
//            hashMapParams.put("PageIndex", mPagerIndex);
//            hashMapParams.put("Lat", bdLocation.getLatitude());
//            hashMapParams.put("Lng", bdLocation.getLongitude());
//
//            noHttpRequest.request(getActivity(), HttpConstant.NEARBY_LIST, hashMapParams, 1,
//                    ThridMenuItemFragment.this, false,
//                    "thirdMenuList", false, true);
            mLat = bdLocation.getLatitude() + "";
            mLong = "" + bdLocation.getLongitude();
            requestData(mLat, mLong);
        } else {
            LocationHelper.getInstance().start(this);

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}

package cn.wingene.mallxm.display.home.secondMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dalong.refreshlayout.OnRefreshListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxf.ui.jd_refresh.JDRefreshLayout;
import cn.wingene.mallxm.display.home.secondMenu.adapter.SelectItemAdapter;
import cn.wingene.mallxm.display.home.secondMenu.data.MenuItemContentModel;

import static cn.wingene.mallxm.display.home.SecondMenuFragment.MENU_CODE_ARG;

/**
 * Created by wangcq on 2017/8/14.
 * 专题子页面
 */

public class SecondMenuItemFragment extends MyBaseFragment implements HttpListener<String> {

    private RecyclerView selectRecyclerV;
    private SelectItemAdapter mSelectItemAdapter;
    private List<MenuItemContentModel.DataBean.ListBean> mListBean = new ArrayList<>();

    private LinearLayout noDataGroup;
    private JDRefreshLayout mJDRefreshLayout;


    private int mOrderBy = 0;
    private int mPagerIndex = 1;//分页索引

    public static SecondMenuItemFragment newInstance(Bundle bundle) {
        SecondMenuItemFragment secondMenuItemFragment = new SecondMenuItemFragment();
        secondMenuItemFragment.setArguments(bundle);

        return secondMenuItemFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_layout, container, false);
        initViews(view);
        initRecyclerV();
        mPagerIndex = 1;
        requestData();
        return view;
    }

    private void initViews(View root) {
        mJDRefreshLayout = (JDRefreshLayout) root.findViewById(R.id.secondMenuRefreshV);
        selectRecyclerV = (RecyclerView) root.findViewById(R.id.selectRecyclerV);
        noDataGroup = (LinearLayout) root.findViewById(R.id.noDataGroup);

        mJDRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPagerIndex = 1;
                requestData();
            }

            @Override
            public void onLoadMore() {
                mPagerIndex++;
                requestData();
            }
        });

    }

    private void initRecyclerV() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        selectRecyclerV.setLayoutManager(linearLayoutManager);
        mSelectItemAdapter = new SelectItemAdapter(mListBean);
        selectRecyclerV.setAdapter(mSelectItemAdapter);
    }

    private void showResult(MenuItemContentModel menuItemContentModel) {
        if (mListBean.size() == 0) {
            if (menuItemContentModel.getData().getList().size() == 0) {
                noDataGroup.setVisibility(View.VISIBLE);
                selectRecyclerV.setVisibility(View.GONE);
            } else {
                noDataGroup.setVisibility(View.GONE);
                selectRecyclerV.setVisibility(View.VISIBLE);
            }
        }
        if (mPagerIndex == 1 && mListBean.size() != 0) {
            mListBean.clear();
        }
        mListBean.addAll(menuItemContentModel.getData().getList());
        mSelectItemAdapter.notifyDataSetChanged();
    }

    private void requestData() {
        if (getArguments() != null) {
            NoHttpRequest<MenuItemContentModel> noHttpRequest = new NoHttpRequest<>(MenuItemContentModel.class);
            HashMap<String, Object> hashMapParams = new HashMap<>();
            hashMapParams.put("CategoryCode", getArguments().getString(MENU_CODE_ARG));
            hashMapParams.put("OrderBy", mOrderBy);
            hashMapParams.put("PageIndex", mPagerIndex);
            noHttpRequest.request(getActivity(), HttpConstant.SPECIAL_LIST, hashMapParams, 1, this, false,
                    "menuList", false, true);

        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            mJDRefreshLayout.stopLoadMore(true);
            mJDRefreshLayout.stopRefresh(true);

            GsonUtil<MenuItemContentModel> gsonUtil = new GsonUtil<>(MenuItemContentModel.class);
            MenuItemContentModel menuItemContentModel = gsonUtil.fromJson(response.get());
            showResult(menuItemContentModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}

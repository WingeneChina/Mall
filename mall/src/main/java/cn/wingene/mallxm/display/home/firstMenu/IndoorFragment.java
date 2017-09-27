package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dalong.refreshlayout.OnRefreshListener;
import com.limecn.ghmall.R;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxf.ui.jd_refresh.JDRefreshLayout;
import cn.wingene.mallxf.util.SpaceItemDecoration;
import cn.wingene.mallxm.display.home.firstMenu.adapter.ProductGroupAdapter;
import cn.wingene.mallxm.display.home.firstMenu.adapter.ProductListCommentAdapter;
import cn.wingene.mallxm.display.home.firstMenu.adapter.SpecialOfferRecyclerVAdapter;
import cn.wingene.mallxm.display.home.firstMenu.adapter.YouLikeProduceAdapter;
import cn.wingene.mallxm.display.home.firstMenu.data.ProductListModel;

import static cn.wingene.mallxm.display.home.FirstMenuFragment.PRODUCT_PARAMS;

/**
 * Created by wangcq on 2017/8/8.
 * 居家
 */

public class IndoorFragment extends MyBaseFragment implements HttpListener<String> {
    private RecyclerView indoorRecyclerV;
    private LinearLayout noDataGroup;
    private NestedScrollView haveDataGroupV;
    private JDRefreshLayout mJDRefreshLayout;
    private int orderBy = 0;//0 综合、2 金额降序 、1 金额升序
    private int mPagerIndex = 1;
    private List<ProductListModel.DataBean.ListBean> mListBeanList = new ArrayList<>();
    private ProductGroupAdapter youLikeProduceAdapter;


    public static IndoorFragment newInstance(Bundle bundle) {
        IndoorFragment indoorFragment = new IndoorFragment();
        indoorFragment.setArguments(bundle);

        return indoorFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_indoor_layout, container, false);
        initViews(rootView);
        initSpecialRecyclerV();
        if (mListBeanList.size() == 0) {
            requestData();

        }
        return rootView;
    }

    private void initViews(View root) {
        indoorRecyclerV = (RecyclerView) root.findViewById(R.id.indoorRecyclerV);
        mJDRefreshLayout = (JDRefreshLayout) root.findViewById(R.id.refreshProductLayoutV);
        noDataGroup = (LinearLayout) root.findViewById(R.id.noDataGroup);
        haveDataGroupV = (NestedScrollView) root.findViewById(R.id.haveDataGroupView);

        mJDRefreshLayout.setCanRefresh(true);
        mJDRefreshLayout.setCanLoad(true);

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

    private void requestData() {
        if (getArguments() != null) {
            NoHttpRequest<BaseResponse> responseNoHttpRequest = new NoHttpRequest<>(BaseResponse.class);
            HashMap<String, Object> hasmapParams = new HashMap<>();
            hasmapParams.put("OrderBy", orderBy);
            hasmapParams.put("PageIndex", mPagerIndex);
            String Type = getArguments().getString("type", "");
            hasmapParams.put("Type", Type);
            if ("4".equals(Type)) {
                hasmapParams.put("Key", getArguments().getString("key", ""));
                hasmapParams.put("CategoryCode", "");
            } else {
                hasmapParams.put("CategoryCode", getArguments().getString("key", ""));
                hasmapParams.put("Key", "");

            }
            responseNoHttpRequest.request(getActivity(), HttpConstant.PRODUCT_LIST, hasmapParams, 1, this, false,
                    "specialOffer",
                    true, true);
        }
    }

    /**
     * 写入展示数据
     */
    private void initSpecialRecyclerV() {
        indoorRecyclerV.setNestedScrollingEnabled(false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        indoorRecyclerV.setLayoutManager(gridLayoutManager);


        youLikeProduceAdapter = new ProductGroupAdapter(mListBeanList);
        indoorRecyclerV.setAdapter(youLikeProduceAdapter);

        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(10, 10, 10, 10);
        indoorRecyclerV.addItemDecoration(spaceItemDecoration);

    }

    private void showResultData(ProductListModel productListModel) {
        if (mPagerIndex == 1) {
            mListBeanList.clear();
        }
        mListBeanList.addAll(productListModel
                .getData().getList
                        ());
        youLikeProduceAdapter.notifyDataSetChanged();

    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            mJDRefreshLayout.stopLoadMore(true);
            mJDRefreshLayout.stopRefresh(true);

            GsonUtil<ProductListModel> gsonUtil = new GsonUtil(ProductListModel.class);
            ProductListModel productListModel = gsonUtil.fromJson(response.get());
            if (mListBeanList.size() == 0 && productListModel.getData().getList().size() == 0) {
//                ToastUtil.show("暂无商品", getContext());
                noDataGroup.setVisibility(View.VISIBLE);
                indoorRecyclerV.setVisibility(View.GONE);
                haveDataGroupV.setVisibility(View.GONE);
            } else {
                noDataGroup.setVisibility(View.GONE);
                haveDataGroupV.setVisibility(View.VISIBLE);
                indoorRecyclerV.setVisibility(View.VISIBLE);
            }
            showResultData(productListModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {
        mJDRefreshLayout.stopLoadMore(true);
        mJDRefreshLayout.stopRefresh(true);
    }
}

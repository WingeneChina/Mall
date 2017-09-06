package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dalong.refreshlayout.OnRefreshListener;
import com.yanzhenjie.nohttp.rest.Response;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxf.ui.banner.BannerImgLoader;
import cn.wingene.mallxf.ui.jd_refresh.JDRefreshLayout;
import cn.wingene.mallxf.util.SpaceItemDecoration;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.display.home.firstMenu.adapter.ProductListCommentAdapter;
import cn.wingene.mallxm.display.home.firstMenu.data.ProductListModel;
import cn.wingene.mallxm.display.home.firstMenu.data.RecommendModel;

/**
 * Created by wangcq on 2017/8/13.
 * 商品列表页面
 */

public class ProductListFragment extends MyBaseFragment implements
        HttpListener<String>, View.OnClickListener {
    private JDRefreshLayout mJDRefreshLayout;
    private RecyclerView productListRecyclerV;
    private Banner mBanner;
    private List<String> urlList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    private int orderBy = 0;//0 综合、2 金额降序 、1 金额升序
    private int mPagerIndex = 1;
    private List<ProductListModel.DataBean.ListBean> mListBeanList = new ArrayList<>();
    private ProductListCommentAdapter productListCommentAdapter;

    public static ProductListFragment newInstance(Bundle bundle) {
        ProductListFragment productListFragment = new ProductListFragment();
        productListFragment.setArguments(bundle);
        return productListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_list_layout, container, false);
        initViews(rootView);
//        initRollPagerV();
        initProductListData();
        initEvent();

        requestData();
        return rootView;
    }

    private void initViews(View root) {
        mJDRefreshLayout = (JDRefreshLayout) root.findViewById(R.id.refreshLayoutV);
        productListRecyclerV = (RecyclerView) root.findViewById(R.id.productListV);
        mBanner = (Banner) root.findViewById(R.id.banner);

        mJDRefreshLayout.setCanLoad(true);
        mJDRefreshLayout.setCanRefresh(true);
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

    private void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }

    private void initBanner(final ProductListModel productListModel) {
        if (productListModel != null && productListModel.getData().getBannerList().size() > 0) {
            Collections.sort(productListModel.getData().getBannerList());

            for (RecommendModel.DataBean.BannerListBean bannerListBean : productListModel.getData().getBannerList()) {
                urlList.add(bannerListBean.getImage());
                titleList.add(bannerListBean.getTitle());
            }
        }
        mBanner.setImages(urlList).setBannerTitles(titleList).setDelayTime(3000)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new BannerImgLoader())
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        JumpHelper.startCommodityDetailActivity(getActivity(), Integer.parseInt(productListModel.getData
                                ().getBannerList().get
                                (position).getParam()));

                    }
                }).start();
    }


    private void requestData() {
        NoHttpRequest<BaseResponse> responseNoHttpRequest = new NoHttpRequest<>(BaseResponse.class);
        HashMap<String, Object> hasmapParams = new HashMap<>();
        hasmapParams.put("OrderBy", orderBy);
        hasmapParams.put("PageIndex", mPagerIndex);
        hasmapParams.put("Type", getArguments().getString("type"));
        hasmapParams.put("CategoryCode", getArguments().getString("key", ""));
        responseNoHttpRequest.request(getActivity(), HttpConstant.PRODUCT_LIST, hasmapParams, 1, this, false,
                "productList",
                false, false);

    }

    /**
     * 初始化商品数据
     */
    private void initProductListData() {
        productListRecyclerV.setNestedScrollingEnabled(false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        productListRecyclerV.setLayoutManager(gridLayoutManager);
//
//
//        YouLikeProduceAdapter youLikeProduceAdapter = new YouLikeProduceAdapter(mListBeanList);
//        productListRecyclerV.setAdapter(youLikeProduceAdapter);

        productListCommentAdapter = new ProductListCommentAdapter(mListBeanList);
        productListRecyclerV.setAdapter(productListCommentAdapter);

        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(10, 10, 10, 10);
        productListRecyclerV.addItemDecoration(spaceItemDecoration);
    }

    private void showResultData(ProductListModel productListModel) {
        initBanner(productListModel);

        if (mPagerIndex == 1) {
            mListBeanList.clear();
        }
        mListBeanList.addAll(productListModel.getData().getList());
        productListCommentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            mJDRefreshLayout.stopLoadMore(true);
            mJDRefreshLayout.stopRefresh(true);

            GsonUtil<ProductListModel> gsonUtil = new GsonUtil(ProductListModel.class);
            ProductListModel productListModel = gsonUtil.fromJson(response.get());
            showResultData(productListModel);

            if (productListModel.getData().getList().size() == 0) {
//                ToastUtil.show("暂无商品", getContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }

}

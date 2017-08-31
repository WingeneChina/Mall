package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dalong.refreshlayout.OnRefreshListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxf.adapter.ImagePagerAdapter;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxf.ui.customview.InnerViewpager;
import cn.wingene.mallxf.ui.jd_refresh.JDRefreshLayout;
import cn.wingene.mallxf.util.SpaceItemDecoration;
import cn.wingene.mallxm.display.home.firstMenu.adapter.ProductListCommentAdapter;
import cn.wingene.mallxm.display.home.firstMenu.adapter.YouLikeProduceAdapter;
import cn.wingene.mallxm.display.home.firstMenu.data.ProductListModel;

import static cn.wingene.mallxm.display.home.FirstMenuFragment.PRODUCT_PARAMS;

/**
 * Created by wangcq on 2017/8/13.
 * 商品列表页面
 */

public class ProductListFragment extends MyBaseFragment implements ViewPager.OnPageChangeListener,
        HttpListener<String>, View.OnClickListener {
    private JDRefreshLayout mJDRefreshLayout;
    private RecyclerView productListRecyclerV;
    private InnerViewpager rollPagerV;
    private ImagePagerAdapter mImagePagerAdapter;
    private List<String> urlList = new ArrayList<>();

    private int orderBy = 0;//0 综合、2 金额降序 、1 金额升序
    private int mPagerIndex = 1;
    private List<ProductListModel.DataBean.ListBean> mListBeanList = new ArrayList<>();
    private ProductListCommentAdapter productListCommentAdapter;

    int currentIndex = 0;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            rollPagerV.setCurrentItem(currentIndex + 1, false);

            mHandler.sendEmptyMessageDelayed(1, 5000);

        }
    };

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
        initRollPagerV();
        initProductListData();
        initEvent();

        requestData();
        return rootView;
    }

    private void initViews(View root) {
        mJDRefreshLayout = (JDRefreshLayout) root.findViewById(R.id.refreshLayoutV);
        productListRecyclerV = (RecyclerView) root.findViewById(R.id.productListV);
        rollPagerV = (InnerViewpager) root.findViewById(R.id.rollPagerV);

        mJDRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPagerIndex = 0;
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

    private void initRollPagerV() {
        productListRecyclerV.setNestedScrollingEnabled(false);

        mImagePagerAdapter = new ImagePagerAdapter(urlList);
        rollPagerV.setAdapter(mImagePagerAdapter);
        rollPagerV.addOnPageChangeListener(this);
        mHandler.sendEmptyMessageDelayed(1, 1000);
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        productListRecyclerV.setLayoutManager(gridLayoutManager);

//        YouLikeProduceAdapter youLikeProduceAdapter = new YouLikeProduceAdapter();
//        productListRecyclerV.setAdapter(youLikeProduceAdapter);
        productListCommentAdapter = new ProductListCommentAdapter(mListBeanList);
        productListRecyclerV.setAdapter(productListCommentAdapter);
        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(10, 10, 10, 10);
        productListRecyclerV.addItemDecoration(spaceItemDecoration);
    }

    private void showResultData(ProductListModel productListModel) {
        mListBeanList.addAll(productListModel.getData().getList());
        productListCommentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == urlList.size() - 1) {
            currentIndex = 1;
        } else if (position == 0) {
            currentIndex = urlList.size() - 2;
        } else {
            currentIndex = position;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            rollPagerV.setCurrentItem(currentIndex, false);
        }
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
                ToastUtil.show("暂无商品", getContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }

}

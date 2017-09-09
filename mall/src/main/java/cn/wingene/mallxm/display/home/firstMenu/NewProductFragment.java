package cn.wingene.mallxm.display.home.firstMenu;

import java.util.HashMap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dalong.refreshlayout.OnRefreshListener;
import com.yanzhenjie.nohttp.rest.Response;

import cn.wingene.mall.R;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxf.ui.customview.InnerViewpager;
import cn.wingene.mallxf.ui.jd_refresh.JDRefreshLayout;
import cn.wingene.mallxf.util.SpaceItemDecoration;
import cn.wingene.mallxm.display.home.firstMenu.adapter.SpecialOfferRecyclerVAdapter;
import cn.wingene.mallxm.display.home.firstMenu.data.ProductListModel;

/**
 * Created by wangcq on 2017/8/8.
 * 新品
 */

public class NewProductFragment extends MyBaseFragment implements HttpListener<String> {
    private int orderBy = 0;//0 综合、2 金额降序 、1 金额升序
    private int mPagerIndex = 1;

    private InnerViewpager rollPagerV;
    private RecyclerView specialRecyclerV;
    private JDRefreshLayout refreshLayout;

    public static NewProductFragment newInstance(Bundle bundle) {
        NewProductFragment newProductFragment = new NewProductFragment();
        newProductFragment.setArguments(bundle);

        return newProductFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmen_special_offer_layout, container, false);
        //fragment_new_product_layout
        initViews(rootView);
        requestData();
        return rootView;
    }

    private void initViews(View root) {
        rollPagerV = (InnerViewpager) root.findViewById(R.id.rollPagerV);
        specialRecyclerV = (RecyclerView) root.findViewById(R.id.specialRecyclerV);
        refreshLayout = (JDRefreshLayout) root.findViewById(R.id.refreshLayout);


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.stopRefresh(true);
            }

            @Override
            public void onLoadMore() {
                refreshLayout.stopLoadMore(true);
            }
        });
    }

    /**
     * 请求信息
     */
    private void requestData() {
        NoHttpRequest<BaseResponse> responseNoHttpRequest = new NoHttpRequest<>(BaseResponse.class);
        HashMap<String, Object> hasmapParams = new HashMap<>();
        hasmapParams.put("OrderBy", orderBy);
        hasmapParams.put("PageIndex", mPagerIndex);
        hasmapParams.put("Type", 3);//表示新品类型
        responseNoHttpRequest.request(getActivity(), HttpConstant.PRODUCT_LIST, hasmapParams, 1, this, false,
                "specialOffer",
                false, true);
    }

    /**
     * 写入展示数据
     *
     * @param productListModel
     */
    private void initSpecialRecyclerV(ProductListModel productListModel) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        specialRecyclerV.setLayoutManager(linearLayoutManager);

        SpecialOfferRecyclerVAdapter personRecommendAdapter = new SpecialOfferRecyclerVAdapter(productListModel
                .getData().getList
                        ());
        specialRecyclerV.setAdapter(personRecommendAdapter);

        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(0, 15, 15, 15);
        specialRecyclerV.addItemDecoration(spaceItemDecoration);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Log.e(this.getClass().getName(), "新品  = " + response.get());
        try {
            GsonUtil<ProductListModel> gsonUtil = new GsonUtil(ProductListModel.class);
            ProductListModel productListModel = gsonUtil.fromJson(response.get());
            initSpecialRecyclerV(productListModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}

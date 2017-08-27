package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONObject;

import java.util.HashMap;

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
import cn.wingene.mallxm.display.home.FirstMenuFragment;
import cn.wingene.mallxm.display.home.firstMenu.adapter.PersonRecommendAdapter;
import cn.wingene.mallxm.display.home.firstMenu.adapter.SpecialOfferRecyclerVAdapter;
import cn.wingene.mallxm.display.home.firstMenu.data.ProductListModel;
import cn.wingene.mallxm.display.home.firstMenu.data.RecommendModel;

/**
 * Created by wangcq on 2017/8/8.
 * 天天特价
 */

public class SpecialOfferFragment extends MyBaseFragment implements HttpListener<String> {

    private InnerViewpager rollPagerV;
    private RecyclerView specialRecyclerV;
    private JDRefreshLayout refreshLayout;

    private int orderBy = 0;//0 综合、2 金额降序 、1 金额升序
    private int mPagerIndex = 1;

    public static SpecialOfferFragment newInstance(Bundle bundle) {
        SpecialOfferFragment specialOfferFragment = new SpecialOfferFragment();
        specialOfferFragment.setArguments(bundle);

        return specialOfferFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmen_special_offer_layout, container, false);
        initViews(rootView);
        requestData();
        return rootView;
    }

    private void initViews(View root) {
        rollPagerV = (InnerViewpager) root.findViewById(R.id.rollPagerV);
        specialRecyclerV = (RecyclerView) root.findViewById(R.id.specialRecyclerV);
        refreshLayout = (JDRefreshLayout) root.findViewById(R.id.refreshLayout);
    }

    /**
     * 请求信息
     */
    private void requestData() {
        NoHttpRequest<BaseResponse> responseNoHttpRequest = new NoHttpRequest<>(BaseResponse.class);
        HashMap<String, Object> hasmapParams = new HashMap<>();
        hasmapParams.put("OrderBy", orderBy);
        hasmapParams.put("PageIndex", mPagerIndex);
        hasmapParams.put("Type", 2);
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

        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(10, 10, 10, 10);
        specialRecyclerV.addItemDecoration(spaceItemDecoration);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Log.e(this.getClass().getName(), "天天特价 = " + response.get());
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

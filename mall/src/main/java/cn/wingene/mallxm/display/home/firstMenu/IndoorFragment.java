package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;

import cn.wingene.mall.R;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxf.util.SpaceItemDecoration;
import cn.wingene.mallxm.display.home.firstMenu.adapter.ProductListCommentAdapter;
import cn.wingene.mallxm.display.home.firstMenu.adapter.SpecialOfferRecyclerVAdapter;
import cn.wingene.mallxm.display.home.firstMenu.data.ProductListModel;

import static cn.wingene.mallxm.display.home.FirstMenuFragment.PRODUCT_PARAMS;

/**
 * Created by wangcq on 2017/8/8.
 * 居家
 */

public class IndoorFragment extends MyBaseFragment implements HttpListener<String> {
    private RecyclerView indoorRecyclerV;
    private int orderBy = 0;//0 综合、2 金额降序 、1 金额升序
    private int mPagerIndex = 1;

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
        requestData();
        return rootView;
    }

    private void initViews(View root) {
        indoorRecyclerV = (RecyclerView) root.findViewById(R.id.indoorRecyclerV);
    }

    private void requestData() {
        if (getArguments() != null) {
            NoHttpRequest<BaseResponse> responseNoHttpRequest = new NoHttpRequest<>(BaseResponse.class);
            HashMap<String, Object> hasmapParams = new HashMap<>();
            hasmapParams.put("OrderBy", orderBy);
            hasmapParams.put("PageIndex", mPagerIndex);
            hasmapParams.put("Type", 2);
            hasmapParams.put("CategoryCode", getArguments().getString(PRODUCT_PARAMS));
            responseNoHttpRequest.request(getActivity(), HttpConstant.PRODUCT_LIST, hasmapParams, 1, this, false,
                    "specialOffer",
                    false, true);
        }
    }

    /**
     * 写入展示数据
     *
     * @param productListModel
     */
    private void initSpecialRecyclerV(ProductListModel productListModel) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        indoorRecyclerV.setLayoutManager(linearLayoutManager);

        ProductListCommentAdapter productListCommentAdapter = new ProductListCommentAdapter(productListModel
                .getData().getList
                        ());
        indoorRecyclerV.setAdapter(productListCommentAdapter);

        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(10, 10, 10, 10);
        indoorRecyclerV.addItemDecoration(spaceItemDecoration);

    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            GsonUtil<ProductListModel> gsonUtil = new GsonUtil(ProductListModel.class);
            ProductListModel productListModel = gsonUtil.fromJson(response.get());
            initSpecialRecyclerV(productListModel);
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

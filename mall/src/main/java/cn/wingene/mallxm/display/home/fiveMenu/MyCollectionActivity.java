package cn.wingene.mallxm.display.home.fiveMenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dalong.refreshlayout.OnRefreshListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxf.ui.jd_refresh.JDRefreshLayout;
import cn.wingene.mallxf.util.SpaceItemDecoration;
import cn.wingene.mallxm.D;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.display.home.fiveMenu.adapter.CollectionAdapter;
import cn.wingene.mallxm.display.home.fiveMenu.daa.CollectionDataModel;


public class MyCollectionActivity extends MyBaseActivity implements View.OnClickListener, HttpListener<String>,
        CollectionAdapter.OnCollectionItemClickListener, CollectionAdapter.OnCollectionDeleteViewClickListener {
    //获取收藏列表
    private static final int COLLECTION_LIST_WHAT = 1;
    //删除结果
    private static final int DELETE_COLLECTION = 2;

    private CollectionAdapter mCollectionAdapter;
    private List<CollectionDataModel.DataBean.ListBean> mCollectionList = new ArrayList<>();

    private TextView titleV;
    private ImageView backIcon;
    private ImageView customerV;
    private RecyclerView collectionRecyclerV;
    private TextView errorTextV;
    private LinearLayout noDataGroup;
    private JDRefreshLayout mJDRefreshLayout;

    private int pageIndex = 1;
    private int deletePosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_conllection);
        initViews();
        initEvent();
        requestMyCollection();
        initCollectionRecyclerV();
    }

    private void initViews() {
        titleV = (TextView) findViewById(R.id.titleV);
        backIcon = (ImageView) findViewById(R.id.backIcon);
        customerV = (ImageView) findViewById(R.id.customerV);
        collectionRecyclerV = (RecyclerView) findViewById(R.id.collectionRecyclerV);
        errorTextV = (TextView) findViewById(R.id.errorTextV);
        noDataGroup = (LinearLayout) findViewById(R.id.noDataGroup);

        mJDRefreshLayout = (JDRefreshLayout) findViewById(R.id.refreshCollectionLayoutV);
        mJDRefreshLayout.setCanRefresh(true);
        mJDRefreshLayout.setCanLoad(true);

        mJDRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                requestMyCollection();
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                requestMyCollection();
            }
        });
    }

    private void initCollectionRecyclerV() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        collectionRecyclerV.setLayoutManager(linearLayoutManager);

        mCollectionAdapter = new CollectionAdapter(mCollectionList);
        collectionRecyclerV.setAdapter(mCollectionAdapter);

        mCollectionAdapter.setOnCollectionDeleteViewClickListener(this);
        mCollectionAdapter.setOnCollectionItemClickListener(this);

    }


    private void initEvent() {
        backIcon.setOnClickListener(this);
        customerV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIcon:
                onBackPressed();
                break;
            case R.id.customerV:
                getAgent().tryCallPhone("客服", D.CUSTOMER_PHONE);

                break;
        }
    }

    @Override
    public void onCollectionItemClick(int position) {
        JumpHelper.startCommodityDetailActivity(this, mCollectionList.get(position).getId());
    }


    @Override
    public void onCollectionItemDelete(int position) {
        deletePosition = position;
        deleteCollection(mCollectionList.get(position).getId());
    }

    /**
     * 请求我的收藏
     */
    private void requestMyCollection() {
        NoHttpRequest<BaseResponse> noHttpRequest = new NoHttpRequest<>(BaseResponse.class);
        HashMap<String, Object> hashMapParams = new HashMap<>();
        hashMapParams.put("PageIndex", pageIndex);
        noHttpRequest.request(this, HttpConstant.MY_COLLECTION_LIST, hashMapParams, COLLECTION_LIST_WHAT, this,
                false, null, true, true);

    }

    private void deleteCollection(int id) {
        NoHttpRequest<BaseResponse> noHttpRequest = new NoHttpRequest<>(BaseResponse.class);
        HashMap<String, Object> hashMapParams = new HashMap<>();
        hashMapParams.put("Id", id);
        noHttpRequest.request(this, HttpConstant.DELETE_MY_COLLECTION, hashMapParams, DELETE_COLLECTION, this, false,
                null, false, false);
    }

    private void showResult(CollectionDataModel dataBean) {
        if (pageIndex == 1) {
            mCollectionList.clear();
            mCollectionAdapter.notifyDataSetChanged();

            if (dataBean.getData().getList() == null || dataBean.getData().getList().size() == 0) {
                noDataGroup.setVisibility(View.VISIBLE);
            } else {
                noDataGroup.setVisibility(View.GONE);
            }
        }
        if (dataBean.getData().getList() != null) {
            mCollectionList.addAll(dataBean.getData().getList());
            mCollectionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        GsonUtil<BaseResponse> gsonUtil = new GsonUtil<>(BaseResponse.class);
        BaseResponse baseResponse = gsonUtil.fromJson(response.get());
        if (baseResponse.err == 0) {
            switch (what) {
                case COLLECTION_LIST_WHAT:
                    Log.e(this.getClass().getName(), "收藏列表 返回 = " + response.get());
                    if (baseResponse.data != null) {
                        GsonUtil<CollectionDataModel> gsonUtil1 = new GsonUtil<>(CollectionDataModel
                                .class);
                        CollectionDataModel dataBean = gsonUtil1.fromJson(response.get());
                        showResult(dataBean);
                    }
                    break;
                case DELETE_COLLECTION:
                    ToastUtil.show(baseResponse.msg, this);
                    mCollectionList.remove(deletePosition);
                    mCollectionAdapter.notifyDataSetChanged();
                    deletePosition = 0;
                    break;
            }
        } else {
            ToastUtil.show(baseResponse.msg, this);
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }

}

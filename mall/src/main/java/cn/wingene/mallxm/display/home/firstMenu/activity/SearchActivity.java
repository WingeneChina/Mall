package cn.wingene.mallxm.display.home.firstMenu.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxf.cacheData.UserData;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.model.LocalSearchData;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxf.util.SpaceItemDecoration;
import cn.wingene.mallxm.display.home.firstMenu.adapter.ProductGroupAdapter;
import cn.wingene.mallxm.display.home.firstMenu.adapter.ProductListCommentAdapter;
import cn.wingene.mallxm.display.home.firstMenu.adapter.SearchItemAdapter;
import cn.wingene.mallxm.display.home.firstMenu.data.HotSearchModel;
import cn.wingene.mallxm.display.home.firstMenu.data.ProductListModel;

/**
 * 搜索界面
 */
public class SearchActivity extends AppCompatActivity implements HttpListener<String>, View.OnClickListener {

    private final int SEARCH_RESULT = 1;
    private final int HOT_SEARCH = 2;

    private EditText searchEditV;
    private TextView searchDelV;
    private TextView cancelV;
    private RecyclerView relativeSearchV;
    private RecyclerView hotSearchV;
    private LinearLayout searchPreGroupV;
    private RecyclerView searchResultRecyclerV;
    private LinearLayout notDataGroupV;

    private int orderBy = 0;//0 综合、2 金额降序 、1 金额升序
    private int mPagerIndex = 1;

    private List<ProductListModel.DataBean.ListBean> mListBeanList = new ArrayList<>();
//    private ProductListCommentAdapter mProductListCommentAdapter;

    private ProductGroupAdapter youLikeProduceAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
        initEvent();
        loadHistorySearch();
        loadHotSearch();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(getIntent().getStringExtra("key"))) {
            searchEditV.setText(getIntent().getStringExtra("key"));
            requestSearch();
        }

    }

    private void initViews() {
        searchEditV = (EditText) findViewById(R.id.searchEditV);
        cancelV = (TextView) findViewById(R.id.cancelV);
        searchDelV = (TextView) findViewById(R.id.searchDelV);
        relativeSearchV = (RecyclerView) findViewById(R.id.relativeSearchV);
        hotSearchV = (RecyclerView) findViewById(R.id.hotSearchV);
        searchPreGroupV = (LinearLayout) findViewById(R.id.searchPreGroupV);
        searchResultRecyclerV = (RecyclerView) findViewById(R.id.searchResultRecyclerV);
        notDataGroupV = (LinearLayout) findViewById(R.id.noDataGroup);
        notDataGroupV.setVisibility(View.GONE);
        initSearchResultRecyclerV();

        TextView textView = (TextView) notDataGroupV.findViewById(R.id.errorTextV);
        textView.setText("sorry,还没有相关数据");
    }

    /**
     * 载入历史搜索
     */
    private void loadHistorySearch() {
        try {
            String localJson = UserData.getLocalData();
            Log.e(this.getClass().getName(), "保存的历史搜索item = " + localJson);
            GsonUtil<LocalSearchData> gsonUtil = new GsonUtil<>(LocalSearchData.class);
            final LocalSearchData localSearchData = gsonUtil.fromJson(localJson);
            SearchItemAdapter adapter = new SearchItemAdapter(localSearchData.getLocalList(), SearchItemAdapter
                    .LOCAL_SEARCH_TYPE);

            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4,
                    StaggeredGridLayoutManager.VERTICAL);
            relativeSearchV.setLayoutManager(staggeredGridLayoutManager);
            relativeSearchV.setAdapter(adapter);

            adapter.setHistorySearchItemChoiceListener(new SearchItemAdapter.HistorySearchItemChoiceListener() {
                @Override
                public void historySearchItemChoice(int position) {
                    searchEditV.setText(localSearchData.getLocalList().get(position));
                    requestSearch();
                }
            });
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    /**
     * 载入热门搜索
     */
    private void loadHotSearch() {
        NoHttpRequest<HotSearchModel> noHttpRequest = new NoHttpRequest<>(HotSearchModel.class);
        noHttpRequest.request(this, HttpConstant.HOT_SEARCH, null, HOT_SEARCH, this, false, null, false, true);
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        cancelV.setOnClickListener(this);
        searchDelV.setOnClickListener(this);

        searchEditV.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//
                    requestSearch();
                    LocalSearchData.getInstance().addLocalSearchData(v.getText().toString());

                    try {
                        GsonUtil<LocalSearchData> gsonUtil = new GsonUtil<>(LocalSearchData.class);
                        UserData.saveLocalData(gsonUtil.dataToJson(LocalSearchData.getInstance()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });
        searchEditV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
//                    requestSearch();
                } else {
                    mListBeanList.clear();
                    youLikeProduceAdapter.notifyDataSetChanged();
                    searchResultRecyclerV.setVisibility(View.GONE);
                    notDataGroupV.setVisibility(View.GONE);
                    searchPreGroupV.setVisibility(View.VISIBLE);

                    loadHistorySearch();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchDelV:
                LocalSearchData.getInstance().getLocalList().clear();
                try {
                    GsonUtil<LocalSearchData> gsonUtil = new GsonUtil<>(LocalSearchData.class);
                    UserData.saveLocalData(gsonUtil.dataToJson(LocalSearchData.getInstance()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (relativeSearchV.getAdapter() != null) {
                    relativeSearchV.getAdapter().notifyDataSetChanged();
                }
                break;
            case R.id.cancelV:
                try {
                    GsonUtil<LocalSearchData> gsonUtil = new GsonUtil<>(LocalSearchData.class);
                    UserData.saveLocalData(gsonUtil.dataToJson(LocalSearchData.getInstance()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                onBackPressed();
                break;

        }
    }

    private void requestSearch() {
        NoHttpRequest<BaseResponse> responseNoHttpRequest = new NoHttpRequest<>(BaseResponse.class);
        HashMap<String, Object> hasmapParams = new HashMap<>();
        hasmapParams.put("OrderBy", orderBy);
        hasmapParams.put("PageIndex", mPagerIndex);
        hasmapParams.put("Key", searchEditV.getText().toString());
        hasmapParams.put("Type", getIntent().getStringExtra("type"));
        hasmapParams.put("CategoryCode", getIntent().getStringExtra("typeCode"));
        responseNoHttpRequest.request(this, HttpConstant.PRODUCT_LIST, hasmapParams, SEARCH_RESULT, this, false,
                null,
                true, true);
    }

    /**
     * 写入展示数据
     */
    private void initSearchResultRecyclerV() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        searchResultRecyclerV.setLayoutManager(gridLayoutManager);

        youLikeProduceAdapter = new ProductGroupAdapter(mListBeanList);
        searchResultRecyclerV.setAdapter(youLikeProduceAdapter);

        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(10, 10, 10, 10);
        searchResultRecyclerV.addItemDecoration(spaceItemDecoration);

    }

    private void showResultData(ProductListModel productListModel) {
        mListBeanList.clear();
        youLikeProduceAdapter.notifyDataSetChanged();
        mListBeanList.addAll(productListModel
                .getData().getList
                        ());
        youLikeProduceAdapter.notifyDataSetChanged();
        if (mListBeanList.size() > 0) {
            searchResultRecyclerV.setVisibility(View.VISIBLE);
            searchPreGroupV.setVisibility(View.GONE);

        } else {
            searchPreGroupV.setVisibility(View.VISIBLE);
            searchResultRecyclerV.setVisibility(View.GONE);
//            notDataGroupV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            switch (what) {
                case SEARCH_RESULT:
                    GsonUtil<ProductListModel> gsonUtil = new GsonUtil(ProductListModel.class);
                    ProductListModel productListModel = gsonUtil.fromJson(response.get());
                    showResultData(productListModel);
                    if (productListModel.getData().getList().size() == 0) {
//                        ToastUtil.show("暂无商品", this);
                    }

                    break;
                case HOT_SEARCH:
                    GsonUtil<HotSearchModel> gsonUtil1 = new GsonUtil<>(HotSearchModel.class);
                    final HotSearchModel hotSearchModel = gsonUtil1.fromJson(response.get());
                    List<String> listHot = new ArrayList<>();
                    for (HotSearchModel.DataBean.ListBean listBean : hotSearchModel.getData().getList()) {
                        listHot.add(listBean.getName());
                    }
                    SearchItemAdapter adapter = new SearchItemAdapter(listHot, SearchItemAdapter
                            .HOT_SEARCH_TYPE);

                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4,
                            StaggeredGridLayoutManager.VERTICAL);
                    hotSearchV.setLayoutManager(staggeredGridLayoutManager);
                    hotSearchV.setAdapter(adapter);

                    adapter.setHistorySearchItemChoiceListener(new SearchItemAdapter.HistorySearchItemChoiceListener() {
                        @Override
                        public void historySearchItemChoice(int position) {
                            searchEditV.setText(hotSearchModel.getData().getList().get(position).getName());
                            requestSearch();
                        }
                    });
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }

}

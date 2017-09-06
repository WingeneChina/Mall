package cn.wingene.mallxm.display.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.nohttp.rest.Response;

import cn.wingene.mall.R;
import cn.wingene.mallxf.adapter.MailFragmentPagerAdapter;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.model.IndexModel;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.display.home.firstMenu.RecommendFragment;
import cn.wingene.mallxm.display.home.firstMenu.activity.ProductActivity;
import cn.wingene.mallxm.display.home.firstMenu.activity.ProductRecommendActivity;
import cn.wingene.mallxm.display.home.firstMenu.activity.SearchActivity;
import cn.wingene.mallxm.display.home.firstMenu.data.RecommendModel;
import cn.wingene.mallxm.display.home.setting.AboutAsActivity;

/**
 * Created by wangcq on 2017/8/7.
 * 首页
 */

public class FirstMenuFragment extends MyBaseFragment implements HttpListener<String>, View.OnClickListener {
    public static final String RESULT_ARG = "resultJson";
    public static final String PRODUCT_PARAMS = "CategoryCode";
    private ImageView logoV;
    private TextView searchMarkV;
    private ImageView shoppingCart;
    private TabLayout mTabLayout;
    private ViewPager contentPagerV;
    private MailFragmentPagerAdapter mMailFragmentPagerAdapter;
    private TabLayout.Tab tab;


    public static FirstMenuFragment newInstance(Bundle args) {

        FirstMenuFragment firstMenuFragment = new FirstMenuFragment();
        firstMenuFragment.setArguments(args);

        return firstMenuFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firstmenu_layout, container, false);
        initViews(view);
        initEvent();
//        initViewPager();
        requestData();

        return view;
    }

    private void initViews(View root) {
        logoV = (ImageView) root.findViewById(R.id.logoV);
        searchMarkV = (TextView) root.findViewById(R.id.searchMarkV);
        shoppingCart = (ImageView) root.findViewById(R.id.shoppingCart);
        mTabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        contentPagerV = (ViewPager) root.findViewById(R.id.contentPagerV);

    }

    private void initEvent() {
        shoppingCart.setOnClickListener(this);
        searchMarkV.setOnClickListener(this);
        logoV.setOnClickListener(this);
    }

    /**
     * 请求数据
     */
    private void requestData() {
        NoHttpRequest<BaseResponse> responseNoHttpRequest = new NoHttpRequest<>(BaseResponse.class);
        responseNoHttpRequest.request(getActivity(), HttpConstant.HOME_RECOMMEND, null, 1, this, false, "recommend",
                true, true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shoppingCart:
                JumpHelper.startShoppingCartActivity(getActivity());
                break;
            case R.id.searchMarkV:
                Intent intent = new Intent(this.getActivity(), SearchActivity.class);
                intent.putExtra("type", "0");//综合搜索
                intent.putExtra("typeCode", "");//表示 CategoryCode 字段
                startActivity(intent);
                break;
            case R.id.logoV:
                Intent aboutIntent = new Intent(this.getActivity(), AboutAsActivity.class);
                startActivity(aboutIntent);

                break;
        }
    }

    private void initViewPager(final RecommendModel recommendModel, String resultJson) {
        Bundle bundle = new Bundle();
        bundle.putString(RESULT_ARG, resultJson);
        List<IndexModel> fragmentList = new ArrayList<>();
        fragmentList.add(new IndexModel("推荐", RecommendFragment.newInstance(bundle)));

        for (RecommendModel.DataBean.HeadMenuListBean headMenuListBean : recommendModel.getData().getHeadMenuList()) {
            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setText(headMenuListBean.getTitle());
            mTabLayout.addTab(tab);
        }

        mMailFragmentPagerAdapter = new MailFragmentPagerAdapter(getChildFragmentManager(), fragmentList);
        contentPagerV.setAdapter(mMailFragmentPagerAdapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e(this.getClass().getName(), "tab.getText() = " + tab.getText());
                for (RecommendModel.DataBean.HeadMenuListBean headMenuListBean : recommendModel.getData()
                        .getHeadMenuList()) {
                    if (headMenuListBean.getTitle().contains(String.valueOf(tab.getText()))) {
                        if (!TextUtils.isEmpty(headMenuListBean.getParam())) {
                            Intent intent = new Intent(getActivity(), ProductActivity.class);
                            intent.putExtra("key", headMenuListBean.getParam());
                            intent.putExtra("type", headMenuListBean.getType());
                            intent.putExtra("title", headMenuListBean.getTitle());
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent(getActivity(), ProductRecommendActivity.class);
                            intent.putExtra("key", String.valueOf(headMenuListBean.getParam()));
                            intent.putExtra("type", String.valueOf(headMenuListBean.getType()));
                            intent.putExtra("title", headMenuListBean.getTitle());

                            startActivity(intent);

                        }
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                for (RecommendModel.DataBean.HeadMenuListBean headMenuListBean : recommendModel.getData()
                        .getHeadMenuList()) {
                    if (headMenuListBean.getTitle().contains(String.valueOf(tab.getText()))) {
                        if (!TextUtils.isEmpty(headMenuListBean.getParam())) {
                            Intent intent = new Intent(getActivity(), ProductActivity.class);
                            intent.putExtra("key", headMenuListBean.getParam());
                            intent.putExtra("type", headMenuListBean.getType());
                            intent.putExtra("title", headMenuListBean.getTitle());
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent(getActivity(), ProductRecommendActivity.class);
                            intent.putExtra("key", String.valueOf(headMenuListBean.getParam()));
                            intent.putExtra("type", String.valueOf(headMenuListBean.getType()));
                            intent.putExtra("title", headMenuListBean.getTitle());

                            startActivity(intent);

                        }
                    }
                }
            }
        });
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        GsonUtil<RecommendModel> gsonUtil = new GsonUtil<>(RecommendModel.class);
        RecommendModel recommendModel = gsonUtil.fromJson(response.get());
        if (recommendModel.getErr() == 0) {
            initViewPager(recommendModel, response.get());
        } else {
            ToastUtil.show("加载首页信息失败", getContext());
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }

}

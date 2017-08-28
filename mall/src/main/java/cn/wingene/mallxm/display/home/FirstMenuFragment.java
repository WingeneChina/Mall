package cn.wingene.mallxm.display.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.collect.Collections2;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import cn.wingene.mallxm.display.home.firstMenu.BeautyFragment;
import cn.wingene.mallxm.display.home.firstMenu.CarUseFragment;
import cn.wingene.mallxm.display.home.firstMenu.ClothesFragment;
import cn.wingene.mallxm.display.home.firstMenu.GamingFragment;
import cn.wingene.mallxm.display.home.firstMenu.IndoorFragment;
import cn.wingene.mallxm.display.home.firstMenu.NewProductFragment;
import cn.wingene.mallxm.display.home.firstMenu.OutdoorsFragment;
import cn.wingene.mallxm.display.home.firstMenu.PersonalCareFragment;
import cn.wingene.mallxm.display.home.firstMenu.RecommendFragment;
import cn.wingene.mallxm.display.home.firstMenu.SnacksFragment;
import cn.wingene.mallxm.display.home.firstMenu.SpecialOfferFragment;
import cn.wingene.mallxm.display.home.firstMenu.data.RecommendModel;

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
        }
    }

    private void initViewPager(RecommendModel recommendModel, String resultJson) {
        Bundle bundle = new Bundle();
        bundle.putString(RESULT_ARG, resultJson);

        List<IndexModel> fragmentList = new ArrayList<>();
        fragmentList.add(new IndexModel("推荐", RecommendFragment.newInstance(bundle)));
        fragmentList.add(new IndexModel("天天特价", SpecialOfferFragment.newInstance(bundle)));
        fragmentList.add(new IndexModel("新品", NewProductFragment.newInstance(bundle)));
        for (RecommendModel.DataBean.HeadMenuListBean headMenuListBean : recommendModel.getData().getHeadMenuList()) {
            if (!TextUtils.isEmpty(headMenuListBean.getParam())) {
                bundle.putString(PRODUCT_PARAMS, headMenuListBean.getParam());
                fragmentList.add(new IndexModel(headMenuListBean.getTitle(), IndoorFragment.newInstance(bundle)));

            }
        }
//        fragmentList.add(new IndexModel("居家", IndoorFragment.newInstance(bundle)));
//        fragmentList.add(new IndexModel("零食", SnacksFragment.newInstance(bundle)));
//        fragmentList.add(new IndexModel("美妆", BeautyFragment.newInstance(bundle)));
//        fragmentList.add(new IndexModel("服饰", ClothesFragment.newInstance(bundle)));
//        fragmentList.add(new IndexModel("洗护", PersonalCareFragment.newInstance(bundle)));
//        fragmentList.add(new IndexModel("户外", OutdoorsFragment.newInstance(bundle)));
//        fragmentList.add(new IndexModel("电竞", GamingFragment.newInstance(bundle)));
//        fragmentList.add(new IndexModel("汽车用品", CarUseFragment.newInstance(bundle)));


        mMailFragmentPagerAdapter = new MailFragmentPagerAdapter(getChildFragmentManager(), fragmentList);
        contentPagerV.setAdapter(mMailFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(contentPagerV, true);//同步
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

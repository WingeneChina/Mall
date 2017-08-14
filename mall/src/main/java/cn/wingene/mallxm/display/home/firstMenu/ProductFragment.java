package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxf.adapter.MailFragmentPagerAdapter;
import cn.wingene.mallxf.model.IndexModel;
import cn.wingene.mallxf.ui.MyBaseFragment;

/**
 * Created by wangcq on 2017/8/13.
 * 商品概要fragment
 */

public class ProductFragment extends MyBaseFragment {

    private ImageView backIcon;
    private ImageView searchV;
    private TabLayout productTitleGroupV;
    private ViewPager productPagers;
    private MailFragmentPagerAdapter mMailFragmentPagerAdapter;

    public static ProductFragment newInstance(Bundle bundle) {
        ProductFragment productFragment = new ProductFragment();
        productFragment.setArguments(bundle);

        return productFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_layout, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View root) {
        backIcon = (ImageView) root.findViewById(R.id.backIcon);
        searchV = (ImageView) root.findViewById(R.id.searchV);
        productTitleGroupV = (TabLayout) root.findViewById(R.id.productTitleGroupV);
        productPagers = (ViewPager) root.findViewById(R.id.productPagers);

        List<IndexModel> fragmentList = new ArrayList<>();
        fragmentList.add(new IndexModel("T恤", ProductListFragment.newInstance(null)));
        fragmentList.add(new IndexModel("裤装", ProductListFragment.newInstance(null)));
        fragmentList.add(new IndexModel("内裤", ProductListFragment.newInstance(null)));
        fragmentList.add(new IndexModel("内衣", ProductListFragment.newInstance(null)));
        fragmentList.add(new IndexModel("家居服", ProductListFragment.newInstance(null)));
        fragmentList.add(new IndexModel("袜子", ProductListFragment.newInstance(null)));

        mMailFragmentPagerAdapter = new MailFragmentPagerAdapter(getChildFragmentManager(), fragmentList);
        productPagers.setAdapter(mMailFragmentPagerAdapter);
        productTitleGroupV.setupWithViewPager(productPagers, true);//同步
    }

    private void initEvent() {

    }

}

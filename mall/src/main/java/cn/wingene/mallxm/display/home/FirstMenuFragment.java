package cn.wingene.mallxm.display.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxf.adapter.MailFragmentPagerAdapter;
import cn.wingene.mallxf.model.IndexModel;
import cn.wingene.mallxf.ui.MyBaseFragment;
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

/**
 * Created by wangcq on 2017/8/7.
 * 首页
 */

public class FirstMenuFragment extends MyBaseFragment {

    private ImageView logoV;
    private TextView searchMarkV;
    private ImageView ShoppingCart;
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
        initViewPager();

        return view;
    }

    private void initViews(View root) {
        logoV = (ImageView) root.findViewById(R.id.logoV);
        searchMarkV = (TextView) root.findViewById(R.id.searchMarkV);
        ShoppingCart = (ImageView) root.findViewById(R.id.ShoppingCart);
        mTabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        contentPagerV = (ViewPager) root.findViewById(R.id.contentPagerV);

    }

    private void initViewPager() {

        List<IndexModel> fragmentList = new ArrayList<>();
        fragmentList.add(new IndexModel("推荐", RecommendFragment.newInstance(null)));
//        fragmentList.add(new IndexModel("历史浏览", HistoryFragment.newInstance()));
        fragmentList.add(new IndexModel("天天特价", SpecialOfferFragment.newInstance(null)));
        fragmentList.add(new IndexModel("新品", NewProductFragment.newInstance(null)));
        fragmentList.add(new IndexModel("居家", IndoorFragment.newInstance(null)));
        fragmentList.add(new IndexModel("零食", SnacksFragment.newInstance(null)));
        fragmentList.add(new IndexModel("美妆", BeautyFragment.newInstance(null)));
        fragmentList.add(new IndexModel("服饰", ClothesFragment.newInstance(null)));
        fragmentList.add(new IndexModel("洗护", PersonalCareFragment.newInstance(null)));
        fragmentList.add(new IndexModel("户外", OutdoorsFragment.newInstance(null)));
        fragmentList.add(new IndexModel("电竞", GamingFragment.newInstance(null)));
        fragmentList.add(new IndexModel("汽车用品", CarUseFragment.newInstance(null)));


        mMailFragmentPagerAdapter = new MailFragmentPagerAdapter(getChildFragmentManager(), fragmentList);
        contentPagerV.setAdapter(mMailFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(contentPagerV, true);//同步
    }
}

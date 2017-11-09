package cn.wingene.mallxm.display.home.fourthMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.limecn.ghmall.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxf.ui.banner.BannerImgLoader;
import cn.wingene.mallxf.util.SpaceItemDecoration;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.display.WebActivity;
import cn.wingene.mallxm.display.home.firstMenu.adapter.PersonRecommendAdapter;
import cn.wingene.mallxm.display.home.firstMenu.data.RecommendModel;
import cn.wingene.mallxm.display.home.fourthMenu.adapter.DriverMenuTitleItemAdapter;
import cn.wingene.mallxm.display.home.fourthMenu.data.DriveModel;

/**
 * Created by wangcq on 2017/9/27.
 * 驾培子菜单
 */

public class FouthItemMenuFragment extends MyBaseFragment implements DriverMenuTitleItemAdapter
        .OnMenuItemClickListener {

    private Banner banner;
    private RecyclerView driverItemMenuRecyclerV;
    private RecyclerView driverItemRecyclerV;
    private NestedScrollView haveDataGroupView;
    private TextView errorTextV;
    private LinearLayout noDataGroup;
    //    private JDRefreshLayout refreshProductLayoutV;
    private List<String> urlList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private PersonRecommendAdapter personRecommendAdapter;
    private List<RecommendModel.DataBean.RecommendBean.ProductListBean> mProductListBeen = new ArrayList<>();

    private DriverMenuTitleItemAdapter mDriverMenuTitleItemAdapter;
    private List<RecommendModel.DataBean.BannerListBean> menuList = new ArrayList<>();

    public static FouthItemMenuFragment newInstance(Bundle bundle) {
        FouthItemMenuFragment fouthItemMenuFragment = new FouthItemMenuFragment();
        fouthItemMenuFragment.setArguments(bundle);

        return fouthItemMenuFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth_item_layout, container, false);
        initViews(view);
        initItemRecyclerV();
        initDriverTitleRecycler();
        showData();

        return view;
    }

    private void initViews(View root) {
        banner = (Banner) root.findViewById(R.id.banner);
        driverItemMenuRecyclerV = (RecyclerView) root.findViewById(R.id.driverItemMenuRecyclerV);
        driverItemRecyclerV = (RecyclerView) root.findViewById(R.id.driverItemRecyclerV);
        haveDataGroupView = (NestedScrollView) root.findViewById(R.id.haveDataGroupView);
        errorTextV = (TextView) root.findViewById(R.id.errorTextV);
        noDataGroup = (LinearLayout) root.findViewById(R.id.noDataGroup);
//        refreshProductLayoutV = (JDRefreshLayout) root.findViewById(R.id.refreshProductLayoutV);
    }

    private void initDriverTitleRecycler() {
        driverItemMenuRecyclerV.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
                false);
        driverItemMenuRecyclerV.setLayoutManager(linearLayoutManager);

        mDriverMenuTitleItemAdapter = new DriverMenuTitleItemAdapter(menuList);
        driverItemMenuRecyclerV.setAdapter(mDriverMenuTitleItemAdapter);
        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(0, 15, 15, 0);
        driverItemMenuRecyclerV.addItemDecoration(spaceItemDecoration);

        mDriverMenuTitleItemAdapter.setOnMenuItemClickListener(this);

    }


    @Override
    public void onMenuItemClick(int position) {
        Intent intent = new Intent(this.getActivity(), WebActivity.class);
        intent.putExtra("webUrl", menuList.get(position).getParam());
        intent.putExtra("title", menuList.get(position).getTitle());
        startActivity(intent);

    }

    /**
     * 写入展示数据
     */
    private void initItemRecyclerV() {
        driverItemRecyclerV.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        driverItemRecyclerV.setLayoutManager(linearLayoutManager);

        personRecommendAdapter = new PersonRecommendAdapter(mProductListBeen, true);
        driverItemRecyclerV.setAdapter(personRecommendAdapter);

        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(0, 15, 15, 15);
        driverItemRecyclerV.addItemDecoration(spaceItemDecoration);

    }

    private void showData() {
        if (getArguments() != null) {
            final DriveModel driveModel = getArguments().getParcelable("resultModel");
            if (driveModel != null) {

                mProductListBeen.clear();
                mProductListBeen.addAll(driveModel.ProductList);
                personRecommendAdapter.notifyDataSetChanged();

                urlList.clear();
                titleList.clear();

                for (RecommendModel.DataBean.BannerListBean bannerListBean : driveModel.BannerList) {
                    urlList.add(bannerListBean.getImage());
                    titleList.add(bannerListBean.getTitle());

                }

                banner.setImages(urlList).setBannerTitles(titleList).setDelayTime(3000).setIndicatorGravity
                        (BannerConfig.RIGHT)
                        .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                        .setImageLoader(new BannerImgLoader())
                        .setOnBannerListener(new OnBannerListener() {
                            @Override
                            public void OnBannerClick(int position) {
                                if (!"5".equals(driveModel.BannerList.get(position).getType())) {
                                    JumpHelper.startCommodityDetailActivity(getActivity(), Integer.parseInt(driveModel
                                            .BannerList.get
                                                    (position).getParam()));
                                } else {
                                    Intent intent = new Intent(getActivity(), WebActivity.class);
                                    intent.putExtra("webUrl", driveModel
                                            .BannerList.get(position).getParam());
                                    intent.putExtra("title", driveModel
                                            .BannerList.get(position).getTitle());
                                    startActivity(intent);
                                }
                            }
                        }).start();

                menuList.clear();
                menuList.addAll(driveModel.MenuList);
                Collections.sort(menuList);
            }
        }
    }

}

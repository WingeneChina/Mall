package cn.wingene.mallxm.display.home.firstMenu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxf.adapter.ImagePagerAdapter;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxf.ui.jd_refresh.JDRefreshLayout;
import cn.wingene.mallxf.util.SpaceItemDecoration;
import cn.wingene.mallxm.display.home.firstMenu.adapter.BrandProductAdapter;
import cn.wingene.mallxm.display.home.firstMenu.adapter.DaySpecialPriceAdapter;
import cn.wingene.mallxm.display.home.firstMenu.adapter.PerWeekProductAdapter;
import cn.wingene.mallxm.display.home.firstMenu.adapter.PersonRecommendAdapter;
import cn.wingene.mallxm.display.home.firstMenu.adapter.YouLikeProduceAdapter;

/**
 * Created by wangcq on 2017/8/8.
 * 推荐
 */

public class RecommendFragment extends MyBaseFragment implements ViewPager.OnPageChangeListener {
    private ViewPager mRollViewPager;
    private RecyclerView brandProductRecyclerV;
    private SimpleDraweeView perWeekBGV;
    private TextView perWeekNameV;
    private RecyclerView perWeekRecyclerV;
    private RecyclerView personRecommendRecyclerV;
    private RecyclerView daySpecialPRecyclerV;
    private RecyclerView youLikeRecyclerV;
    private JDRefreshLayout mJDRefreshLayout;

    private ImagePagerAdapter mImagePagerAdapter;
    private List<String> urlList = new ArrayList<>();

    int currentIndex = 0;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mRollViewPager.setCurrentItem(currentIndex + 1, false);

            mHandler.sendEmptyMessageDelayed(1, 5000);

        }
    };

    public static RecommendFragment newInstance(Bundle bundle) {
        RecommendFragment recommendFragment = new RecommendFragment();
        recommendFragment.setArguments(bundle);

        return recommendFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend_layout, container, false);
        initView(view);
        return view;
    }

    private void initView(View root) {
        mJDRefreshLayout = (JDRefreshLayout) root.findViewById(R.id.refreshLayout);
        mRollViewPager = (ViewPager) root.findViewById(R.id.rollPagerV);
        brandProductRecyclerV = (RecyclerView) root.findViewById(R.id.brandProductRecyclerV);
        perWeekBGV = (SimpleDraweeView) root.findViewById(R.id.perWeekBGV);
        perWeekNameV = (TextView) root.findViewById(R.id.perWeekNameV);
        perWeekRecyclerV = (RecyclerView) root.findViewById(R.id.perWeekRecyclerV);
        personRecommendRecyclerV = (RecyclerView) root.findViewById(R.id.personRecommendRecyclerV);
        daySpecialPRecyclerV = (RecyclerView) root.findViewById(R.id.daySpecialPRecyclerV);
        youLikeRecyclerV = (RecyclerView) root.findViewById(R.id.youLikeRecyclerV);

        initRollPager();
        initBrandProduct();
        initPerWeekProduct();
        initPersonRecommend();
        initDaySpecial();
        initYouLike();
    }

    /**
     * 猜你喜欢
     */
    private void initYouLike() {
        youLikeRecyclerV.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        youLikeRecyclerV.setLayoutManager(gridLayoutManager);

        YouLikeProduceAdapter youLikeProduceAdapter = new YouLikeProduceAdapter();
        youLikeRecyclerV.setAdapter(youLikeProduceAdapter);

        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(10, 10, 10, 10);
        youLikeRecyclerV.addItemDecoration(spaceItemDecoration);
    }

    /**
     * 天天特价
     */
    private void initDaySpecial() {
        daySpecialPRecyclerV.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager
                .VERTICAL, false);
        daySpecialPRecyclerV.setLayoutManager(linearLayoutManager);

        DaySpecialPriceAdapter daySpecialPriceAdatper = new DaySpecialPriceAdapter();
        daySpecialPRecyclerV.setAdapter(daySpecialPriceAdatper);

        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(10, 10, 10, 10);
        daySpecialPRecyclerV.addItemDecoration(spaceItemDecoration);

    }

    /**
     * 人气推荐
     */
    private void initPersonRecommend() {
        personRecommendRecyclerV.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        personRecommendRecyclerV.setLayoutManager(linearLayoutManager);

        PersonRecommendAdapter personRecommendAdapter = new PersonRecommendAdapter();
        personRecommendRecyclerV.setAdapter(personRecommendAdapter);

        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(10, 10, 10, 10);
        personRecommendRecyclerV.addItemDecoration(spaceItemDecoration);
    }

    /**
     * 每周新品
     */
    private void initPerWeekProduct() {
        perWeekRecyclerV.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager
                .HORIZONTAL, false);
        perWeekRecyclerV.setLayoutManager(linearLayoutManager);

        PerWeekProductAdapter perWeekProductAdapter = new PerWeekProductAdapter();
        perWeekRecyclerV.setAdapter(perWeekProductAdapter);

        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(10, 10, 10, 10);
        perWeekRecyclerV.addItemDecoration(spaceItemDecoration);
    }

    /**
     * 品牌大厂
     */
    private void initBrandProduct() {
        brandProductRecyclerV.setNestedScrollingEnabled(false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        brandProductRecyclerV.setLayoutManager(gridLayoutManager);

        BrandProductAdapter brandProductAdapter = new BrandProductAdapter();
        brandProductRecyclerV.setAdapter(brandProductAdapter);

        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(10, 10, 10, 10);
        brandProductRecyclerV.addItemDecoration(spaceItemDecoration);

    }

    private void initRollPager() {
        urlList.add("https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1502895397&di=98518077960d23213c0aa954ca4dc156" +
                "&imgtype=jpg&er=1&src=http%3A%2F%2Fatt.bbs.duowan.com%2Fforum%2F201504%2F22%2F1910368waa9k0z0z9y9c8a" +
                ".jpg");
        urlList.add("https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1502895322&di=228c7770fb082ec620cf1f373649b161" +
                "&imgtype=jpg&er=1&src=http%3A%2F%2Fimgcache.cjmx.com%2Ffilm%2F201608%2F20160830144736364.jpg");
        urlList.add("http://mpic.tiankong.com/ecc/3e3/ecc3e349338dbe58603cf270d9cd7c9c/640.jpg?x-oss-process=image" +
                "/resize,m_lfit,h_600,w_600/watermark,image_cXVhbmppbmcucG5n,t_90,g_ne,x_5,y_5");
        urlList.add("https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1502895397&di=98518077960d23213c0aa954ca4dc156" +
                "&imgtype=jpg&er=1&src=http%3A%2F%2Fatt.bbs.duowan.com%2Fforum%2F201504%2F22%2F1910368waa9k0z0z9y9c8a" +
                ".jpg");
        urlList.add("https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1502895322&di=228c7770fb082ec620cf1f373649b161" +
                "&imgtype=jpg&er=1&src=http%3A%2F%2Fimgcache.cjmx.com%2Ffilm%2F201608%2F20160830144736364.jpg");

        mImagePagerAdapter = new ImagePagerAdapter(urlList);
        mRollViewPager.setAdapter(mImagePagerAdapter);
        mRollViewPager.addOnPageChangeListener(this);
        mHandler.sendEmptyMessageDelayed(1, 1000);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == urlList.size() - 1) {
            currentIndex = 1;
        } else if (position == 0) {
            currentIndex = urlList.size() - 2;
        } else {
            currentIndex = position;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            mRollViewPager.setCurrentItem(currentIndex, false);
        }
    }
}

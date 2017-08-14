package cn.wingene.mallxm.display.home.firstMenu.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.wingene.mall.R;
import cn.wingene.mallxf.adapter.MailFragmentPagerAdapter;
import cn.wingene.mallxf.model.IndexModel;
import cn.wingene.mallxm.display.home.firstMenu.ProductListFragment;

public class ProductActivity extends AppCompatActivity {
    private ImageView backIcon;
    private ImageView searchV;
    private TabLayout productTitleGroupV;
    private ViewPager productPagers;
    private MailFragmentPagerAdapter mMailFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_layout);
        initViews();
    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        searchV = (ImageView) findViewById(R.id.searchV);
        productTitleGroupV = (TabLayout) findViewById(R.id.productTitleGroupV);
        productPagers = (ViewPager) findViewById(R.id.productPagers);

        List<IndexModel> fragmentList = new ArrayList<>();
        fragmentList.add(new IndexModel("T恤", ProductListFragment.newInstance(null)));
        fragmentList.add(new IndexModel("裤装", ProductListFragment.newInstance(null)));
        fragmentList.add(new IndexModel("内裤", ProductListFragment.newInstance(null)));
        fragmentList.add(new IndexModel("内衣", ProductListFragment.newInstance(null)));
        fragmentList.add(new IndexModel("家居服", ProductListFragment.newInstance(null)));
        fragmentList.add(new IndexModel("袜子", ProductListFragment.newInstance(null)));

        mMailFragmentPagerAdapter = new MailFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        productPagers.setAdapter(mMailFragmentPagerAdapter);
        productTitleGroupV.setupWithViewPager(productPagers, true);//同步
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIcon:
                onBackPressed();
                break;
            case R.id.searchV:

                break;
        }
    }
}

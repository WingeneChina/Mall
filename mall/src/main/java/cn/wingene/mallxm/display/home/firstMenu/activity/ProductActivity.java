package cn.wingene.mallxm.display.home.firstMenu.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
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
import cn.wingene.mallxm.display.home.firstMenu.IndoorFragment;
import cn.wingene.mallxm.display.home.firstMenu.ProductListFragment;
import cn.wingene.mallxm.display.home.firstMenu.data.ProductGroupModel;

import static cn.wingene.mallxm.display.home.FirstMenuFragment.PRODUCT_PARAMS;

/**
 * 居家、零食、美妆、服饰、洗护、户外、电竞、车用等弹出的页面：
 */
public class ProductActivity extends AppCompatActivity implements HttpListener<String> {
    private ImageView backIcon;
    private ImageView searchV;
    private TextView titleV;
    private TabLayout productTitleGroupV;
    private ViewPager productPagers;
    private MailFragmentPagerAdapter mMailFragmentPagerAdapter;
    private int orderBy = 0;//0 综合、2 金额降序 、1 金额升序
    private int mPagerIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_layout);
        initViews();
        requestData();
    }


    private void requestData() {
        if (getIntent() != null) {
            NoHttpRequest<BaseResponse> responseNoHttpRequest = new NoHttpRequest<>(BaseResponse.class);
            HashMap<String, Object> hasmapParams = new HashMap<>();
            hasmapParams.put("OrderBy", orderBy);
            hasmapParams.put("PageIndex", mPagerIndex);
            hasmapParams.put("Type", String.valueOf(getIntent().getStringExtra("type")));
            hasmapParams.put("ParentCode", getIntent().getStringExtra("key"));
            responseNoHttpRequest.request(this, HttpConstant.PRODUCT_GROUP, hasmapParams, 1, this, false,
                    null,
                    false, false);
        }
    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        searchV = (ImageView) findViewById(R.id.searchV);
        titleV = (TextView) findViewById(R.id.titleV);
        productTitleGroupV = (TabLayout) findViewById(R.id.productTitleGroupV);
        productPagers = (ViewPager) findViewById(R.id.productPagers);

        titleV.setText(getIntent().getStringExtra("title"));

    }

    private void initViewPager(ProductGroupModel productGroupModel) {
        List<IndexModel> fragmentList = new ArrayList<>();
        for (ProductGroupModel.DataBean dataBean : productGroupModel.getData()) {
            Bundle bundle = new Bundle();
            bundle.putString("typeCode", dataBean.getCode());
            fragmentList.add(new IndexModel(dataBean.getName(), IndoorFragment.newInstance(bundle)));
        }
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

    @Override
    public void onSucceed(int what, Response<String> response) {
        Log.e(this.getClass().getName(), "居家...返回数据 = " + response.get());
        try {
            GsonUtil<ProductGroupModel> gsonUtil = new GsonUtil<>(ProductGroupModel.class);
            ProductGroupModel productGroupModel = gsonUtil.fromJson(response.get());
            if (productGroupModel.getErr() == 0) {
                initViewPager(productGroupModel);

            } else {
                ToastUtil.show(productGroupModel.getMsg(), this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}

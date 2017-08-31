package cn.wingene.mallxm.display.home.firstMenu.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import cn.wingene.mall.R;
import cn.wingene.mallxf.util.ActivityUtils;
import cn.wingene.mallxm.display.home.firstMenu.ProductListFragment;

import static cn.wingene.mallxm.display.home.FirstMenuFragment.PRODUCT_PARAMS;

public class ProductSecondActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backIcon;
    private ImageView searchV;
    private FrameLayout contentV;
    private TextView titleV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_second);
        initViews();


    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        searchV = (ImageView) findViewById(R.id.searchV);
        contentV = (FrameLayout) findViewById(R.id.contentV);
        titleV = (TextView) findViewById(R.id.titleV);

        Bundle bundle = new Bundle();
        bundle.putString("typeCode",getIntent().getStringExtra("typeCode"));
        bundle.putString(PRODUCT_PARAMS,getIntent().getStringExtra("key"));
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), ProductListFragment.newInstance(bundle), R.id
                .contentV);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIcon:
                onBackPressed();

                break;

        }
    }
}

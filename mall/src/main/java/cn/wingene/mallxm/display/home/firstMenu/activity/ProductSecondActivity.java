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

/**
 * 点击子条目的界面，比如"品牌制造商直供"
 */
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
        bundle.putString("type", String.valueOf(getIntent().getStringExtra("type")));
        bundle.putString("key", String.valueOf(getIntent().getStringExtra("key")));
        bundle.putString("title", getIntent().getStringExtra("title"));
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), ProductListFragment.newInstance(bundle), R.id
                .contentV);

    }

    private void initEvent(){
        backIcon.setOnClickListener(this);
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

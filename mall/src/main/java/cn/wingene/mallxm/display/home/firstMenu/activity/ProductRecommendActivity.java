package cn.wingene.mallxm.display.home.firstMenu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.limecn.ghmall.R;

import cn.wingene.mallxf.util.ActivityUtils;
import cn.wingene.mallxm.display.home.firstMenu.IndoorFragment;
import cn.wingene.mallxm.display.home.firstMenu.ProductListFragment;

/**
 * 推荐、天天特价、新品
 */
public class ProductRecommendActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backIcon;
    private TextView titleV;
    private ImageView searchV;
    private FrameLayout contentV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_recommend);
        initViews();
        initEvent();
        initProductFragment();

    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIconV);
        titleV = (TextView) findViewById(R.id.titleV);
        searchV = (ImageView) findViewById(R.id.searchV);
        contentV = (FrameLayout) findViewById(R.id.contentV);

        titleV.setText(getIntent().getStringExtra("title"));
    }

    private void initEvent() {
        backIcon.setOnClickListener(this);
        titleV.setOnClickListener(this);
        searchV.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIconV:
                onBackPressed();
                break;
            case R.id.searchV:
                Intent intent = new Intent(this, SearchActivity.class);
                intent.putExtra("type", String.valueOf(getIntent().getStringExtra("type")));//综合搜索
                intent.putExtra("typeCode", String.valueOf(getIntent().getStringExtra("key")));
                startActivity(intent);
                break;
        }
    }


    /**
     * 商品界面
     */
    private void initProductFragment() {
        Bundle bundle = new Bundle();
        Log.e(this.getClass().getName(), "type = " + getIntent().getStringExtra("type"));
        bundle.putString("type", String.valueOf(getIntent().getStringExtra("type")));
        bundle.putString("key", String.valueOf(getIntent().getStringExtra("key")));
        bundle.putString("title", getIntent().getStringExtra("title"));
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), ProductListFragment.newInstance(bundle), R.id
                .contentV);
    }


}

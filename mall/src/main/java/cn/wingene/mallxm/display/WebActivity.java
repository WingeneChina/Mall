package cn.wingene.mallxm.display;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import cn.wingene.mall.R;

/**
 * web加载页面
 */
public class WebActivity extends AppCompatActivity {

    private ImageView backIcon;
    private TextView titleV;
    private ImageView searchV;
    private WebView driverWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initViews();
    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        titleV = (TextView) findViewById(R.id.titleV);
        searchV = (ImageView) findViewById(R.id.searchV);
        driverWebView = (WebView) findViewById(R.id.driverWebView);
    }


}

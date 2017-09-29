package cn.wingene.mallxm.display;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.limecn.ghmall.R;

/**
 * web加载页面
 */
public class WebActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView backIcon;
    private TextView titleV;
    private ImageView searchV;
    private WebView driverWebView;

    private String webUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initViews();
        titleV.setText(String.valueOf(getIntent().getStringExtra("title")));
        webUrl = getIntent().getStringExtra("webUrl");
        initWebView(webUrl);
        initEvent();

    }

    private void initEvent(){
        backIcon.setOnClickListener(this);

    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        titleV = (TextView) findViewById(R.id.titleV);
        searchV = (ImageView) findViewById(R.id.searchV);
        driverWebView = (WebView) findViewById(R.id.driverWebView);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backIcon:
                onBackPressed();
                break;
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(String webUrl) {
        WebSettings webSettings = driverWebView.getSettings();
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setUseWideViewPort(true); //将图片调整到适合webView的大小
        webSettings.setJavaScriptEnabled(true); //支持js
//        mWebView.addJavascriptInterface(new JSClient(), "js");
        driverWebView.setDrawingCacheEnabled(false);// 允许进行可视区域的截图
        driverWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, final int newProgress) {
                super.onProgressChanged(view, newProgress);

            }
        });

        driverWebView.setWebViewClient(new WebViewClient() {
                                      @Override
                                      public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                                         mWebView.addJavascriptInterface(new JSClient(), "js");
                                          view.loadUrl(url);
                                          return super.shouldOverrideUrlLoading(view, url);
                                      }

                                           @Override
                                           public void onPageFinished(WebView view, String url) {
                                               super.onPageFinished(view, url);
                                               if(view.canGoBack()){
                                                   backIcon.setVisibility(View.GONE);
                                               }else{
                                                   backIcon.setVisibility(View.VISIBLE);
                                               }
                                           }
                                       }
        );
        driverWebView.loadUrl(webUrl);  //加载页面
    }


}

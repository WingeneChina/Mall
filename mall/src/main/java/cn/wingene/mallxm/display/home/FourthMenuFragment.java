package cn.wingene.mallxm.display.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.wingene.mall.R;
import cn.wingene.mallxf.ui.MyBaseFragment;

/**
 * Created by wangcq on 2017/8/7.
 */

public class FourthMenuFragment extends MyBaseFragment implements View.OnClickListener {
    private String mLoadUrl = "http://api.52lime.cn/Home/JiaPei";

    private ImageView backIcon;
    private TextView titleV;
    private ImageView searchV;
    private SimpleDraweeView driverV;
    private WebView mWebView;

    public static FourthMenuFragment newInstance(Bundle args) {
        FourthMenuFragment fourthMenuFragment = new FourthMenuFragment();
        fourthMenuFragment.setArguments(args);

        return fourthMenuFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four_menu_layout, container, false);
        initViews(view);
        initWebView(mLoadUrl);

        return view;
    }

    private void initViews(View root) {
        backIcon = (ImageView) root.findViewById(R.id.backIcon);
        titleV = (TextView) root.findViewById(R.id.titleV);
        searchV = (ImageView) root.findViewById(R.id.searchV);
        driverV = (SimpleDraweeView) root.findViewById(R.id.driverV);
        mWebView = (WebView) root.findViewById(R.id.driverWebView);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(String webUrl) {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setUseWideViewPort(true); //将图片调整到适合webView的大小
        webSettings.setJavaScriptEnabled(true); //支持js
//        mWebView.addJavascriptInterface(new JSClient(), "js");
        mWebView.setDrawingCacheEnabled(false);// 允许进行可视区域的截图
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, final int newProgress) {
                super.onProgressChanged(view, newProgress);

            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
                                      @Override
                                      public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                                         mWebView.addJavascriptInterface(new JSClient(), "js");
                                          view.loadUrl(url);
                                          return super.shouldOverrideUrlLoading(view, url);
                                      }
                                  }
        );
        mWebView.loadUrl(webUrl);  //加载页面
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.driverV:

                break;
        }
    }
}

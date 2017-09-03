package cn.wingene.mallxm.display.home.secondMenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;

import cn.wingene.mall.R;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxm.display.home.secondMenu.data.SpecailDetailModel;
import junze.androidxf.tool.HtmlLoader;

import static cn.wingene.mallxf.http.HttpConstant.SPECIAL_DETAIL;

public class SpecialDetailActivity extends AppCompatActivity implements View.OnClickListener, HttpListener<String> {

    private ImageView backIcon;
    private TextView titleV;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_detail);
        initViews();
        initEvent();
        requestDetailData();
    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        titleV = (TextView) findViewById(R.id.titleV);
        mWebView = (WebView) findViewById(R.id.detailDetailWebV);

        titleV.setText(getIntent().getStringExtra("title"));

    }

    private void initEvent() {
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

    private void requestDetailData() {
        NoHttpRequest<BaseResponse> noHttpRequest = new NoHttpRequest<>(BaseResponse.class);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Id", getIntent().getIntExtra("detailId", 0));
        noHttpRequest.request(this, SPECIAL_DETAIL, hashMap, 1, this, false, null, false, true);

    }

    /**
     * 显示结果数据
     */
    private void showResultData(SpecailDetailModel specailDetailModel) {
        String webContent = specailDetailModel.getData().getContent();
        if (!TextUtils.isEmpty(webContent)) {
            HtmlLoader.loadWebViewByHtmlCode(this, mWebView, webContent, true);
        }

    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            GsonUtil<SpecailDetailModel> gsonUtil = new GsonUtil<>(SpecailDetailModel.class);
            SpecailDetailModel specailDetailModel = gsonUtil.fromJson(response.get());
            titleV.setText(specailDetailModel.getData().getTitle());
            showResultData(specailDetailModel);

        } catch (Exception e) {
            GsonUtil<BaseResponse> gsonUtil = new GsonUtil<>(BaseResponse.class);
            BaseResponse baseResponse = gsonUtil.fromJson(response.get());
            ToastUtil.show(baseResponse.msg, this);
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}

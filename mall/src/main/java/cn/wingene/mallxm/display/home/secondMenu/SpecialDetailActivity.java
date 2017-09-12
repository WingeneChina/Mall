package cn.wingene.mallxm.display.home.secondMenu;

import java.util.HashMap;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static cn.wingene.mallxf.http.HttpConstant.NEARBY_DETAIL;
import static cn.wingene.mallxf.http.HttpConstant.SPECIAL_DETAIL;
import com.yanzhenjie.nohttp.rest.Response;

import junze.androidxf.tool.HtmlLoader;

import cn.wingene.mall.R;
import cn.wingene.mall.util.MapUtil;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxm.display.home.secondMenu.data.SpecailDetailModel;

public class SpecialDetailActivity extends MyBaseActivity implements View.OnClickListener, HttpListener<String> {

    private ImageView backIcon;
    private TextView titleV;
    private WebView mWebView;
    private TextView addressTextV;
    private Button clickGoV;

    private SpecailDetailModel specailDetailModel;

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
        addressTextV = (TextView) findViewById(R.id.addressTextV);
        clickGoV = (Button) findViewById(R.id.clickGoV);

        titleV.setText(getIntent().getStringExtra("title"));

    }

    private void initEvent() {
        backIcon.setOnClickListener(this);
        clickGoV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIcon:
                onBackPressed();
                break;
            case R.id.clickGoV:
                if (specailDetailModel != null && specailDetailModel.getData() != null) {
                    try{
                        String region = specailDetailModel.getData().getRegion();
                        double lat = Double.parseDouble(specailDetailModel.getData().getLat());
                        double lnt = Double.parseDouble(specailDetailModel.getData().getLng());
                        //                    getAgent().startPoiActivity(region,lat,lnt);
//                        MapUtil.setUpBaiduAPPByMine(getAgent(),region,lat,lnt);
                        MapUtil.startNavActivity(getAgent(),region,lat,lnt);
//                        MapActivity.major.startForShowMap(getActivity(), new Location(lnt, lat, region));
                        //                    MapActivity.major.startForChoisePoi(getActivity(),100);
                        //                    getAgent().startNavActivity(region,lat,lnt);
                    }catch(Exception e){
                        e.printStackTrace();
                        showToast("位置信息不正确");
                    }
                } else {
                    Toast toast = Toast.makeText(this, "暂不支持", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
        }
    }

    private void requestDetailData() {
        NoHttpRequest<BaseResponse> noHttpRequest = new NoHttpRequest<>(BaseResponse.class);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Id", getIntent().getIntExtra("detailId", 0));
        if (getIntent().getStringExtra("type").equals("special")) {
            noHttpRequest.request(this, SPECIAL_DETAIL, hashMap, 1, this, false, null, false, true);

        } else if (getIntent().getStringExtra("type").equals("nearBy")) {
            noHttpRequest.request(this, NEARBY_DETAIL, hashMap, 2, this, false, null, false, true);

        }

    }

    /**
     * 显示结果数据
     */
    private void showResultData(SpecailDetailModel specailDetailModel) {
        String webContent = specailDetailModel.getData().getContent();
        if (!TextUtils.isEmpty(webContent)) {
            HtmlLoader.loadWebViewByHtmlCode(this, mWebView, webContent, true);
            addressTextV.setText(specailDetailModel.getData().getRegion());
        }
        try{
            String region = specailDetailModel.getData().getRegion();
            double lat = Double.parseDouble(specailDetailModel.getData().getLat());
            double lnt = Double.parseDouble(specailDetailModel.getData().getLng());
            clickGoV.setVisibility(View.VISIBLE);
        }catch(Exception e){
            e.printStackTrace();
            clickGoV.setVisibility(View.GONE);
        }


    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Log.e(this.getClass().getName(), response.get());
        try {
            GsonUtil<BaseResponse> gsonUtil1 = new GsonUtil<>(BaseResponse.class);
            BaseResponse baseResponse = gsonUtil1.fromJson(response.get());
            if (baseResponse.err == 0) {
                GsonUtil<SpecailDetailModel> gsonUtil = new GsonUtil<>(SpecailDetailModel.class);
                specailDetailModel = gsonUtil.fromJson(response.get());
                titleV.setText(specailDetailModel.getData().getTitle());
                showResultData(specailDetailModel);

            } else {
                ToastUtil.show(baseResponse.msg, this);
            }

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

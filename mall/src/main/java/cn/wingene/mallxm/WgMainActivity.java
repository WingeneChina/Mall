package cn.wingene.mallxm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.baidu.location.BDLocation;
import com.limecn.ghmall.R;
import com.yanzhenjie.nohttp.rest.Response;

import junze.java.tool.Trigger;
import junze.java.tool.Trigger.OnTriggerListener;

import junze.android.annotation.AutoRestore;
import junze.androidx.baidu.LocationHelper;
import junze.androidx.baidu.OnReceiveLoactionListener;
import junze.androidxf.core.Agent;
import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxf.util.VersionUtil;
import cn.wingene.mallxm.display.home.FirstMenuFragment;
import cn.wingene.mallxm.display.home.FiveMenuFragment;
import cn.wingene.mallxm.display.home.FourthMenuFragment;
import cn.wingene.mallxm.display.home.SecondMenuFragment;
import cn.wingene.mallxm.display.home.ThirdMenuFragment;
import cn.wingene.mallxm.display.home.dialog.VersionUpdateDialog;
import cn.wingene.mallxm.display.home.setting.data.VersionModel;

/**
 * Created by Wingene on 2017/9/5.
 */

public class WgMainActivity extends MyBaseActivity implements RadioGroup.OnCheckedChangeListener,
        HttpListener<String>, View.OnClickListener {
    public static Major major = new Major(WgMainActivity.class);
    @AutoRestore
    Integer mPostion;

    private RelativeLayout contentV;
    private RadioButton firstMenuV;
    private RadioButton secondMenuV;
    private RadioButton thirdMenuV;
    private RadioButton fourthMenuV;
    private RadioButton fiveMenuV;
    private RadioGroup menuGroup;
    private LinearLayout activity_main;

    private static final int VERSION_INFO_WHAT = 2;//版本信息
    private VersionUpdateDialog mDialog;
    /**
     * download url
     */
    private String mDownloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        initViews();
        initEvent();
        initFragmentParentId(R.id.contentMenuV);

        if (mPostion == null) {
            mPostion = major.parseParams(this);
        }
        check(mPostion);

        requestVersionInfo();
        LocationHelper.getInstance().start(new OnReceiveLoactionListener() {
            @Override
            public void onReceiveLocationListener(BDLocation bdLocation) {

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String info = intent.getStringExtra("PARAM");
        Integer position = info != null ? AKit.fromJson(info, Integer.class) : null;
        if (position != null) {
            check(position);
        }
    }

    private void initViews() {
        contentV = (RelativeLayout) findViewById(R.id.contentMenuV);
        firstMenuV = (RadioButton) findViewById(R.id.firstMenuV);
        secondMenuV = (RadioButton) findViewById(R.id.secondMenuV);
        thirdMenuV = (RadioButton) findViewById(R.id.thirdMenuV);
        fourthMenuV = (RadioButton) findViewById(R.id.fourthMenuV);
        fiveMenuV = (RadioButton) findViewById(R.id.fiveMenuV);
        menuGroup = (RadioGroup) findViewById(R.id.menuGroup);
        activity_main = (LinearLayout) findViewById(R.id.activity_main);
    }

    private void initEvent() {
        menuGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.firstMenuV:
                mPostion = 0;
                turntoFragment(FirstMenuFragment.class, null);
                break;
            case R.id.secondMenuV:
                mPostion = 1;
                turntoFragment(SecondMenuFragment.class, null);
                break;
            case R.id.thirdMenuV:
                mPostion = 2;
                turntoFragment(ThirdMenuFragment.class, null);
                break;
            case R.id.fourthMenuV:
                mPostion = 3;
                turntoFragment(FourthMenuFragment.class, null);
                break;
            case R.id.fiveMenuV:
                mPostion = 4;
                turntoFragment(FiveMenuFragment.class, null);
                break;
        }
    }

    public void check(Integer position) {
        if (position == null) {
            position = 0;
        }
        switch (position) {
            case 1:
                menuGroup.check(R.id.secondMenuV);
                break;
            case 2:
                menuGroup.check(R.id.thirdMenuV);
                break;
            case 3:
                menuGroup.check(R.id.fourthMenuV);
                break;
            case 4:
                menuGroup.check(R.id.fiveMenuV);
                break;
            case 0:
            default:
                menuGroup.check(R.id.firstMenuV);
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                downApp();
                mDialog.dismiss();
                break;
            case R.id.btn_cancel:
                mDialog.dismiss();
                break;
        }
    }

    /**
     * 下载App
     */
    private void downApp() {
//        DownLoadService.startDownloadFile(this, mDownloadUrl);

        Uri uri = Uri.parse(mDownloadUrl);
        Intent downloadIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(downloadIntent);

    }

    /**
     * 请求版本信息
     */
    private void requestVersionInfo() {//APP_VERSION
        NoHttpRequest<VersionModel> noHttpRequest = new NoHttpRequest(VersionModel.class);
        noHttpRequest.request(this.getActivity(), HttpConstant.APP_VERSION, null, VERSION_INFO_WHAT, this, false,
                null, true, false);

    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            switch (what) {
                case VERSION_INFO_WHAT:
                    GsonUtil<VersionModel> versonGson = new GsonUtil<>(VersionModel.class);
                    VersionModel versionModel = versonGson.fromJson(response.get());
                    if (versionModel.getData().getVersionCode() == VersionUtil.getAppVersionCode(this.getActivity())) {
//                        ToastUtil.show("当前已经是最新版本了", this.getActivity());

                    } else if (versionModel.getData().getVersionCode() > VersionUtil.getAppVersionCode(this
                            .getActivity())) {

                        mDialog = new VersionUpdateDialog(this
                                , VersionUtil.getAppVersionName(this)
                                , versionModel.getData().getVersionName()
                                , versionModel.getData().getReason());
                        mDownloadUrl = versionModel.getData().getDownloadUrl();

                        mDialog.showDialog();
                        mDialog.getmBtnOk().setOnClickListener(this);
                        mDialog.getmBtnCancel().setOnClickListener(this);

                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }

    Trigger mTrigger;

    @Override
    public void onBackPressed() {
        if (mTrigger == null) {
            mTrigger = new Trigger();
            mTrigger.setOnTriggerListener(new OnTriggerListener() {
                @Override
                public void onTrigger(Trigger trigger, int i, long l) {
                    if (i == 1) {
                        showToast("再按一次退出");
                    } else if (i > 1) {
                        finish();
                    }
                }

                @Override
                public void onReset(Trigger trigger, int i, long l) {

                }
            });
        }
        mTrigger.trigger(2000);

    }

    public static class Major extends Agent.Major {
        public Major(Class<? extends WgMainActivity> clazz) {
            super(clazz);
        }

        public void startForPosition(Context src, int position) {
            buildParams(src, position).startActivity();
        }

        public Integer parseParams(Activity target) {
            return parseParam(target, Integer.class);
        }
    }


}

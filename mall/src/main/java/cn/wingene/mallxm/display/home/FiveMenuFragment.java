package cn.wingene.mallxm.display.home;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yanzhenjie.nohttp.rest.Response;

import cn.wingene.mall.R;
import cn.wingene.mallxf.cacheData.UserData;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxf.util.VersionUtil;
import cn.wingene.mallxm.D;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.account.data.LoginModel;
import cn.wingene.mallxm.display.home.dialog.VersionUpdateDialog;
import cn.wingene.mallxm.display.home.fiveMenu.MyCollectionActivity;
import cn.wingene.mallxm.display.home.fiveMenu.UserInfoModel;
import cn.wingene.mallxm.display.home.setting.AboutAsActivity;
import cn.wingene.mallxm.display.home.setting.data.VersionModel;

import static cn.wingene.mallxf.http.HttpConstant.USER_INFO;

/**
 * Created by wangcq on 2017/8/7.
 * 个人菜单页面
 */

public class FiveMenuFragment extends MyBaseFragment implements View.OnClickListener, HttpListener<String> {
    private static final int USER_INFO_WHAT = 1;//用户信息
    private static final int VERSION_INFO_WHAT = 2;//版本信息


    private ImageView settingV;
    private SimpleDraweeView personHeadV;
    private TextView personNameV;
    private TextView yingMoneyV;
    private TextView youMoneyV;
    private LinearLayout findOrderV;
    private TextView orderReadyPayV;
    private TextView orderReadySendV;
    private TextView orderAlreadySendV;
    private TextView orderCompletedV;
    private TextView mallMoneyV;
    private TextView customerV;
    private TextView updateAddressV;
    private TextView luckyDesV;
    private RelativeLayout luckGroupV;
    private TextView oneLuckyDesV;
    private TextView versonInfoV;
    private TextView aboutAsV;
    private TextView versonConentV;
    private TextView myCollectionV;

    private VersionUpdateDialog mDialog;
    /**
     * download url
     */
    private String mDownloadUrl;

    public static FiveMenuFragment newInstance(Bundle args) {
        FiveMenuFragment fiveMenuFragment = new FiveMenuFragment();
        fiveMenuFragment.setArguments(args);

        return fiveMenuFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_five_menu_layout, container, false);
//        initViews(view);
        initClickViews(view);
        initEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();

    }

    private void requestData() {
        NoHttpRequest<UserInfoModel> noHttpRequest = new NoHttpRequest<>(UserInfoModel.class);
        noHttpRequest.request(this.getActivity(), USER_INFO, null, USER_INFO_WHAT, this, false, null, false, false);
    }

    private void initClickViews(View root) {
        settingV = (ImageView) root.findViewById(R.id.settingV);
        personHeadV = (SimpleDraweeView) root.findViewById(R.id.personHeadV);
        personNameV = (TextView) root.findViewById(R.id.personNameV);
        yingMoneyV = (TextView) root.findViewById(R.id.yingMoneyV);
        youMoneyV = (TextView) root.findViewById(R.id.youMoneyV);
        findOrderV = (LinearLayout) root.findViewById(R.id.findOrderV);
        orderReadyPayV = (TextView) root.findViewById(R.id.orderReadyPayV);
        orderReadySendV = (TextView) root.findViewById(R.id.orderReadySendV);
        orderAlreadySendV = (TextView) root.findViewById(R.id.orderAlreadySendV);
        orderCompletedV = (TextView) root.findViewById(R.id.orderCompletedV);
        mallMoneyV = (TextView) root.findViewById(R.id.mallMoneyV);
        customerV = (TextView) root.findViewById(R.id.customerV);
        updateAddressV = (TextView) root.findViewById(R.id.updateAddressV);
        luckGroupV = (RelativeLayout) root.findViewById(R.id.luckGroupV);
        luckyDesV = (TextView) root.findViewById(R.id.luckyDesV);
        oneLuckyDesV = (TextView) root.findViewById(R.id.oneLuckyDesV);
        versonInfoV = (TextView) root.findViewById(R.id.versonInfoV);
        aboutAsV = (TextView) root.findViewById(R.id.aboutAsV);
        versonConentV = (TextView) root.findViewById(R.id.versonConentV);
        versonConentV.setText(VersionUtil.getAppVersionName(getContext()));

        myCollectionV = (TextView) root.findViewById(R.id.myCollectionV);

    }


    private void initEvent() {
        personHeadV.setOnClickListener(this);
        settingV.setOnClickListener(this);
        findOrderV.setOnClickListener(this);
        orderReadyPayV.setOnClickListener(this);
        orderReadySendV.setOnClickListener(this);
        orderAlreadySendV.setOnClickListener(this);
        orderCompletedV.setOnClickListener(this);
        updateAddressV.setOnClickListener(this);

        aboutAsV.setOnClickListener(this);
        customerV.setOnClickListener(this);

        versonConentV.setOnClickListener(this);
        mallMoneyV.setOnClickListener(this);

        luckGroupV.setOnClickListener(this);

        myCollectionV.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personHeadV:
                Log.e(this.getClass().getName(), "点击头像");
                if (TextUtils.isEmpty(UserData.getverifiCode())) {
                    JumpHelper.startLoginActivity(this.getActivity());

                } else {
                    JumpHelper.startSettingActivity(this.getActivity());

                }

                break;
            case R.id.settingV:
                JumpHelper.startSettingActivity(this.getActivity());
                break;
            case R.id.findOrderV:
                JumpHelper.startOrderListActivity(getActivity(), -1);
                break;
            case R.id.orderReadyPayV:
                JumpHelper.startOrderListActivity(getActivity(), 0);

                break;
            case R.id.orderReadySendV:
                JumpHelper.startOrderListActivity(getActivity(), 1);


                break;
            case R.id.orderAlreadySendV:
                JumpHelper.startOrderListActivity(getActivity(), 3);


                break;
            case R.id.orderCompletedV:
                JumpHelper.startOrderListActivity(getActivity(), 4);


                break;
            case R.id.updateAddressV:
                JumpHelper.startAddressManagerActivity(getActivity());
                break;
            case R.id.aboutAsV:
                Intent aboutIntent = new Intent(this.getActivity(), AboutAsActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.customerV:
                agent().tryCallPhone("客服", D.CUSTOMER_PHONE);
                break;
            case R.id.versonConentV:
                requestVersionInfo();
                break;
            case R.id.mallMoneyV:
                JumpHelper.startAccountMoney(this.getActivity());
                break;
            case R.id.btn_ok:
                downApp();
                mDialog.dismiss();
                break;
            case R.id.btn_cancel:
                mDialog.dismiss();
                break;
            case R.id.luckGroupV:
                JumpHelper.startLuckyGame(this.getActivity());
                break;
            case R.id.myCollectionV:
                Intent intent = new Intent(this.getActivity(), MyCollectionActivity.class);
                startActivity(intent);
                break;
            default:

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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            personHeadV.setImageURI(UserData.getPersonHeadUrl());

        }
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            switch (what) {
                case USER_INFO_WHAT:
                    GsonUtil<UserInfoModel> gsonUtil = new GsonUtil<>(UserInfoModel.class);
                    UserInfoModel userInfoModel = gsonUtil.fromJson(response.get());
                    if (userInfoModel != null && userInfoModel.getErr() == 0 && userInfoModel.getData() != null) {
                        UserData.savePersonHeadUrl(userInfoModel.getData().getAvatar());//保存服务端提供的默认头像地址
                        personHeadV.setImageURI(userInfoModel.getData().getAvatar());
                        personNameV.setText(userInfoModel.getData().getNickname());
                        yingMoneyV.setText(String.valueOf("应币   " + userInfoModel.getData().getIntegral()));
                        youMoneyV.setText(String.valueOf("游币   " + userInfoModel.getData().getAmount()));
                    } else if (userInfoModel.getErr() != 0) {
                        personHeadV.setImageURI(userInfoModel.getData().getAvatar());
                        personNameV.setText("未登录");
                        yingMoneyV.setText("0");
                        youMoneyV.setText("0");
                    }
                    break;
                case VERSION_INFO_WHAT:
                    GsonUtil<VersionModel> versonGson = new GsonUtil<>(VersionModel.class);
                    VersionModel versionModel = versonGson.fromJson(response.get());

                    if (VersionUtil.getAppVersionCode(this.getActivity()) >= versionModel.getData().getVersionCode()) {
                        ToastUtil.show("当前已经是最新版本了", this.getActivity());

                    } else if (versionModel.getData().getVersionCode() > VersionUtil.getAppVersionCode(this
                            .getActivity())) {

                        mDialog = new VersionUpdateDialog(this.getContext()
                                , VersionUtil.getAppVersionName(this.getContext())
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
//            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}

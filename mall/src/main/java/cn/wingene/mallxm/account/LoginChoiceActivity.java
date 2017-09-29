package cn.wingene.mallxm.account;

import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.limecn.ghmall.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;
import com.yanzhenjie.nohttp.rest.Response;

import cn.wingene.mallxf.cacheData.UserData;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.account.data.LoginModel;

public class LoginChoiceActivity extends AppCompatActivity implements View.OnClickListener, HttpListener<String> {

    private ImageView cancelV;
    private TextView wxLogin;
    private TextView qqLogin;
    private TextView usePhoneLoginV;
    private TextView clickLoginV;

    private SHARE_MEDIA[] list = {SHARE_MEDIA.QQ, SHARE_MEDIA.SINA, SHARE_MEDIA.WEIXIN,
            SHARE_MEDIA.FACEBOOK, SHARE_MEDIA.TWITTER, SHARE_MEDIA.LINKEDIN, SHARE_MEDIA.DOUBAN, SHARE_MEDIA.RENREN,
            SHARE_MEDIA.KAKAO,
            SHARE_MEDIA.VKONTAKTE, SHARE_MEDIA.DROPBOX};
    private ProgressDialog dialog;


    /**
     * openId
     */
    private String openId = null;
    /**
     * screen_name
     */
    private String screen_name;

    /**
     * 头像
     */
    private String profile_image_url;
    /**
     * 微信
     */
    private String wechat_union_Id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_choice);
        initViews();
    }

    private void initViews() {
        cancelV = (ImageView) findViewById(R.id.cancelV);
        wxLogin = (TextView) findViewById(R.id.wxLogin);
        qqLogin = (TextView) findViewById(R.id.qqLogin);
        usePhoneLoginV = (TextView) findViewById(R.id.usePhoneLoginV);
        clickLoginV = (TextView) findViewById(R.id.clickLoginV);

        initEvent();
    }

    private void initEvent() {
        cancelV.setOnClickListener(this);
        wxLogin.setOnClickListener(this);
        qqLogin.setOnClickListener(this);
        usePhoneLoginV.setOnClickListener(this);
        clickLoginV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelV:
                onBackPressed();
                break;
            case R.id.wxLogin:
                UMShareAPI.get(this).doOauthVerify(this, list[2], authListener);

                break;
            case R.id.qqLogin:
                UMShareAPI.get(this).doOauthVerify(this, list[0], authListener);
                break;
            case R.id.usePhoneLoginV:
                JumpHelper.starRegisterFirstStepActivity(this);
                break;
            case R.id.clickLoginV:
//                JumpHelper.startLoginActivity(this);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    UMAuthListener loginAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            Log.e(this.getClass().getName(), "platFormInfo = " + map.toString());

            if (share_media == SHARE_MEDIA.WEIXIN) {
                openId = map.get("openid");
                profile_image_url = map.get("profile_image_url");
                screen_name = map.get("screen_name");
                wechat_union_Id = map.get("unionid");
                commitAccountInfo(1);

            } else if (share_media == SHARE_MEDIA.QQ) {
                openId = map.get("openid");
                profile_image_url = map.get("profile_image_url");
                screen_name = map.get("screen_name");

                commitAccountInfo(2);
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };


    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            SocializeUtils.safeShowDialog(dialog);
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param map 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> map) {
            SocializeUtils.safeCloseDialog(dialog);
            Log.e(this.getClass().getName(), "dataMap = " + map.toString());
//            Toast.makeText(LoginChoiceActivity.this, "成功了 dataMap= " + map.toString(), Toast.LENGTH_LONG).show();
            if (platform == SHARE_MEDIA.WEIXIN) {
                UMShareAPI.get(LoginChoiceActivity.this).getPlatformInfo(LoginChoiceActivity.this, SHARE_MEDIA
                        .WEIXIN, loginAuthListener);

            } else if (platform == SHARE_MEDIA.QQ) {
                UMShareAPI.get(LoginChoiceActivity.this).getPlatformInfo(LoginChoiceActivity.this, SHARE_MEDIA
                        .QQ, loginAuthListener);
            }

        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(LoginChoiceActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(LoginChoiceActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    /**
     * 提交信息
     * platForm 1微信。2QQ
     */
    private void commitAccountInfo(int platForm) {
        NoHttpRequest<BaseResponse> noHttpRequest = new NoHttpRequest<>(BaseResponse.class);
        HashMap<String, Object> hashMapParams = new HashMap<>();
        hashMapParams.put("Account", openId);
        hashMapParams.put("Nickname", screen_name);
        hashMapParams.put("Avatar", profile_image_url);
        hashMapParams.put("RegisterFrom", platForm);
        noHttpRequest.request(this, HttpConstant.ACCOUNT_LOGIN, hashMapParams, 1, this, true , null, false,false);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            switch (what) {
                case 1:
                    GsonUtil<LoginModel> gsonUtil = new GsonUtil(LoginModel.class);
                    LoginModel loginModel = gsonUtil.fromJson(response.get());
                    int resultCode = loginModel.getErr();
                    UserData.saveUserInfo(response.get());
                    UserData.saveUserId(loginModel.getData().getUserId());
                    UserData.saveVerifiCode(loginModel.getData().getVerifiCode());
                    UserData.savePersonHeadUrl(loginModel.getData().getAvatar());
                    if (resultCode == 0) {
                        Log.e(this.getClass().getName(), "登录成功");
                        finish();
                    }
                    break;

            }
        } catch (Exception e) {
            GsonUtil<BaseResponse> gsonUtil = new GsonUtil(BaseResponse.class);
            BaseResponse baseResponse = gsonUtil.fromJson(response.get());
            ToastUtil.show(baseResponse.msg, this);
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}

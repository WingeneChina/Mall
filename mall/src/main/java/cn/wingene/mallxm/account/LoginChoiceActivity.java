package cn.wingene.mallxm.account;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.limecn.ghmall.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;

import java.util.Map;

import cn.wingene.mallxm.JumpHelper;

public class LoginChoiceActivity extends AppCompatActivity implements View.OnClickListener {

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
                JumpHelper.starRegisterActivity(this);
                break;
            case R.id.clickLoginV:
                JumpHelper.startLoginActivity(this);

                break;

        }
    }

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
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(LoginChoiceActivity.this, "成功了 dataMap= " + data.toString(), Toast.LENGTH_LONG).show();
            if (platform == SHARE_MEDIA.WEIXIN) {


            } else if (platform == SHARE_MEDIA.QQ) {


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
}

package cn.wingene.mallxm.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;

import cn.wingene.mall.R;
import cn.wingene.mallxf.cacheData.UserData;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.account.data.LoginModel;

/**
 * 登陆界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, HttpListener<String> {

    private final int NORMAL_PWD_LOGIN = 1;

    private ImageView backIcon;
    private AutoCompleteTextView phoneNumber;
    private EditText password;
    private TextView forgetPwdV;
    private Button loginBtnV;
    private TextView toRegisterV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        phoneNumber = (AutoCompleteTextView) findViewById(R.id.phoneNumber);
        password = (EditText) findViewById(R.id.password);
        forgetPwdV = (TextView) findViewById(R.id.forgetPwdV);
        loginBtnV = (Button) findViewById(R.id.loginBtnV);
        toRegisterV = (TextView) findViewById(R.id.toRegisterV);

        initEvent();

    }

    private void initEvent() {
        forgetPwdV.setOnClickListener(this);
        loginBtnV.setOnClickListener(this);
        toRegisterV.setOnClickListener(this);
        backIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtnV:
                String userPhone = phoneNumber.getText().toString();
                String userPwd = password.getText().toString();

                if (TextUtils.isEmpty(userPhone)) {
                    phoneNumber.setError("请输入用户账号");
                    return;
                }
                if (TextUtils.isEmpty(userPwd)) {
                    password.setError("请输入密码");
                    return;
                }

                commitUserInfo(userPhone, userPwd);
                break;
            case R.id.forgetPwdV:

                break;
            case R.id.toRegisterV:
                JumpHelper.starRegisterFirstStepActivity(this);
                break;
            case R.id.backIcon:
                onBackPressed();
                break;
        }
    }

    /**
     * 提交用户信息
     */
    private void commitUserInfo(String userPhone, String userPwd) {
        NoHttpRequest<BaseResponse> noHttpRequest = new NoHttpRequest<>(BaseResponse.class);
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("Phone", userPhone);
        paramsMap.put("Pwd", userPwd);
        noHttpRequest.request(this, HttpConstant.LOGIN, paramsMap, NORMAL_PWD_LOGIN, this, false, "login", true, false);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            switch (what) {
                case NORMAL_PWD_LOGIN:
                    GsonUtil<LoginModel> gsonUtil = new GsonUtil(LoginModel.class);
                    LoginModel loginModel = gsonUtil.fromJson(response.get());
                    int resultCode = loginModel.getErr();
                    Log.e(this.getClass().getName(), "loginModel errorCode = " + resultCode);
                    UserData.saveUserInfo(response.get());
                    if (resultCode == 0) {
                        Log.e(this.getClass().getName(), "登陆成功");
                        JumpHelper.startMainActivity(this);
                        finish();
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
}

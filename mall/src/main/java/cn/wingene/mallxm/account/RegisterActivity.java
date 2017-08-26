package cn.wingene.mallxm.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;

import cn.wingene.mall.R;
import cn.wingene.mallxf.cacheData.UserData;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.account.data.LoginModel;

/**
 * 注册界面
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, HttpListener<String> {

    private ImageView backIcon;
    private AutoCompleteTextView phoneNumber;
    private EditText password;
    private TextView registerDesV;
    private Button registerBtnV;
    private String mUserPhone;
    private String mVerificode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserPhone = getIntent().getStringExtra("userPhone");
        mVerificode = getIntent().getStringExtra("verifiCode");

        setContentView(R.layout.activity_register);
        initViews();
    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        phoneNumber = (AutoCompleteTextView) findViewById(R.id.phoneNumber);
        password = (EditText) findViewById(R.id.password);
        registerDesV = (TextView) findViewById(R.id.registerDesV);
        registerBtnV = (Button) findViewById(R.id.registerBtnV);

        phoneNumber.setText(mUserPhone);
        initEvent();
    }

    private void initEvent() {
        backIcon.setOnClickListener(this);
        registerBtnV.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIcon:
                onBackPressed();

                break;
            case R.id.registerBtnV:
                String userPhone = phoneNumber.getText().toString();
                String userPwd = password.getText().toString();

                if (TextUtils.isEmpty(userPhone)) {
                    phoneNumber.setError("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(userPwd)) {
                    password.setError("请输入密码");
                    return;
                }

                if (userPwd.length() < 6) {
                    password.setError("密码长度不对");
                    return;
                }

                commitRegisterInfo(userPhone, userPwd, mVerificode);
                break;
        }
    }

    /**
     * 提交用户信息
     */
    private void commitRegisterInfo(String phone, String password, String SMSCode) {
        NoHttpRequest<BaseResponse> noHttpRequest = new NoHttpRequest<>(BaseResponse.class);
        HashMap<String, Object> params = new HashMap<>();
        params.put("Phone", phone);
        params.put("Pwd", password);
        params.put("SMSCode", SMSCode);
        noHttpRequest.request(this, HttpConstant.REGISTER, params, 1, this, false, "register", true, false);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            GsonUtil<LoginModel> gsonUtil = new GsonUtil<>(LoginModel.class);
            LoginModel loginModel = gsonUtil.fromJson(response.get());
            if (loginModel != null && loginModel.getErr() == 0) {
                UserData.saveUserInfo(response.get());
                ToastUtil.show("注册成功", this);
                JumpHelper.startMainActivity(this);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.show("注册出错", this);

        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}

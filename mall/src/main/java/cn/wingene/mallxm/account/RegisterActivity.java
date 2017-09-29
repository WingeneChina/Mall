package cn.wingene.mallxm.account;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.limecn.ghmall.R;
import com.yanzhenjie.nohttp.rest.Response;

import cn.wingene.mallxf.cacheData.UserData;
import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxf.nohttp.ToastUtil;
import cn.wingene.mallxm.account.data.LoginModel;
import cn.wingene.mallxm.display.home.setting.SettingActivity;
import cn.wingene.mallxm.display.home.setting.data.ChangeUserNameModel;

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
    private int type = 0;
    private TextView titleV;
    private CheckBox lookPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserPhone = getIntent().getStringExtra("userPhone");
        mVerificode = getIntent().getStringExtra("verifiCode");
        type = getIntent().getIntExtra("type", 0);

        setContentView(R.layout.activity_register);
        initViews();
    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        phoneNumber = (AutoCompleteTextView) findViewById(R.id.phoneNumber);
        password = (EditText) findViewById(R.id.password);
        registerDesV = (TextView) findViewById(R.id.registerDesV);
        registerBtnV = (Button) findViewById(R.id.registerBtnV);
        titleV = (TextView) findViewById(R.id.titleV);
        lookPwd = (CheckBox) findViewById(R.id.lookPwdV);
        String title = getIntent().getStringExtra("title");
        if(!TextUtils.isEmpty(title)) {
            titleV.setText(title);
            registerBtnV.setText(title);
        }
        phoneNumber.setText(mUserPhone);
        initEvent();
    }

    private void initEvent() {
        backIcon.setOnClickListener(this);
        registerBtnV.setOnClickListener(this);

        lookPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                } else {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);

                }
            }
        });
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
        params.put("SMSCode", SMSCode);

        if (type == 0) {//0注册 1登录 2、修改密码
            params.put("Pwd", password);
            noHttpRequest.accountInfoCommit(this, HttpConstant.REGISTER, params, 1, this, false, "register", true,
                    false);

        } else if (type == 2) {
            params.put("NewPwd", password);
            noHttpRequest.accountInfoCommit(this, HttpConstant.FORGET_PWD, params, 2, this, false, "updatePwd", true,
                    false);

        }

    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            GsonUtil<BaseResponse> baseResponseGsonUtil = new GsonUtil<>(BaseResponse.class);
            BaseResponse baseResponse = baseResponseGsonUtil.fromJson(response.get());
            if(baseResponse.err == 0) {
                switch (what) {
                    case 1://注册
                        GsonUtil<LoginModel> gsonUtil = new GsonUtil<>(LoginModel.class);
                        LoginModel loginModel = gsonUtil.fromJson(response.get());
                        if (loginModel != null && loginModel.getErr() == 0) {
                            UserData.saveUserInfo(response.get());
                            UserData.saveUserId(loginModel.getData().getUserId());
                            UserData.saveVerifiCode(loginModel.getData().getVerifiCode());
                            UserData.savePersonHeadUrl(loginModel.getData().getAvatar());
                            ToastUtil.show("注册成功", this);
//                        JumpHelper.startMainActivity(this);
                            finish();
                        } else {
                            ToastUtil.show(loginModel.getMsg(), this);

                        }
                        break;
                    case 2://修改密码
                        GsonUtil<ChangeUserNameModel> updatePwdGson = new GsonUtil<>(ChangeUserNameModel.class);
                        ChangeUserNameModel updatePwd = updatePwdGson.fromJson(response.get());
                        if (updatePwd.getErr() == 0) {
                            ToastUtil.show("修改密码成功", this);
                            Intent intent = new Intent(this, SettingActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        break;
                }
            }else{
                ToastUtil.show(baseResponse.msg,this);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}

package cn.wingene.mallxm.account;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
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
    private CheckBox lookPwdV;

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
        lookPwdV = (CheckBox) findViewById(R.id.lookPwdV);


        initEvent();

    }

    private void initEvent() {
        forgetPwdV.setOnClickListener(this);
        loginBtnV.setOnClickListener(this);
        toRegisterV.setOnClickListener(this);
        backIcon.setOnClickListener(this);

        lookPwdV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                Intent intent = new Intent(this, RegisterFirstStepActivity.class);
                intent.putExtra("title", "修改密码");
                intent.putExtra("type", 2);
                startActivityForResult(intent, 1);
                break;
            case R.id.toRegisterV:
                Intent intent1 = new Intent(this, RegisterFirstStepActivity.class);
                intent1.putExtra("title", "加入光合");
                intent1.putExtra("type", 0);
                startActivityForResult(intent1, 1);
//                JumpHelper.starRegisterFirstStepActivity(this);
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
        noHttpRequest.accountInfoCommit(this, HttpConstant.LOGIN, paramsMap, NORMAL_PWD_LOGIN, this, false, "login",
                true, false);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            switch (what) {
                case NORMAL_PWD_LOGIN:
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}

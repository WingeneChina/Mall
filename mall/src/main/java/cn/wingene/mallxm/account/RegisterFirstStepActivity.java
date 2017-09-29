package cn.wingene.mallxm.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.limecn.ghmall.R;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;

import cn.wingene.mallxf.http.HttpConstant;
import cn.wingene.mallxf.model.BaseResponse;
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.account.data.VerificoceModel;

public class RegisterFirstStepActivity extends AppCompatActivity implements View.OnClickListener, HttpListener<String> {

    private ImageView backIcon;
    private AutoCompleteTextView phoneNumber;
    private EditText verifiCodeV;
    private Button requestVerificodeV;
    private Button registerNextV;
    private TextView titleV;

    private int countTime = 60;
    private int alreadyTime = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int leftTime = countTime - alreadyTime;
            requestVerificodeV.setText("验证码已发送(" + leftTime + ")");
            alreadyTime++;
            if (leftTime > 0) {
                mHandler.sendEmptyMessageDelayed(1, 1000);
            } else {
                alreadyTime = 0;
                requestVerificodeV.setEnabled(true);
                requestVerificodeV.setText("点击发送验证码");
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_first_step);
        initViews();
        if (getIntent() != null) {
            String title = getIntent().getStringExtra("title");
            if(!TextUtils.isEmpty(title)) {
                titleV.setText(title);
            }
        }
    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        phoneNumber = (AutoCompleteTextView) findViewById(R.id.phoneNumber);
        verifiCodeV = (EditText) findViewById(R.id.verifiCodeV);
        requestVerificodeV = (Button) findViewById(R.id.requestVerificodeV);
        registerNextV = (Button) findViewById(R.id.registerNextV);
        titleV = (TextView) findViewById(R.id.titleV);

        initEvent();
    }

    private void initEvent() {
        requestVerificodeV.setOnClickListener(this);
        registerNextV.setOnClickListener(this);
        backIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerNextV:
                String userPhone = phoneNumber.getText().toString();
                String verifiCode = verifiCodeV.getText().toString();
                if (TextUtils.isEmpty(userPhone)) {
                    phoneNumber.setError("请先输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(verifiCode)) {
                    verifiCodeV.setError("请输入验证码");
                    return;
                }
//                JumpHelper.starRegisterActivity(this);
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("userPhone", userPhone);
                intent.putExtra("verifiCode", verifiCode);
                intent.putExtra("title", titleV.getText());
                intent.putExtra("type", getIntent().getIntExtra("type", 0));
                startActivityForResult(intent, 1);
                break;
            case R.id.requestVerificodeV:
                String userPhoneNumber = phoneNumber.getText().toString();
                if (!TextUtils.isEmpty(userPhoneNumber)) {
                    NoHttpRequest<VerificoceModel> noHttpRequest = new NoHttpRequest(VerificoceModel.class);
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("Phone", userPhoneNumber);
                    params.put("Type", getIntent().getIntExtra("type", 0));//0注册 1登录 2、修改密码
                    noHttpRequest.accountInfoCommit(this, HttpConstant.REQUEST_CODE, params, 1, this, false,
                            "verificode",
                            false, false);

                } else {
                    phoneNumber.setError("请先输入手机号码");
                }
                break;
            case R.id.backIcon:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        GsonUtil<VerificoceModel> gsonUtil = new GsonUtil(VerificoceModel.class);
        VerificoceModel verificoceModel = gsonUtil.fromJson(response.get());
        if (verificoceModel != null && verificoceModel.getErr() == 0) {
            Toast.makeText(this, "验证码已经发送", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(1, 1000);
            requestVerificodeV.setEnabled(false);
        } else {
            Toast toast = Toast.makeText(this, verificoceModel.getMsg(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
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

package cn.wingene.mallxm.display.home.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
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
import cn.wingene.mallxf.nohttp.GsonUtil;
import cn.wingene.mallxf.nohttp.HttpListener;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import cn.wingene.mallxm.account.data.LoginModel;
import cn.wingene.mallxm.display.home.setting.data.ChangeUserNameModel;

/**
 * 修改用户名字
 */
public class ChangeUserNameActivity extends AppCompatActivity implements View.OnClickListener, HttpListener<String> {

    private ImageView backIcon;
    private TextView titleV;
    private EditText userNameEditV;
    private Button commitMsgV;
    private LoginModel loginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_name);
        initViews();
        initEvent();
    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        titleV = (TextView) findViewById(R.id.titleV);
        userNameEditV = (EditText) findViewById(R.id.userNameEditV);
        commitMsgV = (Button) findViewById(R.id.commitMsgV);

        getUserMessage();
    }

    private void initEvent() {
        backIcon.setOnClickListener(this);
        commitMsgV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIcon:
                onBackPressed();
                break;
            case R.id.commitMsgV:
                commitMsg();
                break;
        }
    }

    private void getUserMessage() {
        try {
            String userInfoJson = UserData.getUserInfo();
            GsonUtil<LoginModel> gsonUtil = new GsonUtil<>(LoginModel.class);
            loginModel = gsonUtil.fromJson(userInfoJson);
            if (loginModel != null && loginModel.getData() != null) {
                userNameEditV.setText(loginModel.getData().getNickname());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断字符串长度
     *
     * @param de
     * @return
     */
    private int legthOf(String de) {
        int lenght = 0;
        for (int i = 0; i < de.length(); i++) {
            lenght += String.valueOf(de.charAt(i)).matches("[\u4E00-\u9FA5]") ? 2 : 1;
        }
        return lenght;
    }


    private void commitMsg() {
        //EDITOR_PERSON_INFO
        String userName = userNameEditV.getText().toString();
        if (legthOf(userName) < 4) {
            userNameEditV.setError("用户名必须大于4个字符");
            return;
        }
        NoHttpRequest<ChangeUserNameModel> noHttpRequest = new NoHttpRequest<>(ChangeUserNameModel.class);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Sex", 0);
        hashMap.put("Nickname", userName);
        hashMap.put("Region", "");
        hashMap.put("RegionCode", "");
        hashMap.put("Personality", "");
        noHttpRequest.request(this, HttpConstant.EDITOR_PERSON_INFO, hashMap, 1, this, false, null, true, false);

    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            GsonUtil<ChangeUserNameModel> changeUserNameModelGsonUtil = new GsonUtil<>(ChangeUserNameModel.class);
            ChangeUserNameModel changeUserNameModel = changeUserNameModelGsonUtil.fromJson(response.get());
            if (changeUserNameModel.getErr() == 0) {
                Toast toast = Toast.makeText(this, "修改用户名成功", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                if (loginModel != null) {
                    loginModel.getData().setNickname(userNameEditV.getText().toString());
                    UserData.saveUserInfo(changeUserNameModelGsonUtil.dataToJson(loginModel));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(int what, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}

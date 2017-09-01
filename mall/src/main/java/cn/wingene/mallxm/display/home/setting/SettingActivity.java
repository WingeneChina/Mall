package cn.wingene.mallxm.display.home.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.wingene.mall.R;
import cn.wingene.mallxf.util.VersionUtil;

/**
 * 设置界面
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backIcon;
    private TextView titleV;
    private TextView headDesV;
    private SimpleDraweeView headV;
    private TextView updateUserNameV;
    private TextView bindPhoneNumberV;
    private TextView versionInfoV;
    private TextView clearCacheV;
    private TextView goScoreV;
    private TextView suggestionV;
    private TextView aboutAsV;
    private TextView aboutClauseV;
    private Button loginOutV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
        initEvent();
    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        titleV = (TextView) findViewById(R.id.titleV);
        headDesV = (TextView) findViewById(R.id.headDesV);
        headV = (SimpleDraweeView) findViewById(R.id.headV);
        updateUserNameV = (TextView) findViewById(R.id.updateUserNameV);
        bindPhoneNumberV = (TextView) findViewById(R.id.bindPhoneNumberV);
        versionInfoV = (TextView) findViewById(R.id.versionInfoV);
        clearCacheV = (TextView) findViewById(R.id.clearCacheV);
        goScoreV = (TextView) findViewById(R.id.goScoreV);
        suggestionV = (TextView) findViewById(R.id.suggestionV);
        aboutAsV = (TextView) findViewById(R.id.aboutAsV);
        aboutClauseV = (TextView) findViewById(R.id.aboutClauseV);
        loginOutV = (Button) findViewById(R.id.loginOutV);

        versionInfoV.append(VersionUtil.getPackageInfo(this));
    }

    private void initEvent() {
        backIcon.setOnClickListener(this);
        headV.setOnClickListener(this);
        updateUserNameV.setOnClickListener(this);
        bindPhoneNumberV.setOnClickListener(this);
        clearCacheV.setOnClickListener(this);
        goScoreV.setOnClickListener(this);
        suggestionV.setOnClickListener(this);
        aboutAsV.setOnClickListener(this);
        aboutClauseV.setOnClickListener(this);
        loginOutV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIcon:
                onBackPressed();
                break;
            case R.id.headV:

                break;
            case R.id.updateUserNameV:

                break;
            case R.id.bindPhoneNumberV:

                break;
            case R.id.clearCacheV:

                break;
            case R.id.goScoreV:

                break;
            case R.id.suggestionV:

                break;
            case R.id.aboutAsV:

                break;
            case R.id.aboutClauseV:

                break;
            case R.id.loginOutV:

                break;
        }
    }
}

package cn.wingene.mallxm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.limecn.ghmall.R;

import cn.wingene.mallxf.cacheData.UserData;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxf.util.ActivityUtils;
import cn.wingene.mallxm.display.home.FirstMenuFragment;
import cn.wingene.mallxm.display.home.FiveMenuFragment;
import cn.wingene.mallxm.display.home.FourthMenuFragment;
import cn.wingene.mallxm.display.home.FourthMenusFragment;
import cn.wingene.mallxm.display.home.SecondMenuFragment;
import cn.wingene.mallxm.display.home.ThirdMenuFragment;

/**
 * 主界面
 */
public class MainActivity extends MyBaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RelativeLayout contentV;
    private RadioButton firstMenuV;
    private RadioButton secondMenuV;
    private RadioButton thirdMenuV;
    private RadioButton fourthMenuV;
    private RadioButton fiveMenuV;
    private RadioGroup menuGroup;
    private LinearLayout activity_main;
    private Fragment[] mFragments = new Fragment[5];
    private int hidePosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        initViews();
        initEvent();
        initFragments(savedInstanceState);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void initViews() {
        contentV = (RelativeLayout) findViewById(R.id.contentMenuV);
        firstMenuV = (RadioButton) findViewById(R.id.firstMenuV);
        secondMenuV = (RadioButton) findViewById(R.id.secondMenuV);
        thirdMenuV = (RadioButton) findViewById(R.id.thirdMenuV);
        fourthMenuV = (RadioButton) findViewById(R.id.fourthMenuV);
        fiveMenuV = (RadioButton) findViewById(R.id.fiveMenuV);
        menuGroup = (RadioGroup) findViewById(R.id.menuGroup);
        activity_main = (LinearLayout) findViewById(R.id.activity_main);
    }

    private void initEvent() {
        menuGroup.setOnCheckedChangeListener(this);
    }

    private void initFragments(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments[0] = FirstMenuFragment.newInstance(null);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragments[0], R
                    .id.contentMenuV);

        }
    }

    private void switchFragment(int showPosition, int hidePosition) {
        if (mFragments[showPosition] == null) {
            switch (showPosition) {
                case 0:
                    mFragments[0] = FirstMenuFragment.newInstance(null);
                    ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragments[0], R
                            .id.contentMenuV);
                    break;
                case 1:
                    mFragments[1] = SecondMenuFragment.newInstance(null);
                    ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragments[1], R
                            .id.contentMenuV);
                    break;
                case 2:
                    mFragments[2] = ThirdMenuFragment.newInstance(null);
                    ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragments[2], R
                            .id.contentMenuV);
                    break;
                case 3:
                    mFragments[3] = FourthMenusFragment.newInstance(null);//FourthMenuFragment.newInstance(null);
                    ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragments[3], R
                            .id.contentMenuV);
                    break;
                case 4:
                    mFragments[4] = FiveMenuFragment.newInstance(null);
                    ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragments[4], R
                            .id.contentMenuV);
                    break;
            }
            return;
        }
        ActivityUtils.showHideFragment(getSupportFragmentManager(), mFragments[showPosition],
                mFragments[hidePosition], mFragments);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getCheckedRadioButtonId()) {
            case R.id.firstMenuV:
                switchFragment(0, hidePosition);
                hidePosition = 0;
                break;
            case R.id.secondMenuV:
                switchFragment(1, hidePosition);
                hidePosition = 1;
                break;
            case R.id.thirdMenuV:
                switchFragment(2, hidePosition);
                hidePosition = 2;
                break;
            case R.id.fourthMenuV:
                switchFragment(3, hidePosition);
                hidePosition = 3;
                break;
            case R.id.fiveMenuV:
                switchFragment(4, hidePosition);
                hidePosition = 4;
                break;
        }
    }
}

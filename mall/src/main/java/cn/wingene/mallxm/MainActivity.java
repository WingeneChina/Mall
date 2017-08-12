package cn.wingene.mallxm;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.wingene.mall.R;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxf.ui.MyBaseFragment;
import cn.wingene.mallxf.util.ActivityUtils;
import cn.wingene.mallxm.display.home.FirstMenuFragment;
import cn.wingene.mallxm.display.home.FiveMenuFragment;
import cn.wingene.mallxm.display.home.FourthMenuFragment;
import cn.wingene.mallxm.display.home.SecondMenuFragment;
import cn.wingene.mallxm.display.home.ThirdMenuFragment;

/**
 * 主界面
 */
public class MainActivity extends MyBaseActivity implements RadioGroup.OnCheckedChangeListener {

    private FrameLayout contentV;
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

    private void initViews() {
        contentV = (FrameLayout) findViewById(R.id.contentV);
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
            mFragments[1] = SecondMenuFragment.newInstance(null);
            mFragments[2] = ThirdMenuFragment.newInstance(null);
            mFragments[3] = FourthMenuFragment.newInstance(null);
            mFragments[4] = FiveMenuFragment.newInstance(null);

            ActivityUtils.addMulFragmentToActivity(getSupportFragmentManager(), mFragments, 0, R.id.contentV);


        } else {
            mFragments[0] = getSupportFragmentManager().findFragmentByTag(FirstMenuFragment.class.getSimpleName());
            mFragments[1] = getSupportFragmentManager().findFragmentByTag(SecondMenuFragment.class.getSimpleName());
            mFragments[2] = getSupportFragmentManager().findFragmentByTag(ThirdMenuFragment.class.getSimpleName());
            mFragments[3] = getSupportFragmentManager().findFragmentByTag(FourthMenuFragment.class.getSimpleName());
            mFragments[4] = getSupportFragmentManager().findFragmentByTag(FiveMenuFragment.class.getSimpleName());
        }
    }

    private void switchFragment(int showPosition, int hidePosition) {
        if (mFragments[showPosition] == null) {
            initFragments(null);
        }
        ActivityUtils.showHideFragment(getSupportFragmentManager(), mFragments[showPosition], mFragments[hidePosition]);
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
            case R.id.fiveMenuV:
                switchFragment(3, hidePosition);
                hidePosition = 3;
                break;
        }
    }
}

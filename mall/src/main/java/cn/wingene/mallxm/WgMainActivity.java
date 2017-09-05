package cn.wingene.mallxm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import junze.android.annotation.AutoRestore;
import junze.androidxf.core.Agent;
import junze.androidxf.kit.AKit;

import cn.wingene.mall.R;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxm.display.home.FirstMenuFragment;
import cn.wingene.mallxm.display.home.FiveMenuFragment;
import cn.wingene.mallxm.display.home.FourthMenuFragment;
import cn.wingene.mallxm.display.home.SecondMenuFragment;
import cn.wingene.mallxm.display.home.ThirdMenuFragment;

/**
 * Created by Wingene on 2017/9/5.
 */

public class WgMainActivity extends MyBaseActivity implements RadioGroup.OnCheckedChangeListener {
    public static Major major = new Major(WgMainActivity.class);
    @AutoRestore
    Integer mPostion;

    private RelativeLayout contentV;
    private RadioButton firstMenuV;
    private RadioButton secondMenuV;
    private RadioButton thirdMenuV;
    private RadioButton fourthMenuV;
    private RadioButton fiveMenuV;
    private RadioGroup menuGroup;
    private LinearLayout activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        initViews();
        initEvent();
        initFragmentParentId(R.id.contentMenuV);

        if(mPostion == null){
           mPostion = major.parseParams(this);
        }
        check(mPostion);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String info = intent.getStringExtra("PARAM");
        Integer position = info != null? AKit.fromJson(info, Integer.class):null;
        if(position != null){
            check(position);
        }
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
        case R.id.firstMenuV:
            mPostion = 0;
            turntoFragment(FirstMenuFragment.class, null);
            break;
        case R.id.secondMenuV:
            mPostion = 1;
            turntoFragment(SecondMenuFragment.class, null);
            break;
        case R.id.thirdMenuV:
            mPostion = 2;
            turntoFragment(ThirdMenuFragment.class, null);
            break;
        case R.id.fourthMenuV:
            mPostion = 3;
            turntoFragment(FourthMenuFragment.class, null);
            break;
        case R.id.fiveMenuV:
            mPostion = 4;
            turntoFragment(FiveMenuFragment.class, null);
            break;
        }
    }

    public void check(Integer position) {
        if (position == null) {
            position = 0;
        }
        switch (position) {
        case 1:
            menuGroup.check(R.id.secondMenuV);
            break;
        case 2:
            menuGroup.check(R.id.thirdMenuV);
            break;
        case 3:
            menuGroup.check(R.id.fourthMenuV);
            break;
        case 4:
            menuGroup.check(R.id.fiveMenuV);
            break;
        case 0:
        default:
            menuGroup.check(R.id.firstMenuV);
            break;
        }
    }

    public static class Major extends Agent.Major {
        public Major(Class<? extends WgMainActivity> clazz) {
            super(clazz);
        }

        public void startForPosition(Context src, int position) {
            buildParams(src, position).startActivity();
        }

        public Integer parseParams(Activity target) {
            return parseParam(target, Integer.class);
        }
    }


    //    private void initFragments(Bundle savedInstanceState) {
    //        if (savedInstanceState == null) {
    //            mFragments[0] = FirstMenuFragment.newInstance(null);
    //            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragments[0], R
    //                    .id.contentMenuV);
    //
    //        }
    //    }
    //
    //    private void switchFragment(int showPosition, int hidePosition) {
    //        if (mFragments[showPosition] == null) {
    //            switch (showPosition) {
    //            case 0:
    //                mFragments[0] = FirstMenuFragment.newInstance(null);
    //                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragments[0], R
    //                        .id.contentMenuV);
    //                break;
    //            case 1:
    //                mFragments[1] = SecondMenuFragment.newInstance(null);
    //                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragments[1], R
    //                        .id.contentMenuV);
    //                break;
    //            case 2:
    //                mFragments[2] = ThirdMenuFragment.newInstance(null);
    //                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragments[2], R
    //                        .id.contentMenuV);
    //                break;
    //            case 3:
    //                mFragments[3] = FourthMenuFragment.newInstance(null);
    //                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragments[3], R
    //                        .id.contentMenuV);
    //                break;
    //            case 4:
    //                mFragments[4] = FiveMenuFragment.newInstance(null);
    //                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragments[4], R
    //                        .id.contentMenuV);
    //                break;
    //            }
    //            return;
    //        }
    //        ActivityUtils.showHideFragment(getSupportFragmentManager(), mFragments[showPosition],
    //                mFragments[hidePosition], mFragments);
    //    }
    //
    //    @Override
    //    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
    //        switch (group.getCheckedRadioButtonId()) {
    //        case R.id.firstMenuV:
    //            switchFragment(0, hidePosition);
    //            hidePosition = 0;
    //            break;
    //        case R.id.secondMenuV:
    //            switchFragment(1, hidePosition);
    //            hidePosition = 1;
    //            break;
    //        case R.id.thirdMenuV:
    //            switchFragment(2, hidePosition);
    //            hidePosition = 2;
    //            break;
    //        case R.id.fourthMenuV:
    //            switchFragment(3, hidePosition);
    //            hidePosition = 3;
    //            break;
    //        case R.id.fiveMenuV:
    //            switchFragment(4, hidePosition);
    //            hidePosition = 4;
    //            break;
    //        }
    //    }
}

/**
 *
 */
package cn.wingene.mallx.frame.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import junze.java.net.IHttpCacheElement.ICacheRequest;
import junze.java.net.IHttpElement.IRequest;
import junze.java.net.IHttpElement.IResponse;

import junze.android.annotation.AutoRestore.RestoreType;
import junze.android.util.AUtil;
import junze.android.util.Log;
import junze.androidxf.core.Agent;
import junze.androidxf.core.Agent.Major;
import junze.androidxf.core.AndroidFramer;
import junze.androidxf.kit.RestoreKit;
import junze.androidxf.ui.activity.BaseFragment;
import junze.androidxf.ui.activity.BaseFragmentActivity;

public abstract class BaseAppCompatActivity extends AppCompatActivity {
    public static final String  BASE_FRAGMENT_ACTIVITY_SAVE_INSTANCE= "junze.androidxf.ui.activity" +
            ".BaseFragmentActivity.BASE_FRAGMENT_ACTIVITY_SAVE_INSTANCE";

    private final Map<String, BaseFragment> mFragmentMap = new HashMap<String, BaseFragment>();
    private BaseFragment mCurrentFragment;
    private Integer mFragmentPatentId;
    private int intMajor = Major.MAJOR_DEFAULT;

    private final Agent mAgent = new Agent(this);
    private boolean mIsDestroyed = false;

    public Agent getAgent() {
        return mAgent;
    }

    public void setMajor(int major) {
        this.intMajor = major;
    }

    public int getMajor() {
        return intMajor;
    }

    public void startActivity(Class<? extends Activity> clazz) {
        getAgent().startActivity(clazz);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setMajor(Major.getMajor(this, savedInstanceState));
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            RestoreKit.restoreInstance(BaseFragmentActivity.class,this, RestoreType.ON_CREATE,savedInstanceState
                    .getString(BASE_FRAGMENT_ACTIVITY_SAVE_INSTANCE));
        }
        getAgent().addAgent(AndroidFramer.getInstance().getAgent());
        getAgent().onCreate(this, savedInstanceState);
    }

    @Override
    public void finish() {
        AUtil.hideSoftInputFromWindowIfActive(this);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        mIsDestroyed = true;
        getAgent().onDestroy(this);
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        getAgent().onRestart(this);
        super.onRestart();
    }

    @Override
    protected void onStart() {
        getAgent().onStart(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        getAgent().onStop(this);
        super.onStop();
    }

    @Override
    protected void onPause() {
        getAgent().onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        getAgent().onResume(this);
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Major.saveMajor(this, outState);
        outState.putString(BASE_FRAGMENT_ACTIVITY_SAVE_INSTANCE,RestoreKit.buildInstanceString(BaseFragmentActivity.class,
                this)); ;
        FragmentHelper.saveFragmentState(this, outState);
        getAgent().onSaveInstanceState(this, outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        RestoreKit.restoreInstance(BaseFragmentActivity.class,this, RestoreType.ON_RESTORE,savedInstanceState
                .getString(BASE_FRAGMENT_ACTIVITY_SAVE_INSTANCE));
        FragmentHelper.restoreFragmentState(this, savedInstanceState);
        getAgent().onRestoreInstanceState(this, savedInstanceState);
        //        super.onRestoreInstanceState(savedInstanceState);
        // viewID相同时，会引起不必要的异常。所以先注释掉，等待改进。
    }

    @Override
    protected void onNewIntent(Intent intent) {
        getAgent().onNewIntent(this, intent);
        super.onNewIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getAgent().onActivityResult(requestCode, resultCode, data);
    }

    public void showToast(String info) {
        getAgent().showToast(info);
    }

    public void showToast(String format, Object... args) {
        getAgent().showToast(format, args);
    }

    /**
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     *
     * @param e
     */
    public void showToast(Exception e) {
        getAgent().showToast(e);
    }

    public void exit() {
        getAgent().exit();
    }

    public void showWaitDialog(String message) {
        getAgent().showWaitDialog(message);
    }

    public void cancleWaitDialog() {
        getAgent().cancelWaitDialog();
    }

    public Activity getActivity() {
        return getAgent().getActivity();
    }

    public void showMsgDialog(String message) {
        getAgent().showMsgDialog(message);
    }

    public void showMsgDialog(String title, String message) {
        getAgent().showMsgDialog(title, message);
    }

    public void showMsgDialog(String title, String message, String text, OnClickListener l) {
        getAgent().showMsgDialog(title, message, text, l);
    }

    public ProgressDialog showProgressDialog(String title, String message, OnClickListener l) {
        return getAgent().showProgressDialog(title, message, l);
    }

    public void showConfirmDialog(String message, OnClickListener l) {
        getAgent().showConfirmDialog(message, l);
    }

    public void showConfirmDialog(String title, String message, OnClickListener l) {
        getAgent().showConfirmDialog(title, message, l);
    }

    public void showConfirmDialog(String title, String message, String btn1, OnClickListener l1, String btn2, OnClickListener l2) {
        getAgent().showConfirmDialog(title, message, btn1, l1, btn2, l2);
    }

    public void showConfirmDialog(String title, String message, boolean cancelOnTouchOutside, String btn1, OnClickListener l1, String btn2, OnClickListener l2) {
        getAgent().showConfirmDialog(title, message, cancelOnTouchOutside, btn1, l1, btn2, l2);
    }

    public void initFragmentParentId(int rid) {
        this.mFragmentPatentId = rid;
    }

    public BaseFragment getFragment(String key) {
        return (BaseFragment) getSupportFragmentManager().findFragmentByTag(key);
    }


    public <T extends Fragment> void turntoFragment(String key, Class<? extends BaseFragment> clazz, Bundle bundle) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        BaseFragment value = mFragmentMap.get(key);
        if (value == null) {
            boolean needAdd = false;
            value = (BaseFragment) getSupportFragmentManager().findFragmentByTag(key);
            if (value == null) {
                value = (BaseFragment) BaseFragment.instantiate(this, clazz.getName());
                needAdd = true;
            }
            if (value == null) {
                Log.e("BaseFargmentActivity", "BaseFragment.instantiate" + clazz + "失败了");
                return;
            }
            mFragmentMap.put(key, value);
            if (mFragmentPatentId == null) {
                throw new RuntimeException("please invoke initFragmentParentId before");
            } else {
                if (needAdd) {
                    ft.add(mFragmentPatentId, value, key);
                }
            }
        }
        if (mCurrentFragment != value) {
            if (mCurrentFragment != null) {
                ft.hide(mCurrentFragment);
            }
            ft.show(value);
            mCurrentFragment = value;
        }
        value.commitBundle(bundle);
        //        ft.commit(); http://blog.csdn.net/ranxiedao/article/details/8214936
        ft.commitAllowingStateLoss();
    }

    public void removeFragment(String tag) {
        BaseFragment fragment = mFragmentMap.remove(tag);
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.remove(fragment);
            ft.commitAllowingStateLoss();
        }
    }

    public void removeFragment(Class<? extends BaseFragment> clazz) {
        removeFragment(clazz.getName());
    }

    public <T extends Fragment> void turntoFragment(Class<? extends BaseFragment> clazz, Bundle bundle) {
        turntoFragment(clazz.getName(), clazz, bundle);
    }

    public <T extends Fragment> T getFragment(Class<? extends BaseFragment> clazz) {
        return (T) getFragment(clazz.getName());
    }

    public BaseFragment getCurrentFragment() {
        return mCurrentFragment;
    }

    /**
     * as {@link #isDestroyed()};
     *
     * @return
     */
    public boolean isDestroy() {
        return mIsDestroyed;
    }

    /**
     * 普通请求
     * 执行步骤
     * 1.显示等待框
     * 2.发送请求
     * 3.成功：updateUI; 失败: updateWhenException 或 自动处理（以Toast显示友好的提示信息）。
     * 4.关闭等待框
     *
     * @param msg
     * @param request
     * @param autoHandleException
     */
    public <T extends IResponse> void ask(String msg, boolean autoHandleException, IRequest<T> request) {
        getAgent().ask(msg, autoHandleException, request);
    }

    public <T extends IResponse> void ask(IRequest<T> request) {
        getAgent().ask(request);
    }

    /**
     * 缓存请求，
     * 程序执行步骤
     * 1.updateUIByCache
     * 2.发送请求
     * 3.成功: updateUI; 失败: updateWhenException
     *
     * @param req
     * @return
     */
    public <T extends IResponse> AsyncTask<String, Integer, T> cacheAsk(ICacheRequest<T> req) {
        return getAgent().cacheAsk(req);
    }

    public void showLeaveConfirmDialog() {
        getAgent().showConfirmDialog("提示", "确认离开当前界面   ", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }

    public static class FragmentHelper {
        Map<String, Boolean> visibles;

        public static void saveFragmentState(FragmentActivity activity, Bundle outState) {
            FragmentHelper states = new FragmentHelper();
            states.visibles = new HashMap<String, Boolean>();
            List<Fragment> list = activity.getSupportFragmentManager().getFragments();
            list = list != null ? list : new ArrayList<Fragment>();
            for (Fragment fragment : list) {
                states.visibles.put(fragment.getTag(), !fragment.isHidden());
            }
            outState.putString(FragmentHelper.class.getName(), new Gson().toJson(states));
        }

        public static void restoreFragmentState(FragmentActivity activity, Bundle savedInstanceState) {
            Map<String, Boolean> result = new Gson().fromJson(savedInstanceState.getString(FragmentHelper.class.getName()), FragmentHelper.class).visibles;
            if (result.isEmpty()) {
                return;
            }
            FragmentManager fm = activity.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            for (Entry<String, Boolean> entry : result.entrySet()) {
                if (entry.getValue()) {
                    ft.show(fm.findFragmentByTag(entry.getKey()));
                } else {
                    ft.hide(fm.findFragmentByTag(entry.getKey()));
                }
            }
            ft.commit();
        }
    }

    //    public @interface Restore {
    //        public int value() default 1;
    //    }

}

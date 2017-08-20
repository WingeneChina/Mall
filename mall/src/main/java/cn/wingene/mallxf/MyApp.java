package cn.wingene.mallxf;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;

import cn.wingene.mallxm.MainActivity;
import cn.wingene.mall.R;

import junze.java.util.FileUtil;

import junze.android.tool.CrashHander;
import junze.android.tool.CrashHander.CrashHandleAble;
import junze.android.tool.CrashHander.ErrorInfo;
import junze.android.util.AUtil;
import junze.androidx.baidu.LocationHelper;
import junze.androidxf.core.AndroidFrameConfiguration;
import junze.androidxf.core.AndroidFramer;
import junze.androidxf.core.AppState;
import junze.androidxf.db.DataBaseDao;
import junze.androidxf.db.DataBaseManager;
import junze.androidxf.db.IDataBaseManager.IDataBaseBean;
import junze.androidxf.http.BaseRequest;
import junze.androidxf.http.BaseRequest.AskLogModel;
import junze.androidxf.manager.AppManager;
import junze.androidxf.manager.FileManager;

/**
 * Created by Wingene on 2017/6/12.
 */

public class MyApp extends Application implements CrashHandleAble {
    public final static String TAG = "MyApp";
    public static WeakReference<Application> mApp = null;
    private final static AppState appState = AppState.TEST;


    @Override
    public void onCreate() {
        super.onCreate();
        mApp = new WeakReference<Application>(this);
        if (getAppstate() == AppState.RELEASE) {
            AUtil.setDevelopModel(false);
            junze.android.util.Log.setShowLog(false);
            BaseRequest.setDefaultAskLogModel(AskLogModel.NONE);
        }

        AndroidFrameConfiguration config = new AndroidFrameConfiguration.Builder(this)
                .setDataBaseHelper(MyDBHelper.class).build();
        AndroidFramer.getInstance().init(config);

        Thread.setDefaultUncaughtExceptionHandler(CrashHander.getInstance()
                .init(this.getResources().getString(R.string.app_name), this).setUncaughtHandler(this));

//        LocationHelper.getInstance().init(this);

        if (AppManager.isFirstStart()) {
            AppManager.saveVisionCode();
            new Thread(new Runnable() {

                @Override
                public void run() {
                    FileManager.getInstance().deleteDir(new File(FileManager.getInstance().getDownloadDir()));
                }
            }).start();
        }

        Fresco.initialize(this);
        NoHttp.initialize(InitializationConfig.newBuilder(this).networkExecutor(new OkHttpNetworkExecutor())
                .build());
        Logger.setDebug(true);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void handle(CrashHander hander, ErrorInfo info, Thread thread, Throwable throwable) {
        try {
            FileUtil.export(info.toString() + "\r\n", FileManager.getInstance().getCreashLogFile(), true);
//            final BugInfo buginfo = new BugInfo();
            //            buginfo.setVersion(info.getPackageName() + info.getVersionName());
            //            buginfo.setError(info.getErrorInfo());
            //            buginfo.setContext(info.getMobileInfo());
            //            if (!UserInfoManager.getInstance().isLogined()) {
            //                // 强制下线可能引发空指针的问题。无须提示。
            //                reStart();
            //                hander.killMyAppAfter(2);
            //                return;
            //            }
            if (throwable instanceof OutOfMemoryError) {
                hander.showToast(String.format("%s 内存溢出，即将退出！", info.getAppName()));
                reStart();
                hander.killMyAppAfter(2);

            } else {
                hander.showToast(String.format("%s 发生异常，即将退出！", info.getAppName()));
                try {
                    // 在andorid 5的系统下会自动kill，所以要先睡住。
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reStart() {
        // 以下用来捕获程序崩溃异常
        Intent intent = new Intent();
        // 参数1：包名，参数2：程序入口的activity
        intent.setClassName(getPackageName(), MainActivity.class.getName());
        PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 3000, restartIntent); // 1秒钟后重启应用
    }

    public static <Bean extends IDataBaseBean<ID>, ID> DataBaseDao<Bean, ID> getDao(Class<Bean> clazz) {
        return DataBaseManager.getInstance().getDao(clazz);
    }

    public static AppState getAppstate() {
        return AndroidFramer.getInstance().getAppState();
    }
}

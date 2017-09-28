package cn.wingene.mallxf;

import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.ref.WeakReference;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.baidu.location.BDLocation;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.limecn.ghmall.R;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;

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
import junze.androidxf.manager.PhoneManager;

import cn.wingene.mallxf.util.GlideImageLoader;
import cn.wingene.mallxm.WgMainActivity;

/**
 * Created by Wingene on 2017/6/12.
 */

public class MyApp extends Application implements CrashHandleAble {
    public final static String TAG = "MyApp";
    public static WeakReference<Application> mApp = null;
    private final static AppState appState = AppState.TEST;
    UncaughtExceptionHandler mDefaultHandler;
    private static BDLocation bdLocation;

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

        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(CrashHander.getInstance()
                .init(this.getResources().getString(R.string.app_name), this).setUncaughtHandler(this));

        LocationHelper.getInstance().init(this);
        PhoneManager.getInstance().init(this);
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
        initImagePicker();
        initUmengShareSDK();
    }

    private void initUmengShareSDK(){
//开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        PlatformConfig.setWeixin("wx9b67f644a38ad397", "95a382338595549c0784ac2d72dcf87d");
        PlatformConfig.setQQZone("101431884", "7091645a3fe4ac14ff5825db2c2528b3");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public static BDLocation getBdLocation() {
        return bdLocation;
    }

    public static void setBdLocation(BDLocation bdLocation) {
        MyApp.bdLocation = bdLocation;
    }
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(true);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);
        imagePicker.setShowCamera(true);
    }

    @Override
    public void handle(CrashHander hander, ErrorInfo info, Thread thread, Throwable throwable) {
        try {
            FileUtil.export(info.toString() + "\r\n", FileManager.getInstance().getCreashLogFile(), true);

            if (throwable instanceof OutOfMemoryError) {
                hander.showToast(String.format("%s 内存溢出，即将退出！", info.getAppName()));
            } else {
                hander.showToast(String.format("%s 发生异常，即将退出！", info.getAppName()));
                try {
                    // 在andorid 5的系统下会自动kill，所以要先睡住。
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mDefaultHandler.uncaughtException(thread,throwable);
    }

    private void reStart() {
        // 以下用来捕获程序崩溃异常
        Intent intent = new Intent();
        // 参数1：包名，参数2：程序入口的activity
        intent.setClassName(getPackageName(), WgMainActivity.class.getName());
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

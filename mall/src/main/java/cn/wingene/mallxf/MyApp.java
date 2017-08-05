package cn.wingene.mallxf;

import android.app.Application;

import junze.androidxf.core.AndroidFrameConfiguration;
import junze.androidxf.core.AndroidFramer;

/**
 * Created by Wingene on 2017/6/12.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidFrameConfiguration config = new AndroidFrameConfiguration.Builder(this)
                .setDataBaseHelper(MyDBHelper.class).build();
        AndroidFramer.getInstance().init(config);
    }
}

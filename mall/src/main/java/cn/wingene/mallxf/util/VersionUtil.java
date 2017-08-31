package cn.wingene.mallxf.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by wangcq on 2017/8/29.
 * 获取版本信息
 */

public class VersionUtil {

    public static String getPackageInfo(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager
                    .GET_CONFIGURATIONS);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

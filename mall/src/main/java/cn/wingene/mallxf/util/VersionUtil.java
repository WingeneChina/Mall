package cn.wingene.mallxf.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by wangcq on 2017/8/29.
 * 获取版本信息
 */

public class VersionUtil {

    public static PackageInfo getPackageInfo(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager
                    .GET_CONFIGURATIONS);
            return packageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getAppVersionCode(Context context) {
        String packageName = context.getPackageName();
        int versionCode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            versionCode = pi.versionCode;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            return versionCode;
        }
    }

    public static String getAppVersionName(Context context) {
        String packageName = context.getPackageName();
        String versionCode = "0";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            versionCode = pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return "v" + versionCode;
        }
    }
}

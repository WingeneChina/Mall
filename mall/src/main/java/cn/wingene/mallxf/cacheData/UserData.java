package cn.wingene.mallxf.cacheData;

import android.content.Context;

import cn.wingene.mallxf.MyApp;

/**
 * Created by wangcq on 2017/8/20.
 * 保存用户数据，需要在退出之后清空
 */

public class UserData {

    /**
     * 清除所有数据
     */
    public static void clearAllUserInfo() {
        MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).edit().clear().apply();
    }

    /**
     * 保存用户登陆ID
     *
     * @param userId
     */
    public static void saveUserId(int userId) {
        MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).edit().putInt("UserId", userId).apply();

    }

    /**
     * 获取登录用户ID
     *
     * @return
     */
    public static int getUserId() {
        return MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).getInt("UserId", 0);

    }

    /**
     * 保存用户推送key
     *
     * @param deviceKey
     */
    public static void saveDeviceKey(String deviceKey) {
        MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).edit().putString("DeviceKey",
                deviceKey).apply();

    }

    /**
     * 获取用户推送key
     *
     * @return
     */
    public static String getDeviceKey() {
        return MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).getString("DeviceKey", "");
    }

    /**
     * 保存用户验证码
     *
     * @param verifiCode
     */
    public static void saveVerifiCode(String verifiCode) {
        MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).edit().putString("VerifiCode",
                verifiCode).apply();

    }

    /**
     * 获取用户验证码
     *
     * @return
     */
    public static String getverifiCode() {
        return MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).getString("VerifiCode", "");

    }

    /**
     * 保存用户头像
     *
     * @param personHeadUrl
     */
    public static void savePersonHeadUrl(String personHeadUrl) {
        MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).edit().putString("personHeadUrl",
                personHeadUrl).apply();

    }

    /**
     * 获取用户验证码
     *
     * @return
     */
    public static String getPersonHeadUrl() {
        return MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).getString("personHeadUrl", null);

    }

    /**
     * 保存用户信息json串
     *
     * @param userJson
     */
    public static void saveUserInfo(String userJson) {
        MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).edit().putString("userInfo",
                userJson).apply();
    }

    /**
     * 获取用户json串
     *
     * @return
     */
    public static String getUserInfo() {
        return MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).getString("userInfo", null);

    }

    /**
     * 保存本地搜索记录
     *
     * @param localJson
     */
    public static void saveLocalData(String localJson) {
        MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).edit().putString("localSearch",
                localJson).apply();
    }

    /**
     * 获取保存测本地搜索记录
     *
     * @return
     */
    public static String getLocalData() {
        return MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).getString("localSearch", null);

    }

    /**
     * 保存用户密码
     */
    public static void saveUserPwd(String userPwd) {
        MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).edit().putString("password",
                userPwd).apply();
    }

    /**
     * 获取用户密码
     *
     * @return
     */
    public static String getUserPwd() {
        return MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).getString("password", null);

    }

}

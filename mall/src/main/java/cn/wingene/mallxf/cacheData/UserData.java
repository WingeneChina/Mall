package cn.wingene.mallxf.cacheData;

import android.content.Context;

import cn.wingene.mallxf.MyApp;

/**
 * Created by wangcq on 2017/8/20.
 * 保存用户数据，需要在退出之后清空
 */

public class UserData {

    /**
     * 保存用户登陆ID
     * @param userId
     */
    public static void saveUserId(int userId){
        MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).edit().putInt("UserId",userId).apply();

    }

    /**
     * 获取登录用户ID
     * @return
     */
    public static int getUserId(){
      return   MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).getInt("UserId",0);

    }

    /**
     * 保存用户推送key
     * @param deviceKey
     */
    public static void saveDeviceKey(String deviceKey){
        MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).edit().putString("DeviceKey",deviceKey).apply();

    }

    /**
     * 获取用户推送key
     * @return
     */
    public static String getDeviceKey(){
        return   MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).getString("DeviceKey",null);
    }

    /**
     * 保存用户验证码
     * @param verifiCode
     */
    public static void saveVerifiCode(String verifiCode){
        MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).edit().putString("VerifiCode",verifiCode).apply();

    }

    public static String getverifiCode(){
        return   MyApp.mApp.get().getSharedPreferences("userData", Context.MODE_PRIVATE).getString("VerifiCode",null);

    }
}

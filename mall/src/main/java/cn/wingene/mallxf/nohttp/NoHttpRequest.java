package cn.wingene.mallxf.nohttp;

import android.app.Activity;
import android.util.Log;

import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import cn.wingene.mallxf.cacheData.UserData;
import cn.wingene.mallxf.model.SignParams;
import cn.wingene.mallxf.util.MD5Util;

/**
 * nohttp 网络请求框架
 *
 * @author wangcq
 */
public class NoHttpRequest<T> {
    private Request<String> request;
    //    private JsonBeanRequest<T> request;
    private Class<T> mTClass;
//    private String[] params = new String[]{"UserId=" + UserData.getUserId(), "DeviceType=" + 2, "DeviceKey=" +
//            UserData.getDeviceKey(), "VerifiCode=" + UserData.getverifiCode(), "TimeStamp=" + System
//            .currentTimeMillis() / 1000};

    private List<String> allParmas = new ArrayList<>();

    public NoHttpRequest(Class<T> tClass) {
        this.mTClass = tClass;
        allParmas.clear();
        allParmas.add("UserId=" + UserData.getUserId());
        allParmas.add("DeviceType=" + 2);
        allParmas.add("DeviceKey=" + UserData.getDeviceKey());
        allParmas.add("VerifiCode=" + UserData.getverifiCode());
        allParmas.add("TimeStamp=" + System.currentTimeMillis() / 1000);
    }

    /**
     * 请求方法
     *
     * @param activity     用于显示进度对话框的上下文
     * @param url          请求地址
     * @param hashParams   请求字段和值
     * @param what         谁在请求
     * @param callback     请求结果回调
     * @param canCancel    是否能取消
     * @param cancelSign   如果canCancle 为true 则必须传取消标记
     * @param isShowDialog 是否显示加载进度对话框
     * @param isCache      是否使用缓存
     */
    public void request(Activity activity, String url, HashMap<String, Object> hashParams,
                        int what, HttpListener<String> callback, boolean canCancel, String cancelSign, boolean
                                isShowDialog, boolean
                                isCache) {
        Logger.e("url = " + url);
//        request = new JsonBeanRequest<>(url, RequestMethod.POST, mTClass);
        request = NoHttp.createStringRequest(url);
        if (hashParams != null) {
            request.add(hashParams);
        }
        request.setCancelSign(cancelSign);
        if (isCache) {
            request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        }
//        initPublicParams();
        mergeParams(hashParams);

        // 设置无证书https请求
        SSLContext sslContext = SSLContextUtil.getDefaultSLLContext();
        if (sslContext != null) {
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            request.setSSLSocketFactory(socketFactory);
            request.setHostnameVerifier(SSLContextUtil.HOSTNAME_VERIFIER);
        }
        if (request != null) {
            CallServer.getRequestInstance().add(activity, what,
                    request, callback, canCancel, isShowDialog);
        }
    }

    /**
     * 上传文件
     *
     * @param activity
     * @param what
     * @param url
     * @param uploadFile
     * @param listener
     */
    public void upLoadFile(Activity activity, int what, String url, FileBinary uploadFile, String platCode,
                           HttpListener<T>
                                   listener) {
        JsonBeanRequest<T> request = new JsonBeanRequest<>(url, RequestMethod.POST, mTClass);
        request.add("file", uploadFile);
        request.add("platCode", platCode);
        CallServer.getRequestInstance().add(activity, what, request, listener, false, false);
    }

    /**
     * @param what
     * @param url      下载地址
     * @param saveUrl  保存文件目录
     * @param fileName 文件名称
     * @param listener 下载监听
     */
    public void downLoadFile(int what, String url, String saveUrl, String fileName, DownloadListener listener) {
        DownloadRequest downloadRequest = NoHttp.createDownloadRequest(url, saveUrl, fileName, false, true);
        CallServer.getDownloadInstance().add(what, downloadRequest, listener);
    }

    /**
     * 取消所有
     */
    public void cancel() {
        CallServer.getRequestInstance().cancelAll();
    }

    /**
     * 取消某一个请求
     *
     * @param requestSign
     */
    public void cancelSpecialRequest(Object requestSign) {
        CallServer.getRequestInstance().cancelBySign(requestSign);
    }


    public static String signParams(String[] listParams) {
        StringBuffer signBuffer = new StringBuffer();
        Arrays.sort(listParams);
        for (int i = 0; i < listParams.length; i++) {
            signBuffer.append(listParams[i]);
            signBuffer.append("&");

        }
        signBuffer.append(SignParams.signKey);
        Log.e("", "签名参数 = " + signBuffer.toString());
        String sign = MD5Util.getMD5String(signBuffer.toString()).toUpperCase();
        Log.e("", "输出签名 = " + sign);
        return sign;
    }

    /**
     * 合并公共参数并且签名
     *
     * @param params
     */
    private void mergeParams(HashMap<String, Object> params) {
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String param = entry.getKey() + "=" + entry.getValue();
                allParmas.add(param);
            }
        }
        String[] signParamArray = new String[allParmas.size()];
        for (int i = 0; i < signParamArray.length; i++) {
            signParamArray[i] = allParmas.get(i);
        }

        if (request != null) {
            request.add("Sign", signParams(signParamArray));
        }
    }

    /**
     * 初始化公共参数
     */
    private void initPublicParams() {
        if (request != null) {
            request.add("UserId", UserData.getUserId());//如果用户已经登陆需要传入值
            request.add("DeviceType", 2);//0 网页，1/ios  2/安卓
            request.add("DeviceKey", UserData.getDeviceKey());//推送key
            request.add("VerifiCode", UserData.getverifiCode());
            request.add("TimeStamp", System.currentTimeMillis() / 1000);
//            request.add("Sign", signParams(params));
        }
    }
}

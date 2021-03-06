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
import java.util.Map.Entry;

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
    private Class<T> mTClass;

    private List<String> allParmas = new ArrayList<>();

    public NoHttpRequest(Class<T> tClass) {
        this.mTClass = tClass;
        allParmas.clear();
    }

    /**
     * 登录相关，公共参数不一致，影响参数签名
     *
     * @param activity
     * @param url
     * @param hashParams
     * @param what
     * @param callback
     * @param canCancel
     * @param cancelSign
     * @param isShowDialog
     * @param isCache
     */
    public void accountInfoCommit(Activity activity, String url, HashMap<String, Object> hashParams,
                                  int what, HttpListener<String> callback, boolean canCancel, String cancelSign, boolean
                                          isShowDialog, boolean
                                          isCache) {
        Logger.e("url = " + url);
        request = NoHttp.createStringRequest(url, RequestMethod.POST);
        request.setCancelSign(cancelSign);
        if (isCache) {
            request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        }
        loginMergeParams(hashParams);

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
        request = NoHttp.createStringRequest(url, RequestMethod.POST);
        request.setCancelSign(cancelSign);
        mergeParams(hashParams);

        if (isCache) {
            request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
            if (hashParams != null) {
                request.setCacheKey(url + hashParams.toString());
            }
        }

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
     * @param listener
     */
    public void upLoadFile(Activity activity, int what, String url, HashMap<String, Object>
            hashMap, HttpListener<String> listener) {

        request = NoHttp.createStringRequest(url, RequestMethod.POST);

        mergeParams(hashMap);
//        request.add(hashMap);
        // 设置无证书https请求
        SSLContext sslContext = SSLContextUtil.getDefaultSLLContext();
        if (sslContext != null) {
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            request.setSSLSocketFactory(socketFactory);
            request.setHostnameVerifier(SSLContextUtil.HOSTNAME_VERIFIER);
        }
        if (request != null) {
            CallServer.getRequestInstance().add(activity, what,
                    request, listener, false, true);
        }

//        request = NoHttp.createStringRequest(url, RequestMethod.POST);
//        request.add(hashMap);
//        request.add("ByteAvatar", uploadFile);
//        CallServer.getRequestInstance().add(activity, what, request, listener, false, false);
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
        String sign = MD5Util.getMD5String(signBuffer.toString()).toUpperCase();
        return sign;
    }

    /**
     * 合并公共参数并且签名
     *
     * @param params
     */
    private void mergeParams(HashMap<String, Object> params) {
        params = params != null ? params : new HashMap<String, Object>();
        params.put("UserId", UserData.getUserId());//如果用户已经登陆需要传入值
        params.put("DeviceType", 2);//0 网页，1/ios  2/安卓
        params.put("DeviceKey", UserData.getDeviceKey());//推送key
        params.put("VerifiCode", UserData.getverifiCode());
        params.put("TimeStamp", System.currentTimeMillis() / 1000);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String param = entry.getKey() + "=" + entry.getValue();
            allParmas.add(param);
        }
        String[] signParamArray = new String[allParmas.size()];
        for (int i = 0; i < signParamArray.length; i++) {
            signParamArray[i] = allParmas.get(i);
        }

        if (request != null) {
            request.add(params);
            request.add("Sign", signParams(signParamArray));
        }
    }

    /**
     * 登录流程公共参数设置
     *
     * @param params
     */
    private void loginMergeParams(HashMap<String, Object> params) {
        params = params != null ? params : new HashMap<String, Object>();
        params.put("DeviceType", 2);//0 网页，1/ios  2/安卓
        params.put("DeviceKey", UserData.getDeviceKey());//推送key
        params.put("TimeStamp", System.currentTimeMillis() / 1000);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String param = entry.getKey() + "=" + entry.getValue();
            allParmas.add(param);
        }
        String[] signParamArray = new String[allParmas.size()];
        for (int i = 0; i < signParamArray.length; i++) {
            signParamArray[i] = allParmas.get(i);
        }

        if (request != null) {
            request.add(params);
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

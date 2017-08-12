package cn.wingene.mallxf.nohttp;

import android.app.Activity;

import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.rest.CacheMode;

import java.util.HashMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/**
 * nohttp 网络请求框架
 *
 * @author wangcq
 */
public class NoHttpRequest<T> {

    private JsonBeanRequest<T> request;
    private Class<T> mTClass;

    public NoHttpRequest(Class<T> tClass) {
        this.mTClass = tClass;
    }

    /**
     * 请求方法
     *
     * @param activity     用于显示进度对话框的上下文
     * @param url          请求地址
     * @param hashParams   请求字段和值
     * @param what         谁在请求
     * @param callback     请求结果回调
     * @param canCancle    是否能取消
     * @param cancleSign   如果canCancle 为true 则必须传取消标记
     * @param isShowDislog 是否显示加载进度对话框
     * @param isCache      是否使用缓存
     */
    public void request(Activity activity, String url, HashMap<String, Object> hashParams,
                        int what, HttpListener<T> callback, boolean canCancle, String cancleSign, boolean
                                isShowDislog, boolean
                                isCache) {
        Logger.e("url = " + url);
        request = new JsonBeanRequest<>(url, RequestMethod.POST, mTClass);
        request.setCancelSign(cancleSign);
        if (isCache) {
            request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        }
        request.add(hashParams);

        // 设置无证书https请求
        SSLContext sslContext = SSLContextUtil.getDefaultSLLContext();
        if (sslContext != null) {
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            request.setSSLSocketFactory(socketFactory);
            request.setHostnameVerifier(SSLContextUtil.HOSTNAME_VERIFIER);
        }
        if (request != null) {
            CallServer.getRequestInstance().add(activity, what,
                    request, callback, canCancle, isShowDislog);
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

        request = new JsonBeanRequest<>(url, RequestMethod.POST, mTClass);

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

    public void cancel() {
        CallServer.getRequestInstance().cancelAll();
    }

    public void cancelSpecialRequest(Object requestSign) {
        CallServer.getRequestInstance().cancelBySign(requestSign);
    }
}

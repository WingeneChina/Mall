package cn.wingene.mallxf.nohttp;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.ServerError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

public class HttpResponseListener<T> implements OnResponseListener<T> {

    /**
     * Dialog.
     */
    private WaitDialog mWaitDialog;

    private Request<?> mRequest;

    /**
     * 结果回调.
     */
    private HttpListener<T> callback;

    /**
     * 是否显示dialog.
     */
    private boolean isLoading;
    private Context context;

    /**
     * @param context      context用来实例化dialog.
     * @param request      请求对象.
     * @param httpCallback 回调对象.
     * @param canCancel    是否允许用户取消请求.
     * @param isLoading    是否显示dialog.
     */
    public HttpResponseListener(Context context, Request<?> request, HttpListener<T> httpCallback, boolean canCancel,
                                boolean isLoading) {
        this.mRequest = request;
        this.context = context;

        if (context != null && isLoading) {
            mWaitDialog = new WaitDialog(context);
            mWaitDialog.setCancelable(canCancel);
            mWaitDialog.setCanceledOnTouchOutside(false);
            mWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mRequest.cancel();
                }
            });
        }
        this.callback = httpCallback;
        this.isLoading = isLoading;
    }

    /**
     * 开始请求, 这里显示一个dialog.
     */
    @Override
    public void onStart(int what) {
        if (isLoading && mWaitDialog != null && !mWaitDialog.isShowing()) {
            mWaitDialog.show();
        }

    }

    /**
     * 结束请求, 这里关闭dialog.
     */
    @Override
    public void onFinish(int what) {
        if (isLoading && mWaitDialog != null && mWaitDialog.isShowing())
            mWaitDialog.dismiss();

    }

    /**
     * 成功回调.
     */
    @Override
    public void onSucceed(int what, Response<T> response) {
        com.orhanobut.logger.Logger.e("请求结果 = " + response.get().toString());
        if (callback != null)
            callback.onSucceed(what, response);
    }

    @Override
    public void onFailed(int what, Response<T> response) {
        Exception exception = response.getException();
        if (exception instanceof ServerError) {// 服务器错误
            Log.e(this.getClass().getName(), "服务器发生错误");
            ToastUtil.show("服务器发生错误", context);


        } else if (exception instanceof NetworkError) {// 网络不好
            Log.e(this.getClass().getName(), "请检查网络");
            ToastUtil.show("请检查网络", context);


        } else if (exception instanceof TimeoutError) {// 请求超时
            Log.e(this.getClass().getName(), "请求超时，网络不好或者服务器不稳定");
            ToastUtil.show("请求超时，网络不好或者服务器不稳定", context);


        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            Log.e(this.getClass().getName(), "未发现指定服务器");
            ToastUtil.show("未发现指定服务器", context);

        } else if (exception instanceof URLError) {// URL是错的
            ToastUtil.show("URL错误", context);
            Log.e(this.getClass().getName(), "URL错误");

        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            Log.e(this.getClass().getName(), "没有发现缓存");
            ToastUtil.show("没有发现缓存", context);


        } else {
            Log.e(this.getClass().getName(), "未知错误");
            ToastUtil.show("未知错误", context);

        }
        if (callback != null)
            callback.onFailed(what, response.getTag(), exception, response.getHeaders().getResponseCode(), response
                    .getNetworkMillis());
    }


}

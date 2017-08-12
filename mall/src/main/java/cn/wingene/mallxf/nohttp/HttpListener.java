package cn.wingene.mallxf.nohttp;

import com.yanzhenjie.nohttp.rest.Response;

public interface HttpListener<T> {

	void onSucceed(int what, Response<T> response);

	void onFailed(int what, Object tag, Exception exception,
                  int responseCode, long networkMillis);

}

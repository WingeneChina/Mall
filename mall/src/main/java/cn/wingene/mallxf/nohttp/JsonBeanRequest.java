package cn.wingene.mallxf.nohttp;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;

/**
 * Created by wangcq on 2017/8/7.
 * json 结果bean
 */

public class JsonBeanRequest<T> extends RestRequest<T> {

    private Class<T> mClass;

    public JsonBeanRequest(String url, Class<T> tClass) {
        this(url, RequestMethod.GET, tClass);
    }

    public JsonBeanRequest(String url, RequestMethod requestMethod, Class<T> tClass) {
        super(url, requestMethod);
        this.mClass = tClass;
    }

    /**
     * 关于parseResponse()方法的说明
     * 这里要把byte[] body解析成Sting，一般我们用String s = new String(body);解析。
     * 但是服务器发送的数据有编码，所以我们要分析header中的contentType的编码是utf-8还是gbk或者其它，为了避免每个request都要解析String，作者在StringRequest
     * 写了一个静态方法统一解析，有疑问的人看点击进去看源码。
     * 拿到String之后，利用Gson把数据解析成对象。当然这里可以直接解析成JavaBean
     *
     * @param responseHeaders
     * @param responseBody
     * @return
     * @throws Exception
     */
    @Override
    public T parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        String resultStr = StringRequest.parseResponseString(responseHeaders, responseBody);
        GsonUtil<T> gsonUtil = new GsonUtil<>(mClass);
        return gsonUtil.fromJson(resultStr);
    }
}

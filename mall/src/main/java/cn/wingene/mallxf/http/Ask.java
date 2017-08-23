package cn.wingene.mallxf.http;

import java.util.HashMap;
import java.util.Map;

import cn.wingene.mall.RequestArgUtil;
import cn.wingene.mallxf.cacheData.UserData;
import cn.wingene.mallxf.nohttp.NoHttpRequest;
import com.google.gson.annotations.SerializedName;

import junze.java.net.IHttpElement.IResponse;

import junze.androidxf.http.BaseCacheParamsRequest;
import junze.androidxf.http.BaseParamsRequest;
import junze.androidxf.http.BaseResponse;
import junze.androidxf.http.requestargs.ArgsResult;
import junze.androidxf.http.requestargs.RequestArgs;
import junze.androidxf.http.requestargs.RequestArgsUtils;

/**
 * Created by Wingene on 2017/5/23.
 */

public class Ask {
    public abstract static class MyBaseResponse extends BaseResponse {

        @Override
        protected void performatInitResponse(String strResponse) throws Exception {
            initData(strResponse);
        }

        protected abstract void initData(String data);
    }

    public static class MyBaseRequest<T extends IResponse> extends BaseParamsRequest<T> {
        Map<String, Object> mMap;

        public MyBaseRequest(String mUrl, T mResponse) {
            super(mUrl, mResponse);
            mMap = new HashMap<>();
        }

        @Override
        public Map<String, Object> getMap() throws Exception {
            ArgsResult result = RequestArgUtil.parseArgs(this);
            mMap.putAll(result.getParams());
            return mMap;
        }
    }

    public static class BaseSignRequest<T extends IResponse> extends MyBaseRequest<T> {
        /**
         * 用户ID	不可 未登录 可为0
         */
        @RequestArgs
        @SerializedName("UserId")
        private Integer userId;

        /**
         * 设备类型	不可 0、网页 1、iOS  2、安卓
         */
        @RequestArgs
        @SerializedName("DeviceType")
        private Integer deviceType;

        /**
         * 设备Key	可空  极光推送用户Key
         */
        @RequestArgs
        @SerializedName("DeviceKey")
        private String deviceKey;

        /**
         * 登录验证码	可空
         */
        @RequestArgs
        @SerializedName("VerifiCode")
        private String verifiCode;

        /**
         * 时间戳10位	不可
         */
        @RequestArgs
        @SerializedName("TimeStamp")
        private Long timeStamp;

        /**
         * 签名	不可 详见1.1签名算法
         */
        @RequestArgs
        @SerializedName("Sign")
        private String sign;

        public BaseSignRequest(String mUrl, T mResponse) {
            super(mUrl, mResponse);
            String[] params = new String[]{"UserId=" + UserData.getUserId(), "DeviceType=" + 2, "DeviceKey=" +
                    UserData.getDeviceKey(), "VerifiCode=" + UserData.getverifiCode(), "TimeStamp=" + System
                    .currentTimeMillis() / 1000};
            userId = UserData.getUserId();
            deviceType = 2;
            deviceKey = UserData.getDeviceKey();
            verifiCode = UserData.getverifiCode();
            timeStamp = System.currentTimeMillis() / 1000;
            sign = NoHttpRequest.signParams(params);

            //            putForURLEncoder();
        }
    }

    public static class MyBaseCacheRequest<T extends IResponse> extends BaseCacheParamsRequest<T> {
        Map<String, Object> mMap;

        public MyBaseCacheRequest(String mUrl, T mResponse) {
            super(mUrl, mResponse);
            mMap = new HashMap<>();
        }

        @Override
        public Map<String, Object> getMap() throws Exception {
            return mMap;
        }
    }
}

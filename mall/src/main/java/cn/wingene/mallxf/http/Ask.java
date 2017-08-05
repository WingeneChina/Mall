package cn.wingene.mallxf.http;

import java.util.HashMap;
import java.util.Map;

import junze.java.net.IHttpElement.IResponse;

import junze.androidxf.http.BaseCacheParamsRequest;
import junze.androidxf.http.BaseParamsRequest;
import junze.androidxf.http.BaseResponse;

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
            return mMap;
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

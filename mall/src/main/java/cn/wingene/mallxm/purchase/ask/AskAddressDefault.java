package cn.wingene.mallxm.purchase.ask;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

/**
 * Created by Wingene on 2017/9/3.
 */

public class AskAddressDefault {
    public static class Response extends MyBaseResponse {
        @Override
        protected void initData(JsonElement json) {
        }
    }

    public static class Request extends BaseSignRequest<Response> {
        /**
         * 地址ID	不可
         */
        @SerializedName("Id")
        @RequestArgs
        private Integer id;

        public Request(Integer id) {
            super(HttpAddress.ADDRESS_DEFAULT, new Response());
            this.id = id;
        }
    }


}

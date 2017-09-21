package cn.wingene.mallxm.purchase.ask;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

/**
 * Created by Wingene on 2017/9/4.
 */

public class AskIntegralIndex {
    public static class Response extends MyBaseResponse {
        public Data data;

        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, Data.class);
        }

        public Integer getIntegral() {
            return data.getIntegral();
        }
    }

    public static class Request extends BaseSignRequest<Response> {
        public Request() {
            super(HttpAddress.INTEGRAL_INDEX, new Response());
        }
    }

    private static class Data {
        /**
         * 金币	不可
         */
        @SerializedName("Integral")
        private Integer integral;


        /**
         * 金币	不可
         */
        public Integer getIntegral() {
            return integral;
        }


    }
}

package cn.wingene.mallxm.purchase.ask;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;
import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

/**
 * Created by Wingene on 2017/9/4.
 */

public class AskBandCardDefault {
    public static class Response extends MyBaseResponse {
        public Data data;

        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, Data.class);
        }
    }

    public static class Request extends BaseSignRequest<Response> {
        /**
         * 银行卡ID	不可
         */
        @SerializedName("Id")
        @RequestArgs
        private Integer id;

        public Request(Integer id) {
            super(HttpAddress.BANK_CARD_DEFAULT, new Response());
            this.id = id;
        }
    }

    private static class Data {


    }


}

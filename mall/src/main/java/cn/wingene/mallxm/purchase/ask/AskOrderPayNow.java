package cn.wingene.mallxm.purchase.ask;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;
import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;
import cn.wingene.mallxm.purchase.bean.Order;

/**
 * Created by Wingene on 2017/9/3.
 */

public class AskOrderPayNow {
    public static class Response extends MyBaseResponse {
        public Order data;

        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, Order.class);
        }
    }

    public static class Request extends BaseSignRequest<Response> {
        /**
         * 订单单号	不可
         */
        @SerializedName("No")
        @RequestArgs
        private String no;

        public Request(String no) {
            super(HttpAddress.ORDER_PAY_NOW, new Response());
            this.no = no;
        }
    }

}

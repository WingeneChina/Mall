package cn.wingene.mallxm.purchase.ask;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;
import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

/**
 * Created by Wingene on 2017/9/3.
 */

public class AskCartEdit {
    public static class Response extends MyBaseResponse {
        @Override
        protected void initData(JsonElement json) {
        }
    }

    public static class Request extends BaseSignRequest<Response> {
        /**
         * 购物车ID	不可
         */
        @SerializedName("Id")
        @RequestArgs
        private Integer id;

        /**
         * 购买商品数量	不可
         */
        @SerializedName("ProductNumber")
        @RequestArgs
        private Integer productNumber;


        public Request(Integer id, Integer productNumber) {
            super(HttpAddress.CART_EDIT, new Response());
            this.id = id;
            this.productNumber = productNumber;
        }
    }

}

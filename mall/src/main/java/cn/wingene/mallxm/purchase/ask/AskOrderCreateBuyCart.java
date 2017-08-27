package cn.wingene.mallxm.purchase.ask;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;
import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;
import cn.wingene.mallxm.purchase.bean.Order;

/**
 * Created by Wingene on 2017/8/27.
 */

public class AskOrderCreateBuyCart {
    public static class Response extends MyBaseResponse {
        public Order data;

        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, Order.class);
        }
    }

    public static class Request extends BaseSignRequest<Response> {
        public Request(String cartIds, Integer addressId) {
            super(HttpAddress.ORDER_CREATE_BUY_CART, new Response());
            this.cartIds = cartIds;
            this.addressId = addressId;
        }

        /**
         * 购物车IDs	不可 如 1,2,3 组合
         */
        @SerializedName("CartIds")
        @RequestArgs
        private String cartIds;

        /**
         * 收货地址ID	不可
         */
        @SerializedName("AddressId")
        @RequestArgs
        private Integer addressId;


    }







}

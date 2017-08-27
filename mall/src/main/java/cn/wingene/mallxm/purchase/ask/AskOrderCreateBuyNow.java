package cn.wingene.mallxm.purchase.ask;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;
import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;
import cn.wingene.mallxm.purchase.bean.Order;
import cn.wingene.mallxm.purchase.bean.Product;

/**
 * Created by Wingene on 2017/8/27.
 */

public class AskOrderCreateBuyNow {
    public static class Response extends MyBaseResponse {
        public Order data;

        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, Order.class);
        }
    }

    public static class Request extends BaseSignRequest<Response> {
        public Request(Product product, Integer addressId) {
            super(HttpAddress.ORDER_CREATE_BUY_NOW, new Response());
            this.productId = product.getId();
            this.productSpecId = product.getSpecId();
            this.promotionId = product.getPromotionId();
            this.buyNum = product.getBuyNum();
            this.addressId = addressId;
        }

        /**
         * 商品ID	不可
         */
        @SerializedName("ProductId")
        @RequestArgs
        private Integer productId;

        /**
         * 商品规格ID	不可 没有请填写0
         */
        @SerializedName("ProductSpecId")
        @RequestArgs
        private Integer productSpecId;

        /**
         * 促销活动ID	可空
         */
        @SerializedName("PromotionId")
        @RequestArgs
        private Integer promotionId;

        /**
         * 购买数量	不可
         */
        @SerializedName("BuyNum")
        @RequestArgs
        private Integer buyNum;

        /**
         * 收货地址ID	不可
         */
        @SerializedName("AddressId")
        @RequestArgs
        private Integer addressId;


    }


}

package cn.wingene.mallxm.purchase.ask;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

/**
 * Created by Wingene on 2017/8/26.
 */

public class AskCartList {
    public static class Response extends MyBaseResponse {
        public List<CartItem> data;
        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, new TypeToken<ArrayList<CartItem>>() {
            }.getType());
        }

    }

    public static class Request extends BaseSignRequest<Response> {
        public Request() {
            super(HttpAddress.CART_LIST, new Response());
        }
    }

    public static class CartItem {
        /**
         * 购物车ID	不可
         */
        @SerializedName("Id")
        private Integer id;

        /**
         * 商品ID	不可
         */
        @SerializedName("ProductId")
        private Integer productId;

        /**
         * 商品图片	不可
         */
        @SerializedName("ProductImage")
        private String productImage;

        /**
         * 商品名称	不可
         */
        @SerializedName("ProductName")
        private String productName;

        /**
         * 购买商品数量	不可
         */
        @SerializedName("ProductNumber")
        private Integer productNumber;

        /**
         * 商品单价	不可
         */
        @SerializedName("ProductPrice")
        private Double productPrice;

        /**
         * 商品规格	可空
         */
        @SerializedName("ProductSpec")
        private String productSpec;

        /**
         * 商品规格ID	不可
         */
        @SerializedName("ProductSpecId")
        private Integer productSpecId;

        /**
         * 促销活动ID	不可
         */
        @SerializedName("PromotionId")
        private Integer promotionId;

        /**
         * 状态	不可
         */
        @SerializedName("State")
        private Integer state;

        /**
         * 状态说明	不可
         */
        @SerializedName("StateDesp")
        private Integer stateDesp;


        /**
         * 购物车ID	不可
         */
        public Integer getId() {
            return id;
        }

        /**
         * 商品ID	不可
         */
        public Integer getProductId() {
            return productId;
        }

        /**
         * 商品图片	不可
         */
        public String getProductImage() {
            return productImage;
        }

        /**
         * 商品名称	不可
         */
        public String getProductName() {
            return productName;
        }

        /**
         * 购买商品数量	不可
         */
        public Integer getProductNumber() {
            return productNumber;
        }

        /**
         * 商品单价	不可
         */
        public Double getProductPrice() {
            return productPrice;
        }

        /**
         * 商品规格	可空
         */
        public String getProductSpec() {
            return productSpec;
        }

        /**
         * 商品规格ID	不可
         */
        public Integer getProductSpecId() {
            return productSpecId;
        }

        /**
         * 促销活动ID	不可
         */
        public Integer getPromotionId() {
            return promotionId;
        }

        /**
         * 状态	不可
         * 1、正常
         * 2、库存不足
         * 3、产品已下架
         */
        public Integer getState() {
            return state;
        }

        /**
         * 状态说明	不可
         */
        public Integer getStateDesp() {
            return stateDesp;
        }


    }


}

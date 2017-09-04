package cn.wingene.mallxm.purchase.ask;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;
import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;
import cn.wingene.mallxm.purchase.bean.Account;
import cn.wingene.mallxm.purchase.bean.Address;
import cn.wingene.mallxm.purchase.bean.Product;
import cn.wingene.mallxm.purchase.bean.able.IAddOrder;

/**
 * Created by Wingene on 2017/8/26.
 */

public class AskBuyCart {
    public static class Response extends MyBaseResponse {
        public BuyCarData data;
        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, BuyCarData.class);
        }
    }

    public static class Request extends BaseSignRequest<Response> {
        /**
         * 购物车IDs	不可 如 1,2,3 组合
         */
        @SerializedName("CartIds")
        @RequestArgs
        private String cartIds;

        public Request(String cartIds) {
            super(HttpAddress.ORDER_BUY_CART, new Response());
            this.cartIds = cartIds;
        }
    }

    public static class BuyCarData implements IAddOrder {
        /**
         * 商品信息列表	不可
         */
        @SerializedName("ProductList")
        private List<Product> productList;

        /**
         * 会员账户	不可
         */
        @SerializedName("Account")
        private Account account;

        /**
         * 收货地址	可空
         */
        @SerializedName("Address")
        private Address address;

        /**
         * 总购买数量	不可
         */
        @SerializedName("SumNumber")
        private Integer sumNumber;

        /**
         * 总金额	不可
         */
        @SerializedName("SumPrice")
        private Double sumPrice;

        /**
         * 折扣率	不可
         */
        @SerializedName("Discount")
        private Double discount;

        /**
         * 折扣金额	不可
         */
        @SerializedName("DiscountPrice")
        private Double discountPrice;

        /**
         * 运费金额	不可
         */
        @SerializedName("DeliveryFee")
        private Double deliveryFee;

        /**
         * 实付金额	不可
         */
        @SerializedName("PayPrice")
        private Double payPrice;

        /**
         * 可使用应币支付
         */
        @SerializedName("AcceptIntegral")
        private int acceptIntegral;


        /**
         * 商品信息列表	不可
         */
        @Override
        public List<Product> getProductList() {
            return productList;
        }

        /**
         * 会员账户	不可
         */
        @Override
        public Account getAccount() {
            return account;
        }

        /**
         * 收货地址	可空
         */
        @Override
        public Address getAddress() {
            return address;
        }

        /**
         * 总购买数量	不可
         */
        @Override
        public Integer getSumNumber() {
            return sumNumber;
        }

        /**
         * 总金额	不可
         */
        @Override
        public Double getSumPrice() {
            return sumPrice;
        }

        /**
         * 折扣率	不可
         */
        @Override
        public Double getDiscount() {
            return discount;
        }

        /**
         * 折扣金额	不可
         */
        @Override
        public Double getDiscountPrice() {
            return discountPrice;
        }

        /**
         * 运费金额	不可
         */
        @Override
        public Double getDeliveryFee() {
            return deliveryFee;
        }

        /**
         * 实付金额	不可
         */
        @Override
        public Double getPayPrice() {
            return payPrice;
        }

        public int getAcceptIntegral() {
            return acceptIntegral;
        }
    }


}

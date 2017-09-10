package cn.wingene.mallxm.purchase.ask;

import java.util.ArrayList;
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
import cn.wingene.mallxm.purchase.bean.able.IOrderProductItem;

/**
 * Created by Wingene on 2017/8/26.
 */

public class AskBuyNow {
    public static class Response extends MyBaseResponse {
        public BuyNowData data;

        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, BuyNowData.class);
        }
    }

    public static class Request extends BaseSignRequest<Response> {
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

        public Request(Integer productId, Integer productSpecId, Integer promotionId, Integer buyNum) {
            super(HttpAddress.ORDER_BUY_NOW, new Response());
            this.productId = productId;
            this.productSpecId = productSpecId;
            this.promotionId = promotionId;
            this.buyNum = buyNum;
        }
    }

    public static class BuyNowData implements IAddOrder {
        /**
         * 商品信息	不可
         */
        private Product Product;

        /**
         * 会员账户	不可
         */
        private Account Account;

        /**
         * 收货地址	可空
         */
        private Address Address;

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
         * 商品信息	不可
         */
        public Product getProduct() {
            return Product;
        }

        @Override
        public List<Product> getProductList() {
            List<Product> list = new ArrayList<>();
            list.add(getProduct());
            return list;
        }

        /**
         * 会员账户	不可
         */
        @Override
        public Account getAccount() {
            return Account;
        }

        /**
         * 收货地址	可空
         */
        @Override
        public Address getAddress() {
            return Address;
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

        public Integer getAcceptIntegral() {
            return acceptIntegral;
        }

        @Override
        public List<? extends IOrderProductItem> getOrderProductItem() {
            return getProductList();
        }
    }

}

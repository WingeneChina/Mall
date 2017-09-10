package cn.wingene.mallxm.purchase.ask;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;
import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;
import cn.wingene.mallxm.purchase.bean.Account;
import cn.wingene.mallxm.purchase.bean.Address4;
import cn.wingene.mallxm.purchase.bean.OrderProductItem;
import cn.wingene.mallxm.purchase.bean.able.IOrder;
import cn.wingene.mallxm.purchase.bean.able.IProduct;

/**
 * Created by Wingene on 2017/9/3.
 */

public class AskOrderDetail {
    public static class Response extends MyBaseResponse {
        public OrderDetail data;

        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, OrderDetail.class);
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
            super(HttpAddress.ORDER_DETAIL, new Response());
            this.no = no;
        }
    }

    public static class OrderDetail implements IOrder{
        /**
         * 订单单号	不可
         */
        @SerializedName("No")
        private String no;

        /**
         * 订单状态	不可 详见1.6订单状态
         */
        @SerializedName("State")
        private Integer state;

        /**
         * 订单状态说明	不可
         */
        @SerializedName("StateDesp")
        private String stateDesp;

        /**
         * 退款状态	不可 详见1.9订单状态
         */
        @SerializedName("RefundState")
        private Integer refundState;

        /**
         * 退款状态说明	不可
         */
        @SerializedName("RefundStateDesp")
        private String refundStateDesp;

        /**
         * 下单时间	不可
         */
        @SerializedName("CreateTime")
        private String createTime;

        /**
         * 总金额	不可
         */
        @SerializedName("SumPrice")
        private Double sumPrice;

        /**
         * 总数量	不可
         */
        @SerializedName("SumNumber")
        private Double sumNumber;

        /**
         * 运费	不可
         */
        @SerializedName("DeliveryFee")
        private Double deliveryFee;

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
         * 实付金额	不可
         */
        @SerializedName("PayPrice")
        private Double payPrice;

        /**
         * 支付状态	不可 详见1.11订单支付状态
         */
        @SerializedName("PayState")
        private Integer payState;

        /**
         * 支付状态说明	不可
         */
        @SerializedName("PayStateDesp")
        private String payStateDesp;

        /**
         * 评价状态	不可 详见1.10订单评价状态
         */
        @SerializedName("EvaluationState")
        private Integer evaluationState;

        /**
         * 评价状态说明	不可
         */
        @SerializedName("EvaluationStateDesp")
        private String evaluationStateDesp;

        /**
         * 平台模块	不可 详见1.5平台模块
         */
        @SerializedName("Module")
        private Integer module;

        /**
         * 平台模块说明	不可
         */
        @SerializedName("ModuleDesp")
        private String moduleDesp;

        /**
         * 支付方式	不可 详见1.7支付方式
         */
        @SerializedName("Payment")
        private Integer payment;

        /**
         * 支付方式说明	不可
         */
        @SerializedName("PaymentDesp")
        private String paymentDesp;

        /**
         * 订单商品列表	不可
         */
        @SerializedName("OrderProductList")
        private List<OrderProductList> orderProductList;

        /**
         * 收货地址	可空
         */
        @SerializedName("Address")
        private Address4 address;

        /**
         * 订单支付金额	可空
         */
        @SerializedName("OrderPay")
        private OrderPay orderPay;

        @Override
        public Account getAccount() {
            return null;
        }

        @Override
        public List<OrderProductItem> getProductList() {
            return null;
        }

        @Override
        public Double getAcceptCouponPrice() {
            return null;
        }

        ////
        /**
         * 订单单号	不可
         */
        public String getNo() {
            return no;
        }

        /**
         * 订单状态	不可 详见1.6订单状态
         */
        public Integer getState() {
            return state;
        }

        /**
         * 订单状态说明	不可
         */
        public String getStateDesp() {
            return stateDesp;
        }

        /**
         * 退款状态	不可 详见1.9订单状态
         */
        public Integer getRefundState() {
            return refundState;
        }

        /**
         * 退款状态说明	不可
         */
        public String getRefundStateDesp() {
            return refundStateDesp;
        }

        /**
         * 下单时间	不可
         */
        public String getCreateTime() {
            return createTime;
        }

        /**
         * 总金额	不可
         */
        public Double getSumPrice() {
            return sumPrice;
        }

        /**
         * 总数量	不可
         */
        public Double getSumNumber() {
            return sumNumber;
        }

        /**
         * 运费	不可
         */
        public Double getDeliveryFee() {
            return deliveryFee;
        }

        /**
         * 折扣率	不可
         */
        public Double getDiscount() {
            return discount;
        }

        /**
         * 折扣金额	不可
         */
        public Double getDiscountPrice() {
            return discountPrice;
        }

        /**
         * 实付金额	不可
         */
        public Double getPayPrice() {
            return payPrice;
        }

        /**
         * 支付状态	不可 详见1.11订单支付状态
         */
        public Integer getPayState() {
            return payState;
        }

        /**
         * 支付状态说明	不可
         */
        public String getPayStateDesp() {
            return payStateDesp;
        }

        /**
         * 评价状态	不可 详见1.10订单评价状态
         */
        public Integer getEvaluationState() {
            return evaluationState;
        }

        /**
         * 评价状态说明	不可
         */
        public String getEvaluationStateDesp() {
            return evaluationStateDesp;
        }

        /**
         * 平台模块	不可 详见1.5平台模块
         */
        public Integer getModule() {
            return module;
        }

        /**
         * 平台模块说明	不可
         */
        public String getModuleDesp() {
            return moduleDesp;
        }

        /**
         * 支付方式	不可 详见1.7支付方式
         */
        public Integer getPayment() {
            return payment;
        }

        /**
         * 支付方式说明	不可
         */
        public String getPaymentDesp() {
            return paymentDesp;
        }

        /**
         * 订单商品列表	不可
         */
        public List<OrderProductList> getOrderProductList() {
            return orderProductList;
        }

        /**
         * 收货地址	可空
         */
        public Address4 getAddress() {
            return address;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        public OrderPay getOrderPay() {
            return orderPay;
        }
    }

    public static class OrderProductList implements IProduct {
        /**
         * 订单商品ID	不可
         */
        @SerializedName("Id")
        private Integer id;

        /**
         * 商品ID	不可
         */
        @SerializedName("ProductId")
        private Integer productId;

        /**
         * 商品名称	不可
         */
        @SerializedName("ProductName")
        private String productName;

        /**
         * 图片路径	不可
         */
        @SerializedName("ProductImage")
        private String productImage;

        /**
         * 销售价格	不可
         */
        @SerializedName("SalePrice")
        private Double salePrice;

        /**
         * 合计价格	不可
         */
        @SerializedName("SumPrice")
        private Double sumPrice;

        /**
         * 销售数量	不可
         */
        @SerializedName("BuyNumber")
        private Integer buyNumber;

        /**
         * 规格说明	不可
         */
        @SerializedName("SpecDesp")
        private String specDesp;


        /**
         * 订单商品ID	不可
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
         * 商品名称	不可
         */
        public String getProductName() {
            return productName;
        }

        /**
         * 图片路径	不可
         */
        public String getProductImage() {
            return productImage;
        }

        /**
         * 销售价格	不可
         */
        public Double getSalePrice() {
            return salePrice;
        }

        /**
         * 合计价格	不可
         */
        public Double getSumPrice() {
            return sumPrice;
        }

        /**
         * 销售数量	不可
         */
        public Integer getBuyNumber() {
            return buyNumber;
        }

        /**
         * 规格说明	不可
         */
        public String getSpecDesp() {
            return specDesp;
        }

        @Override
        public String getName() {
            return productName;
        }

        @Override
        public String getDefaultImage() {
            return productImage;
        }

        @Override
        public Double getPrice() {
            return sumPrice;
        }

        @Override
        public Double getPriceMarket() {
            return salePrice;
        }

        @Override
        public Integer getStock() {
            return null;
        }

        @Override
        public Integer getBuyNum() {
            return buyNumber;
        }

        @Override
        public Integer getSpecId() {
            return null;
        }

        @Override
        public String getSpecValue() {
            return specDesp;
        }

        @Override
        public Integer getPromotionId() {
            return null;
        }
    }

    public static class OrderPay {
        /**
         * 游币	不可
         */
        @SerializedName("Amount")
        private Double amount;

        /**
         * 应币	不可
         */
        @SerializedName("Integral")
        private Integer integral;

        /**
         * 第三方支付金额	不可 如支付宝 或微信
         */
        @SerializedName("OrderPay")
        private Double orderPay;


        /**
         * 游币	不可
         */
        public Double getAmount() {
            return amount;
        }

        /**
         * 应币	不可
         */
        public Integer getIntegral() {
            return integral;
        }

        /**
         * 第三方支付金额	不可 如支付宝 或微信
         */
        public Double getOrderPay() {
            return orderPay;
        }


    }


}

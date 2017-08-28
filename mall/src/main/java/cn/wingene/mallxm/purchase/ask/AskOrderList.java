package cn.wingene.mallxm.purchase.ask;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;
import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseCacheSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

/**
 * Created by Wingene on 2017/8/27.
 */

public class AskOrderList {

    public static class Response extends MyBaseResponse {
        public Data data;

        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, Data.class);
        }
    }

    public static class Request extends BaseCacheSignRequest<Response> {
        public Request(Integer state, int pageIndex) {
            super(HttpAddress.ORDER_LIST, new Response());
            this.state = state;
            this.pageIndex = pageIndex;
        }

        /**
         * 订单状态	不可
         */
        @SerializedName("State")
        @RequestArgs
        private Integer state;

        /**
         * 当分页	不可 分页1开始
         */
        @SerializedName("PageIndex")
        @RequestArgs
        private Integer pageIndex;


    }

    public static class Data {
        /**
         * 总页数	不可
         */
        @SerializedName("PageCount")
        private Integer pageCount;

        /**
         * 总记录数	不可
         */
        @SerializedName("RecordCount")
        private Integer recordCount;

        /**
         * 订单详情列表	可空
         */
        @SerializedName("List")
        private List<OrderItem> list;


        /**
         * 总页数	不可
         */
        public Integer getPageCount() {
            return pageCount;
        }

        /**
         * 总记录数	不可
         */
        public Integer getRecordCount() {
            return recordCount;
        }

        /**
         * 订单详情列表	可空
         */
        public List<OrderItem> getList() {
            return list;
        }


    }

    public static class OrderItem {
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
         * 退款状态	不可 详见1.9订单退款状态
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
        private Integer sumNumber;

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
         * 订单商品列表	不可
         */
        @SerializedName("OrderProductList")
        private List<OrderProductList> orderProductList;


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
         * 退款状态	不可 详见1.9订单退款状态
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
        public Integer getSumNumber() {
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
         * 订单商品列表	不可
         */
        public List<OrderProductList> getOrderProductList() {
            return orderProductList;
        }


    }

    public static class OrderProductList {
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


    }


}

package cn.wingene.mallxm.purchase.ask;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;
import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

/**
 * Created by Wingene on 2017/9/3.
 */
public class AskLogisticsDetail {
    public static class Response extends MyBaseResponse {
        public Logistics data;

        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, Logistics.class);
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
            super(HttpAddress.LOGISTICS_DETAIL, new Response());
            this.no = no;
        }
    }

    public static class Logistics {
        /**
         * 物流公司名称	不可
         */
        @SerializedName("ShipperName")
        private String shipperName;

        /**
         * 订单商品图片	不可
         */
        @SerializedName("ProductImage")
        private String productImage;

        /**
         * 商户ID	不可
         */
        @SerializedName("EBusinessID")
        private String eBusinessID;

        /**
         * 订单编号	不可
         */
        @SerializedName("OrderCode")
        private String orderCode;

        /**
         * 快递公司编码	不可
         */
        @SerializedName("ShipperCode")
        private String shipperCode;

        /**
         * 物流单号	不可
         */
        @SerializedName("LogisticCode")
        private String logisticCode;

        /**
         * 成功与否	不可
         */
        @SerializedName("Success")
        private Boolean success;

        /**
         * 物流状态	不可 2-在途中,3-签收,4-问题件
         */
        @SerializedName("State")
        private Integer state;

        /**
         * 失败原因	可空
         */
        @SerializedName("Reason")
        private String reason;

        /**
         * 物流信息列表	可空
         */
        @SerializedName("Traces")
        private List<Traces> traces;


        /**
         * 物流公司名称	不可
         */
        public String getShipperName() {
            return shipperName;
        }

        /**
         * 订单商品图片	不可
         */
        public String getProductImage() {
            return productImage;
        }

        /**
         * 商户ID	不可
         */
        public String getEBusinessID() {
            return eBusinessID;
        }

        /**
         * 订单编号	不可
         */
        public String getOrderCode() {
            return orderCode;
        }

        /**
         * 快递公司编码	不可
         */
        public String getShipperCode() {
            return shipperCode;
        }

        /**
         * 物流单号	不可
         */
        public String getLogisticCode() {
            return logisticCode;
        }

        /**
         * 成功与否	不可
         */
        public Boolean getSuccess() {
            return success;
        }

        /**
         * 物流状态	不可 2-在途中,3-签收,4-问题件
         */
        public Integer getState() {
            return state;
        }

        /**
         * 失败原因	可空
         */
        public String getReason() {
            return reason;
        }

        /**
         * 物流信息列表	可空
         */
        public List<Traces> getTraces() {
            return traces;
        }


    }

    public static class Traces {
        /**
         * 时间	不可
         */
        @SerializedName("AcceptTime")
        private String acceptTime;

        /**
         * 描述	不可
         */
        @SerializedName("AcceptStation")
        private String acceptStation;

        /**
         * 备注	可空
         */
        @SerializedName("Remark")
        private String remark;


        /**
         * 时间	不可
         */
        public String getAcceptTime() {
            return acceptTime;
        }

        /**
         * 描述	不可
         */
        public String getAcceptStation() {
            return acceptStation;
        }

        /**
         * 备注	可空
         */
        public String getRemark() {
            return remark;
        }


    }


}

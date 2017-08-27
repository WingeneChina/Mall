package cn.wingene.mallxm.purchase.ask;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;
import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

/**
 * Created by Wingene on 2017/8/27.
 */

public class AskSubmitPayNow {
    public static class Response extends MyBaseResponse {
        public Data data;

        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, Data.class);
        }
    }

    public static class Request extends BaseSignRequest<Response> {

//        public Request(String no, Integer payment, Integer subPayment, Integer couponId, Double amount, Integer
//                integral) {
//            super(HttpAddress.ORDER_SUBMIT_PAY_NOW, new Response());
//            this.no = no;
//            this.payment = payment;
//            this.subPayment = subPayment;
//            this.couponId = couponId;
//            this.amount = amount;
//            this.integral = integral;
//        }
        public Request(String no,boolean isAlipay, Integer couponId, Double amount, Integer
                integral) {
            super(HttpAddress.ORDER_SUBMIT_PAY_NOW, new Response());
            this.no = no;
            this.payment = isAlipay ? 21 : 22;
            this.subPayment = isAlipay ? 2102 : 2202;
            this.couponId = couponId;
            this.amount = amount;
            this.integral = integral;
        }

        /**
         * 订单单号	不可
         */
        @SerializedName("No")
        @RequestArgs
        private String no;

        /**
         * 支付方式	不可 详见1.7支付方式
         */
        @SerializedName("Payment")
        @RequestArgs
        private Integer payment;

        /**
         * 支付方式子类	不可 详见1.8支付方式子类
         */
        @SerializedName("SubPayment")
        @RequestArgs
        private Integer subPayment;

        /**
         * 券ID	不可 无请填写0
         */
        @SerializedName("CouponId")
        @RequestArgs
        private Integer couponId;

        /**
         * 游币支付金额	不可 使用比例 1:1
         */
        @SerializedName("Amount")
        @RequestArgs
        private Double amount;

        /**
         * 应币支付金额	不可 使用比例 100:1
         */
        @SerializedName("Integral")
        @RequestArgs
        private Integer integral;


    }


    private static class Data {
        /**
         * 订单单号	不可
         */
        @SerializedName("No")
        private String no;

        /**
         * 第三支付金额	不可
         */
        @SerializedName("PayPrice")
        private Double payPrice;

        /**
         * 支付状态	不可
         */
        @SerializedName("PayState")
        private Integer payState;

        /**
         * 支付状态说明	不可 0、未支付 1、已支付 2、已退款
         */
        @SerializedName("PayStateDesp")
        private String payStateDesp;

        /**
         * 支付实体	可空
         */
        @SerializedName("PayData")
        private JsonElement payData;


        /**
         * 订单单号	不可
         */
        public String getNo() {
            return no;
        }

        /**
         * 第三支付金额	不可
         */
        public Double getPayPrice() {
            return payPrice;
        }

        /**
         * 支付状态	不可
         */
        public Integer getPayState() {
            return payState;
        }

        /**
         * 支付状态说明	不可 0、未支付 1、已支付 2、已退款
         */
        public String getPayStateDesp() {
            return payStateDesp;
        }

        public JsonElement getPayData() {
            return payData;
        }

        public WeChat getPayDataForWeChat(){
            return AKit.getGson().fromJson(payData,WeChat.class);
        }

        public Alipay getPayDataForAlipay(){
            return AKit.getGson().fromJson(payData,Alipay.class);
        }
    }


    public static class WeChat {
        /**
         * 公众账号ID	不可
         */
        @SerializedName("AppId")
        private String appId;

        /**
         * 商户号	不可
         */
        @SerializedName("PartnerId")
        private String partnerId;

        /**
         * 预支付交易会话ID	不可
         */
        @SerializedName("PrepayId")
        private String prepayId;

        /**
         * 扩展字段	不可
         */
        @SerializedName("PackAge")
        private String packAge;

        /**
         * 时间戳ID	不可
         */
        @SerializedName("TimeStamp")
        private String timeStamp;

        /**
         * 随机字符串	不可
         */
        @SerializedName("NonceStr")
        private String nonceStr;

        /**
         * 签名	不可
         */
        @SerializedName("Sign")
        private String sign;


        /**
         * 公众账号ID	不可
         */
        public String getAppId() {
            return appId;
        }

        /**
         * 商户号	不可
         */
        public String getPartnerId() {
            return partnerId;
        }

        /**
         * 预支付交易会话ID	不可
         */
        public String getPrepayId() {
            return prepayId;
        }

        /**
         * 扩展字段	不可
         */
        public String getPackAge() {
            return packAge;
        }

        /**
         * 时间戳ID	不可
         */
        public String getTimeStamp() {
            return timeStamp;
        }

        /**
         * 随机字符串	不可
         */
        public String getNonceStr() {
            return nonceStr;
        }

        /**
         * 签名	不可
         */
        public String getSign() {
            return sign;
        }


    }

    public static class Alipay {
        /**
         * 卖家支付宝帐户	不可
         */
        @SerializedName("SellerId")
        private String sellerId;

        /**
         * 合作者身份ID	不可
         */
        @SerializedName("Partner")
        private String partner;

        /**
         * 验签字符串	不可
         */
        @SerializedName("SignText")
        private String signText;


        /**
         * 卖家支付宝帐户	不可
         */
        public String getSellerId() {
            return sellerId;
        }

        /**
         * 合作者身份ID	不可
         */
        public String getPartner() {
            return partner;
        }

        /**
         * 验签字符串	不可
         */
        public String getSignText() {
            return signText;
        }


    }

    public static class WeChatPublic {
        /**
         * 同步通知路径	不可
         */
        @SerializedName("Url")
        private String url;

        /**
         * 公众账号ID	不可
         */
        @SerializedName("AppId")
        private String appId;

        /**
         * 时间戳ID	不可
         */
        @SerializedName("TimeStamp")
        private String timeStamp;

        /**
         * 随机串	不可
         */
        @SerializedName("NonceStr")
        private String nonceStr;

        /**
         * 扩展字符串	不可
         */
        @SerializedName("PackAge")
        private String packAge;

        /**
         * 微信签名方式	不可
         */
        @SerializedName("SignType")
        private String signType;

        /**
         * 签名	不可
         */
        @SerializedName("PaySign")
        private String paySign;


        /**
         * 同步通知路径	不可
         */
        public String getUrl() {
            return url;
        }

        /**
         * 公众账号ID	不可
         */
        public String getAppId() {
            return appId;
        }

        /**
         * 时间戳ID	不可
         */
        public String getTimeStamp() {
            return timeStamp;
        }

        /**
         * 随机串	不可
         */
        public String getNonceStr() {
            return nonceStr;
        }

        /**
         * 扩展字符串	不可
         */
        public String getPackAge() {
            return packAge;
        }

        /**
         * 微信签名方式	不可
         */
        public String getSignType() {
            return signType;
        }

        /**
         * 签名	不可
         */
        public String getPaySign() {
            return paySign;
        }


    }


}

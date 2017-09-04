package cn.wingene.mallxm.purchase.ask;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;
import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

/**
 * Created by Wingene on 2017/9/4.
 */

public class AskAmountRecharge {
    public static class Response extends MyBaseResponse {
        public Data data;

        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, Data.class);
        }
    }

    public static class Request extends BaseSignRequest<Response> {
        /**
         * 卡号	不可
         */
        @SerializedName("CardNo")
        @RequestArgs
        private String cardNo;

        /**
         * 卡密	不可
         */
        @SerializedName("CardPwd")
        @RequestArgs
        private String cardPwd;

        public Request(String cardNo, String cardPwd) {
            super(HttpAddress.AMOUNT_RECHARGE, new Response());
            this.cardNo = cardNo;
            this.cardPwd = cardPwd;
        }
    }

    private static class Data {
        /**
         * 卡号	不可
         */
        @SerializedName("CardNo")
        private String cardNo;

        /**
         * 充值金额	不可
         */
        @SerializedName("CardPrice")
        private String cardPrice;


        /**
         * 卡号	不可
         */
        public String getCardNo() {
            return cardNo;
        }

        /**
         * 充值金额	不可
         */
        public String getCardPrice() {
            return cardPrice;
        }


    }


}

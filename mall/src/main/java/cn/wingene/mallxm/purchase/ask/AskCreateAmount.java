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

public class AskCreateAmount {
    public static class Response extends MyBaseResponse {
        public Data data;

        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, Data.class);
        }
    }

    public static class Request extends BaseSignRequest<Response> {
        /**
         * 提现游币金额	不可
         */
        @SerializedName("AmountPrice")
        @RequestArgs
        private Double amountPrice;

        /**
         * 银行卡ID	不可
         */
        @SerializedName("BankCardId")
        @RequestArgs
        private Integer bankCardId;

        public Request(Double amountPrice, Integer bankCardId) {
            super(HttpAddress.DEPOSIT_CREATE_AMOUNT, new Response());
            this.amountPrice = amountPrice;
            this.bankCardId = bankCardId;
        }
    }

    private static class Data {
        /**
         * 提现金额	不可
         */
        @SerializedName("Price")
        private Double price;

        /**
         * 充值金额	不可
         */
        @SerializedName("CardPrice")
        private String cardPrice;


        /**
         * 提现金额	不可
         */
        public Double getPrice() {
            return price;
        }

        /**
         * 充值金额	不可
         */
        public String getCardPrice() {
            return cardPrice;
        }


    }


}

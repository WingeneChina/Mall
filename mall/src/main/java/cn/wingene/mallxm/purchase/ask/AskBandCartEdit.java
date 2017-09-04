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

public class AskBandCartEdit {
    public static class Response extends MyBaseResponse {
        public Data data;

        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, Data.class);
        }
    }

    public static class Request extends BaseSignRequest<Response> {
        /**
         * 银行卡ID	不可 0添加 >0编辑=原纪录ID
         */
        @SerializedName("Id")
        @RequestArgs
        private Integer id;

        /**
         * 是否默认	不可  0、否 1、是
         */
        @SerializedName("IsDefault")
        @RequestArgs
        private Boolean isDefault;

        /**
         * 开户银行	不可
         */
        @SerializedName("BankType")
        @RequestArgs
        private Integer bankType;

        /**
         * 持卡人姓名	不可
         */
        @SerializedName("BankAccount")
        @RequestArgs
        private String bankAccount;

        /**
         * 银行卡号	不可
         */
        @SerializedName("BankCardNo")
        @RequestArgs
        private String bankCardNo;

        /**
         * 开户支行	不可
         */
        @SerializedName("OpenBank")
        @RequestArgs
        private String openBank;

        public Request(Integer id, Boolean isDefault, Integer bankType, String bankAccount, String bankCardNo,
                String openBank) {
            super(HttpAddress.BANK_CARD_EDIT, new Response());
            this.id = id;
            this.isDefault = isDefault;
            this.bankType = bankType;
            this.bankAccount = bankAccount;
            this.bankCardNo = bankCardNo;
            this.openBank = openBank;
        }
    }

    private static class Data {


    }


}

package cn.wingene.mallxm.purchase.ask;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

/**
 * Created by Wingene on 2017/9/4.
 */

public class AskGetAmount {
    public static class Response extends MyBaseResponse {
        public Data data;

        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, Data.class);
        }
    }

    public static class Request extends BaseSignRequest<Response> {

        public Request() {
            super(HttpAddress.DEPOSIT_GET_AMOUNT, new Response());
        }
    }

    private static class Data {
        /**
         * 游币	不可
         */
        @SerializedName("Amount")
        private Double amount;

        /**
         * 银行卡实体	可空
         */
        @SerializedName("BankBack")
        private BankBack bankBack;


        /**
         * 游币	不可
         */
        public Double getAmount() {
            return amount;
        }

        /**
         * 银行卡实体	可空
         */
        public BankBack getBankBack() {
            return bankBack;
        }


    }

    public static class BankBack {
        /**
         * 银行卡ID	不可
         */
        @SerializedName("Id")
        private Integer id;

        /**
         * 是否默认	不可 0、否 1、是
         */
        @SerializedName("IsDefault")
        private Integer isDefault;

        /**
         * 开户银行	不可
         */
        @SerializedName("BankType")
        private Integer bankType;

        /**
         * 开户银行注释	不可
         */
        @SerializedName("BankTypeDesp")
        private String bankTypeDesp;

        /**
         * 持卡人姓名	不可
         */
        @SerializedName("BankAccount")
        private String bankAccount;

        /**
         * 银行卡号	不可
         */
        @SerializedName("BankCardNo")
        private String bankCardNo;

        /**
         * 开户支行	不可
         */
        @SerializedName("OpenBank")
        private String openBank;


        /**
         * 银行卡ID	不可
         */
        public Integer getId() {
            return id;
        }

        /**
         * 是否默认	不可 0、否 1、是
         */
        public Integer getIsDefault() {
            return isDefault;
        }

        /**
         * 开户银行	不可
         */
        public Integer getBankType() {
            return bankType;
        }

        /**
         * 开户银行注释	不可
         */
        public String getBankTypeDesp() {
            return bankTypeDesp;
        }

        /**
         * 持卡人姓名	不可
         */
        public String getBankAccount() {
            return bankAccount;
        }

        /**
         * 银行卡号	不可
         */
        public String getBankCardNo() {
            return bankCardNo;
        }

        /**
         * 开户支行	不可
         */
        public String getOpenBank() {
            return openBank;
        }


    }


}

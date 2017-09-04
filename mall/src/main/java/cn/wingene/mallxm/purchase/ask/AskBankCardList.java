package cn.wingene.mallxm.purchase.ask;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

/**
 * Created by Wingene on 2017/9/4.
 */

public class AskBankCardList {
    public static class Response extends MyBaseResponse {
        public Data data;

        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, Data.class);
        }
    }

    public static class Request extends BaseSignRequest<Response> {
        public Request() {
            super(HttpAddress.BANK_CARD_LIST, new Response());
        }
    }

    private static class Data {
        /**
         * 总记录数
         */
        @SerializedName("Total")
        private Integer total;

        /**
         * 银行卡列表
         */
        @SerializedName("List")
        private List<BankCard> list;


        /**
         * 总记录数
         */
        public Integer getTotal() {
            return total;
        }

        /**
         * 银行卡列表
         */
        public List<BankCard> getList() {
            return list;
        }


    }

    public static class BankCard {
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

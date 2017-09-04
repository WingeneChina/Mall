package cn.wingene.mallxm.purchase.ask;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;
import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

/**
 * Created by Wingene on 2017/9/4.
 */

public class AskBankCardGetEdit {
    public static class Response extends MyBaseResponse {
        public Data data;
        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json,Data.class);
        }
    }
    public static class Request extends BaseSignRequest<Response> {
        /**
         * 银行卡ID	不可  0添加 >0编辑=原纪录ID
         */
        @SerializedName("Id")
        @RequestArgs
        private Integer id;

        public Request( Integer id) {
            super(HttpAddress.BANK_CARD_GET_EDIT, new Response());
            this.id = id;
        }
    }

    private static class Data {
        /**
         * 开户银行列表	不可
         */
        @SerializedName("BankTypeList")
        private List<BankTypeList> bankTypeList;

        /**
         * 银行卡实体	可空 Id=0 进入添加可空
         */
        @SerializedName("BankBack")
        private BankBack bankBack;


        /**
         * 开户银行列表	不可
         */
        public List<BankTypeList> getBankTypeList() {
            return bankTypeList;
        }

        /**
         * 银行卡实体	可空 Id=0 进入添加可空
         */
        public BankBack getBankBack() {
            return bankBack;
        }


    }

    public static class BankTypeList {
        /**
         * ID	不可
         */
        @SerializedName("Id")
        private Integer id;

        /**
         * 名称	不可
         */
        @SerializedName("Name")
        private String name;


        /**
         * ID	不可
         */
        public Integer getId() {
            return id;
        }

        /**
         * 名称	不可
         */
        public String getName() {
            return name;
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

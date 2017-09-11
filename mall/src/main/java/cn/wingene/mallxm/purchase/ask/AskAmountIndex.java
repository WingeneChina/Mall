package cn.wingene.mallxm.purchase.ask;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

/**
 * Created by Wingene on 2017/9/4.
 */

public class AskAmountIndex {
    public static class Response extends MyBaseResponse {
        public Data data;
        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json,Data.class);
        }

        public Double getAmount() {
            return data.getAmount();
        }

        public boolean isDeposit() {
            return data.getIsDeposit() != null && data.getIsDeposit() ==1;
        }
    }

    public static class Request extends BaseSignRequest<Response> {
        public Request() {
            super(HttpAddress.AMOUNT_INDEX, new Response());
        }
    }

    private static class Data {
        /**
         * 游币	不可
         */
        @SerializedName("Amount")
        private Double amount;

        /**
         * 用户等级	不可
         */
        @SerializedName("Grade")
        private String grade;

        /**
         * 用户等级名称	不可
         */
        @SerializedName("GradeName")
        private String gradeName;

        /**
         * 是否可提现	不可 0、不可 1、可
         */
        @SerializedName("IsDeposit")
        private Integer isDeposit;


        /**
         * 游币	不可
         */
        public Double getAmount() {
            return amount;
        }

        public Integer getIsDeposit() {
            return isDeposit;
        }
    }


}

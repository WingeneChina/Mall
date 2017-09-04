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

public class AskAmountLogList {
   public static class Response extends MyBaseResponse {
       public Data data;
       @Override
       protected void initData(JsonElement json) {
           data = AKit.getGson().fromJson(json,Data.class);
       }
   }

    public static class Request extends BaseSignRequest<Response> {
        /**
         * 当分页	不可 分页1开始
         */
        @SerializedName("PageIndex")
        @RequestArgs
        private Integer pageIndex;

        public Request(Integer pageIndex) {
            super(HttpAddress.AMOUNT_LOG_LIST, new Response());
            this.pageIndex = pageIndex;
        }
    }

    private static class Data {
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
         * 每页条数	不可
         */
        @SerializedName("PageSize")
        private Integer pageSize;

        /**
         * 账单明细列表	可空
         */
        @SerializedName("List")
        private List<AmountLog> list;


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
         * 每页条数	不可
         */
        public Integer getPageSize() {
            return pageSize;
        }

        /**
         * 账单明细列表	可空
         */
        public List<AmountLog> getList() {
            return list;
        }


    }

    public static class AmountLog {
        /**
         * 账单明细ID	不可
         */
        @SerializedName("Id")
        private Integer id;

        /**
         * 金额	不可
         */
        @SerializedName("ChargeAmount")
        private Double chargeAmount;

        /**
         * 操作类型	不可
         */
        @SerializedName("OperateKey")
        private Integer operateKey;

        /**
         * 操作类型说明	不可
         */
        @SerializedName("OperateKeyDesp")
        private String operateKeyDesp;

        /**
         * 金额说明	不可
         */
        @SerializedName("ChargeTypeDesp")
        private String chargeTypeDesp;

        /**
         * 时间	不可
         */
        @SerializedName("LogTime")
        private String logTime;


        /**
         * 账单明细ID	不可
         */
        public Integer getId() {
            return id;
        }

        /**
         * 金额	不可
         */
        public Double getChargeAmount() {
            return chargeAmount;
        }

        /**
         * 操作类型	不可
         */
        public Integer getOperateKey() {
            return operateKey;
        }

        /**
         * 操作类型说明	不可
         */
        public String getOperateKeyDesp() {
            return operateKeyDesp;
        }

        /**
         * 金额说明	不可
         */
        public String getChargeTypeDesp() {
            return chargeTypeDesp;
        }

        /**
         * 时间	不可
         */
        public String getLogTime() {
            return logTime;
        }


    }


}

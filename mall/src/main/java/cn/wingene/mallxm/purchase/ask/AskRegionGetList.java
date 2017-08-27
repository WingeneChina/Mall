package cn.wingene.mallxm.purchase.ask;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;
import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;

/**
 * Created by Wingene on 2017/8/27.
 */

public class AskRegionGetList {
    public static class Response extends MyBaseResponse {
        public Data data;

        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, Data.class);
        }

        public Integer getTotal() {
            return data.getTotal();
        }

        public List<Area> getList() {
            return data.getList();
        }
    }

    public static class Request extends BaseSignRequest<Response> {
        /**
         * 父级编号	不可  “0”表示获取省级信息
         */
        @SerializedName("ParentCode")
        @RequestArgs
        private String parentCode;

        public Request(String parentCode) {
            super(HttpAddress.REGION_GET_LIST, new Response());
            this.parentCode = parentCode;
        }
    }

    private static class Data {
        /**
         * 总记录数
         */
        @SerializedName("Total")
        private Integer total;

        /**
         * 地区列表
         */
        @SerializedName("List")
        private List<Area> list;


        /**
         * 总记录数
         */
        public Integer getTotal() {
            return total;
        }

        /**
         * 地区列表
         */
        public List<Area> getList() {
            return list;
        }


    }

    public static class Area {
        /**
         * 区域编号	不可
         */
        @SerializedName("Code")
        private String code;

        /**
         * 区域名称	不可
         */
        @SerializedName("Name")
        private String name;

        /**
         * 级别	不可
         */
        @SerializedName("Level")
        private Integer level;


        /**
         * 区域编号	不可
         */
        public String getCode() {
            return code;
        }

        /**
         * 区域名称	不可
         */
        public String getName() {
            return name;
        }

        /**
         * 级别	不可
         */
        public Integer getLevel() {
            return level;
        }


    }


}

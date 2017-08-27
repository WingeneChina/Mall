package cn.wingene.mallxm.purchase.ask;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.http.requestargs.RequestArgs;
import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;
import cn.wingene.mallxm.purchase.ask.AskAddressList.AddressItem;

/**
 * Created by Wingene on 2017/8/27.
 */

public class AskAddressSave {
    public static class Response extends MyBaseResponse {
        //       public Data data;
        //       @Override
        //       protected void initData(JsonElement json) {
        //           data = AKit.getGson().fromJson(json,Data.class);
        //       }
    }

    public static class Request extends BaseSignRequest<Response> {

        public Request(AddressItem item) {
            super(HttpAddress.ADDRESS_EDIT, new Response());
            this.id = item.getId();
            this.isDefault = item.getIsDefault();
            this.mobile = item.getMobile();
            this.consignee = item.getConsignee();
            this.region = item.getRegion();
            this.regionCode = item.getRegionCode();
            this.address = item.getAddress();
        }

        /**
         * 地址ID	不可 0添加 >0编辑=原纪录ID
         */
        @SerializedName("Id")
        @RequestArgs
        private Integer id;

        /**
         * 是否默认地址	不可
         */
        @SerializedName("IsDefault")
        @RequestArgs
        private Boolean isDefault;

        /**
         * 手机号码	不可
         */
        @SerializedName("Mobile")
        @RequestArgs
        private String mobile;

        /**
         * 联系人	不可
         */
        @SerializedName("Consignee")
        @RequestArgs
        private String consignee;

        /**
         * 区域名称	不可
         */
        @SerializedName("Region")
        @RequestArgs
        private String region;

        /**
         * 区域编码	不可
         */
        @SerializedName("RegionCode")
        @RequestArgs
        private String regionCode;

        /**
         * 地址详情	不可
         */
        @SerializedName("Address")
        @RequestArgs
        private String address;


    }

    //    private static class Data {
    //
    //
    //    }


}

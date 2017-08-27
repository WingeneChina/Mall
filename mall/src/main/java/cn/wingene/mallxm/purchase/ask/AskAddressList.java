package cn.wingene.mallxm.purchase.ask;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import junze.androidxf.kit.AKit;

import cn.wingene.mallxf.http.Ask.BaseSignRequest;
import cn.wingene.mallxf.http.Ask.MyBaseResponse;
import cn.wingene.mallxm.purchase.bean.able.IAddress;

/**
 * Created by Wingene on 2017/8/27.
 */

public class AskAddressList {

    public static class Response extends MyBaseResponse {
        private Data data;

        @Override
        protected void initData(JsonElement json) {
            data = AKit.getGson().fromJson(json, Data.class);
        }

        public List<AddressItem> getList() {
            return data.getList();
        }
    }

    public static class Request extends BaseSignRequest<Response> {


        public Request() {
            super(HttpAddress.ADDRESS_LIST, new Response());
        }
    }

    private static class Data {
        /**
         * 总记录数
         */
        @SerializedName("Total")
        private Integer total;

        /**
         * 收货地址列表
         */
        @SerializedName("List")
        private List<AddressItem> list;


        /**
         * 总记录数
         */
        public Integer getTotal() {
            return total;
        }

        /**
         * 收货地址列表
         */
        public List<AddressItem> getList() {
            return list;
        }


    }

    public static class AddressItem implements IAddress {
        /**
         * 地址ID	不可
         */
        @SerializedName("Id")
        private Integer id;

        /**
         * 是否默认地址	不可
         */
        @SerializedName("IsDefault")
        private Boolean isDefault;

        /**
         * 手机号码	不可
         */
        @SerializedName("Mobile")
        private String mobile;

        /**
         * 联系人	不可
         */
        @SerializedName("Consignee")
        private String consignee;

        /**
         * 区域名称	不可
         */
        @SerializedName("Region")
        private String region;

        /**
         * 区域编码	不可
         */
        @SerializedName("RegionCode")
        private String regionCode;

        /**
         * 地址详情	不可
         */
        @SerializedName("Address")
        private String address;


        public AddressItem(Integer id, Boolean isDefault, String mobile, String consignee, String region, String
                regionCode, String address) {
            this.id = id;
            this.isDefault = isDefault;
            this.mobile = mobile;
            this.consignee = consignee;
            this.region = region;
            this.regionCode = regionCode;
            this.address = address;
        }

        /**
         * 地址ID	不可
         */
        public Integer getId() {
            return id;
        }

        /**
         * 是否默认地址	不可
         */
        public Boolean getIsDefault() {
            return isDefault;
        }

        /**
         * 手机号码	不可
         */
        public String getMobile() {
            return mobile;
        }

        /**
         * 联系人	不可
         */
        public String getConsignee() {
            return consignee;
        }

        /**
         * 区域名称	不可
         */
        public String getRegion() {
            return region;
        }

        /**
         * 区域编码	不可
         */
        public String getRegionCode() {
            return regionCode;
        }

        /**
         * 地址详情	不可
         */
        public String getAddress() {
            return address;
        }


    }


}

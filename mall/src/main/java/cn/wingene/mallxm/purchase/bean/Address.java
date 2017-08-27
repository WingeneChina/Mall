package cn.wingene.mallxm.purchase.bean;

import com.google.gson.annotations.SerializedName;

import cn.wingene.mallxm.purchase.bean.able.IAddress;

/**
 * Created by Wingene on 2017/8/27.
 */
public class Address implements IAddress {
    /**
     * 地址ID	不可
     */
    @SerializedName("Id")
    private Integer id;

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


    /**
     * 地址ID	不可
     */
    public Integer getId() {
        return id;
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

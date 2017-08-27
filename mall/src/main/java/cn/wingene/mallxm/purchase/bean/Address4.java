package cn.wingene.mallxm.purchase.bean;

import com.google.gson.annotations.SerializedName;

import cn.wingene.mallxm.purchase.bean.able.IAddress4;

/**
 * Created by Wingene on 2017/8/27.
 */
public class Address4 implements IAddress4 {
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
     * 地址详情	不可
     */
    @SerializedName("Address")
    private String address;


    /**
     * 手机号码	不可
     */
    @Override
    public String getMobile() {
        return mobile;
    }

    /**
     * 联系人	不可
     */
    @Override
    public String getConsignee() {
        return consignee;
    }

    /**
     * 区域名称	不可
     */
    @Override
    public String getRegion() {
        return region;
    }

    /**
     * 地址详情	不可
     */
    @Override
    public String getAddress() {
        return address;
    }


}

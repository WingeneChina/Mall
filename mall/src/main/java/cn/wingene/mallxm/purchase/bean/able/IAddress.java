package cn.wingene.mallxm.purchase.bean.able;

/**
 * Created by Wingene on 2017/8/26.
 */
public interface IAddress extends IAddress4{
        /**
         * 地址ID	不可
         */
        Integer getId();

        /**
         * 手机号码	不可
         */
        String getMobile();

        /**
         * 联系人	不可
         */
        String getConsignee();

        /**
         * 区域名称	不可
         */
        String getRegion();

        /**
         * 区域编码	不可
         */
        String getRegionCode();

        /**
         * 地址详情	不可
         */
        String getAddress();
}

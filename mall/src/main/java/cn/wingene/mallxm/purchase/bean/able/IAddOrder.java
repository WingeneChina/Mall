package cn.wingene.mallxm.purchase.bean.able;

import java.util.List;

import cn.wingene.mallxm.purchase.bean.Account;
import cn.wingene.mallxm.purchase.bean.Address;
import cn.wingene.mallxm.purchase.bean.Product;

/**
 * Created by Wingene on 2017/8/27.
 */
public interface IAddOrder {
        /**
         * 商品信息列表	不可
         */
        List<Product> getProductList();

        /**
         * 会员账户	不可
         */
        Account getAccount();

        /**
         * 收货地址	可空
         */
        Address getAddress();

        /**
         * 总购买数量	不可
         */
        Integer getSumNumber();

        /**
         * 总金额	不可
         */
        Double getSumPrice();

        /**
         * 折扣率	不可
         */
        Double getDiscount();

        /**
         * 折扣金额	不可
         */
        Double getDiscountPrice();

        /**
         * 运费金额	不可
         */
        Double getDeliveryFee();

        /**
         * 实付金额	不可
         */
        Double getPayPrice();
}

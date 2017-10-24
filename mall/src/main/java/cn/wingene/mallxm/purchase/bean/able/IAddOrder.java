package cn.wingene.mallxm.purchase.bean.able;

import java.util.List;

import cn.wingene.mallxm.purchase.bean.Product;

/**
 * Created by Wingene on 2017/8/27.
 */
public interface IAddOrder extends IEasyOrder {
        /**
         * 商品信息列表	不可
         */
        List<Product> getProductList();

        /**
         * 总购买数量	不可
         */
        Integer getSumNumber();

        /**
         * 折扣率	不可
         */
        Double getDiscount();

        /**
         * 折扣金额	不可
         */
        Double getDiscountPrice();



}

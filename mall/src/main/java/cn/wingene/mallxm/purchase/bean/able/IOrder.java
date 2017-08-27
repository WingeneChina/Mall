package cn.wingene.mallxm.purchase.bean.able;

import java.util.List;

import cn.wingene.mallxm.purchase.bean.Account;
import cn.wingene.mallxm.purchase.bean.Address4;
import cn.wingene.mallxm.purchase.bean.OrderProductItem;

/**
 * Created by Wingene on 2017/8/27.
 */
public interface IOrder {
        /**
         * 订单单号	不可
         */
        String getNo();

        /**
         * 总金额	不可
         */
        Double getSumPrice();

        /**
         * 总数量	不可
         */
        Double getSumNumber();

        /**
         * 折扣率	不可
         */
        Double getDiscount();

        /**
         * 折扣金额	不可
         */
        Double getDiscountPrice();

        /**
         * 运费	不可
         */
        Double getDeliveryFee();

        /**
         * 支付金额	不可
         */
        Double getPayPrice();

        /**
         * 可使用券金额	不可
         */
        Double getAcceptCouponPrice();

        /**
         * 订单状态	不可
         */
        Integer getState();

        /**
         * 状态说明	不可 详见1.6订单状态
         */
        String getStateDesp();

        /**
         * 商品信息列表	不可
         */
        List<OrderProductItem> getProductList();

        /**
         * 会员账户	不可
         */
        Account getAccount();

        /**
         * 收货地址	不可
         */
        Address4 getAddress();
}

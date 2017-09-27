package cn.wingene.mallxm.purchase.bean.able;

import java.util.List;

import cn.wingene.mallxm.purchase.bean.Account;

/**
 * Created by Wingene on 2017/9/10.
 */
public interface IEasyOrder {
    /**
     * 会员账户	不可
     */
    Account getAccount();

    /**
     * 收货地址	可空
     */
    IAddress4 getAddress();

    /**
     * 总金额	不可
     */
    Double getSumPrice();

    /**
     * 实付金额	不可
     */
    Double getPayPrice();

    Integer getAcceptIntegral();

    List<? extends IOrderProductItem> getOrderProductItem();

    boolean showThirdPart();
}

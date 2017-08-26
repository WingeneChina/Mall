package cn.wingene.mallxm.purchase.ask;

import cn.wingene.mallxf.http.HttpConstant;

/**
 * Created by Wingene on 2017/8/19.
 */

public class HttpAddress extends HttpConstant{
    /**
     * 2.1.2.2、商品详情
     */
    public final static String PRODUCT_DETAIL = SERVER_ADDRESS + "Mall/Product/Detail";

    /**
     * 2.1.2.4、加入购物车
     */
    public final static String CART_ADD = SERVER_ADDRESS + "User/Cart/Add";
    /**
     * 2.1.3.1、立即购买
     */
    public final static String ORDER_BUY_NOW = SERVER_ADDRESS + "Order/BuyNow";

    /**
     * 2.1.3.3、购物车购买
     */
    public final static String ORDER_BUY_CART = SERVER_ADDRESS + "Order/BuyCart";
    /**
     * 2.6、我的购物车
     */
    public final static String CART_LIST = SERVER_ADDRESS + "User/Cart/List";
}

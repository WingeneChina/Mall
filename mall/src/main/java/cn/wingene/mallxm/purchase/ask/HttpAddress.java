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

    /**
     * 2.5.4.1、获取区域列表（省市县）
     */
    public final static String REGION_GET_LIST = SERVER_ADDRESS + "Region/GetList";

    /**
     * 2.5.4.2、获取收货地址列表
     */
    public final static String ADDRESS_LIST = SERVER_ADDRESS + "User/Address/List";
    /**
     * 2.5.4.3、提交收货地址（添加、编辑）
     */
    public final static String ADDRESS_EDIT = SERVER_ADDRESS + "User/Address/Edit";
    /**
     * 2.1.3.2、提交立即购买
     */
    public final static String ORDER_CREATE_BUY_NOW = SERVER_ADDRESS + "Order/CreateBuyNow";
    /**
     * 2.1.3.4、提交购物车购买
     */
    public final static String ORDER_CREATE_BUY_CART = SERVER_ADDRESS + "Order/CreateBuyCart";
    /**
     * 2.1.3.6、提交订单支付
     */
    public final static String ORDER_SUBMIT_PAY_NOW = SERVER_ADDRESS + "Order/SubmitPayNow";

}

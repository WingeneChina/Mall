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
    /**
     * 2.5.2.1、我的订单列表（分页）
     */
    public final static String ORDER_LIST = SERVER_ADDRESS + "User/Order/List";
    /**
     * 2.6.1、编辑购物车（商品数量）
     */
    public final static String CART_EDIT = SERVER_ADDRESS + "User/Cart/Edit";
    /**
     * 2.6.2、删除购物车
     */
    public final static String CART_REMOVE = SERVER_ADDRESS + "User/Cart/Remove";
    /**
     * 2.3.4.5、删除收货地址
     */
    public final static String ADDRESS_REMOVE = SERVER_ADDRESS + "User/Address/Remove";
    /**
     * 2.3.4.6、设置默认收货地址
     */
    public final static String ADDRESS_DEFAULT = SERVER_ADDRESS + "User/Address/Default";
    /**
     * 2.3.2.3、物流详情
     */
    public final static String LOGISTICS_DETAIL = SERVER_ADDRESS + "Logistics/Detail";
    /**
     * 2.5.2.4、取消订单
     */
    public final static String ORDER_CANCEL = SERVER_ADDRESS + "User/Order/Cancel";
    /**
     * 2.5.2.5、确认订单
     */
    public final static String ORDER_CONFIRM = SERVER_ADDRESS + "User/Order/Confirm";
    /**
     * 2.1.3.5、进入订单支付（可跳过）
     */
    public final static String ORDER_PAY_NOW = SERVER_ADDRESS + "Order/PayNow";
    /**
     * 2.5.2.2、订单详情
     */
    public final static String ORDER_DETAIL = SERVER_ADDRESS + "User/Order/Detail";


}

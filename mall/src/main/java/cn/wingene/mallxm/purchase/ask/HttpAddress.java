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

    /**
     * 2.5.3.1、金币
     */
    public final static String INTEGRAL_INDEX = SERVER_ADDRESS + "User/Integral/Index";

    /**
     * 2.5.3.1.1、金币账单明细列表（分页）
     */
    public final static String INTEGRAL_LOG_LIST = SERVER_ADDRESS + "User/Integral/LogList";
    /**
     * 2.5.3.1.2、金币充值
     */
    public final static String INTEGRAL_RECHARGE = SERVER_ADDRESS + "User/Integral/Recharge";
    /**
     * 2.5.3.2、元宝
     */
    public final static String AMOUNT_INDEX = SERVER_ADDRESS + "User/Amount/Index";
    /**
     * 2.5.3.2.1、元宝账单明细列表（分页）
     */
    public final static String AMOUNT_LOG_LIST = SERVER_ADDRESS + "User/Amount/LogList";
    /**
     * 2.5.3.2.2、元宝充值
     */
    public final static String AMOUNT_RECHARGE = SERVER_ADDRESS + "User/Amount/Recharge";
    /**
     * 2.5.3.2.3.7、设置默认银行卡
     */
    public final static String BANK_CARD_DEFAULT = SERVER_ADDRESS + "User/BankCard/Default";
    /**
     * 2.5.3.2.3.6、删除银行卡
     */
    public final static String BANK_CARD_REMOVE = SERVER_ADDRESS + "User/BankCard/Remove";
    /**
     * 2.5.3.2.3.5、提交银行卡（添加、编辑）
     */
    public final static String BANK_CARD_EDIT = SERVER_ADDRESS + "User/BankCard/Edit";
    /**
     * 2.5.3.2.3.6、获取银行卡详情
     */
    public final static String BANK_CARD_DETAIL = SERVER_ADDRESS + "User/BankCard/Detail";
    /**
     * 2.5.3.2.3.4、进入银行卡（添加、编辑）
     */
    public final static String BANK_CARD_GET_EDIT = SERVER_ADDRESS + "User/BankCard/GetEdit";
    /**
     * 2.5.3.2.3.3、获取银行卡址列表
     */
    public final static String BANK_CARD_LIST = SERVER_ADDRESS + "User/BankCard/List";
    /**
     * 2.5.3.2.3.2、提交提现
     */
    public final static String DEPOSIT_CREATE_AMOUNT = SERVER_ADDRESS + "User/Deposit/CreateAmount";
    /**
     * 2.5.3.2.3.1、进入提现
     */
    public final static String DEPOSIT_GET_AMOUNT = SERVER_ADDRESS + "User/Deposit/GetAmount";

    /**
     * 2.1.2.3、收藏商品
     */
    public final static String FAVORITES_SET = SERVER_ADDRESS + "User/Favorites/Set";


}

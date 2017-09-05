package cn.wingene.mallxm;

import android.app.Activity;
import android.content.Context;

import cn.wingene.mallxm.purchase1.CashActivity;
import junze.androidxf.core.Agent.Major.IntentBuilder;

import cn.wingene.mallxm.account.LoginActivity;
import cn.wingene.mallxm.account.RegisterActivity;
import cn.wingene.mallxm.account.RegisterFirstStepActivity;
import cn.wingene.mallxm.display.home.setting.SettingActivity;
import cn.wingene.mallxm.purchase.AddressManagerActivity;
import cn.wingene.mallxm.purchase.CommodityDetailActivity;
import cn.wingene.mallxm.purchase.OrderListActivity;
import cn.wingene.mallxm.purchase.ShoppingCartActivity;


/**
 * Created by Wingene on 2017/8/12.
 */

public class JumpHelper {

    @Deprecated
    public static void startCommodityDetailActivity(Context context) {
        startCommodityDetailActivity(context, 1, 0);
    }

    /**
     * 进入商品详情界面
     *
     * @param src
     * @param productId   商品ID
     * @param promotionId 促销活动ID
     */
    public static void startCommodityDetailActivity(Context src, int productId, int promotionId) {
        CommodityDetailActivity.major.startActivity(src, productId, promotionId);
    }

    public static void startCommodityDetailActivity(Context src, int productId) {
        CommodityDetailActivity.major.startActivity(src, productId, 0);

    }

    /**
     * 进入我的订单界面
     *
     * @param src
     * @param state 订单状态 -1、全部 0、待付款 1、待发货 3、待确认 4、交易完成 5、交易完成已评价
     */
    public static void startOrderListActivity(Context src, int state) {
        OrderListActivity.major.startForOrderState(src, state);
    }

    /**
     * 进入购物车界面
     *
     * @param context
     */
    public static void startShoppingCartActivity(Context context) {
        create(context, ShoppingCartActivity.class).startActivity();
    }

    /**
     * 地址管理界面
     *
     * @param context
     */
    public static void startAddressManagerActivity(Context context) {
        create(context, AddressManagerActivity.class).startActivity();
    }

    ////////////////////////////////////////////////////////////////////
    /////////////                                          /////////////
    ////////////////////////////////////////////////////////////////////

    public static void startLoginActivity(Context context) {
        create(context, LoginActivity.class).startActivity();
    }

    public static void starRegisterFirstStepActivity(Context context) {
        create(context, RegisterFirstStepActivity.class).startActivity();
    }

    public static void starRegisterActivity(Context context) {
        create(context, RegisterActivity.class).startActivity();
    }

//    public static void startMainActivity(Context context) {
//        create(context, MainActivity.class).startActivity();
//    }

    /**
     * 开始设置
     *
     * @param context
     */
    public static void startSettingActivity(Context context) {
        create(context, SettingActivity.class).startActivity();

    }

    /**
     * 开始提现
     *
     * @param context
     */
    public static void startCashActivity(Context context) {
        create(context, CashActivity.class);
    }

    /**
     * @param context
     * @param cls
     * @return
     */
    private static IntentBuilder create(Context context, Class<? extends Activity> cls) {
        return new IntentBuilder(context).setClass(cls);
    }
}

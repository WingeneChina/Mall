package cn.wingene.mallxm;

import android.app.Activity;
import android.content.Context;

import cn.wingene.mallxm.purchase.AddressAddActivity;
import cn.wingene.mallxm.purchase.AddressManagerActivity;
import cn.wingene.mallxm.purchase.CommodityDetailActivity;
import cn.wingene.mallxm.purchase.OrderAddActivity;
import cn.wingene.mallxm.purchase.ShoppingCartActivity;

import junze.androidxf.core.Agent.Major.IntentBuilder;


/**
 * Created by Wingene on 2017/8/12.
 */

public class JumpHelper {

    @Deprecated
    public static void startCommodityDetailActivity(Context context) {
        startCommodityDetailActivity(context, 1, 0);
    }

    public static void startCommodityDetailActivity(Context src, int productId, int promotionId) {
        CommodityDetailActivity.major.startActivity(src, productId, promotionId);
    }


    public static void startShoppingCartActivity(Context context) {
        create(context, ShoppingCartActivity.class).startActivity();
    }
    public static void startOrderAddActivity(Context context) {
        create(context, OrderAddActivity.class).startActivity();
    }
    public static void startAddressManagerActivity(Context context) {
        create(context, AddressManagerActivity.class).startActivity();
    }
    public static void startAddressAddActivity(Context context) {
        create(context, AddressAddActivity.class).startActivity();
    }






    private static IntentBuilder create(Context context, Class<? extends Activity> cls) {
        return new IntentBuilder(context).setClass(cls);
    }
}

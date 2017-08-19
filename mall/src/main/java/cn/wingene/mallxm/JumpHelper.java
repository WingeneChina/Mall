package cn.wingene.mallxm;

import android.app.Activity;
import android.content.Context;

import cn.wingene.mallxm.purchase.CommodityDetailActivity;
import cn.wingene.mallxm.purchase.OrderAddActivity;
import cn.wingene.mallxm.purchase.ShoppingCartActivity;

import junze.androidxf.core.Agent.Major.IntentBuilder;


/**
 * Created by Wingene on 2017/8/12.
 */

public class JumpHelper {

    public static void startCommodityDetailActivity(Context context) {
        create(context,CommodityDetailActivity.class).startActivity();
    }
    public static void startShoppingCartActivity(Context context) {
        create(context, ShoppingCartActivity.class).startActivity();
    }
    public static void startOrderAddActivity(Context context) {
        create(context, OrderAddActivity.class).startActivity();
    }






    private static IntentBuilder create(Context context, Class<? extends Activity> cls) {
        return new IntentBuilder(context).setClass(cls);
    }
}

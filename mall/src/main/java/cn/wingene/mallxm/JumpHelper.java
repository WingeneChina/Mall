package cn.wingene.mallxm;

import android.content.Context;

import cn.wingene.mallx.frame.IntentBuilder;
import cn.wingene.mallxm.purchase.CommodityDetailActivity;


/**
 * Created by Wingene on 2017/8/12.
 */

public class JumpHelper {

    public static void startCommodityDetailActivity(Context context) {
        IntentBuilder.create(context).putClass(CommodityDetailActivity.class).startActivity();
    }
}

package cn.wingene.mallxm.purchase.tool;

import android.view.View;
import android.widget.TextView;

import junze.java.util.StringUtil;

/**
 * Created by Wingene on 2017/9/30.
 */

public class ShowTool {

    public static void showPrice(TextView tv, Double price, boolean isJiaPei) {
        showPrice(tv, "%s", price, isJiaPei);
    }

    public static void showPrice(TextView tv, String format, Double price, boolean isJiaPei) {
        String strPrice = isJiaPei ? String.format("%s元宝", price) : String.format("￥%s", price);
        tv.setText(String.format(format, strPrice));
    }

    public static void showAcceptIntegralOrGone(TextView tv, Integer acceptIntegral, boolean isJiaPei) {
        if (acceptIntegral != null && acceptIntegral != 0 && !isJiaPei) {
            tv.setVisibility(View.VISIBLE);
            tv.setText("可抵金币¥" + acceptIntegral);
        } else {
            tv.setVisibility(View.GONE);
        }
    }
    public static void showAcceptIntegralOrGone(TextView tv, String acceptIntegral, boolean isJiaPei) {
        if (StringUtil.isValid(acceptIntegral) && !isJiaPei) {
            tv.setVisibility(View.VISIBLE);
            tv.setText("可抵金币¥" + acceptIntegral);
        } else {
            tv.setVisibility(View.GONE);
        }
    }

}

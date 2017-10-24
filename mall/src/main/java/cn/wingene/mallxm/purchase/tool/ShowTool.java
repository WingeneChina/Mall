package cn.wingene.mallxm.purchase.tool;

import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.limecn.ghmall.R;

import junze.java.util.StringUtil;

/**
 * Created by Wingene on 2017/9/30.
 */

public class ShowTool {

//    public

    public static void showSumPrice(TextView tvSum, double sum, TextView tvPay, double pay, boolean isJiaPei) {
        tvSum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        ShowTool.showPrice(tvSum, sum, isJiaPei);
        tvSum.setVisibility(sum != pay ? View.VISIBLE : View.GONE);
        ShowTool.showPrice(tvPay, pay, isJiaPei);
    }

    public static void showPrice(TextView tv, Double price, boolean isJiaPei) {
        showPrice(tv, "%s", price, isJiaPei);
    }

    public static void showPrice(TextView tv, String format, Double price, boolean isJiaPei) {
        String strPrice = isJiaPei ? String.format("%s元宝", price) : String.format("￥%s", price);
        tv.setText(String.format(format, strPrice));
    }

    public static void showDeliveryFee(TextView tv,double deliveryFee){
        if(deliveryFee != 0){
            tv.setText(String.format("￥%s",deliveryFee));
            tv.setTextColor(tv.getResources().getColor(R.color.red));
        }else{
            tv.setText("免费");
            tv.setTextColor(tv.getResources().getColor(R.color.black));
        }
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

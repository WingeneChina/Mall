package cn.wingene.mall.util;

import java.math.BigDecimal;

/**
 * Created by Wingene on 2017/9/11.
 */

public class MathUtil {
    public static double round2(double value){
        return new BigDecimal(value).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}

package cn.wingene.mallxm.purchase.tool;

import java.math.BigDecimal;

import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import junze.java.able.IBuilder;
import junze.java.able.ICallBack;

import junze.android.ui.EditViewDialogDeclare.OnEditCompleteListener;
import junze.android.ui.EditViewDialogDeclare.Option;
import junze.androidxf.core.Agent;

/**
 * Created by Wingene on 2017/8/31.
 */

public class NumberTool {
    public static void bindInteger(final Agent agent, final String title, final int min, final
    IBuilder<Integer> bCurrent, final IBuilder<Integer> bMax, final TextView tvReduce, final TextView tvNumber,
            final TextView tvIncrease, final ICallBack<Integer> callback) {
        bindBigDecimal(agent, title, new BigDecimal(min), new IBuilder<BigDecimal>() {
            @Override
            public BigDecimal build() {
                return BigDecimal.valueOf(bCurrent.build());
            }
        }, new IBuilder<BigDecimal>() {
            @Override
            public BigDecimal build() {
                return BigDecimal.valueOf(bMax.build());
            }
        }, tvReduce, tvNumber, tvIncrease, new ICallBack<BigDecimal>() {
            @Override
            public void callBack(BigDecimal value) {
                callback.callBack(value.intValue());
            }
        });
    }

    public static void bindDouble(final Agent agent, final String title, final double min, final IBuilder<Double>
            bCurrent, final IBuilder<Double> bMax, final TextView tvReduce, final TextView tvNumber, final TextView
            tvIncrease, final ICallBack<Double> callback) {
        bindBigDecimal(agent, title, new BigDecimal(min), new IBuilder<BigDecimal>() {
            @Override
            public BigDecimal build() {
                return BigDecimal.valueOf(bCurrent.build());
            }
        }, new IBuilder<BigDecimal>() {
            @Override
            public BigDecimal build() {
                return BigDecimal.valueOf(bMax.build());
            }
        }, tvReduce, tvNumber, tvIncrease, new ICallBack<BigDecimal>() {
            @Override
            public void callBack(BigDecimal value) {
                callback.callBack(value.doubleValue());
            }
        });
    }

    private static void bindBigDecimal(final Agent agent, final String title, final BigDecimal min, final
    IBuilder<BigDecimal> bCurrent, final IBuilder<BigDecimal> bMax, final TextView tvReduce, final TextView
            tvNumber, final TextView tvIncrease, final ICallBack<BigDecimal> callback) {
        tvReduce.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal num = bCurrent.build();
                num = num.subtract(BigDecimal.ONE);
                callback.callBack(num.compareTo(min) == 1 ? num : min);
            }
        });
        tvIncrease.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal num = bCurrent.build();
                num = num.add(BigDecimal.ONE);
                BigDecimal max = bMax.build();
                callback.callBack(num.compareTo(max) == -1 ? num : max);
            }
        });
        tvNumber.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Option option = Option.createOption();
                option.inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL;
                agent.showEditViewDialog(title, option, new OnEditCompleteListener() {
                    @Override
                    public void onEditComplete(String s) {
                        try {
                            BigDecimal b = new BigDecimal(s);
                            if (b.compareTo(min) != -1 && b.compareTo(bMax.build()) != 1) {
                                callback.callBack(b);
                            } else {
                                agent.showToast("您输入的值无效!");
                            }
                        } catch (Exception e) {

                        }
                    }
                });
            }
        });
    }
}

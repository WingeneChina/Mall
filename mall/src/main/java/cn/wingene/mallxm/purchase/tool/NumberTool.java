package cn.wingene.mallxm.purchase.tool;

import java.math.BigDecimal;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import junze.java.able.IBuilder;
import junze.java.able.ICallBack;

import junze.androidxf.core.Agent;

import cn.wingene.mall.R;
import cn.wingene.mallx.frame.ui.EditViewDialogDeclare.OnEditCompleteListener;
import cn.wingene.mallx.frame.ui.EditViewDialogDeclare.Option;
import cn.wingene.mallxf.MyAgent;

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
                EditText et = new EditText(agent.getActivity());
                et.setBackgroundResource(R.drawable.shape_stroke_darkgray_buttom);
                if (agent instanceof MyAgent) {
                    MyAgent mg = (MyAgent) agent;
                    mg.showEditViewDialog2(title, option, new OnEditCompleteListener() {
                        @Override
                        public void onEditComplete(String record) {
                            try {
                                BigDecimal b = new BigDecimal(record);
                                BigDecimal max = bMax.build();
                                if (b.compareTo(min) == -1) {
                                    agent.showToast("您输入的值太小，最小值为%s!", min);
                                } else if (b.compareTo(max) == 1) {
                                    agent.showToast("您输入的值太大，最大值为%s!", max);
                                } else {
                                    callback.callBack(b);
                                }
                            } catch (Exception e) {

                            }
                        }
                    });
                }
            }
        });
    }

    private static void bindBigDecimal(final Agent agent, final String title, final BigDecimal min, final
    IBuilder<BigDecimal> bCurrent, final IBuilder<BigDecimal> bMax, final TextView tvReduce, final EditText
            etNumber, final TextView tvIncrease, final ICallBack<BigDecimal> callback) {
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
        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    BigDecimal b = new BigDecimal(etNumber.getText().toString());
                    if(b.compareTo(bCurrent.build())!=0){
                        BigDecimal max = bMax.build();
                        if(b.compareTo(max) == 1){
                            callback.callBack(max);
                            agent.showToast("最大值%s",max);
                        }else if(b.compareTo(min)==-1 ){
                            callback.callBack(min);
                            agent.showToast("最小值%s",max);
                        }else{
                            callback.callBack(b);
                        }
                    }
                }catch (Exception e){

                }
            }
        });
    }

    public static void bindInteger(final Agent agent, final String title, final int min, final
    IBuilder<Integer> bCurrent, final IBuilder<Integer> bMax, final TextView tvReduce, final EditText tvNumber,
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
}

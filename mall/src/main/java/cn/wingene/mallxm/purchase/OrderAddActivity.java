package cn.wingene.mallxm.purchase;

import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;

import junze.java.able.IBuilder;
import junze.java.able.ICallBack;

import cn.wingene.mall.R;
import cn.wingene.mallx.universalimageloader.ImageHelper;
import cn.wingene.mallxf.http.Ask.NeedLoginException;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxm.JumpHelper;
import cn.wingene.mallxm.purchase.ask.AskBuyCart.BuyCarData;
import cn.wingene.mallxm.purchase.ask.AskBuyNow.BuyNowData;
import cn.wingene.mallxm.purchase.ask.AskOrderCreateBuyNow;
import cn.wingene.mallxm.purchase.ask.AskOrderCreateBuyNow.Response;
import cn.wingene.mallxm.purchase.ask.AskOrderCreateBuyCart;
import cn.wingene.mallxm.purchase.ask.AskSubmitPayNow;
import cn.wingene.mallxm.purchase.bean.Order;
import cn.wingene.mallxm.purchase.bean.able.IAddOrder;
import cn.wingene.mallxm.purchase.bean.Account;
import cn.wingene.mallxm.purchase.bean.able.IAddress;
import cn.wingene.mallxm.purchase.bean.able.IProduct;
import cn.wingene.mallxm.purchase.dialog.BottomPayChoiseDialog;
import cn.wingene.mallxm.purchase.tool.NumberTool;

import junze.widget.HightMatchListView;
import junze.widget.Tile;

import junze.android.ui.EditViewDialogDeclare.OnEditCompleteListener;
import junze.android.ui.EditViewDialogDeclare.Option;
import junze.android.ui.ItemViewHolder;
import junze.androidxf.core.Agent;

/**
 * Created by Wingene on 2017/8/14.
 */

public class OrderAddActivity extends MyBaseActivity {
    public static Major major = new Major(OrderAddActivity.class);
    public static final int RC_ADDRESS_ADD = 1000;
    public static final int RC_ADDRESS_CHOISE = 2000;
    Params mParams;
    private IAddress mAddress;
    private double mSumPrice;
    private double mPayPrice;
    private Account mAccount;
    private double mAmount;
    private int mIntegral;


    private ItemHolder mItemHolder;
    private BottomPayChoiseDialog mPayChoiseDialog;

    private Tile tlBack;
    private LinearLayout llytAddress;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvAddress;
    private HightMatchListView lvOrder;
    private TextView tvTotal;
    private TextView tvIntegral;
    private LinearLayout llytIntegralNumber;
    private TextView tvIntegralReduce;
    private TextView tvIntegralNumber;
    private TextView tvIntegralIncrease;
    private TextView tvAmount;
    private LinearLayout llytAmountNumber;
    private TextView tvAmountReduce;
    private TextView tvAmountNumber;
    private TextView tvAmountIncrease;
    private TextView tvRealTotal;
    private TextView tvPay;

    protected void initComponent(){
        tlBack = (Tile) super.findViewById(R.id.tl_back);
        llytAddress = (LinearLayout) super.findViewById(R.id.llyt_address);
        tvName = (TextView) super.findViewById(R.id.tv_name);
        tvPhone = (TextView) super.findViewById(R.id.tv_phone);
        tvAddress = (TextView) super.findViewById(R.id.tv_address);
        lvOrder = (HightMatchListView) super.findViewById(R.id.lv_order);
        tvTotal = (TextView) super.findViewById(R.id.tv_total);
        tvIntegral = (TextView) super.findViewById(R.id.tv_integral);
        llytIntegralNumber = (LinearLayout) super.findViewById(R.id.llyt_integral_number);
        tvIntegralReduce = (TextView) super.findViewById(R.id.tv_integral_reduce);
        tvIntegralNumber = (TextView) super.findViewById(R.id.tv_integral_number);
        tvIntegralIncrease = (TextView) super.findViewById(R.id.tv_integral_increase);
        tvAmount = (TextView) super.findViewById(R.id.tv_amount);
        llytAmountNumber = (LinearLayout) super.findViewById(R.id.llyt_amount_number);
        tvAmountReduce = (TextView) super.findViewById(R.id.tv_amount_reduce);
        tvAmountNumber = (TextView) super.findViewById(R.id.tv_amount_number);
        tvAmountIncrease = (TextView) super.findViewById(R.id.tv_amount_increase);
        tvRealTotal = (TextView) super.findViewById(R.id.tv_real_total);
        tvPay = (TextView) super.findViewById(R.id.tv_pay);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_add);
        initComponent();
        mParams = major.parseParams(this);
        mItemHolder = new ItemHolder(this, lvOrder);
        IAddOrder bean = mParams.isBuyNow() ? mParams.buyNowData : mParams.buyCarData;
        mAddress = bean.getAddress();
        mItemHolder.addAll(bean.getProductList());
        mAccount = bean.getAccount();
        mAddress = bean.getAddress();
        mSumPrice = bean.getSumPrice();
        mPayPrice = bean.getPayPrice();
        refreshUI();

        tlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        llytAddress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressManagerActivity.major.startForChoise(getActivity(), RC_ADDRESS_CHOISE);
            }
        });
        tvPay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPayChoiseDialog == null) {
                    mPayChoiseDialog = new BottomPayChoiseDialog(getActivity());
                }
                mPayChoiseDialog.show(getAgent(), mPayPrice, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPayChoiseDialog.hide();
                    }
                }, new ICallBack<Boolean>() {
                    @Override
                    public void callBack(final Boolean isAlipay) {
                        if (mParams.isBuyNow()) {
                            ask(new AskOrderCreateBuyNow.Request(mParams.buyNowData.getProduct(), mAddress.getId()) {
                                @Override
                                public void updateUI(Response rsp) {
                                    askPay(rsp.data, isAlipay);
                                }
                            });
                        } else {
                            ask(new AskOrderCreateBuyCart.Request(mParams.cartIds, mAddress.getId()) {
                                @Override
                                public void updateUI(AskOrderCreateBuyCart.Response rsp) {
                                    askPay(rsp.data, isAlipay);
                                }
                            });
                        }
                    }
                });

            }
        });
        if (mAddress == null) {
            AddressAddActivity.major.startForResult(getActivity(), RC_ADDRESS_ADD);
        }
        bindNumber();
    }

    private void bindNumber() {
        tvAmountNumber.setText(String.format("%.1f", mAmount));
        NumberTool.bindDouble(getAgent(), "请输入使用游币的值", 0, new IBuilder<Double>() {
            @Override
            public Double build() {
                return mAmount;
            }
        }, new IBuilder<Double>() {
            @Override
            public Double build() {
                return Math.min(mAccount.getAmount(), mPayPrice - mAmount - mIntegral / 100);
            }
        }, tvAmountReduce, tvAmountNumber, tvAmountIncrease, new ICallBack<Double>() {
            @Override
            public void callBack(Double aDouble) {
                mAmount = aDouble;
                tvAmountNumber.setText(String.format("%.1f", mAmount));
            }
        });

        tvIntegral.setText(String.format("%s", mIntegral));
        NumberTool.bindInteger(getAgent(), "请输入使用应币的值", 0, new IBuilder<Integer>() {
            @Override
            public Integer build() {
                return mIntegral;
            }
        }, new IBuilder<Integer>() {
            @Override
            public Integer build() {
                return Math.min(mAccount.getIntegral(), (int) (mPayPrice - mAmount - mIntegral / 100));
            }
        }, tvIntegralReduce, tvIntegralNumber, tvIntegralIncrease, new ICallBack<Integer>() {
            @Override
            public void callBack(Integer integer) {
                mIntegral = integer;
                tvIntegral.setText(String.format("%s", mIntegral));
            }
        });
    }


    public void askPay(Order order, final boolean isAlipay) {
        ask("提交中...",false,new AskSubmitPayNow.Request(order.getNo(), isAlipay, 0, mAmount, mIntegral) {
            @Override
            public void updateUI(final AskSubmitPayNow.Response rsp) {
                if (rsp.data.getPayState() == 1) {
                    jumpToPayResultActivityAndFinish();
                    return;
                }
                if (isAlipay) {
                    Runnable payRunnable = new Runnable() {

                        @Override
                        public void run() {
                            //调用支付宝
                            PayTask payTask = new PayTask(getActivity());
                            Map<String, String> result = payTask.payV2(rsp.data.getPayDataForAlipay().getSignText()
                                    , true);
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    };
                    // 必须异步调用
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                } else {
                    showMsgDialog("提示", "微信支付功能开发中...", "我知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            jumpToWaitPayActivityAndFinish();
                        }
                    });
                }
            }

            @Override
            protected void updateUIWhenException(Exception e) {
                if(e instanceof NeedLoginException){
                    jumpToWaitPayActivityAndFinish();
                    JumpHelper.startLoginActivity(getActivity());
                }else {
                    showToast(e);
                    jumpToWaitPayActivityAndFinish();
                }
            }
        });
    }

    public static final int SDK_PAY_FLAG = 2000;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case SDK_PAY_FLAG: {
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                String resultStatus = payResult.getResultStatus();// 同步返回需要验证的信息
                if (TextUtils.equals(resultStatus, "9000")) {
                    showToast("支付成功");
                    jumpToPayResultActivityAndFinish();
                } else {
                    // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服<span style="white-space:pre">
                    // </span>务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        showToast("支付结果确认中");
                        jumpToPayResultActivityAndFinish();
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        showToast("支付取消");
                        jumpToWaitPayActivityAndFinish();
                    } else if (TextUtils.equals(resultStatus, "6002")) {
                        showToast("网络异常");
                        jumpToWaitPayActivityAndFinish();
                    } else if (TextUtils.equals(resultStatus, "5000")) {
                        showToast("重复请求");
                        jumpToPayResultActivityAndFinish();
                    } else {
                        showToast("支付失败");
                        jumpToWaitPayActivityAndFinish();
                    }
                }
                break;
            }
            }
        }
    };

    public void jumpToWaitPayActivityAndFinish() {
        finish();
        JumpHelper.startOrderListActivity(getActivity(), 0);
    }

    public void jumpToPayResultActivityAndFinish() {
        finish();
        JumpHelper.startOrderListActivity(getActivity(), 1);
    }


    public void refreshUI() {
        setAddress(mAddress);
        mItemHolder.notifyDataSetChanged();
        tvTotal.setText(String.format("￥%.2f", mSumPrice));
        setAccount(mAccount);
        tvRealTotal.setText(String.format("￥%.2f", mPayPrice));
    }


    public void setAddress(IAddress address) {
        tvName.setText(address != null ? address.getConsignee() : "");
        tvPhone.setText(address != null ? address.getMobile() : "");
        tvAddress.setText(address != null ? address.getRegion() + address.getAddress() : "");
    }

    public void setAccount(Account account) {
        tvAmount.setText(String.format("游币可抵用%s元", account.getAmount()));
        tvIntegral.setText(String.format("应币可抵用%s元", account.getIntegral()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == RC_ADDRESS_ADD) {
            mAddress = AddressAddActivity.major.parseResult(data);
            refreshUI();
        } else if (requestCode == RC_ADDRESS_CHOISE) {
            mAddress = AddressManagerActivity.major.parseResult(data);
            refreshUI();
        }
    }

    private static void bindDoubleNumber(final Agent agent, final String title, final double min, final double
            current, final double max, final TextView tvReduce, final TextView tvNumber, final TextView
            tvIncrease, final String format, final ICallBack<Double> callback) {
        tvReduce.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                double num = Double.parseDouble(tvNumber.getText().toString());
                num = --num >= min ? num : min;
                tvNumber.setText(String.format(format, num));
                callback.callBack(num);
            }
        });
        tvIncrease.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                double num = Double.parseDouble(tvNumber.getText().toString());
                num = ++num <= max ? num : max;
                tvNumber.setText(String.format(format, num));
                callback.callBack(num);
            }
        });
        tvNumber.setText(String.format(format, current));
        tvNumber.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Option option = Option.createOption();
                option.inputType = InputType.TYPE_CLASS_NUMBER;
                agent.showEditViewDialog(title, option, new OnEditCompleteListener() {
                    @Override
                    public void onEditComplete(String s) {
                        try {
                            Double d = Double.parseDouble(s);
                            if (min <= d && d <= max) {
                                tvNumber.setText(String.format(format, d));
                                callback.callBack(d);
                            }
                        } catch (Exception e) {

                        }
                    }
                });
            }
        });
    }

    private static void bindIntNumber(final Agent agent, final String title, final int min, final int current,
            final int max, final TextView tvReduce, final TextView tvNumber, final TextView tvIncrease, final
    String format, final ICallBack<Integer> callback) {
        tvReduce.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(tvNumber.getText().toString());
                num = --num >= min ? num : min;
                tvNumber.setText(String.format(format, num));
                callback.callBack(num);
            }
        });
        tvIncrease.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(tvNumber.getText().toString());
                num = ++num <= max ? num : max;
                tvNumber.setText(String.format(format, num));
                callback.callBack(num);
            }
        });
        tvNumber.setText(String.format(format, current));
        tvNumber.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Option option = Option.createOption();
                option.inputType = InputType.TYPE_CLASS_NUMBER;
                agent.showEditViewDialog(title, option, new OnEditCompleteListener() {
                    @Override
                    public void onEditComplete(String s) {
                        try {
                            int d = Integer.parseInt(s);
                            if (min <= d && d <= max) {
                                tvNumber.setText(String.format(format, d));
                                callback.callBack(d);
                            }
                        } catch (Exception e) {

                        }
                    }
                });
            }
        });
    }

    public static class ItemHolder extends ItemViewHolder<IProduct> {
        private ImageView ivProduct;
        private TextView tvTitle;
        private TextView tvNumber;
        private TextView tvSubTitle;
        private TextView tvPrice;

        @Override
        protected void initComponent() {
            ivProduct = (ImageView) super.findViewById(R.id.iv_product);
            tvTitle = (TextView) super.findViewById(R.id.tv_title);
            tvNumber = (TextView) super.findViewById(R.id.tv_number);
            tvSubTitle = (TextView) super.findViewById(R.id.tv_sub_title);
            tvPrice = (TextView) super.findViewById(R.id.tv_price);
        }


        public ItemHolder(Context mContext, ListView lv) {
            super(mContext, lv, R.layout.listitem_order);
        }

        @Override
        public ItemViewHolder<IProduct> buildNewSelf(Context context) {
            return new ItemHolder(context, null);
        }

        @Override
        public void display(int i, IProduct iProduct) {
            ImageHelper.displayImage(iProduct.getDefaultImage(), ivProduct);
            tvTitle.setText(iProduct.getName());
            tvSubTitle.setText(iProduct.getSpecValue());
            tvNumber.setText(String.format("x %s", iProduct.getBuyNum()));
            tvPrice.setText(String.format("￥%.2f", iProduct.getPrice()));
        }

    }


    private static class Params {
        private BuyCarData buyCarData;
        private BuyNowData buyNowData;
        private String cartIds;

        public Params(String cartIds, BuyCarData buyCarData, BuyNowData buyNowData) {
            this.buyCarData = buyCarData;
            this.buyNowData = buyNowData;
            this.cartIds = cartIds;
        }

        public boolean isBuyNow() {
            return this.cartIds == null;
        }
    }

    public static class Major extends Agent.Major {

        public Major(Class<? extends OrderAddActivity> clazz) {
            super(clazz);
        }

        public void startActivity(Context src, BuyNowData data) {
            buildParams(src, new Params(null, null, data)).startActivity();
        }

        public void startActivity(Context src, String cardIds, BuyCarData data) {
            buildParams(src, new Params(cardIds, data, null)).startActivity();
        }

        public Params parseParams(Activity target) {
            return parseParam(target, Params.class);
        }

    }
}

package cn.wingene.mallxm.purchase;

import java.math.BigDecimal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import junze.java.able.IBuilder;
import junze.java.able.ICallBack;

import junze.widget.HightMatchListView;
import junze.widget.Tile;

import junze.android.ui.ItemViewHolder;
import junze.androidxf.core.Agent;

import cn.wingene.mall.R;
import cn.wingene.mallx.universalimageloader.ImageHelper;
import cn.wingene.mallxf.ui.MyBaseActivity;
import cn.wingene.mallxm.purchase.ask.AskBuyCart.BuyCarData;
import cn.wingene.mallxm.purchase.ask.AskBuyNow.BuyNowData;
import cn.wingene.mallxm.purchase.ask.AskOrderCreateBuyCart;
import cn.wingene.mallxm.purchase.ask.AskOrderCreateBuyNow;
import cn.wingene.mallxm.purchase.ask.AskOrderDetail.OrderDetail;
import cn.wingene.mallxm.purchase.bean.Account;
import cn.wingene.mallxm.purchase.bean.able.IAddOrder;
import cn.wingene.mallxm.purchase.bean.able.IAddress;
import cn.wingene.mallxm.purchase.bean.able.IProduct;
import cn.wingene.mallxm.purchase.tool.NumberTool;
import cn.wingene.mallxm.purchase.tool.PayHelper;
import cn.wingene.mallxm.purchase.tool.PayHelper.OnOrderBuild;
import cn.wingene.mallxm.purchase.tool.PayHelper.PayMothed;

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
    private int mAcceptIntegral;

    private PayHelper mPayHelper;
    private ProductItemHolder mItemHolder;


    private Tile tlBack;
    private Tile tlService;
    private LinearLayout llytAddress;
    private LinearLayout llytAddress1;
    private TextView tvName;
    private TextView tvAddress;
    private TextView tvPhone;
    private TextView tvAddress2;
    private HightMatchListView lvOrder;
    private TextView tvTotal;
    private TextView tvIntegral;
    private TextView tvAcceptIntegral;
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
        tlService = (Tile) super.findViewById(R.id.tl_service);
        llytAddress = (LinearLayout) super.findViewById(R.id.llyt_address);
        llytAddress1 = (LinearLayout) super.findViewById(R.id.llyt_address_1);
        tvName = (TextView) super.findViewById(R.id.tv_name);
        tvAddress = (TextView) super.findViewById(R.id.tv_address);
        tvPhone = (TextView) super.findViewById(R.id.tv_phone);
        tvAddress2 = (TextView) super.findViewById(R.id.tv_address_2);
        lvOrder = (HightMatchListView) super.findViewById(R.id.lv_order);
        tvTotal = (TextView) super.findViewById(R.id.tv_total);
        tvIntegral = (TextView) super.findViewById(R.id.tv_integral);
        tvAcceptIntegral = (TextView) super.findViewById(R.id.tv_accept_integral);
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
        mItemHolder = ProductItemHolder.createForOrderAdd(this, lvOrder);
        IAddOrder bean = mParams.isBuyNow() ? mParams.buyNowData : mParams.buyCarData;
        mAddress = bean.getAddress();
        mItemHolder.addAll(bean.getProductList());
        mAddress = bean.getAddress();
        mSumPrice = bean.getSumPrice();
        mAccount = bean.getAccount();
        mAcceptIntegral = bean.getAcceptIntegral();
        mPayPrice = bean.getPayPrice();
        fillAcceptPay();
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
                if (mPayHelper == null) {
                    mPayHelper = new PayHelper(getAgent());
                    mPayHelper.setOnOrderBuild(new OnOrderBuild() {
                        @Override
                        public void onOrderBuild(final PayHelper helper, final PayMothed payMothed, final double
                                amount, final int integral) {
                            if (mParams.isBuyNow()) {
                                ask(new AskOrderCreateBuyNow.Request(mParams.buyNowData.getProduct(), mAddress
                                        .getId()) {
                                    @Override
                                    public void updateUI(AskOrderCreateBuyNow.Response rsp) {
                                        helper.askPay(rsp.data, payMothed, amount, integral);
                                    }
                                });
                            } else {
                                ask(new AskOrderCreateBuyCart.Request(mParams.cartIds, mAddress.getId()) {
                                    @Override
                                    public void updateUI(AskOrderCreateBuyCart.Response rsp) {
                                        helper.askPay(rsp.data, payMothed, amount, integral);
                                    }
                                });
                            }
                        }
                    });
                }
                mPayHelper.showBottomDialog(mPayPrice, mAmount, mIntegral);
            }
        });
        if (mAddress == null) {
            AddressAddActivity.major.startForResult(getActivity(), RC_ADDRESS_ADD);
        }

        bindNumber();
    }

    private void fillAcceptPay() {
        mIntegral = (int) Math.min((double) getRealAcceptIntegral(), mSumPrice);
        mAmount = Math.min(mAccount.getAmount(),round2(mSumPrice-mIntegral));
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
                return Math.min(mAccount.getAmount(), mSumPrice - (double) mIntegral);
            }
        }, tvAmountReduce, tvAmountNumber, tvAmountIncrease, new ICallBack<Double>() {
            @Override
            public void callBack(Double aDouble) {
                mAmount = new BigDecimal(aDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                refreshUI();
            }
        });

        tvIntegralNumber.setText(String.format("%s", mIntegral));
        NumberTool.bindInteger(getAgent(), "请输入使用应币的值", 0, new IBuilder<Integer>() {
            @Override
            public Integer build() {
                return mIntegral;
            }
        }, new IBuilder<Integer>() {
            @Override
            public Integer build() {
                return Math.min(getRealAcceptIntegral(), (int) (mSumPrice - mAmount));
            }
        }, tvIntegralReduce, tvIntegralNumber, tvIntegralIncrease, new ICallBack<Integer>() {
            @Override
            public void callBack(Integer integer) {
                mIntegral = integer;
                refreshUI();
            }
        });
    }

    void updatePayPrice() {
        mPayPrice = round2(mSumPrice - mAmount - mIntegral);
    }

    public static double round2(double value){
        return new BigDecimal(value).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public int getRealAcceptIntegral(){
        return Math.min(mAccount.getIntegral(),mAcceptIntegral);
    }

    public void refreshUI() {
        updatePayPrice();
        setAddress(mAddress);
        mItemHolder.notifyDataSetChanged();
        tvTotal.setText(String.format("￥%.2f", mSumPrice));
        tvAmountNumber.setText(String.format("%s", mAmount));
        tvIntegralNumber.setText(String.format("%s", mIntegral));
        setAccount(mAccount);
        tvRealTotal.setText(String.format("￥%s", mPayPrice));
        if(mAcceptIntegral==0){
            tvAcceptIntegral.setText("不可抵用");
        }else{
            tvAcceptIntegral.setText(String.format("可抵￥%s",mAcceptIntegral));
        }

    }


    public void setAddress(IAddress address) {
        llytAddress1.setVisibility(address != null ? View.VISIBLE : View.GONE);
        tvAddress2.setVisibility(address == null ? View.VISIBLE : View.GONE);
        tvName.setText(address != null ? address.getConsignee() : "");
        tvPhone.setText(address != null ? address.getMobile() : "");
        tvAddress.setText(address != null ? address.getRegion() + address.getAddress() : "");

    }

    public void setAccount(Account account) {
        tvAmount.setText(String.format("当前游币￥%.1f", account.getAmount()));
        tvIntegral.setText(String.format("当前应币￥%s", account.getIntegral()));
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

    public static class ProductItemHolder extends ItemViewHolder<IProduct> {
        int mLayout;

        private ImageView ivProduct;
        private TextView tvTitle;
        private TextView tvNumber;
        private TextView tvSubTitle;
        private TextView tvPrice;

        @Override
        protected void initComponent() {
            ivProduct = (ImageView) super.findViewById(R.id.iv_product);
            tvTitle = (TextView) super.findViewById(R.id.tv_title);
            tvNumber = (TextView) super.findViewById(R.id.et_number);
            tvSubTitle = (TextView) super.findViewById(R.id.tv_sub_title);
            tvPrice = (TextView) super.findViewById(R.id.tv_price);
        }

        //        public ProductItemHolder(Context mContext, ListView lv) {
        //            super(mContext, lv, R.layout.listitem_order_add_product);
        //        }

        public ProductItemHolder(Context mContext, ListView lv, int layout) {
            super(mContext, lv, layout);
            this.mLayout = layout;
        }

        public static ProductItemHolder createForOrderAdd(Context context, ListView lv) {
            return new ProductItemHolder(context, lv, R.layout.listitem_order_add_product);
        }

        public static ProductItemHolder createForOrderList(Context context, ListView lv) {
            return new ProductItemHolder(context, lv, R.layout.listitem_order_list_product);
        }

        @Override
        public ItemViewHolder<IProduct> buildNewSelf(Context context) {
            return new ProductItemHolder(context, null, mLayout);
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
        private OrderDetail orderDetail;

        //        public Params(String cartIds, BuyCarData buyCarData, BuyNowData buyNowData) {
        //            this.buyCarData = buyCarData;
        //            this.buyNowData = buyNowData;
        //            this.cartIds = cartIds;
        //        }

        public Params(String cartIds, BuyCarData buyCarData, BuyNowData buyNowData, OrderDetail orderDetail) {
            this.buyCarData = buyCarData;
            this.buyNowData = buyNowData;
            this.cartIds = cartIds;
            this.orderDetail = orderDetail;
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
            buildParams(src, new Params(null, null, data, null)).startActivity();
        }
        public void startActivity(Context src,OrderDetail orderDetail) {
            buildParams(src, new Params(null, null, null, orderDetail)).startActivity();
        }

        public void startActivity(Context src, String cardIds, BuyCarData data) {
            buildParams(src, new Params(cardIds, data, null, null)).startActivity();
        }

        public Params parseParams(Activity target) {
            return parseParam(target, Params.class);
        }

    }
}

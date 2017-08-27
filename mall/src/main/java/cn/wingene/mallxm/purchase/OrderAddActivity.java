package cn.wingene.mallxm.purchase;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import junze.java.able.ICallBack;

import cn.wingene.mall.R;
import cn.wingene.mallx.universalimageloader.ImageHelper;
import cn.wingene.mallxf.ui.MyBaseActivity;
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

import junze.widget.HightMatchListView;
import junze.widget.Tile;

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
    private Account mAccount;
    private double mTotal;
    private double mRealTotal;

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
    private TextView tvAmount;
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
        tvAmount = (TextView) super.findViewById(R.id.tv_amount);
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
        mTotal = bean.getSumPrice();
        mRealTotal = bean.getPayPrice();
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
                mPayChoiseDialog.show(getAgent(), mRealTotal, new OnClickListener() {
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
    }

    public void askPay(Order order, final boolean isAlipay) {
        ask(new AskSubmitPayNow.Request(order.getNo(), isAlipay, 0, order.getAccount().getAmount(), order
                .getAccount().getIntegral()) {
            @Override
            public void updateUI(AskSubmitPayNow.Response rsp) {
                showMsgDialog("提示", "支付功能开发中...", "我知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
            }
        });
    }


    public void refreshUI() {
        setAddress(mAddress);
        mItemHolder.notifyDataSetChanged();
        tvTotal.setText(String.format("￥%.2f", mTotal));
        setAccount(mAccount);
        tvRealTotal.setText(String.format("￥%.2f", mRealTotal));
    }


    public void setAddress(IAddress address) {
        tvName.setText(address != null ? address.getConsignee() : "");
        tvPhone.setText(address != null ? address.getMobile() : null);
        tvAddress.setText(address != null ? address.getAddress() : null);
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
